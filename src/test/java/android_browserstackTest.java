import TestBased.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.net.URL;
import java.io.FileReader;
import java.util.Map;
import java.util.Iterator;


public class android_browserstackTest {

    AndroidDriver driver;
    YouTripAndroidUIElementKey UIElementKeyDict;
    YouTripAndroidSubRoutine subProc;
    WebDriverWait wait;

    @BeforeTest(alwaysRun = true)
    @Parameters({"config", "device", "app", "build"})
    public void setUp(String config_file, String device, String appUrl, String buildName) throws Exception {

        System.out.println("SETUP: Android device setup starting");

        //read config file values
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/" + config_file));
        JSONObject devices = (JSONObject) config.get("device");

        UIElementKeyDict = new YouTripAndroidUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //set device capabilites
        Map<String, String> deviceCapabilities = (Map<String, String>) devices.get(device);
        Iterator it = deviceCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        //set common capabilities
        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(capabilities.getCapability(pair.getKey().toString()) == null){
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if(username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if(accessKey == null) {
            accessKey = (String) config.get("key");
        }

        //String app = System.getenv("BROWSERSTACK_APP_ID");
        //if(app != null && !app.isEmpty()) {
        //    capabilities.setCapability("app", app);
        //}

        //set dynamic capabilities for app hash value and build name
        capabilities.setCapability("app", appUrl);
        capabilities.setCapability("build", buildName);

        //setup android specific capabiilties
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        capabilities.setCapability("autoGrantPermissions", "true");

        driver = new AndroidDriver<>(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
        subProc = new YouTripAndroidSubRoutine(UIElementKeyDict, driver);
        wait = subProc.getDriverWait();

        System.out.println("SETUP: Android device setup finished");
    }

    @AfterMethod (alwaysRun = true)
    public void testMethodEnd(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            System.out.println("TEARDOWN: Test Case Failed - Resetting App");
            driver.resetApp();
        }
    }

    @AfterTest (alwaysRun = true)
    public void end() {
        System.out.println("TEARDOWN: Tests Completed - Driver Quitting");
        driver.quit();
    }

}