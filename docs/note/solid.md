## SOLID-Prinzipien

### Single Responsibility Principle (SRP)

- **Zweck:** Stellt sicher, dass jede Klasse nur eine klar abgegrenzte Verantwortung besitzt
- **Anwendung:**
  - `Action` ist nur für Repräsentation vom Spielzug und Prüfung, wann dieser erfolgreich ist, verantwortlich
  - `Action` ist nicht für Serialisierung (-> `Serializer<Action>`) und Übergabe (-> `ActionSender`) zuständig

### Open/Closed Principle (OCP)

- **Zweck:** Erlaubt Erweiterungen durch neue Komponenten (open), ohne bestehenden Code verändern zu müssen (closed)
- **Anwendung:**
  - keine Gott-Klasse, die alle `Actions` serialisiert und bei neuer `Action` erweitert werden muss, sondern:
  - neue `Action` => neuer `Serializer<Action>` (Registrierung in `SerializerRegistry<Action>`)

### Liskov Substitution Principle (LSP)

- **Zweck:** Gewährleistet, dass Unterklassen vollständig kompatibel zur Basisklasse sind (substituiert werden können)
- **Anwendung:**
  - `FormCellStatus` erbt von `VisitableCellStatus`, ist also auch begehbar und hat Gegner-Infos (bspw. `hasEnemy`)
  - `WallCellStatus` erbt nur von `CellStatus`, hat somit keinerlei Informationen zu Gegnern

### Interface Segregation Principle (ISP)

- **Zweck:** Fördert kleine, spezifische Schnittstellen, sodass Klassen nur das implementieren, was sie wirklich benötigen
- **Anwendung:**
  - Parsen und Serialisieren ist in verschiedene Interfaces getrennt (-> `Parser` und `Serializer`)
  - `GameInputReader` benötigt nur `Parser<ActionResult>`, `GameLogger` nur `Serializer<ActionResult>`

### Dependency Inversion Principle (DIP)

- **Zweck:** Erreicht lose Kopplung, indem Klassen von Abstraktionen statt konkreten Implementierungen abhängen
- **Anwendung:**
  - Game benutzt `ActionSender`-Interface statt konkrete Implementation (-> `ConsoleActionSender`)
  - konkrete Implementation wird von Außen in den `Game`-Konstruktor injected
