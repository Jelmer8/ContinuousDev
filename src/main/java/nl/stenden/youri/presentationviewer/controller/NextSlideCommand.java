package nl.stenden.youri.presentationviewer.controller;

import nl.stenden.youri.presentationviewer.model.PresentationModel;

public class NextSlideCommand implements Command {
    private final PresentationModel model;

    /**
     * Constructor voor de NextSlideCommand klasse.
     *
     * @param model het PresentationModel dat de presentatie beheert
     */
    public NextSlideCommand(PresentationModel model) {
        this.model = model;
    }

    /**
     * Voert de opdracht uit om naar de volgende slide te gaan.
     * Dit doet het door de nextSlide methode van het model aan te roepen.
     */
    @Override
    public void execute() {
        model.nextSlide();
    }
}
