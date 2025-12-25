## Strukturmuster

### Bridge

- **Zweck:** Entkoppelt Abstraktionen von ihren Implementierungen, sodass beide Seiten unabhängig erweiterbar und austauschbar bleiben
- **Beispiel:** Remote kombiniert sich dynamisch mit Device

### Adapter

- **Zweck:** Übersetzt inkompatible Schnittstellen, damit bestehende Komponenten ohne interne Änderungen weiterverwendet werden können
- **Beispiel:** `new LegacyAdapter(service)`

### Decorator

- **Zweck:** Erweitert Objekte dynamisch um zusätzliche Funktionalität, ohne ihre ursprüngliche Klasse zu verändern oder zu vererben
- **Beispiel:** `LoggingDecorator` ergänzt bestehende Services

### Composite

- **Zweck:** Vereinheitlicht den Umgang mit Einzelobjekten und hierarchisch zusammengesetzten Strukturen, sodass beide identisch behandelt werden können
- **Beispiel:** Menü enthält Elemente und Untermenüs

### Flyweight

- **Zweck:** Reduziert Speicherverbrauch großer Objektmengen, indem unveränderliche, gemeinsam nutzbare Zustände zentral gespeichert werden
- **Beispiel:** Zeichenobjekte teilen Glyphendaten

### Proxy

- **Zweck:** Kontrolliert und erweitert den Zugriff auf ein Objekt, etwa durch Lazy Loading, Schutzmechanismen oder Caching
- **Beispiel:** `new SecureProxy(resource)`

### Facade

- **Zweck:** Bietet eine vereinfachte Schnittstelle zu komplexen Subsystemen, um deren Nutzung übersichtlicher und klarer zu gestalten
- **Beispiel:** `PdfFacade.export(document)`
