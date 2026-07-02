package nl.stenden.youri.presentationviewer.domain.reader;

import lombok.val;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONImage;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONPresentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONText;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONTitle;
import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.XMLPresentation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSONPresentationFolderReaderTest {

    private final JSONPresentationFolderReader reader = new JSONPresentationFolderReader();

    @Test
    void testReadPresentationsFromFolder(@TempDir File tempDir) throws IOException {
        // Arrange
        String jsonContent = """
            {
                       "showtitle": "JSON-Based presentation example",
                       "slides": [
                         [
                           {
                             "type": "title",
                             "font": "Arial",
                             "content": "First slide"
                           },
                           {
                             "type": "text",
                             "font": "Times New Roman",
                             "indentation": 1,
                             "content": "Hello"
                           },
                           {
                             "type": "text",
                             "indentation": 2,
                             "content": "this is an"
                           },
                           {
                             "type": "text",
                             "indentation": 3,
                             "content": "example"
                           }
                         ],
                         [
                           {
                             "type": "title",
                             "content": "Images!"
                           },
                           {
                             "type": "image",
                             "src": "/example.jpg"
                           },
                           {
                             "type": "image",
                             "indentation": 3,
                             "src": "/some/nested/image.jpg"
                           }
                         ]
                       ]
                     }
        """;

        File jsonFile = new File(tempDir, "presentation.json");
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(jsonContent);
        }

        // Act
        List<Presentation> result = reader.readPresentationsFromFolder(tempDir);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.getFirst() instanceof JSONPresentation);
        val jsonPresentation = (JSONPresentation) result.getFirst();
        assertEquals("* JSON-Based presentation example *", jsonPresentation.getShowTitle());
        assertEquals(2, jsonPresentation.getSlides().size());
        val firstSlideTitle = (JSONTitle) jsonPresentation.getSlides().getFirst().getFirst();
        assertEquals("Arial", firstSlideTitle.getFont());
        assertEquals("First slide", firstSlideTitle.getContent());
        assertEquals("title", firstSlideTitle.getType());
        val text1 = (JSONText) jsonPresentation.getSlides().getFirst().get(1);
        assertEquals("Times New Roman", text1.getFont());
        assertEquals("text", text1.getType());
        assertEquals(1, text1.getIndentation());
        assertEquals("Hello", text1.getContent());
        val text2 = (JSONText) jsonPresentation.getSlides().getFirst().get(2);
        assertEquals("text", text2.getType());
        assertEquals(2, text2.getIndentation());
        assertEquals("this is an", text2.getContent());
        val text3 = (JSONText) jsonPresentation.getSlides().getFirst().get(3);
        assertEquals("text", text3.getType());
        assertEquals(3, text3.getIndentation());
        assertEquals("example", text3.getContent());

        val nextSlideTitle = (JSONTitle) jsonPresentation.getSlides().get(1).getFirst();
        assertEquals("Images!", nextSlideTitle.getContent());
        assertEquals("title", nextSlideTitle.getType());

        val text4 = (JSONImage) jsonPresentation.getSlides().get(1).get(1);
        assertEquals("/example.jpg", text4.getSrc());
        assertEquals("image", text4.getType());

        val text5 = (JSONImage) jsonPresentation.getSlides().get(1).get(2);
        assertEquals("/some/nested/image.jpg", text5.getSrc());
        assertEquals("image", text5.getType());
        assertEquals(3, text5.getIndentation());

    }

    @Test
    void testReadPresentationsFromEmptyFolder(@TempDir File tempDir) throws IOException {
        List<Presentation> result = reader.readPresentationsFromFolder(tempDir);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
