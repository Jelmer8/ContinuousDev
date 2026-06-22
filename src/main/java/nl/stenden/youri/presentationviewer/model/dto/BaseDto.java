package nl.stenden.youri.presentationviewer.model.dto;

import lombok.Data;

/**
 * Inheritance:
 * deze classe is een representatie voor een basis data transfer object
 * waarvan de eigenschappen en gedrag(methoden) van een andere klasse erft. de overervende klasse is de subclass en dit is de superclass
 * <p>
 * de getters en setters worden automatisch gegenereerd door de Lombok bibliotheek (@Data) en zijn de methoden die worden gebruikt in de inheritance
 */
@Data
public class BaseDto {
    private String value;
    private int identation;
}
