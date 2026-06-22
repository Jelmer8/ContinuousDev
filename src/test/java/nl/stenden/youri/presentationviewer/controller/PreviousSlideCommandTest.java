package nl.stenden.youri.presentationviewer.controller;

import nl.stenden.youri.presentationviewer.model.PresentationModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PreviousSlideCommandTest {

    @Mock
    PresentationModel model;

    @InjectMocks
    PreviousSlideCommand previousSlideCommand;

    @Test
    void execute() {

        //verwachten dat de methode previousSlide() van het model wordt aangeroepen
        BDDMockito.doNothing().when(model).previousSlide();

        previousSlideCommand.execute();

        //controleren of de methode previousSlide() van het model is aangeroepen
        BDDMockito.verify(model).previousSlide();
    }
}
