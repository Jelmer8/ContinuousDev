package nl.stenden.youri.presentationviewer.controller;

import nl.stenden.youri.presentationviewer.model.PresentationModel;
import nl.stenden.youri.presentationviewer.view.PresentationView;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    @Test
    void edgeCase() {
        // Precondities zetten en initialiseren van de test
        PresentationModel model = Mockito.mock(PresentationModel.class);
        PresentationView view = Mockito.mock(PresentationView.class);

        int slideIndex = -1;
        SelectSlideCommand command = new SelectSlideCommand(model, slideIndex);

        command.execute();

        Mockito.verify(view, Mockito.times(0)).updateSlidePanel();

    }
}
