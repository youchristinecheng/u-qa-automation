package TestBased;

import TestBased.Utils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class YouAPI {

    private String kycRejectReason;

    public Utils util = new Utils();

    public String getKycRejectReason(){return this.kycRejectReason;}
    public void setKycRejectReason(String value){this.kycRejectReason = value;}

    public String getOTP(String mprefix, String mnumber) {

        String url_backdoorOTP = ("http://backdoor.internal.sg.sit.you.co/onboarding/otp/"+mprefix+"/"+mnumber);
        String otpCode = Unirest.get(url_backdoorOTP)
                .asJson()
                .getBody()
                .getObject()
                .getString("password");

        System.out.println("API CALL: " +url_backdoorOTP);
        return otpCode;
    }

    public Map<String, String> getUserId(String mprefix, String phoneNumber) {
        HashMap<String, String> result = new HashMap<>();
        String baseUri = "http://api.sg.sit.you.co/v2";
        String req_uri = (baseUri + "/public/device");
        HttpResponse<JsonNode> response = Unirest.post(req_uri)
                .header("Content-Type", "application/json")
                .header("x-request-id", "register-device-" + util.getTimestamp())
                .body("{\"device_id\": \"44vic4-20022716280914\"," +
                        "\"device_name\": \"Samsung Note 9\"," +
                        "\"platform\": \"Android\"," +
                        "\"os_ver\": \"7.1.1\"," +
                        "\"app_id\": \"YouTrip\"," +
                        "\"app_ver\": \"1.2.7\"," +
                        "\"language\": \"en-TH\"," +
                        "\"timezone\": \"Asia/Hong_Kong\"}").asJson();

        JSONObject responseJson = response.getBody().getObject();
        String oauth_key = responseJson.getString("oauth_client_key");
        String oauth_passpwd = responseJson.getString("oauth_client_secret");

        req_uri = (baseUri + "/device/otp");
        response = Unirest.post(req_uri)
                .header("Content-Type", "application/json")
                .header("x-request-id", "request-otp-" + util.getTimestamp())
                .basicAuth(oauth_key, oauth_passpwd)
                .body("{\"mcc\": \"" + mprefix + "\"," +
                        "\"phone_number\": \"" + phoneNumber + "\"," +
                        "\"language\": \"en-US\"}").asJson();

        responseJson = response.getBody().getObject();
        String otp_key = responseJson.getString("id");

        String otpCode = getOTP(mprefix, phoneNumber);
        req_uri = (baseUri + "/device/otp/" + otp_key);
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
        result.put("access_token", access_token);
        result.put("refresh_token", refresh_token);

        req_uri = (baseUri + "/me");
        response = Unirest.get(req_uri)
                .header("Content-Type", "application/json")
                .header("x-request-id", "get-user-" + util.getTimestamp())
                .header("Authorization", "Bearer " + access_token).asJson();
        responseJson = response.getBody().getObject();
        String user_id = responseJson.getString("id");
        result.put("user_id", user_id);
        
        return result;
    }


    public String yp_getToken() {
        String url_backdoorYP = ("http://backdoor.internal.sg.sit.you.co/youportal/token?scopes=https://www.googleapis.com/auth/userinfo.email,https://www.googleapis.com/auth/userinfo.profile,openid");
        System.out.println("API CALL: " +url_backdoorYP);

        String ypToken = Unirest.get(url_backdoorYP)
                .header("x-request-id", "token"+util.getTimestamp())
                .asJson()
                .getBody()
                .getObject()
                .getString("token");
        System.out.println("TEST DATA: YP token is " +ypToken);

        return ypToken;
    }

    public String yp_getKYC(String kycRefNo) {

        String token = yp_getToken();

        String url_getKYC = ("http://yp.internal.sg.sit.you.co/api/kyc?ReferenceIDs="+kycRefNo);
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
        String url_getKYCDetails  = ("http://yp.internal.sg.sit.you.co/api/kyc/"+kycID);
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
        if (cardType.equals(TestAccountData.CardType.PC.toString())){
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

        return result;

    }

    public void yp_fullReject(String kycRefNo) {
        this.setKycRejectReason("AUTO TEST: full KYC rejection testing");
        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);

        String url_fullRejectKYC = ("http://yp.internal.sg.sit.you.co/api/kyc/" + kycID + "/reject");
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
        String url_partialRejectKYC = ("http://yp.internal.sg.sit.you.co/api/kyc/"+kycID+"/partially_reject");
        System.out.println("API CALL: " +url_partialRejectKYC);

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
                        "    \"NameOnCard\": \""+kycDetails.get("nameOnCard")+"\",\n" +
                        "    \"Gender\": \""+kycDetails.get("gender")+"\",\n" +
                        "    \"IDNum\": \""+kycDetails.get("idNum")+"\",\n" +
                        "    \"DateOfBirth\": \""+kycDetails.get("dob")+"\",\n" +
                        "    \"Nationality\": \""+kycDetails.get("nationality")+"\"\n" +
                        "  }\n" +
                        "}")
                .asJson();

        assertEquals(200, partialrejectKYCjsonResponse.getStatus());
    }

    public void yp_approve(String kycRefNo) {
        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);
        Map<String, String> kycDetails = yp_getKYCdetails(kycRefNo);

        String url_approveKYC = ("http://yp.internal.sg.sit.you.co/api/kyc/"+kycID+"/accept?SkipArtemis=true");
        System.out.println("API CALL: " +url_approveKYC);

        HttpResponse<JsonNode> rejectKYCjsonResponse = Unirest.put(url_approveKYC)
                .header("Authorization", "Bearer "+token)
                .header("x-request-id", "acceptKYC"+util.getTimestamp())
                .header("x-yp-role", "Singapore Admin")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"AddressLine1\": \""+kycDetails.get("addLine1")+"\",\n" +
                        "    \"AddressLine2\": \""+kycDetails.get("addLine2")+"\"\n" +
                        "}")
                .asJson();

        assertEquals(200, rejectKYCjsonResponse.getStatus());
    }


}
