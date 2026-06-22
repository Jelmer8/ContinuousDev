package nl.stenden.youri.presentationviewer.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Deze klasse is een gegevensrepresentatie voor een presentatie.
 * Een presentatie bestaat uit een titel en een lijst van slides en een titel.
 */
@Data
@Builder
public class PresentationDto {
    private String showTitle;
    private List<SlideDto> slides;
}
