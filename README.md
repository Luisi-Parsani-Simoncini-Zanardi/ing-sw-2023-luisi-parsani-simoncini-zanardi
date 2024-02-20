### FUNZIONALITÀ:
Sono state implementate le regole complete, sia TUI che GUI, e sia il protocollo RMI sia il protocollo socket. Le funzionalità aggiuntive implementate sono chat, resilienza alle disconnessioni e persistenza. Abbiamo inoltre tutta la documentazione richiesta dalle specifiche (UML fatti da noi e autogenerati, file html di javadoc e copertura dei test, peer reviw, documentazione sul funzionamento della comunicazione Client-Server)
### DOVE TROVARE I JAR:
I jar possono essere scaricati ed utilizzati tramite il link presente dul file jar.txt in deliverables
### FUNZIONAMENTO AVVIO SERVER:
Aprire la CLI e spostarsi nella directory 'out/artifacts/server_jar' e digitare:  java -jar softeng-gc44.jar<br>
### FUNZIONAMENTO AVVIO CLIENT:
Aprire la CLI e spostarsi nella directory 'out/artifacts/server_jar' e digitare:  java -jar softeng-gc44.jar arg[0] arg[1] arg[2]<br>
-arg[0] fa riferimento al tipo di interfaccia che si vuole utilizzare: digitare 'gui' per avviare l'interfaccia grafica o 'tui' per avviare l'interfaccia testuale<br>
-arg[1] fa riferimento al tipo di protocollo di comunicazione network che si vuole utilizzare: digitare 'socket' per utilizzare l'interfaccia socket o 'rmi' per utilizzare l'interfaccia 'rmi'<br>
-arg[2] è l'ip del server. Per trovare l'ip desiderato bisogna aprire la CLI e digitare 'ipconfig' (su windows) o 'sudo hostname -I' (su linux) sul dispositivo server.
