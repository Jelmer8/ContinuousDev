package nl.stenden.youri.presentationviewer.controller;

import nl.stenden.youri.presentationviewer.model.PresentationModel;

public class SelectSlideCommand implements Command {
    private final PresentationModel model;
    private final int index;

    /**
     * Constructor voor SelectSlideCommand.
     *
     * @param model het PresentationModel
     * @param index de index van de slide die geselecteerd kan worden
     */
    public SelectSlideCommand(PresentationModel model, int index) {
        this.model = model;
        this.index = index;
    }

    /**
     * Executes the command to select a slide in the presentation model.
     * This will change the selected slide index in the model.
     */
    @Override
    public void execute() {
        model.selectSlide(index);
    }
}
