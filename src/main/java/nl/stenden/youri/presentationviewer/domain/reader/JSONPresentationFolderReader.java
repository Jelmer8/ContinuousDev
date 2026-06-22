package nl.stenden.youri.presentationviewer.domain.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONPresentation;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class om de JSON presentations van een folder in te lezen folder
 * <p>
 * Jackson is een veelgebruikte library voor het lezen en schrijven van JSON in Java. Het biedt een flexibele en krachtige manier om JSON te verwerken.
 * we gebruiken hier de ObjectMapper class van Jackson om JSON-bestanden te lezen en te converteren naar Java-objecten.
 * <p>
 * Jackson heeft wel een representatie nodig van de JSON structuur die we willen lezen. In dit geval is dat de JSONPresentation class met onderliggende json file object structuur.
 */
@Component
public class JSONPresentationFolderReader {

    // De object mapper die gebruikt wordt om de JSON bestanden om te zetten naar een Java Object
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Leest al de .json presentatie files in van een bepaalde folder, en zet deze om jackson naar een lijst van JSONPresentaties (java objecten)
     *
     * @param folderPath : de folder waar de .json presentaties in zitten
     * @return List<Presentation> : een lijst van ingelezen json presentaties
     * @throws IOException : als er een fout optreedt bij het lezen en verwerken van de bestanden
     */
    public List<Presentation> readPresentationsFromFolder(File folderPath) throws IOException {
        File[] files = folderPath.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        List<Presentation> presentations = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                JSONPresentation jsonPresentation = objectMapper.readValue(file, JSONPresentation.class);
                presentations.add(jsonPresentation);
            }
        }
        return presentations;
    }

}

