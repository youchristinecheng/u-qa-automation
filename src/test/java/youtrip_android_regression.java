import TestBased.YouAPI;
import TestBased.Utils;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
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

import static org.testng.Assert.assertEquals;
public class youtrip_android_regression {

    AndroidDriver driver;
    YouAPI api;
    Utils utils;

    @BeforeTest
    public void setUp() throws MalformedURLException {

        api = new YouAPI();
        utils = new Utils();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // setup the capabilities for real device
        //capabilities.setCapability("deviceName", "Nexus 5");
        //capabilities.setCapability(CapabilityType.VERSION, "6.0.1");
        //capabilities.setCapability("automationName", "UiAutomator2");
        //capabilities.setCapability("platformName", "Android");

        // setup the capabilities for Android Emulator
        capabilities.setCapability("deviceName", "Android Emulator");
        //capabilities.setCapability(CapabilityType.VERSION, "7.0.0");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appWaitDuration", "40000");
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");



        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "app-sit-release-master-3.3.0.1140.apk");
        capabilities.setCapability("app", app.getAbsolutePath());

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("SETUP: Android Emulator ready");

    }

    @Test
    public void regTC01_selectTH() {
        //wait till on country page
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Where do you live?']"))), "Where do you live?"));
        System.out.println("TEST STEP: Country Page - on page");
        //click country selection button
        driver.findElement(By.id("co.you.youapp.dev:id/layoutSelectCountry")).click();
        System.out.println("TEST STEP: Country Selection - on page");
        //click TH option
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']")).click();
        System.out.println("TEST STEP: Country Selection Page - click TH button");
        //check returned back to country page with TH selected
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textDesc")).getText(), "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank).");
    }

    @Test
    public void regTC02_selectSG() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //wait till on country page
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Where do you live?']"))), "Where do you live?"));
        System.out.println("TEST STEP: Country Page - on page");
        //click country selection button
        driver.findElement(By.id("co.you.youapp.dev:id/layoutSelectCountry")).click();
        System.out.println("TEST STEP: Country Selection - on page");
        //click SG option
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']")).click();
        System.out.println("TEST STEP: Country Selection Page - click SG button");
        //wait until returned back to country page with SG selected
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textDesc"))), "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));
        //click confirm country
        driver.findElement(By.id("co.you.youapp.dev:id/buttonConfirm")).click();
        System.out.println("TEST STEP: Country Page - continue as SG");
        //wait till on get started page
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetStarted')]"))));
        System.out.println("TEST STEP: Get Started SG Page - on page");
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textButtonName")).getText(), "Get Started");
    }

    @Test
    public void regTC03_login_new_user_OTP() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //click get started button from start screen
        driver.findElement(By.id("co.you.youapp.dev:id/layoutBackground")).click();
        System.out.println("TEST STEP: Get Started SG Page - click Get Started Button");

        //wait till on enter mobile number page
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Enter Mobile Number']"))), "Enter Mobile Number"));
        System.out.println("TEST STEP: Mobile Number Page - on page");

        //generate new mobile number
        String mprefix = "123";
        String mnumber = utils.getTimestamp();
        System.out.println("TEST DATA: Mobile Number is " +mprefix+ " " +mnumber);
        //clear mobile prefix field and enter mobile prefix
        driver.findElement(By.id("co.you.youapp.dev:id/inputPrefix")).clear();
        driver.findElement(By.id("co.you.youapp.dev:id/inputPrefix")).sendKeys(mprefix);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        //enter mobile number
        driver.findElement(By.id("co.you.youapp.dev:id/inputEditText")).click();
        driver.findElement(By.id("co.you.youapp.dev:id/inputEditText")).sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        //click next button
        driver.findElement(By.id("co.you.youapp.dev:id/buttonGetSMS")).click();
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        //wait till on enter SMS page
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Enter Code from SMS']"))), "Enter Code from SMS"));
        System.out.println("TEST STEP: OTP Page - on page");

        //get OTP from backdoor and input otp
        String otpCode = api.getOTP(mprefix, mnumber);

        System.out.println("TEST DATA: OTP Code is " +otpCode);
        String otp1 = otpCode.substring(0);
        String otp2 = otpCode.substring(1);
        String otp3 = otpCode.substring(2);
        String otp4 = otpCode.substring(3);
        String otp5 = otpCode.substring(4);
        String otp6 = otpCode.substring(5);

        //enter OTP - note: need to enter each digit separately
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[1]/android.widget.EditText")).sendKeys(otp1);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[2]/android.widget.EditText")).sendKeys(otp2);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[3]/android.widget.EditText")).sendKeys(otp3);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[4]/android.widget.EditText")).sendKeys(otp4);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[5]/android.widget.EditText")).sendKeys(otp5);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[6]/android.widget.EditText")).sendKeys(otp6);
        System.out.println("TEST STEP: OTP Page - entered OTP");
        //wait till on enter email page
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Enter Email Address"));
        System.out.println("TEST STEP: Enter Email Page - on page");

        //generate new email address
        String email = ("qa+sg"+mnumber+"@you.co");
        System.out.println("TEST DATA: Email address is " +email);

        //input email
        driver.findElement(By.id("co.you.youapp.dev:id/inputEmail")).sendKeys(email);
        System.out.println("TEST STEP: Enter Email Page - entered email");
        //click next button
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Enter Email Page - click Next button");
        //wait till on welcome page
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Welcome"));
        System.out.println("TEST STEP: Welcome Page - on page");
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textTitle")).getText(), "Welcome");

    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //click get youtrip card for free
        driver.findElement(By.id("co.you.youapp.dev:id/buttonOrder")).click();
        System.out.println("TEST STEP: Welcome Page - click Get a YouTrip Card for Free button");
        //wait till on identity verification page
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']/android.widget.TextView"))), "Identity Verification"));
        System.out.println("TEST STEP: Identity Verification Page - on page");
        //click Singaporean and PR
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']")).click();
        System.out.println("TEST STEP: Identity Verification Page - click Singaporean/PR button");
        //wait till on Singaporean and PR page
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[@index='0']"))), "Singaporean / PR"));
        System.out.println("TEST STEP: Singaporean/PR Page - on page");
        //click submit manually
        driver.findElement(By.id("co.you.youapp.dev:id/textManual")).click();
        System.out.println("TEST STEP: Singaporean/PR Page - click submit manually link");
        //wait till on start of KYC page (just a few steps page)
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Just a Few Steps"));
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - on page");
        //click start KYC
        driver.findElement(By.id("co.you.youapp.dev:id/buttonStart")).click();
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click start now button");
        //accept the Android camera permission
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click allow YouTrip access to camera button");
        //wait till on page and take front NRIC photo
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonShutter")).click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click take photo button");
        //confirm front NRIC photo
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonConfirm")).click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click all data is readable button");
        //wait till on back of NRIC information pop up appears and confirm it
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Back of NRIC"));
        System.out.println("TEST STEP: KYC step 2 back of NRIC - on page");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonOK")).click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click on got it button from ID reminder dialog");
        //wait till on page and take back NRIC photo
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonShutter")).click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click take photo button");
        //confirm back NRIC photo
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonConfirm")).click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click all data is readable button");

        //TODO test data to separate
        String surname  = "Tester";
        String firstname = "Auto";

        //wait till on page, enter name and confirm
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/inputLastName")).sendKeys(surname);
        System.out.println("TEST STEP: Full Name (as per NRIC) page - input surname");
        driver.findElement(By.id("co.you.youapp.dev:id/inputGivenName")).sendKeys(firstname);
        System.out.println("TEST STEP: Full Name (as per NRIC) page - input firstname");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Full Name (as per NRIC) page - click Next button");
        //wait till check and confirm dialog appear and confirm
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Check and Confirm"));
        System.out.println("TEST STEP: Full Name (as per NRIC) page - check and confirm dialog appeared");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonPositive")).click();
        System.out.println("TEST STEP: Full Name (as per NRIC) page - on check and confirm dialog click Confirm button");

        //TODO test data to separate
        String newSurname = "Test";

        //wait till on page, clear name on card and enter new name
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/inputCardName")).clear();
        driver.findElement(By.id("co.you.youapp.dev:id/inputCardName")).sendKeys(newSurname+" "+firstname);
        System.out.println("TEST STEP: Preferred Name page - change name on card");
        //confirm new name
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Preferred Name page - click next button");

        //TODO test data - nric number is S1234567L need to randomise it
        String nricNum  = utils.getNRIC();
        String dob = "01-01-1980";
        String nationality = "Singaporean";

        //wait till on page and enter personal information
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/inputIDNumber")).sendKeys(nricNum);
        System.out.println("TEST STEP: Personal Information page - input NRIC number");
        driver.findElement(By.id("co.you.youapp.dev:id/inputDOB")).sendKeys(dob);
        System.out.println("TEST STEP: Personal Information page - input Date Of Birth");
        //click nationality option, which opens new page with drop down
        driver.findElement(By.id("co.you.youapp.dev:id/inputNationality")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //search for nationality and click the first option
        driver.findElement(By.id("co.you.youapp.dev:id/inputSearch")).sendKeys(nationality);
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout")).click();
        System.out.println("TEST STEP: Personal Information page - searched and selected a nationality");
        driver.findElement(By.id("co.you.youapp.dev:id/radioMale")).click();
        //select sex and continue
        System.out.println("TEST STEP: Personal Information page - click sex as male");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Personal Information page - click next button");

        //TODO test data to separate
        String addLine1  = "Auto Test Address Line 1";
        String addLine2 = "Auto Test Line 2";
        String postalCode = "123456";

        //wait till on page and enter residential address
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/inputAddress1")).sendKeys(addLine1);
        System.out.println("TEST STEP: Residential Address page - input address line 1");
        driver.findElement(By.id("co.you.youapp.dev:id/inputAddress2")).sendKeys(addLine2);
        System.out.println("TEST STEP: Residential Address page - input address line 2");
        driver.findElement(By.id("co.you.youapp.dev:id/inputPostal")).sendKeys(postalCode);
        System.out.println("TEST STEP: Residential Address page - input postal code");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Residential Address page - click next button");

        //wait till on page, confirm final steps and submit kyc
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/checkbox")).click();
        System.out.println("TEST STEP: Final Step page - click confirm TnC checkbox");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonSubmit")).click();
        System.out.println("TEST STEP: Final Step page - click submit button");
        //wait for marketing consent dialog and accept
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Be the First to Know"));
        System.out.println("TEST STEP: Marketing consent popup - on page");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonPositive")).click();
        System.out.println("TEST STEP: Final Step page - click Keep Me Updated button");
        //wait for thank you page and confirm
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textTitle")).getText(), "Thank You for Your Application");
        System.out.println("TEST STEP: KYC submitted successfully");
    }

    @Test
    public void regTC05_fullreject_PC_KYC_NRIC() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //get and store the KYC reference number
        String kycRefNo = driver.findElement(By.id("co.you.youapp.dev:id/textRefNumber")).getText();
        System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);
        //call YP full reject with Ref Number
        api.yp_fullReject(kycRefNo);
        //back to the app - wait for reject to be updated
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("co.you.youapp.dev:id/textButtonName"))));
        System.out.println("TEST STEP: KYC rejection received");
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textTitle")).getText(), "Attention");
    }

    @Test
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC() {

        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //driver.findElement(By.id("co.you.youapp.dev:id/button")).click();
        //System.out.println("TEST STEP: Attention page - click retry button");

        //repeat steps from identity verification
    }

    @Test
    public void regTC07_partialreject_PC_KYC_NRIC() {

    }

    @Test
    public void regTC08_resubmit_partialreject_PC_KYC_NRIC() {

    }

    @Test
    public void regTC09_approved_PC_KYC_NRIC() {

    }

    @AfterClass
    public void end() {
        driver.quit();
    }
}