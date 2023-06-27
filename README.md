### FUNZIONAMENTO AVVIO SERVER:
Fare doppio click sul jar 'Server.jar' o in alternativa aprire la CLI e spostarsi nella directory con il file 'Server.jar' e digitare:  java -jar Server.jar<br>
### FUNZIONAMENTO AVVIO CLIENT:
Aprire la CLI e spostarsi nella directory con il file 'Client.jar' e digitare:  java -jar Client.jar arg[0] arg[1] arg[2]<br>
-arg[0] fa riferimento al tipo di interfaccia che si vuole utilizzare: digitare 'gui' per avviare l'interfaccia grafica o 'tui' per avviare l'interfaccia testuale<br>
-arg[1] fa riferimento al tipo di protocollo di comunicazione network che si vuole utilizzare: digitare 'socket' per utilizzare l'interfaccia socket o 'rmi' per utilizzare l'interfaccia 'rmi'<br>
-arg[2] è l'ip del server. Per trovare l'ip desiderato bisogna aprire la CLI e digitare 'ipconfig' (su windows) o 'sudo hostname -I' (su linux).

NOTA: nel caso si stia usando windows è necessario aprire la CLI come amministratore per utilizzare il comando ipconfig
