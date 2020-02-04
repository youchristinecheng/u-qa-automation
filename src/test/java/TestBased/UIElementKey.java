package TestBased;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import sun.reflect.annotation.ExceptionProxy;

public class UIElementKey {
    public enum FindMethod{
        ID,
        ACCESSIBILITYID,
        XPATH,
        ELEMENTTYPE,
        TAGNAME
    }
    private String keyId;
    private FindMethod findBy;

    public UIElementKey(String keyId, FindMethod findBy){
        this.keyId = keyId;
        this.findBy = findBy;
    }

    public WebElement getIOSElement(IOSDriver driver) throws NotFoundException {
        try {
            switch (this.findBy) {
                case ACCESSIBILITYID:
                    return driver.findElementByAccessibilityId(this.keyId);
                case XPATH:
                    return driver.findElementByXPath(this.keyId);
                default:
                    throw new NotFoundException("element not found");
            }
        }catch(Exception e){
            throw new NotFoundException("element not found");
        }
    }

    public WebElement getAndroidElement(AndroidDriver driver) throws NotFoundException {
        try {
            switch (this.findBy) {
                case ID:
                    return driver.findElementById(this.keyId);
                case XPATH:
                    return driver.findElementByXPath(this.keyId);
                default:
                    throw new NotFoundException("element not found");
            }
        }catch(Exception e){
            throw new NotFoundException("element not found");
        }
    }
}
