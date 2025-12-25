## SOLID-Prinzipien

### Single Responsibility Principle (SRP)

- **Zweck:** Stellt sicher, dass jede Einheit nur eine klar abgegrenzte Verantwortung besitzt und dadurch wartbarer bleibt
- **Beispiel:** Logger-Implementation getrennt von Geschäftslogik

### Open/Closed Principle (OCP)

- **Zweck:** Erlaubt Erweiterungen durch neue Komponenten, ohne bestehenden Code verändern zu müssen, wodurch Stabilität steigt
- **Beispiel:** Neue Implementierung eines Interfaces statt Erweiterung eines switch-case-Blocks

### Liskov Substitution Principle (LSP)

- **Zweck:** Gewährleistet, dass Unterklassen vollständig kompatibel zur Basisklasse sind und ohne Probleme substituiert werden können
- **Beispiel:** Rechteck statt Quadrat vermeiden

### Interface Segregation Principle (ISP)

- **Zweck:** Fördert kleine, fokussierte Schnittstellen, damit Implementierungen nur wirklich benötigte Fähigkeiten erfüllen müssen
- **Beispiel:** `Readable` und `Writable` getrennt

### Dependency Inversion Principle (DIP)

- **Zweck:** Erreicht lose Kopplung, indem Module von Abstraktionen statt konkreten Implementierungen abhängen
- **Beispiel:** Konstruktor erhält Interface statt Klasse
