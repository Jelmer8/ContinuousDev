# Readme voor PresentationViewer geschreven in Markdown formaat

___

### Markdown

Dit is een eenvoudige opmaaktaal die veel gebruikt wordt voor documentatie, zoals README-bestanden op platforms zoals
GitHub. Het biedt een manier om tekst te formatteren met behulp van eenvoudige symbolen en tekens.

- zie ook https://www.markdownguide.org/basic-syntax/

___

# Build Instructies voor PresentationViewer

## Vooraf

1. **Java Development Kit (JDK)**: Ensure JDK 21 or higher is installed.
2. **Maven**: Install Apache Maven (version 3.8 or higher).
3. **Spring Boot**: The application uses Spring Boot, no additional setup is required.
4. **IDE**: IntelliJ IDEA is recommended for development.

___

## Steps to Build, Compile, Test, Start, and Package the Application

### 1. Build de Applicatie

onderstaand commando compileert de code, voert de tests uit en pakt de applicatie in een JAR-bestand.

```
mvn clean install
``` 

### 2. Compileer de Applicatie

Om de applicatie te compileren, gebruik je het volgende commando:
dit compileert de broncode van de applicatie naar .class files zonder de tests uit te voeren.

```
mvn compile
```

### 3. Draaien van de testen

om de unit testen uit te voeren, en de resultaten in een console gebruik je het volgende commando:

```
mvn test
```

### 4. Starten van de Applicatie

om de applicatie te starten, gebruik je het volgende commando:

```
mvn spring-boot:run
```

een andere optie is om de applicatie te starten vanuit de gecompileerde JAR:

```
java -jar target/presentationviewer-<version>.jar
```

of vanuit de IDE, zoals IntelliJ IDEA:

````
Run> Run PresentationViewerApplication 
of selecteer de PresentationViewerApplication class en klik op de groene run knop in de IDE.
  of control shift F10 (Windows/Linux) of command shift R (Mac) om de applicatie te starten.
````

### 5. Package van de Applicatie

om een deployable JAR-bestand te maken, gebruik je het volgende commando:

```
mvn package
```

de packaged .jar komt terecht in de target directory van het project.

je kan het versienummer van de applicatie vinden in de `pom.xml` file onder de `<version>` tag.
deze kan je aanpassen indien nodig.

### Acties binnen Intellij

in het maven menu:

- clean: presentationviewer > Lifecycle > clean
- compile : presentationviewer > Lifecycle > compile
- package: presentationviewer > Lifecycle > package
- install: presentationviewer > Lifecycle > install

### unit testen draaien binnen Intellij

Unit testen draaien in IntelliJ IDEA voor het project PresentationViewer

- 1: Open het project: Zorg ervoor dat het project PresentationViewer is geopend in IntelliJ IDEA.
- 2: Ga naar de testklasse:
    - Navigeer naar de map src/test/java in de Projectweergave.
    - Open de gewenste testklasse of testmethode die je wilt uitvoeren.

- 3: Test uitvoeren:
    - Klik met de rechtermuisknop op de testklasse of testmethode.
    - Selecteer *Run 'TestClassName'* of *Run 'testMethodName'*.

- 4: Alle testen uitvoeren:
    - Open het Maven-menu in IntelliJ IDEA.
    - Ga naar *presentationviewer > Lifecycle > test.*
    - Klik op **test** om alle unit testen in het project uit te voeren.

- 5: Resultaten bekijken:
    - De testresultaten worden weergegeven in het Run-venster onderaan IntelliJ IDEA.
    - Hier kun je zien welke testen geslaagd zijn, gefaald hebben, of overgeslagen zijn.

- 6: Sneltoets:
    - Gebruik **Ctrl+Shift+F10 (Windows)** om een testklasse of testmethode direct uit te voeren.

____

## Gebruikte Design Patterns in de code

- zie [Gebruik van design patterns binnen de Presentationviewer](designpatterns.md)

___

## Lombok Framework

- zie [Gebruik van Java Framework Lombok](lombok.md)

