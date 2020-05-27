package TestBased;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.Properties;

public class TestTETransactions {
    String timeStamp;
    String timeWithoutDate;
    String date;
    int trxnCount=0;
    Utils utils = new Utils();
    public void createTETransactionFile(String BU, String TrxnType, String TrxnStatus, String Card, String TrxnAmount, String TrxnCurr, String BillAmount, String BillCurr, String trxnDesc) {
        try {
            Properties properties;
            String propertyFilePath = "src/test/resources/Transaction.properties";
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(propertyFilePath));
                properties = new Properties();
                try {
                    properties.load(reader);
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Global.properties not found at " + propertyFilePath);
            }
            TrxnCurr= String.valueOf(getCurrencyCode(TrxnCurr));
            BillCurr= String.valueOf(getCurrencyCode(BillCurr));

            timeStamp = utils.getTimestampTillss();
            timeWithoutDate = utils.getTimestampWithoutDate();
            date = LocalDate.now().toString();

            String contentBody = "";
            if (TrxnStatus.equalsIgnoreCase("Pending"))
                contentBody = createTransactionBody(properties.getProperty("AuthAdv"),BU, TrxnType, TrxnStatus, Card, TrxnAmount, TrxnCurr, BillAmount, BillCurr, trxnDesc);
            else if (TrxnStatus.equalsIgnoreCase("Reverse"))
                contentBody = createTransactionBody(properties.getProperty("AuthRev"), BU, TrxnType, TrxnStatus, Card, TrxnAmount, TrxnCurr, BillAmount, BillCurr, trxnDesc);
            else if (TrxnStatus.equalsIgnoreCase("Complete"))
                contentBody = createTransactionBody(properties.getProperty("FinAdv"), BU, TrxnType, "Complete", Card, TrxnAmount, TrxnCurr, BillAmount, BillCurr, trxnDesc);
            else if (TrxnStatus.equalsIgnoreCase("PendingReverse")) {
                contentBody = createTransactionBody(properties.getProperty("AuthAdv"), BU, TrxnType, "Pending", Card, TrxnAmount, TrxnCurr, BillAmount, BillCurr, trxnDesc);
                contentBody = contentBody + "\n" + createTransactionBody(properties.getProperty("AuthRev"), BU, TrxnType, "Reverse", Card, TrxnAmount, TrxnCurr, BillAmount, BillCurr, trxnDesc);
            } else if (TrxnStatus.equalsIgnoreCase("PendingComplete")) {
                contentBody = createTransactionBody(properties.getProperty("AuthAdv"), BU, TrxnType, "Pending", Card, TrxnAmount, TrxnCurr, BillAmount, BillCurr, trxnDesc);
                contentBody = contentBody + "\n" + createTransactionBody(properties.getProperty("FinAdv"), BU, TrxnType, "Complete", Card, TrxnAmount, TrxnCurr, BillAmount, BillCurr, trxnDesc);
            }
            String content = properties.getProperty("Header") + "\n" + contentBody + "\n" + properties.getProperty("Footer");
            content = content.replaceAll("YTG", BU);
            content = content.replaceAll("(FileDate=\")[^&]*(\")", "$1" + date + "$2" +" TotNumTxns=\""+trxnCount+"\"");
            content = content.replaceAll("(YTGtxnexp)[^&]*(.xml)", "$1" + timeStamp + "$2");

            File destFile = new File("src/test/resources/" + BU + "txnexp" + timeStamp + ".xml");
            FileUtils.writeStringToFile(destFile, content, "UTF-8");
            System.out.println("date" + LocalDate.now() + " timestamp=" + timeStamp + " timeWithoutDate=" + timeWithoutDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createTransactionBody(String content, String BU, String TrxnType, String TrxnStatus, String Card, String TrxnAmount, String TrxnCurr, String BillAmount, String BillCurr, String trxnDesc) {
        trxnCount++;
        content = content.replaceAll("YTG", BU);
        content = content.replaceAll("(<LOCALDATE>)[^&]*(</LOCALDATE>)", "$1" + date + "$2");
        content = content.replaceAll("(<CORTEXDATE>)[^&]*(</CORTEXDATE>)", "$1" + date + "$2");
        content = content.replaceAll("(<TXNDATE>)[^&]*(</TXNDATE>)", "$1" + date + "$2");
        content = content.replaceAll("(<CTXDATELOCAL>)[^&]*(</CTXDATELOCAL>)", "$1" + date + "$2");
        content = content.replaceAll("(<CARDID>)[^&]*(</CARDID>)", "$1" + Card + "$2");
        content = content.replaceAll("(<TLOGID>)[^&]*(</TLOGID>)", "$1" + timeStamp + "$2");
        content = content.replaceAll("(<ORGTLOGID>)[^&]*(</ORGTLOGID>)", "$1" + timeStamp + "$2");
        content = content.replaceAll("(<LOCALTIME>)[^&]*(</LOCALTIME>)", "$1" + timeWithoutDate + "$2");
        content = content.replaceAll("(<TXNTIME>)[^&]*(</TXNTIME>)", "$1" + timeWithoutDate + "$2");
        content = content.replaceAll("(<CTXTIMELOCAL>)[^&]*(</CTXTIMELOCAL>)", "$1" + timeWithoutDate + "$2");
        content = content.replaceAll("(<BILLAMT>)[^&]*(</BILLAMT>)", "$1" + BillAmount + "$2");
        content = content.replaceAll("(<ACCCUR>)[^&]*(</ACCCUR>)", "$1" + BillCurr + "$2");
        content = content.replaceAll("(<CURTXN>)[^&]*(</CURTXN>)", "$1" + TrxnCurr + "$2");
        content = content.replaceAll("(<AMTTXN>)[^&]*(</AMTTXN>)", "$1" + TrxnAmount + "$2");
        content = content.replaceAll("(<TERMLOCATION>)[^&]*(</TERMLOCATION>)", "$1" + trxnDesc + "$2");
        content = content.replaceAll("(<AMTBLK>)[^&]*(</AMTBLK>)", "$1" + BillAmount + "$2");
        if(TrxnStatus.equalsIgnoreCase("Reverse"))
            content = content.replaceAll("(<AMTBLK>)[^&]*(</AMTBLK>)", "$1" + "-"+BillAmount + "$2");
        if(TrxnStatus.equalsIgnoreCase("Complete"))
            content = content.replaceAll("(<TLOGID>)[^&]*(</TLOGID>)", "$1" + timeStamp+1 + "$2");
        if(TrxnType.equalsIgnoreCase("Sales"))
            content = content.replaceAll("(<TXNCODE>)[^&]*(</TXNCODE>)", "$1" + "0" + "$2");
        else if(TrxnType.equalsIgnoreCase("ATM")) {
            content = content.replaceAll("(<TXNCODE>)[^&]*(</TXNCODE>)", "$1" + "1" + "$2");
            content = content.replaceAll("(<AMTCOM>)[^&]*(</AMTCOM>)", "$1" + "5.00" + "$2");
            float billamount=Float.parseFloat(BillAmount)+5;
            content = content.replaceAll("(<AMTBLK>)[^&]*(</AMTBLK>)", "$1" + billamount + "$2");
        }
        //ßSystem.out.println("con= "+content);
        return content;
    }
    public int getCurrencyCode(String Currency){
        int code=0;
        switch (Currency){
            case "SGD":
                code=702;
                break;
            case "USD":
                code=840;
                break;
            case "EUR":
                code=978;
                break;
            case "GBP":
                code=826;
                break;
            case "JPY":
                code=392;
                break;
            case "HKD":
                code=344;
                break;
            case "AUD":
                code=036;
                break;
            case "NZD":
                code=554;
                break;
            case "CHF":
                code=756;
                break;
            case "SEK":
                code=752;
                break;
            case "MYR":
                code=458;
                break;
            case "THB":
                code=764;
                break;
            case "INR":
                code=356;
                break;
            default:
        }
        return code;
    }
}
