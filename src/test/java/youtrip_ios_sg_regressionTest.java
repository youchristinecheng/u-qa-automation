import TestBased.TestAccountData;
import TestBased.TestAccountData.CardStatus;
import TestBased.TestAccountData.CardType;
import TestBased.TestAccountData.KYCStatus;
import TestBased.TestAccountData.Market;
import TestBased.TestCardData;
import TestBased.YouTripIosUIElementKey.PageKey;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.smartcardio.Card;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.testng.Assert.*;

public class youtrip_ios_sg_regressionTest extends ios_browserstackTest {

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
    public void regTC04_selectSG() {
        isRequriedReset = true;
        System.out.println("Test STEP: Start \"regTC04_selectSG\"");
        try {
            subProc.procSelectCountry(Market.Singapore);
            System.out.println("Test STEP: Finish \"regTC04_selectSG\"");
        } catch (Exception e) {
            System.out.println("Test STEP: Fail \"regTC04_selectSG\"");
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
        String mprefix = "123";
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
    public void regTC08_submit_PC_KYC_NRIC() throws InterruptedException {
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
        testAccountData.TestAccountMarket = subProc.getCurrentMarket();
        testAccountData.IsExplorerModeOn = false;
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

    @Test (groups = { "regression_test"})
    public void regTC09_fullreject_and_resubmit_PC_KYC_NRIC() {
        System.out.println("Test STEP: Start \"regTC09_fullreject_and_resubmit_PC_KYC_NRIC\"");
        isForebackEnable = true;
        isRequriedReset = false;
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
                fail("fail to load test account from previous test case in succeed");
            }

            // Limited Home Page
            el= (IOSElement)UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Thank You for Your Application");
            Thread.sleep(2000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            kycRefNo = el.getText();
            System.out.println("TEST DATA: KYC Reference from Limited Home Page " +kycRefNo);

            System.out.println("TEST STEP: KYC rejection Send");
            isForebackEnable = false;
            subProc.api.yp_fullReject(kycRefNo);
            Thread.sleep(20000);
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
            testAccountData.UnderUse = true;

            Thread.sleep(20000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);


            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC09_fullreject_and_resubmit_PC_KYC_NRIC\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC09_fullreject_and_resubmit_PC_KYC_NRIC\"");
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
            Thread.sleep(2000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            kycRefNo = el.getText();
            System.out.println("TEST DATA: KYC Reference from Limited Home Page " +kycRefNo);

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
    public void regTC11_approved_PC_KYC_NRIC() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC11_approved_PC_KYC_NRIC\"");
        isForebackEnable = false;
        isRequriedReset = true;
        IOSElement el;
        try {
            if (testAccountData == null) {
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
            Thread.sleep(2000);
            //get and store the KYC reference number
            String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver)).getText();
            System.out.println("TEST DATA: KYC submission reference number is " + kycRefNo);

            //call YP full reject with Ref Number
            subProc.api.yp_approve(kycRefNo);

            //back to the app - wait for reject to be updated
            Thread.sleep(25000);
            //wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
            System.out.println("TEST STEP: KYC approval received");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Your Card is On Its Way");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver).getText(), "My Card Arrived");

            testAccountData.KycStatus = KYCStatus.Clear;
            testAccountData.UnderUse = false;

            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC11_approved_PC_KYC_NRIC\"");
            testAccountData = null;
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC11_approved_PC_KYC_NRIC\"");
            testAccountData.UnderUse = false;
            testAccountData.KycStatus = KYCStatus.UnknownKycStatus;
            subProc.api.data_updateTestUser(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC19_submit_NPC_KYC_EmploymentPass() throws InterruptedException {
        System.out.println("Test STEP: Start \"reg19_submit_NPC_KYC_EmploymentPass\"");
        isForebackEnable = false;
        isRequriedReset = false;

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
            testAccountData.UnderUse = true;
            testAccountData.printTestAccountData("NEW GEN");

            subProc.procSelectCountry(Market.Singapore);
            subProc.procOTPLogin(mprefix, mnumber, email,true);
            Thread.sleep(2000);

            System.out.println("TEST STEP: Welcome Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "lblWelcome", driver), "Welcome"));
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.WelcomePageElementDict, "btnNPCRegister", driver);
            el.click();
            Thread.sleep(2000);

            subProc.procSubmitSGNPCEmploymentPassKYC(osMainVerInt, false, false, testAccountData.Card.YouId, testAccountData.LastName, testAccountData.FirstName,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null);

            Thread.sleep(20000);
            System.out.println("TEST STEP: Verify Back to Limited Home Page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Thank You for Your Application");
            Thread.sleep(2000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String kycRefNo = el.getText();
            System.out.println("TEST DATA: KYC Reference from Limited Home Page " +kycRefNo);
            testAccountData.Id = subProc.api.yp_getKYCdetails(kycRefNo).get("userId");

            subProc.api.data_updateTestCard(testAccountData.Card);
            subProc.api.data_createTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"reg19_submit_NPC_KYC_EmploymentPass\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"reg19_submit_NPC_KYC_EmploymentPass\"");
            testAccountData = null;
            if (testCardData != null){
                testCardData.Status = CardStatus.UnknownCardStatus;
                testCardData.UnderUse = false;
                subProc.api.data_updateTestCard(testCardData);
            }
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC20_fullreject_and_resubmit_NPC_KYC_EmploymentPass() {
        System.out.println("Test STEP: Start \"regTC20_fullreject_and_resubmit_NPC_KYC_EmploymentPass\"");
        isForebackEnable = true;
        isRequriedReset = false;
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
                fail("fail to load test account from previous test case in succeed");
            }

            // Limited Home Page
            el = (IOSElement)UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Thank You for Your Application");
            Thread.sleep(2000);
            kycRefNo = ((IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver)).getText();
            System.out.println("TEST DATA: KYC Reference from Limited Home Page " +kycRefNo);

            System.out.println("TEST STEP: KYC rejection Send");
            isForebackEnable = false;
            subProc.api.yp_fullReject(kycRefNo);
            Thread.sleep(25000);
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

            Thread.sleep(20000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            testAccountData.UnderUse = true;
            testAccountData.Card.UnderUse = true;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC20_fullreject_and_resubmit_NPC_KYC_EmploymentPass\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC20_fullreject_and_resubmit_NPC_KYC_EmploymentPass\"");
            if(!isForebackEnable) {
                testAccountData.UnderUse = false;
                testAccountData.KycStatus = KYCStatus.UnknownKycStatus;
                testAccountData.Card.UnderUse = false;
                subProc.api.data_updateTestUser(testAccountData);
                testAccountData = null;
            }
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC21_partialreject_and_resubmit_NPC_KYC_EmploymentPass() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC21_partialreject_and_resubmit_NPC_KYC_EmploymentPass\"");
        isForebackEnable = true;
        isRequriedReset = false;
        IOSElement el;

        String actualText;
        String kycRefNo;
        try {
            if (testAccountData == null) {
                //testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.NPC.toString(), KYCStatus.Submit.toString());
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
            Thread.sleep(2000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            kycRefNo = el.getText();
            System.out.println("TEST DATA: KYC Reference from Limited Home Page " +kycRefNo);

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

            subProc.procSubmitSGNPCEmploymentPassKYC(osMainVerInt, false, true, testAccountData.Card.YouId, testAccountData.LastName, testAccountData.FirstName,
                    testAccountData.Birthdate, testAccountData.NricNumber,
                    testAccountData.AddressLineOne, testAccountData.AddressLineTwo, testAccountData.PostalCode,
                    null, null, null, null, null);

            Thread.sleep(20000);
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver);
            String newKycRefNo = el.getText();
            Assert.assertNotEquals(newKycRefNo, kycRefNo);

            testAccountData.UnderUse = isRequriedReset;
            testAccountData.Card.UnderUse = isRequriedReset;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC21_partialreject_and_resubmit_NPC_KYC_EmploymentPass\"");
        }catch (Exception e){
            System.out.println("Test STEP: Fail \"regTC21_partialreject_and_resubmit_NPC_KYC_EmploymentPass\"");
            if(!isForebackEnable) {
                testAccountData.UnderUse = false;
                testAccountData.KycStatus = KYCStatus.UnknownKycStatus;
                testAccountData.Card.UnderUse = false;
                subProc.api.data_updateTestUser(testAccountData);
                testAccountData = null;
            }
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC22_approved_NPC_KYC_EmploymentPass() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC22_approved_NPC_KYC_EmploymentPass\"");
        isForebackEnable = false;
        isRequriedReset = true;
        IOSElement el;
        try {
            if (testAccountData == null) {
                //testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatus(CardType.NPC.toString(), KYCStatus.Submit.toString());
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

            //get and store the KYC reference number
            Thread.sleep(2000);
            String kycRefNo = (UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblReferenceNumVal", driver)).getText();
            System.out.println("TEST DATA: KYC submission reference number is " + kycRefNo);

            //call YP full reject with Ref Number
            subProc.api.yp_approve(kycRefNo);

            //back to the app - wait for reject to be updated
            Thread.sleep(20000);
            //wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver)));
            System.out.println("TEST STEP: KYC approval received");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "lblTitle", driver).getText(), "Verification Complete");
            assertEquals(UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnNext", driver).getText(), "Activate Card");

            testAccountData.KycStatus = KYCStatus.Clear;
            testAccountData.UnderUse = false;
            testAccountData.Card.UnderUse = false;

            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC22_approved_NPC_KYC_EmploymentPass\"");
            testAccountData = null;
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC22_approved_NPC_KYC_EmploymentPass\"");
            testAccountData.UnderUse = false;
            testAccountData.KycStatus = KYCStatus.UnknownKycStatus;
            testAccountData.Card.UnderUse = false;
            subProc.api.data_updateTestUser(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC07_Logout() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC07_Logout\"");
        isRequriedReset = false;
        IOSElement el;
        try {
            if(!subProc.api.util.isActivedTestAccountValid(CardType.PC, KYCStatus.Clear, CardStatus.Active, testAccountData))
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());
            else
                testAccountData.printTestAccountData("Inherit from last test case");

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);
            subProc.procLogoutFromHomePage();

            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            System.out.println("Test STEP: Finish \"regTC07_Logout\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC07_Logout\"");
            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "not_ready"})
    public void regTC33_ChangePINFromSetting() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC33_ChangePINFromSetting\"");
        isRequriedReset = false;
        isForebackEnable = true;
        IOSElement el;
        String newAPPPinCode = "2222";
        try {
            if(!subProc.api.util.isActivedTestAccountValid(CardType.PC, KYCStatus.Clear, CardStatus.Active, testAccountData))
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());
            else
                testAccountData.printTestAccountData("Inherit from last test case");

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            isForebackEnable = subProc.procChangeAppPinFromHomePage(defaultAPPPinCode, newAPPPinCode);

            subProc.procLogoutFromHomePage();
            System.out.println("TEST STEP: Re-Login with new App Pin Code");
            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, newAPPPinCode);
            subProc.procChangeAppPinFromHomePage(newAPPPinCode, defaultAPPPinCode);
            subProc.procLogoutFromHomePage();

            System.out.println("Test STEP: Finish \"regTC33_ChangePINFromSetting\"");

            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC33_ChangePINFromSetting\"");
            if(testAccountData != null) {
                testAccountData.UnderUse = false;
                testAccountData.Card.UnderUse = false;
                if(!isForebackEnable)
                    testAccountData.Card.Status = CardStatus.UnknownCardStatus;
                subProc.api.data_updateTestUser(testAccountData);
            }
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC24_AddCardFromTopUpPage() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC24_AddCardFromTopUpPage\"");
        isRequriedReset = true;
        isForebackEnable = false;
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
            if(!subProc.api.util.isActivedTestAccountValid(CardType.PC, KYCStatus.Clear, CardStatus.NewActive, testAccountData))
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
            System.out.println("Test STEP: Finish \"regTC24_AddCardFromTopUpPage\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC24_AddCardFromTopUpPage\"");
            if(testAccountData != null) {
                testAccountData.UnderUse = false;
                testAccountData.Card.Status = CardStatus.UnknownCardStatus;
                testAccountData.Card.UnderUse = false;
                subProc.api.data_updateTestUser(testAccountData);
            }
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC25_UpdateCardFromTopUpPage() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC25_UpdateCardFromTopUpPage\"");
        isRequriedReset = false;
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
            if(!subProc.api.util.isActivedTestAccountValid(CardType.PC, KYCStatus.Clear, CardStatus.Active, testAccountData))
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());
            else
                testAccountData.printTestAccountData("Inherit from last test case");

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

            // Back to Home Page
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.TopUpPageElementDict, "btnClose", driver);
            el.click();
            Thread.sleep(2000);
            // Perform Logout
            subProc.procVerifyInHomePage(Market.Singapore);
            subProc.procLogoutFromHomePage();

            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            System.out.println("Test STEP: Finish \"regTC25_UpdateCardFromTopUpPage\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC25_UpdateCardFromTopUpPage\"");
            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test"})
    public void regTC25_UpdateCardFromOrderCardReplacePage() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC25_UpdateCardFromOrderCardReplacePage\"");
        isRequriedReset = false;
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
            if(!subProc.api.util.isActivedTestAccountValid(CardType.PC, KYCStatus.Clear, CardStatus.Active, testAccountData))
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());
            else
                testAccountData.printTestAccountData("Inherit from last test case");

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCard", driver).click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Card Lock Page - on page");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).isDisplayed());
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).isDisplayed());
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Order Replacement Card Page - on page");
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

            System.out.println("TEST STEP: Order Replacement Card Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblTitle", driver);
            Assert.assertEquals(el.getText(), "Order Replacement Card");
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblCreditCardNumber", driver);
            Assert.assertEquals(el.getText(), expectedDisplayCard);
            // Back to Card Page
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "btnClose", driver);
            el.click();
            Thread.sleep(2000);
            // Back to Home Page
            el = (IOSElement) UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnClose", driver);
            el.click();
            Thread.sleep(2000);
            subProc.procVerifyInHomePage(Market.Singapore);
            subProc.procLogoutFromHomePage();

            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            System.out.println("Test STEP: Finish \"regTC25_UpdateCardFromOrderCardReplacePage\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC25_UpdateCardFromOrderCardReplacePage\"");
            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }

    }

    @Test (groups = { "regression_test"})
    public void regTC26_TopUpSuccess() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC26_TopUpSuccess\"");
        isRequriedReset = false;
        IOSElement el;
        List<WebElement> balanceList;
        List<WebElement> activityList;
        double actualBalance = 0.0;
        double expectedBalance = 0.0;
        double topUpAmt = 20.00;
        String expectedTxnDisplayVal="";

        JavascriptExecutor jsExec = (JavascriptExecutor) driver;
        try {
            if(!subProc.api.util.isActivedTestAccountValid(CardType.PC, KYCStatus.Clear, CardStatus.Active, testAccountData))
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());
            else
                testAccountData.printTestAccountData("Inherit from last test case");

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);

            System.out.println("TEST STEP: Home Page - Get Current SGD Balance");
            balanceList = UIElementKeyDict.getHorizontalBalanceBlockList(driver);
            for(int i = 1; i < balanceList.size(); i++){
                if(balanceList.get(i).getText().equals("SGD")){
                    actualBalance = subProc.api.util.formatStringAmountValueToDouble(balanceList.get(i + 1).getText());
                    //actualBalance = Double.parseDouble(balanceList.get(i + 1).getText());
                    expectedBalance = actualBalance + topUpAmt;
                    expectedTxnDisplayVal = "+ " + subProc.api.util.formatAmountValueToString(topUpAmt) + " SGD";
                    System.out.println("TEST DATA: Home Page - Current SGD Balance: " + String.valueOf(actualBalance));
                    System.out.println("TEST DATA: Home Page - Expect SGD Balance After Top-up: " + String.valueOf(expectedBalance));
                    System.out.println("TEST DATA: Home Page - Expect Top-up Transaction: " + String.valueOf(expectedTxnDisplayVal));
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

            Assert.assertEquals(activityList.get(0).getText(), expectedTxnDisplayVal);
            Assert.assertEquals(activityList.get(2).getText(), "Top Up");
            //Assert.assertEquals(activityList.get(1).getText(), "1 min");

            subProc.procLogoutFromHomePage();

            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            System.out.println("Test STEP: Finish \"regTC26_TopUpSuccess\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC26_TopUpSuccess\"");
            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "not_ready"})
    public void regTC41_LockCard() throws InterruptedException {
        try {
            System.out.println("Test STEP: Start \"regTC41_LockCard\"");
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

            // Checking FIS card status; which is only avaiable while in sit env; Yet Checking the code directly is not ideal.
            if(fisapi != null){
                List<String> cardStatusCodeList = fisapi.getCardStatusFromService(testAccountData.Card.CardIDToken);
                Assert.assertEquals(cardStatusCodeList.get(0), "21");
                Assert.assertEquals(cardStatusCodeList.get(1), "00");
            }

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
            testAccountData.Card.Status = CardStatus.Locked;
            subProc.api.data_updateTestUser(testAccountData);
            System.out.println("Test STEP: Finish \"regTC41_LockCard\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC41_LockCard\"");
            if(testAccountData != null) {
                testAccountData.UnderUse = false;
                testAccountData.Card.UnderUse = false;
                testAccountData.Card.Status = CardStatus.UnknownCardStatus;
                subProc.api.data_updateTestUser(testAccountData);
            }
            e.printStackTrace();
            fail();
        }
    }

    @Test (groups = { "regression_test", "not_ready"})
    public void regTC43_checkOrderReplacementCardPage() throws InterruptedException {
        System.out.println("Test STEP: Start \"regTC43_checkOrderReplacementCardPage\"");
        isRequriedReset = false;
        IOSElement el;
        try {
            if(!subProc.api.util.isActivedTestAccountValid(CardType.PC, KYCStatus.Clear, CardStatus.Active, testAccountData))
                testAccountData = subProc.api.data_getTestUserByCardTypeAndKycStatusAndCardStatus(CardType.PC.toString(), KYCStatus.Clear.toString(), CardStatus.Active.toString());
            else
                testAccountData.printTestAccountData("Inherit from last test case");

            subProc.procLoginToHomePage(Market.Singapore, testAccountData.MCC, testAccountData.PhoneNumber, defaultAPPPinCode);
            UIElementKeyDict.getElement(PageKey.HomePageElementDict, "btnCard", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Card Lock Page - on page");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).isDisplayed());
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "lblOrderCardTitle", driver).getText(), "Replacement Card");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "lblOrderCardDesc", driver).getText(), "Card lost or stolen?\nGet a replacement card for S$10.00.");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).isDisplayed());
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnOrderCard", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Order Replacement CardP age - on page");
            String expectedAddressBlock = testAccountData.AddressLineOne + "\n" + testAccountData.AddressLineTwo + "\n" + testAccountData.PostalCode;
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblTitle", driver).getText(), "Order Replacement Card");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblCurrencySign", driver).getText(), "S$");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblFeeAmt", driver).getText(), "10.00");
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "txtAddress", driver).getText(), expectedAddressBlock);
            Assert.assertEquals(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "lblImportantNote", driver).getText(), "Important: Your original YouTrip card will be suspended once you place the order.");
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "btnChangeCard", driver)));
            wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "sliderOrderReplacementCard", driver)));

            System.out.println("TEST STEP: Order Replacement CardP age - exit");
            UIElementKeyDict.getElement(PageKey.OrderReplacementCardPageElementDict, "btnClose", driver).click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: Card Lock Page - on page and exit");
            Assert.assertTrue(UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "toggleLockCard", driver).isDisplayed());
            UIElementKeyDict.getElement(PageKey.LockCardPageElementDict, "btnClose", driver).click();
            Thread.sleep(2000);

            subProc.procVerifyInHomePage(Market.Singapore);
            subProc.procLogoutFromHomePage();

            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            System.out.println("Test STEP: Finish \"regTC43_checkOrderReplacementCardPage\"");
        }catch(Exception e){
            System.out.println("Test STEP: Fail \"regTC43_checkOrderReplacementCardPage\"");
            subProc.unlockTestAccountData(testAccountData);
            testAccountData = null;
            e.printStackTrace();
            fail();
        }
    }

//    @Test
//    public void test_card_activation(){
//        //1001006
//        System.out.println("Test STEP: Start \"test_card_activation\"");
//
//        driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", "com.apple.mobilesafari"));
//
//        List args = new ArrayList();
////        args.add("-u");
////        args.add(deepLinkURL);
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("bundleId", "com.apple.mobilesafari");
//        params.put("arguments", args);
//        driver.executeScript("mobile: launchApp", params);
//
//        args.clear();
//        params.clear();
//        params.put("bundleId", "co.you.youapp");
//        driver.executeScript("mobile: launchApp", params);
//
//        //driver.findElementByAccessibilityId("Open").click();
//
//        isRequriedReset = false;
//        IOSElement el;
//        try {
//            testAccountData = subProc.api.data_getTestUserByUserID("1001006");
//
//            //if(isAppReset) {
//            subProc.procSelectCountry(Market.Singapore);
//            subProc.procOTPLogin(testAccountData.MCC, testAccountData.PhoneNumber, testAccountData.Email, false);
//
//            // Transition wait after OTP-login to Limited Home page
//            Thread.sleep(2000);
//            el = (IOSElement) UIElementKeyDict.getElement(PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
//            if (el != null) {
//                el.click();
//                Thread.sleep(2000);
//            }
//
//            //}
//
//            System.out.println("debug here");
//
//        }catch(Exception e){
//            System.out.println("Test STEP: Fail \"test_card_activation\"");
//            subProc.unlockTestAccountData(testAccountData);
//            testAccountData = null;
//            e.printStackTrace();
//            fail();
//        }
//    }
}