package TestBased;

import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestAccountData {
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

        Card = null;
    }

    public String toRequestBodyString( boolean isCreate) {
        System.out.println("ACCOUNT DATA EXPORT: Id - " + this.Id);
        System.out.println("ACCOUNT DATA EXPORT: MCC - " + this.MCC);
        System.out.println("ACCOUNT DATA EXPORT: PhoneNumber - " + this.PhoneNumber);
        System.out.println("ACCOUNT DATA EXPORT: Email - " + this.Email);
        System.out.println("ACCOUNT DATA EXPORT: KycStatus - " + this.KycStatus.toString());
        System.out.println("ACCOUNT DATA EXPORT: CardType - " + this.TestAccountCardType.toString());
        System.out.println("ACCOUNT DATA EXPORT: LastName - " + this.LastName);
        System.out.println("ACCOUNT DATA EXPORT: FirstName - " + this.FirstName);
        if (this.TestAccountCardType.equals(TestAccountCardType.PC)) {
            System.out.println("ACCOUNT DATA EXPORT: NameOnCard - " + this.NameOnCard);
        }
        System.out.println("ACCOUNT DATA EXPORT: Birthdate - " + this.Birthdate);
        System.out.println("ACCOUNT DATA EXPORT: NricNumber - " + this.NricNumber);
        System.out.println("ACCOUNT DATA EXPORT: AddressLineOne - " + this.AddressLineOne);
        System.out.println("ACCOUNT DATA EXPORT: AddressLineTwo - " + this.AddressLineTwo);
        System.out.println("ACCOUNT DATA EXPORT: PostalCode - " + this.PostalCode);
        System.out.println("ACCOUNT DATA EXPORT: UnderUse - " + this.UnderUse);
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
            builder.append("\"NameOnCard\": \"" + this.NameOnCard + "\",\n");
        }
        builder.append("\t\t\"Birthdate\":\"" + this.Birthdate + "\",\n")
                .append("\t\t\"PostalCode\":\"" + this.PostalCode + "\",\n")
                .append("\t\t\"AddressLineOne\":\"" + this.AddressLineOne + "\",\n")
                .append("\t\t\"AddressLineTwo\":\"" + this.AddressLineTwo + "\",\n")
                .append("\t\t\"KycStatus\":" + this.KycStatus.getCodeKYCStatus() + ",\n")
                .append("\t\t\"CardType\":" + this.TestAccountCardType.getCodeCardType() + ",\n")
                .append("\t\t\"UnderUse\":" + this.UnderUse + "\n")
                .append("\t}");
        if (this.Card != null) {
            if (isCreate && this.TestAccountCardType.equals(TestAccountCardType.NPC)) {
                builder.append("\n");
            }else {
                builder.append(",\n").append(this.Card.toRequestSubBodyString());
            }
        } else {
            builder.append("\n");
        }
        builder.append("}");
        return builder.toString();
    }

    public static TestAccountData toTestAccountData (JSONObject jObj) throws NoSuchFieldException{
        TestAccountData data = new TestAccountData();
        String[] keyArray= new String[] {"Id", "MCC", "PhoneNumber", "NricNumber", "Email",
                "FirstName", "LastName", "NameOnCard", "Birthdate", "PostalCode",
                "AddressLineOne", "AddressLineTwo", "KycStatus", "CardType", "UnderUse"};
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

            if(jObj.has("card")){
                JSONObject cardObj = jObj.getJSONObject("card");
                data.Card = TestCardData.toTestCardData(cardObj);
            }

            return data;
        }catch (JSONException jex){
            throw new NoSuchFieldException(jex.getMessage());
        }
    }
}