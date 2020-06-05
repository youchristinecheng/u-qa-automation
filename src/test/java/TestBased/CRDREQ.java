package TestBased;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "CRDREQ")
public class CRDREQ {
    @XmlElement (name = "CARD")
    public List<CriReqCardObj> CARD;
}
