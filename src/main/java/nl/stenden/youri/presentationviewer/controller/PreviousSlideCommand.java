package nl.stenden.youri.presentationviewer.controller;

import nl.stenden.youri.presentationviewer.model.PresentationModel;

public class PreviousSlideCommand implements Command {
    private final PresentationModel model;

    /**
     * Constructor voor de PreviousSlideCommand klasse.
     *
     * @param model het PresentationModel dat de huidige presentatie beheert
     */
    public PreviousSlideCommand(PresentationModel model) {
        this.model = model;
    }

    /**
     * Voert de opdracht uit om naar de vorige slide te gaan.
     * Dit doet het door de previous methode van het model aan te roepen.
     */
    @Override
    public void execute() {
        model.previousSlide();
    }
}
