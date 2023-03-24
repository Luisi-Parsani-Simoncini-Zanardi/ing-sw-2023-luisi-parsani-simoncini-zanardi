# ing-sw-2023-luisi-parsani-simoncini-zanardi

DIVISIONE COMPITI:

Luca -> Aggiungere i javadoc ai test e sistemare l'UML (aggiungendo anche metodi get/set goalRed e tryPersGoal)

Lore -> Sistemare l'uml di commonGoal

Lollo -> Creare i test di savegamestatus e sistemare gli errori di SaveGameStatus dovuti al nuovo commongoal

Davide -> Istanziare in json i vari tipi di board --> DONE

NOTA SU METODI MODIFICATI: (da Davide)

Correggendo i test ho aggiunto un metodo alla classe bag, si tratta di getBagSize() che restituisce un intero uguale alla quantità di tiles presenti nella bag
Ho ache modificato il metodo setSelf che prima, prendendo una shelf come parametro e copiandola al suo interno, creava una ridondanza con il costruttore di shelf che
prende una shelf come parametro (praticamente facevano la stessa identica cosa). Adesso il metodo setSelf ha come parametro una matrice di tiles che copia all'interno del suo attributo.

NOTA PER QUANDO CREATE NUOVE CLASSI O AGGIUNGETE NUOVI METODI (da Davide)
In generale quando create o modificate le classi cercate di mantenere questo pattern:

-Attributi
-Costruttori
-Getters
-Setters
-Metodi vari
