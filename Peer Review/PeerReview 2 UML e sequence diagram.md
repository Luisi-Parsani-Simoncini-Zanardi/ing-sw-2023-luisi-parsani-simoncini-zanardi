# Peer-Review 2: UML e sequence diagram

## Lorenzo Luisi, Luca Parsani, Davide Simoncini, Lorenzo Zanardi
## Gruppo 44

Valutazione del diagramma UML e del sequence diagram della network del gruppo 34.

# Lati positivi

Inserire il nickname prima di effettuare la register consente di gestire in modo piu' dinamico la creazione della lobby, senza andare incontro a scenari di concorrenza tra i client che vogliono registrarsi come primo giocatore. <br> 
Gli eventi sembrano ben suddivisi e organizzati, in modo da poter coprire tutti gli scenari possibili. <br>
Il sistema di lobby management sembra ben organizzato e interessante. Non avendo noi implementato le partite multiple non abbiamo un metro di paragone, ma crediamo che sia una strada potenzialmente corretta

# Lati negativi

A parer nostro converrebbe trovare un modo per inviare la notify solo al Listener corretto, o perlomeno a una classe intermedia di dispatch, invece di trasmetterlo a tutti e di scartarlo a posteriori perche' quest'ultima soluzione richiede l'invocazione di molte notify (visto anche il notevole numero di eventi) che poi vengono scartate, andando ad influire sulle performance in caso di eventuali volumi elevati. 

# Confronto tra le architetture

La differenza principale tra quest’implementazione e la nostra e' la gestione degli eventi, che in qui vengono ascoltati da un Listener, mentre invece nella nostra implementazione sono osservati da un Observer. Di conseguenza, in questa implementazione vengono inviati piu' messaggi di piccola dimensione, in contrasto con i nostri singoli messaggi di dimensione maggiore.<br>
La gestione del flusso di gioco e' molto simile concettualmente alla nostra.<br>
La chat asincrona e' un idea ben strutturata, a cui anche noi abbiamo pensato, essendo svincolata per definizione dal flusso di gioco.
