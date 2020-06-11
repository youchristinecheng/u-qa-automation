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
    private String apiEndPoint;
    private String kbankMockPoint;
    private Market currenMarkettValue;
    private String productId;
    private String bstackAuthUserName;
    private String bstackAuthPwd;
    private String bstackBuildName;
    private String bstackAppHashId;
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
    public void setMockUpEndPoint(String apiEndPOint, String kbankMockPoint) {
        this.apiEndPoint = apiEndPOint;
        this.kbankMockPoint = kbankMockPoint;
    }
    public void setMarket(Market value){ this.currenMarkettValue = value; }
    public void setIsDevEnv(boolean value) {this.isDevEnv = value; }
    public void setBstackInfo(String authUserName, String authPwd, String buildName, String appHashId){
        this.bstackAuthUserName = authUserName;
        this.bstackAuthPwd = authPwd;
        this.bstackBuildName = buildName;
        this.bstackAppHashId = appHashId;

    }

    public YouAPI(){
        this.apiEndPoint = "";
        this.kbankMockPoint = "";
        backDoorAuthUserName = "qa";
        backDoorAuthPwd = "youtrip1@3";
        Unirest.config().verifySsl(false);
        isDevEnv = false;
        this.setBstackInfo("", "", "", "");
    }

    /*
     * ###### Backdoor API calls######
     */
    public String getOTP(String mprefix, String mnumber) {

        String url_backdoorOTP = (backDoorEndPoint + "/onboarding/otp/" + mprefix + "/" + mnumber);
        String otpCode = null;
        System.out.println("API CALL: " + url_backdoorOTP);

        otpCode = Unirest.get(url_backdoorOTP)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject()
                .getString("password");

        return otpCode;
    }

    public String getActivateCardEmailLink (String userID) throws InterruptedException {

        //get device ID
        String deviceID = data_getTestUserDeviceID(userID);
        Thread.sleep(5000);
        //magic link API call
        String url_activateCardMagicLink = (backDoorEndPoint + "/onboarding/magicLink/YouTrip/"+deviceID+"/activateCard");
        System.out.println("API CALL: " +url_activateCardMagicLink);

        String token = Unirest.get(url_activateCardMagicLink)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject()
                .getString("token");


        //setup email url for activate card and return it
        String domainEndPoint = this.apiEndPoint.substring(7);
        String activateCardEmailURL = ("https://" + domainEndPoint + "/link/token-activate-card/"+token);
        System.out.println("TEST DATA: Activation Email Link is " + activateCardEmailURL);
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
     * ###### Frontend API call Hacks######
     */
    public HashMap<String, String> hack_genUserMetaData(String mprefix, String phoneNumber) throws NoSuchFieldException{
        HashMap<String, String> result = new HashMap<>();
        String usrId = this.data_getTestUserIdByPhoneNumber(mprefix, phoneNumber);
        result.put("user_id", usrId);

        HashMap<String, String> currentDeviceMeta = this.data_getTestUserRegisteredDevice(usrId);
        /*"deviceId",
        "deviceName"
        "platform",
        "osVersion",
        "appID"
        "appVersion"
        "lang"*/

        String req_uri = (this.apiEndPoint + "/public/device");
        HttpResponse<JsonNode> response = Unirest.post(req_uri)
                .header("Content-Type", "application/json")
                .header("x-request-id", "register-device-" + util.getTimestamp())
                .body("{\"device_id\": \"" + currentDeviceMeta.get("deviceId") + "\"," +
                        "\"device_name\": \"" + currentDeviceMeta.get("deviceName") + "\"," +
                        "\"platform\": \""+ currentDeviceMeta.get("platform") + "\"," +
                        "\"os_ver\": \"" + currentDeviceMeta.get("osVersion") + "\"," +
                        "\"app_id\": \""  + currentDeviceMeta.get("appID") + "\"," +
                        "\"app_ver\": \"" + currentDeviceMeta.get("appVersion") + "\"," +
                        "\"language\": \"" + currentDeviceMeta.get("lang") + "\"," +
                        "\"timezone\": \"Asia/Hong_Kong\"}").asJson();

        JSONObject responseJson = response.getBody().getObject();
        String oauth_key = responseJson.getString("oauth_client_key");
        String oauth_passpwd = responseJson.getString("oauth_client_secret");

        req_uri = (this.apiEndPoint + "/device/otp");
        response = Unirest.post(req_uri)
                .header("Content-Type", "application/json")
                .header("x-request-id", "request-otp-" + util.getTimestamp())
                .basicAuth(oauth_key, oauth_passpwd)
                .body("{\"mcc\": \"" + mprefix + "\"," +
                        "\"phone_number\": \"" + phoneNumber + "\"," +
                        "\"language\": \"en-US\"}").asJson();

        responseJson = response.getBody().getObject();
        String otp_key = responseJson.getString("id");

        String otpCode = this.getOTP(mprefix, phoneNumber);
        req_uri = (this.apiEndPoint + "/device/otp/" + otp_key);
        response = Unirest.post(req_uri)
                .header("Content-Type", "application/json")
                .header("x-request-id", "verify-otp-" + util.getTimestamp())
                .basicAuth(oauth_key, oauth_passpwd)
                .body("{\"password\": \"" + otpCode + "\"," +
                        "\"mcc\": \"" + mprefix + "\"," +
                        "\"phone_number\": \"" + phoneNumber + "\"}").asJson();
        responseJson = response.getBody().getObject();
        String access_token = responseJson.getString("access_token");
        String refresh_token = responseJson.getString("refresh_token");
        System.out.println("access_token:" + access_token);
        System.out.println("refresh_token:" + refresh_token);
        result.put("access_token", access_token);
        result.put("refresh_token", refresh_token);

        req_uri = (this.apiEndPoint + "/me");
        response = Unirest.get(req_uri)
                .header("Content-Type", "application/json")
                .header("x-request-id", "get-user-" + util.getTimestamp())
                .header("Authorization", "Bearer " + access_token).asJson();
        assertEquals(200, response.getStatus());

        return result;
    }

    public void hack_thRequestAndPassKYC(boolean isPCCard, String thaiIdNumber, String youId, String mprefix, String phoneNumber, String email) throws NoSuchFieldException, InterruptedException {
        if (!isDevEnv || this.kbankMockPoint.equals("")){
            throw  new ValueException("Provided Environment is not TH Dev environment to execute mocking process");
        }

        System.out.println("TEST STEP: work around log-in generate User meta data");
        HashMap<String, String> userMetaMap = this.hack_genUserMetaData(mprefix, phoneNumber);
        String accessToken = userMetaMap.get("access_token");

        HttpResponse<JsonNode> response;

        response = Unirest.post(this.apiEndPoint + "/me/kyc/" + this.productId + "/request")
                .header("Content-Type", "application/json")
                .header("x-request-id", "th-kyc-" + util.getTimestamp())
                .header("Authorization", "Bearer " + accessToken)
                .asJson();

        assertEquals(200, response.getStatus());

        JSONObject responseJson = response.getBody().getObject();
        String tokenUri = responseJson.getJSONObject("deeplink").getString("uri");
        int idx = tokenUri.indexOf("=");
        String tokenId = tokenUri.substring(idx + 1);
        System.out.println(tokenId);
        Thread.sleep(1000);

        System.out.println("TEST STEP: Mocking approving KYC");
        response = Unirest.post(this.kbankMockPoint + "/authentication")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\t\"token_id\": \"" + tokenId + "\",\n" +
                        "\t\"status\": \"10\",\n" +
                        "\t\"email\": \"" + email + "\"\n" +
                        "}").asJson();

        assertEquals(200, response.getStatus());

        System.out.println("TEST STEP: Checking KYC");
        response = Unirest.get(this.apiEndPoint + "/me/kyc/" + this.productId)
                .header("Content-Type", "application/json")
                .header("x-request-id", "th-kyc-" + util.getTimestamp())
                .header("Authorization", "Bearer " + accessToken)
                .asJson();

        assertEquals(200, response.getStatus());
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

    public String data_getTestUserIdByPhoneNumber(String mprefix, String phoneNumber) throws NoSuchFieldException {
        String url_getTestUserId = (this.dataBackDoorEndPoint + "/testUserData/getUserByPhoneNumber/" + mprefix + "/" + phoneNumber);

        System.out.println("API CALL: " + url_getTestUserId);

        JSONObject rspbody = Unirest.get(url_getTestUserId)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();

        Boolean _isFound = rspbody.getBoolean("IsFound");
        if(!_isFound)
            throw new NoSuchFieldException("User Id with given PhoneNumber: \"" + mprefix + "\" \"" + phoneNumber + "\" is not found.");

        JSONObject usrObj = rspbody.getJSONObject("User");
        String userId = usrObj.getString("ID");
        return userId;
    }

    public HashMap<String, String> data_getTestUserRegisteredDevice(String userID) throws NoSuchFieldException {
        HashMap<String, String> result = new HashMap<>();
        String url_getTestUserRegisteredDeviceId = (this.dataBackDoorEndPoint + "/testUserData/getDeviceID/" +  userID);

        System.out.println("API CALL: " + url_getTestUserRegisteredDeviceId);

        JSONObject Rspbody = Unirest.get(url_getTestUserRegisteredDeviceId)
                .basicAuth(backDoorAuthUserName, backDoorAuthPwd)
                .asJson()
                .getBody()
                .getObject();

        /*
         * "ID": "",
         * "DeviceID": "45BDCD5B-098E-4A00-85FA-737292992CF4",
         * "DeviceName": "iPhone 8 Plus",
         * "Platform": "iOS",
         * "OSVersion": "13.1.2",
         * "AppID": "YouTrip",
         * "AppVersion": "3.6.0-sit",
         * "Language": "en-SG",
         * "Timezone": "GMT+8",
         * "IsFound": true
         * */

        result.put("deviceId", Rspbody.getString("DeviceID"));
        result.put("deviceName", Rspbody.getString("DeviceName"));
        result.put("platform", Rspbody.getString("Platform"));
        result.put("osVersion", Rspbody.getString("OSVersion"));
        result.put("appID", Rspbody.getString("AppID"));
        result.put("appVersion", Rspbody.getString("AppVersion"));
        result.put("lang", Rspbody.getString("Language"));
        Boolean _isFound = Rspbody.getBoolean("IsFound");

        if(!_isFound)
            throw new NoSuchFieldException("Device Id with given UserID: \"" + userID + "\" is not found.");
        return result;
    }

    private String browserStack_getBuildHashID(String buildName) {
        String url_browserstackBuilds = ("https://api-cloud.browserstack.com/app-automate/builds.json");
        String buildId = null;
        System.out.println("API CALL: " +url_browserstackBuilds);
        HttpResponse<JsonNode> buildjsonResponse = Unirest.get(url_browserstackBuilds)
                .basicAuth(this.bstackAuthUserName, this.bstackAuthPwd)
                .asJson();
        //get full JSON Array response body
        JSONArray jArr = buildjsonResponse.getBody().getArray();
        JSONObject jObj = null;
        for(int i =0; i <jArr.length();i++){
            // Iterate the Array to find the correct build with matched build name
            System.out.println("Finding build with build name: " + buildName);
            jObj = jArr.getJSONObject(i).getJSONObject("automation_build");
            System.out.println("Matching build name: " + buildName + " with item: " + jObj.getString("name"));
            if(jObj.getString("name").equals(buildName)){
                break;
            }
            System.out.println("Build name mismatch - Next item");
        }

        assert jObj != null;
        buildId = jObj.getString("hashed_id");
        return buildId;
    }

    public String browserStack_getSessionInfo() {
        String buildHashId = this.browserStack_getBuildHashID(this.bstackBuildName);
        String url_browserstackSessions = ("https://api-cloud.browserstack.com/app-automate/builds/" + buildHashId + "/sessions.json");
        String buildId = null;
        System.out.println("API CALL: " +url_browserstackSessions);

        HttpResponse<JsonNode> buildjsonResponse = Unirest.get(url_browserstackSessions)
                .basicAuth(this.bstackAuthUserName, this.bstackAuthPwd)
                .asJson();
        //get full JSON Array response body
        JSONArray jArr = buildjsonResponse.getBody().getArray();
        JSONObject jObj = null;
        for(int i =0; i <jArr.length();i++){
            // Iterate the Array to find the correct build with matched build name
            System.out.println("Finding build with build name: " + this.bstackBuildName);
            jObj = jArr.getJSONObject(i).getJSONObject("automation_session");
            System.out.println("Matching build name: " + this.bstackBuildName + " with item: " + jObj.getString("build_name"));
            if(jObj.getString("build_name").equals(this.bstackBuildName)){
                JSONObject appInfoJObj = jObj.getJSONObject("app_details");
                System.out.println("Matching app hash: " + this.bstackAppHashId + " with item: " + appInfoJObj.getString("app_url"));
                if(appInfoJObj.getString("app_url").equals(this.bstackAppHashId)){
                    break;
                }else{
                    System.out.println("App hash mismatch - Next item");
                    continue;
                }
            }
            System.out.println("Build name mismatch - Next item");
        }

        String public_url =  jObj.getString("public_url");

        return public_url;
    }

    public boolean browserStack_isTestbrowserStackSession(){
        return !this.bstackAuthPwd.equals("") && !this.bstackAuthUserName.equals("");
    }
}
