package nl.stenden.youri.presentationviewer.domain.reader;

import lombok.SneakyThrows;
import nl.stenden.youri.presentationviewer.domain.documentmodel.Presentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.json.JSONPresentation;
import nl.stenden.youri.presentationviewer.domain.documentmodel.xml.XMLPresentation;
import nl.stenden.youri.presentationviewer.domain.mapper.PresentatieDtoMapper;
import nl.stenden.youri.presentationviewer.model.PresentationFolderRecord;
import nl.stenden.youri.presentationviewer.model.dto.PresentationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresentationFolderReaderTest {

    @Mock
    private JSONPresentationFolderReader jsonReader;

    @Mock
    private XMLPresentationFolderReader xmlReader;

    @Mock
    private PresentatieDtoMapper mapper;

    @InjectMocks
    private PresentationFolderReader folderReader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadPresentations_WithXMLFolder(@TempDir File tempDir) throws IOException, JAXBException {
        // init
        File presentationFolder = new File(tempDir, "xmlPresentation");
        assertTrue(presentationFolder.mkdir());

        File xmlFile = new File(presentationFolder, "example.xml");
        assertTrue(xmlFile.createNewFile());

        Presentation mockPresentation = new XMLPresentation();
        PresentationDto mockDto = PresentationDto.builder().build();

        when(xmlReader.readPresentationsFromFolder(presentationFolder))
                .thenReturn(List.of(mockPresentation));
        when(mapper.mapToPresentationDto(mockPresentation)).thenReturn(mockDto);

        // aanroepen
        List<PresentationFolderRecord> results = folderReader.readPresentations(tempDir.getAbsolutePath());

        // Controleren
        assertEquals(1, results.size());
        assertEquals("xmlPresentation", results.get(0).folderName());
        assertEquals(1, results.get(0).presentations().size());
        verify(xmlReader, times(1)).readPresentationsFromFolder(presentationFolder);
        verify(mapper, times(1)).mapToPresentationDto(mockPresentation);
    }

    @Test
    void testReadPresentations_WithJSONFolder(@TempDir File tempDir) throws IOException {
        // init
        File presentationFolder = new File(tempDir, "jsonPresentation");
        assertTrue(presentationFolder.mkdir());

        File jsonFile = new File(presentationFolder, "example.json");
        assertTrue(jsonFile.createNewFile());

        Presentation mockPresentation = new JSONPresentation();
        PresentationDto mockDto = PresentationDto.builder().build();

        when(jsonReader.readPresentationsFromFolder(presentationFolder))
                .thenReturn(List.of(mockPresentation));
        when(mapper.mapToPresentationDto(mockPresentation)).thenReturn(mockDto);

        // aanroepen
        List<PresentationFolderRecord> results = folderReader.readPresentations(tempDir.getAbsolutePath());

        // controleren
        assertEquals(1, results.size());
        assertEquals("jsonPresentation", results.get(0).folderName());
        assertEquals(1, results.get(0).presentations().size());
        verify(jsonReader, times(1)).readPresentationsFromFolder(presentationFolder);
        verify(mapper, times(1)).mapToPresentationDto(mockPresentation);
    }

    @Test
    void testReadPresentations_IncorrecteFolder() {
        List<PresentationFolderRecord> result = folderReader.readPresentations("invalid/path/to/folder");
        assertTrue(result.isEmpty());
    }

    @Test
    void testReadPresentations_DefaultWaarde() {
        List<PresentationFolderRecord> result = folderReader.readPresentations(null);
        assertNotNull(result);
    }
}
