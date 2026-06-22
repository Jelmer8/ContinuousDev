package nl.stenden.youri.presentationviewer.view;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

/**
 * Abstracte klasse voor het lezen van bestanden.
 * Deze klasse bevat een methode om een bestand op te halen van een bepaald pad.
 * Het wordt voornamelijk gebruikt om afbeeldingen in te lezen, maar kan ook voor andere bestanden worden gebruikt.
 */
public abstract class AbstractFileReader {

    /**
     * Haalt een file op van een bepaald pad, wordt nu vooral gebruikt om plaatjes in te lezen, maar kan ook gebruikt worden voor andere bestanden.
     *
     * @param path : het pad van de file
     * @return File : de file die opgehaald is
     * @throws IOException : als er een fout optreedt bij het ophalen van de file
     */
    protected static File fileFromPath(String path) throws IOException {
        return new ClassPathResource(path).getFile();
    }
}
