package TestBased;

import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestAccountData {
    public enum Market{
        UnknownMarket(0),
        Singapore(1),
        Thailand(2);

        private final int codeMarket;
        private static Map marketMap = new HashMap<>();
        Market(int codeMarket){
            this.codeMarket=codeMarket;
        }

        static {
            for (Market m : Market.values()) {
                marketMap.put(m.codeMarket, m);
            }
        }

        public static Market valueOf(int codeMarket) {
            return (Market) marketMap.get(codeMarket);
        }

        public int getCodeMarket(){
            return this.codeMarket;
        }
    }

    public enum CardStatus{
        UnknownCardStatus(0),
        Inactive(1),
        NewActive(2),
        Active(3),
        Locked(4),
        Suspended(5),
        Replace(6),
        NPCPending(7),
        NotIssued(8);
        private final int codeCardStatus;
        private static Map cardStatusMap = new HashMap<>();
        CardStatus(int codeCardStatus){
            this.codeCardStatus=codeCardStatus;
        }

        static {
            for (CardStatus s : CardStatus.values()) {
                cardStatusMap.put(s.codeCardStatus, s);
            }
        }

        public static CardStatus valueOf(int codeCardStatus) {
            return (CardStatus) cardStatusMap.get(codeCardStatus);
        }

        public int getCodeCardStatus(){
            return this.codeCardStatus;
        }
    }

    public enum KYCStatus{
        UnknownKycStatus(0),
        Submit(1),
        Reject(2),
        PartialReject(3),
        Clear(4);
        private final int codeKYCStatus;
        private static Map kycStatusMap = new HashMap<>();
        KYCStatus(int codeKYCStatus){
            this.codeKYCStatus=codeKYCStatus;
        }

        static {
            for (KYCStatus s : KYCStatus.values()) {
                kycStatusMap.put(s.codeKYCStatus, s);
            }
        }

        public static KYCStatus valueOf(int codeKYCStatus) {
            return (KYCStatus) kycStatusMap.get(codeKYCStatus);
        }

        public int getCodeKYCStatus(){
            return this.codeKYCStatus;
        }
    }

    public enum CardType {
        PC(1),
        NPC(2);
        private final int codeCardType;
        private static Map cardTypeMap = new HashMap<>();
        CardType(int codeCardType){
            this.codeCardType=codeCardType;
        }

        static {
            for (CardType t : CardType.values()) {
                cardTypeMap.put(t.codeCardType, t);
            }
        }

        public static CardType valueOf(int codeCardType) {
            return (CardType) cardTypeMap.get(codeCardType);
        }

        public int getCodeCardType(){
            return this.codeCardType;
        }
    }


    public String Id;
    public String MCC;
    public String PhoneNumber;
    public String Email;
    public KYCStatus KycStatus;

    public CardType TestAccountCardType;
    public int NumOfReplace;
    public boolean UnderUse;

    public String LastName;
    public String FirstName;
    public String NameOnCard;
    public String Birthdate;
    public String NricNumber;
    public String AddressLineOne;
    public String AddressLineTwo;
    public String PostalCode;
    public Market TestAccountMarket;
    public Boolean IsExplorerModeOn;

    public TestCardData Card;

    public TestAccountData() {
        MCC = null;
        PhoneNumber = null;
        Id = null;
        Email = null;
        TestAccountCardType = null;

        LastName = "";
        FirstName = "";
        NameOnCard = null;
        NricNumber = "";
        Birthdate = "";
        AddressLineOne = "";
        AddressLineTwo = "";
        PostalCode = "";
        UnderUse = false;
        TestAccountMarket = null;
        IsExplorerModeOn = false;


        Card = null;
    }

    public String toRequestBodyString( boolean isCreate) {
        this.printTestAccountData("EXPORT");
        StringBuilder builder = new StringBuilder();

        builder.append("{\n")
                .append("\t\"user\": {\n")
                .append("\t\t\"Id\":\"" + this.Id + "\",\n")
                .append("\t\t\"MCC\":\"" + this.MCC + "\",\n")
                .append("\t\t\"PhoneNumber\":\"" + this.PhoneNumber + "\",\n")
                .append("\t\t\"NricNumber\": \"" + this.NricNumber + "\",\n")
                .append("\t\t\"Email\":\"" + this.Email + "\",\n")
                .append("\t\t\"FirstName\":\"" + this.FirstName + "\",\n")
                .append("\t\t\"LastName\":\"" + this.LastName + "\",\n");
        if (this.TestAccountCardType.equals(TestAccountCardType.PC)) {
            builder.append("\t\t\"NameOnCard\": \"" + this.NameOnCard + "\",\n");
        }
        builder.append("\t\t\"Birthdate\":\"" + this.Birthdate + "\",\n")
                .append("\t\t\"PostalCode\":\"" + this.PostalCode + "\",\n")
                .append("\t\t\"AddressLineOne\":\"" + this.AddressLineOne + "\",\n")
                .append("\t\t\"AddressLineTwo\":\"" + this.AddressLineTwo + "\",\n")
                .append("\t\t\"KycStatus\":" + this.KycStatus.getCodeKYCStatus() + ",\n")
                .append("\t\t\"CardType\":" + this.TestAccountCardType.getCodeCardType() + ",\n")
                .append("\t\t\"UnderUse\":" + this.UnderUse + ",\n")
                .append("\t\t\"Market\":" + this.TestAccountMarket.getCodeMarket() + ",\n")
                .append("\t\t\"IsExplorerModeOn\":" + this.IsExplorerModeOn);
        if (this.Card != null) {
            if (isCreate && this.TestAccountCardType.equals(CardType.PC)) {
                // When in Creation with Card (if in case), it will maintain the relationship by the Card section adding together for creation
                builder.append("\n\t},\n").append(this.Card.toRequestSubBodyString());
            }else if (isCreate && this.TestAccountCardType.equals(CardType.NPC)){
                builder.append("\n\t}\n");
            }else {
                // When in update case, it will maintain the relationship by the CardId field from update
                // Or When in creation case of NPC(card is already exist, it will maintain the relationship by giving the CardId field
                builder.append(",\n")
                        .append("\t\t\"CardId\": \"" + this.Card.Id + "\"\n\t}\n");
            }
        } else {
            // When case is PC creation without Card, or updating user which not yet has a card will fall to this condition.
            builder.append("\n\t}\n");
        }
        builder.append("}");
        return builder.toString();
    }

    public static TestAccountData toTestAccountData (JSONObject jObj) throws NoSuchFieldException{
        TestAccountData data = new TestAccountData();
        String[] keyArray= new String[] {"Id", "MCC", "PhoneNumber", "NricNumber", "Email",
                "FirstName", "LastName", "NameOnCard", "Birthdate", "PostalCode",
                "AddressLineOne", "AddressLineTwo", "KycStatus", "CardType", "UnderUse", "Market", "IsExplorerModeOn"};
        try {
            for(int i =0; i < keyArray.length;i++){
                if(!jObj.has(keyArray[i])){
                    throw new NoSuchFieldException("Could not find field from data service response: " + keyArray[i]);
                }
            }
            data.Id = jObj.getString(keyArray[0]);
            data.MCC = jObj.getString(keyArray[1]);
            data.PhoneNumber = jObj.getString(keyArray[2]);
            data.NricNumber = jObj.getString(keyArray[3]);
            data.Email = jObj.getString(keyArray[4]);
            data.FirstName = jObj.getString(keyArray[5]);
            data.LastName = jObj.getString(keyArray[6]);
            data.NameOnCard = jObj.getString(keyArray[7]);
            data.Birthdate = jObj.getString(keyArray[8]);
            data.PostalCode = jObj.getString(keyArray[9]);
            data.AddressLineOne = jObj.getString(keyArray[10]);
            data.AddressLineTwo = jObj.getString(keyArray[11]);
            data.KycStatus = KYCStatus.valueOf(jObj.getString(keyArray[12]));
            data.TestAccountCardType = CardType.valueOf(jObj.getString(keyArray[13]));
            data.UnderUse = jObj.getBoolean(keyArray[14]);
            data.TestAccountMarket = Market.valueOf(jObj.getString(keyArray[15]));
            data.IsExplorerModeOn = jObj.getBoolean(keyArray[16]);

            data.printTestAccountData("IMPORT");

            if(jObj.optJSONObject("card") != null){
                JSONObject cardObj = jObj.optJSONObject("card");
                data.Card = TestCardData.toTestCardData(cardObj);
            }

            return data;
        }catch (JSONException jex){
            throw new NoSuchFieldException(jex.getMessage());
        }
    }

    public void printTestAccountData(String direction) {
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": Id - " + this.Id);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": PhoneNumber - " + this.PhoneNumber);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": Email - " + this.Email);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": KycStatus - " + this.KycStatus.toString());
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": CardType - " + this.TestAccountCardType.toString());
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": LastName - " + this.LastName);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": FirstName - " + this.FirstName);
        if (this.TestAccountCardType.equals(CardType.PC)) {
            System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": NameOnCard - " + this.NameOnCard);
        }
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": Birthdate - " + this.Birthdate);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": NricNumber - " + this.NricNumber);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": AddressLineOne - " + this.AddressLineOne);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": AddressLineTwo - " + this.AddressLineTwo);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": PostalCode - " + this.PostalCode);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": UnderUse - " + this.UnderUse);
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": Market - " + this.TestAccountMarket.toString());
        System.out.println("ACCOUNT DATA " + direction.toUpperCase() + ": IsExplorerModeOn - " + this.IsExplorerModeOn);
    }
}