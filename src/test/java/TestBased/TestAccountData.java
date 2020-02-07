package TestBased;

import TestBased.YouTripIosUIElementKey.Market;

public class TestAccountData {
    public enum CardStatus{
        INACTIVE,
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


    public Market market;
    public String mprefix;
    public String mnumber;
    public String emailAddress;
    public KYCStatus kycStatus;

    public String youId;
    public String cardId;
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
        emailAddress = null;
        youId = null;
        cardId = null;
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
}