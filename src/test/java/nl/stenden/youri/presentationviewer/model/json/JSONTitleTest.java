package nl.stenden.youri.presentationviewer.model.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONTitle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONTitleTest {

    @Test
    public void testJSONTitleDeserialization() throws Exception {
        String json = "{ \"type\": \"title\", \"font\": \"Arial\", \"content\": \"inhoud\" }";
        ObjectMapper objectMapper = new ObjectMapper();
        JSONTitle jsonImage = objectMapper.readValue(json, JSONTitle.class);

        assertNotNull(jsonImage);
        assertEquals("Arial", jsonImage.getFont());
        assertEquals("title", jsonImage.getType());
        assertEquals("inhoud", jsonImage.getContent());
    }

    @Test
    void getType() {
        JSONTitle title = new JSONTitle();
        assertEquals("title", title.getType());
    }

}
