package TestBased;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import TestBased.TestAccountData.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class YouAPI {

    private String kycRejectReason;
    private String backDoorEndPoint;
    private String dataBackDoorEndPoint;
    private String ypEndPoint;
    private String backDoorAuthUserName;
    private String backDoorAuthPwd;
    private Market currenMarkettValue;
    private String productId;
    private boolean isDevEnv;

    public Utils util = new Utils();

    public String getKycRejectReason(){return this.kycRejectReason;}
    public Boolean getIsDevEnv(){ return this.isDevEnv; }
    public void setKycRejectReason(String value){this.kycRejectReason = value;}
    public void setBackDoorEndPoint(String endPoint, String productId, String UserName, String Password) {
        if (endPoint.endsWith("/"))
            endPoint = endPoint.replaceAll("/$", "");
        this.backDoorEndPoint = endPoint;
        this.productId = productId;
        // Remain to its default value as if not given
        if (UserName != null && Password != null) {
            this.backDoorAuthUserName = UserName;
            this.backDoorAuthPwd = Password;
        }
    }
    public void setYPEndPoint(String value){ this.ypEndPoint = value; }
    public void setDataBackDoorEndPoint(String value){ this.dataBackDoorEndPoint = value; }
    public void setMarket(Market value){ this.currenMarkettValue = value; }
    public void setIsDevEnv(boolean value) {this.isDevEnv = value; }

    public YouAPI(){
        backDoorAuthUserName = "qa";
        backDoorAuthPwd = "youtrip1@3";
        Unirest.config().verifySsl(false);
        isDevEnv = false;
    }

    /*
     * ###### Backdoor API calls######
     */
    public String getOTP(String mprefix, String mnumber) {

        String url_backdoorOTP = (backDoorEndPoint + "/onboarding/otp/" + mprefix + "/" + mnumber);
        String otpCode = null;

        otpCode = Unirest.get(url_backdoorOTP)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject()
                .getString("password");

        System.out.println("API CALL: " + url_backdoorOTP);
        return otpCode;
    }

    public String getActivateCardEmailLink (String userID) throws InterruptedException {

        //get device ID
        String deviceID = data_getTestUserDeviceID(userID);
        Thread.sleep(5000);
        //magic link API call
        String url_activateCardMagicLink = (backDoorEndPoint + "/onboarding/magicLink/YouTrip/"+deviceID+"/activateCard");

        String token = Unirest.get(url_activateCardMagicLink)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject()
                .getString("token");

        System.out.println("API CALL: " +url_activateCardMagicLink);

        //setup email url for activate card and return it
        String activateCardEmailURL = ("https://api.sit.you.co/v2/link/token-activate-card/"+token);
        System.out.println("TEST DATA: Activation Email Link is " +activateCardEmailURL);
        return activateCardEmailURL;
    }

    public TestCardData devEnvGenerateCard(boolean isPCCard, String userId) throws ValueException, NotImplementedException{
        if(!isDevEnv){
            throw new ValueException("This function is only support from Dev Environemnt");
        }

        String url_generateCardRequest = "";
        if(!isPCCard) {
            url_generateCardRequest = (backDoorEndPoint + "/onboarding/cards/npc/" + this.productId);
        }else{
            url_generateCardRequest = (backDoorEndPoint + "/onboarding/cards/pc/" + this.productId + "/" + userId);
        }

        HttpResponse<JsonNode> res = Unirest.post(url_generateCardRequest)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .header("x-request-id", "generateCard"+util.getTimestamp())
                .asJson();
        JSONObject responseJson = res.getBody().getObject();
        TestCardData card_data = new TestCardData();

        card_data.Id = (isPCCard) ? userId :  responseJson.getString("card_id_token");
        card_data.CardIDToken = responseJson.getString("card_id_token");
        card_data.YouId = responseJson.getString("you_id");
        card_data.TestCardMarket = this.currenMarkettValue;
        card_data.Status = (isPCCard) ? CardStatus.Inactive : CardStatus.NPCPending;
        card_data.UnderUse = false;
        card_data.NumOfReplace = 0;
        card_data.TestCardCardType = (isPCCard) ? CardType.PC : CardType.NPC;
        card_data.printTestCardData("NEW GEN ON DEV");

        this.data_createTestCard(card_data);

        return card_data;
    }

    /*
     * ###### YouPortal API calls ######
     */

    public String yp_getToken() {
        String url_backdoorYP = (backDoorEndPoint + "/youportal/token?scopes=https://www.googleapis.com/auth/userinfo.email,https://www.googleapis.com/auth/userinfo.profile,openid");
        System.out.println("API CALL: " + url_backdoorYP);

        String ypToken = null;
        Unirest.config().verifySsl(false);
        ypToken = Unirest.get(url_backdoorYP)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .header("x-request-id", "token" + util.getTimestamp())
                .asJson()
                .getBody()
                .getObject()
                .getString("token");


        return ypToken;
    }

    public String yp_getKYC(String kycRefNo) {

        String token = yp_getToken();

        String url_getKYC = (ypEndPoint + "/api/kyc?ReferenceIDs=" +kycRefNo);
        System.out.println("API CALL: " +url_getKYC);

        HttpResponse<JsonNode> getKYCjsonResponse = Unirest.get(url_getKYC)
                .header("Authorization", "Bearer "+token)
                .header("x-request-id", "getKYC"+util.getTimestamp())
                .header("x-yp-role", "Singapore Admin")
                .asJson();

        //get full JSON response body
        JSONObject responseJson = getKYCjsonResponse.getBody().getObject();
        //get necessary array data and get KYC ID
        JSONArray responseArray = responseJson.getJSONArray("FirstPageResults");
        JSONObject arrayObj = responseArray.getJSONObject(0);
        String ypKycID = arrayObj.getString("ID");
        System.out.println("TEST DATA: KYC ID is " +ypKycID);

        return ypKycID;
    }

    public Map<String, String> yp_getKYCdetails(String kycRefNo) {
        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);
        String addLine1;
        String addLine2;
        String postalCode;
        String firstName;
        String lastName;
        String nameOnCard;
        String gender;
        String idNum;
        String dob;
        String nationality;
        String userId;
        String cardType;

        //call get KYC details
        String url_getKYCDetails  = (ypEndPoint + "/api/kyc/" +kycID);
        System.out.println("API CALL: " +url_getKYCDetails);

        HttpResponse<JsonNode> getKYCjsonResponse = Unirest.get(url_getKYCDetails)
                .header("Authorization", "Bearer "+token)
                .header("x-request-id", "getKYC"+util.getTimestamp())
                .header("x-yp-role", "Singapore Admin")
                .asJson();

        //get full JSON response body
        JSONObject responseJson = getKYCjsonResponse.getBody().getObject();
        //store data
        userId = responseJson.getString("UserID");
        System.out.println("get kyc: User ID is " +userId);
        cardType = responseJson.getString("CardType");
        System.out.println("get card type: Card Type is " +cardType);
        firstName = responseJson.getString("FirstName");
        System.out.println("get kyc: First Name is " +firstName);
        lastName = responseJson.getString("LastName");
        System.out.println("get kyc: Last Name is " +lastName);
        if (cardType.equals(CardType.PC.toString())){
            nameOnCard = responseJson.getString("NameOnCard");
            System.out.println("get kyc: Name On Card  is " + nameOnCard);
        }else{
            nameOnCard = null;
        }
        idNum = responseJson.getString("IDNum");
        System.out.println("get kyc: ID Number is " +idNum);
        dob = responseJson.getString("DateOfBirth");
        System.out.println("get kyc: Date of Birth is " +dob);
        nationality = responseJson.getString("Nationality");
        System.out.println("get kyc: Nationality is " +nationality);
        gender = responseJson.getString("Gender");
        System.out.println("get kyc: Gender is " +gender);

        //address to handle separately
        JSONObject addJson = responseJson.getJSONObject("Address");
        addLine1 = addJson.getString("AddressLine1");
        System.out.println("get kyc: Address Line 1 is " +addLine1);
        addLine2 = addJson.getString("AddressLine2");
        System.out.println("get kyc: Address Line 2 is " +addLine2);
        postalCode = addJson.getString("PostalCode");
        System.out.println("get kyc: Postal Code is " +postalCode);

        Map<String, String> result = new HashMap<>();

        result.put("firstName", firstName);
        result.put("lastName", lastName);
        result.put("nameOnCard", nameOnCard);
        result.put("idNum", idNum);
        result.put("dob", dob);
        result.put("nationality", nationality);
        result.put("gender", gender);
        result.put("addLine1", addLine1);
        result.put("addLine2", addLine2);
        result.put("postalCode", postalCode);
        result.put("userId", userId);
        result.put("cardType", cardType);

        return result;

    }

    public void yp_fullReject(String kycRefNo) {
        this.setKycRejectReason("AUTO TEST: full KYC rejection testing");
        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);

        String url_fullRejectKYC = (ypEndPoint + "/api/kyc/" +kycID+ "/reject");
        System.out.println("API CALL: " + url_fullRejectKYC);

        HttpResponse<JsonNode> rejectKYCjsonResponse = Unirest.put(url_fullRejectKYC)
                .header("Authorization", "Bearer " + token)
                .header("x-request-id", "fullRejectKYC" + util.getTimestamp())
                .header("x-yp-role", "Singapore Admin")
                .header("Content-Type", "application/json")
                .body("{\"Reason\":\"" + getKycRejectReason() + "\"}")
                .asJson();

        assertEquals(200, rejectKYCjsonResponse.getStatus());
    }

    public void yp_partialReject(String kycRefNo) {
        //notes: for partial reject you must pass all personal, residental information even if it is not editted
        //approach is to call get kyc first, store the data and past it into partial reject api
        this.setKycRejectReason("AUTO TEST: partial KYC rejection testing - changed address line 2 and first name");
        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);
        Map<String, String> kycDetails = yp_getKYCdetails(kycRefNo);

        //call partial reject
        String url_partialRejectKYC = (ypEndPoint + "/api/kyc/" +kycID+ "/partially_reject");
        System.out.println("API CALL: " +url_partialRejectKYC);

        if (kycDetails.get("cardType").equals("PC")) {
            //for PC partial rejects, name on card is required to be passed in request
            HttpResponse<JsonNode> partialrejectKYCjsonResponse = Unirest.put(url_partialRejectKYC)
                    .header("Authorization", "Bearer " + token)
                    .header("x-request-id", "fullPartialKYC" + util.getTimestamp())
                    .header("x-yp-role", "Singapore Admin")
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"Reason\": \"" + this.getKycRejectReason() + "\",\n" +
                            "  \"EditedKYC\": {\n" +
                            "    \"Address\": {\n" +
                            "      \"AddressLine1\": \"" + kycDetails.get("addLine1") + "\",\n" +
                            "      \"AddressLine2\": \"PARTIAL EDIT TEST\",\n" +
                            "      \"PostalCode\": \"" + kycDetails.get("postalCode") + "\"\n" +
                            "    },\n" +
                            "    \"FirstName\": \"Auto YP Edit\",\n" +
                            "    \"LastName\": \"" + kycDetails.get("lastName") + "\",\n" +
                            "    \"NameOnCard\": \"" + kycDetails.get("nameOnCard") + "\",\n" +
                            "    \"Gender\": \"" + kycDetails.get("gender") + "\",\n" +
                            "    \"IDNum\": \"" + kycDetails.get("idNum") + "\",\n" +
                            "    \"DateOfBirth\": \"" + kycDetails.get("dob") + "\",\n" +
                            "    \"Nationality\": \"" + kycDetails.get("nationality") + "\"\n" +
                            "  }\n" +
                            "}")
                    .asJson();

            assertEquals(200, partialrejectKYCjsonResponse.getStatus());
        } else {
            //for NPC partial reject does not need name on card value
            HttpResponse<JsonNode> partialrejectKYCjsonResponse = Unirest.put(url_partialRejectKYC)
                    .header("Authorization", "Bearer "+token)
                    .header("x-request-id", "fullPartialKYC"+util.getTimestamp())
                    .header("x-yp-role", "Singapore Admin")
                    .header("Content-Type", "application/json")
                    .body("{\n" +
                            "  \"Reason\": \"" + this.getKycRejectReason() + "\",\n" +
                            "  \"EditedKYC\": {\n" +
                            "    \"Address\": {\n" +
                            "      \"AddressLine1\": \""+kycDetails.get("addLine1")+"\",\n" +
                            "      \"AddressLine2\": \"PARTIAL EDIT TEST\",\n" +
                            "      \"PostalCode\": \""+kycDetails.get("postalCode")+"\"\n" +
                            "    },\n" +
                            "    \"FirstName\": \"Auto YP Edit\",\n" +
                            "    \"LastName\": \""+kycDetails.get("lastName")+"\",\n" +
                            "    \"Gender\": \""+kycDetails.get("gender")+"\",\n" +
                            "    \"IDNum\": \""+kycDetails.get("idNum")+"\",\n" +
                            "    \"DateOfBirth\": \""+kycDetails.get("dob")+"\",\n" +
                            "    \"Nationality\": \""+kycDetails.get("nationality")+"\"\n" +
                            "  }\n" +
                            "}")
                    .asJson();

            assertEquals(200, partialrejectKYCjsonResponse.getStatus());
        }
    }

    public void yp_approve(String kycRefNo) {
        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);
        Map<String, String> kycDetails = yp_getKYCdetails(kycRefNo);

        String url_approveKYC = (ypEndPoint + "/api/kyc/"+kycID+"/accept?SkipArtemis=true");
        System.out.println("API CALL: " +url_approveKYC);

        HttpResponse<JsonNode> acceptKYCjsonResponse = Unirest.put(url_approveKYC)
                .header("Authorization", "Bearer "+token)
                .header("x-request-id", "acceptKYC"+util.getTimestamp())
                .header("x-yp-role", "Singapore Admin")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"AddressLine1\": \""+kycDetails.get("addLine1")+"\",\n" +
                        "    \"AddressLine2\": \""+kycDetails.get("addLine2")+"\"\n" +
                        "}")
                .asJson();

        assertEquals(200, acceptKYCjsonResponse.getStatus());
    }

    /*
     * ###### Test Data API calls ######
     */

    public void data_createTestUser(TestAccountData data) {
        String url_createUser = (this.dataBackDoorEndPoint + "/testUser/create");

        System.out.println("API CALL: " +url_createUser);

        HttpResponse<JsonNode> partialrejectKYCjsonResponse = Unirest.post(url_createUser)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .header("x-request-id", "createTestUser"+util.getTimestamp())
                .header("Content-Type", "application/json")
                .body(data.toRequestBodyString(true))
                .asJson();

        assertEquals(200, partialrejectKYCjsonResponse.getStatus());

        if(data.TestAccountCardType.equals(CardType.NPC) && data.Card != null){
            data_bindTestCardToTestUser(data.Id, data.Card.Id);
        }
    }

    public void data_updateTestUser(TestAccountData data){
        String url_updateUser = (this.dataBackDoorEndPoint + "/testUser/update");

        System.out.println("API CALL: " +url_updateUser);

        HttpResponse<JsonNode> updateTestUserjsonResponse = Unirest.post(url_updateUser)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .header("x-request-id", "updateTestUser"+util.getTimestamp())
                .header("Content-Type", "application/json")
                .body(data.toRequestBodyString(false))
                .asJson();

        assertEquals(200, updateTestUserjsonResponse.getStatus());
        if(data.Card != null){
            this.data_updateTestCard(data.Card);
        }
    }

    public TestAccountData data_getTestUserByUserID(String userID) throws NoSuchFieldException {
        String url_getTestUser = (this.dataBackDoorEndPoint + "/testUser/" +userID);

        System.out.println("API CALL: " + url_getTestUser);

        JSONObject Rspbody = Unirest.get(url_getTestUser)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();
        return TestAccountData.toTestAccountData(Rspbody);
    }

    public TestAccountData data_getTestUserByCardTypeAndKycStatus(String cardType, String kycStatus) throws NoSuchFieldException {
        String url_getTestUser = (this.dataBackDoorEndPoint + "/testUser/searchNoCardTestUser/" + this.currenMarkettValue.toString() +  "/" + cardType + "/" + kycStatus);

        System.out.println("API CALL: " + url_getTestUser);

        JSONObject Rspbody = Unirest.get(url_getTestUser)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();

        return TestAccountData.toTestAccountData(Rspbody);
    }

    public TestAccountData data_getTestUserByCardTypeAndKycStatusAndCardStatus(String cardType, String kycStatus, String cardStatus) throws NoSuchFieldException{
        String url_getTestUser = (this.dataBackDoorEndPoint + "/testUser/searchTestUser/" + this.currenMarkettValue.toString() +  "/" + cardType + "/" + kycStatus + "/" + cardStatus);

        System.out.println("API CALL: " + url_getTestUser);

        JSONObject Rspbody = Unirest.get(url_getTestUser)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();

        return TestAccountData.toTestAccountData(Rspbody);
    }

    public void data_createTestCard(TestCardData data){
        String url_createCard = (this.dataBackDoorEndPoint + "/testCard/create");

        System.out.println("API CALL: " + url_createCard);

        HttpResponse<JsonNode> updateTestCardjsonResponse = Unirest.post(url_createCard)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .header("x-request-id", "createTestCard"+util.getTimestamp())
                .header("Content-Type", "application/json")
                .body(data.toRequestBodyString())
                .asJson();

        assertEquals(200, updateTestCardjsonResponse.getStatus());
    }

    public void data_updateTestCard(TestCardData data){
        String url_updateCard = (this.dataBackDoorEndPoint + "/testCard/update");

        System.out.println("API CALL: " + url_updateCard);

        HttpResponse<JsonNode> updateTestCardjsonResponse = Unirest.post(url_updateCard)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .header("x-request-id", "updateTestCard"+util.getTimestamp())
                .header("Content-Type", "application/json")
                .body(data.toRequestBodyString())
                .asJson();

        assertEquals(200, updateTestCardjsonResponse.getStatus());
    }

    public TestCardData data_getTestCardByCardTypeAndStatus(String cardType, String cardStatus) throws NoSuchFieldException{
        String url_getTestCaed = (this.dataBackDoorEndPoint + "/testCard/searchTestCard/" + this.currenMarkettValue.toString() +  "/" + cardType + "/" + cardStatus);

        System.out.println("API CALL: " + url_getTestCaed);

        JSONObject Rspbody = Unirest.get(url_getTestCaed)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();

        return TestCardData.toTestCardData(Rspbody);
    }

    public TestCardData data_getTestCardByCardID(String cardID) throws NoSuchFieldException {
        String url_getTestCard = (this.dataBackDoorEndPoint + "/testCard/" +cardID);

        System.out.println("API CALL: " + url_getTestCard);

        JSONObject Rspbody = Unirest.get(url_getTestCard)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();

        return TestCardData.toTestCardData(Rspbody);
    }

    public void data_bindTestCardToTestUser(String userId, String cardId) {
        String url_updateTestUserCard = (this.dataBackDoorEndPoint + "/testUser/bindTestCardToTestUser");

        System.out.println("API CALL: " + url_updateTestUserCard);

        HttpResponse<JsonNode> bindTestCardToTestUserjsonResponse = Unirest.post(url_updateTestUserCard)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .header("x-request-id", "updateTestCard"+util.getTimestamp())
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\t\"userId\":\""+userId+"\",\n" +
                        "\t\"cardId\": \""+cardId+"\"\n" +
                        "}")
                .asJson();
        assertEquals(200, bindTestCardToTestUserjsonResponse.getStatus());
    }

    public String data_getTestUserDeviceID(String userID) {
        String url_getTestUserRegisteredDeviceId = (this.dataBackDoorEndPoint + "/testUserData/getDeviceID/" +  userID);

        System.out.println("API CALL: " + url_getTestUserRegisteredDeviceId);

        JSONObject Rspbody = Unirest.get(url_getTestUserRegisteredDeviceId)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();

        String _deviceId = Rspbody.getString("DeviceID");
        return _deviceId;
    }
}
