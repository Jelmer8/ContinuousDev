package nl.stenden.youri.presentationviewer.domain.mapper;

import lombok.AllArgsConstructor;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONPresentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.XMLPresentation;
import nl.stenden.youri.presentationviewer.model.dto.PresentationDto;
import org.springframework.stereotype.Component;

/**
 * Deze mapper class zet de verschillende presentatie objecten om naar een Data Transfer Object (dto) die gebruikt kan worden voor binnen de presentatie laag.
 */
@Component
@AllArgsConstructor
public class PresentatieDtoMapper {
    private final JSONPresentationMapper jsonPresentationMapper;
    private final XMLPresentatieMapper xmlPresentatieMapper;

    /**
     * Zet een presentatie object om naar een PresentationDto.
     *
     * @param presentation het presentatie object
     * @return een PresentationDto object
     */
    public PresentationDto mapToPresentationDto(Presentation presentation) {

        if (presentation instanceof JSONPresentation jsonPresentation) {
            return jsonPresentationMapper.mapToPresentationDto(jsonPresentation);
        }
        if (presentation instanceof XMLPresentation xmlPresentation) {
            return xmlPresentatieMapper.mapToPresentationDto(xmlPresentation);
        }
        return null;
    }

}
