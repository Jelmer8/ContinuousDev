package nl.stenden.youri.presentationviewer.domain.documentmodel.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * deze classe is een representatie voor een image element binnen een slide in een XML bestand
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class XMLImage {

    @XmlAttribute(name = "src")
    private String src;

    @XmlAttribute(name = "indentation")
    private int indentation;

    public String getSrc() {
        return src;
    }

    public int getIndentation() {
        return indentation;
    }

}
