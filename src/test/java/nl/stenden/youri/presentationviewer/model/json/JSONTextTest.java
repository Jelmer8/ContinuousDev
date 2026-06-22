package nl.stenden.youri.presentationviewer.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JSONTextTest {

    @Test
    public void testJSONTextDeserialization() throws Exception {
        String json = "{ \"type\": \"text\", \"font\": \"Arial\", \"content\": \"inhoud\", \"indentation\": 2 }";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONText jsonImage = objectMapper.readValue(json, JSONText.class);

        assertNotNull(jsonImage);
        assertEquals("Arial", jsonImage.getFont());
        assertEquals(2, jsonImage.getIndentation());
        assertEquals("text", jsonImage.getType());
        assertEquals("inhoud", jsonImage.getContent());
    }

    @Test
    void getType() {
        JSONText jsonText = new JSONText();
        assertEquals("text", jsonText.getType());
    }
}
