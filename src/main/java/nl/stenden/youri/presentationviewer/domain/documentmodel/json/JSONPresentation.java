package nl.stenden.youri.presentationviewer.domain.documentmodel.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;

import java.util.List;

/**
 * JSON implementatie van de Presentation interface, wat overeenkomt met een presentatie uit een .json file.
 * <p>
 * de JSONPresentation maakt gebruik van Abstractie via een interface Presentatie, maw
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
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JSONPresentation implements Presentation {

    @JsonProperty("showtitle")
    private String showTitle;

    @JsonProperty("slides")
    private List<List<JSONSlide>> slides;

    /**
     * Retourneert de titel van de presentatie, voor JSON-presentaties prefixen we deze met "json ".
     * dit doen we puur even om het verschil tussen de JSON en XML presentaties te laten zien
     *
     * @return de titel van de presentatie
     */
    public String getShowTitle() {
        return "* " + showTitle + " *";
    }

}
