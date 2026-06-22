package nl.stenden.youri.presentationviewer.view;

import lombok.val;
import nl.stenden.youri.presentationviewer.controller.NextSlideCommand;
import nl.stenden.youri.presentationviewer.controller.PreviousSlideCommand;
import nl.stenden.youri.presentationviewer.controller.SelectSlideCommand;
import nl.stenden.youri.presentationviewer.domain.reader.PresentationFolderReader;
import nl.stenden.youri.presentationviewer.model.PresentationFolderRecord;
import nl.stenden.youri.presentationviewer.model.PresentationModel;
import nl.stenden.youri.presentationviewer.model.dto.ImageDto;
import nl.stenden.youri.presentationviewer.model.dto.SlideDto;
import nl.stenden.youri.presentationviewer.model.dto.TextDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * De presentatie viewer klasse die de GUI voor de presentatie viewer implementeert.
 * Deze klasse toont een lijst van presentaties en de slides van de geselecteerde presentatie.
 * Het maakt gebruik van het Model-View-Controller (MVC) patroon.
 */
@Component
public class PresentationView extends JFrame implements Observer {

    // Model dat de staat van de presentatie bijhoudt
    private final PresentationModel model;
    //bestandnamen lijst van presentaties
    private JList<String> bestandsnamenLijst;
    //panel waarin de slides worden weergegeven
    private final JPanel slidePanel;
    // Knoppen voor navigatie en acties
    private JButton buttonVolgende, buttonVorige, buttonSelecteer, buttonExit;

    /**
     * Constructor voor de presentatie viewer.
     * Leest de presentaties uit de opgegeven folder en initialiseert de GUI componenten.
     *
     * @param presentationFolderReader de reader om presentaties te lezen, wordt via dependency injection (Spring) geleverd
     */
    public PresentationView(PresentationFolderReader presentationFolderReader) {

        //setup van de JFrame
        setTitle("Presentation Viewer of Youri Zondervan");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        val presentationFolderRecords = presentationFolderReader.readPresentations(PresentationFolderReader.ROOT_FOLDER_PATH);
        this.model = new PresentationModel(presentationFolderRecords);
        model.addObserver(this);

        // Linker panel - lijst met bestandsnamen van presentaties waaruit gekozen kan worden
        initPresentatieBestandsnamenLijst();

        // Slide paneel in centrum
        slidePanel = new JPanel(new BorderLayout());
        add(slidePanel, BorderLayout.CENTER);

        // Button paneel onderin
        initButtonPanel();

        // init slide op eerste bestand met presentaties en eerste index
        updateSlidePanel();

    }

    private static void zetHeaderTeSelecterenPresentaties(JScrollPane teSelecterenPresentaties) {
        JLabel label = new JLabel("Te selecteren presentaties:");
        label.setAlignmentX(1);
        label.setAlignmentY(1);
        teSelecterenPresentaties.setColumnHeaderView(label);
    }

    private void initButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonVorige = new JButton("Vorige");
        buttonVolgende = new JButton("Volgende");
        buttonSelecteer = new JButton("Selecteer Slide...");
        buttonExit = new JButton("Afsluiten");

        buttonPanel.add(buttonVorige);
        buttonPanel.add(buttonSelecteer);
        buttonPanel.add(buttonVolgende);
        buttonPanel.add(buttonExit);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button acties via commands
        buttonVolgende.addActionListener(e -> new NextSlideCommand(model).execute());
        buttonVorige.addActionListener(e -> new PreviousSlideCommand(model).execute());
        buttonExit.addActionListener(e -> System.exit(0));

        buttonSelecteer.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Slide nummer: [" + model.getSlides().size() + "]");

            if (input != null) {
                try {
                    int index = Integer.parseInt(input) - 1;
                    new SelectSlideCommand(model, index).execute();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ongeldig nummer");
                }
            }
        });

        updateButtonPanel();
    }

    private void initPresentatieBestandsnamenLijst() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (PresentationFolderRecord folder : model.getFolderRecords()) {
            listModel.addElement(folder.folderName());
        }
        bestandsnamenLijst = new JList<>(listModel);
        bestandsnamenLijst.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Color txtcolor = new Color(0, 0, 0);
        bestandsnamenLijst.setForeground(txtcolor);
        Color bgcolor = new Color(255, 255, 255);
        bestandsnamenLijst.setBackground(bgcolor);

        bestandsnamenLijst.setFont(new Font("verdana", Font.BOLD, 12));
        Color bordercolor = new Color(57, 55, 55);
        bestandsnamenLijst.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, bordercolor));

        bestandsnamenLijst.setSelectedIndex(model.getSelectedFolder() != null ? model.getFolderRecords().indexOf(model.getSelectedFolder()) : -1);

        bestandsnamenLijst.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = bestandsnamenLijst.getSelectedIndex();
                if (index != -1) {
                    model.setSelectedFolderIndex(index);
                }
            }
        });

        JScrollPane teSelecterenPresentaties = new JScrollPane(bestandsnamenLijst);
        teSelecterenPresentaties.setPreferredSize(new Dimension(300, 0));
        zetHeaderTeSelecterenPresentaties(teSelecterenPresentaties);
        add(teSelecterenPresentaties, BorderLayout.WEST);
    }

    public void updateSlidePanel() {
        slidePanel.removeAll();

        SlideDto slide = model.getSelectedSlide();
        if (slide != null) {
            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

            slidePanel.setBorder(BorderFactory.createTitledBorder("Slide " + (model.getSelectedSlideIndex() + 1) + " van " + model.getSlides().size() + " - " + model.getSelectedPresentationTitle()));

            // Titel
            if (slide.getTitle() != null) {
                content.add(PresentationUtil.addTitle(slide.getTitle()));
            }

            // Texts
            if (slide.getTexts() != null) {
                for (TextDto text : slide.getTexts()) {
                    content.add(PresentationUtil.addText(text));
                }
            }

            // Images (voorbeeld: alleen naam tonen)
            if (slide.getImages() != null) {
                for (ImageDto img : slide.getImages()) {
                    content.add(PresentationUtil.addImage(img, "presentations/".concat(model.getSelectedFolder().folderName())));
                }
            }

            slidePanel.add(content, BorderLayout.CENTER);
        } else {

            slidePanel.add(new JLabel("Geen slide beschikbaar"), BorderLayout.CENTER);
        }

        updateButtonPanel();

        slidePanel.revalidate();
        slidePanel.repaint();
    }

    private void updateButtonPanel() {

        if (ObjectUtils.isEmpty(model.getSelectedSlide())) {
            buttonVorige.setVisible(false);
            buttonVolgende.setVisible(false);
            buttonSelecteer.setVisible(false);
        } else {
            buttonVorige.setVisible(true);
            buttonVolgende.setVisible(true);
            buttonSelecteer.setVisible(true);

            buttonVorige.setEnabled(true);
            buttonVolgende.setEnabled(true);
            buttonSelecteer.setEnabled(true);
            if (ObjectUtils.isEmpty(model.getSlides())) {
                buttonSelecteer.setEnabled(false);
            }
            if (model.getSelectedSlideIndex() == 0) {
                buttonVorige.setEnabled(false);
            }
            if (model.getSelectedSlideIndex() == model.getSlides().size() - 1) {
                buttonVolgende.setEnabled(false);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateSlidePanel();
    }
}
