package nl.stenden.youri.presentationviewer.domain.documentmodel.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * deze classe is een representatie voor een text element binnen een slide in een XML bestand
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XMLText {

    @XmlAttribute(name = "font")
    private String font;

    @XmlAttribute(name = "indentation")
    private int indentation;

    @XmlValue
    private String value;

    public String getFont() {
        return font;
    }

    public int getIndentation() {
        return indentation;
    }

    public String getValue() {
        return value;
    }


}
