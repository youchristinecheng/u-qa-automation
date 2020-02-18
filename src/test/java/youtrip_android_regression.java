import TestBased.YouAPI;
import TestBased.Utils;
import TestBased.YouTripAndroidUIElementKey;
import TestBased.YouTripAndroidUIElementKey.PageKey;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.lang.*;

import static org.testng.Assert.assertEquals;
public class youtrip_android_regression {

    AndroidDriver driver;
    YouAPI api;
    Utils utils;
    YouTripAndroidUIElementKey UIElementKeyDict;

    public boolean takeScreenshot(final String name) {
        String screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", name)));
    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {

        api = new YouAPI();
        utils = new Utils();
        UIElementKeyDict = new YouTripAndroidUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // setup the capabilities for real device
        //capabilities.setCapability("deviceName", "Nexus 5");
        //capabilities.setCapability(CapabilityType.VERSION, "6.0.1");
        //capabilities.setCapability("automationName", "UiAutomator2");
        //capabilities.setCapability("platformName", "Android");

        // setup the capabilities for Android Emulator
        /*capabilities.setCapability("deviceName", "Android Emulator");
        //capabilities.setCapability(CapabilityType.VERSION, "7.0.0");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appWaitDuration", "40000");
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");



        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "app-sit-release-master-3.3.0.1140.apk");
        capabilities.setCapability("app", app.getAbsolutePath());*/

        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("SETUP: Android Emulator ready");

    }

    @Test
    public void regTC01_selectTH() throws InterruptedException {
        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //wait till on country page and click country select
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblTitle", driver)));
        System.out.println("TEST STEP: Country Page - on page");
        String TC01_TS01 = null;
        takeScreenshot(TC01_TS01);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "optionCountry", driver);
        el.click();
        System.out.println("TEST STEP: Country Page - clicked select country");
        String TC01_TS02 = null;
        takeScreenshot(TC01_TS02);
        //click TH option
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CountrySelectionElementDict, "Thailand", driver);
        el.click();
        System.out.println("TEST STEP: Country Selection Page - click TH button");
        String TC01_TS03 = null;
        takeScreenshot(TC01_TS03);
        //check returned back to country page with TH selected
        assertEquals((UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "countryDesc", driver)).getText(), "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank).");
    }

    @Test
    public void regTC02_selectSG() throws InterruptedException {
        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //wait till on country page and click country select
        wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblTitle", driver)));
        System.out.println("TEST STEP: Country Page - on page");
        String TC02_TS01 = null;
        takeScreenshot(TC02_TS01);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "optionCountry", driver);
        el.click();
        System.out.println("TEST STEP: Country Page - clicked select country");
        String TC02_TS02 = null;
        takeScreenshot(TC02_TS02);
        //click SG option
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CountrySelectionElementDict, "Singapore", driver);
        el.click();
        System.out.println("TEST STEP: Country Selection Page - click SG button");
        String TC02_TS03 = null;
        takeScreenshot(TC02_TS03);
        Thread.sleep(2000);
        //wait until returned back to country page with SG selected
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "countryDesc", driver)), "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));
        //click confirm country
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "btnConfirm", driver);
        el.click();
        System.out.println("TEST STEP: Country Page - continue as SG");
        String TC02_TS04 = null;
        takeScreenshot(TC02_TS04);
        Thread.sleep(2000);
        //wait till on get started page
        wait.until(ExpectedConditions.elementToBeClickable((UIElementKeyDict.getElement(PageKey.GetStartedPageElementDict, "btnGetStarted", driver))));
        System.out.println("TEST STEP: Get Started SG Page - on page");
        String TC02_TS05 = null;
        takeScreenshot(TC02_TS05);
        assertEquals((UIElementKeyDict.getElement(PageKey.GetStartedPageElementDict, "btnGetStarted", driver)).getText(), "Get Started");
    }

    @Test
    public void regTC03_login_new_user_OTP() throws InterruptedException {
        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //click get started button from start screen
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.GetStartedPageElementDict, "btnGetStarted", driver);
        el.click();
        System.out.println("TEST STEP: Get Started SG Page - click Get Started Button");
        String TC03_TS01 = null;
        takeScreenshot(TC03_TS01);
        Thread.sleep(2000);
        //wait till on enter mobile number page
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "lblTitle", driver)), "Enter Mobile Number"));
        System.out.println("TEST STEP: Mobile Number Page - on page");
        String TC03_TS02 = null;
        takeScreenshot(TC03_TS02);

        //TODO separate test data
        String mprefix = "123";
        String mnumber = utils.getTimestamp();
        System.out.println("TEST DATA: Mobile Number is " +mprefix+ " " +mnumber);

        //clear mobile prefix field, enter mobile prefix and number and click next
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "txtPrefix", driver);
        el.clear();
        el.sendKeys(mprefix);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        String TC03_TS03 = null;
        takeScreenshot(TC03_TS03);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "txtPhoneNumber", driver);
        el.click();
        el.sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        String TC03_TS04 = null;
        takeScreenshot(TC03_TS04);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        String TC03_TS05 = null;
        takeScreenshot(TC03_TS05);
        Thread.sleep(2000);

        //wait till on enter SMS page
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "lblTitle", driver)), "Enter Code from SMS"));
        System.out.println("TEST STEP: OTP Page - on page");
        String TC03_TS06 = null;
        takeScreenshot(TC03_TS06);
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
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit1", driver);
        el.sendKeys(otp1);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit2", driver);
        el.sendKeys(otp2);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit3", driver);
        el.sendKeys(otp3);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit4", driver);
        el.sendKeys(otp4);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit5", driver);
        el.sendKeys(otp5);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit6", driver);
        el.sendKeys(otp6);
        System.out.println("TEST STEP: OTP Page - entered OTP");
        String TC03_TS07 = null;
        takeScreenshot(TC03_TS07);
        Thread.sleep(2000);

        //wait till on enter email page
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "lblTitle", driver)), "Enter Email Address"));
        System.out.println("TEST STEP: Enter Email Page - on page");
        String TC03_TS08 = null;
        takeScreenshot(TC03_TS08);

        //TODO separate test data
        String email = ("qa+sg"+mnumber+"@you.co");
        System.out.println("TEST DATA: Email address is " +email);

        //input email and click next button
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "txtUserEmail", driver);
        el.sendKeys(email);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Enter Email Page - click Next button");
        String TC03_TS09 = null;
        takeScreenshot(TC03_TS09);
        Thread.sleep(2000);

        //wait till on welcome page
        wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver)));
        System.out.println("TEST STEP: Welcome Page - on page");
        String TC03_TS10 = null;
        takeScreenshot(TC03_TS10);
        assertEquals((UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver)).getText(), "Welcome");
    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC() throws InterruptedException {
        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //click get youtrip card for free
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnPCRegister", driver);
        el.click();
        System.out.println("TEST STEP: Welcome Page - click Get a YouTrip Card for Free button");
        String TC04_TS01 = null;
        takeScreenshot(TC04_TS01);
        Thread.sleep(2000);

        //wait till on identity verification page
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.IdentityVerificationElementDict, "lblTitle", driver)), "Identity Verification"));
        System.out.println("TEST STEP: Identity Verification Page - on page");
        String TC04_TS02 = null;
        takeScreenshot(TC04_TS02);
        //click Singaporean and PR
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.IdentityVerificationElementDict, "btnSG", driver);
        el.click();
        System.out.println("TEST STEP: Identity Verification Page - click Singaporean/PR button");
        String TC04_TS03 = null;
        takeScreenshot(TC04_TS03);
        Thread.sleep(2000);

        //wait till on Singaporean and PR page
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.PRRegistrationElementDict, "lblTitle", driver)), "Singaporean / PR"));
        System.out.println("TEST STEP: Singaporean/PR Page - on page");
        String TC04_TS04 = null;
        takeScreenshot(TC04_TS04);
        //click submit manually
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PRRegistrationElementDict, "btnManualSubmit", driver);
        el.click();
        System.out.println("TEST STEP: Singaporean/PR Page - click submit manually link");
        String TC04_TS05 = null;
        takeScreenshot(TC04_TS05);
        Thread.sleep(2000);

        //wait till on start of KYC page (just a few steps page)
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.StepsPageElementDict, "lblTitle", driver)), "Just a Few Steps"));
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - on page");
        String TC04_TS06 = null;
        takeScreenshot(TC04_TS06);
        //click start KYC
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.StepsPageElementDict, "btnStart", driver);
        el.click();
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click start now button");
        Thread.sleep(3000);

        //accept the Android camera permission
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CameraAccessAlertElementDict, "btnAccept", driver);
        el.click();
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click allow YouTrip access to camera button");
        String TC04_TS07 = null;
        takeScreenshot(TC04_TS07);
        Thread.sleep(3000);

        //wait till on page and take front NRIC photo
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CameraPageElementDict, "btnShutter", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click take photo button");
        String TC04_TS08 = null;
        takeScreenshot(TC04_TS08);
        Thread.sleep(3000);

        //confirm front NRIC photo
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click all data is readable button");
        String TC04_TS09 = null;
        takeScreenshot(TC04_TS09);
        Thread.sleep(2000);

        //wait till on back of NRIC information pop up appears and confirm it
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.TurnBackPopUpPageElementDict, "lblDesc", driver)), "Back of NRIC"));
        System.out.println("TEST STEP: KYC step 2 back of NRIC - on page");
        String TC04_TS10 = null;
        takeScreenshot(TC04_TS10);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.TurnBackPopUpPageElementDict, "btnOK", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click on got it button from ID reminder dialog");
        String TC04_TS11 = null;
        takeScreenshot(TC04_TS11);
        Thread.sleep(3000);

        //wait till on page and take back NRIC photo
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CameraPageElementDict, "btnShutter", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click take photo button");
        String TC04_TS12 = null;
        takeScreenshot(TC04_TS12);
        Thread.sleep(3000);

        //confirm back NRIC photo
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click all data is readable button");
        String TC04_TS13 = null;
        takeScreenshot(TC04_TS13);
        Thread.sleep(3000);

        //TODO test data to separate
        String surname  = "Tester";
        String firstname = "Auto";

        //wait till on page, enter name and confirm
        //wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NamePageElementDict, "lblTitle", driver), "Full Name (as per NRIC)"));
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "txtSurname", driver);
        el.sendKeys(surname);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "txtGivenName", driver);
        el.sendKeys(firstname);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "btnNext", driver);
        el.click();
        //wait till check and confirm dialog appear and confirm
        Thread.sleep(2000);
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NamePageElementDict, "lblCheckAndConfirmTitle", driver), "Check and Confirm"));
        System.out.println("TEST STEP: Full Name (as per NRIC) page - check and confirm dialog appeared");
        String TC04_TS14 = null;
        takeScreenshot(TC04_TS14);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "btnCheckAndConfirmConfirm", driver);
        el.click();
        System.out.println("TEST STEP: Full Name (as per NRIC) page - on check and confirm dialog click Confirm button");
        String TC04_TS15 = null;
        takeScreenshot(TC04_TS15);
        Thread.sleep(3000);

        //TODO test data to separate
        String newSurname = "Test";

        //wait till on page, clear name on card and enter new name
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NameOnCardElementDict, "lblTitle", driver), "Preferred Name"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NameOnCardElementDict, "txtCardName", driver);
        el.clear();
        el.sendKeys(newSurname+" "+firstname);
        System.out.println("TEST STEP: Preferred Name page - change name on card");
        String TC04_TS16 = null;
        takeScreenshot(TC04_TS16);
        //confirm new name
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NameOnCardElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Preferred Name page - click next button");
        Thread.sleep(3000);

        //TODO test data to separate
        String nricNum  = utils.getNRIC();
        String dob = "01-01-1980";
        String nationality = "Singaporean";

        //wait till on page and enter personal information
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver);
        el.sendKeys(nricNum);
        System.out.println("TEST STEP: Personal Information page - input NRIC number");
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver);
        el.sendKeys(dob);
        System.out.println("TEST STEP: Personal Information page - input Date Of Birth");
        //click nationality option, which opens new page with drop down
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "btnNationality", driver);
        el.click();
        //search for nationality and click the first option
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCNationalityElementDict, "txtInputSearch", driver);
        el.sendKeys(nationality);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCNationalityElementDict, "btnFirstSearchOption", driver);
        el.click();
        System.out.println("TEST STEP: Personal Information page - searched and selected a nationality");
        //select sex and continue
        Thread.sleep(2000);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "btnMale", driver);
        el.click();
        System.out.println("TEST STEP: Personal Information page - click sex as male");
        String TC04_TS17 = null;
        takeScreenshot(TC04_TS17);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Personal Information page - click next button");
        Thread.sleep(3000);

        //TODO test data to separate
        String addLine1  = "Auto Test Address Line 1";
        String addLine2 = "Auto Test Line 2";
        String postalCode = "123456";

        //wait till on page and enter residential address
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "lblTitle", driver), "Residential Address"));
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver);
        el.sendKeys(addLine1);
        System.out.println("TEST STEP: Residential Address page - input address line 1");
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver);
        el.sendKeys(addLine2);
        System.out.println("TEST STEP: Residential Address page - input address line 2");
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver);
        el.sendKeys(postalCode);
        System.out.println("TEST STEP: Residential Address page - input postal code");
        String TC04_TS18 = null;
        takeScreenshot(TC04_TS18);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Residential Address page - click next button");
        Thread.sleep(3000);

        //wait till on page, confirm final steps and submit kyc
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "lblTitle", driver), "Final Step"));
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "btnAgree", driver);
        el.click();
        System.out.println("TEST STEP: Final Step page - click confirm TnC checkbox");
        String TC04_TS19 = null;
        takeScreenshot(TC04_TS19);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "btnSubmit", driver);
        el.click();
        System.out.println("TEST STEP: Final Step page - click submit button");
        Thread.sleep(2000);

        //wait for marketing consent dialog and accept
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.KYCKeepUpdatePopUpElementDict, "lblTitle", driver), "Be the First to Know"));
        System.out.println("TEST STEP: Marketing consent popup - on page");
        String TC04_TS20 = null;
        takeScreenshot(TC04_TS20);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCKeepUpdatePopUpElementDict, "btnAccept", driver);
        el.click();
        System.out.println("TEST STEP: Final Step page - click Keep Me Updated button");
        Thread.sleep(3000);

        //wait for thank you page and confirm
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals((UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)).getText(), "Thank You for Your Application");
        System.out.println("TEST STEP: KYC submitted successfully");
        String TC04_TS21 = null;
        takeScreenshot(TC04_TS21);
    }

    @Test
    public void regTC05_fullreject_PC_KYC_NRIC() throws InterruptedException {
        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //get and store the KYC reference number
        String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "referenceNum", driver)).getText();
        System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);

        //call YP full reject with Ref Number
        api.yp_fullReject(kycRefNo);

        //back to the app - wait for reject to be updated
        Thread.sleep(10000);
        wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
        System.out.println("TEST STEP: KYC rejection received");
        String TC05_TS01 = null;
        takeScreenshot(TC05_TS01);
        assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Attention");
    }

    @Test
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC() throws InterruptedException {

        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
        el.click();
        System.out.println("TEST STEP: Attention page - click retry button");
        String TC06_TS01 = null;
        takeScreenshot(TC06_TS01);
        Thread.sleep(2000);

        //repeat steps from identity verification
        //wait till on identity verification page
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.IdentityVerificationElementDict, "lblTitle", driver)), "Identity Verification"));
        System.out.println("TEST STEP: Identity Verification Page - on page");
        String TC06_TS02 = null;
        takeScreenshot(TC06_TS02);
        //click Singaporean and PR
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.IdentityVerificationElementDict, "btnSG", driver);
        el.click();
        System.out.println("TEST STEP: Identity Verification Page - click Singaporean/PR button");
        Thread.sleep(2000);

        //wait till on Singaporean and PR page
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.PRRegistrationElementDict, "lblTitle", driver)), "Singaporean / PR"));
        System.out.println("TEST STEP: Singaporean/PR Page - on page");
        String TC06_TS03 = null;
        takeScreenshot(TC06_TS03);
        //click submit manually
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PRRegistrationElementDict, "btnManualSubmit", driver);
        el.click();
        System.out.println("TEST STEP: Singaporean/PR Page - click submit manually link");
        Thread.sleep(2000);

        //wait till on start of KYC page (just a few steps page)
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.StepsPageElementDict, "lblTitle", driver)), "Just a Few Steps"));
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - on page");
        String TC06_TS04 = null;
        takeScreenshot(TC06_TS04);
        //click start KYC
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.StepsPageElementDict, "btnStart", driver);
        el.click();
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click start now button");
        Thread.sleep(3000);

        //TODO not needed if already accepted
        //accept the Android camera permission
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CameraAccessAlertElementDict, "btnAccept", driver);
        //el.click();
        //System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click allow YouTrip access to camera button");
        //Thread.sleep(3000);

        //wait till on page and take front NRIC photo
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CameraPageElementDict, "btnShutter", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click take photo button");
        String TC06_TS05 = null;
        takeScreenshot(TC06_TS05);
        Thread.sleep(3000);

        //confirm front NRIC photo
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click all data is readable button");
        String TC06_TS06 = null;
        takeScreenshot(TC06_TS06);
        Thread.sleep(2000);

        //wait till on back of NRIC information pop up appears and confirm it
        wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(PageKey.TurnBackPopUpPageElementDict, "lblDesc", driver)), "Back of NRIC"));
        System.out.println("TEST STEP: KYC step 2 back of NRIC - on page");
        String TC06_TS07 = null;
        takeScreenshot(TC06_TS07);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.TurnBackPopUpPageElementDict, "btnOK", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click on got it button from ID reminder dialog");
        String TC06_TS08 = null;
        takeScreenshot(TC06_TS08);
        Thread.sleep(3000);

        //wait till on page and take back NRIC photo
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.CameraPageElementDict, "btnShutter", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click take photo button");
        String TC06_TS09 = null;
        takeScreenshot(TC06_TS09);
        Thread.sleep(3000);

        //confirm back NRIC photo
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
        el.click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click all data is readable button");
        String TC06_TS10 = null;
        takeScreenshot(TC06_TS10);
        Thread.sleep(3000);

        //TODO test data to separate
        String surname  = "Tester";
        String firstname = "Auto FullReject";

        //wait till on page, enter name and confirm
        //wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NamePageElementDict, "lblTitle", driver), "Full Name (as per NRIC)"));
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "txtGivenName", driver);
        el.clear();
        el.sendKeys(firstname);
        String TC06_TS11 = null;
        takeScreenshot(TC06_TS11);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "btnNext", driver);
        el.click();
        //wait till check and confirm dialog appear and confirm
        Thread.sleep(2000);
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NamePageElementDict, "lblCheckAndConfirmTitle", driver), "Check and Confirm"));
        System.out.println("TEST STEP: Full Name (as per NRIC) page - check and confirm dialog appeared");
        String TC06_TS12 = null;
        takeScreenshot(TC06_TS12);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "btnCheckAndConfirmConfirm", driver);
        el.click();
        System.out.println("TEST STEP: Full Name (as per NRIC) page - on check and confirm dialog click Confirm button");
        Thread.sleep(3000);

        //wait till on page, clear name on card and enter new name
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NameOnCardElementDict, "lblTitle", driver), "Preferred Name"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String TC06_TS13 = null;
        takeScreenshot(TC06_TS13);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NameOnCardElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Preferred Name page - click next button");
        Thread.sleep(3000);

        //wait till on page and click next on personal information
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
        String TC06_TS14 = null;
        takeScreenshot(TC06_TS14);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Personal Information page - click next button");
        Thread.sleep(3000);

        //wait till on page and enter residential address
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "lblTitle", driver), "Residential Address"));
        String TC06_TS15 = null;
        takeScreenshot(TC06_TS15);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Residential Address page - click next button");
        Thread.sleep(3000);

        //wait till on page, confirm final steps and submit kyc
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "lblTitle", driver), "Final Step"));
        String TC06_TS16 = null;
        takeScreenshot(TC06_TS16);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "btnAgree", driver);
        el.click();
        System.out.println("TEST STEP: Final Step page - click confirm TnC checkbox");
        String TC06_TS17 = null;
        takeScreenshot(TC06_TS17);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "btnSubmit", driver);
        el.click();
        System.out.println("TEST STEP: Final Step page - click submit button");
        Thread.sleep(3000);

        //wait for thank you page and confirm
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals((UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)).getText(), "Thank You for Your Application");
        System.out.println("TEST STEP: KYC submitted successfully");
        String TC06_TS18 = null;
        takeScreenshot(TC06_TS18);
    }

    @Test
    public void regTC07_partialreject_PC_KYC_NRIC() throws InterruptedException {

        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //get and store the KYC reference number
        String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "referenceNum", driver)).getText();
        System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);

        //call YP full reject with Ref Number
        api.yp_partialReject(kycRefNo);

        //back to the app - wait for reject to be updated
        Thread.sleep(10000);
        wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
        System.out.println("TEST STEP: KYC rejection received");
        String TC07_TS01 = null;
        takeScreenshot(TC07_TS01);
        assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Attention");
    }

    @Test
    public void regTC08_resubmit_partialreject_PC_KYC_NRIC() throws InterruptedException {

        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
        el.click();
        System.out.println("TEST STEP: Attention page - click retry button");
        String TC08_TS01 = null;
        takeScreenshot(TC08_TS01);
        Thread.sleep(2000);

        //repeat steps from full name page

        //wait till on page, enter name and confirm
        //wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NamePageElementDict, "lblTitle", driver), "Full Name (as per NRIC)"));

        //assert changes to given name from YouPortal
        assertEquals(UIElementKeyDict.getElement(PageKey.NamePageElementDict, "txtGivenName", driver).getText(), "AUTO YP EDIT");
        String TC08_TS02 = null;
        takeScreenshot(TC08_TS02);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "btnNext", driver);
        el.click();
        //wait till check and confirm dialog appear and confirm
        Thread.sleep(2000);
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NamePageElementDict, "lblCheckAndConfirmTitle", driver), "Check and Confirm"));
        System.out.println("TEST STEP: Full Name (as per NRIC) page - check and confirm dialog appeared");
        String TC08_TS03 = null;
        takeScreenshot(TC08_TS03);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NamePageElementDict, "btnCheckAndConfirmConfirm", driver);
        el.click();
        System.out.println("TEST STEP: Full Name (as per NRIC) page - on check and confirm dialog click Confirm button");
        Thread.sleep(3000);

        //wait till on page, clear name on card and enter new name
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.NameOnCardElementDict, "lblTitle", driver), "Preferred Name"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String TC08_TS04 = null;
        takeScreenshot(TC08_TS04);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.NameOnCardElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Preferred Name page - click next button");
        Thread.sleep(3000);

        //wait till on page and click next on personal information
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
        String TC08_TS05 = null;
        takeScreenshot(TC08_TS05);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.PersonalInformationElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Personal Information page - click next button");
        Thread.sleep(3000);

        //wait till on page and enter residential address
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "lblTitle", driver), "Residential Address"));
        //assert changes to given name from YouPortal
        assertEquals(UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver).getText(), "PARTIAL EDIT TEST");
        String TC08_TS06 = null;
        takeScreenshot(TC08_TS06);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.ResidentialAddressElementDict, "btnNext", driver);
        el.click();
        System.out.println("TEST STEP: Residential Address page - click next button");
        Thread.sleep(3000);

        //wait till on page, confirm final steps and submit kyc
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "lblTitle", driver), "Final Step"));
        String TC08_TS07 = null;
        takeScreenshot(TC08_TS07);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "btnAgree", driver);
        el.click();
        System.out.println("TEST STEP: Final Step page - click confirm TnC checkbox");
        String TC08_TS08 = null;
        takeScreenshot(TC08_TS08);
        el = (AndroidElement) UIElementKeyDict.getElement(PageKey.KYCFinalStepElementDict, "btnSubmit", driver);
        el.click();
        System.out.println("TEST STEP: Final Step page - click submit button");
        Thread.sleep(3000);

        //wait for thank you page and confirm
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals((UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)).getText(), "Thank You for Your Application");
        System.out.println("TEST STEP: KYC submitted successfully");
        String TC08_TS09 = null;
        takeScreenshot(TC08_TS09);
    }

    @Test
    public void regTC09_approved_PC_KYC_NRIC() throws InterruptedException {

        AndroidElement el;
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //get and store the KYC reference number
        String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "referenceNum", driver)).getText();
        System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);

        //call YP full reject with Ref Number
        api.yp_approve(kycRefNo);

        //back to the app - wait for reject to be updated
        Thread.sleep(10000);
        wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
        System.out.println("TEST STEP: KYC rejection received");
        String TC09_TS01 = null;
        takeScreenshot(TC09_TS01);
        assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Your Card is On Its Way");

    }

    @AfterClass
    public void end() {
        //driver.quit();
    }
}