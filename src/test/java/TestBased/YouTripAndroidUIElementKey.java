package TestBased;
import TestBased.UIElementData.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        YnumberPageElementDict,
        IdentityVerificationElementDict,
        PRRegistrationElementDict,
        ForeignerRegistrationElementDict,
        CameraAccessAlertElementDict,
        StepsPageElementDict,
        CameraPageElementDict,
        PhotoConfirmPageElementDict,
        TurnBackPopUpPageElementDict,
        ProofOfAddressPageElementDict,
        NamePageElementDict,
        NameOnCardElementDict,
        PersonalInformationElementDict,
        KYCNationalityElementDict,
        ResidentialAddressElementDict,
        KYCFinalStepElementDict,
        KYCKeepUpdatePopUpElementDict,
        LimitedHomePageElementDict,
        LeftSideMenuElementDict,
        SettingPageElementDict,
        ConfirmEmailPageElementDict,
        CheckSentEmailPageElementDict,
        CreateConfirmPinPageElementDict,
        FingerPrintPageElementDict,
        UnlockAppPageElementDict,
        HomePageElementDict
    }

    private HashMap<String, UIElementData> CountryPageElementDict;
    private HashMap<String, UIElementData> CountrySelectionElementDict;
    private HashMap<String, UIElementData> GetStartedPageElementDict;
    private HashMap<String, UIElementData> MobileNumberPageElementDict;
    private HashMap<String, UIElementData> OTPPageElementDict;
    private HashMap<String, UIElementData> EmailPageElementDict;
    private HashMap<String, UIElementData> WelcomePageElementDict;
    private HashMap<String, UIElementData> YnumberPageElementDict;
    private HashMap<String, UIElementData> IdentityVerificationElementDict;
    private HashMap<String, UIElementData> PRRegistrationElementDict;
    private HashMap<String, UIElementData> ForeignerRegistrationElementDict;
    private HashMap<String, UIElementData> StepsPageElementDict;
    private HashMap<String, UIElementData> CameraAccessAlertElementDict;
    private HashMap<String, UIElementData> CameraPageElementDict;
    private HashMap<String, UIElementData> PhotoConfirmPageElementDict;
    private HashMap<String, UIElementData> TurnBackPopUpPageElementDict;
    private HashMap<String, UIElementData> ProofOfAddressPageElementDict;
    private HashMap<String, UIElementData> NamePageElementDict;
    private HashMap<String, UIElementData> NameOnCardElementDict;
    private HashMap<String, UIElementData> PersonalInformationElementDict;
    private HashMap<String, UIElementData> ResidentialAddressElementDict;
    private HashMap<String, UIElementData> KYCNationalityElementDict;
    private HashMap<String, UIElementData> KYCFinalStepElementDict;
    private HashMap<String, UIElementData> KYCKeepUpdatePopUpElementDict;
    private HashMap<String, UIElementData> LimitedHomePageElementDict;
    private HashMap<String, UIElementData> LeftSideMenuElementDict;
    private HashMap<String, UIElementData> SettingPageElementDict;
    private HashMap<String, UIElementData> ConfirmEmailPageElementDict;
    private HashMap<String, UIElementData> CheckSentEmailPageElementDict;
    private HashMap<String, UIElementData> CreateConfirmPinPageElementDict;
    private HashMap<String, UIElementData> FingerPrintPageElementDict;
    private HashMap<String, UIElementData> UnlockAppPageElementDict;
    private HashMap<String, UIElementData> HomePageElementDict;

    private HashMap<Integer, HashMap<String, UIElementData>> Container;

    public YouTripAndroidUIElementKey(){

        this.CountryPageElementDict = new HashMap<>();
        this.CountrySelectionElementDict = new HashMap<>();
        this.GetStartedPageElementDict = new HashMap<>();
        this.MobileNumberPageElementDict = new HashMap<>();
        this.OTPPageElementDict = new HashMap<>();
        this.EmailPageElementDict = new HashMap<>();
        this.WelcomePageElementDict = new HashMap<>();
        this.YnumberPageElementDict = new HashMap<>();
        this.IdentityVerificationElementDict = new HashMap<>();
        this.PRRegistrationElementDict = new HashMap<>();
        this.ForeignerRegistrationElementDict = new HashMap<>();
        this.StepsPageElementDict = new HashMap<>();
        this.CameraAccessAlertElementDict = new HashMap<>();
        this.CameraPageElementDict = new HashMap<>();
        this.PhotoConfirmPageElementDict = new HashMap<>();
        this.TurnBackPopUpPageElementDict = new HashMap<>();
        this.ProofOfAddressPageElementDict = new HashMap<>();
        this.NamePageElementDict = new HashMap<>();
        this.NameOnCardElementDict = new HashMap<>();
        this.PersonalInformationElementDict = new HashMap<>();
        this.KYCNationalityElementDict = new HashMap<>();
        this.ResidentialAddressElementDict = new HashMap<>();
        this.KYCFinalStepElementDict = new HashMap<>();
        this.KYCKeepUpdatePopUpElementDict = new HashMap<>();
        this.LimitedHomePageElementDict = new HashMap<>();
        this.LeftSideMenuElementDict = new HashMap<>();
        this.SettingPageElementDict = new HashMap<>();
        this.ConfirmEmailPageElementDict = new HashMap<>();
        this.CheckSentEmailPageElementDict = new HashMap<>();
        this.CreateConfirmPinPageElementDict = new HashMap<>();
        this.FingerPrintPageElementDict = new HashMap<>();
        this.UnlockAppPageElementDict = new HashMap<>();
        this.HomePageElementDict = new HashMap<>();

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
        this.WelcomePageElementDict.put("btnNPCRegister", new UIElementData("co.you.youapp.dev:id/buttonActivate", FindMethod.ID));
        this.Container.put(PageKey.WelcomePageElementDict.ordinal(), this.WelcomePageElementDict);

        //Enter Y-number Page
        this.YnumberPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.YnumberPageElementDict.put("yNumDigit1", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[1]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit2", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[2]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit3", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[3]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit4", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[4]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit5", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[5]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit6", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[6]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit7", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[7]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit8", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[8]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit9", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[9]/android.widget.EditText", FindMethod.XPATH));
        this.YnumberPageElementDict.put("yNumDigit10", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[10]/android.widget.EditText", FindMethod.XPATH));
        this.Container.put(PageKey.YnumberPageElementDict.ordinal(), this.YnumberPageElementDict);

        //Identity Verification Page
        this.IdentityVerificationElementDict.put("lblTitle", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']/android.widget.TextView", FindMethod.XPATH));
        this.IdentityVerificationElementDict.put("btnSG", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']", FindMethod.XPATH));
        this.IdentityVerificationElementDict.put("btnForeigner", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='2']", FindMethod.XPATH));
        this.Container.put(PageKey.IdentityVerificationElementDict.ordinal(), this.IdentityVerificationElementDict);

        //Singaporean & PR Page
        this.PRRegistrationElementDict.put("lblTitle", new UIElementData("//android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[@index='0']", FindMethod.XPATH));
        this.PRRegistrationElementDict.put("btnManualSubmit", new UIElementData("co.you.youapp.dev:id/textManual", FindMethod.ID));
        this.Container.put(PageKey.PRRegistrationElementDict.ordinal(), this.PRRegistrationElementDict);

        //Foreigner - Submit Document Page
        this.ForeignerRegistrationElementDict.put("lblTitle", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']/android.widget.TextView", FindMethod.XPATH));
        this.ForeignerRegistrationElementDict.put("btnEmploymentPass", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']", FindMethod.XPATH));
        this.ForeignerRegistrationElementDict.put("btnSPass", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='2']", FindMethod.XPATH));
        this.ForeignerRegistrationElementDict.put("btnWorkPermit", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='3']", FindMethod.XPATH));
        this.ForeignerRegistrationElementDict.put("btnStudentPass", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='4']", FindMethod.XPATH));
        this.ForeignerRegistrationElementDict.put("btnOthers", new UIElementData("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='5']", FindMethod.XPATH));
        this.Container.put(PageKey.ForeignerRegistrationElementDict.ordinal(), this.ForeignerRegistrationElementDict);

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

        //Confirm Proof of Address
        this.ProofOfAddressPageElementDict.put("lblTitle", new UIElementData("//android.widget.LinearLayout/android.widget.LinearLayout/android.widget.TextView", FindMethod.XPATH));
        this.ProofOfAddressPageElementDict.put("otherDocsLink", new UIElementData("//android.widget.LinearLayout[contains(@resource-id,'layoutPoints')]/android.widget.LinearLayout[3]/android.widget.TextView[contains(@resource-id,'textTitle')]", FindMethod.XPATH));
        this.ProofOfAddressPageElementDict.put("btnOK", new UIElementData("co.you.youapp.dev:id/buttonConfirm", FindMethod.ID));
        this.Container.put(PageKey.ProofOfAddressPageElementDict.ordinal(), this.ProofOfAddressPageElementDict);

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
        this.LimitedHomePageElementDict.put("btnActivate", new UIElementData("co.you.youapp.dev:id/button", FindMethod.ID));
        this.LimitedHomePageElementDict.put("btnMenu", new UIElementData("co.you.youapp.dev:id/imageLeft", FindMethod.ID));
        this.Container.put(PageKey.LimitedHomePageElementDict.ordinal(), this.LimitedHomePageElementDict);

        //Left Side Menu
        this.LeftSideMenuElementDict.put("optSupport", new UIElementData("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[1]", FindMethod.XPATH));
        this.LeftSideMenuElementDict.put("optSettings", new UIElementData("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[2]", FindMethod.XPATH));
        this.Container.put(PageKey.LeftSideMenuElementDict.ordinal(), this.LeftSideMenuElementDict);

        //Settings Page
        this.SettingPageElementDict.put("optGiveUsFeedback", new UIElementData("co.you.youapp.dev:id/clickFeedback", FindMethod.ID));
        this.SettingPageElementDict.put("optTermsOfUse", new UIElementData("co.you.youapp.dev:id/clickTnc", FindMethod.ID));
        this.SettingPageElementDict.put("optPrivacyPolicy", new UIElementData("co.you.youapp.dev:id/layoutPrivacy", FindMethod.ID));
        this.SettingPageElementDict.put("optLogOut", new UIElementData("co.you.youapp.dev:id/clickLogout", FindMethod.ID));
        this.Container.put(PageKey.SettingPageElementDict.ordinal(), this.SettingPageElementDict);

        //Confirm Email Address Page
        this.ConfirmEmailPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.ConfirmEmailPageElementDict.put("txtUserEmail", new UIElementData("co.you.youapp.dev:id/inputEmail", FindMethod.ID));
        this.ConfirmEmailPageElementDict.put("btnNext", new UIElementData("co.you.youapp.dev:id/buttonNext", FindMethod.ID));
        this.Container.put(PageKey.ConfirmEmailPageElementDict.ordinal(), this.ConfirmEmailPageElementDict);

        //Check Sent Email Page
        this.CheckSentEmailPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.Container.put(PageKey.CheckSentEmailPageElementDict.ordinal(), this.CheckSentEmailPageElementDict);

        //CreatePINPage/ Confirm Page
        this.CreateConfirmPinPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("lblSummary", new UIElementData("co.you.youapp.dev:id/textInputHint", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn1", new UIElementData("co.you.youapp.dev:id/input1", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn2", new UIElementData("co.you.youapp.dev:id/input2", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn3", new UIElementData("co.you.youapp.dev:id/input3", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn4", new UIElementData("co.you.youapp.dev:id/input4", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn5", new UIElementData("co.you.youapp.dev:id/input4", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn6", new UIElementData("co.you.youapp.dev:id/input6", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn7", new UIElementData("co.you.youapp.dev:id/input7", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn8", new UIElementData("co.you.youapp.dev:id/input8", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn9", new UIElementData("co.you.youapp.dev:id/input9", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btn0", new UIElementData("co.you.youapp.dev:id/input0", FindMethod.ID));
        this.CreateConfirmPinPageElementDict.put("btnDelete", new UIElementData("co.you.youapp.dev:id/clickDelete", FindMethod.ID));
        this.Container.put(PageKey.CreateConfirmPinPageElementDict.ordinal(), this.CreateConfirmPinPageElementDict);

        //FingerPrint Setting Page
        this.FingerPrintPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.FingerPrintPageElementDict.put("skipLink", new UIElementData("co.you.youapp.dev:id/textNegative", FindMethod.ID));
        this.Container.put(PageKey.FingerPrintPageElementDict.ordinal(), this.FingerPrintPageElementDict);

        //Unlock App Page
        this.UnlockAppPageElementDict.put("lblTitle", new UIElementData("co.you.youapp.dev:id/textTitle", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn1", new UIElementData("co.you.youapp.dev:id/input1", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn2", new UIElementData("co.you.youapp.dev:id/input2", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn3", new UIElementData("co.you.youapp.dev:id/input3", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn4", new UIElementData("co.you.youapp.dev:id/input4", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn5", new UIElementData("co.you.youapp.dev:id/input4", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn6", new UIElementData("co.you.youapp.dev:id/input6", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn7", new UIElementData("co.you.youapp.dev:id/input7", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn8", new UIElementData("co.you.youapp.dev:id/input8", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn9", new UIElementData("co.you.youapp.dev:id/input9", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btn0", new UIElementData("co.you.youapp.dev:id/input0", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btnForgot", new UIElementData("co.you.youapp.dev:id/button", FindMethod.ID));
        this.UnlockAppPageElementDict.put("btnDelete", new UIElementData("co.you.youapp.dev:id/clickDelete", FindMethod.ID));
        this.Container.put(PageKey.UnlockAppPageElementDict.ordinal(), this.UnlockAppPageElementDict);

        //Home Page
        //non explorer + explorer
        this.HomePageElementDict.put("logoYouTrip", new UIElementData("co.you.youapp.dev:id/imageHeader", FindMethod.ID));
        this.HomePageElementDict.put("btnTopUp", new UIElementData("co.you.youapp.dev:id/clickTopUp", FindMethod.ID));
        this.HomePageElementDict.put("btnExchange", new UIElementData("co.you.youapp.dev:id/clickExchange", FindMethod.ID));
        //non explorer only
        this.HomePageElementDict.put("btnCard", new UIElementData("co.you.youapp.dev:id/clickCard", FindMethod.ID));
        //explorer only
        this.HomePageElementDict.put("menuWallet", new UIElementData("Wallet", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("menuCard", new UIElementData("Card", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("menuHelp", new UIElementData("Help", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("menuSettings", new UIElementData("Settings", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.HomePageElementDict.ordinal(), this.HomePageElementDict);


    }

    public WebElement getElement(PageKey page, String elementKey, WebDriver driver) throws NotFoundException{
        return getElement(page, elementKey, driver, false);
    }

    public WebElement getElement(PageKey page, String elementKey, WebDriver driver, boolean isNullable){
        UIElementData result = null;
        result = (this.Container.get(page.ordinal())).get(elementKey);
        if (result ==  null){
            if (isNullable)
                return null;
            else
                throw new NotFoundException("Element Not Declared");
        }else{
            return result.getAndroidElement((AndroidDriver)driver, isNullable);
        }
    }
}
