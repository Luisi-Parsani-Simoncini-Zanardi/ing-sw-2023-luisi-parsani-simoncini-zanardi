# ing-sw-2023-luisi-parsani-simoncini-zanardi

Fare personalGoal e board con JSON -> davide

Creare test sul Model -> lore

Iniziare ad abbozzare il Controller -> luca

Setuppare i JSON di backup per la persistenza -> lollo

NOTA BUG!!!
bisogna sistemare temporaryTiles nella classe player in modo che non ne posso avere più di 3
-Lorenzo Luisi (RISOLTO)
sistemato, ho messo una Exception. poi parleremo meglio di cosa deve fare, ma penso sia legato alla view quello
-Luca Parsani

AGGIORNAMENTO BUG PLAYER!
mi sono appena accorto che addTiles deve gestire anche il fatto che non si possono aggiungere EMPTY e UNUSED tiles.
-Lorenzo Luisi (RISOLTO)

CONSIDERAZIONE SULLA CLASSE BOARD:
potremmo settare endGame direttamente a false all'inizio della partita invece che doverlo fare manualmente ogni volta con setEndgame, perchè quando chiamiamo il costruttore di Board l'attributo endGame rimane non inizializzato. -Lorenzo Luisi
