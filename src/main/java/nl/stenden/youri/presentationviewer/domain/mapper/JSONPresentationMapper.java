package nl.stenden.youri.presentationviewer.domain.mapper;

import lombok.val;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.*;
import nl.stenden.youri.presentationviewer.model.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Deze mapper class zet de JSON objecten om naar een Data Transfer Object (dto) die gebruikt kan worden voor binnen de presentatie laag.
 */
@Component
public class JSONPresentationMapper {

    private static SlideDto mapJsonSslide(List<JSONSlide> jsonList) {
        if (jsonList == null || jsonList.isEmpty()) {
            return null;
        }

        Optional<TitleDto> title = mapTitel(jsonList);

        List<TextDto> texts = mapTeksten(jsonList);

        List<ImageDto> images = mapPlaatjes(jsonList);

        val titleDto = new TitleDto();
        titleDto.setValue("no slide title found");

        return SlideDto.builder()
                .title(title.orElse(titleDto))
                .texts(texts)
                .images(images)
                .build();
    }

    private static List<ImageDto> mapPlaatjes(List<JSONSlide> jsonList) {
        return jsonList.stream()
                .filter(slide -> slide instanceof JSONImage)
                .map(JSONImage.class::cast)
                .map(JSONPresentationMapper::mapJsonImage)
                .toList();
    }

    private static List<TextDto> mapTeksten(List<JSONSlide> jsonList) {
        return jsonList.stream()
                .filter(slide -> slide instanceof JSONText)
                .map(JSONText.class::cast)
                .map(JSONPresentationMapper::mapJsonText)
                .toList();
    }

    private static Optional<TitleDto> mapTitel(List<JSONSlide> jsonList) {
        return jsonList.stream()
                .filter(slide -> slide instanceof JSONTitle)
                .map(JSONTitle.class::cast)
                .map(JSONPresentationMapper::mapJsonTitle)
                .findFirst();
    }

    private static TitleDto mapJsonTitle(JSONTitle jsonTitle) {
        if (jsonTitle == null) {
            return null;
        }
        val titleDto = new TitleDto();
        titleDto.setFont(jsonTitle.getFont());
        titleDto.setValue(jsonTitle.getContent());
        return titleDto;

    }

    private static TextDto mapJsonText(JSONText jsonText) {
        if (jsonText == null) {
            return null;
        }
        val textDto = new TextDto();
        textDto.setFont(jsonText.getFont());
        textDto.setValue(jsonText.getContent());
        textDto.setIdentation(jsonText.getIndentation());
        return textDto;

    }

    private static ImageDto mapJsonImage(JSONImage jsonImage) {
        if (jsonImage == null) {
            return null;
        }
        val imageDto = new ImageDto();
        imageDto.setValue(jsonImage.getSrc());
        imageDto.setIdentation(jsonImage.getIndentation());
        return imageDto;

    }

    /**
     * Zet een JSONPresentation object om naar een PresentationDto object.
     *
     * @param presentation het JSONPresentation object dat omgezet moet worden
     * @return een PresentationDto object met de gegevens van de JSONPresentation
     */
    public PresentationDto mapToPresentationDto(JSONPresentation presentation) {
        if (presentation == null) {
            return null;
        }

        return PresentationDto.builder()
                .showTitle(presentation.getShowTitle())
                .slides(mapJsonSlides(presentation.getSlides()))
                .build();
    }

    private List<SlideDto> mapJsonSlides(List<List<JSONSlide>> slides) {
        if (slides == null || slides.isEmpty()) {
            return List.of();
        }


        return slides.stream()
                .map(JSONPresentationMapper::mapJsonSslide)
                .toList();

    }

}
