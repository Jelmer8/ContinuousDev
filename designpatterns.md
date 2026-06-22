# Overzicht van de gebruikte design patterns voor de presentatie viewer applicatie

## Dependency Injection Pattern:

Dependency Injection (DI) is een ontwerppatroon waarbij objecten hun afhankelijkheden van buitenaf ontvangen, in plaats
van ze zelf te creëren.
Dit zorgt voor losgekoppelde code, waardoor klassen flexibeler en gemakkelijker te testen zijn,
omdat afhankelijkheden kunnen worden vervangen door mock-objecten tijdens het testen.

voorbeeld waarbij de `JSONPresentationFolderReader` en `XMLPresentationFolderReader` worden geïnjecteerd in de
`PresentationFolderReader` klasse:
de `JSONPresentationFolderReader` en de `XMLPresentationFolderReader` zijn Components die door Spring worden
aangemaakt (als singleton, maar 1 instantie binnen de applicatie).

implementatie voorbeeld:

```
public class PresentationFolderReader {        
    private JSONPresentationFolderReader jsonPresentationFolderReader;
    private XMLPresentationFolderReader xmlPresentationFolderReader;
```

## Model-View-Controller (MVC) Pattern:

Het MVC-patroon scheidt de presentatie, logica en gegevens van de applicatie.

- **Model**:  De `PresentationModel` beheert de presentaties, houdt de status van de geselecteerde presentatiefolder en
  de geselecteerde slide bij.
- **View**: De gebruikersinterface van de applicatie, die de gegevens toont aan de gebruiker. In dit project wordt dit
  voornamelijk gerepresenteerd door de `PresentationViewer`, die de presentaties toont.
- **Controller**: De laag die de interactie tussen het model en de view beheert. In dit project worden knoppen gebruikt
  om acties uit te voeren, zoals "Next" en "Previous", die commando's naar het model sturen.

## Observer Pattern

Doel: View automatisch updaten bij wijzigingen in het model.

implementatie:

* `PresentationModel` extends Observable, en kan hierdoor de *setChanged()* en de *notifyObservers()* aanroepen als het
  model wijzigt na klikken buttons voor next, previous, selectslide en selectfolder
* `PresentationView` implementeert Observer en registreert registreert het model als instance variabele,
* overschrijft de *update()* methode om de view te updaten bij veranderingen in het model.

```
 @Override
 public void update(Observable o, Object arg) {
  updateSlidePanel();
 }
```

## Command Pattern

Doel: Gebruikersacties (knoppen) loskoppelen van de uitvoering van logica.

Implementatie: Elke buttonactie (`NextSlideCommand`, `PreviousSlideCommand`, `SelectSlideCommand`) is een eigen
Command-klasse met een execute()-methode,
dit komt doordat ze de interface `Command` implementeren.

voorbeeld van de interface:

```
public interface Command {
    void execute();
} 
```

voorbeeld van een concrete command klasse, met daarin de implementatie van de execute() methode:

```
public class NextSlideCommand  implements Command {
    private PresentationModel model;

    public NextSlideCommand(PresentationModel model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.nextSlide();
    }
}
```

## Singleton Pattern :

Wordt gebruikt om ervoor te zorgen dat er slechts één instantie van een klasse is en dat deze instantie toegankelijk is
vanuit de hele applicatie.
een `@Component annotatie class` in Spring is standaard een singleton. Dit betekent dat wanneer je een bean definieert
met @Component (of een andere stereotype-annotatie zoals @Service of @Repository),
Spring ervoor zorgt dat er slechts één instantie van die bean wordt gemaakt en beheerd in de applicatiecontext

voorbeeld van een component class:

````
@Component
public class JSONPresentationMapper {}
````

## Inheritance Pattern : subclass of superclass voorbeeld

Inheritance is een een concept uit de object-georiënteerde programmering
waarbij een nieuwe klasse (de subklasse of afgeleide klasse) eigenschappen en methoden van een
bestaande klasse (de superklasse of basisklasse) overneemt, via het extends keyword

implementatie:
`BaseDto` die gebruikt wordt als basis voor de `TextBaseDto` klasse,
waarbij de getters en setters van de `BaseDto` klasse worden overgenomen in de `TextBaseDto` klasse.
een `ImageDto` is ook een subclass van de `BaseDto` klasse, en heeft ook de getters en setters van die klasse.

```
@Data
public class BaseDto {
    private String value;
    private int identation;
}

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextBaseDto extends BaseDto {
    private String font;
}

```

## Composition Pattern: has-a (sterk) relatie (waarbij het ene object niet kan bestaan zonder het andere)

Compositie impliceert een sterke, afhankelijke relatie, waarbij het ene object (het geheel) niet kan bestaan zonder het
andere (het deel).
wordt bereikt door instance variabelen te gebruiken in een klasse die verwijzen naar andere objecten.

zie als voorbeeld de `SlideDto` class, die een lijst van `TextDto` en `ImageDto` objecten bevat naast de `TitleDto`,
en een `ImageDto` kan niet bestaan zonder een `SlideDto` object.
een `SlideDto` kan niet bestaan zonder een `TitleDto`, en kan ook niet bestaan zonder een lijst van `TextDto` en
`ImageDto` objecten.

```
@Data
@Builder
public class SlideDto {
    private TitleDto title;
    private List<TextDto> texts;
    private List<ImageDto> images;
}
```

## Aggregation Pattern: has-a (zwak) relatie (waarbij de objecten onafhankelijk van elkaar kunnen) bestaan

Aggregatie impliceert een zwakkere relatie tussen objecten, waarbij het ene object kan bestaan zonder het andere.

Een voorbeeld hiervan is de relatie tussen de `PresentationModel` en de `SlideDto`. De `PresentationModel` kan een lijst
van `SlideDto` objecten bevatten,
maar de `SlideDto` objecten kunnen onafhankelijk van de `PresentationModel` bestaan.

```
public class PresentationModel {
    private final List<PresentationFolderRecord> folderRecords;
}

public class SlideDto {
    private TitleDto title;
    private List<TextDto> texts;
    private List<ImageDto> images;
}
```

## Interface:

Wordt gebruikt om complexe objecten te bouwen door kleinere, eenvoudigere objecten samen te voegen.
Zie het gebruik van de interface `Presentation`, deze bevat een methode *getShowTitle()* onafhankelijk van gebruikte
type implementatie, json of xml
`JSONPresentation` en `XMLPresentation` implementeren beide deze interface,

```
public interface Presentation {
    String getShowTitle();
}

public class JSONPresentation implements Presentation {
    public String getShowTitle() {
        return "json " + showTitle;
    }
    
 public class XMLPresentation implements Presentation {
    public String getShowTitle() {
        return "xml "+ showTitle;
    }   
```

## Builder Pattern:

Het Lombok @Builder patroon is een annotatie die automatisch een Builder-klasse genereert voor een Java-object.
Dit patroon wordt gebruikt om complexe objecten op een eenvoudige en leesbare manier te creëren,
zonder dat je handmatig een Builder-klasse hoeft te schrijven.

voorbeeld van een Builder patroon met Lombok:

```
@Data
@Builder
public class SlideDto {
    private TitleDto title;
    private List<TextDto> texts;
    private List<ImageDto> images;
}

SlideDto slide = SlideDto.builder()
                .title("Introductie")
                .texts(List.of("Welkom", "Agenda"))
                .images(List.of("image1.png", "image2.png"))
                .build();
```

## Factory Pattern:

Wordt gebruikt om objecten te maken zonder dat de clientcode hoeft te weten hoe deze objecten worden gemaakt.

voorbeeld van een factory pattern in dit project:

* zie `PresentatieDtoMapper`, met de methode  *PresentationDto mapToPresentationDto(Presentatie presentation)* gebruikt
  om een `PresentationDto` aan te maken
  onder water wordt de structuur verborgen van wat voor PresentatieObject we willen mappen, json of xml, maar dat kun je
  later evt uitbreiden naar een ander formaat .csv of zo

## (Data) Mapper Pattern:

Het Mapper Pattern is een structureel design pattern dat wordt gebruikt om gegevens te transformeren van het ene
objectmodel naar een ander.
de `JSONPresentationMapper` en `XMLPresentationMapper` zijn voorbeelden van het (Data) Mapper Pattern.









