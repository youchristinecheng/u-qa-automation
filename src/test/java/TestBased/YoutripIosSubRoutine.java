package TestBased;

import TestBased.TestAccountData.KYCDocType;
import TestBased.TestAccountData.Market;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSElement;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import static org.testng.Assert.assertEquals;

public class YoutripIosSubRoutine {

    private YouTripIosUIElementKey UIElementKeyDict;
    private AppiumDriver driver;
    private WebDriverWait wait;
    private Market currentMarket;
    public YouAPI api;


    public TestAccountData.Market getCurrentMarket() {
        return currentMarket;
    }

    public YoutripIosSubRoutine(Market market, YouTripIosUIElementKey UIElementKeyDict, AppiumDriver driver){
        this.UIElementKeyDict = UIElementKeyDict;
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        api = new YouAPI();
        currentMarket = market;
        api.setMarket(this.currentMarket);
    }

    public WebDriverWait getDriverWait() { return this.wait; }

    public void procHandleDevAlert() throws Exception {
        IOSElement el;
        try {
            // Handle the ios DEV alert By Force Logout
            System.out.println("DEV ALERT: dev version alert displayed");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.DevAlertElementDict, "Force Logout", driver);
            el.click();
        }catch(Exception e){
            throw e;
        }
    }

    public void procActivateDeepLinkFromSafari(String deepLinkURL) throws InterruptedException {
        // Hacking code for switch to safari and opend deeplink
        driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", "com.apple.mobilesafari"));
        WebElement webEl;

        System.out.println("TEST STEP: Launch Safari App for apply DeepLink");
        Map<String, Object> params = new HashMap<>();
        params.put("bundleId", "com.apple.mobilesafari");
        driver.executeScript("mobile: launchApp", params);
        Thread.sleep(3000);
        // Click on url field
        webEl = driver.findElementByAccessibilityId("URL");
        webEl.click();
        Thread.sleep(1000);
        // Set value in url field to desired url
        webEl = driver.findElementByAccessibilityId("URL");
        webEl.sendKeys(deepLinkURL);
        Thread.sleep(1000);
        // Go to this url
        driver.findElementByAccessibilityId("Go").click();
        Thread.sleep(2000);

        driver.findElementByAccessibilityId("Open").click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: Switch back to YouTrip App from DeepLink");
        params.clear();
        params.put("bundleId", "co.you.youapp");
        driver.executeScript("mobile: launchApp", params);
        Thread.sleep(5000);

        driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", "com.apple.mobilesafari"));
        Thread.sleep(1000);
    }

    public void procSelectCountry() throws Exception{
        this.procSelectCountry(this.getCurrentMarket());
    }

    public void procSelectCountry(Market country) throws Exception{
        String expectedResult;
        IOSElement el;
        try {
            // Handle the ios DEV alert By Force Logout
            // Wait for page change
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
            //Go to country page
            System.out.println("TEST STEP: Country Selection - on page");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "optionCountry", driver);
            el.click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Country Selection Page - click " + country.toString() + " button");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountrySelectionElementDict, country.toString(), driver);
            el.click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Verified Country Description - on page");
            System.out.println("!@#"+UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "lblDesc", driver).getText()+"!@#Z$");
            expectedResult = country.equals(Market.Singapore) ? "You’ll need an NRIC or a FIN to apply for a YouTrip Singapore account." : "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank).";
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "lblDesc", driver), expectedResult));
            
            System.out.println("TEST STEP: Country Page - continue as " + country);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: Get Started " + country + " Page - on page");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver);
            assertEquals(el.getText(), "Get Started");
        }catch(Exception e){
            throw e;
        }
    }

    public void procOTPLogin(String mprefix, String mnumber, String email, boolean isFirstTimeLogin) throws Exception{
        IOSElement el;

        System.out.println("TEST STEP: Get Started Page - on page");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver);
        el.click();

        System.out.println("TEST STEP: Mobile Number Page - on page");
        WebElement ele = UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "lblTitle", driver, true);
        if( el != null){
            System.out.println(ele.getText());
        }else{
            System.out.println("Nothing is found!");
        }
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "lblTitle", driver), "Login / Sign Up"));


        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "txtPrefix", driver);
        el.click();
        this.clearTextFieldValueForiOS12(el, 2);
        // Enter the test data value
        el.sendKeys(mprefix);

        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "txtPhoneNumber", driver);
        el.click();
        el.sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: OTP Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.OTPPageElementDict, "lblTitle", driver), "Mobile Verification"));
        // Get OTP from backdoor and input otp
        String otpCode = api.getOTP(mprefix, mnumber);
        Thread.sleep(10000);

        // Make use of app text field focus shifting functionality
        System.out.println("TEST STEP: OTP Page - entered OTP");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.OTPPageElementDict, "OTPDigit1", driver);
        el.sendKeys(otpCode);
        System.out.println("TEST STEP: OTP Page - Send OTP");

        Thread.sleep(2000);
        if(isFirstTimeLogin) {
            System.out.println("TEST STEP: Enter Email Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EmailPageElementDict, "lblTitle", driver), "Enter Your Email Address"));
            System.out.println("TEST DATA: Email address is " + email);
            System.out.println("TEST STEP: Enter Email Page - entered email");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EmailPageElementDict, "UserEmail", driver);
            el.sendKeys(email);
            System.out.println("TEST STEP: Enter Email Page - click Next button");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EmailPageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(2000);
        }
    }

    private void procSelectKYCDocType(String CardTypeDisplay, KYCDocType docType) throws Exception{
        IOSElement el;
        if(docType.equals(KYCDocType.MANUALNRIC)){
            System.out.println("TEST STEP: " + CardTypeDisplay + " Registration - NRIC");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.IdentityVerificationElementDict, "singaporeanPRRegister", driver);
            el.click();
            // Select on Manual NRIC
            System.out.println("TEST STEP: " + CardTypeDisplay + " Registration - Select Manual NRIC");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PRRegistrationElementDict, "btnSubmit", driver);
            el.click();
            Thread.sleep(2000);
        }else{
            System.out.println("TEST STEP: " + CardTypeDisplay + " Registration - Foreigner " + docType);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.IdentityVerificationElementDict, "foreignerRegister", driver);
            el.click();
            Thread.sleep(2000);

            if(docType.equals(KYCDocType.EMPLOYMENTPASS)
                    || docType.equals(KYCDocType.SPASS)
                    || docType.equals(KYCDocType.WORKPERMIT)
                    || docType.equals(KYCDocType.STUNDETSPASS)) {
                System.out.println("TEST STEP: " + CardTypeDisplay + " Registration - Select" + docType);
                String eleKeyName = "";
                if (docType.equals("Employment Pass")){
                    eleKeyName = "btnEmploymentPass";
                }else if (docType.equals("S Pass")){
                    eleKeyName = "btnSPass";
                }else if (docType.equals("Work Permit")){
                    eleKeyName = "btnWorkPermit";
                }else {
                    eleKeyName = "btnStudentPass";
                }
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.DocumentTypeRegistrationElementDict, eleKeyName, driver);
                el.click();
                Thread.sleep(2000);
            }else{
                System.out.println("TEST STEP: " + CardTypeDisplay + " Registration - Select Other");
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.IdentityVerificationElementDict, "btnOthers", driver);
                el.click();
                Thread.sleep(2000);

                throw new Exception("Selection of other is not yet handle");
            }
        }
    }

    public void procSubmitNonMyInfoKYC(int deviceOSVersion, boolean isPC, boolean isFullRejectFlow, boolean isPartialRejectFlow, KYCDocType docType,
                              String youId, String surname, String givenName,
                              String nameOnCard, String dateOfBirth, String nricNumber,
                              String addressLine1, String addressLine2, String postoalCode,
                              String newSurname, String newGivenName, String newNameOnCard, String newDateOfBirth, String newAddressLine1, String newAddressLine2) throws Exception{
        IOSElement el;
        String CardTypeDisplay = isPC ? "PC" : "NPC";

        if(!isPartialRejectFlow) {
            if(!isFullRejectFlow && !isPC) {
                System.out.println("TEST STEP: " + CardTypeDisplay + " Registration - Enter Y-Number");
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EnterYNumberPageElementDict, "lblTitle", driver), "Enter Y-Number"));
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EnterYNumberPageElementDict, "txtYouIdDigit1", driver);
                el.sendKeys(youId.substring(2));
                Thread.sleep(2000);
            }

            this.procSelectKYCDocType(CardTypeDisplay, docType);

            System.out.println("TEST STEP: KYC Start Page - on page");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.StepsPageElementDict, "lblTitle", driver), "Follow These Steps"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.StepsPageElementDict, "btnStart", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Process - Allow Camera access alert");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraAccessAlertElementDict, "btnOK", driver, true);
            if (el != null) {
                el.click();
                Thread.sleep(1000);
            }

            System.out.println("TEST STEP: KYC Process - Front of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Process - Confirm Front of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
            el.click();
            Thread.sleep(2000);

            System.out.println("TEST STEP: KYC Process - Start Back of NRIC Capture dialog");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.TurnBackPopUpPageElementDict, "lblDesc", driver), "Now capture the back of your NRIC."));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.TurnBackPopUpPageElementDict, "btnOK", driver);
            el.click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: KYC Process - Back of NRIC Capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
            el.click();
            Thread.sleep(1000);

            System.out.println("TEST STEP: KYC Process - Confirm Back of NRIC capture");
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
            el.click();
            Thread.sleep(2000);

            if(!docType.equals(KYCDocType.MANUALNRIC)) {
                System.out.println("TEST STEP: KYC Process - Start Proof of Address Capture dialog");
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblTitle", driver), "Proof of Address"));
                Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDesc", driver).getText(), "Take a photo of one of the following documents:");
                Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt1", driver).getText(), "•      Phone Bill within 6 months");
                Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt2", driver).getText(), "•      Utilities Bill within 6 months");
                Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt3", driver).getText(), "•      Bank Statement within 6 months");
                Assert.assertEquals(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "lblDescPt4", driver).getText(), "•      Other accepted documents");
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ProofOfAddressPopUpPageElementDict, "btnOK", driver);
                el.click();
                Thread.sleep(1000);

                System.out.println("TEST STEP: KYC Process - Proof of Address Capture");
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CameraPageElementDict, "btnShutter", driver);
                el.click();
                Thread.sleep(1000);

                System.out.println("TEST STEP: KYC Process - Confirm Proof of Address capture");
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PhotoConfirmPageElementDict, "btnAllDataIsReadable", driver);
                el.click();
                Thread.sleep(2000);
            }
        }

        System.out.println("TEST STEP: KYC Process - Enter User Name");
        String fullNameTitle = "";

        System.out.print(!docType.equals(KYCDocType.MANUALNRIC));
        if(docType.equals(KYCDocType.MANUALNRIC)){
            fullNameTitle = "Full Name (as per NRIC)";
        }else{
            fullNameTitle = "Full Name (as per Pass / Permit)";
        }
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "lblTitle", driver), fullNameTitle));
        if(isFullRejectFlow || isPartialRejectFlow) {
            if(deviceOSVersion >= 13) {
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver), surname));
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver), givenName));
            }
            if(isFullRejectFlow) {
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver);
                el.click();
                this.clearTextFieldValueForiOS12(el, surname.length());
                el.sendKeys(newSurname);
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver);
                el.click();
                this.clearTextFieldValueForiOS12(el, givenName.length());
                el.sendKeys(newGivenName);
                if(isPC) {
                    // by default app will update the NameOnCard as it has been updated
                    nameOnCard = newSurname + " " + newGivenName;
                }
            }
        }else {
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtSurname", driver);
            el.sendKeys(surname);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "txtGivenName", driver);
            el.sendKeys(givenName);
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(500);

        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "lblCheckAndConfirmTitle", driver), "Check and Confirm"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnCheckAndConfirmConfirm", driver);
        el.click();
        Thread.sleep(2000);

        if(isPC){
            System.out.println("TEST STEP: KYC Process - Enter NameOnCard");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NameOnCardElementDict, "lblTitle", driver), "Preferred Name"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NameOnCardElementDict, "txtCardName", driver);
            if(deviceOSVersion >= 13) {
                Assert.assertEquals(el.getText(), nameOnCard);
            }
            if(isFullRejectFlow) {
                el.click();
                this.clearTextFieldValueForiOS12(el, newSurname.length() + newGivenName.length() + 1);
                el.sendKeys(newNameOnCard);
                //Dismiss keyboard from UI
                driver.hideKeyboard();
            }
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NamePageElementDict, "btnNext", driver);
            el.click();
            Thread.sleep(2000);
        }

        System.out.println("TEST STEP: KYC Process - Enter Personal Information");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
        if(isFullRejectFlow || isPartialRejectFlow){
            if(deviceOSVersion >= 13) {
                String formattedDateOfBirth = dateOfBirth.substring(0, 2) + " - " + dateOfBirth.substring(2, 4) + " - " + dateOfBirth.substring(4);
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver), nricNumber));
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver), formattedDateOfBirth));
            }
            if(isFullRejectFlow) {
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver);
                el.click();
                this.clearTextFieldValueForiOS12(el, dateOfBirth.length());
                el.sendKeys(newDateOfBirth);
            }
        } else {
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtIdCardNumber", driver);
            el.sendKeys(nricNumber);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtDateOfBirth", driver);
            el.sendKeys(dateOfBirth);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnNationality", driver);
            el.click();
            Thread.sleep(500);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCNationalityElementDict, "Singaporean", driver);
            el.click();
            Thread.sleep(500);
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), "Personal Information"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnMale", driver);
            el.click();
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Enter Residential Address");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "lblTitle", driver), "Residential Address"));
        if(isFullRejectFlow || isPartialRejectFlow){
            if(deviceOSVersion >= 13) {
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver), addressLine1));
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver), addressLine2));
                wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver), postoalCode));
            }
            if (isFullRejectFlow) {
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver);
                this.clearTextFieldValueForiOS12(el, addressLine1.length());
                el.sendKeys(newAddressLine1);
                el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver);
                this.clearTextFieldValueForiOS12(el, addressLine2.length());
                el.sendKeys(newAddressLine2);
            }
        } else {
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine1", driver);
            el.sendKeys(addressLine1);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressLine2", driver);
            el.sendKeys(addressLine2);
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "txtAddressPostalCode", driver);
            el.sendKeys(postoalCode);
        }
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.ResidentialAddressElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: KYC Process - Submit KYC");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "lblTitle", driver), "Final Step"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "btnAgree", driver);
        el.click();
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCFinalStepElementDict, "btnSubmit", driver);
        el.click();
        Thread.sleep(2000);
        // Accept Keep On Notification
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCKeepUpdatePopUpElementDict, "btnAccept", driver, true);
        if (el != null)
            el.click();
    }

    public void procLoginToHomePage(Market market, String mprefix, String phoneNumber, String appPinCode) throws Exception {
        IOSElement el;
        this.procSelectCountry(market);
        this.procOTPLogin(mprefix, phoneNumber, "", false);

        Thread.sleep(2000);
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
        if(el != null) {
            el.click();
            Thread.sleep(2000);
        }
        this.procEnterAPPPinCode(appPinCode);

        Thread.sleep(2000);
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.NotificationAlertElementDict, "btnAllow", driver, true);
        if(el != null) {
            el.click();
            Thread.sleep(2000);
        }

        this.procVerifyInHomePage(market);
    }

    public void procEnterAPPPinCode(String appPinCode) throws Exception {
        System.out.println("TEST STEP: App PIN Code Page - Enter App PIN Code: " + appPinCode);
        for (char c : appPinCode.toCharArray()) {
            (UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.APPPinCodePageElementDict, Character.toString(c), driver)).click();
        }
    }

    public void procVerifyInHomePage() throws Exception{
        this.procVerifyInHomePage(this.currentMarket);
    }

    public void procVerifyInHomePage(Market market) throws Exception {
        System.out.println("TEST STEP: Home Page - Verify Home Page is Entered");
        wait.until(ExpectedConditions.visibilityOf(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "btnMenu", driver)));
        Assert.assertTrue(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "btnTopUp", driver).isDisplayed());
        Assert.assertTrue(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "btnExchange", driver).isDisplayed());
        Assert.assertTrue(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "btnCard", driver).isDisplayed());
        WebElement ele = UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "btnReferral", driver, true);
        if(market.equals(Market.Singapore)){
            Assert.assertNull(ele);
        }else{
            Assert.assertTrue(ele.isDisplayed());
        }
    }

    public void procLogoutFromHomePage()throws Exception {
        IOSElement el;
        System.out.println("TEST STEP: Logout From HomePage");
        UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "btnMenu", driver).click();
        Thread.sleep(2000);
        UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "menuBtnSetting", driver).click();
        Thread.sleep(2000);
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.SettingPageElementDict, "btnLogout", driver);
        el.click();
        Thread.sleep(3000);
        System.out.println("TEST STEP: Back to Country Selection page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "lblTitle", driver), "Where do you live?"));
        Assert.assertTrue(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.CountryPageElementDict, "optionCountry", driver).isDisplayed());
    }

    public void clearTextFieldValueForiOS12(IOSElement el, Integer existingTextLength) {
        for (int i = 0; i < existingTextLength; i++)
            el.sendKeys("\b");
    }

    public boolean procChangeAppPinFromHomePage(String oldAPPPinCode, String newAPPPINCode)throws Exception{
        boolean isForebackEnable = true;
        IOSElement el;
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "btnMenu", driver);
        el.click();
        Thread.sleep(2000);
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.HomePageElementDict, "menuBtnSetting", driver);
        el.click();
        Thread.sleep(2000);

        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.SettingPageElementDict, "btnChangePIN", driver, true);
        el.click();
        Thread.sleep(3000);

        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.APPPinCodePageElementDict, "lblChangePinTitle", driver), "Enter Current PIN"));
        this.procEnterAPPPinCode(oldAPPPinCode);
        Thread.sleep(2000);

        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.APPPinCodePageElementDict, "lblChangePinNewPinTitle", driver), "Create New PIN"));
        this.procEnterAPPPinCode(newAPPPINCode);
        Thread.sleep(2000);

        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.APPPinCodePageElementDict, "lblChangePinConfirmPinTitle", driver), "Confirm New PIN"));
        this.procEnterAPPPinCode(newAPPPINCode);
        Thread.sleep(5000);
        isForebackEnable = false;

        this.procVerifyInHomePage(Market.Singapore);
        return isForebackEnable;
    }

    public void unlockTestAccountData(TestAccountData testAccountData){
        System.out.println("TEARDOWN: Release TestUser");
        testAccountData.UnderUse = false;
        testAccountData.Card.UnderUse = false;
        this.api.data_updateTestUser(testAccountData);
    }

    public void proc_TH_OTPLogin(String mprefix, String mnumber) throws Exception{
        IOSElement el;

        System.out.println("TEST STEP: Get Started Page - on page");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver);
        el.click();

        System.out.println("TEST STEP: Mobile Number Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "lblTitle", driver), "Enter Mobile Number"));

        System.out.println("TEST DATA: Mobile Number is " + mprefix + " " + mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "txtPrefix", driver);
        el.click();
        this.clearTextFieldValueForiOS12(el, 2);
        // Enter the test data value
        el.sendKeys(mprefix);

        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "txtPhoneNumber", driver);
        el.click();
        el.sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.MobileNumberPageElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(2000);

        System.out.println("TEST STEP: OTP Page - on page");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.OTPPageElementDict, "lblTitle", driver), "Enter Code from SMS"));
        // Get OTP from backdoor and input otp
        String otpCode = api.getOTP(mprefix, mnumber);
        Thread.sleep(10000);

        // Make use of app text field focus shifting functionality
        System.out.println("TEST STEP: OTP Page - entered OTP");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.OTPPageElementDict, "OTPDigit1", driver);
        el.sendKeys(otpCode);
        System.out.println("TEST STEP: OTP Page - Send OTP");

        Thread.sleep(2000);
    }

    public void procTH_SubmitKYC(int deviceOSVersion, boolean isPCCardFlow, String thaiIdNumber, String youId) throws Exception {
        IOSElement el;
        if(!isPCCardFlow) {
            System.out.println("TEST STEP: NPC Registration - Enter Y-Number");
            wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EnterYNumberPageElementDict, "lblTitle", driver), "Enter Y-Number"));
            el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.EnterYNumberPageElementDict, "txtYouIdDigit1", driver);
            el.sendKeys(youId.substring(2));
            Thread.sleep(2000);
        }

        System.out.println("TEST STEP: KYC Process - Enter Thai ID");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "lblTitle", driver), "Enter ID Number"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "txtIdNumber", driver);
        el.sendKeys(thaiIdNumber);
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.PersonalInformationElementDict, "btnNext", driver);
        el.click();
        Thread.sleep(3000);

        System.out.println("TEST STEP: KYC Process - Authentication From KPlus");
        wait.until(ExpectedConditions.textToBePresentInElement(UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCKPLUSAuthenticationPageElementDict, "lblTitle", driver), "Register with K PLUS"));
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCKPLUSAuthenticationPageElementDict, "btnNoKPlus", driver);
        assertEquals(el.getText(), "Do not have K PLUS?");
        el = (IOSElement) UIElementKeyDict.getElement(YouTripIosUIElementKey.PageKey.KYCKPLUSAuthenticationPageElementDict, "btnOpenKPlus", driver);
        assertEquals(el.getText(), "Register with K PLUS");
        el.click();
        Thread.sleep(2000);
    }
}
