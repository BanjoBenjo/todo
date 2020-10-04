# todo
Bachelorprojekt von Benjamin Wagner und Etienne Foubert.
## Beschreibung
Der Hauptbildschirm der App zeigt eine Liste, in der offene Aufgaben angezeigt werden. Der Nutzer kann in einem Dialog Aufgaben anlegen, die über verschiedene Attribute verfügen wie Name, Fälligkeitszeitpunkt, Notizen und Kategorie. Es gibt drei Arten von Aufgaben (`Tasks`), eine einfache Aufgabe (`BasicTask`), eine Aufgabe mit Deadline und optionaler Erinnerung vor der Deadline (`ScheduledTask`), eine Einkaufsliste (`ShoppingList`), die mehrere `ShoppingItems` verfügt, sowie einen `LocationTask`, der eine Erinnerung auslöst, wenn man am hinterlegten Ort angekommen ist. Der Nutzer kann erledigte Aufgaben nach links ziehen, um sie als abgeschlossen zu kennzeichnen oder nach rechts, um sie zu löschen.
## Entwurfsmuster
Durch die Implementierung des Kommando-Entwurfsmodells sollen die Aktionen die auf dem Hauptbildschirm ausgeführt, in Objekten gespeichert werden. So können diese rückgängig gemacht und wiederhergestellt werden. Durch die Speicherung der Aktionen können diese anschließend in einer Timeline visualisiert werden.
Das Strategy-Muster wird eingesetzt, um Redundanz im Code der verschiedenen Task-Klassen zu vermeiden.
Weiterhin werden wir für das GUI versuchen ein Entwurfsmuster anzuwenden, um das Erledigen von Aufgaben für den Nutzer befriedigend zu gestalten.
Durch die unkomplizierten Hauptfunktionen sollte die App leicht erweiterbar sein, um weitere funktionelle Entwurfsmuster einzufügen, beispielsweise über Einkaufslisten oder einen Fitnessplan.
## Weitere Informationen
Die Entwicklung der App erfolgt in Android Studio in Java. Das UML-Diagramm wurde mithilfe von _Draw.io_ erstellt.

