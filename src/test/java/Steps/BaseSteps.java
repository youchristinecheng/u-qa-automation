package Steps;

import TestBased.*;
import io.appium.java_client.AppiumDriver;

public class BaseSteps {
    AppiumDriver driver = Utils.getDriver();
    YouTripAndroidUIElementKey UIElementKeyDict = new YouTripAndroidUIElementKey();
    YouTripAndroidSubRoutine subProc = new YouTripAndroidSubRoutine(TestAccountData.Market.Singapore, UIElementKeyDict, driver);

}
