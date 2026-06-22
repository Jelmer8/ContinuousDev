package nl.stenden.youri.presentationviewer.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONPresentation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JSONPresentationTest {

    @Test
    public void testJSONPresentationDeserialization() throws Exception {
        String json = "{ \"showtitle\": \"Sample Presentation\", \"slides\": [[{\"type\": \"title\", \"content\": \"Slide 1\"}]] }";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONPresentation jsonPresentation = objectMapper.readValue(json, JSONPresentation.class);

        assertNotNull(jsonPresentation);
        assertEquals("* Sample Presentation *", jsonPresentation.getShowTitle());
        assertNotNull(jsonPresentation.getSlides());
        assertEquals(1, jsonPresentation.getSlides().size());
        assertEquals(1, jsonPresentation.getSlides().getFirst().size());
    }

}
