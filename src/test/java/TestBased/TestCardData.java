package TestBased;


import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

public class TestCardData {

    public String Id;
    public String YouId;
    public String CardIDToken;
    public TestAccountData.CardType TestCardCardType;
    public TestAccountData.CardStatus Status;
    public int NumOfReplace;
    public boolean UnderUse;
    public TestAccountData.Market TestCardMarket;

    public TestCardData() {
        Id = null;
        YouId = null;
        CardIDToken = null;
        TestCardCardType = null;
        Status = null;
        NumOfReplace = 0;
        UnderUse = false;
        TestCardMarket = null;
    }

    public TestCardData(TestAccountData accountData) {
        this();
        this.Id = accountData.Id;
        this.TestCardCardType = accountData.TestAccountCardType;
    }

    public String toRequestSubBodyString() {
        this.printTestCardData("EXPORT");
        StringBuilder builder = new StringBuilder();

        builder.append("\t\"card\": {\n")
                .append("\t\t\"Id\": \""+this.Id+"\",\n")
                .append("\t\t\"Status\": "+this.Status.getCodeCardStatus()+",\n")
                .append("\t\t\"YouId\":\""+this.YouId +"\",\n")
                .append("\t\t\"NumOfReplace\": "+this.NumOfReplace+",\n")
                .append("\t\t\"CardType\": "+this.TestCardCardType.getCodeCardType()+",\n")
                .append("\t\t\"CardIDToken\": \""+this.CardIDToken+"\",\n")
                .append("\t\t\"UnderUse\": "+this.UnderUse+",\n")
                .append("\t\t\"Market\": "+this.TestCardMarket.getCodeMarket()+"\n")
                .append("\t}\n");

        return builder.toString();
    }

    public String toRequestBodyString() {
        StringBuilder builder = new StringBuilder();

        builder.append("{\n")
                .append(this.toRequestSubBodyString())
                .append("}");

        return builder.toString();
    }

    public static TestCardData toTestCardData(JSONObject jObj) throws NoSuchFieldException {
        TestCardData data = new TestCardData();
        String[] keyArray= new String[] {"Id", "Status", "YouId", "NumOfReplace", "CardType",
                "CardIDToken", "UnderUse", "Market"};
        try {
            for(int i =0; i < keyArray.length;i++){
                if(!jObj.has(keyArray[i])){
                    throw new NoSuchFieldException("Could not find field from data service response: " + keyArray[i]);
                }
            }
            data.Id = jObj.getString(keyArray[0]);
            data.Status = TestAccountData.CardStatus.valueOf(jObj.getString(keyArray[1]));
            data.YouId = jObj.getString(keyArray[2]);
            data.NumOfReplace = jObj.getInt(keyArray[3]);
            data.TestCardCardType = TestAccountData.CardType.valueOf(jObj.getString(keyArray[4]));
            data.CardIDToken = jObj.getString(keyArray[5]);
            data.UnderUse = jObj.getBoolean(keyArray[6]);
            data.TestCardMarket = TestAccountData.Market.valueOf(jObj.getString(keyArray[7]));

            data.printTestCardData("IMPORT");
            return data;
        }catch (JSONException jex){
            throw new NoSuchFieldException(jex.getMessage());
        }
    }

    public void printTestCardData(String direction) {
        System.out.println("CARD DATA " + direction.toUpperCase() + ": Id - " + this.Id);
        System.out.println("CARD DATA " + direction.toUpperCase() + ": YouId - " + this.YouId);
        System.out.println("CARD DATA " + direction.toUpperCase() + ": CardIDToken - " + this.CardIDToken);
        System.out.println("CARD DATA " + direction.toUpperCase() + ": Status - " + this.Status);
        System.out.println("CARD DATA " + direction.toUpperCase() + ": NumOfReplace - " + this.NumOfReplace);
        System.out.println("CARD DATA " + direction.toUpperCase() + ": TestCardCardType - " + this.TestCardCardType.toString());
        System.out.println("CARD DATA " + direction.toUpperCase() + ": UnderUse - " + this.UnderUse);
        System.out.println("CARD DATA " + direction.toUpperCase() + ": Market - " + this.TestCardMarket.toString());
    }
}
