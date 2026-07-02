package nl.stenden.youri.presentationviewer.model;

import nl.stenden.youri.presentationviewer.model.dto.PresentationDto;
import nl.stenden.youri.presentationviewer.model.dto.SlideDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PresentationModelTest {

    private PresentationDto presentation1;
    private PresentationDto presentation2;
    private SlideDto slide1;
    private SlideDto slide2;

    private PresentationFolderRecord folderRecord1;
    private PresentationFolderRecord folderRecord2;

    private PresentationModel model;

    private boolean wasNotified;

    @BeforeEach
    void setUp() {
        slide1 = SlideDto.builder().build();
        slide2 = SlideDto.builder().build();

        presentation1 = PresentationDto.builder().showTitle("Presentation A").slides(List.of(slide1, slide2)).build();
        presentation2 = PresentationDto.builder().showTitle("Presentation B").slides(List.of()).build();

        folderRecord1 = new PresentationFolderRecord("Folder 1", List.of(presentation1));
        folderRecord2 = new PresentationFolderRecord("Folder 2", List.of(presentation2));

        model = new PresentationModel(List.of(folderRecord1, folderRecord2));

        // Observer for notification checks
        model.addObserver((o, arg) -> wasNotified = true);
        wasNotified = false;
    }

    @Test
    void testInit() {
        assertEquals(folderRecord1, model.getSelectedFolder());
        assertEquals("Presentation A", model.getSelectedPresentationTitle());
        assertEquals(List.of(slide1, slide2), model.getSlides());
        assertEquals(slide1, model.getSelectedSlide());
    }

    @Test
    void testNextSlide() {
        model.nextSlide();
        assertEquals(slide2, model.getSelectedSlide());
        assertTrue(wasNotified);
    }

    @Test
    void testNextSlide_AtEndDoesNotAdvance() {
        model.nextSlide(); // to slide 1
        wasNotified = false;
        model.nextSlide(); // already at last
        assertEquals(slide2, model.getSelectedSlide());
        assertFalse(wasNotified);
    }

    @Test
    void testPreviousSlide() {
        model.nextSlide(); // now at slide 2
        wasNotified = false;
        model.previousSlide();
        assertEquals(slide1, model.getSelectedSlide());
        assertTrue(wasNotified);
    }

    @Test
    void testPreviousSlide_AtBeginningDoesNothing() {
        model.previousSlide();
        assertEquals(slide1, model.getSelectedSlide());
        assertFalse(wasNotified);
    }

    @Test
    void testSelectSlide_ValidIndex() {
        model.selectSlide(1);
        assertEquals(slide2, model.getSelectedSlide());
        assertTrue(wasNotified);
    }

    @Test
    void testSelectSlide_InvalidIndex_DoesNothing() {
        model.selectSlide(999);
        assertEquals(slide1, model.getSelectedSlide());
        assertFalse(wasNotified);
    }

    @Test
    void testSetSelectedFolderIndex_Valid() {
        model.setSelectedFolderIndex(1);
        assertEquals(folderRecord2, model.getSelectedFolder());
        assertNull(model.getSelectedSlide()); // empty slide list
        assertTrue(wasNotified);
    }

    @Test
    void testSetSelectedFolderIndex_InvalidIndex_DoesNothing() {
        model.setSelectedFolderIndex(99);
        assertEquals(folderRecord1, model.getSelectedFolder());
        assertFalse(wasNotified);
    }

    @Test
    @DisplayName("Geen presentaties gevonden, niets te tonen : ERROR test")
    void testModelWithEmptyFolders() {
        PresentationModel emptyModel = new PresentationModel(List.of());
        assertNull(emptyModel.getSelectedFolder());
        assertNull(emptyModel.getSelectedSlide());
        assertTrue(emptyModel.getSlides().isEmpty());
    }
}
