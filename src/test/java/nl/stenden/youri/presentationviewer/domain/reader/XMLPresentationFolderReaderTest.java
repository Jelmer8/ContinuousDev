package nl.stenden.youri.presentationviewer.domain.reader;

import lombok.val;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.XMLPresentation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.SAXParseException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class XMLPresentationFolderReaderTest {

    private final XMLPresentationFolderReader reader = new XMLPresentationFolderReader();

    @Test
    void testReadPresentationsFromFolder(@TempDir File tempDir) throws Exception {
        // Arrange
        String xmlContent = """
                   <presentation>
                          <showtitle>XML-Based presentation example</showtitle>

                          <slide>
                              <title font="Arial">First slide</title>
                              <text font="Times New Roman" indentation="1">Hello</text>
                              <text indentation="2">this is an</text>
                              <text indentation="3">example</text>
                          </slide>

                          <slide>
                              <title>Images!</title>
                              <image src="/example.jpg"/>
                              <image indentation="3" src="/some/nested/image.jpg"/>
                          </slide>

                      </presentation>
                """;

        File xmlFile = new File(tempDir, "presentation.xml");
        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write(xmlContent);
        }

        // Act
        List<Presentation> result = reader.readPresentationsFromFolder(tempDir);

        // Assert
        assertEquals(1, result.size());
        assertInstanceOf(XMLPresentation.class, result.getFirst());
        val xmlPresentation = (XMLPresentation) result.getFirst();
        assertEquals("# XML-Based presentation example #", xmlPresentation.getShowTitle());
        assertEquals(2, xmlPresentation.getSlides().size());
        assertEquals("First slide", xmlPresentation.getSlides().getFirst().getTitle().getValue());
        assertEquals("Arial", xmlPresentation.getSlides().getFirst().getTitle().getFont());

        assertEquals("Times New Roman", xmlPresentation.getSlides().getFirst().getTexts().getFirst().getFont());
        assertEquals(1, xmlPresentation.getSlides().getFirst().getTexts().getFirst().getIndentation());
        assertEquals("Hello", xmlPresentation.getSlides().getFirst().getTexts().getFirst().getValue());

        assertEquals("this is an", xmlPresentation.getSlides().getFirst().getTexts().get(1).getValue());
        assertEquals(2, xmlPresentation.getSlides().getFirst().getTexts().get(1).getIndentation());

        assertEquals("example", xmlPresentation.getSlides().getFirst().getTexts().get(2).getValue());
        assertEquals(3, xmlPresentation.getSlides().getFirst().getTexts().get(2).getIndentation());

        assertEquals("Images!", xmlPresentation.getSlides().get(1).getTitle().getValue());
        assertEquals("/example.jpg", xmlPresentation.getSlides().get(1).getImages().getFirst().getSrc());
        assertEquals("/some/nested/image.jpg", xmlPresentation.getSlides().get(1).getImages().get(1).getSrc());
        assertEquals(3, xmlPresentation.getSlides().get(1).getImages().get(1).getIndentation());

    }

    @Test
    void testReadPresentationsFromEmptyFolder(@TempDir File tempDir) throws JAXBException {
        List<Presentation> result = reader.readPresentationsFromFolder(tempDir);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Unmarshall ERROR test")
    void testReadPresentationsFromFolderError(@TempDir File tempDir) throws Exception {
        // Arrange
        String xmlContent = """
                   <presentation>
                          <showtitle>XML-Based presentation example</showtitle>

                          <slideError>
                              <title font="Arial">First slide</title>
                              <text font="Times New Roman" indentation="1">Hello</text>
                              <text indentation="2">this is an</text>
                              <text indentation="3">example</text>
                          </slide>

                          <slideX>
                              <title>Images!</title>
                              <image src="/example.jpg"/>
                              <image indentation="3" src="/some/nested/image.jpg"/>
                          </slide>

                      </presentationA>
                """;

        File xmlFile = new File(tempDir, "presentation.xml");
        try (FileWriter writer = new FileWriter(xmlFile)) {
            writer.write(xmlContent);
        }

        val thrown = assertThrows(
                UnmarshalException.class,
                () ->  reader.readPresentationsFromFolder(tempDir),
                "The element type \"slideError\" must be terminated by the matching end-tag \"</slideError>\"."
        );

        assertTrue(thrown.getCause().getMessage().contains("The element type \"slideError\" must be terminated by the matching end-tag \"</slideError>\"."));


    }
}
