### INFORMAZION SULLA CONSEGNA:
Abbiamo riscontrato delle gravi problematiche nella creazione del file .jar, sottovalutando l'operazione e credendo che questa venisse svolta da intellij senza intoppi. Abbiamo invece scoperto che i nostri due arefatti (client e server) avevano entrambi problemi con le risorse e non riuscivano, uno, a caricare correttamente le immagini e i file json dalle risorse, l'altro, a salvare nelle risorse i progressi della partita (e implementare quindi la disconnessione del server).
Nella versione caricata siamo purtroppo riusciti ad arginare parzialmente questi problemi, e gli eseguibili appaiono parzialmente funzionanti (riusciamo a recuperare le immagini ma non a salvare il file, non implementando quindi una funzionalità aggiuntiva).

Dal punto di vista del codice sorgente però, se questo viene eseguito dall'ambiente di sviluppo, quindi IntelliJ, esso implementa perfettamente tutte le sue funzionalità, che sono quelle elencate successivamente:
	Connessione Client-Server tramite RMI
	Connessione Client-Sever tramite utilizzo di Socket
	Chat
	Persisteza lato server
	Riconnessione dei client
	Gui (che potrebbe essere decisamente migliorata, sia nel modo in cui esegue i refresh, sia completata con l'utilizzo di più immagini).

Purtroppo in seguito agli ultimi e parzialmente vani tentativi di far funzionare i .jar abbiamo modificato il sorgente rendendo i JSon di configurazione delle classi contenenti delle stringhe e abbimamo modificato il modo per accedere alle risorse (il caricamento delle immagini appare più lento).
### FUNZIONALITÀ:
Sono state implementate le regole complete, sia TUI che GUI, e sia il protocollo RMI sia il protocollo socket. Le funzionalità aggiuntive implementate sono chat, resilienza alle disconnessioni e persistenza. Abbiamo inoltre tutta la documentazione richiesta dalle specifiche (UML fatti da noi e autogenerati, file html di javadoc e copertura dei test, peer reviw, documentazione sul funzionamento della comunicazione Client-Server)
### DOVE TROVARE I JAR:
A causa della dimensione dei jar (oltre 25MB) gitub non ci ha permesso di caricarli direttamente nella repository, quindi questi possono essere scaricati ed utilizzati tramite il link presente dul file jar.txt in deliverables
### FUNZIONAMENTO AVVIO SERVER:
Aprire la CLI e spostarsi nella directory 'out/artifacts/server_jar' e digitare:  java -jar softeng-gc44.jar<br>
### FUNZIONAMENTO AVVIO CLIENT:
Aprire la CLI e spostarsi nella directory 'out/artifacts/server_jar' e digitare:  java -jar softeng-gc44.jar arg[0] arg[1] arg[2]<br>
-arg[0] fa riferimento al tipo di interfaccia che si vuole utilizzare: digitare 'gui' per avviare l'interfaccia grafica o 'tui' per avviare l'interfaccia testuale<br>
-arg[1] fa riferimento al tipo di protocollo di comunicazione network che si vuole utilizzare: digitare 'socket' per utilizzare l'interfaccia socket o 'rmi' per utilizzare l'interfaccia 'rmi'<br>
-arg[2] è l'ip del server. Per trovare l'ip desiderato bisogna aprire la CLI e digitare 'ipconfig' (su windows) o 'sudo hostname -I' (su linux) sul dispositivo server.
