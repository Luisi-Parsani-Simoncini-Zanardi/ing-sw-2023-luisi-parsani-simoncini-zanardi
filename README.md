# ing-sw-2023-luisi-parsani-simoncini-zanardi

NOTA SU METODI MODIFICATI: (da Davide)
Correggendo i test ho aggiunto un metodo alla classe bag, si tratta di getBagSize() che restituisce un intero uguale alla quantità di tiles presenti nella bag
(la bag è irraggiungibile "dall'esterno" e quindi conferire questo metodo agli utilizzatori della classe mi è sembrato utile).
Ho anche modificato il metodo setSelf che prima, prendendo una shelf come parametro e copiandola al suo interno, creava una ridondanza con il costruttore di shelf che
prende una shelf come parametro (praticamente facevano la stessa identica cosa). Adesso il metodo setSelf ha come parametro una matrice di tiles che copia all'interno del suo attributo.
Aggiunto anche un costruttore vuoto di personalGoal che genera un peronalGoal completamente empty.

NOTA PER QUANDO CREATE NUOVE CLASSI O AGGIUNGETE NUOVI METODI (da Davide)
In generale quando create o modificate le classi cercate di mantenere questo pattern:

-Attributi
-Costruttori
-Getters
-Setters
-Metodi vari
