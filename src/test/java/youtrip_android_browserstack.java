import TestBased.*;
import TestBased.YouTripAndroidUIElementKey.Market;
import TestBased.YouTripAndroidUIElementKey.PageKey;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class youtrip_android_browserstack {

    AndroidDriver driver;
    Utils utils;
    YouTripAndroidUIElementKey UIElementKeyDict;
    YouTripAndroidSubRoutine subProc;
    WebDriverWait wait;
    TestAccountData testAccountData;
    boolean isContinueTest;

    public static String userName = "youtechyoutech1";
    public static String accessKey = "xe5uxMLBNiz8dxLc3uJg";

    @BeforeTest (alwaysRun = true)
    public void setUp() throws MalformedURLException {

        utils = new Utils();
        testAccountData = null;
        UIElementKeyDict = new YouTripAndroidUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        /*
         * ###### Desired Capabilities for BrowserStack ######
         */
        capabilities.setCapability("device", "Google Pixel 3");
        capabilities.setCapability("os_version", "9.0");
        capabilities.setCapability("project", "Test Project");
        capabilities.setCapability("build", "App 3.5.0 - RC1");
        capabilities.setCapability("name", "Android Regression Suite");
        capabilities.setCapability("app", "bs://25c25122873334c142e7e081869f9d42274115e2");
        capabilities.setCapability("browserstack.appium_version", "1.16.0");
        capabilities.setCapability("browserstack.local", "false");
        capabilities.setCapability("real_mobile", "true");
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        capabilities.setCapability("autoGrantPermissions", "true");

        driver = new AndroidDriver<>(new URL("https://"+userName+":"+accessKey+"@hub-cloud.browserstack.com/wd/hub"), capabilities);

        /*
         * ###### Desired Capabilities for BrowserStack ######
         */

        subProc = new YouTripAndroidSubRoutine(UIElementKeyDict, driver);
        wait = subProc.getDriverWait();
        isContinueTest = true;
        System.out.println("SETUP: Android Emulator ready");

    }

    @Test (groups = { "regression_test"})
    public void regTC01_selectTH() {
        try {
            //select TH from country selection
            subProc.procSelectCountry(Market.Thailand);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC02_selectSG() {
        try {
            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC03_login_new_user_OTP() {
        try {
            String mprefix = "123";
            String mnumber = utils.getTimestamp();
            String email = ("qa+sg" + mnumber + "@you.co");

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(mprefix, mnumber, true);
            //enter email address and continue to welcome page
            subProc.procEnterEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC04_submit_PC_KYC_NRIC(ITestContext context) {
        testAccountData = new TestAccountData();

        try {
            String mprefix = "123";
            String mnumber = utils.getTimestamp();
            String email = ("qa+sg" + mnumber + "@you.co");
            testAccountData.MCC = mprefix;
            testAccountData.PhoneNumber = mnumber;
            testAccountData.Email = email;
            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.TestAccountCardType = TestAccountData.CardType.PC;
            testAccountData.LastName = "AUTO TESTER";
            testAccountData.FirstName = "ANDROID PC";
            testAccountData.NameOnCard = "TESTER ANDROID";
            testAccountData.NricNumber = utils.getNRIC();
            testAccountData.Birthdate = "01-01-1980";
            testAccountData.AddressLineOne = "Auto Test Addresss Line 1";
            testAccountData.AddressLineTwo = "Android Device Line 2";
            testAccountData.PostalCode = "123456";
            testAccountData.Card = null;
            testAccountData.UnderUse = isContinueTest;
            String nationality = "Singaporean";

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(mprefix, mnumber, true);
            //enter email address and continue to welcome page
            subProc.procEnterEmail(email);
            //select PC card
            subProc.procSelectCardType(true);
            //select NRIC method
            subProc.procSelectNRIC("Manual");
            //submit KYC
            subProc.procSubmitSGKYC(false, false, true, true,
                    testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber, nationality,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo,
                    testAccountData.PostalCode);
            //get KYC ID number
            String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "referenceNum", driver)).getText();
            System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);
            //get user ID and store it as attribute and into data service
            String userId = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");
            context.setAttribute("userid", userId);
            testAccountData.Id = userId;
            //store test data
            subProc.api.data_createTestUser(testAccountData);

        } catch (Exception e) {
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC05_fullreject_PC_KYC_NRIC(ITestContext context) {
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //perform full rejection
            subProc.procRejectKYC(true);

            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Reject;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC(ITestContext context) {
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //select NRIC method
            subProc.procSelectNRIC("Manual");
            Thread.sleep(2000);
            //resubmit kyc after full rejection - change surname and address line 1
            testAccountData.LastName = "AUTOMATED TESTER";
            testAccountData.AddressLineOne = "Test Address Line 1";

            subProc.procSubmitSGKYC(true, false, true, true,
                    testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber, "Singaporean",
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode);

            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC07_partialreject_PC_KYC_NRIC(ITestContext context) {
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //perform partial rejection
            subProc.procRejectKYC(false);

            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.PartialReject;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC08_resubmit_partialreject_PC_KYC_NRIC(ITestContext context) {
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);

            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //resubmit kyc after partial rejection - change name on card and dob
            testAccountData.NameOnCard = "AUTOTESTER ANDROID";
            testAccountData.Birthdate = "02-01-1980";

            subProc.procSubmitSGKYC(false, true, true, true,
                    testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber, "Singaporean",
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode);

            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC09_approved_PC_KYC_NRIC(ITestContext context) {
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);

            //perform approval
            subProc.procApproveKYC(true);

            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Clear;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC10_logout_PC_KYC_NRIC(ITestContext context) {
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //logout of account
            subProc.procLogout();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test" })
    public void regTC11_submit_NPC_KYC_forgeiner(ITestContext context) {
        testAccountData = new TestAccountData();
        TestCardData testCardData = null;
        try {
            testCardData = subProc.api.data_getTestCardByCardTypeAndStatus(TestAccountData.CardType.NPC.toString(), TestAccountData.CardStatus.NPCPending.toString());
            testAccountData.Card = testCardData;
            testAccountData.Card.Status = TestAccountData.CardStatus.Inactive;

            String mprefix = "123";
            String mnumber = utils.getTimestamp();
            String email = ("qa+sg" + mnumber + "@you.co");
            testAccountData.MCC = mprefix;
            testAccountData.PhoneNumber = mnumber;
            testAccountData.Email = email;
            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.TestAccountCardType = TestAccountData.CardType.NPC;
            testAccountData.LastName = "AUTO TESTER";
            testAccountData.FirstName = "ANDROID NPC";
            testAccountData.NameOnCard = "TESTER ANDROID";
            testAccountData.NricNumber = "QA" + utils.getNRIC();
            testAccountData.Birthdate = "01-01-1980";
            testAccountData.AddressLineOne = "Auto Test Addresss Line 1";
            testAccountData.AddressLineTwo = "Android Device Line 2";
            testAccountData.PostalCode = "123456";
            testAccountData.UnderUse = isContinueTest;
            String nationality = "Singaporean";

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(mprefix, mnumber, true);
            //enter email address and continue to welcome page
            subProc.procEnterEmail(email);
            //select NPC card
            subProc.procSelectCardType(false);
            //enter Y-number
            subProc.procEnterYNumber(testAccountData.Card.YouId);
            //select Forgeiner method
            subProc.procSelectNonNRIC("Employment Pass");
            //submit KYC
            subProc.procSubmitSGKYC(false, false, false, false,
                    testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber, nationality,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo,
                    testAccountData.PostalCode);

            //get KYC ID number
            String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "referenceNum", driver)).getText();
            System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);
            //get user ID and store it as attribute and into data service
            String userId = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");
            context.setAttribute("userid", userId);
            testAccountData.Id = userId;
            //store test data
            subProc.api.data_updateTestCard(testAccountData.Card);
            subProc.api.data_createTestUser(testAccountData);

        } catch (Exception e) {
            testAccountData = null;
            if (testCardData != null){
              testCardData.Status = TestAccountData.CardStatus.UnknownCardStatus;
              testCardData.UnderUse = false;
              subProc.api.data_updateTestCard(testCardData);
            }
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC12_fullreject_NPC_KYC_forgeiner(ITestContext context) {
        try {

            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //perform full rejection
            subProc.procRejectKYC(true);

            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Reject;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC13_resubmit_fullreject_NPC_KYC_forgeiner(ITestContext context) {
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //select Forgeiner method
            subProc.procSelectNonNRIC("Employment Pass");
            Thread.sleep(2000);
            //resubmit kyc after full rejection - change surname and address line 1
            testAccountData.LastName = "AUTOMATED TESTER";
            testAccountData.AddressLineOne = "Test Address Line 1";

            subProc.procSubmitSGKYC(true, false, false, false,
                    testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber, "Singaporean",
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode);

            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC14_partialreject_NPC_KYC_forgeiner(ITestContext context) {
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //perform partial rejection
            subProc.procRejectKYC(false);

            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.PartialReject;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC15_resubmit_partialreject_NPC_KYC_forgeiner(ITestContext context) {
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);

            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //resubmit kyc after partial rejection - change name on card and dob
            testAccountData.NameOnCard = "AUTOTESTER ANDROID";
            testAccountData.Birthdate = "02-01-1980";
            subProc.procSubmitSGKYC(false, true, false, false,
                    testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber, "Singaporean",
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode);

            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test" })
    public void regTC16_approved_NPC_KYC_forgeiner(ITestContext context) {
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //perform approval
            subProc.procApproveKYC(false);
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Clear;
            testAccountData.UnderUse = isContinueTest;
            subProc.api.data_updateTestUser(testAccountData);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "not_ready" })
    public void regTC17_activatecard_NPC_KYC_forgeiner() {
        //TODO blocked on activation - need magic link api updates
    }

    @Test (groups = { "regression_test"})
    public void regTC18_logout_NPC_KYC_forgeiner(ITestContext context) {

        //TODO once TC17 is enable need to update test to unlock app first then logout
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //logout of account
            subProc.procLogout();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test", "not_ready" })
    public void regTC19_login_existing_user_OTP() {
        AndroidElement el;

        try {
            //using SIT Profile account for testing
            String userID = "1000101";
            String pin = "1111";
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //otp login using existing account
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            //enter PIN
            subProc.procEnterPIN(pin);
            //check if on home page
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.HomePageElementDict, "logoYouTrip", driver);
            assertTrue(el.isDisplayed());
            System.out.println("TEST STEP: Home Page - on page");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

    /*@Test
    public void regTC20_topup_addcard_3DS_notsupported() {

    }

    @Test
    public void regTC21_topup_addcard_3DS_supported_cancel() {

    }

    @Test
    public void regTC22_topup_addcard_3DS_supported_verify_cancelled() {

    }

    @Test
    public void regTC23_topup_addcard_3DS_supported_verify_failed() {

    }

    @Test
    public void regTC24_topup_addcard_3DS_supported_verify_success() {

    }

    @Test
    public void regTC25_topup_updatecard() {

    }

    @Test
    public void regTC26_topup() {

    }*/

    @AfterMethod (alwaysRun = true)
    public void testMethodEnd() {
        driver.resetApp();
        //driver.quit();
    }

    @AfterTest (alwaysRun = true)
    public void end() {
        driver.quit();
    }
}