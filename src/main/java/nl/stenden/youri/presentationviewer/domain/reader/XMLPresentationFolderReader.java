package nl.stenden.youri.presentationviewer.domain.reader;

import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.XMLPresentation;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * class om de XML presentations van een folder in te lezen
 * <p>
 * JAXB is een veelgebruikte library voor het lezen en schrijven van XML in Java. Het biedt een flexibele en krachtige manier om XML te verwerken.
 * we gebruiken hier de Unmarshaller class van Jaxb om XML-bestanden te lezen en te converteren naar Java-objecten.
 * <p>
 * Jaxb heeft wel een representatie nodig van de XML structuur die we willen lezen. In dit geval is dat de XMLPresentation class met onderliggende xml file object structuur.
 */
@Component
public class XMLPresentationFolderReader {

    /**
     * Leest al de .xml presentatie files in van een bepaalde folder, en zet deze om via jaxb naar een lijst van XMLPresentaties (java objecten)
     *
     * @param folderPath : de folder waar de presentaties in zitten
     * @return List<Presentation> : een lijst van ingelezen .xml presentaties
     * @throws JAXBException : als er een fout optreedt bij het lezen van de bestanden en het omzetten naar een java object
     */
    public List<Presentation> readPresentationsFromFolder(File folderPath) throws JAXBException {
        File[] files = folderPath.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

        List<Presentation> presentations = new ArrayList<>();

        if (files != null) {
            JAXBContext jaxbContext = JAXBContext.newInstance(XMLPresentation.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            for (File file : files) {
                XMLPresentation xmlPresentation = (XMLPresentation) unmarshaller.unmarshal(file);
                presentations.add(xmlPresentation);
            }
        }
        return presentations;
    }

}

