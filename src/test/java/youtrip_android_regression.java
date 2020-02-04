import io.appium.java_client.android.AndroidDriver;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import kong.unirest.Unirest;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class youtrip_android_regression {

    AndroidDriver driver;

    @BeforeTest
    public void setUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        // setup the capabilities for real device
        //capabilities.setCapability("deviceName", "Nexus 5");
        //capabilities.setCapability(CapabilityType.VERSION, "6.0.1");
        //capabilities.setCapability("automationName", "UiAutomator2");
        //capabilities.setCapability("platformName", "Android");

        // setup the capabilities for Android Emulator
        capabilities.setCapability("deviceName", "Android Emulator");
        //capabilities.setCapability(CapabilityType.VERSION, "7.0.0");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appWaitDuration", "40000");
        capabilities.setCapability("appWaitPackage", "co.you.youapp.dev");
        capabilities.setCapability("appWaitActivity", "co.you.youapp.ui.base.SingleFragmentActivity");



        File filePath = new File(System.getProperty("user.dir"));
        File appDir = new File(filePath, "/apps");
        File app = new File(appDir, "app-sit-release-master-3.3.0.1140.apk");
        capabilities.setCapability("app", app.getAbsolutePath());

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("SETUP: Android Emulator ready");

    }

    @Test
    public void regTC01_selectTH() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Where do you live?']"))), "Where do you live?"));
        System.out.println("TEST STEP: Country Page - on page");
        driver.findElement(By.id("co.you.youapp.dev:id/layoutSelectCountry")).click();
        System.out.println("TEST STEP: Country Selection - on page");
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']")).click();
        System.out.println("TEST STEP: Country Selection Page - click TH button");
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textDesc")).getText(), "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank).");
    }

    @Test
    public void regTC02_selectSG() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Where do you live?']"))), "Where do you live?"));
        System.out.println("TEST STEP: Country Page - on page");
        driver.findElement(By.id("co.you.youapp.dev:id/layoutSelectCountry")).click();
        System.out.println("TEST STEP: Country Selection - on page");
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']")).click();
        System.out.println("TEST STEP: Country Selection Page - click SG button");
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textDesc"))), "You must have a NRIC or FIN to apply for a Singapore YouTrip account."));
        driver.findElement(By.id("co.you.youapp.dev:id/buttonConfirm")).click();
        System.out.println("TEST STEP: Country Page - continue as SG");
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetStarted')]"))));
        System.out.println("TEST STEP: Get Started SG Page - on page");
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textButtonName")).getText(), "Get Started");
    }

    @Test
    public void regTC03_login_new_user_OTP() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //click get started button from start screen
        //driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetStarted')]")).click();
        driver.findElement(By.id("co.you.youapp.dev:id/layoutBackground")).click();
        System.out.println("TEST STEP: Get Started SG Page - click Get Started Button");

        //enter mobile number and click next
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Enter Mobile Number']"))), "Enter Mobile Number"));
        System.out.println("TEST STEP: Mobile Number Page - on page");
        //driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputPrefix')]")).clear();
        driver.findElement(By.id("co.you.youapp.dev:id/inputPrefix")).clear();

        //TODO separate below function
        //generate new mobile number
        SimpleDateFormat formatter= new SimpleDateFormat("YYMMDDHHmmssSS");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        String mprefix = "123";
        String mnumber = formatter.format(date);
        System.out.println("TEST DATA: Mobile Number is " +mprefix+ " " +mnumber);

        //driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputPrefix')]")).sendKeys(mprefix);
        driver.findElement(By.id("co.you.youapp.dev:id/inputPrefix")).sendKeys(mprefix);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number prefix");
        //driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputEditText')]")).click();
        //driver.findElement(By.xpath("//android.widget.EditText[contains(@resource-id,'inputEditText')]")).sendKeys(mnumber);
        driver.findElement(By.id("co.you.youapp.dev:id/inputEditText")).click();
        driver.findElement(By.id("co.you.youapp.dev:id/inputEditText")).sendKeys(mnumber);
        System.out.println("TEST STEP: Mobile Number Page - inputted mobile number");
        //driver.findElement(By.xpath("//android.widget.FrameLayout[contains(@resource-id,'buttonGetSMS')]")).click();
        driver.findElement(By.id("co.you.youapp.dev:id/buttonGetSMS")).click();
        System.out.println("TEST STEP: Mobile Number Page - clicked Next button");

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.TextView[@text='Enter Code from SMS']"))), "Enter Code from SMS"));
        System.out.println("TEST STEP: OTP Page - on page");

        //TODO separate below function
        //get OTP from backdoor and input otp
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //String body = Unirest.get("http://backdoor.internal.sg.sit.you.co/onboarding/otp/123/2220001")
        String backdoorOTP = ("http://backdoor.internal.sg.sit.you.co/onboarding/otp/"+mprefix+"/"+mnumber);
        System.out.println("API CALL: " +backdoorOTP);

        String body = Unirest.get(backdoorOTP)
                .asJson()
                .getBody()
                .getObject()
                .getString("password");

        System.out.println(body);
        String otp1 = body.substring(0);
        String otp2 = body.substring(1);
        String otp3 = body.substring(2);
        String otp4 = body.substring(3);
        String otp5 = body.substring(4);
        String otp6 = body.substring(5);

        //driver.findElement(By.id("co.you.youapp.dev:id/inputPIN")).sendKeys(body);

        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[1]/android.widget.EditText")).sendKeys(otp1);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[2]/android.widget.EditText")).sendKeys(otp2);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[3]/android.widget.EditText")).sendKeys(otp3);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[4]/android.widget.EditText")).sendKeys(otp4);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[5]/android.widget.EditText")).sendKeys(otp5);
        driver.findElement(By.xpath("//android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[6]/android.widget.EditText")).sendKeys(otp6);
        System.out.println("TEST STEP: OTP Page - entered OTP");


        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Enter Email Address"));
        System.out.println("TEST STEP: Enter Email Page - on page");

        //TODO separate below function
        //generate new email address
        String email = ("qa+sg"+formatter.format(date)+"@you.co");
        System.out.println("TEST DATA: Email address is " +email);

        //input email and continue to welcome screen
        driver.findElement(By.id("co.you.youapp.dev:id/inputEmail")).sendKeys(email);
        System.out.println("TEST STEP: Enter Email Page - entered email");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Enter Email Page - click Next button");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Welcome"));
        System.out.println("TEST STEP: Welcome Page - on page");
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textTitle")).getText(), "Welcome");

    }

    @Test
    public void regTC04_submit_PC_KYC_NRIC() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        //click get youtrip card for free
        driver.findElement(By.id("co.you.youapp.dev:id/buttonOrder")).click();
        System.out.println("TEST STEP: Welcome Page - click Get a YouTrip Card for Free button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='0']/android.widget.TextView"))), "Identity Verification"));
        System.out.println("TEST STEP: Identity Verification Page - on page");

        //click Singaporean and PR
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[@index='1']")).click();
        System.out.println("TEST STEP: Identity Verification Page - click Singaporean/PR button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.xpath("//android.widget.ScrollView/android.widget.LinearLayout/android.widget.TextView[@index='0']"))), "Singaporean / PR"));

        //android.widget.LinearLayout[contains(@resource-id,'layoutPIN')]/android.widget.LinearLayout[1]/android.widget.EditText
        System.out.println("TEST STEP: Singaporean/PR Page - on page");

        //click submit manually
        driver.findElement(By.id("co.you.youapp.dev:id/textManual")).click();
        System.out.println("TEST STEP: Singaporean/PR Page - click submit manually link");
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Just a Few Steps"));
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - on page");

        //start KYC - front NRIC photo
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonStart")).click();
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click start now button");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
        System.out.println("TEST STEP: KYC start/ Just a Few Steps Page - click allow YouTrip access to camera button");
        //TODO below wait doesnt work on camera page
        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Step 1: FRONT of NRIC"));
        //System.out.println("TEST STEP: KYC step 1 front of NRIC - on page");

        //take front NRIC photo, confirm and submit
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonShutter")).click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click take photo button");

        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/clickRetry"))), "Retry"));
        //System.out.println("TEST STEP: KYC step 1 front of NRIC - photo taken, ready to confirm");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonConfirm")).click();
        System.out.println("TEST STEP: KYC step 1 front of NRIC - click all data is readable button");
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Back of NRIC"));
        System.out.println("TEST STEP: KYC step 2 back of NRIC - on page");
        //take back NRIC photo, confirm and submit
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonOK")).click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click on got it button from ID reminder dialog");
        //TODO below wait doesnt work on camera page
        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Step 2: BACK of NRIC"));
        //System.out.println("TEST STEP: KYC step 2 back of NRIC - on page");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/buttonShutter")).click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click take photo button");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/clickRetry"))), "Retry"));
        //System.out.println("TEST STEP: KYC step 2 back of NRIC - photo taken, ready to confirm");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonConfirm")).click();
        System.out.println("TEST STEP: KYC step 2 back of NRIC - click all data is readable button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //TODO need to add better page check conditions
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Full Name (as per NRIC)"));
        System.out.println("TEST STEP: Full Name (as per NRIC) page - on page");

        //complete surname, given name and submit
        String surname  = "Tester";
        String firstname = "Auto";
        driver.findElement(By.id("co.you.youapp.dev:id/inputLastName")).sendKeys(surname);
        System.out.println("TEST STEP: Full Name (as per NRIC) page - input surname");
        driver.findElement(By.id("co.you.youapp.dev:id/inputGivenName")).sendKeys(firstname);
        System.out.println("TEST STEP: Full Name (as per NRIC) page - input firstname");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Full Name (as per NRIC) page - click Next button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Check and Confirm"));
        System.out.println("TEST STEP: Full Name (as per NRIC) page - check and confirm dialog appeared");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonPositive")).click();
        System.out.println("TEST STEP: Full Name (as per NRIC) page - on check and confirm dialog click Confirm button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //TODO need to refactor below wait, not always working
        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Preferred Name"));
        //System.out.println("TEST STEP: Preferred Name page - on page");

        //check and edit name on card
        String newSurname = "Test";
        driver.findElement(By.id("co.you.youapp.dev:id/inputCardName")).clear();
        driver.findElement(By.id("co.you.youapp.dev:id/inputCardName")).sendKeys(newSurname+" "+firstname);
        System.out.println("TEST STEP: Preferred Name page - change name on card");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Preferred Name page - click next button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //TODO need to refactor below wait, not always working
        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Personal Information"));
        //System.out.println("TEST STEP: Personal Information page - on page");


        //enter personal information
        //TODO nric number is S1234567L need to randomise it
        String nricNum  = "S1234567A";
        String dob = "01-01-1980";
        String nationality = "Singaporean";

        driver.findElement(By.id("co.you.youapp.dev:id/inputIDNumber")).sendKeys(nricNum);
        System.out.println("TEST STEP: Personal Information page - input NRIC number");
        driver.findElement(By.id("co.you.youapp.dev:id/inputDOB")).sendKeys(dob);
        System.out.println("TEST STEP: Personal Information page - input Date Of Birth");
        driver.findElement(By.id("co.you.youapp.dev:id/inputNationality")).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/inputSearch")).sendKeys(nationality);
        driver.findElement(By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout")).click();
        System.out.println("TEST STEP: Personal Information page - searched and selected a nationality");
        driver.findElement(By.id("co.you.youapp.dev:id/radioMale")).click();
        System.out.println("TEST STEP: Personal Information page - click sex as male");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Personal Information page - click next button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //TODO need to refactor below wait, not always working
        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Residential Address"));
        //System.out.println("TEST STEP: Residential Address page - on page");

        //enter residential address
        String addLine1  = "Auto Test Address Line 1";
        String addLine2 = "Auto Test Line 2";
        String postalCode = "123456";

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/inputAddress1")).sendKeys(addLine1);
        System.out.println("TEST STEP: Residential Address page - input address line 1");
        driver.findElement(By.id("co.you.youapp.dev:id/inputAddress2")).sendKeys(addLine2);
        System.out.println("TEST STEP: Residential Address page - input address line 2");
        driver.findElement(By.id("co.you.youapp.dev:id/inputPostal")).sendKeys(postalCode);
        System.out.println("TEST STEP: Residential Address page - input postal code");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonNext")).click();
        System.out.println("TEST STEP: Residential Address page - click next button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //TODO need to refactor below wait, not always working
        //wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Final Step"));
        //System.out.println("TEST STEP: Final Step page - on page");

        //confirm final steps and submit kyc
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("co.you.youapp.dev:id/checkbox")).click();
        System.out.println("TEST STEP: Final Step page - click confirm TnC checkbox");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonSubmit")).click();
        System.out.println("TEST STEP: Final Step page - click submit button");
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.textToBePresentInElement((driver.findElement(By.id("co.you.youapp.dev:id/textTitle"))), "Be the First to Know"));
        System.out.println("TEST STEP: Marketing consent popup - on page");
        driver.findElement(By.id("co.you.youapp.dev:id/buttonPositive")).click();
        System.out.println("TEST STEP: Final Step page - click Keep Me Updated button");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textTitle")).getText(), "Thank You for Your Application");
        System.out.println("TEST STEP: KYC submitted successfully");
    }

    @Test
    public void regTC05_fullreject_PC_KYC_NRIC() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String kycRefNo = driver.findElement(By.id("co.you.youapp.dev:id/textRefNumber")).getText();
        System.out.println("TEST DATA: KYC submission reference number is " +kycRefNo);

        SimpleDateFormat formatter= new SimpleDateFormat("YYMMDDHHmmssSS");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        String timestamp = formatter.format(date);

        //login to YP and get token using backdoor
        String backdoorYP = ("http://backdoor.internal.sg.sit.you.co/youportal/token?scopes=https://www.googleapis.com/auth/userinfo.email,https://www.googleapis.com/auth/userinfo.profile,openid");
        System.out.println("API CALL: " +backdoorYP);

        String ypToken = Unirest.get(backdoorYP)
                .header("x-request-id", "token"+timestamp)
                .asJson()
                .getBody()
                .getObject()
                .getString("token");

        System.out.println("DAN TEST - YP token: " +ypToken);



        //get KYC record from reference number on screen
        String url_getKYC = ("http://yp.internal.sg.sit.you.co/api/kyc?ReferenceIDs="+kycRefNo);
        System.out.println("API CALL: " +url_getKYC);

        HttpResponse<JsonNode> getKYCjsonResponse = Unirest.get(url_getKYC)
                .header("Authorization", "Bearer "+ypToken)
                .header("x-request-id", "getKYC"+timestamp)
                .header("x-yp-role", "Singapore Admin")
                .asJson();

        //get full JSON response body
        JSONObject responsejson = getKYCjsonResponse.getBody().getObject();
        //get first page result array
        JSONArray results = responsejson.getJSONArray("FirstPageResults");
        //get KYC ID
        JSONObject obj = results.getJSONObject(0);
        String ypKycID = obj.getString("ID");

        System.out.println("DAN TEST - KYC ID: " +ypKycID);

        //full reject KYC record
        String url_fullRejectKYC = ("http://yp.internal.sg.sit.you.co/api/kyc/"+ypKycID+"/reject");
        System.out.println("API CALL: " +url_fullRejectKYC);

        HttpResponse<JsonNode> rejectKYCjsonResponse = Unirest.put(url_fullRejectKYC)
                .header("Authorization", "Bearer "+ypToken)
                .header("x-request-id", "fullRejectKYC"+timestamp)
                .header("x-yp-role", "Singapore Admin")
                .header("Content-Type", "application/json")
                .body("{\"Reason\":\"[AUTO TEST: full KYC rejection testing\"}")
                .asJson();

        assertEquals(200, rejectKYCjsonResponse.getStatus());

        //back to the app for checking
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("co.you.youapp.dev:id/textButtonName"))));
        System.out.println("TEST STEP: KYC rejection received");
        assertEquals(driver.findElement(By.id("co.you.youapp.dev:id/textTitle")).getText(), "Attention");
    }

    @Test
    public void regTC06_resubmit_fullreject_PC_KYC_NRIC() {

        //WebDriverWait wait = new WebDriverWait(driver, 10);
        //driver.findElement(By.id("co.you.youapp.dev:id/button")).click();
        //System.out.println("TEST STEP: Attention page - click retry button");

        //repeat steps from identity verification
    }

    @Test
    public void regTC07_partialreject_PC_KYC_NRIC() {

    }

    @Test
    public void regTC08_resubmit_partialreject_PC_KYC_NRIC() {

    }

    @Test
    public void regTC09_approved_PC_KYC_NRIC() {

    }

    @AfterClass
    public void end() {
        driver.quit();
    }
}