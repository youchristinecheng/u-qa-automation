package TestBased;

import TestBased.Utils;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import static org.testng.Assert.assertEquals;

public class YouAPI {

    Utils util = new Utils();

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

    public void yp_fullReject(String kycRefNo) {

        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);

        String url_fullRejectKYC = ("http://yp.internal.sg.sit.you.co/api/kyc/"+kycID+"/reject");
        System.out.println("API CALL: " +url_fullRejectKYC);

        HttpResponse<JsonNode> rejectKYCjsonResponse = Unirest.put(url_fullRejectKYC)
                .header("Authorization", "Bearer "+yp_getToken())
                .header("x-request-id", "fullRejectKYC"+util.getTimestamp())
                .header("x-yp-role", "Singapore Admin")
                .header("Content-Type", "application/json")
                .body("{\"Reason\":\"AUTO TEST: full KYC rejection testing\"}")
                .asJson();

        assertEquals(200, rejectKYCjsonResponse.getStatus());
    }

    public void yp_partialReject(String kycRefNo) {
        //notes: for partial reject you must pass all personal, residental information even if it is not editted
        //approach is to call get kyc first, store the data and past it into partial reject api
        String token = yp_getToken();
        String kycID = yp_getKYC(kycRefNo);
        String ypKycID;
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
        firstName = responseJson.getString("FirstName");
        System.out.println("get kyc: First Name is " +firstName);
        lastName = responseJson.getString("LastName");
        System.out.println("get kyc: Last Name is " +lastName);
        nameOnCard = responseJson.getString("NameOnCard");
        System.out.println("get kyc: Name On Card  is " +nameOnCard);
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

        //call partial reject
        String url_partialRejectKYC = ("http://yp.internal.sg.sit.you.co/api/kyc/"+kycID+"/partially_reject");
        System.out.println("API CALL: " +url_partialRejectKYC);

        HttpResponse<JsonNode> partialrejectKYCjsonResponse = Unirest.put(url_partialRejectKYC)
                .header("Authorization", "Bearer "+yp_getToken())
                .header("x-request-id", "fullPartialKYC"+util.getTimestamp())
                .header("x-yp-role", "Singapore Admin")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "  \"Reason\": \"AUTO TEST: partial KYC rejection testing - changed address line 2 and first name\",\n" +
                        "  \"EditedKYC\": {\n" +
                        "    \"Address\": {\n" +
                        "      \"AddressLine1\": \""+addLine1+"\",\n" +
                        "      \"AddressLine2\": \"PARTIAL EDIT TEST\",\n" +
                        "      \"PostalCode\": \""+postalCode+"\"\n" +
                        "    },\n" +
                        "    \"FirstName\": \"Auto YP Edti\",\n" +
                        "    \"LastName\": \""+lastName+"\",\n" +
                        "    \"NameOnCard\": \""+nameOnCard+"\",\n" +
                        "    \"Gender\": \""+gender+"\",\n" +
                        "    \"IDNum\": \""+idNum+"\",\n" +
                        "    \"DateOfBirth\": \""+dob+"\",\n" +
                        "    \"Nationality\": \""+nationality+"\"\n" +
                        "  }\n" +
                        "}")
                .asJson();

        assertEquals(200, partialrejectKYCjsonResponse.getStatus());

    }

}
