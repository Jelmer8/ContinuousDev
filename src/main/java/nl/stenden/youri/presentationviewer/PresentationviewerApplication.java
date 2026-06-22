package nl.stenden.youri.presentationviewer;

import nl.stenden.youri.presentationviewer.view.PresentationView;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.swing.*;

/**
 * Main class van de PresentationViewer applicatie
 */
@SpringBootApplication
public class PresentationviewerApplication implements CommandLineRunner {

    private final ApplicationContext context;

    public PresentationviewerApplication(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(PresentationviewerApplication.class, args);
    }

    @Override
    public void run(String... args) {

        SwingUtilities.invokeLater(() -> {
            //om de applicatie te starten door de PresentationView klasse te gebruiken via de Spring context
            PresentationView frame = context.getBean(PresentationView.class);
            //om de applicatie in het midden van het scherm te plaatsen
            frame.setLocationRelativeTo(null);
            //om de applicatie zichtbaar te maken
            frame.setVisible(true);
        });
    }


}
