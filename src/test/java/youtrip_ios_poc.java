import TestBased.*;
import TestBased.TestAccountData.*;
import TestBased.YouTripIosUIElementKey.Market;
import TestBased.YouTripIosUIElementKey.PageKey;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.*;
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
    boolean isContinueTest;
    String defaultAPPPinCode;
    Integer osMainVerInt;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        testAccountData = null;
        UIElementKeyDict = new YouTripIosUIElementKey();
        defaultAPPPinCode = "1111";
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
//        capabilities.setCapability("deviceName", "YouTech QAs iPhone");
//        capabilities.setCapability(CapabilityType.VERSION, "12.1.1");
//        capabilities.setCapability("udid", "00008020-00026C2E3A46002E");
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
        try {
            subProc.procHandleDevAlert();
        }catch(Exception e){
            fail(e.getMessage());
        }
        isContinueTest = true;
        osMainVerInt = -1;
        System.out.println("TEST PARAMETER: iOS Main Version(Default): " + osMainVerInt);
        Map<String, Object> caps = ((IOSDriver) driver).getSessionDetails();
        String verStr = caps.get("sdkVersion").toString();
        osMainVerInt = Integer.parseInt(verStr.substring(0, verStr.indexOf(".")));
        System.out.println("TEST PARAMETER: iOS Main Version(In Session): " + osMainVerInt);
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

            testAccountData.PhoneNumber = mnumber;
            testAccountData.MCC = mprefix;
            testAccountData.Email = email;
            testAccountData.KycStatus = KYCStatus.Submit;
            testAccountData.TestAccountCardType = CardType.PC;
            testAccountData.LastName = "TESTER";;
            testAccountData.FirstName = "AUTO";;
            testAccountData.NameOnCard = testAccountData.LastName + " " + testAccountData.FirstName;
            testAccountData.NricNumber = subProc.api.util.getNRIC();
            testAccountData.Birthdate = dateOfBirthFormatter.format(dateOfBirth);
            testAccountData.AddressLineOne = "1";
            testAccountData.AddressLineTwo = "2";
            testAccountData.PostalCode = "000000";
            testAccountData.Card = null;
            testAccountData.UnderUse = isContinueTest;

            System.out.println("PRESET TEST DATA: Mobile Number is " + testAccountData.MCC + " " + testAccountData.PhoneNumber);
            System.out.println("PRESET TEST DATA: Email address is " + email);
            System.out.println("PRESET TEST DATA: Surname - " + testAccountData.LastName + ";Firstname - " + testAccountData.FirstName + ";");
            System.out.println("PRESET TEST DATA: NRIC Number: " + testAccountData.NricNumber + "DateOfBirth:" + testAccountData.Birthdate);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email,true);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnPCRegister", driver);
            el.click();

            subProc.procSubmitSGPCNRICKYC(osMainVerInt, false, false, testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null, null);

//            subProc.api.util.exportAccountTestData(testAccountData);
            Thread.sleep(25000);
            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String kycRefNo = el.getText();
            testAccountData.Id = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");

            subProc.api.data_createTestUser(testAccountData);
        }catch (Exception e){
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC09_fullreject_and_resubmit_PC_KYC_NRIC() {
        IOSElement el;
        String actualText;
        String kycRefNo;
        // All text input only available for CAPITAL LETTER from frontend
        String newSurname = "FREJECTTESTER".toUpperCase();
        String newGivenName = "FREJECTAUTO".toUpperCase();
        String newNameOnCard = "FRAUTO TEST".toUpperCase();
        String newAddressLine1 = "FREJECT1".toUpperCase();
        String newAddressLine2 = "FREJECT2".toUpperCase();
        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -25);
        Date dateOfBirth = c.getTime();
        SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");
        String newDateOfBirth = dateOfBirthFormatter.format(dateOfBirth);
        try {
            if (testAccountData == null) {
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.PC.toString(), KYCStatus.Submit.toString());
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if (el != null)
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

            subProc.procSubmitSGPCNRICKYC(osMainVerInt, true, false, testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    newSurname, newGivenName, newNameOnCard, newDateOfBirth, newAddressLine1, newAddressLine2);

            testAccountData.LastName = newSurname;
            testAccountData.FirstName = newGivenName;
            testAccountData.NameOnCard = newNameOnCard;
            testAccountData.Birthdate = newDateOfBirth;
            testAccountData.AddressLineOne = newAddressLine1;
            testAccountData.AddressLineTwo = newAddressLine2;
            testAccountData.UnderUse = isContinueTest;

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            subProc.api.data_updateTestUser(testAccountData);
        }catch (Exception e){
            testAccountData = null;
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
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.PC.toString(), KYCStatus.Submit.toString());
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

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

            testAccountData.FirstName = "Auto YP Edit";
            testAccountData.AddressLineTwo = "PARTIAL EDIT TEST";

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


            subProc.procSubmitSGPCNRICKYC(osMainVerInt, false, false, testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null, null);

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);
        }catch (Exception e){
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC11_approved_PC_KYC_NRIC() throws InterruptedException {

        IOSElement el;
        try {
            if (testAccountData == null) {
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.PC.toString(), KYCStatus.Submit.toString());
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

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

            testAccountData.KycStatus = KYCStatus.Clear;
            testAccountData.UnderUse = false;

            subProc.api.data_updateTestUser(testAccountData);
        }catch(Exception e){
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void reg19_submit_NPC_KYC_EmploymentPass() throws InterruptedException {
        IOSElement el;
        testAccountData = new TestAccountData();
        TestCardData testCardData = null;
        try {
            testCardData = subProc.api.data_getTestCardByCardTypeAndStatus(CardType.NPC.toString(), CardStatus.NPCPending.toString());
            testAccountData.Card = testCardData;
            testAccountData.Card.Status = CardStatus.Inactive;

            SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(formatter.format(date));
            String mprefix = "123";
            String mnumber = formatter.format(date);
//            String mnumber = "200373143841380"; // Fixed the mobile number for testing purpose to control the account is accessing the same one
            String email = ("qa+sg" + mnumber + "@you.co");

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.YEAR, -20);
            Date dateOfBirth = c.getTime();
            SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");

            testAccountData.PhoneNumber = mnumber;
            testAccountData.MCC = mprefix;
            testAccountData.Email = email;
            testAccountData.KycStatus = KYCStatus.Submit;
            testAccountData.TestAccountCardType = CardType.NPC;
            testAccountData.LastName = "TESTER";
            testAccountData.FirstName = "AUTO";
            testAccountData.NameOnCard = "";
            testAccountData.NricNumber = subProc.api.util.getNRIC();
            testAccountData.Birthdate = dateOfBirthFormatter.format(dateOfBirth);
            testAccountData.AddressLineOne = "1";
            testAccountData.AddressLineTwo = "2";
            testAccountData.PostalCode = "000000";
            testAccountData.UnderUse = isContinueTest;

            System.out.println("PRESET TEST DATA: Mobile Number is " + testAccountData.MCC + " " + testAccountData.PhoneNumber);
            System.out.println("PRESET TEST DATA: Email address is " + email);
            System.out.println("PRESET TEST DATA: Surname - " + testAccountData.LastName + ";Firstname - " + testAccountData.FirstName + ";");
            System.out.println("PRESET TEST DATA: NRIC Number: " + testAccountData.NricNumber + "DateOfBirth:" + testAccountData.Birthdate);

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email,true);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnNPCRegister", driver);
            el.click();
            Thread.sleep(2000);

            subProc.procSubmitSGNPCEmploymentPassKYC(osMainVerInt, false, false, testAccountData.Card.YouId, testAccountData.LastName, testAccountData.FirstName,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null);

            Thread.sleep(25000);
            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));
            String kycRefNo = ((IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver)).getText();
            testAccountData.Id = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");

            subProc.api.data_createTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            subProc.api.data_bindTestCardToTestUser(testAccountData.Id, testAccountData.Card.Id);
        }catch (Exception e){
            testAccountData = null;
            if (testCardData != null){
                testCardData.Status = CardStatus.UnknownCardStatus;
                testCardData.UnderUse = false;
                subProc.api.data_updateTestCard(testCardData);
            }
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
        // All text input only available for CAPITAL LETTER from frontend
        String newSurname = "FREJECTTESTER".toUpperCase();
        String newGivenName = "FREJECTAUTO".toUpperCase();
        String newAddressLine1 = "FREJECT1".toUpperCase();
        String newAddressLine2 = "FREJECT2".toUpperCase();
        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -25);
        Date dateOfBirth = c.getTime();
        SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");
        String newDateOfBirth = dateOfBirthFormatter.format(dateOfBirth);

        try {
            if (testAccountData == null) {
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.NPC.toString(), KYCStatus.Submit.toString());
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null)
                el.click();

            // Limited Home Page
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver), "Thank You for Your Application"));
            kycRefNo = ((IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver)).getText();

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

            subProc.procSubmitSGNPCEmploymentPassKYC(osMainVerInt, true, false, testAccountData.Card.YouId, testAccountData.LastName, testAccountData.FirstName,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    newSurname, newGivenName, newDateOfBirth, newAddressLine1, newAddressLine2);


            testAccountData.LastName = newSurname;
            testAccountData.FirstName = newGivenName;
            testAccountData.Birthdate = newDateOfBirth;
            testAccountData.AddressLineOne = newAddressLine1;
            testAccountData.AddressLineTwo = newAddressLine2;

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            testAccountData.UnderUse = isContinueTest;
            testAccountData.Card.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch (Exception e){
            testAccountData = null;
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
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.NPC.toString(), KYCStatus.Submit.toString());
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

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

            testAccountData.FirstName = "Auto YP Edit";
            testAccountData.AddressLineTwo = "PARTIAL EDIT TEST";

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

            subProc.procSubmitSGNPCEmploymentPassKYC(osMainVerInt, false, false, testAccountData.Card.YouId, testAccountData.LastName, testAccountData.FirstName,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null);

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            testAccountData.UnderUse = isContinueTest;
            testAccountData.Card.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch (Exception e){
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC22_approved_NPC_KYC_EmploymentPass() throws InterruptedException {

        IOSElement el;
        try {
            if (testAccountData == null) {
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.NPC.toString(), KYCStatus.Submit.toString());
            }

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

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
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Verification Complete");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver).getText(), "My Card Arrived");

            testAccountData.KycStatus = KYCStatus.Clear;
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;

            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC07_Logout() throws InterruptedException {
        IOSElement el;
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);
            System.out.println("TEST STEP: Logout");
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnMenu", driver).click();
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "menuBtnSetting", driver).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.SettingPageElementDict, "btnLogout", driver)));
            UIElementKeyDict.getElement(PageKey.SettingPageElementDict, "btnLogout", driver).click();
            Thread.sleep(3000);
            System.out.println("TEST STEP: Back to Country Selection page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
            Assert.assertTrue(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "optionCountry", driver).isDisplayed());

            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC33_ChangePINFromSetting() throws InterruptedException {
        IOSElement el;
        String newAPPPinCode = "2222";
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            subProc.procChangeAppPinFromHomePage(defaultAPPPinCode, newAPPPinCode);

            System.out.println("TEST STEP: Logout");
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnMenu", driver).click();
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "menuBtnSetting", driver).click();
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.SettingPageElementDict, "btnLogout", driver)));
            UIElementKeyDict.getElement(PageKey.SettingPageElementDict, "btnLogout", driver).click();
            Thread.sleep(3000);
            System.out.println("TEST STEP: Re-Login with new App Pin Code");
            subProc.procLoginToHomePage(Market.Singapore, "123", "1110687", newAPPPinCode);
            subProc.procChangeAppPinFromHomePage(newAPPPinCode, defaultAPPPinCode);

            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC24_AddCardFromTopUpPage() throws InterruptedException {
        IOSElement el;
        String toBeCard = "4000000000003089";
        String defaultCVV = "000";
        String expectedDisplayCard = "Pay by Visa-" + toBeCard.substring(12);

        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 5);
        Date expiryDate = c.getTime();
        SimpleDateFormat expiryDateFormatter = new SimpleDateFormat("MMYY");
        String expiryDateString = expiryDateFormatter.format(expiryDate);
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.NewActive.toString());

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnTopUp", driver).click();
            Thread.sleep(3000);

            System.out.println("TEST STEP: Add Credit Card Pop Up - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "AddCardPopUpLblTitle", driver);
            Assert.assertEquals(el.getText(), "Add a Debit/Credit Card");
            UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "AddCardPopUpBtnGotIt", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Add Card Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Debit/Credit Card");

            System.out.println("TEST STEP: Change Card Page - Enter New Credit Card");
            System.out.println("TEST DATA: Credit Card ID: " + toBeCard);
            System.out.println("TEST DATA: Expiry Date: " + expiryDateString);
            System.out.println("TEST DATA: CVV: " + defaultCVV);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "txtCardNumber", driver);
            el.sendKeys(toBeCard);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "txtExpiryDate", driver);
            el.sendKeys(expiryDateString);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "txtCVV", driver);
            el.sendKeys(defaultCVV);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(3000);

            System.out.println("TEST STEP: 3DS verification Pop Up - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Verification Required");
            UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "btnOK", driver).click();
            Thread.sleep(10000);

            System.out.println("TEST STEP: 3DS verification Stub Page - on page");
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnPass", driver)));
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnFail", driver)));
            UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnPass", driver).click();
            Thread.sleep(10000);

            System.out.println("TEST STEP: Back to TopUp Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "lblAmtTitle", driver);
            Assert.assertEquals(el.getText(), "Amount (SGD)");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "lblPayBy", driver);
            Assert.assertEquals(el.getText(), expectedDisplayCard);
            UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "btnClose", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Card Lock Page - on page");
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCard", driver).click();
            Thread.sleep(1000);;
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).isDisplayed());
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Order Replacement CardP age - Verify Card information");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblCreditCardNumber", driver);
            expectedDisplayCard = "Visa-" + toBeCard.substring(12);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblCreditCardNumber", driver);
            Assert.assertEquals(el.getText(), expectedDisplayCard);

            testAccountData.UnderUse = false;
            testAccountData.Card.Status = CardStatus.Active;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);

        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.Status = CardStatus.UnknownCardStatus;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC25_UpdateCardFromTopUpPage() throws InterruptedException {
        IOSElement el;
        String currentCard = "";
        String toBeCard = "";
        String defaultCVV = "000";
        String expectedDisplayCard = "";

        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 5);
        Date expiryDate = c.getTime();
        SimpleDateFormat expiryDateFormatter = new SimpleDateFormat("MMYY");
        String expiryDateString = expiryDateFormatter.format(expiryDate);
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnTopUp", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: TopUp Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "lblAmtTitle", driver);
            Assert.assertEquals(el.getText(), "Amount (SGD)");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "lblPayBy", driver);
            currentCard = el.getText();
            toBeCard = (currentCard.equals("Pay by Visa-3089"))? "4000000000003055" : "4000000000003089";
            expectedDisplayCard = "Pay by Visa-" + toBeCard.substring(12);

            UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "btnChangeCard", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Change Card Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Debit/Credit Card");

            System.out.println("TEST STEP: Change Card Page - Enter New Credit Card");
            System.out.println("TEST DATA: Credit Card ID: " + toBeCard);
            System.out.println("TEST DATA: Expiry Date: " + expiryDateString);
            System.out.println("TEST DATA: CVV: " + defaultCVV);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "txtCardNumber", driver);
            el.sendKeys(toBeCard);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "txtExpiryDate", driver);
            el.sendKeys(expiryDateString);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "txtCVV", driver);
            el.sendKeys(defaultCVV);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardPageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(3000);

            System.out.println("TEST STEP: 3DS verification Pop Up - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Verification Required");
            UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "btnOK", driver).click();
            Thread.sleep(10000);

            System.out.println("TEST STEP: 3DS verification Stub Page - on page");
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnPass", driver)));
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnFail", driver)));
            UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnPass", driver).click();
            Thread.sleep(15000);

            System.out.println("TEST STEP: Back to TopUp Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "lblAmtTitle", driver);
            Assert.assertEquals(el.getText(), "Amount (SGD)");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "lblPayBy", driver);
            Assert.assertEquals(el.getText(), expectedDisplayCard);

            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC25_UpdateCardFromOrderCardReplacePage() throws InterruptedException {
        IOSElement el;
        String currentCard = "";
        String toBeCard = "";
        String defaultCVV = "000";
        String expectedDisplayCard = "";

        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 5);
        Date expiryDate = c.getTime();
        SimpleDateFormat expiryDateFormatter = new SimpleDateFormat("MMYY");
        String expiryDateString = expiryDateFormatter.format(expiryDate);
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCard", driver).click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Card Lock Page - on page");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).isDisplayed());
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).isDisplayed());
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Order Replacement CardP age - on page");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblTitle", driver).getText(), "Order Replacement Card");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblCreditCardNumber", driver);
            currentCard = el.getText();
            toBeCard = (currentCard.equals("Visa-3089"))? "4000000000003055" : "4000000000003089";
            expectedDisplayCard = "Visa-" + toBeCard.substring(12);


            UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "btnChangeCard", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Change Card Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardChangeCardPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Debit/Credit Card");

            System.out.println("TEST STEP: Change Card Page - Enter New Credit Card");
            System.out.println("TEST DATA: Credit Card ID: " + toBeCard);
            System.out.println("TEST DATA: Expiry Date: " + expiryDateString);
            System.out.println("TEST DATA: CVV: " + defaultCVV);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardChangeCardPageElementDict, "txtCardNumber", driver);
            el.sendKeys(toBeCard);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardChangeCardPageElementDict, "txtExpiryDate", driver);
            el.sendKeys(expiryDateString);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardChangeCardPageElementDict, "txtCVV", driver);
            el.sendKeys(defaultCVV);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardChangeCardPageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(3000);

            System.out.println("TEST STEP: 3DS verification Pop Up - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Verification Required");
            UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "btnOK", driver).click();
            Thread.sleep(10000);

            System.out.println("TEST STEP: 3DS verification Stub Page - on page");
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnPass", driver)));
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnFail", driver)));
            UIElementKeyDict.getElement(PageKey.ChangeCardVerificationPopUpPageElementDict, "3dsStubBtnPass", driver).click();
            Thread.sleep(15000);

            System.out.println("TEST STEP: Order Replacement CardP age - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardChangeCardPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Debit/Credit Card");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardChangeCardPageElementDict, "lblCreditCardNumber", driver);
            Assert.assertEquals(el.getText(), expectedDisplayCard);

            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC26_TopUpSuccess() throws InterruptedException {
        IOSElement el;
        List<WebElement> balanceList;
        List<WebElement> activityList;
        double actualBalance = 0.0;
        double expectedBalance = 0.0;
        double topUpAmt = 20.00;

        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            System.out.println("TEST STEP: Home Page - Get Current SGD Balance");
            balanceList = UIElementKeyDict.getHorizontalBalanceBlockList(driver);
            for(int i = 1; i < balanceList.size(); i++){
                if(balanceList.get(i).getText().equals("SGD")){
                    actualBalance = Double.parseDouble(balanceList.get(i + 1).getText());
                    expectedBalance = actualBalance + topUpAmt;
                    System.out.println("TEST DATA: Home Page - Current SGD Balance: " + String.valueOf(actualBalance));
                    System.out.println("TEST DATA: Home Page - Expect SGD Balance After Top-up: " + String.valueOf(expectedBalance));
                    break;
                }
            }

            System.out.println("TEST STEP: TopUp Page - on page");
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnTopUp", driver).click();
            Thread.sleep(2000);
            UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "txtAmt", driver).sendKeys(String.valueOf(topUpAmt));
            el = (IOSElement)UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "sliderTopUp", driver);
            el.click();
            Rectangle eleRect = el.getRect();

            Map<String, Object> params = new HashMap<>();
            params.put("duration", 1.0);
            params.put("fromX", eleRect.x);
            params.put("fromY", eleRect.x);
            params.put("toX", eleRect.x + eleRect.width);
            params.put("toY", eleRect.x);
            params.put("element", el.getId());
            jsExec.executeScript("mobile: dragFromToForDuration", params);
            Thread.sleep(20000);

            System.out.println("TEST STEP: TopUp PopUp - TopUp Success");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "lblTopUpSuccessPopUpDesc", driver), "Top Up is Successful"));
            el = (IOSElement)UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "btnTopUpSuccessPopUpOk", driver);
            el.click();

            Thread.sleep(2000);
            subProc.procVerifyInHomePage(Market.Singapore);

            balanceList = UIElementKeyDict.getHorizontalBalanceBlockList(driver);
            // Verify SGD is move to the left most after Top Up
            Assert.assertEquals(balanceList.get(1).getText(), "SGD");
            actualBalance = Double.parseDouble(balanceList.get(2).getText());
            // Verify SGD balance is updated by adding 20
            Assert.assertEquals(expectedBalance, actualBalance);

            activityList = UIElementKeyDict.getRecentActivityBlockList(driver);
            Assert.assertEquals(activityList.get(0).getText(), "+ " + String.format("%.2f", topUpAmt) + " SGD");
            Assert.assertEquals(activityList.get(2).getText(), "Top Up");
            Assert.assertEquals(activityList.get(1).getText(), "1 min");


            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }



    @Test
    public void regTC41_LockCard() throws InterruptedException {
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());

            subProc.procLoginToHomePage(Market.Singapore, "123", "1110687", defaultAPPPinCode);
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCard", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Card Lock Page - on page");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).isDisplayed());
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "lblOrderCardTitle", driver).getText(), "Replacement Card");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "lblOrderCardDesc", driver).getText(), "Card lost or stolen?\nGet a replacement card for S$10.00.");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).isDisplayed());

            System.out.println("TEST STEP: Card Lock Page - LockCard");
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).click();
            Thread.sleep(3000);
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnClose", driver).click();

            subProc.procVerifyInHomePage(Market.Singapore);
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.HomePageElementDict, "lblCardStatus", driver).getText(), "Your Card is Locked");

            //TODO: Checking FIS card status  eqaul to card blocked by user via API Calls

            System.out.println("TEST STEP: Home Page - Check Main Buttons Disabled");
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnTopUp", driver).click();
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.HomePageElementDict, "txtCardLockedPopUpTitle", driver).getText(), "Your Card is Locked");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.HomePageElementDict, "txtCardLockedPopUpDesc", driver).getText(), "Tap \"Card\" button to unlock your card first.");
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCardLockedPopUpOK", driver).click();

            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnExchange", driver).click();
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.HomePageElementDict, "txtCardLockedPopUpTitle", driver).getText(), "Your Card is Locked");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.HomePageElementDict, "txtCardLockedPopUpDesc", driver).getText(), "Tap \"Card\" button to unlock your card first.");
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCardLockedPopUpOK", driver).click();

            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC43_checkOrderReplacementCardPage() throws InterruptedException {
        IOSElement el;
        try {
            testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());

            subProc.procLoginToHomePage(Market.Singapore, "123", "1110687", defaultAPPPinCode);
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCard", driver).click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Card Lock Page - on page");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).isDisplayed());
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "lblOrderCardTitle", driver).getText(), "Replacement Card");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "lblOrderCardDesc", driver).getText(), "Card lost or stolen?\nGet a replacement card for S$10.00.");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).isDisplayed());
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Order Replacement CardP age - on page");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblTitle", driver).getText(), "Order Replacement Card");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblCurrencySign", driver).getText(), "S$");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblFeeAmt", driver).getText(), "10.00");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "txtAddress", driver).getText(), "Q\nA\n000000");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblImportantNote", driver).getText(), "Important: Your original YouTrip card will be suspended once you place the order.");
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "btnChangeCreditCard", driver)));
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "sliderOrderReplacementCard", driver)));

            System.out.println("TEST STEP: Order Replacement CardP age - exit");
            UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "btnClose", driver).click();

            System.out.println("TEST STEP: Card Lock Page - on page and exit");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).isDisplayed());
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnClose", driver).click();

            subProc.procVerifyInHomePage(Market.Singapore);

            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
        }catch(Exception e){
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            subProc.api.data_updateTestCard(testAccountData.Card);
            e.printStackTrace();
            fail();
        }
    }

    /*@Test
    public void test(){
        IOSElement el;
        try {
            String mprefix = "65";
            String mnumber = "200318102902351";
            System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
            System.out.println("TEST DATA: Email address is \"No EMail\"");
            subProc.api.setBackDoorEndPoint("http://uoy.backdoor.dev.you.co/", true, "qa", "youtrip1@3");

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, "", false);
            Thread.sleep(500);

            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null)
                el.click();
            subProc.procEnterAPPPinCode("1111");

            Thread.sleep(2000);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null) {
                el.click();
                Thread.sleep(2000);
            }

            System.out.println("Entered Home Page");
            Set<String> contexts = ((IOSDriver)driver).getContextHandles();
            for (String context : contexts) {
                System.out.println(context);
            }

            System.out.println("Debug");
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }*/

    @AfterMethod
    public void TestMethodTeardown(){
        /*((IOSDriver)driver).resetApp();
        try {
            subProc.procHandleDevAlert();
        }catch(Exception e){
            fail(e.getMessage());
        }*/
    }

    @AfterTest
    public void End() {
        //System.out.println(driver.getPageSource());
        ((IOSDriver)driver).closeApp();
        driver.quit();
    }
}