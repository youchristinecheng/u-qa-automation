package TestBased;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.HashMap;

public class YouTripIosUIElementKey {
    public enum PageKey{
        DevAlertElementDict,
        CameraAccessAlertElementDict,
        CountryPageElementDict,
        CountrySelectionElementDict,
        GetStartedPageElementDict,
        MobileNumberPageElementDict,
        OTPPageElementDict,
        EmailPageElementDict,
        WelcomePageElementDict
    }

    private HashMap<String, UIElementKey> DevAlertElementDict;
    private HashMap<String, UIElementKey> CameraAccessAlertElementDict;
    private HashMap<String, UIElementKey> CountryPageElementDict;
    private HashMap<String, UIElementKey> CountrySelectionElementDict;
    private HashMap<String, UIElementKey> GetStartedPageElementDict;
    private HashMap<String, UIElementKey> MobileNumberPageElementDict;
    private HashMap<String, UIElementKey> OTPPageElementDict;
    private HashMap<String, UIElementKey> EmailPageElementDict;
    private HashMap<String, UIElementKey> WelcomePageElementDict;
    private ArrayList<HashMap<String, UIElementKey>> Container;

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

        this.DevAlertElementDict.put("Force Logout", new UIElementKey("//XCUIElementTypeButton[@name=\"Force logout\"]", UIElementKey.FindMethod.XPATH));
        this.DevAlertElementDict.put("Download logs", new UIElementKey("//XCUIElementTypeButton[@name=\"Download logs\"]", UIElementKey.FindMethod.XPATH));
        this.DevAlertElementDict.put("Continue", new UIElementKey("//XCUIElementTypeButton[@name=\"Continue\"]", UIElementKey.FindMethod.XPATH));
        this.Container.add(this.DevAlertElementDict);

        this.CountryPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("optionCountry", new UIElementKey("optionCountry", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("lblDesc", new UIElementKey("lblDesc", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("btnNext", new UIElementKey("btnNext", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.add(this.CountryPageElementDict);

        this.CountrySelectionElementDict.put("btnClose", new UIElementKey("btnClose", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountrySelectionElementDict.put("Singapore", new UIElementKey("Singapore", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountrySelectionElementDict.put("Thailand", new UIElementKey("Thailand", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.add(this.CountrySelectionElementDict);

        this.GetStartedPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.GetStartedPageElementDict.put("btnGetStarted", new UIElementKey("btnGetStarted", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.add(this.GetStartedPageElementDict);

        this.MobileNumberPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("txtPrefix", new UIElementKey("txtPrefixxx", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("txtPhoneNumber", new UIElementKey("txtPhonenumber", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("btnNext", new UIElementKey("btnNext", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.add(this.MobileNumberPageElementDict);

        this.OTPPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit1", new UIElementKey("passcodeDigit1", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit2", new UIElementKey("passcodeDigit2", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit3", new UIElementKey("passcodeDigit3", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit4", new UIElementKey("passcodeDigit4", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit5", new UIElementKey("passcodeDigit5", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit6", new UIElementKey("passcodeDigit6", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.add(this.OTPPageElementDict);

        this.EmailPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("UserEmail", new UIElementKey("txtUserEmail", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("btnNext", new UIElementKey("btnNext", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.add(this.EmailPageElementDict);

        this.WelcomePageElementDict.put("lblWelcome", new UIElementKey("lblWelcome", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.WelcomePageElementDict.put("btnPCRegister", new UIElementKey("txtUserEmail", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.WelcomePageElementDict.put("btnNPCRegister", new UIElementKey("txtUserEmail", UIElementKey.FindMethod.ACCESSIBILITYID));
        // Menu Button and its sub-menu item ONLY retrieve by ID ["icMenu"] and corresponding sub-menu ID due to dynamic rendering
        this.Container.add(this.WelcomePageElementDict);

        this.CameraAccessAlertElementDict.put("btnOK", new UIElementKey("//XCUIElementTypeButton[@name=\"OK\"]", UIElementKey.FindMethod.XPATH));
        this.Container.add(this.WelcomePageElementDict);
    }

    public WebElement getElement(PageKey page, String elementKey, IOSDriver driver){
        UIElementKey result = null;
        result = (this.Container.get(page.ordinal())).get(elementKey);
        if (result ==  null){
            throw new NotFoundException("Element Not Declared");
        }else{
            return result.getIOSElement(driver);
        }
    }
}
