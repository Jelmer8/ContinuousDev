package nl.stenden.youri.presentationviewer.model;

import com.sun.istack.NotNull;
import nl.stenden.youri.presentationviewer.model.dto.PresentationDto;

import java.util.List;

/**
 * deze classe is een gegevens representatie voor een presentatie folder record
 * <p>
 * een presentatie folder record bestaat uit een folder naam en een lijst van naar presentatie model getransformeerde epresentaties
 *
 */
public record PresentationFolderRecord(@NotNull String folderName, @NotNull List<PresentationDto> presentations) {
}
