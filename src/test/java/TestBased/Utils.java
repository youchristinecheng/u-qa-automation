package TestBased;

import TestBased.TestAccountData.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import kong.unirest.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import static org.testng.Assert.fail;


public class Utils {

    Random rand = new Random();
    String decimalAmtFormat = "#,###.00";

    //provide timestamp in format for mobile number/ email uniqueness
    public String getTimestamp() {

    SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
    Date date = new Date(System.currentTimeMillis());
    return formatter.format(date);
    }

    public String getThaiID(){
        try {
            Pattern r = Pattern.compile("[A-Za-z]");

            int randomNum = 1000000 + rand.nextInt(9999999);
            String password = randomNum + getTimestamp();

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            System.out.println("myHash: " + myHash);

            String thaiIdNum = myHash.substring(0, 12).toUpperCase();
            System.out.println("Candidate: " + thaiIdNum);
            Matcher m = r.matcher(thaiIdNum);
            while (m.find()) {
                String found = m.group();
                int modDigit = ((int) ((char) found.charAt(0))) % 65;
                if (m.start() == 0) {
                    thaiIdNum = Integer.toString(modDigit) + thaiIdNum.substring(1);
                } else {
                    thaiIdNum = thaiIdNum.substring(0, m.start()) + Integer.toString(modDigit) + thaiIdNum.substring(m.end());
                }
            }

            int checksum = 0;
            for (int i =0; i < thaiIdNum.length();i++){
                int d = Character.getNumericValue(thaiIdNum.charAt(i));
                checksum += (d * (13 - i));
            }
            thaiIdNum += (11 - checksum % 11) % 10;

            return thaiIdNum;
        }catch(NoSuchAlgorithmException nsae){
            return null;
        }
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

    public boolean takeScreenshot(String name, WebDriver driver) {
        String screenshotDirectory = System.getProperty("appium.screenshots.dir");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", name)));
    }

    public double formatStringAmountValueToDouble(String value){
        DecimalFormat formatter = new DecimalFormat(this.decimalAmtFormat);
        Number amountValue = formatter.parse(value, new ParsePosition(0));

        return amountValue.doubleValue();
    }

    public String formatAmountValueToString(Double value){
        DecimalFormat formatter = new DecimalFormat(this.decimalAmtFormat);
        String amountValue = formatter.format(value);
        return  amountValue;
    }

    public boolean isActivedTestAccountValid(CardType cardType, KYCStatus kycStatus, CardStatus cardstatus, TestAccountData data){
        if(data != null) {
            if (data.KycStatus.equals(kycStatus)) {
                if (data.Card != null) {
                    return data.Card.Status.equals(cardstatus) && data.TestAccountCardType.equals(cardType);
                }
            }
        }
        return false;
    }

    private CriReqCardObj foundTargetCardObject(File fp, String yNumber){
        try {
            CriReqCardObj targetCardObj = null;
            JAXBContext jaxbContext = JAXBContext.newInstance(CRDREQ.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            CRDREQ crdreq = (CRDREQ) jaxbUnmarshaller.unmarshal(fp);

            for (CriReqCardObj cardObj : crdreq.CARD) {
                if (cardObj.RECID.equals(yNumber)) {
                    targetCardObj = cardObj;
                    break;
                }
            }
            return targetCardObj;
        }catch(Exception ex){
            return null;
        }
    }


    public File getAndValidCRIFile(String userId, boolean isDevEnv, Market market) throws Exception{
        String s3AccessKey = "AKIA5Q7BAXIXWLQHFK5L";
        String s3SecretKey = "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";
        CriReqCardObj targetCardObj = null;
        File fp = null;

        try {
            DBInfoRetriever dbInfo = new DBInfoRetriever(DBConnectionHandler.getInstance(isDevEnv, market));
            HashMap<String, String> criObj = dbInfo.getCriInfoByUserId(userId);
            String youId = criObj.get("youId");
            String criFileId = criObj.get("criFileId");
            String criFileName = criFileId.substring(criFileId.lastIndexOf("/") + 1);

            fp = new File("src/test/resources/" + criFileName);
            // Check if Y-number is exist in the last download CRI File
            if (fp.exists()){
//                targetCardObj = foundTargetCardObject(fp, youId);
                Files.deleteIfExists(fp.toPath());
            }

            if (targetCardObj == null) {
                AWSCredentials myCredentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
                AmazonS3 s3client = AmazonS3ClientBuilder.standard().withRegion("ap-southeast-1").withCredentials(new AWSStaticCredentialsProvider(myCredentials)).build();

                System.out.println("Downloading an object...");
                S3Object s3object = s3client.getObject(new GetObjectRequest("u-backend-store-sit", criFileId));
                System.out.println("Content-Type: " + s3object.getObjectMetadata().getContentType());
                InputStream s3input = s3object.getObjectContent();


                FileOutputStream fileOutputStream = new FileOutputStream(fp);
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = s3input.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                fileOutputStream.close();

                targetCardObj = foundTargetCardObject(fp, youId);
                if (targetCardObj == null) {
                    fail("No Card entry is found by Y-Number: [" + youId + "] with CRI file:" + criFileId);
                }
            }

            Assert.assertEquals(targetCardObj.ACCPROFILE, criObj.get("accProfile"));
            Assert.assertEquals(targetCardObj.ACTION, criObj.get("action"));

            Assert.assertEquals(targetCardObj.ADDRL1, criObj.get("ref_code_addr1"));
            Assert.assertEquals(targetCardObj.ADDRL2, criObj.get("ref_code_addr2"));
            Assert.assertEquals(targetCardObj.CARDNAME, criObj.get("cardName"));
            Assert.assertEquals(targetCardObj.CARRIERCODE, criObj.get("carrierCode"));
            Assert.assertEquals(targetCardObj.CITY, criObj.get("city"));
            Assert.assertEquals(targetCardObj.COUNTRY, criObj.get("country"));
            Assert.assertEquals(targetCardObj.CRDPRODUCT, criObj.get("cardProduct"));
            Assert.assertEquals(targetCardObj.CRDPROFILE, criObj.get("cardProfile"));
            Assert.assertEquals(targetCardObj.CRDUSRDATA, youId);
            Assert.assertEquals(targetCardObj.CURRCODE, criObj.get("curCode"));
            Assert.assertEquals(targetCardObj.DESIGNREF, criObj.get("designRef"));
            Assert.assertEquals(targetCardObj.DOB, criObj.get("dateOfBirth"));

            Assert.assertEquals(targetCardObj.FIRSTNAME, criObj.get("firstName"));
            Assert.assertEquals(targetCardObj.INSTCODE, criObj.get("instCode"));
            Assert.assertEquals(targetCardObj.LANG, criObj.get("language"));
            Assert.assertEquals(targetCardObj.LASTNAME, criObj.get("lastName"));

            Assert.assertEquals(targetCardObj.NEWACC, criObj.get("newAcc"));
            Assert.assertEquals(targetCardObj.NEWCUST, criObj.get("newCust"));
            Assert.assertNotNull(targetCardObj.PINBLK);
            Assert.assertEquals(targetCardObj.POSTCODE, criObj.get("postCode"));

            Assert.assertNotNull(targetCardObj.PRODUCEPIN);
            Assert.assertEquals(targetCardObj.PROGRAMID, criObj.get("programId"));
            Assert.assertEquals(targetCardObj.STATCODE, criObj.get("statusCode"));

            return fp;
        } catch (AmazonServiceException ase) {
            ase.printStackTrace();
            fail();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            fail();
        } catch(Exception e){
            e.printStackTrace();
            fail();
        }

    }
}
