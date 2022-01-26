# Beschreibung Patterns-and-Frameworks

Semesterprojekt zur Entwicklung eines auf Java/JavaFX basierenden
Computerspiels.

# Spielablauf

### Startseite und Highscore

Auf der Startseite befindet sich eine Highscore Tabelle mit allen Teams und den
jeweiligen Leveln und der erreichten Punktzahl. Über die Startseite kann sich
ein Spieler registrieren oder einloggen, sein Profil ändern, ein Team anlegen
oder einem Team beitreten oder ein Spiel starten.

Ist ein Spieler eingeloggt und ist er in einem aktiven Team registriert, so
werden im Top Panel der Startseite die aktuellen Teamdaten geladen.

### Registrierung und Login

Ist kein Spieler angemeldet, erscheint auf der Startseite oben rechts ein
Button "Login/Register". Mit Klick hierauf kommt der Spieler auf eine neue Seite
mit zwei alternativen Eingabeformularen.

Ist der Spieler bereits registriert, kann er sich hier links mit seiner
Emailadresse und seinem Passwort einloggen
(die Emailadresse ist einzigartig). Aus dem Backend werden die entsprechenden
Spielerdaten inklusive eventuell bereits bestehender Teams geladen und der
Spieler wird auf die Startseite zurückgeleitet.

Ist der Spieler noch nicht registriert, kann er das rechts mit seinem Namen,
seiner Emailadresse und einem Passwort tun. Der Spieler wird dann entsprechend
in der Datenbank angelegt, automatisch eingeloggt und ebenfalls auf die
Startseite zurückgeleitet.

Bei Falscheingaben werden Validierungsfehler angezeigt.

### Settings

Eingeloggte Spieler sehen auf der Startsteite rechts oben einen Button "
Settings", unter dem sie weitere Einstellungen vornehmen können.

#### Profile

Unter Profil kann der Name des Spielers geändert werden. Die Änderungen werden
in der Datenbank entsprechend synchronisiert. Das Ändern des Passworts ist für
später vorgesehen, wurde jedoch noch nicht umgesetzt.

Bei Falscheingaben werden Validierungsfehler angezeigt.

#### Team

Spieler müssen Teams beitreten, um spielen zu können. Ist ein Spieler noch in
keinem aktiven Team, dann findet er hier die Möglichkeit, ein Team zu erstellen
oder einem bereits bestehenden Team beizutreten. Ist der Spieler schon im
eingegebenen Team oder ist das Team bereits voll, dann werden entsprechende
Fehlermeldungen angezeigt.

Hat der Spieler erfolgreich ein Team erstellt, ist einem Team beigetreten oder
wurde nach dem Login bereits ein aktives Team für diesen Spieler gefunden, wird
hier das aktive Team mit den verbundenen Spielern angezeigt.

#### Logout

Spieler können sich auch wieder ausloggen. Nach dem Logout werden alle Spieler-
und Teamdaten zurückgesetzt und der Spieler muss sich erneut einloggen, um
wieder spielen zu können.

### Spiel

Das Spiel kann über die Startseite gestartet werden. Besitzt der Spieler kein
aktives Team, dann wird ein entsprechender Button mit der Aufforderung, ein Team
zu erstellen oder einem Team beizutreten, angezeigt. Ist der Spieler Teil eines
aktiven Teams, werden oben im Top Panel die Teamdaten und unter der Highscore
Tabelle der Startbutton angezeigt.

Nach dem Start wird der Spieler auf den Wartebereich geleitet - hier wird auf
den zweiten Spieler des Teams gewartet. Wenn sich beide Spieler im Wartebereich
befinden, wird der Start-Button aktiv geschaltet und sobald einer der Spieler
auf den diesen klickt, beginnt das Spiel. Verlässt einer der Spieler den
Wartebereich vorzeitig, dann ist der Start-Button wieder inaktiv.

Das Spiel startet bei einem neuen Team mit Level 1. Bei einem bestehenden Team
wird das letzte gespeicherte Level mit dem verknüpften Punkte- und Lebensstand
aus der Datenbank geladen.

Das Spiel wird gespielt, indem auf den Frisbee geklickt wird. Jeder Spieler kann
nur auf den Frisbee klicken, der bei seinem eigenen Character ist. Der Frisbee
fliegt dann mit einem ermittelten Zufallswert zum anderen Spieler, der diesen
fangen muss. Spieler können mit den Pfeiltasten den eigenen Character nach
rechts und links bewegt werden, sowie hüpfen. Die Spieler dürfen sich außerdem
nur bis auf einen festgelegten Mindestabstand nähern. Wird der Frisbee gefangen,
gibt es einen Punkt. Wird der Frisbee nicht gefangen, dann wird ein Leben (
Frisbee) abgezogen. Nach einem festgelegten Countdown ist ein Level beendet.

Sind alle Leben verloren, dann ist das Spiel beendet und es wird ein
entsprechender Dialog angezeigt. Die erreichten Punkte werden in der Datenbank
gespeichert und der Spieler kann zurück auf die Startsteite. Hier muss er ein
neues Team anlegen, um weiterzuspielen.

Wurde ein Level erfolgreich beendet, dann wird ein entsprechender Dialog
angezeigt. Mit Klick auf einen der Bestätigungsbuttons werden Level, Punktzahl
und Leben in der Datenbank gespeichert und der Spieler kann entweder direkt
weiterspielen oder zurück zur Startseite. Sobald ein Spieler auf den
Weiter-Button klickt, beginnt das nächste Level für beide Spieler.

Wurde das letzte Level (momentan Levle 3) beendet, so ist das Spiel gewonnen.
Dem Spieler wird ein entsprechender Dialog angezeigt, die erreichten Punkte und
Leben werden in der Datenbank gespeichert und der Spieler kann zurück auf die
Startseite navigieren. Möchte er weiterspielen, muss ein neues Team angelegt
werden und ein zweiter Spieler muss diesem Team beitreten.

Während dem Spiel kann unten rechts mit "Quit" jederzeit unterbrochen werden. In
dem angezeigten Dialog kann entweder weitergespielt werden - der Countdown und
das Spiel laufen dann weiter - oder zurück zur Startseite navigiert werden. Bei
Rückkehr zur Startseite werden keine Daten aus dem Spiel gespeichert, bei
erneutem Spielbeginn startet der Spieler also wieder beim zuletzt gespeicherten
Spielstand. Die Pausen werden für beide Spieler synchronisiert. Sobald also ein
Spieler auf Quit klickt, wird für beide Spieler der entsprechende Dialog
angezeigt. Klickt ein Spieler dann auf den Weiter-Button, wird das Spiel für
beide Spieler wieder fortgeführt.

Sollte ein Spieler das laufende Spiel verlassen, wird dem anderen Spieler ein
entsprechender Dialog angezeigt und das Spiel kann beim letzten gespeicherten
Level wieder aufgenommen werden.

# Development

## Client starten

Der Client kann über die Main-Class `StartApplication` gestartet werden.

### Intellij

Die `frontend.iml` kann man bauen lassen, indem man auf File -> Project
Structure geht und ein neues Modul importiert (auf das + klicken).
Hier `frontend` in der Ordnerstruktur auswählen und bei "Import module from
existing model" maven auswählen. Bei der Run Configuration kann dann `frontend`
als Modul gewählt werden, der Pfad zur Main Application
ist `puf.frisbee.frontend.StartApplication`.

### Umgebungsvariablen

Für die Kommunikation mit dem Backend muss `.env.example` in ein neues
File `.env` kopiert werden. Je nachdem, ob mit dem lokalen oder deploytem
Backend kommuniziert werden soll, müssen die Environment Variablen angepasst
werden.

## Server und Datenbank starten

Für den Server gibt es ein eigenes Repository mit einer eigenen README:
https://github.com/D1ANAmic/Patterns-and-Frameworks-Backend

Die Spieldaten werden in einer PostgreSQL Datenbank gespeichert.

Die Anwendung funktioniert nur mit laufendem Backend. Soll die Anwendung mit dem
produktiv geschalteten Backend kommunizieren, muss ein Tunnel aufgebaut werden.

# Architekturentscheidungen

Wir benutzen das MVVM-Pattern ohne Framework. Die Instancen der verschiedenen
Models, Views und ViewModels werden über Factories verwaltet.

Die Umsetzung des Spiels als Multi-Player-Game erfolgt mithilfe von Sockets. Die
Bewegungsanweisungen der Spieler, sowie die Parameter der Flugbahn des Frisbees
werden durch Requests an das Backend gesendet und dort (nachdem zwei Spieler
durch ihren Teamnamen gematched wurden) weitergeleitet. Die Berechnung der
Bewegungen und der Flugbahn geschieht dann im jeweiligen Client. So wird die
Datenübertragung minimal gehalten.

## Frameworks und libraries

Für das Frontend wurde Folgendes verwendet:

* Maven
* JavaFX
* Jackson zum Parsen der Requests und Responses der Netzwerkverbindung
* Dotenv für die Umgebungsvariablen
