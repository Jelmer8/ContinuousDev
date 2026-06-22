package nl.stenden.youri.presentationviewer.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * deze classe is een slide representatie voor een data transfer object slide element binnen een data transfer object presention element
 */
@Data
@Builder
public class SlideDto {
    private TitleDto title;
    private List<TextDto> texts;
    private List<ImageDto> images;
}
