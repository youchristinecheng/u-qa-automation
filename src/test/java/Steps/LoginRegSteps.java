package Steps;

import TestBased.YouTripAndroidUIElementKey;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.testng.Assert.assertEquals;

public class LoginRegSteps extends BaseSteps {

    @Given("^user has launched the app and is on country selection page$")
    public void user_on_country_selection() {
        try {
            subProc.procOnCountryScreen();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("^user select \"([^\"]*)\" country$")
    public void user_select_country(String country) {
        try {
            AndroidElement el;
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "optionCountry", driver);
            el.click();
            System.out.println("TEST STEP: Country Page - clicked select country");
            Thread.sleep(2000);
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountrySelectionElementDict, country, driver);
            el.click();
            System.out.println("TEST STEP: Country Selection Page - click " + country + " button");
            if (country.equals("SG")){
                Thread.sleep(2000);
                assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "countryDesc", driver)).getText(), "You must have a NRIC or FIN to apply for a Singapore YouTrip account.");
                System.out.println("TEST STEP: Country Page - verified country description");
            } else if (country.equals("TH")){
                Thread.sleep(2000);
                assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "countryDesc", driver)).getText(), "You must have a Thailand ID to apply for a Thailand YouTrip account (powered by KBank).");
                System.out.println("TEST STEP: Country Page - verified country description");
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    @When("^user confirms country$")
    public void user_confirm_country() {
        try {
            Thread.sleep(3000);
            AndroidElement el;
            el = (AndroidElement) UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.CountryPageElementDict, "btnConfirm", driver);
            el.click();
            Thread.sleep(10000);
            System.out.println("TEST STEP: Country Page - click continue button");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("^user will see get started page$")
    public void userWillSeeGetStartedPage() {
        try {
            Thread.sleep(3000);
            assertEquals((UIElementKeyDict.getElement(YouTripAndroidUIElementKey.PageKey.GetStartedPageElementDict, "btnGetStarted", driver)).getText(), "Get Started");
            System.out.println("TEST STEP: Get Started Page - on page");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}