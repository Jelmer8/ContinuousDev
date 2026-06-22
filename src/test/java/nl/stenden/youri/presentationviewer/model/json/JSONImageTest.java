package nl.stenden.youri.presentationviewer.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JSONImageTest {

    @Test
    public void testJSONImageDeserialization() throws Exception {
        String json = "{ \"type\": \"image\", \"src\": \"image.png\", \"indentation\": 2 }";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONImage jsonImage = objectMapper.readValue(json, JSONImage.class);

        assertNotNull(jsonImage);
        assertEquals("image.png", jsonImage.getSrc());
        assertEquals(2, jsonImage.getIndentation());
        assertEquals("image", jsonImage.getType());
    }

    @Test
    public void testGetType() {
        JSONImage jsonImage = new JSONImage();
        assertEquals("image", jsonImage.getType());
    }
}
