import TestBased.OnScreenExpectedStringValue;
import TestBased.TestAccountData;
import TestBased.TestAccountData.CardType;
import TestBased.TestAccountData.KYCStatus;
import TestBased.TestAccountData.Market;
import TestBased.YouTripIosUIElementKey.PageKey;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class youtrip_ios_th_regressionTest extends ios_browserstackTest {
    @Test (groups = { "regression_test"})
    public void regTC03_selectTH() {
        isRequriedReset = true;
        System.out.println("Test STEP: Start \"regTC03_selectTH\"");
        try {
            subProc.procSelectCountry();
            System.out.println("TEST CASE: Finish \"regTC01_selectTH\"");
        }catch(Exception e){
            System.out.println("TEST CASE: Fail \"regTC01_selectTH\"");
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC05_login_new_user_OTP() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC05_login_new_user_OTP\"");
        testAccountData = new TestAccountData();
        SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        String mprefix = "66";
        String mnumber = formatter.format(date);
        String email = ("qa+th" + mnumber + "@you.co");
        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST DATA: Email address is " + email);
        testAccountData.PhoneNumber = mnumber;
        testAccountData.MCC = mprefix;
        testAccountData.Email = email;
        isRequriedReset = false;

        try {
            subProc.procSelectCountry();
            subProc.proc_TH_OTPLogin(mprefix, mnumber);

            System.out.println("Test STEP: Finish \"regTC05_login_new_user_OTP\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC05_login_new_user_OTP\"");
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC08_submit_PC_TH() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC08_submit_PC_TH\"");
        isRequriedReset = false;
        IOSElement el;

        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -20);
        Date dateOfBirth = c.getTime();
        SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");

        if (isAppReset) {
            testAccountData = new TestAccountData();
            SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
            System.out.println(formatter.format(date));
            String mprefix = "66";
            String mnumber = formatter.format(date);
            String email = ("qa+th" + mnumber + "@you.co");

            testAccountData.PhoneNumber = mnumber;
            testAccountData.MCC = mprefix;
            testAccountData.Email = email;
        }
        testAccountData.KycStatus = KYCStatus.Submit;
        testAccountData.TestAccountCardType = CardType.PC;
        testAccountData.LastName = "";;
        testAccountData.FirstName = "";;
        testAccountData.NameOnCard = "";
        testAccountData.NricNumber = subProc.api.util.getThaiID();
        testAccountData.Birthdate = dateOfBirthFormatter.format(dateOfBirth);
        testAccountData.AddressLineOne = "1";
        testAccountData.AddressLineTwo = "2";
        testAccountData.PostalCode = "000000";
        testAccountData.Card = null;
        testAccountData.UnderUse = true;
        testAccountData.TestAccountMarket = Market.Thailand;
        testAccountData.printTestAccountData("NEW GEN");

        try {
            if(isAppReset) {
                subProc.procSelectCountry();
                subProc.proc_TH_OTPLogin(testAccountData.MCC, testAccountData.PhoneNumber);
                Thread.sleep(2000);
            }
            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), OnScreenExpectedStringValue.LimitedHomePageTitle));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnOrderPC", driver);
            assertEquals(el.getText(), "Apply Today for FREE");
            el.click();
            Thread.sleep(2000);

            subProc.procTH_SubmitKYC(osMainVerInt, true, testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, testAccountData.NricNumber, "");
            testAccountData.Id = subProc.api.data_getTestUserIdByPhoneNumber(testAccountData.MCC, testAccountData.PhoneNumber);

            subProc.api.data_createTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC08_submit_PC_TH\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC08_submit_PC_TH\"");
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC23_PC_Activate_Card_TH() throws InterruptedException{
        //NPC card activation test
        if(!subProc.api.getIsDevEnv()){
            throw new SkipException("This test is only available in Dev Environnment");
        }

        System.out.println("Test STEP: Start \"regTC23_PC_Activate_Card_TH\"");
        isForebackEnable = false;
        isRequriedReset = true;
        IOSElement el;
        try {
            if (testAccountData == null) {
                //testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.NPC.toString(), KYCStatus.Submit.toString());
                fail("fail to load test account from previous test case in succeed");
            }

            if(subProc.api.getIsDevEnv()) {
                testAccountData.Card = subProc.api.devEnvGenerateCard(true, testAccountData.Id);
                subProc.api.data_bindTestCardToTestUser(testAccountData.Id, testAccountData.Card.Id);
            }

            if(isAppReset) {
                subProc.procSelectCountry();
                subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

                // Transition wait after OTP-login to Limited Home page
                Thread.sleep(2000);
                System.out.println("TEST STEP: Allow notification Pop Up from YouTrip App if need");
                el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
                if (el != null) {
                    el.click();
                    Thread.sleep(2000);
                }
            }

            subProc.procActiveCardRegistration(true, testAccountData.Id, testAccountData.Card.YouId, this.defaultAPPPinCode);
//            subProc.procVerifyInHomePage();

            testAccountData.Card.Status = TestAccountData.CardStatus.NewActive;
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC23_PC_Activate_Card_TH\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC23_PC_Activate_Card_TH\"");
            if(testAccountData != null) {
                testAccountData.UnderUse = false;
                testAccountData.KycStatus = KYCStatus.UnknownKycStatus;
                if(testAccountData.Card != null) {
                    testAccountData.Card.UnderUse = false;
                }
                subProc.api.data_updateTestUser(testAccountData);
            }
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }
}
