package nl.stenden.youri.presentationviewer.model.dto;

import lombok.*;

/**
 * Inheritance:
 * deze classe is een representatie voor een text base data transfer object
 * waarvan de eigenschappen en gedrag(methoden) van een andere klasse erft. de overervende klasse is de subclass en dit is de superclass
 * <p>
 * de getters en setters worden automatisch gegenereerd door de Lombok bibliotheek en zijn de methoden die worden gebruikt in de inheritance
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextBaseDto extends BaseDto {
    private String font;
}
