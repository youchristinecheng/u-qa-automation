package TestBased;

import TestBased.TestAccountData.*;
import kong.unirest.json.JSONObject;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
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

    public boolean takeScreenshot(String name, WebDriver driver) {
        String screenshotDirectory = System.getProperty("appium.screenshots.dir");
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", name)));
    }

}
