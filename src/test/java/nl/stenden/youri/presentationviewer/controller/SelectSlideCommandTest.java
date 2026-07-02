package nl.stenden.youri.presentationviewer.controller;

import nl.stenden.youri.presentationviewer.model.PresentationModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class SelectSlideCommandTest {

    @Test
    void execute() {
        // Precondities zetten en initialiseren van de test
        PresentationModel model = Mockito.mock(PresentationModel.class);
        int slideIndex = 5;
        SelectSlideCommand command = new SelectSlideCommand(model, slideIndex);

        // uitvoeren
        command.execute();

        // Verwachting controleren
        Mockito.verify(model).selectSlide(slideIndex);
    }
}
