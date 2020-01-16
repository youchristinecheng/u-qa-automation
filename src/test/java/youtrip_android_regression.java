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

import static org.testng.Assert.assertTrue;

public class youtrip_android_regression {

    AndroidDriver driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // setup the capabilities for real device
        //capabilities.setCapability("deviceName", "Nexus 5");
        //capabilities.setCapability(CapabilityType.VERSION, "6.0.1");
        //capabilities.setCapability("automationName", "UiAutomator2");
        //capabilities.setCapability("platformName", "Android");

        // setup the capabilities for Android Emulator
        capabilities.setCapability("deviceName", "Pixel 2");
        //capabilities.setCapability(CapabilityType.VERSION, "7.0.0");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appWaitDuration", "40000");
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");



        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps/Android/");
        File app = new File(appDir, "app-sit-release-master-3.3.0.1140.apk");
        capabilities.setCapability("app", app.getAbsolutePath());

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4444/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("SETUP: Android Emulator ready");

        //WebDriverWait wait = new WebDriverWait(driver, 20);
        //wait.until(ExpectedConditions.(By.xpath("//android.widget.TextView[@text='Where do you live?']"), "Where do you live?"));
        //System.out.println("TEST STEP: Country Selection Page - on page");
    }

    @Test
    public void regTC01_selectTH() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Where do you live?']"))), "Where do you live?"));
        System.out.println("TEST STEP: Country Page - on page");
        //driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.widget.LinearLayout[@index='1']")).click();
        driver.findElement(By.id("co.you.youapp.dev:id/layoutSelectCountry")).click();
        System.out.println("TEST STEP: Country Selection - on page");
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']")).click();
        System.out.println("TEST STEP: Country Selection Page - click TH button");
        assertTrue(driver.findElement(By.id("co.you.youapp.dev:id/textDesc")).getText().equals("You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank)."));
    }

    @Test
    public void regTC02_selectSG() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Where do you live?']"))), "Where do you live?"));
        System.out.println("TEST STEP: Country Page - on page");
        //driver.findElement(By.xpath("//android.support.v7.widget.RecyclerView/android.widget.LinearLayout[@index='1']")).click();
        driver.findElement(By.id("co.you.youapp.dev:id/layoutSelectCountry")).click();
        System.out.println("TEST STEP: Country Selection - on page");
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']")).click();
        System.out.println("TEST STEP: Country Selection Page - click SG button");
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textDesc"))), "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));
        driver.findElement(By.id("co.you.youapp.dev:id/buttonConfirm")).click();
        System.out.println("TEST STEP: Country Page - continue as SG");
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetStarted')]"))));
        System.out.println("TEST STEP: Get Started SG Page - on page");
        assertTrue(driver.findElement(By.id("co.you.youapp.dev:id/textButtonName")).getText().equals("Get Started"));
    }

    @Test
    public void regTC03_login_new_user_OTP() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //click get started button from start screen
        driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetStarted')]")).click();
        System.out.println("TEST STEP: Get Started SG Page - click Get Started Button");

        //enter mobile number and click next
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Enter Mobile Number']"))), "Enter Mobile Number"));
        System.out.println("TEST STEP: Mobile Number Page - on page");
        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputPrefix')]")).clear();

        //TODO - need to randomise the phone number
        String mprefix, mnumber;
        mprefix = "123";
        mnumber = "2220001";

        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputPrefix')]")).sendKeys(mprefix);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputEditText')]")).click();
        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputEditText')]")).sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetSMS')]")).click();
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");

        //get OTP from backdoor and input otp
        //TODO - parameterise the mcc + phone
        driver.get("http://backdoor.internal.sg.sit.you.co/onboarding/otp/123/2220001");





    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC() {

    }

    @Test
    public void regTC05_fullreject_PC_KYC_NRIC() {

    }

    @Test
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC() {

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