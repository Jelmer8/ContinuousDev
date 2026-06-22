package nl.stenden.youri.presentationviewer.domain.documentmodel.xml;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * deze classe is een representatie voor een slide element in een XML bestand
 * een slide bestaat uit een titel, een lijst van teksten en een lijst van plaatjes
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class XMLSlide {

    @XmlElement(name = "title")
    private XMLTitle title;

    @XmlElement(name = "text")
    private List<XMLText> texts;

    @XmlElement(name = "image")
    private List<XMLImage> images;

    public XMLTitle getTitle() {
        return title;
    }

    public List<XMLText> getTexts() {
        return texts;
    }

    public List<XMLImage> getImages() {
        return images;
    }
}
