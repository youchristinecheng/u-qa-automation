import TestBased.YouTripIosUIElementKey;
import TestBased.YouTripIosUIElementKey.PageKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import kong.unirest.Unirest;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import sun.jvm.hotspot.debugger.Page;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class youtrip_ios_poc {

    IOSDriver driver;
    YouTripIosUIElementKey UIElementKeyDict;

    @BeforeTest
    public void setUp() throws MalformedURLException {

        UIElementKeyDict = new YouTripIosUIElementKey();
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // setup the capabilities for ios emulator
        //capabilities.setCapability("deviceName", "iPhone Simulator");
        //capabilities.setCapability(CapabilityType.VERSION, "11.4.1");
        //capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("platformName", "iOS");

        // setup the capabilities for real iphone
        capabilities.setCapability("deviceName", "YouTech iPhone");
        capabilities.setCapability(CapabilityType.VERSION, "13.1.2");
        capabilities.setCapability("udid", "cbfc3c66708111e5a48ad06f8917b951007bcb9e");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("bundleId", "co.you.youapp");

        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "YOUTrip TH_SIT.ipa");
        capabilities.setCapability("app", app.getAbsolutePath());

        capabilities.setCapability("xcodeOrgId", "2HWNYH89R4");
        capabilities.setCapability("xcodeSigningId", "iPhone Developer");

        //capabilities.setCapability("app", "apps/app-dev_sim-release-1.1.1.730.apk");
        // Set android appPackage desired capability.
        //capabilities.setCapability("appPackage", "co.you.youapp.dev");
        // Set android appActivity desired capability.
        //capabilities.setCapability("appActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        //capabilities.setCapability("intentAction", "android.intent.action.MAIN");
        //capabilities.setCapability("intentCategory", "android.intent.category.LAUNCHER");
        //co.you.youapp.ui.home.HomeActivity
        //co.you.youapp.ui.home.limited.LimitedHomeActivity
        //co.you.youapp.ui.magiclink.MagicLinkActivity
        //co.you.youapp.ui.base.SingleFragmentActivity

        // Created object of RemoteWebDriver will all set capabilities.
        // Set appium server address and port number in URL string.
        // It will launch calculator app in android device.
        driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void regTC01_selectTH() {

        IOSElement el;
        // Handle the ios DEV alert By Force Logout
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("DEV ALERT: dev version alert displayed");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.DevAlertElementDict, "Force Logout", driver);
        el.click();

        // Wait for page change
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
        //Go to country page
        System.out.println("TEST STEP: Country Selection - on page");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "optionCountry", driver);
        el.click();

        System.out.println("TEST STEP: Country Selection Page - click TH button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountrySelectionElementDict, "Thailand", driver);
        el.click();

        System.out.println("TEST STEP: Verified Country Description - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblDesc", driver),
                "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank)."));

        System.out.println("TEST STEP: Country Page - continue as TH");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "btnNext", driver);
        el.click();

        System.out.println("TEST STEP: Get Started TH Page - on page");
        el = (IOSElement) driver.findElementByAccessibilityId("btnGetStarted");
        assertEquals(el.getText(), "Get Started");
    }

    @Test
    public void regTC01_selectSG() {
        IOSElement el;
        // Handle the ios DEV alert By Force Logout
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("DEV ALERT: dev version alert displayed");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.DevAlertElementDict, "Force Logout", driver);
        el.click();

        // Wait for page change
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
        //Go to country page
        System.out.println("TEST STEP: Country Selection - on page");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "optionCountry", driver);
        el.click();

        System.out.println("TEST STEP: Country Selection Page - click SG button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountrySelectionElementDict, "Singapore", driver);
        el.click();

        System.out.println("TEST STEP: Verified Country Description - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblDesc", driver),
                "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));

        System.out.println("TEST STEP: Country Page - continue as SG");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "btnNext", driver);
        el.click();

        System.out.println("TEST STEP: Get Started SG Page - on page");
        el = (IOSElement) driver.findElementByAccessibilityId("btnGetStarted");
        assertEquals(el.getText(), "Get Started");
    }

    @Test
    public void regTC03_login_new_user_OTP() throws InterruptedException {
        IOSElement el;
        // Handle the ios DEV alert By Force Logout
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("DEV ALERT: dev version alert displayed");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.DevAlertElementDict, "Force Logout", driver);
        el.click();

        // Wait for page change
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
        //Go to country page
        System.out.println("TEST STEP: Country Selection - on page");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "optionCountry", driver);
        el.click();

        System.out.println("TEST STEP: Country Selection Page - click SG button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountrySelectionElementDict, "Singapore", driver);
        el.click();

        System.out.println("TEST STEP: Verified Country Description - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblDesc", driver),
                "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));

        System.out.println("TEST STEP: Country Page - continue as SG");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "btnNext", driver);
        el.click();

        System.out.println("TEST STEP: Get Started SG Page - on page");
        el = (IOSElement) driver.findElementByAccessibilityId("btnGetStarted");
        assertEquals(el.getText(), "Get Started");
        el.click();

        System.out.println("TEST STEP: Mobile Number Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "lblTitle", driver), "Enter Mobile Number"));
        // Generate New Mobile Number
        SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        String mprefix = "123";
        String mnumber = formatter.format(date);
        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "txtPrefix", driver);
        el.click();
        // Remove default preset MCC value
        for (int i = 0; i < el.getText().length(); i++) {
            // Remove default preset MCC value
            el.sendKeys("\b");
        }
        // Enter the test data value
        el.sendKeys(mprefix);

        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "txtPhoneNumber", driver);
        el.click();
        el.sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "btnNext", driver);
        el.click();

        Thread.sleep(2000);
        System.out.println("TEST STEP: OTP Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "lblTitle", driver), "Enter Code from SMS"));
        // Get OTP from backdoor and input otp
        Thread.sleep(10000);
        String backdoorOTP = ("http://backdoor.internal.sg.sit.you.co/onboarding/otp/" + mprefix + "/" + mnumber);
        System.out.println("API CALL: " + backdoorOTP);
        String body = Unirest.get(backdoorOTP)
                .asJson()
                .getBody()
                .getObject()
                .getString("password");
        System.out.println("TEST DATA: OTP is " + body);
        // Make use of app text field focus shifting functionality
        System.out.println("TEST STEP: OTP Page - entered OTP");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit1", driver);
        el.sendKeys(body);
        System.out.println("TEST STEP: OTP Page - Debug entered OTP");

        Thread.sleep(1000);
        System.out.println("TEST STEP: Enter Email Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "lblTitle", driver), "Enter Email Address"));
        // Generate new email address
        String email = ("qa+sg" + formatter.format(date) + "@you.co");
        System.out.println("TEST DATA: Email address is " + email);
        System.out.println("TEST STEP: Enter Email Page - entered email");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "UserEmail", driver);
        el.sendKeys(email);
        System.out.println("TEST STEP: Enter Email Page - click Next button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "btnNext", driver);
        el.click();

        System.out.println("TEST STEP: Welcome Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC() throws InterruptedException {
        IOSElement el;
        // Handle the ios DEV alert By Force Logout
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("DEV ALERT: dev version alert displayed");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.DevAlertElementDict, "Force Logout", driver);
        el.click();

        // Wait for page change
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
        //Go to country page
        System.out.println("TEST STEP: Country Selection - on page");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "optionCountry", driver);
        el.click();

        System.out.println("TEST STEP: Country Selection Page - click SG button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountrySelectionElementDict, "Singapore", driver);
        el.click();

        System.out.println("TEST STEP: Verified Country Description - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "lblDesc", driver),
                "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));

        System.out.println("TEST STEP: Country Page - continue as SG");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.CountryPageElementDict, "btnNext", driver);
        el.click();

        System.out.println("TEST STEP: Get Started SG Page - on page");
        el = (IOSElement) driver.findElementByAccessibilityId("btnGetStarted");
        assertEquals(el.getText(), "Get Started");
        el.click();

        System.out.println("TEST STEP: Mobile Number Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "lblTitle", driver), "Enter Mobile Number"));
        // Generate New Mobile Number
        SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        String mprefix = "123";
        String mnumber = formatter.format(date);
        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "txtPrefix", driver);
        el.click();
        // Remove default preset MCC value
        for (int i = 0; i < el.getText().length(); i++) {
            // Remove default preset MCC value
            el.sendKeys("\b");
        }
        // Enter the test data value
        el.sendKeys(mprefix);

        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "txtPhoneNumber", driver);
        el.click();
        el.sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.MobileNumberPageElementDict, "btnNext", driver);
        el.click();

        Thread.sleep(2000);
        System.out.println("TEST STEP: OTP Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "lblTitle", driver), "Enter Code from SMS"));
        // Get OTP from backdoor and input otp
        Thread.sleep(10000);
        String backdoorOTP = ("http://backdoor.internal.sg.sit.you.co/onboarding/otp/" + mprefix + "/" + mnumber);
        System.out.println("API CALL: " + backdoorOTP);
        String body = Unirest.get(backdoorOTP)
                .asJson()
                .getBody()
                .getObject()
                .getString("password");
        System.out.println("TEST DATA: OTP is " + body);
        // Make use of app text field focus shifting functionality
        System.out.println("TEST STEP: OTP Page - entered OTP");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.OTPPageElementDict, "OTPDigit1", driver);
        el.sendKeys(body);
        System.out.println("TEST STEP: OTP Page - Debug entered OTP");

        Thread.sleep(1000);
        System.out.println("TEST STEP: Enter Email Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "lblTitle", driver), "Enter Email Address"));
        // Generate new email address
        String email = ("qa+sg" + formatter.format(date) + "@you.co");
        System.out.println("TEST DATA: Email address is " + email);
        System.out.println("TEST STEP: Enter Email Page - entered email");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "UserEmail", driver);
        el.sendKeys(email);
        System.out.println("TEST STEP: Enter Email Page - click Next button");
        el = (IOSElement) UIElementKeyDict.getElement(PageKey.EmailPageElementDict, "btnNext", driver);
        el.click();

        System.out.println("TEST STEP: Welcome Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
        el = (IOSElement) driver.findElementByAccessibilityId("Get a YouTrip Card for FREE");
        el.click();

        System.out.println("TEST STEP: PC Registration - NRIC");
        el = (IOSElement) driver.findElementByAccessibilityId("For Singaporean / PR");
        el.click();
        // Select on Manual NRIC
        System.out.println("TEST STEP: PC Registration - Select Manual NRIC");
        el = (IOSElement) driver.findElementByAccessibilityId("Submit manually");
        el.click();

        System.out.println("TEST STEP: KYC Start Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElementByAccessibilityId("Just a Few Steps"), "Just a Few Steps"));
        el = (IOSElement) driver.findElementByAccessibilityId("Start Now");
        el.click();
        System.out.println("TEST STEP: KYC Process - Allow Camera access alert");
        Thread.sleep(2000);
        el = (IOSElement) driver.findElementByAccessibilityId("OK");
        el.click();
        System.out.println("TEST STEP: KYC Process - Front of NRIC capture");
        el = (IOSElement) driver.findElementByAccessibilityId("btnShutter");
        el.click();
        System.out.println("TEST STEP: KYC Process - Confirm Front of NRIC capture");
        el = (IOSElement) driver.findElementByAccessibilityId("All Data is Readable");
        el.click();
        System.out.println("TEST STEP: KYC Process - Start Back of NRIC Capture dialog");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElementByAccessibilityId("Now turn to the back of your NRIC and take a photo again."), "Now turn to the back of your NRIC and take a photo again."));
        el = (IOSElement) driver.findElementByAccessibilityId("OK");
        el.click();
        System.out.println("TEST STEP: KYC Process - Back of NRIC Capture");
        el = (IOSElement) driver.findElementByAccessibilityId("btnShutter");
        el.click();
        System.out.println("TEST STEP: KYC Process - Confirm Back of NRIC capture");
        el = (IOSElement) driver.findElementByAccessibilityId("All Data is Readable");
        el.click();

//        el = driver.findElementByXPath()

        System.out.println("debug");
    }
    /*@AfterMethod
    public void TestMethodTeardown(){
        driver.resetApp();
    }*/

    @AfterTest
    public void End() {
        //System.out.println(driver.getPageSource());
        driver.closeApp();
        driver.quit();
    }
}