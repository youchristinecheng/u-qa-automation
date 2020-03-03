import TestBased.*;
import TestBased.TestAccountData.*;
import TestBased.YouTripIosUIElementKey.Market;
import TestBased.YouTripIosUIElementKey.PageKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebDriver;
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

    WebDriver driver;
    YouTripIosUIElementKey UIElementKeyDict;
    YoutripIosSubRoutine subProc;
    WebDriverWait wait;
    TestAccountData testAccountData;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        testAccountData = null;
        UIElementKeyDict = new YouTripIosUIElementKey();
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();
        /*
         * ###### Desired Capabilities for iOS Emulator ######
         */
        /*capabilities.setCapability("deviceName", "iPhone Simulator");
        capabilities.setCapability(CapabilityType.VERSION, "11.4.1");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("platformName", "iOS");*/
        /*
         * ###### Desired Capabilities for iOS Emulator ######
         */

        /*
         * ###### Desired Capabilities for Real iPhone ######
         */
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
        /*
         * ###### Desired Capabilities for Real Device ######
         */

        /*
         * ###### Desired Capabilities for AWS Device Farm ######
         * AWS Device Farm require setup as Default value.
         * No Desired Capabilities configuration should be done.
         * Reminded that this default setting ONLY allow with Appium 1.9.1 with iOS 12 on aws device farm
         */

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
    public void regTC03_selectTH() {
        try {
            subProc.procSelectCountry(Market.Thailand);
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC04_selectSG() {
        try {
            subProc.procSelectCountry(Market.Singapore);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC05_login_new_user_OTP() throws InterruptedException {
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
    public void regTC08_submit_PC_KYC_NRIC() throws InterruptedException {
        IOSElement el;
        testAccountData = new TestAccountData();
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

            testAccountData.market = Market.Singapore;
            testAccountData.mnumber = mnumber;
            testAccountData.mprefix = mprefix;
            testAccountData.emailAddress = email;
            testAccountData.kycStatus = KYCStatus.SUBMIT;
            testAccountData.cardId = null;
            testAccountData.youId = null;
            testAccountData.cardType = CardType.PC;
            testAccountData.cardStatus = null;
            testAccountData.surname = "TESTER";;
            testAccountData.givenName = "AUTO";;
            testAccountData.nameOnCard = testAccountData.surname + " " + testAccountData.givenName;
            testAccountData.nricNumber = subProc.api.util.getNRIC();
            testAccountData.dateOfBirth = dateOfBirthFormatter.format(dateOfBirth);
            testAccountData.addressLine1 = "1";
            testAccountData.addressLine2 = "2";
            testAccountData.postoalCode = "000000";

            System.out.println("PRESET TEST DATA: Mobile Number is " + testAccountData.mprefix + " " + testAccountData.mnumber);
            System.out.println("PRESET TEST DATA: Email address is " + email);
            System.out.println("PRESET TEST DATA: Surname - " + testAccountData.surname + ";Firstname - " + testAccountData.givenName + ";");
            System.out.println("PRESET TEST DATA: NRIC Number: " + testAccountData.nricNumber + "DateOfBirth:" + testAccountData.dateOfBirth);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email,true);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnPCRegister", driver);
            el.click();

            subProc.procSubmitSGPCNRICKYC(false, false, testAccountData.surname, testAccountData.givenName, testAccountData.nameOnCard,
                    testAccountData.dateOfBirth, testAccountData.nricNumber,
                    testAccountData.addressLine1, testAccountData.addressLine2, testAccountData.postoalCode);

//            subProc.api.util.exportAccountTestData(testAccountData);
            Thread.sleep(25000);
            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String kycRefNo = el.getText();
            testAccountData.userId = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");

            subProc.api.util.exportAccountTestData(testAccountData);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

        System.out.println("debug");
    }

    @Test
    public void regTC09_fullreject_and_resubmit_PC_KYC_NRIC() {
        IOSElement el;
        String actualText;
        String kycRefNo;
        try {
            if (testAccountData == null) {
                HashMap<String, String> searchCriteria = new HashMap<>();
                searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
                searchCriteria.put("cardtype", CardType.PC.toString());
                testAccountData = subProc.api.util.searchFromPoolBy(searchCriteria);
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.mprefix, testAccountData.mnumber, testAccountData.emailAddress, false);

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

            subProc.procSubmitSGPCNRICKYC(true, false, testAccountData.surname, testAccountData.givenName, testAccountData.nameOnCard,
                    testAccountData.dateOfBirth, testAccountData.nricNumber,
                    testAccountData.addressLine1, testAccountData.addressLine2, testAccountData.postoalCode);

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
    public void regTC10_partialreject_and_resubmit_PC_KYC_NRIC() throws InterruptedException {
        IOSElement el;
        String actualText;
        String kycRefNo;
        try {
            if (testAccountData == null) {
                HashMap<String, String> searchCriteria = new HashMap<>();
                searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
                searchCriteria.put("cardtype", CardType.PC.toString());
                testAccountData = subProc.api.util.searchFromPoolBy(searchCriteria);
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.mprefix, testAccountData.mnumber, testAccountData.emailAddress, false);

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

            testAccountData.givenName = "Auto YP Edit";
            testAccountData.addressLine2 = "PARTIAL EDIT TEST";

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

            subProc.procSubmitSGPCNRICKYC(false, true, testAccountData.surname, testAccountData.givenName, testAccountData.nameOnCard,
                    testAccountData.dateOfBirth, testAccountData.nricNumber,
                    testAccountData.addressLine1, testAccountData.addressLine2, testAccountData.postoalCode);

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            subProc.api.util.updateData(testAccountData);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC11_approved_PC_KYC_NRIC() throws InterruptedException {

        IOSElement el;
        try {
            if (testAccountData == null) {
                HashMap<String, String> searchCriteria = new HashMap<>();
                searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
                searchCriteria.put("cardtype", CardType.PC.toString());
                testAccountData = subProc.api.util.searchFromPoolBy(searchCriteria);
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.mprefix, testAccountData.mnumber, testAccountData.emailAddress, false);

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

            testAccountData.kycStatus = KYCStatus.CLEAR;
            subProc.api.util.updateData(testAccountData);

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void reg19_submit_NPC_KYC_EmploymentPass() throws InterruptedException {
        IOSElement el;
        testAccountData = new TestAccountData();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
            String mprefix = "123";
            //String mnumber = formatter.format(date);
            String mnumber = "200362103424111"; // Fixed the mobile number for testing purpose to control the account is accessing the same one
            String email = ("qa+sg" + mnumber + "@you.co");

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, -20);
            Date dateOfBirth = c.getTime();
            SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");

            testAccountData.market = Market.Singapore;
            testAccountData.mnumber = mnumber;
            testAccountData.mprefix = mprefix;
            testAccountData.emailAddress = email;
            testAccountData.kycStatus = KYCStatus.SUBMIT;
            testAccountData.cardId = "1863782754944798";
            testAccountData.youId = "Y-8101083375";
            testAccountData.cardType = CardType.NPC;
            testAccountData.cardStatus = CardStatus.INACTIVE;
            testAccountData.surname = "TESTER";
            testAccountData.givenName = "AUTO";
            testAccountData.nameOnCard = null;
            testAccountData.nricNumber = subProc.api.util.getNRIC();
            testAccountData.dateOfBirth = dateOfBirthFormatter.format(dateOfBirth);
            testAccountData.addressLine1 = "1";
            testAccountData.addressLine2 = "2";
            testAccountData.postoalCode = "000000";

            System.out.println("PRESET TEST DATA: Mobile Number is " + testAccountData.mprefix + " " + testAccountData.mnumber);
            System.out.println("PRESET TEST DATA: Email address is " + email);
            System.out.println("PRESET TEST DATA: Surname - " + testAccountData.surname + ";Firstname - " + testAccountData.givenName + ";");
            System.out.println("PRESET TEST DATA: NRIC Number: " + testAccountData.nricNumber + "DateOfBirth:" + testAccountData.dateOfBirth);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email,false);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnNPCRegister", driver);
            el.click();
            Thread.sleep(2000);

            subProc.procSubmitSGNPCEmploymentPassKYC(false, false, testAccountData.youId, testAccountData.surname, testAccountData.givenName,
                    testAccountData.dateOfBirth, testAccountData.nricNumber,
                    testAccountData.addressLine1, testAccountData.addressLine2, testAccountData.postoalCode,
                    null, null, null, null, null);

            Thread.sleep(25000);
            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String kycRefNo = el.getText();
            testAccountData.userId = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");

            subProc.api.util.exportAccountTestData(testAccountData);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

        System.out.println("debug");
    }

    @Test
    public void regTC20_fullreject_and_resubmit_NPC_KYC_EmploymentPass() {
        IOSElement el;
        String actualText;
        String kycRefNo;
        String newSurname = "FRejectTESTER";
        String newGivenName = "FRejectAUTO";
        String newAddressLine1 = "FReject1";
        String newAddressLine2 = "FReject2";
        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -25);
        Date dateOfBirth = c.getTime();
        SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");
        String newDateOfBirth = dateOfBirthFormatter.format(dateOfBirth);

        try {
            if (testAccountData == null) {
                HashMap<String, String> searchCriteria = new HashMap<>();
                searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
                searchCriteria.put("cardtype", CardType.NPC.toString());
                testAccountData = subProc.api.util.searchFromPoolBy(searchCriteria);
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.mprefix, testAccountData.mnumber, testAccountData.emailAddress, false);

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

            subProc.procSubmitSGNPCEmploymentPassKYC(true, false, testAccountData.surname, testAccountData.givenName, testAccountData.nameOnCard,
                    testAccountData.dateOfBirth, testAccountData.nricNumber,
                    testAccountData.addressLine1, testAccountData.addressLine2, testAccountData.postoalCode,
                    newSurname, newGivenName, newDateOfBirth, newAddressLine1, newAddressLine2);

            testAccountData.surname = newSurname;
            testAccountData.givenName = newGivenName;
            testAccountData.dateOfBirth = newDateOfBirth;
            testAccountData.addressLine1 = newAddressLine1;
            testAccountData.addressLine2 = newAddressLine2;

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);
            subProc.api.util.updateData(testAccountData);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC21_partialreject_and_resubmit_NPC_KYC_EmploymentPass() throws InterruptedException {
        IOSElement el;
        String actualText;
        String kycRefNo;
        try {
            if (testAccountData == null) {
                HashMap<String, String> searchCriteria = new HashMap<>();
                searchCriteria.put("kycstatus", KYCStatus.SUBMIT.toString());
                searchCriteria.put("cardtype", CardType.NPC.toString());
                testAccountData = subProc.api.util.searchFromPoolBy(searchCriteria);
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.mprefix, testAccountData.mnumber, testAccountData.emailAddress, false);

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

            testAccountData.givenName = "Auto YP Edit";
            testAccountData.addressLine2 = "PARTIAL EDIT TEST";

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

            subProc.procSubmitSGNPCEmploymentPassKYC(false, true, testAccountData.surname, testAccountData.givenName, testAccountData.nameOnCard,
                    testAccountData.dateOfBirth, testAccountData.nricNumber,
                    testAccountData.addressLine1, testAccountData.addressLine2, testAccountData.postoalCode,
                    null, null, null, null, null);

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            subProc.api.util.updateData(testAccountData);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    /*@Test
    public void regTC10_TopUp() throws InterruptedException {
        IOSElement el;
        try {
            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin("123", "1110110", "", false);

            ((IOSElement)UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "1", driver)).click();
            ((IOSElement)UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "1", driver)).click();
            ((IOSElement)UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "1", driver)).click();
            ((IOSElement)UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "1", driver)).click();

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null)
                el.click();

            ((IOSDriver) driver).findElementByAccessibilityId("icon home topup").click();

            //get and store the KYC reference number
            System.out.println("debug");

        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }*/

    @Test
    public void test(){
        IOSElement el;
        try {
            YouAPI api = new YouAPI();
            api.yp_getKYCdetails("1234387610686357504");
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @AfterMethod
    public void TestMethodTeardown(){
        ((IOSDriver)driver).resetApp();
    }

    @AfterTest
    public void End() {
        //System.out.println(driver.getPageSource());
        ((IOSDriver)driver).closeApp();
        driver.quit();
    }
}