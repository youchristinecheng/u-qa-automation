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
                .body("{\"Reason\":\"[AUTO TEST: full KYC rejection testing\"}")
                .asJson();

        assertEquals(200, rejectKYCjsonResponse.getStatus());
    }

}
