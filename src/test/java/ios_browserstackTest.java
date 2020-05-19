import TestBased.*;
import TestBased.TestAccountData.Market;
import io.appium.java_client.ios.IOSDriver;
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

import static org.testng.Assert.fail;


public class ios_browserstackTest {

    IOSDriver driver;
    YouTripIosUIElementKey UIElementKeyDict;
    YoutripIosSubRoutine subProc;
    WebDriverWait wait;
    TestAccountData testAccountData;
    boolean isRequriedReset;
    boolean isAppReset;
    boolean isForebackEnable;
    String defaultAPPPinCode;
    Integer osMainVerInt;
    YouFISAPI fisapi;

    @BeforeTest(alwaysRun = true)
    @org.testng.annotations.Parameters(value={"config", "device", "app", "build", "env"})
    public void setUp(String config_file, String device, @Optional() String appUrl, @Optional() String buildName, String env) throws Exception {

        System.out.println("SETUP: iOS device setup starting");

        //read config file values
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/" + config_file));
        JSONObject devices = (JSONObject) config.get("device");

        UIElementKeyDict = new YouTripIosUIElementKey();
        DesiredCapabilities capabilities = new DesiredCapabilities();

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

        if(username.equals("") && accessKey.equals("")){
            capabilities.setCapability("app", commonCapabilities.get("app"));
            driver = new IOSDriver<>(new URL("http://"+ config.get("server") + "/wd/hub"), capabilities);
        }else{
            capabilities.setCapability("app", appUrl);
            capabilities.setCapability("build", buildName);
            driver = new IOSDriver<>(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        testAccountData = null;
        UIElementKeyDict = new YouTripIosUIElementKey();
        defaultAPPPinCode = "1111";
        assert environment != null;
        subProc =  new YoutripIosSubRoutine(Market.valueOf(environment.get("market").toString()), UIElementKeyDict, driver);
        subProc.api.setYPEndPoint(environment.get("sg_youportalEndPoint").toString());
        subProc.api.setDataBackDoorEndPoint(environment.get("sg_databackdoorEndPoint").toString());
        subProc.api.setBackDoorEndPoint(environment.get("sg_backdoorEndPoint").toString(), true, null, null);

        if(env.equals("dev")) {
            fisapi = null;
        }else{
            fisapi = new YouFISAPI();
        }



        wait = subProc.getDriverWait();
        try {
            subProc.procHandleDevAlert();
        }catch(Exception e){
            fail(e.getMessage());
        }
        isRequriedReset = false;
        isAppReset = true;
        isForebackEnable = true;
        osMainVerInt = -1;
        System.out.println("TEST PARAMETER: iOS Main Version(Default): " + osMainVerInt);
        Map<String, Object> caps = ((IOSDriver) driver).getSessionDetails();
        String verStr = caps.get("sdkVersion").toString();
        osMainVerInt = Integer.parseInt(verStr.substring(0, verStr.indexOf(".")));
        System.out.println("TEST PARAMETER: iOS Main Version(In Session): " + osMainVerInt);
    }

    @AfterMethod (alwaysRun = true)
    public void TestMethodTeardown(ITestResult result){
        System.out.println("TEARDOWN: Resetting App");
        if(result.getStatus() == ITestResult.FAILURE) {
            isRequriedReset = true;
        }

        if(isRequriedReset) {
            isAppReset = true;
            driver.resetApp();
            try {
                subProc.procHandleDevAlert();
            } catch (Exception e) {
                fail(e.getMessage());
            }
        }else{
            isAppReset = false;
        }
    }

    @AfterTest (alwaysRun = true)
    public void End() {
        System.out.println("TEARDOWN: Tests Completed - Driver Quitting");
        driver.closeApp();
        driver.quit();
    }
}