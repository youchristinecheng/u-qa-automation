import TestBased.TestAccountData;
import TestBased.YouFISAPI;
import TestBased.YouTripAndroidSubRoutine;
import TestBased.YouTripAndroidUIElementKey;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import pro.truongsinh.appium_flutter.FlutterFinder;

public class android_flutterTest {

    AndroidDriver driver;
    WebDriverWait wait;
    FlutterFinder find;

    @BeforeTest(alwaysRun = true)
    public void setUp() throws Exception {

        System.out.println("SETUP: Android device setup starting");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //set capabilities
        capabilities.setCapability("automationName", "Flutter");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Galaxy Note 9");
        capabilities.setCapability("deviceId", "335730444f413498");
        capabilities.setCapability("version", "9.0.0");
        capabilities.setCapability("autoGrantPermissions", "true");

        //get app
        capabilities.setCapability("app", "/Users/danielchan/IdeaProjects/u-qa-automation/apps/app-debug.apk");

        //connect to appium server
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        wait = new WebDriverWait(driver, 5);
        System.out.println("SETUP: Android device setup finished");
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
