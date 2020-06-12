import TestBased.OnScreenExpectedStringValue;
import TestBased.TestAccountData;
import TestBased.TestAccountData.Market;
import TestBased.Utils;
import TestBased.YouTripAndroidUIElementKey;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import static org.testng.Assert.fail;

public class youtrip_android_th_regressionTest extends android_browserstackTest {

    Utils utils = new Utils();

    @Test
    public void regTC01_selectTH() {
        System.out.println("TEST CASE: Start \"regTC01_selectTH\"");
        try {
            //select TH from country selection
            subProc.procSelectCountry(Market.Thailand);
            System.out.println("TEST CASE: Finish \"regTC01_selectTH\"");
            //confirm country and continue to get started
            subProc.procConfirmCountry();
        } catch (Exception e) {
            System.out.println("TEST CASE: Fail \"regTC01_selectTH\"");
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC02_login_new_user_OTP() {
        System.out.println("TEST CASE: Start \"regTC02_login_new_user_OTP\"");
        testAccountData = new TestAccountData();
        try {
            String mprefix = "66";
            String mnumber = utils.getTimestamp();
            String email = ("qa+th" + mnumber + "@you.co");
            testAccountData.MCC = mprefix;
            testAccountData.PhoneNumber = mnumber;
            testAccountData.Email = email;
            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(mprefix, mnumber, false);
            System.out.println("TEST CASE: Finish \"regTC02_login_new_user_OTP\"");
        } catch (Exception e) {
            System.out.println("TEST CASE: Fail \"regTC02_login_new_user_OTP\"");
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC03_submit_and_approve_PC_KYC() {
        System.out.println("TEST CASE: Start \"regTC03_submit_and_approve_PC_KYC\"");
        AndroidElement el;
        try {
            if (testAccountData == null) {
                fail("fail to load test account from previous test case");
                System.out.println("TEST CASE: Fail \"regTC03_submit_and_approve_PC_KYC\"");
            }
            testAccountData.KycStatus = TestAccountData.KYCStatus.Submit;
            testAccountData.TestAccountCardType = TestAccountData.CardType.PC;
            testAccountData.LastName = "";
            testAccountData.FirstName = "";
            testAccountData.NameOnCard = "";
            testAccountData.NricNumber = subProc.api.util.getThaiID();
            testAccountData.Birthdate = "01-01-1980";
            testAccountData.AddressLineOne = "Auto Test Addresss Line 1";
            testAccountData.AddressLineTwo = "Android Device Line 2";
            testAccountData.PostalCode = "123456";
            testAccountData.Card = null;
            testAccountData.UnderUse = true;
            testAccountData.TestAccountMarket = subProc.getCurrentMarket();
            testAccountData.IsExplorerModeOn = false;
            String nationality = "Thai";

            //select PC card
            subProc.procSelectCardType(true);
            //enter ID number
            subProc.procSubmitTHKYC(true,testAccountData.NricNumber);

            //handle open with dialog
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.AndroidSystemAlertElementDict, "justOnceOption", driver, true);
            if (el != null) {
                el.click();
                System.out.println("TEST STEP: Android Dialog - confirm Android dialog");
            }
            Thread.sleep(3000);

            //update KYC status of KYC and redirect back to YouTrip App
            subProc.api.hack_thRequestAndPassKYC(true, testAccountData.NricNumber, "", testAccountData.MCC, testAccountData.PhoneNumber,  testAccountData.Email);
            subProc.procSwitchBackToYouTripApp();

            //Verify back to YouTrip App on Limited Home page
            //back to the app - wait for approval
            wait.until(ExpectedConditions.textToBePresentInElement((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.LimitedHomePageElementDict, "lblTitle", driver)), OnScreenExpectedStringValue.LimitedHomePageSGPCKYCApprovedTitle));
            System.out.println("TEST STEP: Limited HomePage - on page");
            //get ID number for storage
            testAccountData.Id = subProc.api.data_getTestUserIdByPhoneNumber(testAccountData.MCC, testAccountData.PhoneNumber);
            //create test user
            subProc.api.data_createTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC03_submit_and_approve_PC_KYC\"");
        } catch (Exception e) {
            System.out.println("TEST CASE: Fail \"regTC03_submit_and_approve_PC_KYC\"");
            testAccountData = null;
            e.printStackTrace();
            fail();
        }

    }

    @Test
    public void regTC04_activatecard_PC_KYC() {
        if(!subProc.api.getIsDevEnv()){
            throw new SkipException("This test is only available in Dev Environnment");
        }
        System.out.println("TEST CASE: Start \"regTC04_activatecard_PC_KYC\"");
        AndroidElement el;
        try {
            if (testAccountData == null) {
                fail("fail to load test account from previous test case");
                System.out.println("TEST CASE: Fail \"regTC04_activatecard_PC_KYC\"");
            }

            //create PC card for user
            if(subProc.api.getIsDevEnv()) {
                testAccountData.Card = subProc.api.devEnvGenerateCard(true, testAccountData.Id);
                subProc.api.data_bindTestCardToTestUser(testAccountData.Id, testAccountData.Card.Id);
            }
            Thread.sleep(5000);
            //activate card
            subProc.procActivateCard(Market.Thailand, true, testAccountData.Id, testAccountData.Card.YouId, "1111");
            Thread.sleep(10000);
            subProc.procVerifyInHomePage();
            //update test user
            testAccountData.Card.Status = TestAccountData.CardStatus.NewActive;
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("TEST CASE: Finish \"regTC04_activatecard_PC_KYC\"");

        } catch (Exception e) {
            System.out.println("TEST CASE: Fail \"regTC04_activatecard_PC_KYC\"");
            testAccountData.UnderUse = false;
            testAccountData.KycStatus = TestAccountData.KYCStatus.UnknownKycStatus;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

}