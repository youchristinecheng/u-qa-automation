package TestBased;
import TestBased.UIElementData.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;

public class YouTripAndroidUIElementKey {
    public enum PageKey{
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
        KYCKeepUpdatePopUpElementDict,
        LimitedHomePageElementDict
    }

    private HashMap<String, UIElementData> CountryPageElementDict;
    private HashMap<String, UIElementData> CountrySelectionElementDict;
    private HashMap<String, UIElementData> GetStartedPageElementDict;
    private HashMap<String, UIElementData> MobileNumberPageElementDict;
    private HashMap<String, UIElementData> OTPPageElementDict;
    private HashMap<String, UIElementData> EmailPageElementDict;
    private HashMap<String, UIElementData> WelcomePageElementDict;
    private HashMap<String, UIElementData> IdentityVerificationElementDict;
    private HashMap<String, UIElementData> PRRegistrationElementDict;
    private HashMap<String, UIElementData> StepsPageElementDict;
    private HashMap<String, UIElementData> CameraAccessAlertElementDict;
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
    private HashMap<String, UIElementData> LimitedHomePageElementDict;

    private HashMap<Integer, HashMap<String, UIElementData>> Container;

    public YouTripAndroidUIElementKey(){

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
        this.CameraAccessAlertElementDict = new HashMap<>();
        this.CameraPageElementDict = new HashMap<>();
        this.PhotoConfirmPageElementDict = new HashMap<>();
        this.TurnBackPopUpPageElementDict = new HashMap<>();
        this.NamePageElementDict = new HashMap<>();
        this.NameOnCardElementDict = new HashMap<>();
        this.PersonalInformationElementDict = new HashMap<>();
        this.KYCNationalityElementDict = new HashMap<>();
        this.ResidentialAddressElementDict = new HashMap<>();
        this.KYCFinalStepElementDict = new HashMap<>();
        this.KYCKeepUpdatePopUpElementDict = new HashMap<>();
        this.LimitedHomePageElementDict = new HashMap<>();

        this.Container = new HashMap<>();

        this.build();
    }

    private void build(){


        //Country Page
        this.CountryPageElementDict.put("lblTitle", new UIElementData("//android.widget.TextView[@text='Where do you live?']", UIElementData.FindMethod.XPATH));
        this.CountryPageElementDict.put("optionCountry", new UIElementData("co.you.youapp.dev:id/layoutSelectCountry", UIElementData.FindMethod.ID));
        this.CountryPageElementDict.put("countryDesc", new UIElementData("co.you.youapp.dev:id/textDesc", UIElementData.FindMethod.ID));
        this.CountryPageElementDict.put("btnConfirm", new UIElementData("co.you.youapp.dev:id/buttonConfirm", UIElementData.FindMethod.ID));
        this.Container.put(PageKey.CountryPageElementDict.ordinal(), this.CountryPageElementDict);

        //Country Selection Page
        this.CountrySelectionElementDict.put("Singapore", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']", UIElementData.FindMethod.XPATH));
        this.CountrySelectionElementDict.put("Thailand", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']", UIElementData.FindMethod.XPATH));
        this.Container.put(PageKey.CountrySelectionElementDict.ordinal(), this.CountrySelectionElementDict);

        //Get Started Page
        this.GetStartedPageElementDict.put("btnGetStarted", new UIElementData("co.you.youapp.dev:id/textButtonName", UIElementData.FindMethod.ID));
        this.Container.put(PageKey.GetStartedPageElementDict.ordinal(), this.GetStartedPageElementDict);

        //Enter Mobile Number Page
        this.MobileNumberPageElementDict.put("lblTitle", new UIElementData("//android.widget.TextView[@text='Enter Mobile Number']", FindMethod.XPATH));
        this.MobileNumberPageElementDict.put("txtPrefix", new UIElementData("co.you.youapp.dev:id/inputPrefix", FindMethod.ID));
        this.MobileNumberPageElementDict.put("txtPhoneNumber", new UIElementData("co.you.youapp.dev:id/inputEditText", FindMethod.ID));
        this.MobileNumberPageElementDict.put("btnNext", new UIElementData("co.you.youapp.dev:id/buttonGetSMS", FindMethod.ID));
        this.Container.put(PageKey.MobileNumberPageElementDict.ordinal(), this.MobileNumberPageElementDict);

        //Enter OTP Page
        this.OTPPageElementDict.put("lblTitle", new UIElementData("//android.widget.TextView[@text='Enter Code from SMS']", FindMethod.XPATH));
        this.OTPPageElementDict.put("OTPDigit1", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[1]/android.widget.EditText", FindMethod.XPATH));
        this.OTPPageElementDict.put("OTPDigit2", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[2]/android.widget.EditText", FindMethod.XPATH));
        this.OTPPageElementDict.put("OTPDigit3", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[3]/android.widget.EditText", FindMethod.XPATH));
        this.OTPPageElementDict.put("OTPDigit4", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[4]/android.widget.EditText", FindMethod.XPATH));
        this.OTPPageElementDict.put("OTPDigit5", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[5]/android.widget.EditText", FindMethod.XPATH));
        this.OTPPageElementDict.put("OTPDigit6", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[6]/android.widget.EditText", FindMethod.XPATH));
        this.Container.put(PageKey.OTPPageElementDict.ordinal(), this.OTPPageElementDict);

        //Enter Email Address Page
        this.EmailPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.EmailPageElementDict.put("txtUserEmail", new UIElementData("co.you.youapp.dev:id/inputEmail", FindMethod.ID));
        this.EmailPageElementDict.put("btnNext", new UIElementData("co.you.youapp.dev:id/buttonNext", FindMethod.ID));
        this.Container.put(PageKey.EmailPageElementDict.ordinal(), this.EmailPageElementDict);

        //Welcome Page
        this.WelcomePageElementDict.put("lblWelcome", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.WelcomePageElementDict.put("btnPCRegister", new UIElementData("co.you.youapp.dev:id/buttonOrder", FindMethod.ID));
        this.Container.put(PageKey.WelcomePageElementDict.ordinal(), this.WelcomePageElementDict);

        //Identity Verification Page
        this.IdentityVerificationElementDict.put("lblTitle", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']/android.widget.TextView", FindMethod.XPATH));
        this.IdentityVerificationElementDict.put("btnSG", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']", FindMethod.XPATH));
        this.Container.put(PageKey.IdentityVerificationElementDict.ordinal(), this.IdentityVerificationElementDict);

        //Singaporean & PR Page
        this.PRRegistrationElementDict.put("lblTitle", new UIElementData("//android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[@index='0']", FindMethod.XPATH));
        this.PRRegistrationElementDict.put("btnManualSubmit", new UIElementData("co.you.youapp.dev:id/textManual", FindMethod.ID));
        this.Container.put(PageKey.PRRegistrationElementDict.ordinal(), this.PRRegistrationElementDict);

        //Just a Few Steps Page
        this.StepsPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.StepsPageElementDict.put("btnStart", new UIElementData("co.you.youapp.dev:id/buttonStart", FindMethod.ID));
        this.Container.put(PageKey.StepsPageElementDict.ordinal(), this.StepsPageElementDict);

        //Camera Alert Access
        this.CameraAccessAlertElementDict.put("btnAccept", new UIElementData("com.android.packageinstaller:id/permission_allow_button", FindMethod.ID));
        this.Container.put(PageKey.CameraAccessAlertElementDict.ordinal(), this.CameraAccessAlertElementDict);

        //Camera Page
        this.CameraPageElementDict.put("btnShutter", new UIElementData("co.you.youapp.dev:id/buttonShutter", FindMethod.ID));
        this.Container.put(PageKey.CameraPageElementDict.ordinal(), this.CameraPageElementDict);

        //Confirm Photo Page
        this.PhotoConfirmPageElementDict.put("btnAllDataIsReadable", new UIElementData("co.you.youapp.dev:id/buttonConfirm", FindMethod.ID));
        this.Container.put(PageKey.PhotoConfirmPageElementDict.ordinal(), this.PhotoConfirmPageElementDict);

        //Back of ID PopUp
        this.TurnBackPopUpPageElementDict.put("lblDesc", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.TurnBackPopUpPageElementDict.put("btnOK", new UIElementData("co.you.youapp.dev:id/buttonOK", FindMethod.ID));
        this.Container.put(PageKey.TurnBackPopUpPageElementDict.ordinal(), this.TurnBackPopUpPageElementDict);

        //Given & Family Name Page
        this.NamePageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.NamePageElementDict.put("txtSurname", new UIElementData("co.you.youapp.dev:id/inputLastName", FindMethod.ID));
        this.NamePageElementDict.put("txtGivenName", new UIElementData("co.you.youapp.dev:id/inputGivenName", FindMethod.ID));
        this.NamePageElementDict.put("btnNext", new UIElementData("co.you.youapp.dev:id/buttonNext", FindMethod.ID));
        this.NamePageElementDict.put("lblCheckAndConfirmTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.NamePageElementDict.put("btnCheckAndConfirmConfirm", new UIElementData("co.you.youapp.dev:id/buttonPositive", FindMethod.ID));
        this.Container.put(PageKey.NamePageElementDict.ordinal(), this.NamePageElementDict);

        //Name on Card Page
        this.NameOnCardElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.NameOnCardElementDict.put("txtCardName", new UIElementData("co.you.youapp.dev:id/inputCardName" , FindMethod.ID));
        this.NameOnCardElementDict.put("btnNext", new UIElementData("co.you.youapp.dev:id/buttonNext", FindMethod.ID));
        this.Container.put(PageKey.NameOnCardElementDict.ordinal(), this.NameOnCardElementDict);

        //Personal Information Page
        this.PersonalInformationElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.PersonalInformationElementDict.put("txtIdCardNumber", new UIElementData("co.you.youapp.dev:id/inputIDNumber", FindMethod.ID));
        this.PersonalInformationElementDict.put("txtDateOfBirth", new UIElementData("co.you.youapp.dev:id/inputDOB", FindMethod.ID));
        this.PersonalInformationElementDict.put("btnNationality", new UIElementData("co.you.youapp.dev:id/inputNationality", FindMethod.ID));
        this.PersonalInformationElementDict.put("btnMale", new UIElementData("co.you.youapp.dev:id/radioMale", FindMethod.ID));
        this.PersonalInformationElementDict.put("btnNext", new UIElementData("co.you.youapp.dev:id/buttonNext", FindMethod.ID));
        this.Container.put(PageKey.PersonalInformationElementDict.ordinal(), this.PersonalInformationElementDict);

        //Nationality Drop Down
        this.KYCNationalityElementDict.put("txtInputSearch", new UIElementData("co.you.youapp.dev:id/inputSearch", FindMethod.ID));
        this.KYCNationalityElementDict.put("btnFirstSearchOption", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout", FindMethod.XPATH));
        this.Container.put(PageKey.KYCNationalityElementDict.ordinal(), this.KYCNationalityElementDict);

        //Residential Address Page
        this.ResidentialAddressElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.ResidentialAddressElementDict.put("txtAddressLine1", new UIElementData("co.you.youapp.dev:id/inputAddress1", FindMethod.ID));
        this.ResidentialAddressElementDict.put("txtAddressLine2", new UIElementData("co.you.youapp.dev:id/inputAddress2", FindMethod.ID));
        this.ResidentialAddressElementDict.put("txtAddressPostalCode", new UIElementData("co.you.youapp.dev:id/inputPostal", FindMethod.ID));
        this.ResidentialAddressElementDict.put("btnNext", new UIElementData("co.you.youapp.dev:id/buttonNext", FindMethod.ID));
        this.Container.put(PageKey.ResidentialAddressElementDict.ordinal(), this.ResidentialAddressElementDict);

        //Final Steps Page
        this.KYCFinalStepElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.KYCFinalStepElementDict.put("btnAgree", new UIElementData("co.you.youapp.dev:id/checkbox" , FindMethod.ID));
        this.KYCFinalStepElementDict.put("btnSubmit", new UIElementData("co.you.youapp.dev:id/buttonSubmit", FindMethod.ID));
        this.Container.put(PageKey.KYCFinalStepElementDict.ordinal(), this.KYCFinalStepElementDict);

        //Keep Update PopUp
        this.KYCKeepUpdatePopUpElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.KYCKeepUpdatePopUpElementDict.put("btnAccept", new UIElementData("co.you.youapp.dev:id/buttonPositive", FindMethod.ID));
        this.Container.put(PageKey.KYCKeepUpdatePopUpElementDict.ordinal(), this.KYCKeepUpdatePopUpElementDict);

        //Limited Home page
        this.LimitedHomePageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.LimitedHomePageElementDict.put("referenceNum", new UIElementData("co.you.youapp.dev:id/textRefNumber", FindMethod.ID));
        this.LimitedHomePageElementDict.put("btnRetry", new UIElementData("co.you.youapp.dev:id/button", FindMethod.ID));
        this.Container.put(PageKey.LimitedHomePageElementDict.ordinal(), this.LimitedHomePageElementDict);
    }

    public WebElement getElement(PageKey page, String elementKey, AndroidDriver driver){
        UIElementData result = null;
        result = (this.Container.get(page.ordinal())).get(elementKey);
        if (result ==  null){
            throw new NotFoundException("Element Not Declared");
        }else{
            return result.getAndroidElement(driver);
        }
    }
}
