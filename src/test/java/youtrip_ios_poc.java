import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class youtrip_ios_poc {

    IOSDriver driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        // Created object of DesiredCapabilities class.
        DesiredCapabilities capabilities = new DesiredCapabilities();

        // setup the capabilities for ios emulator
        //capabilities.setCapability("deviceName", "iPhone Simulator");
        //capabilities.setCapability(CapabilityType.VERSION, "11.4.1");
        //capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("platformName", "iOS");

        // setup the capabilities for real iphone
        capabilities.setCapability("deviceName", "iPhone 6s");
        capabilities.setCapability(CapabilityType.VERSION, "11.4.1");
        capabilities.setCapability("automationName", "XCUITest");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("bundleId", "co.you.youapp");

        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "YOUTrip.app");
        capabilities.setCapability("app", app.getAbsolutePath());

        //capabilities.setCapability("app", "apps/app-dev_sim-release-1.1.1.730.apk");
        // Set android appPackage desired capability.
        //capabilities.setCapability("appPackage", "co.you.youapp.dev");
        // Set android appActivity desired capability.
        //capabilities.setCapability("appActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        //capabilities.setCapability("intentAction", "android.intent.action.MAIN");
        //capabilities.setCapability("intentCategory", "android.intent.category.LAUNCHER");
        //co.you.youapp.ui.home.HomeActivity
        //co.you.youapp.ui.home.limited.LimitedHomeActivity
        //co.you.youapp.ui.magiclink.MagicLinkActivity
        //co.you.youapp.ui.base.SingleFragmentActivity

        // Created object of RemoteWebDriver will all set capabilities.
        // Set appium server address and port number in URL string.
        // It will launch calculator app in android device.
        driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void firstTest() {
        System.out.println("DC: starting firstTest");

        //handle the ios DEV alert
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //wait.until(ExpectedConditions.textToBePresentInElement(By.xpath("//XCUIElementTypeStaticText[@name='This is DEV Version']")), "This is DEV Version");
        System.out.println("DEV ALERT: dev version alert displayed");
        driver.findElement(By.xpath("//XCUIElementTypeButton[@name='Submit']")).click();
        System.out.println("DEV ALERT: clicked the submit button ");

        //wait for get started page and click the get started button
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.accessibilityId("Get Started")));
        System.out.println("GET STARTED: page displayed");
        driver.findElementByAccessibilityId("Get Started").click();
        System.out.println("GET STARTED: button clicked");

        // assert the next page - enter mobile number is displayed
        //wait.until(ExpectedConditions.textToBePresentInElement(By.accessibility-id("Enter Mobile Number"), "Enter Mobile Number"));
        assertTrue(driver.findElementByAccessibilityId("Enter Mobile Number").getText().equals("Enter Mobile NumberX"));
    }

    @AfterTest
    public void End() {
        driver.quit();
    }
}