package nl.stenden.youri.presentationviewer.domain.documentmodel.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * deze classe is een representatie voor een presentatie element in een XML bestand
 * een presentatie bestaat uit een showtitle en een lijst van slides
 * <p>
 * de XMLPresentation maakt gebruik van Abstractie via een interface Presentatie, maw
 * Presentatie presentatie = new XMLPresentation();
 * maar het kan ook zo zijn dat presentatie een JSONPresentatie is,
 * Presentatie presentatie = new JSONPresentation();
 * door de implementatie te verbergen kunnen we makkelijk gebruik maken op hoger nivea van de Presentatie interface
 * <p>
 * kijk naar de methode getShowTitle(), deze staat gedefinieerd in de interface Presentatie,
 * en moet in de XMLPresentation en in de JSONPresentation moeten staan, maar daarbij zou het een eigen implementatie kunnen hebben,
 * we zouden er ook een abstracte klasse van kunnen maken, waarbij we de implementatie van getShowTitle() in de abstracte klasse zetten,
 * daar heb ik nu niet voor gekozen omdat het niet nodig is.
 *
 */
@XmlRootElement(name = "presentation")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class XMLPresentation implements Presentation {

    @XmlElement(name = "showtitle")
    private String showTitle;

    @XmlElement(name = "slide")
    private List<XMLSlide> slides;

    /**
     * Retourneert de titel van de presentatie, voor JSON-presentaties prefixen we deze met "xml ".
     * dit doen we puur even om het verschil tussen de JSON en XML presentaties te laten zien
     *
     * @return de titel van de presentatie
     */
    public String getShowTitle() {
        return "# " + showTitle + " #";
    }

}
