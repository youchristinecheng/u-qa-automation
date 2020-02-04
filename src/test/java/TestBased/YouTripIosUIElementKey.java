package TestBased;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import sun.jvm.hotspot.debugger.Page;

import java.rmi.server.ExportException;
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
        WelcomePageElementDict,
        IdentityVerificationElementDict,
        PRRegistrationElementDict,
        CameraAccessAlertElementDict,
        StepsPageElementDict,
        CameraPageElementDict,
        PhotoConfirmPageElementDict,
        TurnBackPopUpPageElementDict,
        NamePageElementDict
    }

    private HashMap<String, UIElementKey> DevAlertElementDict;
    private HashMap<String, UIElementKey> CountryPageElementDict;
    private HashMap<String, UIElementKey> CountrySelectionElementDict;
    private HashMap<String, UIElementKey> GetStartedPageElementDict;
    private HashMap<String, UIElementKey> MobileNumberPageElementDict;
    private HashMap<String, UIElementKey> OTPPageElementDict;
    private HashMap<String, UIElementKey> EmailPageElementDict;
    private HashMap<String, UIElementKey> WelcomePageElementDict;
    private HashMap<String, UIElementKey> IdentityVerificationElementDict;
    private HashMap<String, UIElementKey> PRRegistrationElementDict;
    private HashMap<String, UIElementKey> CameraAccessAlertElementDict;
    private HashMap<String, UIElementKey> StepsPageElementDict;
    private HashMap<String, UIElementKey> CameraPageElementDict;
    private HashMap<String, UIElementKey> PhotoConfirmPageElementDict;
    private HashMap<String, UIElementKey> TurnBackPopUpPageElementDict;
    private HashMap<String, UIElementKey> NamePageElementDict;

    private HashMap<Integer, HashMap<String, UIElementKey>> Container;

    public YouTripIosUIElementKey(){

        this.DevAlertElementDict = new HashMap<>();
        this.CameraAccessAlertElementDict = new HashMap<>();
        this.CountryPageElementDict = new HashMap<>();
        this.CountrySelectionElementDict = new HashMap<>();
        this.GetStartedPageElementDict = new HashMap<>();
        this.MobileNumberPageElementDict = new HashMap<>();
        this.OTPPageElementDict = new HashMap<>();
        this.EmailPageElementDict = new HashMap<>();
        this.WelcomePageElementDict = new HashMap<>();
        this.IdentityVerificationElementDict = new HashMap<>();
        this.PRRegistrationElementDict = new HashMap<>();
        this.StepsPageElementDict = new HashMap<>();
        this.CameraPageElementDict = new HashMap<>();
        this.PhotoConfirmPageElementDict = new HashMap<>();
        this.TurnBackPopUpPageElementDict = new HashMap<>();
        this.NamePageElementDict = new HashMap<>();

        this.Container = new HashMap<>();

        this.build();
    }

    private void build(){

        this.DevAlertElementDict.put("Force Logout", new UIElementKey("//XCUIElementTypeButton[@name=\"Force logout\"]", UIElementKey.FindMethod.XPATH));
        this.DevAlertElementDict.put("Download logs", new UIElementKey("//XCUIElementTypeButton[@name=\"Download logs\"]", UIElementKey.FindMethod.XPATH));
        this.DevAlertElementDict.put("Continue", new UIElementKey("//XCUIElementTypeButton[@name=\"Continue\"]", UIElementKey.FindMethod.XPATH));
        this.Container.put(PageKey.DevAlertElementDict.ordinal(), this.DevAlertElementDict);

        this.CountryPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("optionCountry", new UIElementKey("optionCountry", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("lblDesc", new UIElementKey("lblDesc", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("btnNext", new UIElementKey("btnNext", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.CountryPageElementDict.ordinal(), this.CountryPageElementDict);

        this.CountrySelectionElementDict.put("btnClose", new UIElementKey("btnClose", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountrySelectionElementDict.put("Singapore", new UIElementKey("Singapore", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CountrySelectionElementDict.put("Thailand", new UIElementKey("Thailand", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.CountrySelectionElementDict.ordinal(), this.CountrySelectionElementDict);

        this.GetStartedPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.GetStartedPageElementDict.put("btnGetStarted", new UIElementKey("btnGetStarted", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.GetStartedPageElementDict.ordinal(), this.GetStartedPageElementDict);

        this.MobileNumberPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("txtPrefix", new UIElementKey("txtPrefixxx", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("txtPhoneNumber", new UIElementKey("txtPhonenumber", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("btnNext", new UIElementKey("btnNext", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.MobileNumberPageElementDict.ordinal(), this.MobileNumberPageElementDict);

        this.OTPPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit1", new UIElementKey("passcodeDigit1", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit2", new UIElementKey("passcodeDigit2", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit3", new UIElementKey("passcodeDigit3", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit4", new UIElementKey("passcodeDigit4", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit5", new UIElementKey("passcodeDigit5", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit6", new UIElementKey("passcodeDigit6", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.OTPPageElementDict.ordinal(), this.OTPPageElementDict);

        this.EmailPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("UserEmail", new UIElementKey("txtUserEmail", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("btnNext", new UIElementKey("btnNext", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.EmailPageElementDict.ordinal(), this.EmailPageElementDict);

        this.WelcomePageElementDict.put("lblWelcome", new UIElementKey("lblWelcome", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.WelcomePageElementDict.put("btnPCRegister", new UIElementKey("btnOrderPC", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.WelcomePageElementDict.put("btnNPCRegister", new UIElementKey("btnActiveNow", UIElementKey.FindMethod.ACCESSIBILITYID));
        // Menu Button and its sub-menu item ONLY retrieve by ID ["icMenu"] and corresponding sub-menu ID due to dynamic rendering
        this.Container.put(PageKey.WelcomePageElementDict.ordinal(), this.WelcomePageElementDict);

        this.IdentityVerificationElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.IdentityVerificationElementDict.put("singaporeanPRRegister", new UIElementKey("//XCUIElementTypeStaticText[@name=\"For Singaporean / PR\"]", UIElementKey.FindMethod.XPATH));
        this.IdentityVerificationElementDict.put("foreignerRegister", new UIElementKey("//XCUIElementTypeStaticText[@name=\"For Foreginer\"]", UIElementKey.FindMethod.XPATH));
        this.Container.put(PageKey.IdentityVerificationElementDict.ordinal(), this.IdentityVerificationElementDict);

        this.PRRegistrationElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("btnMyInfo", new UIElementKey("btnMyInfo", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("btnSubmit", new UIElementKey("btnSubmit", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.PRRegistrationElementDict.ordinal(), this.PRRegistrationElementDict);

        this.StepsPageElementDict.put("btnBack", new UIElementKey("btnBack", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.StepsPageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.StepsPageElementDict.put("btnStart", new UIElementKey("btnStart", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.StepsPageElementDict.ordinal(), this.StepsPageElementDict);

        this.CameraAccessAlertElementDict.put("btnOK", new UIElementKey("//XCUIElementTypeButton[@name=\"OK\"]", UIElementKey.FindMethod.XPATH));
        this.Container.put(PageKey.CameraAccessAlertElementDict.ordinal(), this.CameraAccessAlertElementDict);

        this.CameraPageElementDict.put("lblDesc", new UIElementKey("lblDesc", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.CameraPageElementDict.put("btnShutter", new UIElementKey("btnShoot", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.CameraPageElementDict.ordinal(), this.CameraPageElementDict);

        this.PhotoConfirmPageElementDict.put("lblDesc", new UIElementKey("lblDesc", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.PhotoConfirmPageElementDict.put("btnAllDataIsReadable", new UIElementKey("btnOK", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.PhotoConfirmPageElementDict.ordinal(), this.PhotoConfirmPageElementDict);

        this.TurnBackPopUpPageElementDict.put("lblDesc", new UIElementKey("lblTurnBackPopUpDesc", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.TurnBackPopUpPageElementDict.put("btnOK", new UIElementKey("btnOK", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.TurnBackPopUpPageElementDict.ordinal(), this.TurnBackPopUpPageElementDict);

        this.NamePageElementDict.put("btnClose", new UIElementKey("btnClose", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("lblTitle", new UIElementKey("lblTitle", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("txtSurname", new UIElementKey("txtSurname", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("txtGivenName", new UIElementKey("txtGivenName", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("btnNext", new UIElementKey("btnNext", UIElementKey.FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.NamePageElementDict.ordinal(), this.NamePageElementDict);
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
