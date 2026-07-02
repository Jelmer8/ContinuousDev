package nl.stenden.youri.presentationviewer.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONPresentation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JSONPresentationTest {

    @Test
    @DisplayName("Edge case voor deserialisatie presentation")
    public void testJSONPresentationDeserialization() throws Exception {
        String json = "{ \"showtitle\": \"Sample Presentation\", \"slides\": [[{\"type\": \"title\", \"content\": \"Slide 1\"}]] }";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONPresentation jsonPresentation = objectMapper.readValue(json, JSONPresentation.class);

        assertNotNull(jsonPresentation);
        assertEquals("* Sample Presentation *", jsonPresentation.getShowTitle());
        assertNotNull(jsonPresentation.getSlides());
        assertNotNull(jsonPresentation.getSlides().get(0));

        assertEquals(1, jsonPresentation.getSlides().size());
        assertEquals(1, jsonPresentation.getSlides().getFirst().size());

        assertThrows(IndexOutOfBoundsException.class, () -> {
            jsonPresentation.getSlides().get(2);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            jsonPresentation.getSlides().get(1);
        });

    }



}
