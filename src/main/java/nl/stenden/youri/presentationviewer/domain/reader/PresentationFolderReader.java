package nl.stenden.youri.presentationviewer.domain.reader;

import lombok.AllArgsConstructor;
import lombok.val;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;
import nl.stenden.youri.presentationviewer.domain.mapper.PresentatieDtoMapper;
import nl.stenden.youri.presentationviewer.model.PresentationFolderRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * class om de presentaties van een folder te lezen en daarna  om te zetten naar een java document object model (DOM) via een mapper.
 * hierbij rekening houden met de verschillende bestandsformaten (XML en JSON). Huidige opzet biedt ruimte voor uitbreiding naar andere formaten.
 *
 */

@Component
@AllArgsConstructor
public class PresentationFolderReader {
    public static final String ROOT_FOLDER_PATH = "src/main/resources/presentations/";
    //mapper die de presentatie objecten omzet naar een PresentationDto
    private final PresentatieDtoMapper presentatieDtoMapper;
    //specifieke readers voor de verschillende bestandsformaten
    private JSONPresentationFolderReader jsonPresentationFolderReader;
    private XMLPresentationFolderReader xmlPresentationFolderReader;

    /**
     * Controleert of de folder XML bestanden bevat
     *
     * @param folder de folder die gecontroleerd moet worden
     * @return boolean true als de folder XML bestanden bevat, anders false
     */
    private static boolean isXMLFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".xml")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Leest de presentaties van een folder en zet deze om naar een lijst van PresentationFolderRecords
     *
     * @param rootFolderPath de folder waar de presentaties in zitten
     * @return List<PresentationFolderRecord> een lijst van ingelezen presentaties
     */
    public List<PresentationFolderRecord> readPresentations(String rootFolderPath) {
        // Controleer of de opgegeven rootFolder bestaat, anders gebruik de standaard rootFolder
        String rootFolder = ObjectUtils.isEmpty(rootFolderPath) ? ROOT_FOLDER_PATH : rootFolderPath;

        // Maak een File object van de rootFolder en haal alle subfolders op die presentaties bevatten
        File rootFolderFile = new File(rootFolder);
        // Controleer of de rootFolder een geldige directory is
        File[] presentationsFolders = rootFolderFile.listFiles(File::isDirectory);
        List<PresentationFolderRecord> presentationList = new ArrayList<>();

        if (presentationsFolders != null) {
            for (File presentationFolder : presentationsFolders) {
                // Controleer of de folder XML of JSON presentaties bevat en maak de juiste PresentationFolderRecord aan
                if (isXMLFolder(presentationFolder)) {
                    presentationList.add(createXMLPresentations(presentationFolder));
                } else {
                    presentationList.add(createJSONPresentations(presentationFolder));
                }
            }
        }

        return presentationList;
    }

    /**
     * Maakt een PresentationFolderRecord aan voor de XML presentaties in de folder
     *
     * @param presentationFolder de folder waar de presentaties in zitten
     * @return PresentationFolderRecord de aangemaakte PresentationFolderRecord
     */
    private PresentationFolderRecord createXMLPresentations(File presentationFolder) {
        try {
            // Lees de XML presentaties uit de folder en map deze naar PresentationDto's
            List<Presentation> xmlPresentations = xmlPresentationFolderReader.readPresentationsFromFolder(presentationFolder);
            val mappedDtoPresentations = xmlPresentations.stream()
                    .map(presentatieDtoMapper::mapToPresentationDto)
                    .toList();

            return new PresentationFolderRecord(presentationFolder.getName(), mappedDtoPresentations);
        } catch (JAXBException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Maakt een PresentationFolderRecord aan voor de JSON presentaties in de folder
     *
     * @param presentationFolder de folder waar de presentaties in zitten
     * @return PresentationFolderRecord de aangemaakte PresentationFolderRecord
     */
    private PresentationFolderRecord createJSONPresentations(File presentationFolder) {
        try {
            // Lees de JSON presentaties uit de folder en map deze naar PresentationDto's
            List<Presentation> jsonPresentations = jsonPresentationFolderReader.readPresentationsFromFolder(presentationFolder);
            val mappedDtoPresentations = jsonPresentations.stream()
                    .map(presentatieDtoMapper::mapToPresentationDto)
                    .toList();
            return new PresentationFolderRecord(presentationFolder.getName(), mappedDtoPresentations);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
