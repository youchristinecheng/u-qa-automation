package TestBased;

import TestBased.YouTripIosUIElementKey.Market;
import org.json.JSONException;
import org.json.JSONObject;

public class TestAccountData {
    public enum CardStatus{
        INACTIVE,
        NEWACTIVE,
        ACTIVE,
        LOCKED,
        SUSPENDED,
        REPLACE
    }
    public enum KYCStatus{
        SUBMIT,
        REJECT,
        PARTIALREJECT,
        CLEAR
    }

    public enum CardType{
        PC,
        NPC
    }


    public Market market;
    public String mprefix;
    public String mnumber;
    public String userId;
    public String emailAddress;
    public KYCStatus kycStatus;

    public String youId;
    public String cardId;
    public CardType cardType;
    public CardStatus cardStatus;

    public String surname;
    public String givenName;
    public String nameOnCard;
    public String nricNumber;
    public String dateOfBirth;
    public String addressLine1;
    public String addressLine2;
    public String postoalCode;

    public TestAccountData() {
        mprefix = null;
        mnumber = null;
        userId = null;
        emailAddress = null;
        youId = null;
        cardId = null;
        cardType = null;
        cardStatus = null;
        market = null;

        surname = "";
        givenName = "";
        nameOnCard = "";
        nricNumber = "";
        dateOfBirth = "";
        addressLine1 = "";
        addressLine2 = "";
        postoalCode = "";
    }

    public void print(){
        JSONObject newJobj = new JSONObject();
        newJobj.put("userid", this.userId);
        newJobj.put("prefix", this.mprefix);
        newJobj.put("phonenumber", this.mnumber);
        newJobj.put("emailaddress", this.emailAddress);
        newJobj.put("cardtype", this.cardType.toString());
        newJobj.put("kycstatus", this.kycStatus.toString());

        newJobj.put("surename", this.surname);
        newJobj.put("givenname", this.givenName);
        newJobj.put("nameoncard", this.nameOnCard);
        newJobj.put("dateofbirth", this.dateOfBirth);
        newJobj.put("nricnumber", this.nricNumber);
        newJobj.put("addressline1", this.addressLine1);
        newJobj.put("addressline2", this.addressLine2);
        newJobj.put("postalcode", this.postoalCode);

        if(this.cardType.equals(CardType.NPC)) {
            System.out.println("CARD DATA EXPORT: youid - " + this.youId);
            System.out.println("CARD DATA EXPORT: cardid - " + this.cardId);
            System.out.println("CARD DATA EXPORT: cardstatus - " + this.cardStatus);
            System.out.println("CARD DATA EXPORT: numofreplace - 0");

            JSONObject cardJobj = new JSONObject();
            cardJobj.put("youid", this.youId);
            cardJobj.put("cardid", this.cardId);
            cardJobj.put("cardstatus", this.cardStatus.toString());
            cardJobj.put("numofreplace", 0);

            newJobj.put("card", cardJobj);
        }
        System.out.println(newJobj.toString(4));
    }
}