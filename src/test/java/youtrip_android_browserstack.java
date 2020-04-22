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

    public static String userName = "danielchan2";
    public static String accessKey = "pWT7MCmWvUfYUsMNoQ9y";

    @BeforeTest (alwaysRun = true)
    public void setUp() throws MalformedURLException {

        utils = new Utils();
        testAccountData = null;
        UIElementKeyDict = new YouTripAndroidUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        /*
         * ###### Desired Capabilities for BrowserStack ######
         */
        //ANDROID 7
        //capabilities.setCapability("device", "Samsung Galaxy S8");
        //capabilities.setCapability("os_version", "7.0");

        //ANDROID 7.1
        //capabilities.setCapability("device", "Google Pixel");
        //capabilities.setCapability("device", "Samsung Galaxy Note 8");
        //capabilities.setCapability("os_version", "7.1");

        //ANDROID 8
        //capabilities.setCapability("device", "Google Pixel 2");
        //capabilities.setCapability("device", "Samsung Galaxy S9");
        //capabilities.setCapability("os_version", "8.0");

        //ANDROID 8.1
        //capabilities.setCapability("device", "Samsung Galaxy Note 9");
        //capabilities.setCapability("os_version", "8.1");

        //ANDROID 9
        //capabilities.setCapability("device", "Samsung Galaxy Note 10");
        //capabilities.setCapability("device", "Samsung Galaxy S10");
        //capabilities.setCapability("device", "Xiaomi Redmi Note 8");
        //capabilities.setCapability("device", "OnePlus 7");
        //capabilities.setCapability("os_version", "9.0");

        //ANDROID 10
        //capabilities.setCapability("device", "Samsung Galaxy S20");
        //capabilities.setCapability("device", "OnePlus 7T");
        capabilities.setCapability("device", "Google Pixel 3");
        capabilities.setCapability("os_version", "10.0");


        capabilities.setCapability("project", "Test Project");
        capabilities.setCapability("build", "App 3.6.0 - RC1 - 1183 ");
        capabilities.setCapability("name", "Android Regression Suite");
        capabilities.setCapability("app", "bs://3f8448d822df61c77ffa13b38bcb9fc189eb0b00");
        capabilities.setCapability("browserstack.appium_version", "1.17.0");
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
        System.out.println("TEST CASE: Start \"regTC01_selectTH\"");
        try {
            //select TH from country selection
            subProc.procSelectCountry(Market.Thailand);
            System.out.println("TEST CASE: Finish \"regTC01_selectTH\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC02_selectSG() {
        System.out.println("TEST CASE: Start \"regTC02_selectSG\"");
        try {
            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            System.out.println("TEST CASE: Finish \"regTC02_selectSG\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC03_login_new_user_OTP() {
        System.out.println("TEST CASE: Start \"regTC03_login_new_user_OTP\"");
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
            System.out.println("TEST CASE: Finish \"regTC03_login_new_user_OTP\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC04_submit_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC04_submit_PC_KYC_NRIC\"");
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
            System.out.println("TEST CASE: Finish \"regTC04_submit_PC_KYC_NRIC\"");
        } catch (Exception e) {
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC05_fullreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC05_fullreject_PC_KYC_NRIC\"");
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
            System.out.println("TEST CASE: Finish \"regTC05_fullreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC06_resubmit_fullreject_PC_KYC_NRIC\"");
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
            System.out.println("TEST CASE: Finish \"regTC06_resubmit_fullreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC07_partialreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC07_partialreject_PC_KYC_NRIC\"");
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
            System.out.println("TEST CASE: Finish \"regTC07_partialreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC08_resubmit_partialreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC08_resubmit_partialreject_PC_KYC_NRIC\"");
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
            System.out.println("TEST CASE: Finish \"regTC08_resubmit_partialreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC09_approved_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC09_approved_PC_KYC_NRIC\"");
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
            System.out.println("TEST CASE: Finish \"regTC09_approved_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test"})
    public void regTC10_logout_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC10_logout_PC_KYC_NRIC\"");
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
            System.out.println("TEST CASE: Finish \"regTC10_logout_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test" })
    public void regTC11_submit_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC11_submit_NPC_KYC_forgeiner\"");
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
            System.out.println("TEST CASE: Finish \"regTC11_submit_NPC_KYC_forgeiner\"");

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
        System.out.println("TEST CASE: Start \"regTC12_fullreject_NPC_KYC_forgeiner\"");
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
            System.out.println("TEST CASE: Finish \"regTC12_fullreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC13_resubmit_fullreject_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC13_resubmit_fullreject_NPC_KYC_forgeiner\"");
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
            System.out.println("TEST CASE: Finish \"regTC13_resubmit_fullreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC14_partialreject_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC14_partialreject_NPC_KYC_forgeiner\"");
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
            System.out.println("TEST CASE: Finish \"regTC14_partialreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC15_resubmit_partialreject_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC15_resubmit_partialreject_NPC_KYC_forgeiner\"");
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
            System.out.println("TEST CASE: Finish \"regTC15_resubmit_partialreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test" })
    public void regTC16_approved_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC16_approved_NPC_KYC_forgeiner\"");
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
            System.out.println("TEST CASE: Finish \"regTC16_approved_NPC_KYC_forgeiner\"");
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
        System.out.println("TEST CASE: Start \"regTC18_logout_NPC_KYC_forgeiner\"");
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
            System.out.println("TEST CASE: Finish \"regTC18_logout_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "sanity_test", "not_ready" })
    public void regTC19_login_existing_user_OTP() {
        System.out.println("TEST CASE: Start \"regTC19_login_existing_user_OTP\"");
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
            System.out.println("TEST CASE: Finish \"regTC19_login_existing_user_OTP\"");
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