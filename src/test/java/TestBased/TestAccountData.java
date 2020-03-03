package TestBased;

import TestBased.YouTripIosUIElementKey.Market;

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
}