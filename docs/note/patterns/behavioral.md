## Verhaltensmuster

### Strategy

- **Zweck:** Kapselt austauschbare Algorithmen, damit das konkrete Vorgehen flexibel zur Laufzeit gewechselt werden kann
- **Beispiel:** `sorter.setStrategy(new QuickSort())`

### Template Methode

- **Zweck:** Legt die Grundstruktur eines Algorithmus fest und erlaubt Unterklassen, einzelne Schritte kontrolliert anzupassen
- **Beispiel:** `AbstractProcessor.process()`

### Chain of Responsibility

- **Zweck:** Leitet Anfragen durch eine Handlerkette, sodass jeder Bearbeiter entscheiden kann, ob er zuständig ist oder weiterleitet
- **Beispiel:** HandlerA -> HandlerB -> HandlerC

### State

- **Zweck:** Organisiert veränderliches Verhalten eines Objekts anhand austauschbarer Zustandsobjekte, die den aktuellen Modus bestimmen
- **Beispiel:** `player.setState(new PlayingState())`

### Memento

- **Zweck:** Speichert und stellt Objektzustände wieder her, ohne interne Details offenzulegen oder direkte Kopplungen zu erzeugen
- **Beispiel:** `history.push(editor.save())`

### Command

- **Zweck:** Verpackt Operationen als eigenständige Objekte, wodurch Ausführung, Rücknahme oder Planung flexibel gesteuert werden können
- **Beispiel:** `new SaveCommand(document)`

### Iterator

- **Zweck:** Erlaubt sequentielles Durchlaufen einer Sammlung, ohne deren interne Struktur offenzulegen oder Kopplungen zu erzeugen
- **Beispiel:** `for (var iterator = list.iterator(); ...)`

### Visitor

- **Zweck:** Trennt Operationen von Objektstrukturen, sodass neue Funktionen hinzugefügt werden können, ohne Klassen zu verändern
- **Beispiel:** `tree.accept(new PrintVisitor())`

### Interpreter

- **Zweck:** Modelliert Grammatikregeln und Auswertung kleiner Sprachen, um Ausdrücke kontrolliert und flexibel interpretieren zu können
- **Beispiel:** `expression.interpret(context)`

### Mediator

- **Zweck:** Koordiniert Kommunikation zwischen Objekten zentral, um direkte Abhängigkeiten zu reduzieren und Interaktionen zu ordnen
- **Beispiel:** `dialogMediator.notify(sender)`

### Observer

- **Zweck:** Benachrichtigt abhängige Objekte automatisch über Zustandsänderungen, um reaktive Aktualisierung zu ermöglichen
- **Beispiel:** `subject.addObserver(observer)`
