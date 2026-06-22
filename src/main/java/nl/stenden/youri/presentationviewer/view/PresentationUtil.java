package nl.stenden.youri.presentationviewer.view;

import lombok.experimental.UtilityClass;
import nl.stenden.youri.presentationviewer.model.dto.ImageDto;
import nl.stenden.youri.presentationviewer.model.dto.TextDto;
import nl.stenden.youri.presentationviewer.model.dto.TitleDto;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * deze klasse is een utility class voor het toevoegen Swing elementen aan een scherm op basis van DTO elementen in een presentatie
 * <p>
 * doordat deze klasse een utility class is, is deze niet bedoeld om geinstantieerd te worden.
 *
 *
 */
@UtilityClass
public class PresentationUtil extends AbstractFileReader {

    /**
     * Voegt een Image toe aan een scherm
     *
     * @param image      : de Image die toegevoegd moet worden
     * @param pathprefix : de prefix van het pad waar de afbeelding zich bevindt
     * @return JLabel : de JLabel die de afbeelding bevat
     */
    public static JLabel addImage(ImageDto image, String pathprefix) {
        try {
            BufferedImage iPic = ImageIO.read(fileFromPath(pathprefix.concat(image.getValue())));
            JLabel imageLabel = new JLabel(new ImageIcon(iPic));
            int identation = ObjectUtils.isEmpty(image.getIdentation()) ? 1 : image.getIdentation() + 1;
            imageLabel.setBorder(BorderFactory.createEmptyBorder(5, (30 * identation), 10, 10));
            return imageLabel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Voegt een Title toe aan een scherm
     *
     * @param title : de Title die toegevoegd moet worden
     * @return JLabel : de JLabel die de titel bevat
     */
    public static JLabel addTitle(TitleDto title) {
        JLabel jLabelTitle = new JLabel();
        jLabelTitle.setText("Title : ".concat(title.getValue()));
        if (!ObjectUtils.isEmpty(title.getFont())) {
            jLabelTitle.setFont(new Font(title.getFont(), Font.PLAIN, 12));
        }
        jLabelTitle.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 10));
        return jLabelTitle;
    }

    /**
     * Voegt een Text toe aan een scherm
     *
     * @param text : de Text die toegevoegd moet worden
     * @return JLabel : de JLabel die de tekst bevat
     */
    public static JLabel addText(TextDto text) {
        JLabel textLabel = new JLabel();
        textLabel.setText(text.getValue());
        if (!ObjectUtils.isEmpty(text.getFont())) {
            textLabel.setFont(new Font(text.getFont(), Font.PLAIN, 12));
        }
        int identation = ObjectUtils.isEmpty(text.getIdentation()) ? 1 : text.getIdentation() + 1;
        textLabel.setBorder(BorderFactory.createEmptyBorder(5, 20 * identation, 5, 5));

        return textLabel;
    }

}
