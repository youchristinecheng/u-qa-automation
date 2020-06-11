package Steps;

import TestBased.TestAccountData;
import TestBased.Utils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KYCSteps extends BaseSteps {

    Utils utils = new Utils();

    @Given("^user wants to order \"([^\"]*)\" card$")
    public void user_orders_card(String cardtype) {
        //test data
        String mprefix = "123";
        String mnumber = utils.getTimestamp();
        String email = ("qa+sg" + mnumber + "@you.co");
        //select country
        //subProc.procSelectCountry(TestAccountData.Market.Singapore);
        //subProc.procConfirmCountry();
        //complete otp as new user
        //subProc.procOTPLogin(mprefix, mnumber, true);
        //subProc.procEnterEmail(email);
        //select card type
        if (cardtype.equals("PC")) {
            //subProc.procSelectCardType(true);
            System.out.println("perform kyc with PC");
        } else if (cardtype.equals("NPC")) {
            //subProc.procSelectCardType(false);
            System.out.println("perform kyc with NPC");
        }
    }

    //EXAMPLE TABLE
    @When("^user submits KYC with \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
    public void user_submit_kyc(String surname, String firstname, String nameoncard, String dob, String nationality,
                                String addLine1, String addLine2, String postcode){
            System.out.println("kyc - surname " +surname);
            System.out.println("kyc - firstname " +firstname);
            System.out.println("kyc - nameoncard " +nameoncard);
            System.out.println("kyc - dob " +dob);
            System.out.println("kyc - nationality " +nationality);
            System.out.println("kyc - addLine1 " +addLine1);
            System.out.println("kyc - addLine2 " +addLine2);
            System.out.println("kyc - postcode " +postcode);
    }

    @Then("^user will see limited home page$")
    public void user_see_limited_home_page(){
        System.out.println("then step");
    }
}
