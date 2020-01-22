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
        capabilities.setCapability("deviceName", "iPhone XS Max");
        capabilities.setCapability(CapabilityType.VERSION, "12.1.1");
        capabilities.setCapability("udid", "00008020-00026C2E3A46002E");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("bundleId", "co.you.youapp");

        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "YouTrip-TH_SIT--pre-3.4.0.1358(616e0ab).ipa");
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
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.DevAlertElementDict, "Force Logout")));
        el.click();

        // Wait for page change
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "HeaderText"))), "Where do you live?"));
        //Go to country page
        System.out.println("TEST STEP: Country Selection - on page");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "CountryText")));
        el.click();

        System.out.println("TEST STEP: Country Selection Page - click TH button");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountrySelectionElementDict, "Thailand")));
        el.click();

        System.out.println("TEST STEP: Verified Country Description - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "CountryDescText"))),
                "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank)."));

        System.out.println("TEST STEP: Country Page - continue as TH");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "ConfirmBtn")));
        el.click();

        System.out.println("TEST STEP: Get Started TH Page - on page");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.GetStartedPageElementDict, "GetStarted")));
        assertEquals(el.getText(), "Get Started");
    }

    @Test
    public void regTC01_selectSG() {
        IOSElement el;
        // Handle the ios DEV alert By Force Logout
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("DEV ALERT: dev version alert displayed");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.DevAlertElementDict, "Force Logout")));
        el.click();

        // Wait for page change
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "HeaderText"))), "Where do you live?"));
        //Go to country page
        System.out.println("TEST STEP: Country Selection - on page");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "CountryText")));
        el.click();

        System.out.println("TEST STEP: Country Selection Page - click SG button");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountrySelectionElementDict, "Singapore")));
        el.click();

        System.out.println("TEST STEP: Verified Country Description - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "CountryDescText"))),
                "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));

        System.out.println("TEST STEP: Country Page - continue as SG");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "ConfirmBtn")));
        el.click();

        System.out.println("TEST STEP: Get Started SG Page - on page");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.GetStartedPageElementDict, "GetStarted")));
        assertEquals(el.getText(), "Get Started");
    }

    @Test
    public void regTC03_login_new_user_OTP() throws InterruptedException {
        IOSElement el;
        // Handle the ios DEV alert By Force Logout
        WebDriverWait wait = new WebDriverWait(driver, 10);
        System.out.println("DEV ALERT: dev version alert displayed");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.DevAlertElementDict, "Force Logout")));
        el.click();

        System.out.println("TEST STEP: Country Selection - on page");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "CountryText")));
        el.click();
        System.out.println("TEST STEP: Country Selection Page - click SG button");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountrySelectionElementDict, "Singapore")));
        el.click();
        System.out.println("TEST STEP: Verified Country Description - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "CountryDescText"))),
                "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));
        System.out.println("TEST STEP: Country Page - continue as SG");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.CountryPageElementDict, "ConfirmBtn")));
        el.click();

        System.out.println("TEST STEP: Get Started SG Page - on page");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.GetStartedPageElementDict, "GetStarted")));
        assertEquals(el.getText(), "Get Started");
        el.click();

        System.out.println("TEST STEP: Mobile Number Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.MobileNumberPageElementDict, "HeaderText"))), "Enter Mobile Number"));
        // Generate New Mobile Number
        SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        String mprefix = "123";
        String mnumber = formatter.format(date);
        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.MobileNumberPageElementDict, "MCC")));
        el.click();
        // Remove default preset MCC value
        for (int i = 0; i < el.getText().length(); i++) {
            // Remove default preset MCC value
            el.sendKeys("\b");
        }
        // Enter the test data value
        el.sendKeys(mprefix);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.MobileNumberPageElementDict, "PhoneNumber")));
        el.click();
        el.sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        el = (IOSElement) driver.findElementByAccessibilityId("Next");
        el.click();

        System.out.println("TEST STEP: OTP Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.OTPPageElementDict, "HeaderText"))), "Enter Code from SMS"));
        // Get OTP from backdoor and input otp
        Thread.sleep(10000);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
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
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.OTPPageElementDict, "OTPDigit1")));
        el.sendKeys(body);
        System.out.println("TEST STEP: OTP Page - Debug entered OTP");

        System.out.println("TEST STEP: Enter Email Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.EmailPageElementDict, "EmailLbl"))), "Enter Email Address"));
        // Generate new email address
        String email = ("qa+sg" + formatter.format(date) + "@you.co");
        System.out.println("TEST DATA: Email address is " + email);
        System.out.println("TEST STEP: Enter Email Page - entered email");
        el = (IOSElement) driver.findElement(By.xpath(UIElementKeyDict.getElementXPath(PageKey.EmailPageElementDict, "Email")));
        el.sendKeys(email);
        System.out.println("TEST STEP: Enter Email Page - click Next button");
        el = (IOSElement) driver.findElementByAccessibilityId("Next");
        el.click();

        System.out.println("TEST STEP: Welcome Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElementByAccessibilityId("Welcome"), "Welcome"));
    }

    @AfterMethod
    public void TestMethodTeardown(){
        driver.resetApp();
    }

    @AfterTest
    public void End() {
        //System.out.println(driver.getPageSource());
        driver.closeApp();
        driver.quit();
    }
}