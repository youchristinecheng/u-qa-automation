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
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnOrderPC", driver);
            assertEquals(el.getText(), "Apply Today for FREE");
            el.click();
            Thread.sleep(2000);

            subProc.procTH_SubmitKYC(osMainVerInt, true, testAccountData.NricNumber, "");
            Thread.sleep(1000);

            System.out.println("TEST STEP: Dismiss Location Pop Up from APP Store if need");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null){
                el.click();
                Thread.sleep(1000);
            }

            subProc.api.hack_thRequestAndPassKYC(true, testAccountData.NricNumber, "", testAccountData.MCC, testAccountData.PhoneNumber,  testAccountData.Email);


            // Switch back to YouTrip App
            System.out.println("TEST STEP: Switch back to YouTrip App from DeepLink");
            driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", "com.apple.AppStore"));
            Map<String, Object> params = new HashMap<>();
            params.put("bundleId", "co.you.youapp");
            driver.executeScript("mobile: launchApp", params);
            Thread.sleep(5000);



            System.out.println("TEST STEP: Close Page to reverse cached KPlus Registering Page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.KYCKPLUSAuthenticationPageElementDict, "icClose", driver);
            el.click();
            Thread.sleep(2000);
            System.out.println("TEST STEP: Close Page to reverse cached Enter Thai ID Number page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.KYCKPLUSAuthenticationPageElementDict, "icClose", driver);
            el.click();
            Thread.sleep(10000);

            // Check for notification pop up is shown or not
            System.out.println("TEST STEP: Allow notification Pop Up from YouTrip App if need");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
            if(el != null){
                el.click();
                Thread.sleep(1000);
            }

            System.out.println("TEST STEP: Verify updated to Limited Home Page");
            el = (IOSElement)UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Your Card is On Its Way");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
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

            System.out.println("TEST STEP: Limited Home Page - KYC already approved");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Your Card is On Its Way");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver).getText(), "My Card Arrived");

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: PC Registration - Enter Y-Number");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.EnterYNumberPageElementDict, "lblTitle", driver), "Enter Y-Number"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.EnterYNumberPageElementDict, "txtYouIdDigit1", driver);
            el.sendKeys(testAccountData.Card.YouId.substring(2));
            Thread.sleep(3000);

            System.out.println("TEST STEP: Confirm Email Address Page - On Page");
            assertEquals(UIElementKeyDict.getElement(PageKey.ActivateCardConfirmEmailPageElementDict, "lblTitle", driver).getText(), "Check Your Email");
            System.out.println("debug");
//            Thread.sleep(2000);

            String deepLinkURL = subProc.api.getActivateCardEmailLink(testAccountData.Id);
            System.out.println(deepLinkURL);

            // Hacking code for switch to safari and opend deeplink
            driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", "com.apple.mobilesafari"));
            List args = new ArrayList();
            args.add("-u");
            args.add(deepLinkURL);

            // Need to have wait time for app launch
            System.out.println("TEST STEP: Launch Safari App for apply DeepLink");
            Map<String, Object> params = new HashMap<>();
            params.put("bundleId", "com.apple.mobilesafari");
            params.put("arguments", args);
            driver.executeScript("mobile: launchApp", params);
            Thread.sleep(7000);

            driver.findElementByAccessibilityId("Open").click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Switch back to YouTrip App from DeepLink");
            args.clear();
            params.clear();
            params.put("bundleId", "co.you.youapp");
            driver.executeScript("mobile: launchApp", params);
            Thread.sleep(5000);

            assertEquals(UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "lblTHStepsTitle", driver).getText(), "Set App PIN and Card PIN");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "btnTHSteps1", driver);
            el.click();
            Thread.sleep(2000);

            // Create a Pin
            assertEquals(UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "lblTHCreateAppPIN", driver).getText(), "Create an App PIN");
            subProc.procEnterAPPPinCode(this.defaultAPPPinCode);
            Thread.sleep(2000);

            // Confirm Your Pin
            assertEquals(UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "lblTHConfirmAppPIN", driver).getText(), "Type Again to Confirm");
            subProc.procEnterAPPPinCode(this.defaultAPPPinCode);
            Thread.sleep(2000);

            assertEquals(UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "lblTHSteps2", driver).getText(), "Step 2: Set a Card PIN");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.APPPinCodePageElementDict, "btnTHSameCardPin", driver);
            el.click();
            Thread.sleep(5000);

            //TODO: Verification of Home Page redirection is skipped due to homepage replacement
            //subProc.procVerifyInHomePage();

            testAccountData.Card.Status = TestAccountData.CardStatus.NewActive;
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC23_PC_Activate_Card_TH\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC23_PC_Activate_Card_TH\"");
            testAccountData.UnderUse = false;
            testAccountData.KycStatus = KYCStatus.UnknownKycStatus;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }
}
