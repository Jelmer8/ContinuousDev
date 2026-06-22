package nl.stenden.youri.presentationviewer.controller;

import nl.stenden.youri.presentationviewer.model.PresentationModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NextSlideCommandTest {

    @Mock
    PresentationModel model;

    @InjectMocks
    NextSlideCommand nextSlideCommand;

    @Test
    void execute() {

        //verwachten dat de methode nextSlide() van het model wordt aangeroepen
        BDDMockito.doNothing().when(model).nextSlide();

        nextSlideCommand.execute();

        //controleren of de methode nextSlide() van het model is aangeroepen
        BDDMockito.verify(model).nextSlide();
    }
}
