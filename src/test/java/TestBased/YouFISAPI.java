package TestBased;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Vector;

import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class YouFISAPI {

    private String domainEndPoint;
    private String requestEndPoint;
    Utils util = new Utils();

    public YouFISAPI(){
        domainEndPoint = "https://testing.metavante.eu:22226/webservices/services";
        Unirest.config().verifySsl(false);
    }

    private Document loadXMLFromResponseString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
        doc.getDocumentElement().normalize();

        return doc;
    }

    private Document getRequestEnvelop(File reqFilePath){
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(reqFilePath);
            doc.getDocumentElement().normalize();

            return doc;
        }catch(IOException | SAXException | ParserConfigurationException e){
            e.printStackTrace();
            return null;
        }
    }

    private String flushRequest(String reqBody){
        String req_uri = (domainEndPoint + "/" + requestEndPoint);


        HttpResponse<String> response = Unirest.post(req_uri)
                .header("Content-Type", "application/xml")
                .header("SOAPAction", requestEndPoint + util.getTimestamp())
                .body(reqBody).asString();

        assert response.getStatus() == 200;
        return response.getBody();
    }

    private String doCardHolderEnquiry(String cardIdToken){
        try {
            this.requestEndPoint = "CardHolderEnquiry";

            String timeStampStr = util.getTimestamp();
            File reqFilePath = new File(System.getProperty("user.dir"), "src/test/java/SOAPRequestsEnvelop/CardHolderEnquiry.xml");
            Document doc = this.getRequestEnvelop(reqFilePath);
            assert doc != null;

            Node subNode = doc.getElementsByTagName("cardID").item(0);
            subNode.setTextContent(cardIdToken);
            subNode = doc.getElementsByTagName("messageID").item(0);
            subNode.setTextContent(timeStampStr.substring(2,12));
            subNode = doc.getElementsByTagName("localDate").item(0);
            subNode.setTextContent(timeStampStr.substring(0,6));
            subNode = doc.getElementsByTagName("localTime").item(0);
            subNode.setTextContent(timeStampStr.substring(0,6));

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();

            return flushRequest(output);
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }


    public List<String> getCardStatusFromService(String cardIdToken){
        try {
            String responseStr = this.doCardHolderEnquiry(cardIdToken);
            Document doc = this.loadXMLFromResponseString(responseStr);

            Node node = doc.getElementsByTagName("prevStatCode").item(0);
            String prevStatCode = node.getTextContent();
            node = doc.getElementsByTagName("statCode").item(0);
            String statCode = node.getTextContent();

            List<String> result= new Vector<String>();
            result.add(statCode);
            result.add(prevStatCode);

            return result;
        }catch (Exception e){
            return null;
        }
    }

    public boolean AdjustBalance(String cardIdToken, String CurrencyCodeStr, Double amt, boolean isCredit){
        try {
            this.requestEndPoint = "CardHolderEnquiry";

            String timeStampStr = util.getTimestamp();
            File reqFilePath = new File(System.getProperty("user.dir"), "src/test/java/SOAPRequestsEnvelop/BalanceAdjustment.xml");
            Document doc = this.getRequestEnvelop(reqFilePath);
            assert doc != null;

            Node subNode = doc.getElementsByTagName("cardID").item(0);
            subNode.setTextContent(cardIdToken);
            subNode = doc.getElementsByTagName("messageID").item(0);
            subNode.setTextContent(timeStampStr.substring(2, 12));
            subNode = doc.getElementsByTagName("localDate").item(0);
            subNode.setTextContent(timeStampStr.substring(0, 6));
            subNode = doc.getElementsByTagName("localTime").item(0);
            subNode.setTextContent(timeStampStr.substring(0, 6));
            subNode = doc.getElementsByTagName("amtAdjustment").item(0);
            subNode.setTextContent(Double.toString(amt));
            subNode = doc.getElementsByTagName("currCode").item(0);
            subNode.setTextContent(CurrencyCodeStr);
            subNode = doc.getElementsByTagName("debOrCred").item(0);
            subNode.setTextContent((isCredit) ? "1" : "0");

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();

            flushRequest(output);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
