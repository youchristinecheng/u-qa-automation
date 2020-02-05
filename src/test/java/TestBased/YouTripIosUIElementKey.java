package TestBased;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import TestBased.UIElementData.*;

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
        NamePageElementDict,
        NameOnCardElementDict,
        PersonalInformationElementDict,
        KYCNationalityElementDict,
        ResidentialAddressElementDict,
        KYCFinalStepElementDict,
        KYCKeepUpdatePopUpElementDict
    }

    private HashMap<String, UIElementData> DevAlertElementDict;
    private HashMap<String, UIElementData> CountryPageElementDict;
    private HashMap<String, UIElementData> CountrySelectionElementDict;
    private HashMap<String, UIElementData> GetStartedPageElementDict;
    private HashMap<String, UIElementData> MobileNumberPageElementDict;
    private HashMap<String, UIElementData> OTPPageElementDict;
    private HashMap<String, UIElementData> EmailPageElementDict;
    private HashMap<String, UIElementData> WelcomePageElementDict;
    private HashMap<String, UIElementData> IdentityVerificationElementDict;
    private HashMap<String, UIElementData> PRRegistrationElementDict;
    private HashMap<String, UIElementData> CameraAccessAlertElementDict;
    private HashMap<String, UIElementData> StepsPageElementDict;
    private HashMap<String, UIElementData> CameraPageElementDict;
    private HashMap<String, UIElementData> PhotoConfirmPageElementDict;
    private HashMap<String, UIElementData> TurnBackPopUpPageElementDict;
    private HashMap<String, UIElementData> NamePageElementDict;
    private HashMap<String, UIElementData> NameOnCardElementDict;
    private HashMap<String, UIElementData> PersonalInformationElementDict;
    private HashMap<String, UIElementData> ResidentialAddressElementDict;
    private HashMap<String, UIElementData> KYCNationalityElementDict;
    private HashMap<String, UIElementData> KYCFinalStepElementDict;
    private HashMap<String, UIElementData> KYCKeepUpdatePopUpElementDict;

    private HashMap<Integer, HashMap<String, UIElementData>> Container;

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
        this.NameOnCardElementDict = new HashMap<>();
        this.PersonalInformationElementDict = new HashMap<>();
        this.KYCNationalityElementDict = new HashMap<>();
        this.ResidentialAddressElementDict = new HashMap<>();
        this.KYCFinalStepElementDict = new HashMap<>();
        this.KYCKeepUpdatePopUpElementDict = new HashMap();


        this.Container = new HashMap<>();

        this.build();
    }

    private void build(){

        this.DevAlertElementDict.put("Force Logout", new UIElementData("//XCUIElementTypeButton[@name=\"Force logout\"]", FindMethod.XPATH));
        this.DevAlertElementDict.put("Download logs", new UIElementData("//XCUIElementTypeButton[@name=\"Download logs\"]", FindMethod.XPATH));
        this.DevAlertElementDict.put("Continue", new UIElementData("//XCUIElementTypeButton[@name=\"Continue\"]", FindMethod.XPATH));
        this.Container.put(PageKey.DevAlertElementDict.ordinal(), this.DevAlertElementDict);

        this.CountryPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("optionCountry", new UIElementData("optionCountry", FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("lblDesc", new UIElementData("lblDesc", FindMethod.ACCESSIBILITYID));
        this.CountryPageElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.CountryPageElementDict.ordinal(), this.CountryPageElementDict);

        this.CountrySelectionElementDict.put("btnClose", new UIElementData("btnClose", FindMethod.ACCESSIBILITYID));
        this.CountrySelectionElementDict.put("Singapore", new UIElementData("Singapore", FindMethod.ACCESSIBILITYID));
        this.CountrySelectionElementDict.put("Thailand", new UIElementData("Thailand", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.CountrySelectionElementDict.ordinal(), this.CountrySelectionElementDict);

        this.GetStartedPageElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.GetStartedPageElementDict.put("btnGetStarted", new UIElementData("btnGetStarted", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.GetStartedPageElementDict.ordinal(), this.GetStartedPageElementDict);

        this.MobileNumberPageElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("txtPrefix", new UIElementData("txtPrefixxx", FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("txtPhoneNumber", new UIElementData("txtPhonenumber", FindMethod.ACCESSIBILITYID));
        this.MobileNumberPageElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.MobileNumberPageElementDict.ordinal(), this.MobileNumberPageElementDict);

        this.OTPPageElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit1", new UIElementData("passcodeDigit1", FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit2", new UIElementData("passcodeDigit2", FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit3", new UIElementData("passcodeDigit3", FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit4", new UIElementData("passcodeDigit4", FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit5", new UIElementData("passcodeDigit5", FindMethod.ACCESSIBILITYID));
        this.OTPPageElementDict.put("OTPDigit6", new UIElementData("passcodeDigit6", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.OTPPageElementDict.ordinal(), this.OTPPageElementDict);

        this.EmailPageElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("UserEmail", new UIElementData("txtUserEmail", FindMethod.ACCESSIBILITYID));
        this.EmailPageElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.EmailPageElementDict.ordinal(), this.EmailPageElementDict);

        this.WelcomePageElementDict.put("lblWelcome", new UIElementData("lblWelcome", FindMethod.ACCESSIBILITYID));
        this.WelcomePageElementDict.put("btnPCRegister", new UIElementData("btnOrderPC", FindMethod.ACCESSIBILITYID));
        this.WelcomePageElementDict.put("btnNPCRegister", new UIElementData("btnActiveNow", FindMethod.ACCESSIBILITYID));
        // Menu Button and its sub-menu item ONLY retrieve by ID ["icMenu"] and corresponding sub-menu ID due to dynamic rendering
        this.Container.put(PageKey.WelcomePageElementDict.ordinal(), this.WelcomePageElementDict);

        this.IdentityVerificationElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.IdentityVerificationElementDict.put("singaporeanPRRegister", new UIElementData("//XCUIElementTypeStaticText[@name=\"For Singaporean / PR\"]", FindMethod.XPATH));
        this.IdentityVerificationElementDict.put("foreignerRegister", new UIElementData("//XCUIElementTypeStaticText[@name=\"For Foreginer\"]", FindMethod.XPATH));
        this.Container.put(PageKey.IdentityVerificationElementDict.ordinal(), this.IdentityVerificationElementDict);

        this.PRRegistrationElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("btnMyInfo", new UIElementData("btnMyInfo", FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("btnSubmit", new UIElementData("btnSubmit", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.PRRegistrationElementDict.ordinal(), this.PRRegistrationElementDict);

        this.StepsPageElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.StepsPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.StepsPageElementDict.put("btnStart", new UIElementData("btnStart", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.StepsPageElementDict.ordinal(), this.StepsPageElementDict);

        this.CameraAccessAlertElementDict.put("btnOK", new UIElementData("//XCUIElementTypeButton[@name=\"OK\"]", FindMethod.XPATH));
        this.Container.put(PageKey.CameraAccessAlertElementDict.ordinal(), this.CameraAccessAlertElementDict);

        this.CameraPageElementDict.put("lblDesc", new UIElementData("lblDesc", FindMethod.ACCESSIBILITYID));
        this.CameraPageElementDict.put("btnShutter", new UIElementData("btnShoot", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.CameraPageElementDict.ordinal(), this.CameraPageElementDict);

        this.PhotoConfirmPageElementDict.put("lblDesc", new UIElementData("lblDesc", FindMethod.ACCESSIBILITYID));
        this.PhotoConfirmPageElementDict.put("btnAllDataIsReadable", new UIElementData("btnOK", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.PhotoConfirmPageElementDict.ordinal(), this.PhotoConfirmPageElementDict);

        this.TurnBackPopUpPageElementDict.put("lblDesc", new UIElementData("lblTurnBackPopUpDesc", FindMethod.ACCESSIBILITYID));
        this.TurnBackPopUpPageElementDict.put("btnOK", new UIElementData("btnOK", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.TurnBackPopUpPageElementDict.ordinal(), this.TurnBackPopUpPageElementDict);

        this.NamePageElementDict.put("btnClose", new UIElementData("btnClose", FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("txtSurname", new UIElementData("txtSurname", FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("txtGivenName", new UIElementData("txtGivenName", FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("lblCheckAndConfirmTitle", new UIElementData("lblNameConfirmTitle", FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("btnCheckAndConfirmConfirm", new UIElementData("btnConfirm", FindMethod.ACCESSIBILITYID));
        this.NamePageElementDict.put("btnCheckAndConfirmChangeInput", new UIElementData("btnNo", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.NamePageElementDict.ordinal(), this.NamePageElementDict);

        this.NameOnCardElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.NameOnCardElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.NameOnCardElementDict.put("txtCardName", new UIElementData("txtCardName" , FindMethod.ACCESSIBILITYID));
        this.NameOnCardElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.NameOnCardElementDict.ordinal(), this.NameOnCardElementDict);

        this.PersonalInformationElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.PersonalInformationElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.PersonalInformationElementDict.put("txtIdCardNumber", new UIElementData("txtIdCardNumber" , FindMethod.ACCESSIBILITYID));
        this.PersonalInformationElementDict.put("txtDateOfBirth", new UIElementData("txtDateOfBirth" , FindMethod.ACCESSIBILITYID));
        this.PersonalInformationElementDict.put("btnNationality", new UIElementData("btnNationality" , FindMethod.ACCESSIBILITYID));
        this.PersonalInformationElementDict.put("btnMale", new UIElementData("btnMale" , FindMethod.ACCESSIBILITYID));
        this.PersonalInformationElementDict.put("btnFemale", new UIElementData("btnFemale" , FindMethod.ACCESSIBILITYID));
        this.PersonalInformationElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.PersonalInformationElementDict.ordinal(), this.PersonalInformationElementDict);

        this.KYCNationalityElementDict.put("btnClose", new UIElementData("btnClose", FindMethod.ACCESSIBILITYID));
        this.KYCNationalityElementDict.put("Singaporean", new UIElementData("//XCUIElementTypeStaticText[@name=\"    Singaporean\"]", FindMethod.XPATH));
        this.Container.put(PageKey.KYCNationalityElementDict.ordinal(), this.KYCNationalityElementDict);

        this.ResidentialAddressElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.ResidentialAddressElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.ResidentialAddressElementDict.put("txtAddressLine1", new UIElementData("txtAddressLine1" , FindMethod.ACCESSIBILITYID));
        this.ResidentialAddressElementDict.put("txtAddressLine2", new UIElementData("txtAddressLine2" , FindMethod.ACCESSIBILITYID));
        this.ResidentialAddressElementDict.put("txtAddressPostalCode", new UIElementData("txtAddressPostalCode" , FindMethod.ACCESSIBILITYID));
        this.ResidentialAddressElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.ResidentialAddressElementDict.ordinal(), this.ResidentialAddressElementDict);

        this.KYCFinalStepElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.KYCFinalStepElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.KYCFinalStepElementDict.put("btnAgree", new UIElementData("btnAgree" , FindMethod.ACCESSIBILITYID));
        this.KYCFinalStepElementDict.put("btnSubmit", new UIElementData("btnSubmit", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.KYCFinalStepElementDict.ordinal(), this.KYCFinalStepElementDict);

        this.KYCKeepUpdatePopUpElementDict.put("btnAccept", new UIElementData("//XCUIElementTypeStaticText[@name=\"Keep Me Updated\"]", FindMethod.XPATH));
        this.KYCKeepUpdatePopUpElementDict.put("btnReject", new UIElementData("//XCUIElementTypeStaticText[@name=\"Do not send me updates\"]", FindMethod.XPATH));
        this.Container.put(PageKey.KYCKeepUpdatePopUpElementDict.ordinal(), this.KYCKeepUpdatePopUpElementDict);
    }

    public WebElement getElement(PageKey page, String elementKey, IOSDriver driver){
        UIElementData result = null;
        result = (this.Container.get(page.ordinal())).get(elementKey);
        if (result ==  null){
            throw new NotFoundException("Element Not Declared");
        }else{
            return result.getIOSElement(driver);
        }
    }
}
