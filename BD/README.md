# Readme BD
[Link alla documentazione](https://github.com/taekwondodev/OOBD2324project-unina/blob/main/BD/Documentazione_BasiDiDati-OOBD2324.pdf)


## Installing
Eseguire il comando SQL per creare un Database.

```sql
CREATE DATABASE postgres WITH OWNER postgres;
```

Importare il dump del Database.
[Link al dump](https://github.com/taekwondodev/OOBD2324project-unina/blob/main/BD/db_dump.sql)

Issue known: error at line 360 syntax error. Se incontri questo errore ignora il file Dump e procedi manualmente importando ed eseguendo i file delle Tables, Trigger, Procedure e PopulateDB.

Importare le Tables del Database.
[Link alle Tables](https://github.com/taekwondodev/OOBD2324project-unina/blob/main/BD/TABLES.sql)

Importare i Trigger del Database.
[Link ai Trigger](https://github.com/taekwondodev/OOBD2324project-unina/blob/main/BD/TRIGGER.sql)

Importare le Procedure del Database.
[Link alle Procedure](https://github.com/taekwondodev/OOBD2324project-unina/blob/main/BD/PROCEDURE.sql)

Importare le Insert del Database.
[Link alle insert](https://github.com/taekwondodev/OOBD2324project-unina/blob/main/BD/PopulateDB.sql)
