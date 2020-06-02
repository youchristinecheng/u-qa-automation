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

    @BeforeTest
    public void setUp() throws MalformedURLException, InterruptedException {
        System.out.println("SETUP: Android device setup starting");
        UIElementKeyDict = new YouTripAndroidUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //real device - note 9
        capabilities.setCapability("deviceName", "Galaxy Note 9");
        capabilities.setCapability("deviceId", "335730444f413498");

        //set capabilities
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        capabilities.setCapability("autoGrantPermissions", "true");

        //specify app
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apps/");
        File app = new File(appDir, "app-sit-release-HEAD-3.6.0.1189.apk");
        capabilities.setCapability("app",app.getAbsolutePath());

        //connect to appium server
        Utils.setDriver(new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities));
        System.out.println("SETUP: Android device setup finished");

        //confirm app opened
        AppiumDriver driver = Utils.getDriver();
        subProc = new YouTripAndroidSubRoutine(TestAccountData.Market.Singapore, UIElementKeyDict, driver);
        subProc.procOnCountryScreen();
        System.out.println("SETUP: App opened and on country selection page");
    }


}
