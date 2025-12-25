## Erzeugungsmuster

### Singleton

- **Zweck:** Stellt sicher, dass eine Klasse nur eine einzige Instanz besitzt und global konsistent verfügbar bleibt
- **Beispiel:** `Logger.getInstance()` statt `new Logger()`

### Factory Method

- **Zweck:** Ermöglicht Unterklassen, spezifische Objektarten flexibel zu erzeugen, ohne dass der Aufrufer konkrete Klassen kennen muss
- **Beispiel:** `ParserFactory` erzeugt `JsonParser` oder `XmlParser`.

### Abstract Factory

- **Zweck:** Liefert konsistente Familien verwandter Objekte, ohne dass der Client konkrete Implementierungen kennen oder auswählen muss
- **Beispiel:** `uiFactory.createButton()`

### Builder

- **Zweck:** Trennt komplexe Objektkonstruktion von ihrer Darstellung für einen flexiblen, schrittweisen Aufbau
- **Beispiel:** `new ConfigBuilder().stepA().stepB().build()`

### Prototype

- **Zweck:** Erstellt neue Objekte durch Kopieren existierender Instanzen, wodurch teure Erzeugungsprozesse reduziert werden können
- **Beispiel:** `object.clone()`
