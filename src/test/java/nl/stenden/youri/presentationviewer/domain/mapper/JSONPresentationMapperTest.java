package nl.stenden.youri.presentationviewer.domain.mapper;

import lombok.val;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONPresentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONSlide;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONTitle;
import nl.stenden.youri.presentationviewer.model.dto.PresentationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JSONPresentationMapperTest {

    @InjectMocks
    JSONPresentationMapper mapper;

    @Test
    void testMapToPresentationDto_NullInput() {
        // Arrange
        JSONPresentation presentation = null;

        // Act
        PresentationDto result = mapper.mapToPresentationDto(presentation);

        // Assert
        assertNull(result, "Result should be null when input is null");
    }

    @Test
    void testMapToPresentationDto_ValidInput() {
        // Arrange
        val presentation = JSONPresentation.builder()
                .showTitle("Test Presentation")
                .slides(List.of(List.of(
                        new JSONSlide() {
                            @Override
                            public String getType() {
                                return "slide";
                            }
                        },
                        new JSONTitle() {
                            @Override
                            public String getType() {
                                return "title";
                            }

                            @Override
                            public String getFont() {
                                return "Arial";
                            }

                            @Override
                            public String getContent() {
                                return "Welcome to the presentation!";
                            }
                        }
                ))).build();


        // Act
        PresentationDto result = mapper.mapToPresentationDto(presentation);

        // Assert
        assertNotNull(result, "Result should not be null for valid input");
        assertEquals("* Test Presentation *", result.getShowTitle(), "Show title should match");
        assertNotNull(result.getSlides(), "Slides should not be null");
        val firstSlide = result.getSlides().getFirst();
        assertEquals(firstSlide.getTitle().getValue(), "Welcome to the presentation!", "Slide title should match");
        assertEquals(firstSlide.getTitle().getFont(), "Arial", "Font should match");

    }

}
