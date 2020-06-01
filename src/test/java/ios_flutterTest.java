import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pro.truongsinh.appium_flutter.FlutterFinder;

import java.net.URL;

public class ios_flutterTest {

    IOSDriver driver;
    WebDriverWait wait;
    FlutterFinder find;

    @BeforeTest(alwaysRun = true)
    public void setUp() throws Exception {

        System.out.println("SETUP: iOS device setup starting");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //set capabilities
        capabilities.setCapability("automationName", "Flutter");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("xcodeOrgId", "2HWNYH89R4");
        capabilities.setCapability("xcodeSigningId", "iPhone Developer");
        //capabilities.setCapability("autoGrantPermissions", "true");

        //for real device
        //capabilities.setCapability("deviceName", "YouTech iPhone");
        //capabilities.setCapability("platformVersion", "13.1.2");
        //capabilities.setCapability("udid", "cbfc3c66708111e5a48ad06f8917b951007bcb9e");
        //capabilities.setCapability("app", "/Users/danielchan/IdeaProjects/u-qa-automation/apps/Payload.ipa");

        //for emulator
        capabilities.setCapability("deviceName", "iPhone 11");
        capabilities.setCapability("platformVersion", "13.2");
        capabilities.setCapability("udid", "E4DC51F2-F053-49E0-ABA4-59BE5A253EBF");
        capabilities.setCapability("app", "/Users/danielchan/IdeaProjects/u-qa-automation/apps/Payload.ipa");


        //connect to appium server
        driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 5);
        System.out.println("SETUP: iOS device setup finished");
        find = new FlutterFinder(driver);
    }

    @Test
    public void firstFlutterTest() throws InterruptedException {
        //Country Selection - click country selection box
        Thread.sleep(5000);
        MobileElement changeCountryBtn = find.byValueKey("changeCountryBtn");
        changeCountryBtn.click();
        System.out.println("TEST STEP - Click change country button");
        Thread.sleep(5000);
        //Country Selection - confirm country
        MobileElement thButton = find.byValueKey("thBtn");
        thButton.click();
        System.out.println("TEST STEP - Click Thailand option");

    }

    @AfterMethod(alwaysRun = true)
    public void testMethodEnd(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            System.out.println("TEARDOWN: Test Case Failed - Resetting App");
            driver.resetApp();
        }
    }

    @AfterTest(alwaysRun = true)
    public void end() {
        System.out.println("TEARDOWN: Tests Completed - Driver Quitting");
        driver.quit();
    }
}
