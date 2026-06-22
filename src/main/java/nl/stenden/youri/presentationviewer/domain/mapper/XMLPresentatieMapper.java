package nl.stenden.youri.presentationviewer.domain.mapper;

import lombok.val;
import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.*;
import nl.stenden.youri.presentationviewer.model.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Deze mapper class zet de XML objecten om naar een Data Transfer Object (dto) die gebruikt kan worden voor binnen de presentatie laag.
 */
@Component
public class XMLPresentatieMapper {

    private static TextDto mapXMLText(XMLText xmlText) {
        if (xmlText == null) {
            return null;
        }

        val textDto = new TextDto();
        textDto.setFont(xmlText.getFont());
        textDto.setValue(xmlText.getValue());
        textDto.setIdentation(xmlText.getIndentation());
        return textDto;

    }

    private static List<ImageDto> mapXMLImages(List<XMLImage> xmlImages) {
        if (xmlImages == null) {
            return List.of();
        }
        return xmlImages.stream()
                .map(XMLPresentatieMapper::mapXMLImage)
                .toList();
    }

    private static ImageDto mapXMLImage(XMLImage xmlImage) {
        if (xmlImage == null) {
            return null;
        }

        val imageDto = new ImageDto();
        imageDto.setValue(xmlImage.getSrc());
        imageDto.setIdentation(xmlImage.getIndentation());
        return imageDto;
    }

    /**
     * Zet een XML presentatie object om naar een PresentationDto.
     *
     * @param presentation het XML presentatie object
     * @return een PresentationDto object
     */
    public PresentationDto mapToPresentationDto(XMLPresentation presentation) {
        if (presentation == null) {
            return null;
        }

        return PresentationDto.builder()
                .showTitle(presentation.getShowTitle())
                .slides(mapXMLSlides(presentation.getSlides()))
                .build();
    }

    private List<SlideDto> mapXMLSlides(List<XMLSlide> slides) {
        if (slides == null) {
            return List.of();
        }
        return slides.stream().map(xmlSlide -> SlideDto.builder()
                        .title(mapXMLTitle(xmlSlide.getTitle()))
                        .texts(mapXMLTexts(xmlSlide.getTexts()))
                        .images(mapXMLImages(xmlSlide.getImages()))
                        .build())
                .toList();
    }

    private TitleDto mapXMLTitle(XMLTitle xmlTitle) {
        if (xmlTitle == null) {
            return null;
        }
        val titleDto = new TitleDto();
        titleDto.setFont(xmlTitle.getFont());
        titleDto.setValue(xmlTitle.getValue());
        return titleDto;

    }

    private List<TextDto> mapXMLTexts(List<XMLText> xmlTexts) {
        if (xmlTexts == null) {
            return List.of();
        }
        return xmlTexts.stream()
                .map(XMLPresentatieMapper::mapXMLText)
                .toList();
    }

}
