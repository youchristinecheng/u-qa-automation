package TestBased;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import kong.unirest.Unirest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.text.SimpleDateFormat;
import java.util.Date;
import TestBased.YouTripIosUIElementKey.Market;
import org.testng.Assert;

import static org.testng.Assert.assertEquals;

public class YoutripIosSubRoutine {

    private YouTripIosUIElementKey UIElementKeyDict;
    private WebDriver driver;
    private WebDriverWait wait;
    public YouAPI api;

    public YoutripIosSubRoutine(YouTripIosUIElementKey UIElementKeyDict, WebDriver driver){
        this.UIElementKeyDict = UIElementKeyDict;
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        api = new YouAPI();
    }

    public WebDriverWait getDriverWait() { return this.wait; }

    public void procHandleDevAlert() throws Exception {
        IOSElement el;
        try {
            // Handle the ios DEV alert By Force Logout
            WebDriverWait wait = new WebDriverWait(driver, 20);
            System.out.println("DEV ALERT: dev version alert displayed");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.DevAlertElementDict, "Continue", driver);
            el.click();
        }catch(Exception e){
            throw e;
        }
    }

    public void procSelectCountry(Market country) throws Exception{
        String expectedResult;
        IOSElement el;
        try {
            // Handle the ios DEV alert By Force Logout
            WebDriverWait wait = new WebDriverWait(driver, 20);
            // Wait for page change
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
            //Go to country page
            System.out.println("TEST STEP: Country Selection - on page");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "optionCountry", driver);
            el.click();

            System.out.println("TEST STEP: Country Selection Page - click " + country.toString() + " button");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountrySelectionElementDict, country.toString(), driver);
            el.click();

            System.out.println("TEST STEP: Verified Country Description - on page");
            expectedResult = country.equals(Market.Singapore) ? "You must have a NRIC or FIN to apply for a Singapore YouTrip account." : "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank).";
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "lblDesc", driver), expectedResult));

            System.out.println("TEST STEP: Country Page - continue as " + country);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "btnNext", driver);
            el.click();

            System.out.println("TEST STEP: Get Started " + country + " Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver);
            assertEquals(el.getText(), "Get Started");
        }catch(Exception e){
            throw e;
        }
    }

    public void procOTPLogin(String mprefix, String mnumber, String email, boolean isFirstTimeLogin) throws Exception{
        IOSElement el;

        System.out.println("TEST STEP: Get Started Page - on page");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver);
        el.click();

        System.out.println("TEST STEP: Mobile Number Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "lblTitle", driver), "Enter Mobile Number"));

        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "txtPrefix", driver);
        el.click();
        el.clear();
        // Enter the test data value
        el.sendKeys(mprefix);

        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "txtPhoneNumber", driver);
        el.click();
        el.sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "btnNext", driver);
        el.click();

        Thread.sleep(2000);
        System.out.println("TEST STEP: OTP Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.OTPPageElementDict, "lblTitle", driver), "Enter Code from SMS"));
        // Get OTP from backdoor and input otp
        Thread.sleep(10000);

        String otpCode = api.getOTP(mprefix, mnumber);

        // Make use of app text field focus shifting functionality
        System.out.println("TEST STEP: OTP Page - entered OTP");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.OTPPageElementDict, "OTPDigit1", driver);
        el.sendKeys(otpCode);
        System.out.println("TEST STEP: OTP Page - Send OTP");

        Thread.sleep(5000);
        if(isFirstTimeLogin) {
            System.out.println("TEST STEP: Enter Email Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EmailPageElementDict, "lblTitle", driver), "Enter Email Address"));
            System.out.println("TEST DATA: Email address is " + email);
            System.out.println("TEST STEP: Enter Email Page - entered email");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EmailPageElementDict, "UserEmail", driver);
            el.sendKeys(email);
            System.out.println("TEST STEP: Enter Email Page - click Next button");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EmailPageElementDict, "btnNext", driver);
            el.click();
        }
    }

    public void procSubmitSGPCNRICKYC(boolean isFullRejectFlow, boolean isPartialRejectFlow, String surname, String givenName,
                                      String nameOnCard, String dateOfBirth, String nricNumber,
                                      String addressLine1, String addressLine2, String postoalCode) throws Exception {
        IOSElement el;

        if(!isPartialRejectFlow) {
            System.out.println("TEST STEP: PC Registration - NRIC");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.IdentityVerificationElementDict, "singaporeanPRRegister", driver);
            el.click();
            // Select on Manual NRIC
            System.out.println("TEST STEP: PC Registration - Select Manual NRIC");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PRRegistrationElementDict, "btnSubmit", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Start Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.StepsPageElementDict, "lblTitle", driver), "Just a Few Steps"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.StepsPageElementDict, "btnStart", driver);
            el.click();

            Thread.sleep(2000);
            System.out.println("TEST STEP: KYC Process - Allow Camera access alert");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraAccessAlertElementDict, "btnOK", driver, true);
            if (el != null)
                el.click();

            System.out.println("TEST STEP: KYC Process - Front of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Process - Confirm Front of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Process - Start Back of NRIC Capture dialog");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.TurnBackPopUpPageElementDict, "lblDesc", driver), "Now turn to the back of your NRIC and take a photo again."));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.TurnBackPopUpPageElementDict, "btnOK", driver);
            el.click();
            System.out.println("TEST STEP: KYC Process - Back of NRIC Capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
            el.click();
            System.out.println("TEST STEP: KYC Process - Confirm Back of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
            el.click();
            Thread.sleep(2000);
        }

        System.out.println("TEST STEP: KYC Process - Enter User Name");
        if(isFullRejectFlow || isPartialRejectFlow) {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver), surname));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver), givenName));
        }else {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "lblTitle", driver), "Full Name (as per NRIC)"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver);
            el.sendKeys(surname);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver);
            el.sendKeys(givenName);
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "lblCheckAndConfirmTitle", driver), "Check and Confirm"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnCheckAndConfirmConfirm", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Enter NameOnCard");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NameOnCardElementDict, "lblTitle", driver), "Preferred Name"));
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NameOnCardElementDict, "txtCardName", driver), nameOnCard));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Enter Personal Information");
        if(isFullRejectFlow || isPartialRejectFlow){
            String formattedDateOfBirth = dateOfBirth.substring(0, 2) + " - " + dateOfBirth.substring(2, 4) + " - " + dateOfBirth.substring(4);
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver), nricNumber));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver), formattedDateOfBirth));
        } else {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver);
            el.sendKeys(nricNumber);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver);
            el.sendKeys(dateOfBirth);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnNationality", driver);
            el.click();
            Thread.sleep(500);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCNationalityElementDict, "Singaporean", driver);
            el.click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnMale", driver);
            el.click();
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Enter Residential Address");
        if(isFullRejectFlow || isPartialRejectFlow){
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver), addressLine1));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver), addressLine2));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver), postoalCode));
        } else {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "lblTitle", driver), "Residential Address"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver);
            el.sendKeys(addressLine1);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver);
            el.sendKeys(addressLine2);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver);
            el.sendKeys(postoalCode);
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Submit KYC");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "lblTitle", driver), "Final Step"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "btnAgree", driver);
        el.click();
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "btnSubmit", driver);
        el.click();
        Thread.sleep(2000);
        // Accept Keep On Notification
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCKeepUpdatePopUpElementDict, "btnAccept", driver, true);
        if (el != null)
            el.click();
    }

    public void procSubmitSGNPCEmploymentPassKYC(boolean isFullRejectFlow, boolean isPartialRejectFlow, String youId, String surname, String givenName,
                                                 String dateOfBirth, String nricNumber,
                                                 String addressLine1, String addressLine2, String postoalCode,
                                                 String newSurname, String newGivenName, String newDateOfBirth, String newAddressLine1, String newAddressLine2) throws Exception {
        IOSElement el;

        if(!isPartialRejectFlow) {
            if(!isFullRejectFlow) {
                System.out.println("TEST STEP: NPC Registration - Enter Y-Number");
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EnterYNumberPageElementDict, "lblTitle", driver), "Enter Y-Number"));
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EnterYNumberPageElementDict, "txtYouIdDigit1", driver);
                el.sendKeys(youId.substring(2));
                Thread.sleep(2000);
            }
            System.out.println("TEST STEP: NPC Registration - Foreigner Employment Pass");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.IdentityVerificationElementDict, "foreignerRegister", driver);
            el.click();
            // Select on Manual NRIC
            System.out.println("TEST STEP: NPC Registration - Select Employment Pass");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.DocumentTypeRegistrationElementDict, "btnEmploymentPass", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Start Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.StepsPageElementDict, "lblTitle", driver), "Just a Few Steps"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.StepsPageElementDict, "btnStart", driver);
            el.click();

            Thread.sleep(2000);
            System.out.println("TEST STEP: KYC Process - Allow Camera access alert");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraAccessAlertElementDict, "btnOK", driver, true);
            if (el != null)
                el.click();

            System.out.println("TEST STEP: KYC Process - Front of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Process - Confirm Front of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Process - Start Back of NRIC Capture dialog");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.TurnBackPopUpPageElementDict, "lblDesc", driver), "Now turn to the back of your Pass / Permit and take a photo again."));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.TurnBackPopUpPageElementDict, "btnOK", driver);
            el.click();
            System.out.println("TEST STEP: KYC Process - Back of NRIC Capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
            el.click();
            System.out.println("TEST STEP: KYC Process - Confirm Back of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
            el.click();
            System.out.println("TEST STEP: KYC Process - Start Proof of Address Capture dialog");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblTitle", driver), "Proof of Address"));
            Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDesc", driver).getText(), "Please take a photo of one of the followings:");
            Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt1", driver).getText(), "•      Phone Bill within 6 months");
            Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt2", driver).getText(), "•      Utilities Bill within 6 months");
            Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt3", driver).getText(), "•      Bank Statement within 6 months");
            Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt4", driver).getText(), "•      Other accepted documents");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "btnOK", driver);
            el.click();
            System.out.println("TEST STEP: KYC Process - Proof of Address Capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
            el.click();
            System.out.println("TEST STEP: KYC Process - Confirm Proof of Address capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
            el.click();
            Thread.sleep(2000);
        }

        System.out.println("TEST STEP: KYC Process - Enter User Name");
        if(isFullRejectFlow || isPartialRejectFlow) {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "lblTitle", driver), "Full Name (as per Pass / Permit)"));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver), surname));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver), givenName));
            if(isFullRejectFlow) {
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver);
                el.clear();
                el.sendKeys(newSurname);
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver);
                el.clear();
                el.sendKeys(newGivenName);
            }
        }else {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "lblTitle", driver), "Full Name (as per Pass / Permit)"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver);
            el.sendKeys(surname);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver);
            el.sendKeys(givenName);
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "lblCheckAndConfirmTitle", driver), "Check and Confirm"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnCheckAndConfirmConfirm", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Enter Personal Information");
        if(isFullRejectFlow || isPartialRejectFlow) {
            String formattedDateOfBirth = dateOfBirth.substring(0, 2) + " - " + dateOfBirth.substring(2, 4) + " - " + dateOfBirth.substring(4);
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver), nricNumber));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver), formattedDateOfBirth));
            if(isFullRejectFlow) {
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver);
                el.clear();
                el.sendKeys(newDateOfBirth);
            }
        } else {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver);
            el.sendKeys(nricNumber);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver);
            el.sendKeys(dateOfBirth);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnNationality", driver);
            el.click();
            Thread.sleep(500);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCNationalityElementDict, "Singaporean", driver);
            el.click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnMale", driver);
            el.click();
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Enter Residential Address");
        if(isFullRejectFlow || isPartialRejectFlow) {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver), addressLine1));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver), addressLine2));
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver), postoalCode));
            if (isFullRejectFlow) {
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver);
                el.clear();
                el.sendKeys(newAddressLine1);
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver);
                el.clear();
                el.sendKeys(newAddressLine2);
            }
        } else {
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "lblTitle", driver), "Residential Address"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver);
            el.sendKeys(addressLine1);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver);
            el.sendKeys(addressLine2);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver);
            el.sendKeys(postoalCode);
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Submit KYC");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "lblTitle", driver), "Final Step"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "btnAgree", driver);
        el.click();
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "btnSubmit", driver);
        el.click();
        Thread.sleep(2000);
        // Accept Keep On Notification
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCKeepUpdatePopUpElementDict, "btnAccept", driver, true);
        if (el != null)
            el.click();
    }
}
