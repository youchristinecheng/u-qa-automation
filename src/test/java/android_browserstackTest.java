import TestBased.*;
import TestBased.TestAccountData.Market;
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
import java.util.concurrent.TimeUnit;


public class android_browserstackTest {

    AndroidDriver driver;
    YouTripAndroidUIElementKey UIElementKeyDict;
    YouTripAndroidSubRoutine subProc;
    WebDriverWait wait;
    TestAccountData testAccountData;

    @BeforeTest(alwaysRun = true)
    @Parameters({"config", "device", "app", "build", "env"})
    public void setUp(String config_file, String device, @Optional() String appUrl, @Optional() String buildName, String env) throws Exception {

        System.out.println("SETUP: Android device setup starting");
        UIElementKeyDict = new YouTripAndroidUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        //read config file
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/" + config_file));
        JSONObject devices = (JSONObject) config.get("device");

        //read environment config file
        JSONObject envconfig = (JSONObject) parser.parse(new FileReader("src/test/resources/env.json"));
        JSONObject environment = null;
        if(envconfig.containsKey(env))
            environment = (JSONObject) envconfig.get(env);

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

        //set dynamic capabilities for app hash value and build name
        //if local config then use those in config file
        if (config_file.equals("android_conf_debug_local.json")) {
            capabilities.setCapability("app", commonCapabilities.get("app"));
        } else {
            capabilities.setCapability("app", appUrl);
            capabilities.setCapability("build", buildName);
        }

        //setup android specific capabiilties
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");
        capabilities.setCapability("autoGrantPermissions", "true");

        //connect to appium server
        if(username.equals("") && accessKey.equals("")){
            driver = new AndroidDriver<>(new URL("http://"+ config.get("server") + "/wd/hub"), capabilities);
        }else{
            driver = new AndroidDriver<>(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        testAccountData = null;
        assert environment != null;
        subProc = new YouTripAndroidSubRoutine(Market.valueOf(environment.get("market").toString()), UIElementKeyDict, driver);
        subProc.api.setYPEndPoint(environment.get("sg_youportalEndPoint").toString());
        subProc.api.setDataBackDoorEndPoint(environment.get("sg_databackdoorEndPoint").toString());
        subProc.api.setBackDoorEndPoint(environment.get("sg_backdoorEndPoint").toString(), true, null, null);

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