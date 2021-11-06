# Beschreibung Patterns-and-Frameworks
Semesterprojekt zur Entwicklung eines auf Java/JavaFX basierenden Computerspiels.

# Development
## Client starten
Der Client kann über die Main-Class `StartApplication` gestartet werden.

### Intellij
Die `frontend.iml` kann man bauen lassen, indem man auf File -> Project Structure geht und 
ein neues Modul importiert (auf das + klicken). Hier `frontend` in der Ordnerstruktur auswählen
und bei "Import module from existing model" maven auswählen.  
Bei der Run Configuration kann dann `frontend` als Modul gewählt werden, der Pfad zur Main Application
ist `puf.frisbee.frontend.StartApplication`.

## Server starten
TBD

## Datenbank starten
TBD

## Tests starten
TBD

# Architekturentscheidungen
Wir benutzen das MVVM-Pattern ohne Framework.

## Datenbank
PostgreSQL: https://www.postgresql.org

## Frameworks und libraries
* Hibernate ORM: https://hibernate.org/orm/
* Material Design Library: http://www.jfoenix.com/
