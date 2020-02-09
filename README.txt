project folder:
MarilizePi-cs1c-project05

Brief description of submitted files:

src/lazyTrees/FoothillTunesStore.java
    - Simulates the song store of buying and adding tunes to a store's playlist.
    - Implements BST with lazy deletion to keep track of total inventory ("deleted" + non deleted)
      and current inventory (non deleted only).
    - Contains main.

src/lazyTrees/Item.java
    - One object of Item class represents one item in the inventory, with two class members.

src/lazyTrees/LazySearchTree.java
    - LazySearchTree is a Binary Search Tree (BST) algorithm that implements lazy deletion to solve the assigned tasks.

src/lazyTrees/MillionSongDataSubset.java
    - One object of class MillionSongDataSubset parses a JSON data set and stores each entry in an array.

src/lazyTrees/PrintObject.java
    - It traverses and prints out data.

src/lazyTrees/SongEntry.java
    - Stores a simplified version of the genre data set from the Million Song Dataset.

src/lazyTrees/SuperMarket.java
    - Simulates the market scenario of buying and adding items to a store's inventory.
    - Implements BST with lazy deletion to keep track of total inventory ("deleted" + non deleted)
      and current inventory (non deleted only).
    - Contains main.

src/lazyTrees/TimeConverter.java
    - Converts duration into a string representation.

src/lazyTrees/Traverser.java
    - It provides interface used for all tree algorithms.

-----

resources/inventory_empty.txt
    - Tests removing an item from empty inventory.

resources/inventory_invalid_removal.txt
    - Tests removing an item that might not exist (previously lazily deleted).

resources/inventory_log.txt
    - Tests implementation of LazySearchTree by adding and removing items from inventory.

resources/inventory_no_item.txt
    - Tests removing item that does not exist (never in the inventory).

resources/short.txt
    - Tests removing an item from a tree that has right and left children.

resources/music_genre_subset.json
    - Contains songs in a JSON format (over 59600 songs).

resources/RUN.txt
    - Console outputs various tests/runs of SuperMarket.java class.

resources/RUN2.txt
    - Console outputs various tests/runs of FoothillTunesStore.java class.

resources/tunes.txt
    - Tests adding and removing songs from tunes.

resources/tunes_empty.txt
    - Tests when list of songs is empty.

-----

.gitignore
    - .gitignore content tells git to ignore specified files or folder.

-----

README.txt
    - Description of submitted files.