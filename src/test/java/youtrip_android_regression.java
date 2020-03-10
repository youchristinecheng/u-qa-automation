import TestBased.*;
import TestBased.YouTripAndroidUIElementKey.Market;
import TestBased.YouTripAndroidUIElementKey.PageKey;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.lang.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class youtrip_android_regression {

    AndroidDriver driver;
    YouAPI api;
    Utils utils;
    YouTripAndroidUIElementKey UIElementKeyDict;
    YouTripAndroidSubRoutine subProc;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() throws MalformedURLException {

        api = new YouAPI();
        utils = new Utils();
        UIElementKeyDict = new YouTripAndroidUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        /*
         * ###### Desired Capabilities for Real Device ######
         */
        /*capabilities.setCapability("deviceName", "Nexus 5");
        capabilities.setCapability(CapabilityType.VERSION, "6.0.1");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "app-sit-release-master-3.3.0.1140.apk");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");*/
        /*
         * ###### Desired Capabilities for Real Device ######
         */

        /*
         * ###### Desired Capabilities for Android Emulator ######
         */
        capabilities.setCapability("deviceName", "Android Emulator");
        //capabilities.setCapability(CapabilityType.VERSION, "7.0.0");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appWaitDuration", "40000");
        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "app-sit-release-master-3.3.0.1140.apk");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        /*
         * ###### Desired Capabilities for Android Emulator ######
         */

        /*
         * ###### Desired Capabilities for AWS Device Farm ######
         */
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        /*
         * ###### Desired Capabilities for AWS Device Farm ######
         */

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        subProc = new YouTripAndroidSubRoutine(UIElementKeyDict, driver);
        wait = subProc.getDriverWait();
        System.out.println("SETUP: Android Emulator ready");

    }

    @Test
    public void regTC01_selectTH() {

        try {
            //select TH from country selection
            subProc.procSelectCountry(Market.Thailand);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
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

    @Test
    public void regTC03_login_new_user_OTP() {
        try {
            //TODO separate test data
            String mprefix = "123";
            String mnumber = utils.getTimestamp();
            String email = ("qa+sg" + mnumber + "@you.co");

            //enter mobile phone, get OTP and continue as new user
            subProc.procOTPLogin(mprefix, mnumber, false);
            //enter email address and continue to welcome page
            subProc.procEnterEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC() {
        try {
            //TODO separate test data
            String surname = "Tester";
            String firstname = "Auto";
            String idNumber = utils.getNRIC();
            String dob = "01-01-1980";
            String nationality = "Singaporean";
            String addressLine1 = "Auto Test Address Line 1";
            String addressLine2 = "Auto Test Line 2";
            String postalCode = "123456";

            //select PC card
            subProc.procSelectCardType(true);
            //select NRIC method
            subProc.procSelectNRIC("Manual");
            //submit KYC
            subProc.procSubmitSGKYC(false, false, true, true,
                    surname, firstname, dob, idNumber, nationality,
                    addressLine1, addressLine2, postalCode);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC05_fullreject_PC_KYC_NRIC() {
        try {
            //perform full rejection
            subProc.procRejectKYC(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC() {
        AndroidElement el;
        try {
            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //select NRIC method
            subProc.procSelectNRIC("Manual");
            Thread.sleep(2000);
            //resubmit kyc after full rejection
            subProc.procSubmitSGKYC(true, false, true, true,
                    null, null, null, null, null, null,
                    null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC07_partialreject_PC_KYC_NRIC() {
        try {
            //perform partial rejection
            subProc.procRejectKYC(false);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC08_resubmit_partialreject_PC_KYC_NRIC() {
        AndroidElement el;
        try {
            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //resubmit kyc after partial rejection
            subProc.procSubmitSGKYC(false, true, true, true,
                    null, null, null, null, null, null,
                    null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC09_approved_PC_KYC_NRIC() {
        try {
            //perform approval
            subProc.procApproveKYC(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC10_logout_PC_KYC_NRIC() {
        try {
            //logout of account
            subProc.procLogout();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC11_submit_NPC_KYC_forgeiner() {
        try {
            //TODO separate test data
            String surname = "Tester";
            String firstname = "Auto";
            String idNumber = "QA" + utils.getNRIC();
            String dob = "01-01-1980";
            String nationality = "Singaporean";
            String addressLine1 = "Auto Test Address Line 1";
            String addressLine2 = "Auto Test Line 2";
            String postalCode = "123456";

            String ynum = "8130934511";

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
            //select NPC card
            subProc.procSelectCardType(false);
            //enter Y-number
            subProc.procEnterYNumber(ynum);
            //select Forgeiner method
            subProc.procSelectNonNRIC("Employment Pass");
            //submit KYC
            subProc.procSubmitSGKYC(false, false, false, false,
                    surname, firstname, dob, idNumber, nationality,
                    addressLine1, addressLine2, postalCode);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC12_fullreject_NPC_KYC_forgeiner() {
        try {
            //perform full rejection
            subProc.procRejectKYC(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC13_resubmit_fullreject_NPC_KYC_forgeiner() {
        AndroidElement el;
        try {
            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //select Forgeiner method
            subProc.procSelectNonNRIC("Employment Pass");
            //resubmit kyc after full rejection
            subProc.procSubmitSGKYC(true, false, false, false,
                    null, null, null, null, null, null,
                    null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC14_partialreject_NPC_KYC_forgeiner() {
        try {
            //perform partial rejection
            subProc.procRejectKYC(false);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC15_resubmit_partialreject_NPC_KYC_forgeiner() {
        AndroidElement el;
        try {
            //click retry button
            el = (AndroidElement) UIElementKeyDict.getElement(PageKey.LimitedHomePageElementDict, "btnRetry", driver);
            el.click();
            System.out.println("TEST STEP: Attention page - click retry button");
            Thread.sleep(2000);
            //resubmit kyc after partial rejection
            subProc.procSubmitSGKYC(false, true, false, false,
                    null, null, null, null, null, null,
                    null, null);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void regTC16_approved_NPC_KYC_forgeiner() {
        try {
            //perform approval
            subProc.procApproveKYC(false);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    //TODO blocked on activation - need magic link api updates
    /*@Test
    public void regTC17_activatecard_NPC_KYC_forgeiner() {

    }*/

    @Test
    public void regTC18_logout_NPC_KYC_forgeiner() {
        try {
            //logout of account
            subProc.procLogout();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /*@Test
    public void regTC19_login_existing_user_OTP() {
        try {
            //TODO separate test data
            String mprefix = "123";
            String mnumber = "1110418";
            String pin = "1111";

            //select SG from country selection
            subProc.procSelectCountry(Market.Singapore);
            //confirm country and continue to get started
            subProc.procConfirmCountry();
            //otp login using existing account
            subProc.procOTPLogin(mprefix, mnumber, false);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }*/

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

    @AfterClass
    public void end() {
        //driver.quit();
    }
}