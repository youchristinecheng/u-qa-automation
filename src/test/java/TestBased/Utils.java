package TestBased;

import java.text.SimpleDateFormat;
import java.util.Date;
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

}
