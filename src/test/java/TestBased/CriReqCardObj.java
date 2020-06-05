package TestBased;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class CriReqCardObj {
    @XmlElement
    public String ACCPROFILE;
    @XmlElement
    public String ACTION;
    @XmlElement(name = "ADDDETLIST")
    public List<ADDDET> ADDDETLIST;
    @XmlElement
    public String ADDRL1;
    @XmlElement
    public String ADDRL2;
    @XmlElement
    public String CARDNAME;
    @XmlElement
    public String CARRIERCODE;
    @XmlElement
    public String CITY;
    @XmlElement
    public String COUNTRY;
    @XmlElement
    public String CRDPRODUCT;
    @XmlElement
    public String CRDPROFILE;
    @XmlElement
    public String CRDUSRDATA;
    @XmlElement
    public String CURRCODE;
    @XmlElement
    public String DESIGNREF;
    @XmlElement
    public String DOB;
    @XmlElement
    public String FEELIST;
    @XmlElement
    public String FIRSTNAME;
    @XmlElement
    public String INSTCODE;
    @XmlElement
    public String LANG;
    @XmlElement
    public String LASTNAME;
    @XmlElement
    public String MISCSVCLIST;
    @XmlElement
    public String NEWACC;
    @XmlElement
    public String NEWCUST;
    @XmlElement
    public String PINBLK;
    @XmlElement
    public String POSTCODE;
    @XmlElement
    public String PRIADDDETLIST;
    @XmlElement
    public String PRODUCEPIN;
    @XmlElement
    public String PROGRAMID;
    @XmlElement
    public String RECID;
    @XmlElement
    public String STATCODE;
    @XmlElement(name = "WALLET")
    public WALLET WALLET;
}

class ADDDET{
    @XmlElement
    public String REFCODE;
    @XmlElement
    public String VALUE;
}

class WALLET{
    @XmlElement
    public String USAGEPROFILE;
}