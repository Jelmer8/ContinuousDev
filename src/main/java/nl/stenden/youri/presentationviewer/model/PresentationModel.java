package nl.stenden.youri.presentationviewer.model;

import lombok.Getter;
import nl.stenden.youri.presentationviewer.model.dto.SlideDto;

import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * PresentationModel is een model dat de staat van de presentatie bijhoudt.
 * Het bevat een lijst van PresentationFolderRecords en houdt de geselecteerde folder en geselecteerde slide bij.
 */

@Getter
public class PresentationModel extends Observable {
    private final List<PresentationFolderRecord> folderRecords;
    private int selectedFolderIndex = -1;
    private int selectedSlideIndex = 0;

    /**
     * Constructor voor PresentationModel.
     *
     * @param folderRecords de lijst van PresentationFolderRecords die dit model beheert
     */
    public PresentationModel(List<PresentationFolderRecord> folderRecords) {
        this.folderRecords = folderRecords;
        if (!folderRecords.isEmpty()) selectedFolderIndex = 0;
    }

    /**
     * Retourneert geselecteerde folder record.
     *
     * @return PresentationFolderRecords
     */
    public PresentationFolderRecord getSelectedFolder() {
        if (selectedFolderIndex >= 0 && selectedFolderIndex < folderRecords.size()) {
            return folderRecords.get(selectedFolderIndex);
        }
        return null;
    }

    public String getSelectedPresentationTitle() {
        PresentationFolderRecord folder = getSelectedFolder();
        if (folder != null && !folder.presentations().isEmpty()) {
            return folder.presentations().getFirst().getShowTitle(); // altijd eerste presentatie
        }
        return null;
    }

    /**
     * Retourneert de lijst van slides van de geselecteerde folder.
     * Als er geen folder is geselecteerd of de folder geen presentaties bevat, wordt een lege lijst geretourneerd.
     *
     * @return List<SlideDto> de lijst van slides in de geselecteerde folder
     */
    public List<SlideDto> getSlides() {
        PresentationFolderRecord folder = getSelectedFolder();
        if (folder != null && !folder.presentations().isEmpty()) {
            return folder.presentations().getFirst().getSlides(); // altijd eerste presentatie
        }
        return Collections.emptyList();
    }

    /**
     * Retourneert de geselecteerde slide.
     * Als er geen slides zijn of de index is ongeldig, wordt null geretourneerd.
     *
     * @return SlideDto de geselecteerde slide of null als er geen geldige slide is
     */
    public SlideDto getSelectedSlide() {
        List<SlideDto> slides = getSlides();
        if (!slides.isEmpty() && selectedSlideIndex >= 0 && selectedSlideIndex < slides.size()) {
            return slides.get(selectedSlideIndex);
        }
        return null;
    }

    /**
     * Geselecteerde Folder selectie actie : Zet de geselecteerde folder index en reset de slide selectie  en notify observers.
     *
     * @param index de nieuwe index van de geselecteerde folder
     */
    public void setSelectedFolderIndex(int index) {
        if (index != selectedFolderIndex && index >= 0 && index < folderRecords.size()) {
            selectedFolderIndex = index;
            selectedSlideIndex = 0; // reset slide selectie
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Geselecteerde Slide next button actie :  Verplaatst naar de volgende slide in de huidige presentatie  en notify observers.
     */
    public void nextSlide() {
        if (selectedSlideIndex < getSlides().size() - 1) {
            selectedSlideIndex++;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Geselecteerde Slide previous button actie :  Verplaatst naar de vorige slide in de huidige presentatie en notify observers.
     */
    public void previousSlide() {
        if (selectedSlideIndex > 0) {
            selectedSlideIndex--;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Geselecteerde Slide select actie :  Zet de geselecteerde slide index en notify observers.
     *
     * @param index de nieuwe index van de geselecteerde slide
     */
    public void selectSlide(int index) {
        selectedSlideIndex = index;
        setChanged();
        notifyObservers();
    }
}
