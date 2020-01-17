import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import kong.unirest.Unirest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    }

    @Test
    public void regTC01_selectTH() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Where do you live?']"))), "Where do you live?"));
        System.out.println("TEST STEP: Country Page - on page");
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

        //generate unique mobile number
        SimpleDateFormat formatter= new SimpleDateFormat("YYMMDDHHmmssSS");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        String mprefix = "123";
        String mnumber = formatter.format(date);
        System.out.println("TEST DATA: Mobile Number is " +mprefix+ " " +mnumber);

        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputPrefix')]")).sendKeys(mprefix);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputEditText')]")).click();
        driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputEditText')]")).sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetSMS')]")).click();
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Enter Code from SMS']"))), "Enter Code from SMS"));
        System.out.println("TEST STEP: OTP Page - on page");

        //get OTP from backdoor and input otp
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //String body = Unirest.get("http://backdoor.internal.sg.sit.you.co/onboarding/otp/123/2220001")
        String backdoorOTP = ("http://backdoor.internal.sg.sit.you.co/onboarding/otp/"+mprefix+"/"+mnumber);
        System.out.println("API CALL: " +backdoorOTP);

        String body = Unirest.get(backdoorOTP)
                .asJson()
                .getBody()
                .getObject()
                .getString("password");

        System.out.println(body);
        String otp1 = body.substring(0);
        String otp2 = body.substring(1);
        String otp3 = body.substring(2);
        String otp4 = body.substring(3);
        String otp5 = body.substring(4);
        String otp6 = body.substring(5);

        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[1]/android.widget.EditText")).sendKeys(otp1);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[2]/android.widget.EditText")).sendKeys(otp2);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[3]/android.widget.EditText")).sendKeys(otp3);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[4]/android.widget.EditText")).sendKeys(otp4);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[5]/android.widget.EditText")).sendKeys(otp5);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[6]/android.widget.EditText")).sendKeys(otp6);
        System.out.println("TEST STEP: OTP Page - entered OTP");

        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@resource-id, 'textTitle]"))), "Enter Email Address"));
        System.out.println("TEST STEP: Enter Email Page - on page");


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