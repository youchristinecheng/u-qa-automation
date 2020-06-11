package TestBased;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class mobileController {

    private YouTripAndroidUIElementKey UIElementKeyDict;
    YouTripAndroidSubRoutine subProc;
    //YoutripIosSubRoutine subProc;

    @BeforeTest
    public void setUp() throws MalformedURLException, InterruptedException {
        System.out.println("SETUP: Android device setup starting");
        //System.out.println("SETUP: iOS device setup starting");

        UIElementKeyDict = new YouTripAndroidUIElementKey();
        //UIElementKeyDict = new YouTripIosUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //Anndroid capabilities
        capabilities.setCapability("deviceName", "Galaxy Note 9");
        capabilities.setCapability("deviceId", "335730444f413498");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        capabilities.setCapability("autoGrantPermissions", "true");

        //iOS capabilities
        //capabilities.setCapability("deviceName", "YouTech iPhone");
        //capabilities.setCapability("platformVersion", "13.1.2");
        //capabilities.setCapability("udid", "cbfc3c66708111e5a48ad06f8917b951007bcb9e");
        //capabilities.setCapability("platformName", "iOS");
        //capabilities.setCapability("automationName", "XCUITest");
        //capabilities.setCapability("bundleId", "co.you.youapp");
        //capabilities.setCapability("xcodeOrgId", "2HWNYH89R4");
        //capabilities.setCapability("xcodeSigningId", "iPhone Developer");

        //specify app
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps/");
        File app = new File(appDir, "app-sit-release-HEAD-3.6.0.1189.apk");
        //File app = new File(appDir, "YOUTrip_SIT_3.6.0-sit_1437.ipa");
        capabilities.setCapability("app",app.getAbsolutePath());

        //connect to appium server
        Utils.setDriver(new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));
        System.out.println("SETUP: Android device setup finished");
        //Utils.setDriver(new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));
        //System.out.println("SETUP: iOS device setup finished");

        //confirm app opened
        AppiumDriver driver = Utils.getDriver();
        subProc = new YouTripAndroidSubRoutine(TestAccountData.Market.Singapore, UIElementKeyDict, driver);
        //subProc = new YoutripIosSubRoutine(TestAccountData.Market.Singapore, UIElementKeyDict, driver);
        subProc.procOnCountryScreen();
        System.out.println("SETUP: App opened and on country selection page");
    }
}
