package nl.stenden.youri.presentationviewer.model.xml;

import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.XMLPresentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.XMLSlide;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XMLPresentationTest {

    private static final String XML_DATA = """
        <?xml version="1.0"?>
        <presentation>
            <showtitle>XML-Based presentation example</showtitle>
            <slide>
                <title font="Arial">First slide</title>
                <text font="Times New Roman" indentation="1">Hello</text>
                <text indentation="2">this is an</text>
                <text indentation="3">example</text>
                <image src="/example.jpg"/>
                <text>See image</text>
            </slide>
            <slide>
                <title font="Helvetica">Another Slide</title>
                <text font="Verdana" indentation="1">More</text>
                <text indentation="2">example lines</text>
                <text indentation="5">to test your application</text>
            </slide>
            <slide>
                <title font="Serif">Here we are again</title>
                <text font="Trebuchet">Creating these example slides is very boring</text>
                <text indentation="2">The things I do for you guys</text>
                <text indentation="1" font="Calibri">It's not that hard</text>
                <text indentation="1" font="Calibri">But the conversion to JSON is the real pain</text>
                <image src="/sadface.png"/>
            </slide>
        </presentation>
        """;

    @Test
    public void testUnmarshalXMLPresentation() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(XMLPresentation.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(XML_DATA);
        XMLPresentation presentation = (XMLPresentation) unmarshaller.unmarshal(reader);

        assertNotNull(presentation);
        assertEquals("# XML-Based presentation example #", presentation.getShowTitle());

        List<XMLSlide> slides = presentation.getSlides();
        assertNotNull(slides);
        assertEquals(3, slides.size());

        XMLSlide firstSlide = slides.getFirst();
        assertNotNull(firstSlide);
        assertEquals("First slide", firstSlide.getTitle().getValue());
        assertEquals("Arial", firstSlide.getTitle().getFont());
        assertEquals("Times New Roman", firstSlide.getTexts().getFirst().getFont());
        assertEquals(1, firstSlide.getTexts().get(0).getIndentation());
        assertEquals("Hello", firstSlide.getTexts().get(0).getValue());
        assertEquals(2, firstSlide.getTexts().get(1).getIndentation());
        assertEquals("this is an", firstSlide.getTexts().get(1).getValue());
        assertEquals(3, firstSlide.getTexts().get(2).getIndentation());
        assertEquals("example", firstSlide.getTexts().get(2).getValue());
        assertEquals("See image", firstSlide.getTexts().get(3).getValue());
        assertEquals("/example.jpg", firstSlide.getImages().getFirst().getSrc());
        assertEquals(0, firstSlide.getImages().getFirst().getIndentation());
    }

}
