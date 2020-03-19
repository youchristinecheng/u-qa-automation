package TestBased;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

import TestBased.UIElementData.*;

public class YouTripIosUIElementKey {
    public enum Market{
        Singapore,
        Thailand
    }

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
        DocumentTypeRegistrationElementDict,
        CameraAccessAlertElementDict,
        StepsPageElementDict,
        CameraPageElementDict,
        PhotoConfirmPageElementDict,
        TurnBackPopUpPageElementDict,
        ProofOfAddressPopUpPageElementDict,
        NamePageElementDict,
        NameOnCardElementDict,
        PersonalInformationElementDict,
        KYCNationalityElementDict,
        ResidentialAddressElementDict,
        KYCFinalStepElementDict,
        KYCKeepUpdatePopUpElementDict,
        NotificationAlertElementDict,
        LimitedHomePageElementDict,
        EnterYNumberPageElementDict,
        APPPinCodePageElementDict,
        HomePageElementDict,
        SettingPageElementDict,
        TopUpPageElementDict,
        TopUpChangeCardPageElementDict,
        LockCardPageElementDict,
        OrderReplacementCardPageElementDict,
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
    private HashMap<String, UIElementData> DocumentTypeRegistrationElementDict;
    private HashMap<String, UIElementData> CameraAccessAlertElementDict;
    private HashMap<String, UIElementData> StepsPageElementDict;
    private HashMap<String, UIElementData> CameraPageElementDict;
    private HashMap<String, UIElementData> PhotoConfirmPageElementDict;
    private HashMap<String, UIElementData> TurnBackPopUpPageElementDict;
    private HashMap<String, UIElementData> ProofOfAddressPopUpPageElementDict;
    private HashMap<String, UIElementData> NamePageElementDict;
    private HashMap<String, UIElementData> NameOnCardElementDict;
    private HashMap<String, UIElementData> PersonalInformationElementDict;
    private HashMap<String, UIElementData> ResidentialAddressElementDict;
    private HashMap<String, UIElementData> KYCNationalityElementDict;
    private HashMap<String, UIElementData> KYCFinalStepElementDict;
    private HashMap<String, UIElementData> KYCKeepUpdatePopUpElementDict;
    private HashMap<String, UIElementData> NotificationAlertElementDict;
    private HashMap<String, UIElementData> LimitedHomePageElementDict;
    private HashMap<String, UIElementData> EnterYNumberPageElementDict;
    private HashMap<String, UIElementData> APPPinCodePageElementDict;
    private HashMap<String, UIElementData> HomePageElementDict;
    private HashMap<String, UIElementData> SettingPageElementDict;
    private HashMap<String, UIElementData> TopUpPageElementDict;
    private HashMap<String, UIElementData> TopUpChangeCardPageElementDict;
    private HashMap<String, UIElementData> LockCardPageElementDict;
    private HashMap<String, UIElementData> OrderReplacementCardPageElementDict;
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
        this.DocumentTypeRegistrationElementDict = new HashMap<>();
        this.StepsPageElementDict = new HashMap<>();
        this.CameraPageElementDict = new HashMap<>();
        this.PhotoConfirmPageElementDict = new HashMap<>();
        this.TurnBackPopUpPageElementDict = new HashMap<>();
        this.ProofOfAddressPopUpPageElementDict = new HashMap<>();
        this.NamePageElementDict = new HashMap<>();
        this.NameOnCardElementDict = new HashMap<>();
        this.PersonalInformationElementDict = new HashMap<>();
        this.KYCNationalityElementDict = new HashMap<>();
        this.ResidentialAddressElementDict = new HashMap<>();
        this.KYCFinalStepElementDict = new HashMap<>();
        this.KYCKeepUpdatePopUpElementDict = new HashMap<>();
        this.NotificationAlertElementDict =  new HashMap<>();
        this.LimitedHomePageElementDict = new HashMap<>();
        this.EnterYNumberPageElementDict = new HashMap<>();
        this.APPPinCodePageElementDict = new HashMap<>();
        this.HomePageElementDict = new HashMap<>();
        this.TopUpPageElementDict = new HashMap<>();
        this.TopUpChangeCardPageElementDict = new HashMap<>();
        this.SettingPageElementDict = new HashMap<>();
        this.LockCardPageElementDict = new HashMap<>();
        this.OrderReplacementCardPageElementDict = new HashMap<>();

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
        this.CountrySelectionElementDict.put(Market.Singapore.toString(), new UIElementData("Singapore", FindMethod.ACCESSIBILITYID));
        this.CountrySelectionElementDict.put(Market.Thailand.toString(), new UIElementData("Thailand", FindMethod.ACCESSIBILITYID));
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
        this.IdentityVerificationElementDict.put("foreignerRegister", new UIElementData("//XCUIElementTypeStaticText[@name=\"For Foreigner\"]", FindMethod.XPATH));
        this.Container.put(PageKey.IdentityVerificationElementDict.ordinal(), this.IdentityVerificationElementDict);

        this.PRRegistrationElementDict.put("btnBack", new UIElementData("btnBack", FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("btnMyInfo", new UIElementData("btnMyInfo", FindMethod.ACCESSIBILITYID));
        this.PRRegistrationElementDict.put("btnSubmit", new UIElementData("btnSubmit", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.PRRegistrationElementDict.ordinal(), this.PRRegistrationElementDict);

        this.DocumentTypeRegistrationElementDict.put("btnEmploymentPass", new UIElementData("//XCUIElementTypeStaticText[@name=\"Employment Pass\"]", FindMethod.XPATH));
        this.DocumentTypeRegistrationElementDict.put("btnSPass", new UIElementData("//XCUIElementTypeStaticText[@name=\"S Pass\"]", FindMethod.XPATH));
        this.DocumentTypeRegistrationElementDict.put("btnWorkPermit", new UIElementData("//XCUIElementTypeStaticText[@name=\"Work Permit\"]", FindMethod.XPATH));
        this.DocumentTypeRegistrationElementDict.put("btnStudentPass", new UIElementData("//XCUIElementTypeStaticText[@name=\"Student Pass\"]", FindMethod.XPATH));
        this.DocumentTypeRegistrationElementDict.put("btnOthers", new UIElementData("//XCUIElementTypeStaticText[@name=\"Others\"]", FindMethod.XPATH));
        this.Container.put(PageKey.DocumentTypeRegistrationElementDict.ordinal(), this.DocumentTypeRegistrationElementDict);

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

        this.ProofOfAddressPopUpPageElementDict.put("lblTitle", new UIElementData("lblProofofAddressTitle", FindMethod.ACCESSIBILITYID));
        //TODO: Here is the switch between old version and new version. Once new version is launch, old version of code would be able to remove
//        this.ProofOfAddressPopUpPageElementDict.put("lblDescPt2", new UIElementData("lblProofofAddressDesc", FindMethod.ACCESSIBILITYID));
        //TODO: New version code below
        this.ProofOfAddressPopUpPageElementDict.put("lblDesc", new UIElementData("lblProofofAddressDesc", FindMethod.ACCESSIBILITYID));
        this.ProofOfAddressPopUpPageElementDict.put("lblDescPt1", new UIElementData("lblProofofAddressDescPt1", FindMethod.ACCESSIBILITYID));
        this.ProofOfAddressPopUpPageElementDict.put("lblDescPt2", new UIElementData("lblProofofAddressDescPt2", FindMethod.ACCESSIBILITYID));
        this.ProofOfAddressPopUpPageElementDict.put("lblDescPt3", new UIElementData("lblProofofAddressDescPt3", FindMethod.ACCESSIBILITYID));
        this.ProofOfAddressPopUpPageElementDict.put("lblDescPt4", new UIElementData("lblProofofAddressDescPt4", FindMethod.ACCESSIBILITYID));

        this.ProofOfAddressPopUpPageElementDict.put("btnOK", new UIElementData("btnOK", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.ProofOfAddressPopUpPageElementDict.ordinal(), this.ProofOfAddressPopUpPageElementDict);

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

        this.KYCKeepUpdatePopUpElementDict.put("btnAccept", new UIElementData("Keep Me Updated", FindMethod.ACCESSIBILITYID));
        this.KYCKeepUpdatePopUpElementDict.put("btnReject", new UIElementData("Do not send me updates", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.KYCKeepUpdatePopUpElementDict.ordinal(), this.KYCKeepUpdatePopUpElementDict);

        this.NotificationAlertElementDict.put("lblTitle", new UIElementData("//XCUIElementTypeStaticText[@name=\"“YouTrip” Would Like to Send You Notifications\"]", FindMethod.XPATH));
        this.NotificationAlertElementDict.put("btnDeny", new UIElementData("//XCUIElementTypeButton[@name=\"Don’t Allow\"]", FindMethod.XPATH));
        this.NotificationAlertElementDict.put("btnAllow", new UIElementData("//XCUIElementTypeButton[@name=\"Allow\"]", FindMethod.XPATH));
        this.Container.put(PageKey.NotificationAlertElementDict.ordinal(), this.NotificationAlertElementDict);

        this.LimitedHomePageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.LimitedHomePageElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.LimitedHomePageElementDict.put("lblKycResultDesc", new UIElementData("lblKycResultDesc", FindMethod.ACCESSIBILITYID));
        this.LimitedHomePageElementDict.put("lblReferenceNumVal", new UIElementData("Reference NumberVal", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.LimitedHomePageElementDict.ordinal(), this.LimitedHomePageElementDict);

        this.EnterYNumberPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit1", new UIElementData("txtYouIdDigit1", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit2", new UIElementData("txtYouIdDigit2", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit3", new UIElementData("txtYouIdDigit3", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit4", new UIElementData("txtYouIdDigit4", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit5", new UIElementData("txtYouIdDigit5", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit6", new UIElementData("txtYouIdDigit6", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit7", new UIElementData("txtYouIdDigit7", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit8", new UIElementData("txtYouIdDigit8", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit9", new UIElementData("txtYouIdDigit9", FindMethod.ACCESSIBILITYID));
        this.EnterYNumberPageElementDict.put("txtYouIdDigit10", new UIElementData("txtYouIdDigit10", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.EnterYNumberPageElementDict.ordinal(), this.EnterYNumberPageElementDict);

        this.APPPinCodePageElementDict.put("lblChangePinTitle", new UIElementData("Enter Current PIN", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("lblChangePinNewPinTitle", new UIElementData("Create New PIN", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("lblChangePinConfirmPinTitle", new UIElementData("Confirm New PIN", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("1", new UIElementData("1", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("2", new UIElementData("2", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("3", new UIElementData("3", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("4", new UIElementData("4", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("5", new UIElementData("5", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("6", new UIElementData("6", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("7", new UIElementData("7", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("8", new UIElementData("8", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("9", new UIElementData("9", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("0", new UIElementData("0", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("btnBackSpace", new UIElementData("icDelete", FindMethod.ACCESSIBILITYID));
        this.APPPinCodePageElementDict.put("btnForgot", new UIElementData("Forgot?", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.APPPinCodePageElementDict.ordinal(), this.APPPinCodePageElementDict);

        this.HomePageElementDict.put("btnMenu", new UIElementData("btnMenu", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("btnTopUp", new UIElementData("btnTopUp", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("btnExchange", new UIElementData("btnExchange", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("btnCard", new UIElementData("btnCard", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("lblCardStatus", new UIElementData("lblCardStatus", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("btnReferral", new UIElementData("btnReferral", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("menuBtnSupport", new UIElementData("btnSupport", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("menuBtnOverseasWithdrawal", new UIElementData("btnOverseasWithdrawal", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("menuBtnTransferOut", new UIElementData("btnTransferOut", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("menuBtnSetting", new UIElementData("btnSetting", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("txtCardLockedPopUpTitle", new UIElementData("Your Card is Locked", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("txtCardLockedPopUpDesc", new UIElementData("Tap \"Card\" button to unlock your card first.", FindMethod.ACCESSIBILITYID));
        this.HomePageElementDict.put("btnCardLockedPopUpOK", new UIElementData("OK", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.HomePageElementDict.ordinal(), this.HomePageElementDict);

        this.SettingPageElementDict.put("btnClose", new UIElementData("icClose", FindMethod.ACCESSIBILITYID));
        this.SettingPageElementDict.put("btnExplorerMode", new UIElementData("Explorer Mode", FindMethod.ACCESSIBILITYID));
        this.SettingPageElementDict.put("btnChangePIN", new UIElementData("Change PIN", FindMethod.ACCESSIBILITYID));
        this.SettingPageElementDict.put("btnLogout", new UIElementData("btnLogout", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.SettingPageElementDict.ordinal(), this.SettingPageElementDict);

        this.TopUpPageElementDict.put("btnClose", new UIElementData("icClose", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("lblTitle", new UIElementData("lblAmtTitle", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("txtAmt", new UIElementData("txtAmt", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("txtAmtDesc", new UIElementData("txtAmtDesc", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("btnInfo", new UIElementData("btnInfo", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("lblCard", new UIElementData("lblCard", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("btnChangeCard", new UIElementData("btnChangeCard", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("sliderTopUp", new UIElementData("sliderTopUp", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("lblTopUpSuccessPopUpDesc", new UIElementData("Top Up is Successful", FindMethod.ACCESSIBILITYID));
        this.TopUpPageElementDict.put("btnTopUpSuccessPopUpOk", new UIElementData("OK", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.TopUpPageElementDict.ordinal(), this.TopUpPageElementDict);

        this.TopUpChangeCardPageElementDict.put("btnClose", new UIElementData("icClose", FindMethod.ACCESSIBILITYID));
        this.TopUpChangeCardPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.TopUpChangeCardPageElementDict.put("txtCardNumber", new UIElementData("txtCardNumber", FindMethod.ACCESSIBILITYID));
        this.TopUpChangeCardPageElementDict.put("txtExpiryDate", new UIElementData("txtExpiryDate", FindMethod.ACCESSIBILITYID));
        this.TopUpChangeCardPageElementDict.put("txtCVV", new UIElementData("txtCVV", FindMethod.ACCESSIBILITYID));
        this.TopUpChangeCardPageElementDict.put("btnNext", new UIElementData("btnNext", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.TopUpChangeCardPageElementDict.ordinal(), this.TopUpChangeCardPageElementDict);

        this.LockCardPageElementDict.put("btnClose", new UIElementData("icClose", FindMethod.ACCESSIBILITYID));
        this.LockCardPageElementDict.put("toggleLockCard", new UIElementData("toggleLockCard", FindMethod.ACCESSIBILITYID));
        this.LockCardPageElementDict.put("lblOrderCardTitle", new UIElementData("lblOrderCardTitle", FindMethod.ACCESSIBILITYID));
        this.LockCardPageElementDict.put("lblOrderCardDesc", new UIElementData("lblOrderCardDesc", FindMethod.ACCESSIBILITYID));
        this.LockCardPageElementDict.put("btnOrderCard", new UIElementData("btnOrderCard", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.LockCardPageElementDict.ordinal(), this.LockCardPageElementDict);

        this.OrderReplacementCardPageElementDict.put("btnClose", new UIElementData("btnClose", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("lblTitle", new UIElementData("lblTitle", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("lblCurrencySign", new UIElementData("lblCurrencySign", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("lblFeeAmt", new UIElementData("lblFeeAmt", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("txtAddress", new UIElementData("txtAddress", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("lblCreditCardNumber", new UIElementData("lblCreditCardNumber", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("lblImportantNote", new UIElementData("lblImportantNote", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("btnChangeCreditCard", new UIElementData("btnChangeCreditCard", FindMethod.ACCESSIBILITYID));
        this.OrderReplacementCardPageElementDict.put("sliderOrderReplacementCard", new UIElementData("sliderOrderReplacementCard", FindMethod.ACCESSIBILITYID));
        this.Container.put(PageKey.OrderReplacementCardPageElementDict.ordinal(), this.OrderReplacementCardPageElementDict);

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
            return result.getIOSElement((IOSDriver)driver, isNullable);
        }
    }

    public List<WebElement> getHorizontalBalanceBlockList(WebDriver driver){
        // The Ordering of balance is under currency, amount in pair by zero-based indexing as object order 1 by 1.
        // The first (0-index) block must be "+OtherCurrency" cell block
        return driver.findElements(By.xpath("//XCUIElementTypeCell[@name=\"horizontalBalanceCell\"]/XCUIElementTypeStaticText"));

    }

    public List<WebElement> getRecentActivityBlockList(WebDriver driver){
        // The Ordering of activity is under Amount, Time, Description in collection by zero-based indexing as object 1 by 1.
        // The first (0-index) block must be the latest
        return driver.findElements(By.xpath("//XCUIElementTypeCell[@name=\"recentActivityCell\"]/XCUIElementTypeStaticText"));
    }
}
