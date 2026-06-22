package nl.stenden.youri.presentationviewer.view;

import nl.stenden.youri.presentationviewer.model.dto.ImageDto;
import nl.stenden.youri.presentationviewer.model.dto.TextDto;
import nl.stenden.youri.presentationviewer.model.dto.TitleDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.*;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class PresentationUtilTest {

    @TempDir
    File tempDir;


    @Test
    void testAddImage_ReturnsNullOnInvalidPath() {
        // Arrange
        ImageDto imageDto = new ImageDto();
        imageDto.setIdentation(0);
        imageDto.setValue("nonexistent.png");

        // Act
        JLabel label = PresentationUtil.addImage(imageDto, tempDir.getAbsolutePath() + File.separator);

        // Assert
        assertThat(label).isNull(); // Expected to fail
    }

    @Test
    void testAddTitle_ReturnsJLabel() {
        // Arrange
        TitleDto titleDto = new TitleDto();
        titleDto.setFont("Arial");
        titleDto.setValue("My Title");


        // Act
        JLabel label = PresentationUtil.addTitle(titleDto);

        // Assert
        assertThat(label.getText()).isEqualTo("Title : My Title");
        assertThat(label.getFont().getName()).isEqualTo("Arial");
    }

    @Test
    void testAddTitle() {
        // Arrange
        TitleDto titleDto = new TitleDto();
        titleDto.setValue("Untitled");

        // Act
        JLabel label = PresentationUtil.addTitle(titleDto);

        // Assert
        assertThat(label.getFont()).isNotNull(); // Should not throw
    }

    @Test
    void testAddText_ReturnsJLabel() {
        // Arrange
        TextDto textDto = new TextDto();
        textDto.setValue("Some text");
        textDto.setFont("Courier New");
        textDto.setIdentation(2);


        // Act
        JLabel label = PresentationUtil.addText(textDto);

        // Assert
        assertThat(label.getText()).isEqualTo("Some text");
        assertThat(label.getFont().getName()).isEqualTo("Courier New");
    }

    @Test
    void testAddText() {
        // Arrange
        TextDto textDto = new TextDto();
        textDto.setValue("Default text");

        // Act
        JLabel label = PresentationUtil.addText(textDto);

        // Assert
        assertThat(label.getText()).isEqualTo("Default text");
        assertThat(label.getFont()).isNotNull();
    }
}
