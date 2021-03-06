package TestBased;

import TestBased.TestAccountData.Market;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class YouTripAndroidSubRoutine {

    private YouTripAndroidUIElementKey UIElementKeyDict;
    private AppiumDriver driver;
    private WebDriverWait wait;
    private Market currentMarket;
    public YouAPI api;

    public TestAccountData.Market getCurrentMarket() {
        return currentMarket;
    }

    public YouTripAndroidSubRoutine(Market market, YouTripAndroidUIElementKey UIElementKeyDict, AppiumDriver driver){
        this.UIElementKeyDict = UIElementKeyDict;
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
        api = new YouAPI();
        currentMarket = market;
        api.setMarket(this.currentMarket);
    }

    public WebDriverWait getDriverWait() {
        return this.wait;
    }

    public void procSelectCountry(Market country) throws Exception {
        String expectedResult;
        AndroidElement el;
        try {
            Thread.sleep(2000);
            //wait till on country page and click country select
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "lblTitle", driver)));
            System.out.println("TEST STEP: Country Page - on page");
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "optionCountry", driver);
            el.click();
            System.out.println("TEST STEP: Country Page - clicked select country");
            //select country
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountrySelectionElementDict, country.toString(), driver);
            el.click();
            System.out.println("TEST STEP: Country Selection Page - click " + country.toString() + " button");
            //assert correct country is selected
            expectedResult = country.equals(Market.Singapore) ? OnScreenExpectedStringValue.CountryPageSGDescription : OnScreenExpectedStringValue.CountryPageTHDescription;
            assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "countryDesc", driver)).getText(), expectedResult);
            System.out.println("TEST STEP: Country Page - verified country description");

        }catch(Exception e){
            throw e;
        }
    }

    public void procConfirmCountry() throws Exception {
        AndroidElement el;
        try {
            Thread.sleep(2000);
            //click confirm country
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "btnConfirm", driver);
            el.click();
            System.out.println("TEST STEP: Country Page - click continue button");
            Thread.sleep(15000);
            //wait till on get started page
            wait.until(ExpectedConditions.elementToBeClickable((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver))));
            System.out.println("TEST STEP: Get Started Page - on page");
            assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver)).getText(), OnScreenExpectedStringValue.GetStartPageGetStartButton);

        }catch(Exception e){
            throw e;
        }
    }

    public void procOTPLogin(String mprefix, String mnumber, boolean isFirstTimeLogin) throws Exception {
        AndroidElement el;
        try {
            //click get started button from start screen
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver);
            el.click();
            System.out.println("TEST STEP: Get Started Page - click Get Started Button");
            Thread.sleep(2000);
            //wait till on enter mobile number page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.MobileNumberPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.EnterMobileNumberPageTitle));
            System.out.println("TEST STEP: Mobile Number Page - on page");
            System.out.println("TEST DATA: Using Mobile Number " + mprefix + " " + mnumber);
            //clear mobile prefix field and enter mobile prefix
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.MobileNumberPageElementDict, "txtPrefix", driver);
            el.clear();
            el.sendKeys(mprefix);
            System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
            //enter mobile number
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.MobileNumberPageElementDict, "txtPhoneNumber", driver);
            el.click();
            el.sendKeys(mnumber);
            System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
            //hide keyboard
            driver.hideKeyboard();
            //click next button
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.MobileNumberPageElementDict, "btnNext", driver);
            el.click();
            System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
            Thread.sleep(2000);
            //wait till on enter SMS page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.OTPPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.OTPPageTitle));
            System.out.println("TEST STEP: OTP Page - on page");
            //get OTP from backdoor and input otp
            String otpCode = api.getOTP(mprefix, mnumber);
            System.out.println("TEST DATA: OTP Code is " +otpCode);
            String otp1 = otpCode.substring(0);
            String otp2 = otpCode.substring(1);
            String otp3 = otpCode.substring(2);
            String otp4 = otpCode.substring(3);
            String otp5 = otpCode.substring(4);
            String otp6 = otpCode.substring(5);
            //enter OTP - note: need to enter each digit separately
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.OTPPageElementDict, "OTPDigit1", driver);
            el.sendKeys(otp1);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.OTPPageElementDict, "OTPDigit2", driver);
            el.sendKeys(otp2);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.OTPPageElementDict, "OTPDigit3", driver);
            el.sendKeys(otp3);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.OTPPageElementDict, "OTPDigit4", driver);
            el.sendKeys(otp4);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.OTPPageElementDict, "OTPDigit5", driver);
            el.sendKeys(otp5);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.OTPPageElementDict, "OTPDigit6", driver);
            el.sendKeys(otp6);
            System.out.println("TEST STEP: OTP Page - entered OTP");

            Thread.sleep(3000);

            if(isFirstTimeLogin) {
                //wait till on enter email page
                assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.EmailPageElementDict, "lblTitle", driver)).getText(), OnScreenExpectedStringValue.EnterEmailPageTitle);
                System.out.println("TEST STEP: Enter Email Page - on page");
            }
            Thread.sleep(3000);

        }catch(Exception e){
            throw e;
        }
    }

    //TODO procedure for email login

    public void procEnterEmail(String email) throws Exception {
        AndroidElement el;
        try {
            //wait till on enter email page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.EmailPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.EnterEmailPageTitle));
            System.out.println("TEST STEP: Enter Email Page - on page");
            System.out.println("TEST DATA: Email address " +email);
            //input email
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.EmailPageElementDict, "txtUserEmail", driver);
            el.sendKeys(email);
            //hide keyboard
            driver.hideKeyboard();
            //click next button
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.EmailPageElementDict, "btnNext", driver);
            el.click();
            System.out.println("TEST STEP: Enter Email Page - click Next button");
            Thread.sleep(2000);
            //wait till on welcome page
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.WelcomePageElementDict, "lblWelcome", driver)));
            System.out.println("TEST STEP: Welcome Page - on page");
            assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.WelcomePageElementDict, "lblWelcome", driver)).getText(), OnScreenExpectedStringValue.LimitedHomePageSGTitle);

        }catch(Exception e){
            throw e;
        }
    }

    public void procEnterPIN(String pin) throws Exception {
        AndroidElement el;
        try {
            //wait till on enter mobile number page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.UnlockAppPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.UnlockAppPINPageSGTitle));
            System.out.println("TEST STEP: Unlock App Page - on page");
            //for each PIN digit click corresponding number on keypad
            for (char i : pin.toCharArray()) {
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.UnlockAppPageElementDict, ("btn"+Character.toString(i)), driver);
                    el.click();
                    System.out.println("TEST STEP: Unlock App Page - click digit " +Character.toString(i));
            }

            System.out.println("TEST STEP: Unlock App Page - PIN entered");
            Thread.sleep(4000);
        }catch(Exception e){
            throw e;
        }
    }

    public void procSelectCardType(boolean isPC) throws Exception {
        AndroidElement el;
        try {
            //wait till on welcome page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.WelcomePageElementDict, "lblWelcome", driver)), OnScreenExpectedStringValue.LimitedHomePageSGTitle));
            System.out.println("TEST STEP: Welcome Page - on page");
            if (isPC) {
                //click get youtrip card for free
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.WelcomePageElementDict, "btnPCRegister", driver);
                assertEquals(el.getText(), OnScreenExpectedStringValue.LimitedHomePageSGPCRegistrationButton);
                el.click();
                System.out.println("TEST STEP: Welcome Page - click " + OnScreenExpectedStringValue.LimitedHomePageSGPCRegistrationButton + " button");
                Thread.sleep(2000);
            } else {
                //click i have youtrip card
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.WelcomePageElementDict, "btnNPCRegister", driver);
                assertEquals(el.getText(), OnScreenExpectedStringValue.LimitedHomePageSGNPCRegistrationButton);
                el.click();
                System.out.println("TEST STEP: Welcome Page - click " + OnScreenExpectedStringValue.LimitedHomePageSGNPCRegistrationButton + " button");
                Thread.sleep(2000);
            }
        }catch(Exception e){
                throw e;
        }
    }

    public void procEnterYNumber(String ynumber) throws Exception {
        AndroidElement el;
        try {
            //wait till on enter Y-number page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCEnterYNumberPageTitle));
            System.out.println("TEST STEP: Enter Y-Number Page - on page");
            System.out.println("TEST DATA: Y-Number is " +ynumber);
            String ynum_digit1 = ynumber.substring(2);
            String ynum_digit2 = ynumber.substring(3);
            String ynum_digit3 = ynumber.substring(4);
            String ynum_digit4 = ynumber.substring(5);
            String ynum_digit5 = ynumber.substring(6);
            String ynum_digit6 = ynumber.substring(7);
            String ynum_digit7 = ynumber.substring(8);
            String ynum_digit8 = ynumber.substring(9);
            String ynum_digit9 = ynumber.substring(10);
            String ynum_digit10 = ynumber.substring(11);
            //enter y-number - note: need to enter each digit separately
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit1", driver);
            el.sendKeys(ynum_digit1);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit2", driver);
            el.sendKeys(ynum_digit2);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit3", driver);
            el.sendKeys(ynum_digit3);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit4", driver);
            el.sendKeys(ynum_digit4);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit5", driver);
            el.sendKeys(ynum_digit5);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit6", driver);
            el.sendKeys(ynum_digit6);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit7", driver);
            el.sendKeys(ynum_digit7);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit8", driver);
            el.sendKeys(ynum_digit8);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit9", driver);
            el.sendKeys(ynum_digit9);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit10", driver);
            el.sendKeys(ynum_digit10);
            System.out.println("TEST STEP: Enter Y-Number Page - entered Y-number");
            Thread.sleep(2000);
            //wait till on identity verification page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.IdentityVerificationElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCIdentityVerificationPageTitle));
            System.out.println("TEST STEP: Identity Verification Page - on page");

        }catch(Exception e){
            throw e;
        }
    }

    public void procSelectNRIC(String nricType) throws Exception {
        AndroidElement el;
        try {
            //wait till on identity verification page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.IdentityVerificationElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCIdentityVerificationPageTitle));
            System.out.println("TEST STEP: Identity Verification Page - on page");
            //click Singaporean and PR
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.IdentityVerificationElementDict, "btnSG", driver);
            el.click();
            System.out.println("TEST STEP: Identity Verification Page - click Singaporean/PR button");
            Thread.sleep(2000);
            //wait till on Singaporean and PR page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PRRegistrationElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCIdentityVerificationPageSingaporeanButton));
            System.out.println("TEST STEP: Singaporean/PR Page - on page");

            if (nricType.equals("MyInfo")) {
                //TODO select MyInfo
            } else if (nricType.equals("Manual")) {
                //click submit manually
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PRRegistrationElementDict, "btnManualSubmit", driver);
                el.click();
                System.out.println("TEST STEP: Singaporean/PR Page - click submit manually link");
                Thread.sleep(2000);
            } else {
                //TODO goes back to reselect
            }
        }catch (Exception e){
            throw e;
        }
    }

    public void procSelectNonNRIC(String nonNricType) throws Exception {
        AndroidElement el;
        try {
            //wait till on identity verification page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.IdentityVerificationElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCIdentityVerificationPageTitle));
            System.out.println("TEST STEP: Identity Verification Page - on page");
            //click Foreigner
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.IdentityVerificationElementDict, "btnForeigner", driver);
            el.click();
            System.out.println("TEST STEP: Identity Verification Page - click Foreigner");
            Thread.sleep(2000);
            //wait till on Foreigner submit document list
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ForeignerRegistrationElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCSubmitDocumentPageTitle));
            System.out.println("TEST STEP: Foreigner Submit Document - on page");

            if (nonNricType.equals("Employment Pass")) {
                //click submit with Employment Pass
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ForeignerRegistrationElementDict, "btnEmploymentPass", driver);
                el.click();
                System.out.println("TEST STEP: Foreigner Submit Document - click Employment Pass");
                Thread.sleep(2000);
            } else if (nonNricType.equals("S Pass")) {
                //click submit with S Pass
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ForeignerRegistrationElementDict, "btnSPass", driver);
                el.click();
                System.out.println("TEST STEP: Foreigner Submit Document - click S Pass");
                Thread.sleep(2000);
            } else if (nonNricType.equals("Work Permit")) {
                //click submit with Work Permit
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ForeignerRegistrationElementDict, "btnWorkPermit", driver);
                el.click();
                System.out.println("TEST STEP: Foreigner Submit Document - click Work Permit");
                Thread.sleep(2000);
            } else if (nonNricType.equals("Student Pass")) {
                //click submit with Student Pass
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ForeignerRegistrationElementDict, "btnStudentPass", driver);
                el.click();
                System.out.println("TEST STEP: Foreigner Submit Document - click Student Pass");
                Thread.sleep(2000);
            } else {
                //TODO handle other type with drop down box of others
            }
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.StepsPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCSGStepsPageTitle));
            System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - on page");
            }catch(Exception e){
                throw e;
            }
        }

    public void procSubmitSGKYC(boolean isFullRejectFlow, boolean isPartialRejectFlow, boolean isNRIC, boolean isPC,
                                String surname, String firstname, String nameOnCard, String dob, String idNumber,
                                String nationality, String addressLine1, String addressLine2, String postalcode) throws Exception {
        AndroidElement el;
        try {
            //handle full submission/ full rejection flow where photos are required
            if (!isPartialRejectFlow) {
                //--------------//
                //KYC FIRST STEP//
                //--------------//

                //wait till on start of KYC page (just a few steps page)
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.StepsPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.KYCSGStepsPageTitle));
                System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - on page");
                //click start KYC
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.StepsPageElementDict, "btnStart", driver);
                el.click();
                System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click start now button");
                Thread.sleep(3000);
                //accept the Android camera permission if it appears (not needed permission allowed)
                //el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.AndroidSystemAlertElementDict, "cameraAccept", driver, true);
                //if (el != null) {
                //    el.click();
                //    System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click allow YouTrip access to camera button");
                //    Thread.sleep(3000);
                //}

                //--------------//
                //KYC FRONT PHOTO//
                //--------------//

                //wait till on page and take front photo
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
                el.click();
                System.out.println("TEST STEP: KYC step 1 front of card - click take photo button");
                Thread.sleep(3000);
                //confirm front photo
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
                el.click();
                System.out.println("TEST STEP: KYC step 1 front of card - click all data is readable button");
                Thread.sleep(2000);

                //--------------//
                //KYC BACK PHOTO//
                //--------------//
                if (!isNRIC) {
                    //wait till on back of card information pop up appears and confirm it
                    wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.TurnBackPopUpPageElementDict, "lblDesc", driver)), OnScreenExpectedStringValue.KYCBackOfPassPopUpTitle));
                    System.out.println("TEST STEP: KYC step 2 back of card - ID reminder dialog appears");
                } else {
                    //wait till on back of card information pop up appears and confirm it
                    wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.TurnBackPopUpPageElementDict, "lblDesc", driver)), OnScreenExpectedStringValue.KYCBackOfNRICPopUpTitle));
                    System.out.println("TEST STEP: KYC step 2 back of card - ID reminder dialog appears");

                }
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.TurnBackPopUpPageElementDict, "btnOK", driver);
                el.click();
                System.out.println("TEST STEP: KYC step 2 back of card - click on got it button from ID reminder dialog");
                Thread.sleep(3000);
                //wait till on page and take back photo
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
                el.click();
                System.out.println("TEST STEP: KYC step 2 back of card - click take photo button");
                Thread.sleep(3000);
                //confirm back  photo
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
                el.click();
                System.out.println("TEST STEP: KYC step 2 back of card - click all data is readable button");
                Thread.sleep(3000);

                //--------------//
                //KYC ADDRESS PROOF PHOTO//
                //--------------//

                //handle non NRIC address proof photos
                if (!isNRIC) {
                    //wait until proof of address pop up appears and confirm it
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ProofOfAddressPageElementDict, "btnOK", driver);
                    el.click();
                    System.out.println("TEST STEP: KYC step 3 proof of address - click ok");
                    Thread.sleep(3000);
                    //wait till on page and take photo of address proof
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
                    el.click();
                    System.out.println("TEST STEP: KYC step 3 proof of address - click take photo button");
                    Thread.sleep(3000);
                    //confirm address proof photo
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
                    el.click();
                    System.out.println("TEST STEP: KYC step 3 proof of address - click all data is readable button");
                    Thread.sleep(3000);
                }
            }

            //--------------//
            //KYC FULL NAME //
            //--------------//
            if (!isNRIC) {
                //wait till on full name page for non nric
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NamePageElementDict, "lblTitle", driver), OnScreenExpectedStringValue.KYCEnterFullNamePagePassTitle));
                System.out.println("TEST STEP: Full Name (Non NRIC) page - on page");
            } else {
                //wait till on full name page for nric
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NamePageElementDict, "lblTitle", driver), OnScreenExpectedStringValue.KYCEnterFullNamePageNRICTitle));
                System.out.println("TEST STEP: Full Name (NRIC) page - on page");
            }
            //handle new submission cases need to enter first name and surname
            if (!isFullRejectFlow && !isPartialRejectFlow) {
                //enter name
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver);
                el.sendKeys(surname);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver);
                el.sendKeys(firstname);
            }

            driver.hideKeyboard();
            //continue to next page
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NamePageElementDict, "btnNext", driver);
            el.click();
            System.out.println("TEST STEP: Full Name page - click next button");
            Thread.sleep(2000);
            //wait till check and confirm dialog appear and confirm
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NamePageElementDict, "lblCheckAndConfirmTitle", driver), OnScreenExpectedStringValue.KYCCheckFullNamePopUpTitle));
            System.out.println("TEST STEP: Full Name page - check and confirm dialog appeared");
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NamePageElementDict, "btnCheckAndConfirmConfirm", driver);
            el.click();
            System.out.println("TEST STEP: Full Name page - on check and confirm dialog click Confirm button");
            Thread.sleep(3000);

            //----------------//
            //KYC NAME ON CARD//
            //----------------//

            //handle PC case where name on card needs to be input
            if (isPC) {
                //wait till on page
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NameOnCardElementDict, "lblTitle", driver), OnScreenExpectedStringValue.KYCEnterNameOnCardPageTitle));
                //handle new submissions case to edit name on card
                if (!isFullRejectFlow || !isPartialRejectFlow) {
                    //clear name and enter new one
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NameOnCardElementDict, "txtCardName", driver);
                    el.clear();
                    el.sendKeys(nameOnCard);
                    System.out.println("TEST STEP: Preferred Name page - change name on card");
                }

                driver.hideKeyboard();
                //confirm new name
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.NameOnCardElementDict, "btnNext", driver);
                el.click();
                System.out.println("TEST STEP: Preferred Name page - click next button");
                Thread.sleep(3000);
            }

            //--------------------//
            //KYC PERSONAL DETAILS//
            //-------------------//

            //wait till on page and enter personal information
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), OnScreenExpectedStringValue.KYCEnterPersonalInformationPageTitle));

            //handle new submission cases need to enter personal details
            if (!isFullRejectFlow && !isPartialRejectFlow) {
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver);
                el.sendKeys(idNumber);
                System.out.println("TEST STEP: Personal Information page - input ID number");
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver);
                el.sendKeys(dob);
                System.out.println("TEST STEP: Personal Information page - input Date Of Birth");
                //click nationality option, which opens new page with drop down
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PersonalInformationElementDict, "btnNationality", driver);
                el.click();
                //search for nationality and click the first option
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCNationalityElementDict, "txtInputSearch", driver);
                el.sendKeys(nationality);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCNationalityElementDict, "btnFirstSearchOption", driver);
                el.click();
                System.out.println("TEST STEP: Personal Information page - searched and selected a nationality");
                //select sex and continue
                Thread.sleep(2000);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PersonalInformationElementDict, "btnMale", driver);
                el.click();
                System.out.println("TEST STEP: Personal Information page - click sex as male");
            }
            driver.hideKeyboard();
            Thread.sleep(2000);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.PersonalInformationElementDict, "btnNext", driver);
            el.click();
            System.out.println("TEST STEP: Personal Information page - click next button");
            Thread.sleep(3000);

            //-----------------------//
            //KYC RESIDENTIAL DETAILS//
            //-----------------------//

            //wait till on page and enter residential address
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ResidentialAddressElementDict, "lblTitle", driver), OnScreenExpectedStringValue.KYCEnterResidentialAddressPageTitle));

            //handle new submission cases need to enter address details
            if (!isFullRejectFlow && !isPartialRejectFlow) {
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver);
                el.sendKeys(addressLine1);
                System.out.println("TEST STEP: Residential Address page - input address line 1");
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver);
                el.sendKeys(addressLine2);
                System.out.println("TEST STEP: Residential Address page - input address line 2");
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver);
                el.sendKeys(postalcode);
                System.out.println("TEST STEP: Residential Address page - input postal code");
            }
            driver.hideKeyboard();
            Thread.sleep(2000);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ResidentialAddressElementDict, "btnNext", driver);
            el.click();
            System.out.println("TEST STEP: Residential Address page - click next button");
            Thread.sleep(3000);

            //---------------//
            //KYC FINAL STEPS//
            //---------------//

            //wait till on page, confirm final steps and submit kyc
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCFinalStepElementDict, "lblTitle", driver), OnScreenExpectedStringValue.KYCFinalStepPageTitle));
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCFinalStepElementDict, "btnAgree", driver);
            el.click();
            System.out.println("TEST STEP: Final Step page - click confirm TnC checkbox");
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCFinalStepElementDict, "btnSubmit", driver);
            el.click();
            System.out.println("TEST STEP: Final Step page - click submit button");
            Thread.sleep(2000);

            //handle marketing consent if not already accepted
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCKeepUpdatePopUpElementDict, "btnAccept", driver, true);
            if (el != null) {
                //wait for marketing consent dialog and accept
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCKeepUpdatePopUpElementDict, "lblTitle", driver), OnScreenExpectedStringValue.KYCMarketingConsentPopUpTitle));
                System.out.println("TEST STEP: Marketing consent popup - on page");
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCKeepUpdatePopUpElementDict, "btnAccept", driver);
                el.click();
                System.out.println("TEST STEP: Final Step page - click Keep Me Updated button");
                Thread.sleep(3000);
            }

            //wait for thank you page and confirm
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver)).getText(), OnScreenExpectedStringValue.LimitedHomePageSGKYCSubmittedTitle);
            System.out.println("TEST STEP: KYC submitted successfully");
            Thread.sleep(10000);

        }catch(Exception e){
            throw e;
        }

    }

    public void procSubmitTHKYC(boolean isPC, String thaiID) throws InterruptedException {
        AndroidElement el;
        try {
            //wait till on Enter Thai ID page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ThaiIdNumberElementDict, "lblTitle", driver)), "Enter ID Number"));
            System.out.println("TEST STEP: Enter ID Number page - on page");
            //enter Thai ID and confirm
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ThaiIdNumberElementDict, "txtIDNumber", driver);
            el.sendKeys(thaiID);
            System.out.println("TEST STEP: Enter ID Number page - entered thai ID number");
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ThaiIdNumberElementDict, "btnNext", driver);
            el.click();
            System.out.println("TEST STEP: Enter ID Number page - clicked next button");
            Thread.sleep(2000);
            //wait till on Authenticate with KBank page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCKPLUSAuthenticationPageElementDict, "lblTitle", driver)), "Register with K PLUS"));
            System.out.println("TEST STEP: Register with K Plus page - on page");
            //click register with kplus
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.KYCKPLUSAuthenticationPageElementDict, "btnRegisterKPlus", driver);
            el.click();
            System.out.println("TEST STEP: Register with K Plus page - clicked Register with KPLUS button");
            Thread.sleep(2000);
        }catch(Exception e){
            throw e;
        }
    }

    public void procRejectKYC(boolean isFullReject) throws Exception {
        AndroidElement el;
        try {
            //get and store the KYC reference number
            String kycRefNo = (UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "referenceNum", driver)).getText();
            System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);
            if (isFullReject) {
                //call YP full reject with Ref Number
                api.yp_fullReject(kycRefNo);
            } else {
                //call YP partial reject with Ref Number
                api.yp_partialReject(kycRefNo);
            }

            //back to the app - wait for reject to be updated
            Thread.sleep(15000);
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
            System.out.println("TEST STEP: KYC rejection received");
            assertEquals(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), OnScreenExpectedStringValue.LimitedHomePageSGKYCRejectedTitle);
        }catch(Exception e){
            throw e;
        }
    }

    public void procApproveKYC(boolean isPC) throws Exception {
        AndroidElement el;
        try {
            //get and store the KYC reference number
            String kycRefNo = (UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "referenceNum", driver)).getText();
            System.out.println("TEST DATA: KYC submission reference number is " + kycRefNo);

            //call YP full reject with Ref Number
            api.yp_approve(kycRefNo);

            //back to the app - wait for approval
            Thread.sleep(10000);
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
            System.out.println("TEST STEP: KYC rejection received");
            if (isPC) {
                assertEquals(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), OnScreenExpectedStringValue.LimitedHomePageSGPCKYCApprovedTitle);
            } else {
                assertEquals(UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), OnScreenExpectedStringValue.LimitedHomePageSGNPCKYCApprovedTitle);
            }
            Thread.sleep(2000);
        }catch(Exception e){
            throw e;
        }
    }

    public void procActivateCard(Market country, boolean isPC, String userID, String ynumber, String pin) throws Exception {
        AndroidElement el;
        boolean pinConfirmed = false;
        try {
            //click activate card button
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "btnActivate", driver);
            el.click();
            Thread.sleep(2000);
            System.out.println("TEST STEP: Limited Home Page - click activate card button");

            //handle y-number entry for PC
            if (isPC) {
                System.out.println("TEST STEP: Enter Y-Number Page - on page");
                System.out.println("TEST DATA: Y-Number is " +ynumber);
                String ynum_digit1 = ynumber.substring(2);
                String ynum_digit2 = ynumber.substring(3);
                String ynum_digit3 = ynumber.substring(4);
                String ynum_digit4 = ynumber.substring(5);
                String ynum_digit5 = ynumber.substring(6);
                String ynum_digit6 = ynumber.substring(7);
                String ynum_digit7 = ynumber.substring(8);
                String ynum_digit8 = ynumber.substring(9);
                String ynum_digit9 = ynumber.substring(10);
                String ynum_digit10 = ynumber.substring(11);
                //enter y-number - note: need to enter each digit separately
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit1", driver);
                el.sendKeys(ynum_digit1);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit2", driver);
                el.sendKeys(ynum_digit2);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit3", driver);
                el.sendKeys(ynum_digit3);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit4", driver);
                el.sendKeys(ynum_digit4);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit5", driver);
                el.sendKeys(ynum_digit5);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit6", driver);
                el.sendKeys(ynum_digit6);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit7", driver);
                el.sendKeys(ynum_digit7);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit8", driver);
                el.sendKeys(ynum_digit8);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit9", driver);
                el.sendKeys(ynum_digit9);
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.YnumberPageElementDict, "yNumDigit10", driver);
                el.sendKeys(ynum_digit10);
                System.out.println("TEST STEP: Enter Y-Number Page - entered Y-number");
                Thread.sleep(2000);
            }


            //SG only: wait till on confirm email page and continue
            if (country.equals(Market.Singapore)) {
              wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.ConfirmEmailPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.ActiveCardSGConfirmEmailPageTitle));
              System.out.println("TEST STEP: Confirm Email Page - on page");
              el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.EmailPageElementDict, "btnNext", driver);
              el.click();
              System.out.println("TEST STEP: Confirm Email Page - click Next button");
              Thread.sleep(2000);
            }

            //wait till on check email page
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CheckSentEmailPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.ActiveCardSGCheckEmailPageTitle));
            System.out.println("TEST STEP: Check Your Email Page - on page");

            //call API to get device ID
            String activateCardEmailURL = api.getActivateCardEmailLink(userID);

            //run deep link command to open activation link
            JavascriptExecutor jsExec = (JavascriptExecutor) driver;
            Map<String, Object> params = new HashMap<>();
            params.put("url", activateCardEmailURL );
            params.put("package", "com.sec.android.app.sbrowser");
            jsExec.executeScript("mobile:deepLink", params);
            System.out.println("TEST STEP: Android Browser - opened and deeplink applied");
            Thread.sleep(3000);

            //handle Android system dialog on browser entry
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.AndroidSystemAlertElementDict, "justOnceOption", driver, true);
            if (el != null) {
                el.click();
                System.out.println("TEST STEP: Android Browser - confirm Android dialog");
            }
            Thread.sleep(3000);

            //TH only - need to set app and card pin
            if (country.equals(Market.Thailand)) {
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.SetAppPinCardPinElementDict, "lblTitle", driver)), "Set App PIN and Card PIN"));
                System.out.println("TEST STEP: Set App PIN and Card PIN page - on page");
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.SetAppPinCardPinElementDict, "btnSetAppPin", driver);
                el.click();
                System.out.println("TEST STEP: Set App PIN and Card PIN page - click set APP PIN button");
                //Set PIN
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CreateConfirmPinPageElementDict, "lblTitle", driver)), "Create an App PIN"));
                for (char i : pin.toCharArray()) {
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.UnlockAppPageElementDict, ("btn" + Character.toString(i)), driver);
                    el.click();
                    System.out.println("TEST STEP: Create App PIN Page - click digit " + Character.toString(i));
                }
                System.out.println("TEST STEP: Create App PIN Page - PIN entered");
                Thread.sleep(3000);
                //Confirm PIN
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CreateConfirmPinPageElementDict, "lblTitle", driver)), "Tyoe Again to Confirm"));
                for (char i : pin.toCharArray()) {
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.UnlockAppPageElementDict, ("btn" + Character.toString(i)), driver);
                    el.click();
                    System.out.println("TEST STEP: Confirm App PIN Page - click digit " + Character.toString(i));
                }
            }
            else if (country.equals(Market.Singapore)) {
                //set PIN
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CreateConfirmPinPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.ActiveCardSGCreatePINPageTitle));
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CreateConfirmPinPageElementDict, "lblSummary", driver)), OnScreenExpectedStringValue.ActiveCardSGCreatePINPageSummary));
                //for each PIN click corresponding number on keypad
                for (char i : pin.toCharArray()) {
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.UnlockAppPageElementDict, ("btn" + Character.toString(i)), driver);
                    el.click();
                    System.out.println("TEST STEP: Create App PIN Page - click digit " + Character.toString(i));
                }
                System.out.println("TEST STEP: Create App PIN Page - PIN entered");
                Thread.sleep(3000);

                //Confirm PIN
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CreateConfirmPinPageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.ActiveCardSGConfirmPINPageTitle));
                for (char i : pin.toCharArray()) {
                    el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.UnlockAppPageElementDict, ("btn" + Character.toString(i)), driver);
                    el.click();
                    System.out.println("TEST STEP: Confirm App PIN Page - click digit " + Character.toString(i));
                }

                System.out.println("TEST STEP: Confirm App PIN Page - PIN entered");
                Thread.sleep(3000);
            }

            //need to handle finger print module
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.FingerPrintPageElementDict, "skipLink", driver, true);
            if (el != null) {
                System.out.println("TEST STEP: Fingerprint Authentication Page - Dialog Displayed");
                el.click();
                System.out.println("TEST STEP: Fingerprint Authentication Page - Skip Now clicked");
                Thread.sleep(3000);
            }

            //TH only - set card PIN
            if (country.equals(Market.Thailand)) {
                wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.SetCardPinElementDict, "lblTitle", driver)), "Step 2: Set a Card PIN"));
                System.out.println("TEST STEP: Set a Card PIN page - on page");
                el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.UnlockAppPageElementDict, "btnSameCode", driver);
                el.click();
                System.out.println("TEST STEP: Set a Card PIN page - click Use the same code button");
            }

        }catch(Exception e) {
            throw e;
        }
    }

    public void procVerifyInHomePage() throws Exception {
        AndroidElement el;
        try {
            //find YouTrip logo from top of home page
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.HomePageElementDict, "logoYouTrip", driver);
            assertTrue(el.isDisplayed());
            System.out.println("TEST STEP: Home Page - on page");
        }catch(Exception e){
            throw e;
        }
    }

    public void procLogout() throws Exception {
        //need to refactor for TH
        AndroidElement el;
        try {
            //click hamburger menu
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "btnMenu", driver);
            el.click();
            System.out.println("TEST STEP: Limited Home page - click hamburger menu");
            Thread.sleep(3000);
            //click settings and logout
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LeftSideMenuElementDict, "optSettings", driver);
            el.click();
            System.out.println("TEST STEP: Hambuger menu - click setting option");
            Thread.sleep(3000);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.SettingPageElementDict, "optLogOut", driver);
            el.click();
            System.out.println("TEST STEP: Setting menu - click logout option");
            Thread.sleep(3000);
            //check if on country page
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "optionCountry", driver);
            assertTrue(el.isDisplayed());
            System.out.println("TEST STEP: Country Page - on page");
        }catch(Exception e){
            throw e;
        }
    }

    public void procSwitchBackToYouTripApp() throws InterruptedException {
        AndroidElement el;
        try {
            driver.activateApp("co.you.youapp.dev");
            System.out.println("TEST STEP: Switch back to YouTrip App");
            Thread.sleep(5000);
        }catch(Exception e){
            throw e;
        }
    }

}
