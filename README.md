# ing-sw-2023-luisi-parsani-simoncini-zanardi

Fare personalGoal e board con JSON -> davide

Creare test sul Model -> lore

Iniziare ad abbozzare il Controller -> luca

Setuppare i JSON di backup per la persistenza -> lollo

POTENZIALI BUG:
-non c'è nessun modo di accedere alla bag di una board attraverso la classe Board e quindi potrebbe dare dei problemi quando si usa il costruttore di Board passandogli dei parametri -> della bag non ci serve sapere il contenuto se non per estrarre, no? a cosa ci serve accedergli? -LP
-bisogna gestire il fatto che non si possono solo 2 common goal in game e che quest'ultimi non possono essere uguali -> il fatto che ce ne siano solo 2 sarà all'interno della funzione di start, ne verranno inizializzati solo due. anche il fatto che devono essere diversi verrà gestito lì: generiamo rng da uno a dodici appoggiato su una variabile tmp, commonGoal(tmp); rng da uno a 12 su tmp2, while tmp==tmp2: sovrascriviamo tmp2 con nuovi rng, e infine commongoal(tmp2) -LP
