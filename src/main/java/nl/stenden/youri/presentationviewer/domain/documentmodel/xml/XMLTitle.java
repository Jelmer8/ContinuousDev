package nl.stenden.youri.presentationviewer.domain.documentmodel.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * deze classe is een representatie voor een titel element binnen een slide in een XML bestand
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XMLTitle {

    @XmlAttribute(name = "font")
    private String font;

    @XmlValue
    private String value;

    public String getFont() {
        return font;
    }

    public String getValue() {
        return value;
    }


}
