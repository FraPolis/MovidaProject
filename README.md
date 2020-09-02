# Movida Project
Progetto sviluppato per il corso di Algoritmi e Strutture Dati.

## Gruppo 
* Nome Gruppo : Caffeine Overflow
* Filippo Bartolucci - 0000838531
* Francesco Polisena - 0000731200

## Progetto
MOVIDA è un un'applicazione Java per interagire con un knowledge-base a tema cinema. 
L'applicazione deve essere sviluppata utilizzando 2 strutture dati e 2 algoritmi di ordinamento dati.

Strutture dati implementate:
* BTree
* Linked List

Algortimi di ordinamento implementati:
* Bubble Sort
* Heap Sort

## Funzionamento
Come prima cosa è necessario aver importato MovidaCore,che si trova nel package movida.bartoluccipolisena, per instanziarne la classe.
Se non si specifica l'algoritmo di ordinamento oppure la struttura dati, vengono utilizzati di default l'algoritmo Bubble Sort e la struttura dati BTree.

## MovidaCore
È la parte centrale dell'applicazione e si occupa della gestione delle strutture dati utilizzate:
```java
StrutturaDati movies = new MyBtree() ; 
// Struttura dati in cui vengono salvati i film.
// Il tipo "Struttura Dati" è un'interfaccia attraverso la quale si possono implementare BTree e LinkedList.
// Si cambia implementazione utilizzando il metodo setMap()

Movie[] myAllMoviesSorted; 
// Array ordinato (BubbleSort / Heapsort) di tipo Movie.

Sort s = new BubbleSort(); 
// Algoritmo di sort. Il tipo Sort è un'interfaccia per BubbleSort e HeapSort.
// Si cambia implementazione utilizzando il metodo setMap()

MyGraph graph; 
// Grafo con le informazioni relative agli attori

 ```
La funzione ` loadFromFile(File F)` si occupa di caricare i film da un file di testo. I film vengono salvati in `movies` utilizzando la struttura dati scelta con setMap (BTree struttura di default). Contemporanemente alla creazione dei dati per i film viene anche popolato `graph` utilizzato per le collaborazioni tra gli attori.
 
# Licenza 
