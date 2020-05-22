import TestBased.TestAccountData;
import TestBased.TestAccountData.CardStatus;
import TestBased.TestAccountData.CardType;
import TestBased.TestAccountData.KYCStatus;
import TestBased.TestAccountData.Market;
import TestBased.TestCardData;
import TestBased.YouTripIosUIElementKey.PageKey;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
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
            subProc.procSelectCountry(Market.Thailand);
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
        String email = ("qa+sg" + mnumber + "@you.co");
        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST DATA: Email address is " + email);
        testAccountData.PhoneNumber = mnumber;
        testAccountData.MCC = mprefix;
        testAccountData.Email = email;
        isRequriedReset = false;

        try {
            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email, true);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            System.out.println("Test STEP: Finish \"regTC05_login_new_user_OTP\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC05_login_new_user_OTP\"");
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC10_partialreject_and_resubmit_PC_KYC_NRIC() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC10_partialreject_and_resubmit_PC_KYC_NRIC\"");
        isForebackEnable = true;
        isRequriedReset = false;
        IOSElement el;
        String actualText;
        String kycRefNo;
        try {
            if (testAccountData == null) {
                //testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.PC.toString(), KYCStatus.Submit.toString());
                fail("fail to load test account from previous test case in succeed");
            }

            if(isAppReset) {
                subProc.procSelectCountry(Market.Singapore);
                subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);

                // Transition wait after OTP-login to Limited Home page
                Thread.sleep(2000);
                el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
                if (el != null) {
                    el.click();
                    Thread.sleep(2000);
                }
            }

            // Limited Home Page
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Thank You for Your Application");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            kycRefNo = el.getText();

            System.out.println("TEST STEP: KYC Partial rejection Send");
            isForebackEnable = false;
            subProc.api.yp_partialReject(kycRefNo);
            Thread.sleep(25000);
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

            subProc.procSubmitSGPCNRICKYC(osMainVerInt, false, true, testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null, null);

            Thread.sleep(25000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            testAccountData.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC10_partialreject_and_resubmit_PC_KYC_NRIC\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC10_partialreject_and_resubmit_PC_KYC_NRIC\"");
            if(!isForebackEnable) {
                testAccountData.UnderUse = false;
                testAccountData.KycStatus = KYCStatus.UnknownKycStatus;
                subProc.api.data_updateTestUser(testAccountData);
                testAccountData = null;
            }
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC08_submit_PC_TH() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC08_submit_PC_KYC_NRIC\"");
        isRequriedReset = false;
        IOSElement el;

        Date date = new Date(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, -20);
        Date dateOfBirth = c.getTime();
        SimpleDateFormat dateOfBirthFormatter = new SimpleDateFormat("ddMMYYYY");

        if (!isAppReset) {
            testAccountData = new TestAccountData();
            SimpleDateFormat formatter = new SimpleDateFormat("YYMMDDHHmmssSS");
            System.out.println(formatter.format(date));
            String mprefix = "123";
            String mnumber = formatter.format(date);
            String email = ("qa+sg" + mnumber + "@you.co");

            testAccountData.PhoneNumber = mnumber;
            testAccountData.MCC = mprefix;
            testAccountData.Email = email;
        }
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
        testAccountData.UnderUse = true;
        testAccountData.printTestAccountData("NEW GEN");

        try {
            if(isAppReset) {
                subProc.procSelectCountry(Market.Singapore);
                subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, true);
                Thread.sleep(2000);
            }

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnPCRegister", driver);
            el.click();

            subProc.procSubmitSGPCNRICKYC(osMainVerInt, false, false, testAccountData.LastName, testAccountData.FirstName, testAccountData.NameOnCard,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null, null);

            Thread.sleep(20000);
            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            el = (IOSElement)UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Thank You for Your Application");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String kycRefNo = el.getText();
            testAccountData.Id = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");

            subProc.api.data_createTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC08_submit_PC_KYC_NRIC\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC08_submit_PC_KYC_NRIC\"");
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }
}