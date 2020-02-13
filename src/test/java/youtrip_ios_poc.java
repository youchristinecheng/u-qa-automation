import TestBased.TestAccountData;
import TestBased.TestAccountData.*;
import TestBased.Utils;
import TestBased.YoutripIosSubRoutine;
import TestBased.YouTripIosUIElementKey;
import TestBased.YouTripIosUIElementKey.Market;
import TestBased.YouTripIosUIElementKey.PageKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import kong.unirest.Unirest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.rmi.CORBA.Util;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.*;

public class youtrip_ios_poc {

    WebDriver driver;
    YouTripIosUIElementKey UIElementKeyDict;
    YoutripIosSubRoutine subProc;
    WebDriverWait wait;

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
        subProc =  new YoutripIosSubRoutine(UIElementKeyDict, driver);
        wait = subProc.getDriverWait();
    }

    @Test
    public void regTC01_selectTH() {
        try {
            subProc.procSelectCountry(Market.Thailand);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC01_selectSG() {
        try {
            subProc.procSelectCountry(Market.Singapore);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC03_login_new_user_OTP() throws InterruptedException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
            String mprefix = "123";
            String mnumber = formatter.format(date);
            String email = ("qa+sg" + mnumber + "@you.co");
            System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
            System.out.println("TEST DATA: Email address is " + email);
            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email, true);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC() throws InterruptedException {
        IOSElement el;
        TestAccountData newUserData = new TestAccountData();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
            String mprefix = "123";
            String mnumber = formatter.format(date);
            String email = ("qa+sg" + mnumber + "@you.co");

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, -20);
            Date dateOfBirth = c.getTime();
            SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");

            newUserData.market = Market.Singapore;
            newUserData.mnumber = mnumber;
            newUserData.mprefix = mprefix;
            newUserData.emailAddress = email;
            newUserData.kycStatus = KYCStatus.SUBMIT;
            newUserData.cardId = null;
            newUserData.youId = null;
            newUserData.cardStatus = null;
            newUserData.surname = "TESTER";;
            newUserData.givenName = "AUTO";;
            newUserData.nameOnCard = newUserData.surname + " " + newUserData.givenName;
            newUserData.nricNumber = subProc.api.util.getNRIC();
            newUserData.dateOfBirth = dateOfBirthFormatter.format(dateOfBirth);
            newUserData.addressLine1 = "1";
            newUserData.addressLine2 = "2";
            newUserData.postoalCode = "000000";

            System.out.println("PRESET TEST DATA: Mobile Number is " + newUserData.mprefix + " " + newUserData.mnumber);
            System.out.println("PRESET TEST DATA: Email address is " + email);
            System.out.println("PRESET TEST DATA: Surname - " + newUserData.surname + ";Firstname - " + newUserData.givenName + ";");
            System.out.println("PRESET TEST DATA: NRIC Number: " + newUserData.nricNumber + "DateOfBirth:" + newUserData.dateOfBirth);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email,true);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnPCRegister", driver);
            el.click();

            subProc.procSubmitSGPCNRICKYC(false, false, newUserData.surname, newUserData.givenName, newUserData.nameOnCard,
                    newUserData.dateOfBirth, newUserData.nricNumber,
                    newUserData.addressLine1, newUserData.addressLine2, newUserData.postoalCode);

            subProc.api.util.exportAccountTestData(newUserData);
            Thread.sleep(25000);
            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

        System.out.println("debug");
    }

    @Test
    public void regTC05_fullreject_and_resubmit_PC_KYC_NRIC() {
        IOSElement el;
        String actualText;
        String kycRefNo;
        try {
            HashMap<String, String> searchCriteria = new HashMap<>();
            searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
            TestAccountData loadedData = subProc.api.util.searchFromPoolBy(searchCriteria);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(loadedData.mprefix, loadedData.mnumber, loadedData.emailAddress, false);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null)
                el.click();

            // Limited Home Page
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            kycRefNo = el.getText();

            subProc.api.yp_fullReject(kycRefNo);
            Thread.sleep(15000);
            System.out.println("TEST STEP: KYC rejection received");

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            actualText = el.getText();
            assertEquals("Attention", actualText);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblKycResultDesc", driver);
            assertTrue(el.isDisplayed());
            actualText = el.getText();
            assertEquals("Sorry, we were unable to process your application due to the following reasons: \n\n" + subProc.api.getKycRejectReason() + "\n\nYou’ll also be required to take photos of your ID again to verify your identity.", actualText);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(2000);

            subProc.procSubmitSGPCNRICKYC(true, false, loadedData.surname, loadedData.givenName, loadedData.nameOnCard,
                    loadedData.dateOfBirth, loadedData.nricNumber,
                    loadedData.addressLine1, loadedData.addressLine2, loadedData.postoalCode);

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC07_partialreject_and_resubmit_PC_KYC_NRIC() throws InterruptedException {
        IOSElement el;
        String actualText;
        String kycRefNo;
        try {
            HashMap<String, String> searchCriteria = new HashMap<>();
            searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
            TestAccountData loadedData = subProc.api.util.searchFromPoolBy(searchCriteria);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(loadedData.mprefix, loadedData.mnumber, loadedData.emailAddress, false);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null)
                el.click();

            // Limited Home Page
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            kycRefNo = el.getText();

            subProc.api.yp_partialReject(kycRefNo);
            Thread.sleep(15000);
            System.out.println("TEST STEP: KYC Partial rejection received");

            loadedData.givenName = "Auto YP Edit";
            loadedData.addressLine2 = "PARTIAL EDIT TEST";

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            actualText = el.getText();
            assertEquals("Attention", actualText);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblKycResultDesc", driver);
            assertTrue(el.isDisplayed());
            actualText = el.getText();
            assertEquals("We have noticed errors in your application and have edited it for you. These are the following errors:\n\n" + subProc.api.getKycRejectReason() + "\n\nPlease check all the data to make sure they are accurate and submit again.", actualText);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(2000);

            subProc.procSubmitSGPCNRICKYC(false, true, loadedData.surname, loadedData.givenName, loadedData.nameOnCard,
                    loadedData.dateOfBirth, loadedData.nricNumber,
                    loadedData.addressLine1, loadedData.addressLine2, loadedData.postoalCode);

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            subProc.api.util.updateData(loadedData);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC09_approved_PC_KYC_NRIC() throws InterruptedException {

        IOSElement el;
        try {
            HashMap<String, String> searchCriteria = new HashMap<>();
            searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
            TestAccountData loadedData = subProc.api.util.searchFromPoolBy(searchCriteria);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(loadedData.mprefix, loadedData.mnumber, loadedData.emailAddress, false);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null)
                el.click();

            //get and store the KYC reference number
            String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver)).getText();
            System.out.println("TEST DATA: KYC submission reference number is " + kycRefNo);

            //call YP full reject with Ref Number
            subProc.api.yp_approve(kycRefNo);

            //back to the app - wait for reject to be updated
            Thread.sleep(10000);
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
            System.out.println("TEST STEP: KYC approval received");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Your Card is On Its Way");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver).getText(), "My Card Arrived");

            loadedData.kycStatus = KYCStatus.CLEAR;
            subProc.api.util.updateData(loadedData);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    /*@Test
    public void test(){
        IOSElement el;
        try {
            HashMap<String, String> searchCriteria = new HashMap<>();
            searchCriteria.put("kycstatus", KYCStatus.CLEAR.toString());
            TestAccountData loadedData = subProc.api.util.searchFromPoolBy(searchCriteria);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(loadedData.mprefix, loadedData.mnumber, loadedData.emailAddress, false);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null)
                el.click();

            //get and store the KYC reference number
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
            System.out.println("TEST STEP: KYC approval received");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Your Card is On Its Way");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver).getText(), "My Card Arrived");

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(2000);
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Your Card is On Its Way");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "txtYouIdDigit1", driver);
            el.sendKeys(loadedData.youId);


            System.out.println("debug");
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }*/

    /*@AfterMethod
    public void TestMethodTeardown(){
        driver.resetApp();
    }*/

    @AfterTest
    public void End() {
        //System.out.println(driver.getPageSource());
        ((IOSDriver)driver).closeApp();
        driver.quit();
    }
}