# todo
Bachelorprojekt von Benjamin Wagner und Etienne Foubert.
## Beschreibung
Der Hauptbildschirm der App zeigt eine Liste, in der offene Aufgaben angezeigt werden. Der Nutzer kann in einem Dialog Aufgaben anlegen, die über verschiedene Attribute verfügen wie Name, Fälligkeitszeitpunkt, Notizen und Kategorie. Es gibt drei Arten von Aufgaben (`Tasks`), eine einfache Aufgabe (`BasicTask`), eine Aufgabe mit Deadline und optionaler Erinnerung vor der Deadline (`ScheduledTask`), eine Einkaufsliste (`ShoppingList`), die mehrere `ShoppingItems` verfügt, sowie einen `LocationTask`, der eine Erinnerung auslöst, wenn man am hinterlegten Ort angekommen ist. Der Nutzer kann erledigte Aufgaben nach links ziehen, um sie als abgeschlossen zu kennzeichnen oder nach rechts, um sie zu löschen. Abgeschlossene Aufgaben werden ebenfalls in einer Liste gespeichert, damit ein Verlauf entsteht.
## Kategorien
Die Kategorien können durch den Nutzer angelegt werden. Sie besitzen einen Namen sowie eine Farbe, die im Hauptbildschirm für eine bessere Übersicht sorgt.
## Entwurfsmuster
Durch die Implementierung des Kommando-Entwurfsmodells sollen die Aktionen die auf dem Hauptbildschirm ausgeführt, in Objekten gespeichert werden. So können diese rückgängig gemacht und erneut ausgeführt werden (_undo_ und _redo_). Durch die Speicherung der Aktionen können diese anschließend in einer Timeline visualisiert werden.
Das Strategy-Muster wird eingesetzt, um Redundanz im Code der verschiedenen Task-Klassen zu vermeiden. Genauer wird mithilfe des Strategy-Muster zwischen verschiedenen Benachrichtigungstypen gewechselt.
Außerdem werden die verschiedenen Tasks nach dem Template-Muster von der Oberklasse Task erben.
Durch die unkomplizierten Hauptfunktionen sollte die App leicht erweiterbar sein, um weitere funktionelle Entwurfsmuster einzufügen, beispielsweise über Einkaufslisten oder einen Fitnessplan.
## Weitere Informationen
Die Entwicklung der App erfolgt in Android Studio in Java. Das UML-Diagramm wurde mithilfe von [Draw.io](https://draw.io) erstellt.

## Colors
Basic colors: `595959`,`7f7f7f`,`a5a5a5`,`cccccc`,`f2f2f2`  
Category colors: `7400b8`,`6930c3`,`5e60ce`,`5390d9`,`4ea8de`,`48bfe3`,`56cfe1`,`64dfdf`,`72efdd`,`80ffdb`
