package TestBased;

import TestBased.TestAccountData.*;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import net.bytebuddy.dynamic.loading.ClassInjector;
import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.NotFoundException;
import org.testng.annotations.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Utils {

    Random rand = new Random();

    //provide timestamp in format for mobile number/ email uniqueness
    public String getTimestamp() {

    SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
    Date date = new Date(System.currentTimeMillis());
    return formatter.format(date);
    }

    public String getNRIC() {
        try {
            Pattern r = Pattern.compile("[A-Za-z]");

            int randomNum = 1000000 + rand.nextInt(9999999);
            String password = randomNum + getTimestamp();

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            System.out.println("myHash: " + myHash);

            String nric = myHash.substring(0, 7).toUpperCase();
            System.out.println("Candidate: " + nric);
            Matcher m = r.matcher(nric);
            while (m.find()) {
                String found = m.group();
                System.out.println(found + " " + m.start() + " " + m.end());
                int modDigit = ((int) ((char) found.charAt(0))) % 65;
                if (m.start() == 0) {
                    nric = Integer.toString(modDigit) + nric.substring(1);
                } else if (m.start() == 6) {
                    nric = nric.substring(0, 6) + Integer.toString(modDigit);
                } else {
                    nric = nric.substring(0, m.start()) + Integer.toString(modDigit) + nric.substring(m.end());
                }
                System.out.println("WIP: " + nric);
            }
            m = r.matcher(myHash.substring(7, 9));
            if (!m.find()) {
                int i = ((Integer.parseInt(myHash.substring(7, 9))) % 26) + 65;
                char c = (char) i;
                nric = "S" + nric + String.valueOf(c);
            } else {
                String found = m.group();
                nric = "S" + nric + found;
            }
            return nric;
        }catch(NoSuchAlgorithmException nsae){
            return null;
        }
    }

    //create method for SG NRIC ID uniqueness - S1234567X
/*    public String getNRIC() {

        String nric = "S";

        int randomNum = 1000000 + rand.nextInt(9999999);
        nric += String.valueOf(randomNum);

        int randomChar = 65 + (new Random()).nextInt(90-65);
        char ch = (char)randomChar;
        nric += ch;

        System.out.println("TEST DATA: Random NRIC generated " +nric);
        return nric;
    }*/

    public void exportAccountTestData(TestAccountData data) throws Exception {
        try {
            boolean isNewCreate = false;
            Path srcFilePath = Paths.get(System.getProperty("user.dir"), "data");
            if(!Files.exists(srcFilePath)){
                Files.createDirectories(srcFilePath);
            }
            srcFilePath = Paths.get(System.getProperty("user.dir"), "data","ios_existing_data.json");
            if(!Files.exists(srcFilePath)){
                Files.createFile(srcFilePath);
                isNewCreate = true;
            }
            File dataFile= new File(srcFilePath.toUri());
            FileReader reader = new FileReader(dataFile);
            JSONArray jObjArray;

            if (!isNewCreate) {
                JSONTokener tokener = new JSONTokener(reader);
                jObjArray = new JSONArray(tokener);
            }else{
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
            ioe.printStackTrace();
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
