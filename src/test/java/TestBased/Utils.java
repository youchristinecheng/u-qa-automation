package TestBased;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    //provide timestamp in format for mobile number/ email uniqueness
    public String getTimestamp() {

    SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
    Date date = new Date(System.currentTimeMillis());
    return formatter.format(date);
    }

    //create method for SG NRIC ID uniqueness

}
