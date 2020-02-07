package TestBased;

import TestBased.TestAccountData.*;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.NotFoundException;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Utils {

    Random rand = new Random();

    //provide timestamp in format for mobile number/ email uniqueness
    public String getTimestamp() {

    SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
    Date date = new Date(System.currentTimeMillis());
    return formatter.format(date);
    }

    //create method for SG NRIC ID uniqueness - S1234567X
    public String getNRIC() {

        String nric = "S";

        int randomNum = 1000000 + rand.nextInt(9999999);
        nric += String.valueOf(randomNum);

        int randomChar = 65 + (new Random()).nextInt(90-65);
        char ch = (char)randomChar;
        nric += ch;

        System.out.println("TEST DATA: Random NRIC generated " +nric);
        return nric;
    }

    public void exportAccountTestData(TestAccountData data) throws Exception {
        try {
            Path srcFilePath = Paths.get(System.getProperty("user.dir"), "data", "ios_existing_data.json");
            File dataFile= new File(srcFilePath.toUri());
            FileReader reader = new FileReader(dataFile);
            JSONArray jObjArray;

            if (dataFile.exists()) {
                JSONTokener tokener = new JSONTokener(reader);
                jObjArray = new JSONArray(tokener);
            }else{

                if(!dataFile.createNewFile()){
                    throw new IOException("File disable to create");
                }
                jObjArray = new JSONArray();
            }
            reader.close();

            JSONObject newJobj = new JSONObject();
            newJobj.put("prefix", data.mprefix);
            newJobj.put("phonenumber", data.mnumber);
            newJobj.put("emailaddress", data.emailAddress);
            newJobj.put("kycstatus", data.kycStatus.toString());

            newJobj.put("surename", data.surname);
            newJobj.put("givenname", data.givenName);
            newJobj.put("nameoncard", data.nameOnCard);
            newJobj.put("dateofbirth", data.dateOfBirth);
            newJobj.put("nricnumber", data.nricNumber);
            newJobj.put("addressline1", data.addressLine1);
            newJobj.put("addressline2", data.addressLine2);
            newJobj.put("postalcode", data.postoalCode);


            jObjArray.put(newJobj);

            FileWriter writer = new FileWriter(dataFile);
            jObjArray.write(writer, 2, 0);
            writer.close();

        }catch(IOException ioe){
            throw new Exception(ioe.getMessage());
        }
    }

    public void updateData(TestAccountData data)throws Exception{
        try{
            Path srcFilePath = Paths.get(System.getProperty("user.dir"), "data", "ios_existing_data.json");
            File dataFile= new File(srcFilePath.toUri());
            FileReader reader = new FileReader(dataFile);


            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jObjArray = new JSONArray(tokener);

            JSONObject jObj = null;
            for (int i = 0; i < jObjArray.length(); i++) {
                jObj = jObjArray.getJSONObject(i);
                if (jObj.getString("prefix").equals(data.mprefix) &&
                        jObj.getString("phonenumber").equals(data.mnumber)) {
                    break;
                }
            }
            if (jObj == null)
                throw new NotFoundException("Test Account not Found");
            reader.close();

            JSONObject cardJobj;
            int replacementCnt = 0;
            try{
                cardJobj = (JSONObject) jObj.get("card");
                replacementCnt = cardJobj.getInt("numofreplace");
            }catch(JSONException joe){
                cardJobj = new JSONObject();
            }

            jObj.put("prefix", data.mprefix);
            jObj.put("phonenumber", data.mnumber);
            jObj.put("emailaddress", data.emailAddress);
            jObj.put("kycstatus", data.kycStatus.toString());

            jObj.put("surename", data.surname);
            jObj.put("givenname", data.givenName);
            jObj.put("nameoncard", data.nameOnCard);
            jObj.put("dateofbirth", data.dateOfBirth);
            jObj.put("nricnumber", data.nricNumber);
            jObj.put("addressline1", data.addressLine1);
            jObj.put("addressline2", data.addressLine2);
            jObj.put("postalcode", data.postoalCode);

            if (data.cardId != null) {
                cardJobj.put("youid", data.youId);
                cardJobj.put("cardid", data.cardId);
                cardJobj.put("cardstatus", data.cardStatus.toString());
                cardJobj.put("numofreplace", data.cardStatus.equals(CardStatus.REPLACE) ? replacementCnt + 1 : replacementCnt);
                jObj.put("card", cardJobj);
            }

            FileWriter writer = new FileWriter(dataFile);
            jObjArray.write(writer, 2, 0);
            writer.close();

        }catch(IOException ioe){
            throw new Exception(ioe.getMessage());
        }
    }


    public void insertCardInfo(String prefix, String phoneNumber, String youId, String cardId, CardStatus cardStatus) throws Exception{
        try{
            Path srcFilePath = Paths.get(System.getProperty("user.dir"), "data", "ios_existing_data.json");
            File dataFile= new File(srcFilePath.toUri());
            FileReader reader = new FileReader(dataFile);


            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jObjArray = new JSONArray(tokener);

            JSONObject jObj = null;
            for (int i = 0; i < jObjArray.length(); i++) {
                    jObj = jObjArray.getJSONObject(i);
                    if (jObj.getString("prefix").equals(prefix) &&
                    jObj.getString("phonenumber").equals(phoneNumber)) {
                        break;
                    }
            }
            if (jObj == null)
                throw new NotFoundException("Test Account not Found");
            reader.close();

            JSONObject cardJobj;
            int replacementCnt = 0;
            try{
                cardJobj = (JSONObject) jObj.get("card");
                replacementCnt = cardJobj.getInt("numofreplace");
            }catch(JSONException joe){
                cardJobj = new JSONObject();
            }


            cardJobj.put("youid", youId);
            cardJobj.put("cardid", cardId);
            cardJobj.put("cardstatus", cardStatus.toString());
            cardJobj.put("numofreplace", cardStatus.equals(CardStatus.REPLACE) ? replacementCnt + 1 : replacementCnt);

            jObj.put("card", cardJobj);

            FileWriter writer = new FileWriter(dataFile);
            jObjArray.write(writer, 2, 0);
            writer.close();

        }catch(IOException ioe){
            throw new Exception(ioe.getMessage());
        }
    }

    public TestAccountData searchFromPoolBy(HashMap<String, String> criteriaMap) throws Exception {
        try {
            Path srcFilePath = Paths.get(System.getProperty("user.dir"), "data", "ios_existing_data.json");
            File dataFile = new File(srcFilePath.toUri());
            FileReader reader = new FileReader(dataFile);


            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jObjArray = new JSONArray(tokener);

            JSONObject jObj = null;
            boolean isCorrect = false;
            JSONObject cardJobj = null;
            for (int i = 0; i < jObjArray.length(); i++) {
                jObj = jObjArray.getJSONObject(i);
                isCorrect = true;

                try{
                    cardJobj = (JSONObject) jObj.get("card");
                }catch(JSONException joe){
                    cardJobj = null;
                }

                for (HashMap.Entry<String, String> entry : criteriaMap.entrySet()) {
                    if(entry.getKey().toLowerCase().equals("youid") ||
                        entry.getKey().toLowerCase().equals("cardid") ||
                        entry.getKey().toLowerCase().equals("cardstatus")){
                        if(cardJobj == null){
                            isCorrect = false;
                            break;
                        } else if (!cardJobj.getString(entry.getKey().toLowerCase()).equals(entry.getValue())) {
                            isCorrect = false;
                            break;
                        }
                    } else if (!jObj.getString(entry.getKey().toLowerCase()).equals(entry.getValue())) {
                        isCorrect = false;
                        break;
                    }
                }
                if (isCorrect) {
                    break;
                }
            }
            if (!isCorrect)
                throw new NotFoundException("Test Account not Found");
            reader.close();

            TestAccountData result =  new TestAccountData();
            result.mprefix = jObj.getString("prefix");
            result.mnumber = jObj.getString("phonenumber");
            result.emailAddress = jObj.getString("emailaddress");
            result.kycStatus = TestAccountData.KYCStatus.valueOf(jObj.getString("kycstatus"));

            result.surname = jObj.getString("surename");
            result.givenName = jObj.getString("givenname");
            result.nameOnCard = jObj.getString("nameoncard");
            result.dateOfBirth = jObj.getString("dateofbirth");
            result.nricNumber = jObj.getString("nricnumber");
            result.addressLine1 = jObj.getString("addressline1");
            result.addressLine2 = jObj.getString("addressline2");
            result.postoalCode = jObj.getString("postalcode");


            if(cardJobj != null){
                result.youId = cardJobj.getString("youid");
                result.cardId = cardJobj.getString("cardid");
                result.cardStatus = TestAccountData.CardStatus.valueOf(cardJobj.getString("cardstatus"));
            }

            return result;
        }catch(IOException ioe){
            throw new Exception(ioe.getMessage());
        }
    }

}
