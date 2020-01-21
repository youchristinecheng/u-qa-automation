package TestBased;

import java.util.ArrayList;
import java.util.HashMap;

public class YouTripIosUIElementKey {
    public enum PageKey{
        DevAlertElementDict,
        CountryPageElementDict,
        CountrySelectionElementDict,
        GetStartedPageElementDict,
        MobileNumberPageElementDict,
        OTPPageElementDict,
        EmailPageElementDict,
        WelcomePageElementDict
    }

    private HashMap<String, String> DevAlertElementDict;
    private HashMap<String, String> CountryPageElementDict;
    private HashMap<String, String> CountrySelectionElementDict;
    private HashMap<String, String> GetStartedPageElementDict;
    private HashMap<String, String> MobileNumberPageElementDict;
    private HashMap<String, String> OTPPageElementDict;
    private HashMap<String, String> EmailPageElementDict;
    private HashMap<String, String> WelcomePageElementDict;
    private ArrayList<HashMap<String, String>> Container;

    public YouTripIosUIElementKey(){

        this.DevAlertElementDict = new HashMap<>();
        this.CountryPageElementDict = new HashMap<>();
        this.CountrySelectionElementDict = new HashMap<>();
        this.GetStartedPageElementDict = new HashMap<>();
        this.MobileNumberPageElementDict = new HashMap<>();
        this.OTPPageElementDict = new HashMap<>();
        this.EmailPageElementDict = new HashMap<>();
        this.WelcomePageElementDict = new HashMap<>();

        this.Container = new ArrayList<>();

        this.build();
    }

    private void build(){
        this.DevAlertElementDict.put("Force Logout", "//XCUIElementTypeButton[@name=\"Force logout\"]");
        this.DevAlertElementDict.put("Download logs", "//XCUIElementTypeButton[@name=\"Download logs\"]");
        this.DevAlertElementDict.put("Continue", "//XCUIElementTypeButton[@name=\"Continue\"]");
        this.Container.add(this.DevAlertElementDict);

        this.CountryPageElementDict.put("HeaderText", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[1]");
        this.CountryPageElementDict.put("HeadingImg", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeImage[1]");
        this.CountryPageElementDict.put("CountryText", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[1]");
        this.CountryPageElementDict.put("CountryDescText", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[2]");
        this.CountryPageElementDict.put("ConfirmBtn", "//XCUIElementTypeButton[@name=\"Confirm\"]");
        this.Container.add(this.CountryPageElementDict);

        this.CountrySelectionElementDict.put("CloseButton", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]");
        this.CountrySelectionElementDict.put("Singapore", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[1]/XCUIElementTypeStaticText[1]");
        this.CountrySelectionElementDict.put("Thailand", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTable[1]/XCUIElementTypeCell[2]/XCUIElementTypeStaticText[1]");
        this.Container.add(this.CountrySelectionElementDict);

        this.GetStartedPageElementDict.put("BackBtn", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]");
        this.GetStartedPageElementDict.put("GetStarted", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[2]");
        this.Container.add(this.GetStartedPageElementDict);

        this.MobileNumberPageElementDict.put("BackBtn", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]");
        this.MobileNumberPageElementDict.put("HeaderText", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[1]");
        this.MobileNumberPageElementDict.put("MCC", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextField[1]");
        this.MobileNumberPageElementDict.put("PhoneNumber", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextField[2]");
        // Clear Text Button ONLY retrieve by ID ["icClose"] due to xPath is changed after phone number enetered with additional element
        // Next Button ONLY retrieve by ID ["Next"] due to xPath is changed after phone number enetered with additional element
        this.Container.add(this.MobileNumberPageElementDict);

        this.OTPPageElementDict.put("BackBtn", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]");
        this.OTPPageElementDict.put("HeaderText", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[1]");
        this.OTPPageElementDict.put("OTPDigit1", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeStaticText[1]");
        this.OTPPageElementDict.put("OTPDigit2", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeStaticText[2]");
        this.OTPPageElementDict.put("OTPDigit3", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeStaticText[3]");
        this.OTPPageElementDict.put("OTPDigit4", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeStaticText[4]");
        this.OTPPageElementDict.put("OTPDigit5", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeStaticText[5]");
        this.OTPPageElementDict.put("OTPDigit6", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[2]/XCUIElementTypeStaticText[6]");
        this.Container.add(this.OTPPageElementDict);

        this.EmailPageElementDict.put("BackBtn", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]");
        this.EmailPageElementDict.put("EmailLbl", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]//XCUIElementTypeStaticText[1]");
        this.EmailPageElementDict.put("Email", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]//XCUIElementTypeTextField[1]");
        // Clear Text Button ONLY retrieve by ID ["icClose"] due to xPath is changed after phone number enetered with additional element
        // Next Button ONLY retrieve by ID ["Next"] due to xPath is changed after phone number enetered with additional element
        this.Container.add(this.EmailPageElementDict);

        this.WelcomePageElementDict.put("HeaderText", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[4]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[1]");
        this.WelcomePageElementDict.put("PCRegisterBtn", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[4]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]");
        this.WelcomePageElementDict.put("NPCRegisterBtn", "//XCUIElementTypeApplication[@name=\"YouTrip\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[4]/XCUIElementTypeOther[1]/XCUIElementTypeScrollView[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[2]");
        // Menu Button and its sub-menu item ONLY retrieve by ID ["icMenu"] and corresponding sub-menu ID due to dynamic rendering
        this.Container.add(this.EmailPageElementDict);
    }

    public String getElementXPath(PageKey page, String elementKey){
        String result = "";
        result = (this.Container.get(page.ordinal())).get(elementKey);
        /*
        switch (page){
            case DevAlertElementDict:
                result = this.DevAlertElementDict.get(elementKey);
                break;
            case GetStartedPageElementDict:
                result =  this.GetStartedPageElementDict.get(elementKey);
                break;
            case CountryPageElementDict:
                result = this.CountryPageElementDict.get(elementKey);
                break;
            case CountrySelectionElementDict:
                result = this.CountrySelectionElementDict.get(elementKey);
                break;
            case MobileNumberPageElementDict:
                result = this.MobileNumberPageElementDict.get(elementKey);
                break;
            case OTPPageElementDict:
                result = this.OTPPageElementDict.get(elementKey);
                break;
            default:
                break;
        }*/
        return result ==  null ? "" : result;
    }
}
