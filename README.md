> ing-sw-2023-luisi-parsani-simoncini-zanardi

### COSE SU CUI RAGIONARE:
- Più client possono essere sulla stessa macchina<br>
- Bisogna poter selezionare tra TUI e GUI<br>
- Bisogna poter selezionare tra socket e RMI<br>

- - -

### NOTA PER QUANDO CREATE NUOVE CLASSI O AGGIUNGETE NUOVI METODI *(da Davide)*
In generale quando create o modificate le classi cercate di mantenere questo pattern:<br>
- Attributi<br>
- Costruttori<br>
- Getters<br>
- Setters<br>
- Metodi vari<br>






//turno
//chiama selectTiles/deselectTiles ad ogni click su board
//confermo la mia scelta, chiamo comfirmSelectedtiles
//chiama select column
//chiama placeTiles
//chiama endTurn
//            //chiama checkCommonGoal
//            //chiama checkEndGame
//                //controlla di non essere già in endgame
//                //controlla che il giocatore abbia riempito la shelf
//                    // se vero, setta endGame e assegna punto
//            //chiama saveGameStatus
//            //controlla se la board e' "vuota", e in caso chiama fillBoard
//            //passa il turno al giocatore successivo, o se era l'ultimo giocatore chiama endGame
//                    //endGame calcola i punteggi e assegna il vincitore e poi chiama resetGame
