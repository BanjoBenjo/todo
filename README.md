# todo
Projektarbeit von Benjamin Wagner und Etienne Foubert.
## Beschreibung
Der Hauptbildschirm der App zeigt eine Liste, in der heutige offene Aufgaben angezeigt werden. Der Nutzer kann in einem Dialog Aufgaben anlegen, die über verschiedene Attribute verfügen wie Name, Fälligkeitszeitpunkt, Notizen und Kategorie. Der Nutzer kann erledigte Aufgaben nach links ziehen, um sie als abgeschlossen zu kennzeichnen oder nach rechts, um sie auf den nächsten Tag zu verschieben. Falls der Nutzer zu viele Aufgaben aufschiebt erhält er eine visuelle Warnung.
Ein zweiter Bildschirm zeigt die komplette Liste aller Aufgaben.
## Entwurfsmuster
Durch die Implementierung des Kommando-Entwurfsmodells sollen die Aktionen die auf dem Hauptbildschirm ausgeführt, in Objekten gespeichert werden. So können diese rückgängig gemacht und wiederhergestellt werden. Durch die Speicherung der Aktionen können diese anschließend in einer Timeline visualisiert werden.
Weiterhin werden wir für das GUI versuchen ein Entwurfsmuster anzuwenden, um das Erledigen von Aufgaben für den Nutzer befriedigend zu gestalten.
Durch die unkomplizierten Hauptfunktionen sollte die App leicht erweiterbar sein, um weitere funktionelle Entwurfsmuster einzufügen, beispielsweise über Einkaufslisten oder einen Fitnessplan.
## Weitere Informationen
Die Entwicklung der App erfolgt in Android Studio in Java. Das UML-Diagramm wurde mithilfe von _Draw.io_ erstellt.

