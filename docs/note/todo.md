### docs

- Präsentation:
  - Wo setzen wir Entwurfsmuster ein ([hier](patterns) notieren)?
  - Wie wollen wir das Projekt vorstellen? Datei vorbereiten?
- UML:
  - Dateien hierarchisch statt flach anordnen?
  - Pfeile in Klassendiagrammen richtig?

### src

- `GameBot`:
  - komplettes Projekt anschauen / reviewen
  - `BotPlayerTest` überfliegen
- Algorithmus:
  - Symmetrie vom Labyrinth beachten?
  - 2-Zellen-Abstand, um mehr neue Zellen zu entdecken?
- Strategies:
  - `SearchStrategy` funktionstüchtig? einbauen? aktuell in `NavigationStrategy`
  - Chain of Responsibilites für Strategies einbauen?
    - Strategies kennen ihren Nachfolger
    - rufen diesen auf, wenn selbst keine `Action` möglich ist
- Testing:
  - `GameTest`:
    - `Player` nicht als Mock, evtl. als Spy?
    - Aktualisierungen testen?
  - weitere JUnit-Tests erstellen
- Sonstiges:
  - Sichtbarkeit von Klassen
