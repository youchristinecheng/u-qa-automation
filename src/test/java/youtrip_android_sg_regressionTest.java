import TestBased.*;
import TestBased.TestAccountData.Market;
import TestBased.YouTripAndroidUIElementKey.PageKey;
import io.appium.java_client.android.AndroidElement;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class youtrip_android_sg_regressionTest extends android_browserstackTest {

    Utils utils = new Utils();

    @Test
    public void regTC01_selectTH() {
        System.out.println("TEST CASE: Start \"regTC01_selectTH\"");
        try {
            //select TH from country selection
            subProc.procSelectCountry(Market.Thailand);
            System.out.println("TEST CASE: Finish \"regTC01_selectTH\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC01_selectTH\"");
        }
    }

    @Test
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
            System.out.println("TEST CASE: Fail \"regTC02_selectSG\"");
        }
    }

    @Test
    public void regTC03_login_new_user_OTP(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC03_login_new_user_OTP\"");
        try {
            String mprefix = "123";
            String mnumber = utils.getTimestamp();
            String email = ("qa+sg" + mnumber + "@you.co");

            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(mprefix, mnumber, true);
            //enter email address and continue to welcome page
            subProc.procEnterEmail(email);
            //pass data to next test case
            context.setAttribute("mprefix", mprefix);
            context.setAttribute("mnumber", mnumber);
            context.setAttribute("email", email);
            System.out.println("TEST CASE: Finish \"regTC03_login_new_user_OTP\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC03_login_new_user_OTP\"");
        }
    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC04_submit_PC_KYC_NRIC\"");
        testAccountData = new TestAccountData();
        try {
            String mprefix = (String) context.getAttribute("mprefix");
            String mnumber = (String) context.getAttribute("mnumber");
            String email = (String) context.getAttribute("email");
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
            testAccountData.UnderUse = true;
            testAccountData.TestAccountMarket = subProc.getCurrentMarket();
            testAccountData.IsExplorerModeOn = false;
            String nationality = "Singaporean";

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
            //create test user
            subProc.api.data_createTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC04_submit_PC_KYC_NRIC\"");
        } catch (Exception e) {
            testAccountData = null;
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC04_submit_PC_KYC_NRIC\"");
        }
    }

    @Test
    public void regTC05_fullreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC05_fullreject_PC_KYC_NRIC\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //perform full rejection
            subProc.procRejectKYC(true);
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Reject;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC05_fullreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC05_fullreject_PC_KYC_NRIC\"");
        }
    }

    @Test
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC06_resubmit_fullreject_PC_KYC_NRIC\"");
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
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
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC06_resubmit_fullreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC06_resubmit_fullreject_PC_KYC_NRIC\"");
        }
    }

    @Test
    public void regTC07_partialreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC07_partialreject_PC_KYC_NRIC\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //perform partial rejection
            subProc.procRejectKYC(false);
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.PartialReject;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC07_partialreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC07_partialreject_PC_KYC_NRIC\"");
        }
    }

    @Test
    public void regTC08_resubmit_partialreject_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC08_resubmit_partialreject_PC_KYC_NRIC\"");
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
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
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC08_resubmit_partialreject_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC08_resubmit_partialreject_PC_KYC_NRIC\"");
        }
    }

    @Test
    public void regTC09_approved_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC09_approved_PC_KYC_NRIC\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //perform approval
            subProc.procApproveKYC(true);
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Clear;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC09_approved_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC09_approved_PC_KYC_NRIC\"");
        }
    }

    @Test
    public void regTC10_logout_PC_KYC_NRIC(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC10_logout_PC_KYC_NRIC\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //logout of account
            subProc.procLogout();
            System.out.println("TEST CASE: Finish \"regTC10_logout_PC_KYC_NRIC\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC10_logout_PC_KYC_NRIC\"");
        }
    }

    @Test
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
            testAccountData.UnderUse = true;
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
            //create test user and test card
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
            System.out.println("TEST CASE: Fail \"regTC11_submit_NPC_KYC_forgeiner\"");
        }
    }

    @Test
    public void regTC12_fullreject_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC12_fullreject_NPC_KYC_forgeiner\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //perform full rejection
            subProc.procRejectKYC(true);
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Reject;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC12_fullreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC12_fullreject_NPC_KYC_forgeiner\"");
        }
    }

    @Test
    public void regTC13_resubmit_fullreject_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC13_resubmit_fullreject_NPC_KYC_forgeiner\"");
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
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
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC13_resubmit_fullreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC13_resubmit_fullreject_NPC_KYC_forgeiner\"");
        }
    }

    @Test
    public void regTC14_partialreject_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC14_partialreject_NPC_KYC_forgeiner\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //perform partial rejection
            subProc.procRejectKYC(false);
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.PartialReject;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC14_partialreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC14_partialreject_NPC_KYC_forgeiner\"");
        }
    }

    @Test
    public void regTC15_resubmit_partialreject_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC15_resubmit_partialreject_NPC_KYC_forgeiner\"");
        AndroidElement el;
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
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
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC15_resubmit_partialreject_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC15_resubmit_partialreject_NPC_KYC_forgeiner\"");
        }
    }

    @Test
    public void regTC16_approved_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC16_approved_NPC_KYC_forgeiner\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //perform approval
            subProc.procApproveKYC(false);
            //update test user
            testAccountData.KycStatus = TestAccountData.KYCStatus.Clear;
            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC16_approved_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC16_approved_NPC_KYC_forgeiner\"");
        }
    }

    @Test
    public void regTC17_activatecard_NPC_KYC_forgeiner() {
        System.out.println("TEST CASE: Start \"regTC17_activatecard_NPC_KYC_forgeiner\"");
        AndroidElement el;
        try {
            /////////////////////
            //using SIT Profile account for testing
            String userID = "1000955";
            String pin = "1111";
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //otp login using existing account
            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, false);
            ////////////////////////
            //above is for testing direct login and activation
            //for follow on ot 16 assume the customer is on the verification page already
            subProc.procActivateCard(false, userID, null, "1111");

            //
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.HomePageElementDict, "logoYouTrip", driver);
            assertTrue(el.isDisplayed());
            System.out.println("TEST STEP: Home Page - on page");





            System.out.println("TEST CASE: Finish \"regTC17_activatecard_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC17_activatecard_NPC_KYC_forgeiner\"");
        }

    }

    @Test
    public void regTC18_logout_NPC_KYC_forgeiner(ITestContext context) {
        System.out.println("TEST CASE: Start \"regTC18_logout_NPC_KYC_forgeiner\"");
        try {
            //recall user ID from previous test case
            String userID = (String) context.getAttribute("userid");
            testAccountData = subProc.api.data_getTestUserByUserID(userID);
            //logout of account
            subProc.procLogout();
            System.out.println("TEST CASE: Finish \"regTC18_logout_NPC_KYC_forgeiner\"");
        } catch (Exception e) {
            e.printStackTrace();
            fail();
            System.out.println("TEST CASE: Fail \"regTC18_logout_NPC_KYC_forgeiner\"");
        }
    }

    @Test
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
            System.out.println("TEST CASE: Fail \"regTC19_login_existing_user_OTP\"");
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

}