import TestBased.TestAccountData;
import TestBased.TestAccountData.CardStatus;
import TestBased.TestAccountData.CardType;
import TestBased.TestAccountData.KYCStatus;
import TestBased.TestAccountData.Market;
import TestBased.TestCardData;
import TestBased.UIElementData;
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
        String email = ("qa+th" + mnumber + "@you.co");
        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST DATA: Email address is " + email);
        testAccountData.PhoneNumber = mnumber;
        testAccountData.MCC = mprefix;
        testAccountData.Email = email;
        isRequriedReset = false;

        try {
            subProc.procSelectCountry(Market.Thailand);
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
                subProc.procSelectCountry(Market.Thailand);
                subProc.proc_TH_OTPLogin(testAccountData.MCC, testAccountData.PhoneNumber);
                Thread.sleep(2000);
            }
            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnOrderPC", driver);
            assertEquals(el.getText(), "Apply Today for FREE");
            el.click();

            subProc.procTH_SubmitKYC(osMainVerInt, true, testAccountData.NricNumber, "");

            el = (IOSElement) UIElementKeyDict.getElement(PageKey.AppStoreLocationAccessAlertPopUpElementDict, "Allow", driver, true);
            if(el != null){
                el.click();
                Thread.sleep(1000);
            }

            subProc.api.hack_thSubmitAndPassKYC(true, testAccountData.NricNumber, "", testAccountData.MCC, testAccountData.PhoneNumber,  testAccountData.Email);

            // Switch back to YouTrip App
            System.out.println("TEST STEP: Switch back to YouTrip App from DeepLink");
            Map<String, Object> params = new HashMap<>();
            params.put("bundleId", "co.you.youapp");
            driver.executeScript("mobile: launchApp", params);
            Thread.sleep(5000);

            System.out.println("TEST STEP: Close Page to lookup cache clearing");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.KYCKPLUSAuthenticationPageElementDict, "icClose", driver);
            el.click();
            Thread.sleep(2000);
            System.out.println("TEST STEP: Close Page to cached limited home page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.KYCKPLUSAuthenticationPageElementDict, "icClose", driver);
            el.click();
            Thread.sleep(2000);
            System.out.println("TEST STEP: Try on Logout and re-login");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnMenu", driver);
            el.click();
            Thread.sleep(2000);

            //driver.findElementByAccessibilityId("menuBtnSetting").click();
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "menuBtnSetting", driver);
            el.click();
            Thread.sleep(2000);
            //driver.findElementByAccessibilityId("Log Out");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.SettingPageElementDict, "limitedHomeBtnLogout", driver);
            el.click();
            Thread.sleep(2000);

            subProc.procSelectCountry(Market.Thailand);
            subProc.proc_TH_OTPLogin(testAccountData.MCC, testAccountData.PhoneNumber);
            Thread.sleep(2000);

            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            el = (IOSElement)UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Thank You for Your Application");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String kycRefNo = el.getText();
            testAccountData.Id = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");

            subProc.api.data_createTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC08_submit_PC_TH\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC08_submit_PC_TH\"");
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }
}
