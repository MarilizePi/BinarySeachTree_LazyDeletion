package lazyTrees;

import java.io.*;
import java.util.*;

/**
 * Simulates the song store of buying and adding tunes to a store's playlist.
 * Implements BST with lazy deletion to keep track of total inventory ("deleted" + non deleted)
 * and current inventory (non deleted only).
 *
 * @author Foothill College, [Pires, Marilize]
 */
public class FoothillTunesStore {
    public static final boolean SHOW_DETAILS = true;

    // TODO: Define the functor class PrintObject to traverse and print out data
    //       from LazySearchTree.
    // Note: Each class must be placed in its own .java file unless explicitly
    //       described as a nested class. See Program Guidelines for details.
    PrintObject<SongEntry> printObject = new PrintObject<>();

    // The data structure, which we use to add and remove songs.
    private LazySearchTree<SongEntry> tunes;

    /**
     * Instantiates inventory to be a LazySearchTree of SongEntry objects.
     */
    public FoothillTunesStore() {
        tunes = new LazySearchTree<>();
    }

    /**
     * Add a new song with the name as in parameter into inventory (tunes). If there is
     * already same name song, increase amount by one, if not create a new object.
     *
     * @param songs SongEntry to be added to the inventory tree.
     */
    public void addToInventory(SongEntry songs) {

        // Check if the item is in the inventory tree.
        boolean isFound = tunes.contains(songs);

        // If the song is not found, add the temporary object as another node (category) to the tree.
        if (!isFound) {
            // TODO: Modify insert method to work with lazy deletion such that it updates
            //       both hard and soft sizes.
            tunes.insert(songs);

            // NOTE: Need to check if the item was lazily deleted, then we need to increment the count
            SongEntry found = tunes.find(songs);
            if (found.getCount() == 0) {
                found.incrementCount();
            }
            return;
        }
    }

    /**
     * If the song is in the inventory, decrease the count by one.
     * If only one item is left, remove it from the inventory.
     *
     * @param songs SongEntry to be removed to the inventory tree.
     */
    public void removeFromInventory(SongEntry songs) {

        boolean isFound = tunes.contains(songs);

        // check if the song exists in the inventory disregarding lazy deletion
        if (!isFound) {
            throw new NoSuchElementException();
        }

        SongEntry found = tunes.find(songs);

        // if the items has zero left in stock,
        // then treat it as if it does not exist in the tree.
        if (found.getCount() == 0) {
            throw new NoSuchElementException();
        }
        // if the item has one left in stock,
        // then decrement its count and lazy delete it in the tree.
        else {
            found.decrementCount();
            tunes.remove(songs);
        }
    }


    /**
     * Display the first song item and last time of the soft tree in lexical order.
     */
    public void showFirstAndLastItem(String message) {
        System.out.println("\n" + message);

        // TODO: Modify the protected methods findMin() and findMax() to implement lazy deletion.
        //       Searches from the root of the tree and return the minimum and maximum node that
        //       has NOT been "deleted".
        try {
            SongEntry min = tunes.findMin();
            System.out.println("First item: " + min.toString());
        } catch (Exception NoSuchElementException) {
            System.out.println("Warning: minimum element not found!");
        }

        try {
            SongEntry max = tunes.findMax();
            System.out.println("Last item: " + max.toString());
        } catch (Exception NoSuchElementException) {
            System.out.println("Warning: maximum element not found!");
        }

    }

    /**
     * Shows the state of the tree by displaying:
     * - the *hard* tunes, which includes deleted nodes.
     * - the *soft* tunes, which excludes deleted nodes.
     *
     * @param message  Additional details about the state.
     * @param showTree Set to true if we want to display the contents of the tree
     */
    protected void displayInventoryState(String message, boolean showTree) {
        System.out.println("\n" + message);
        System.out.println("\"hard\" number of unique songs (i.e. mSizeHard) = " + tunes.sizeHard());
        System.out.println("\"soft\" number of unique songs (i.e. mSize) = " + tunes.size());

        if (!showTree)
            return;

        System.out.println("\nTesting traversing \"hard\" inventory:");

        // TODO: First, rename the public/private pair traverse() method of FHsearch_tree to traverseHard() method.
        //       Then, reuse this public/private pair of methods to traverses the tree
        //       and displays all the nodes.
        // NOTE: Here, we call the public version.
        tunes.traverseHard(printObject);


        System.out.println("\n\nTesting traversing \"soft\" inventory:");

        // TODO: Define a public/private pair of methods that traverses the tree
        //       and displays only nodes that have not been lazily deleted.
        // NOTE: Here, we call the public version.
        tunes.traverseSoft(printObject);
        System.out.println("\n");
    }

    public static void main(String[] args) {
        // Directory path for JSON file of songs
        final String FILEPATH = "resources/music_genre_subset.json";

        // TODO: Tests the LazySearchTree by adding and removing items from the inventory
        final String TESTFILE = "resources/tunes.txt";    // Directory path for plain-text file

        // NOTE: An example of testing the boundary condition when removing an item that may not exist
        ///final String TESTFILE = "resources/tunes_empty.txt";

        // parses the JSON input file
        MillionSongDataSubset msd = new MillionSongDataSubset(FILEPATH);

        // retrieves the parsed objects
        SongEntry[] allSongs = msd.getArrayOfSongs();

        ArrayList<SongEntry> listOfSongs = new ArrayList<>(Arrays.asList(allSongs));

        System.out.printf("Welcome! We have over %d in FoothillTunes store! \n", listOfSongs.size());

        System.out.printf("Test file: %s \n", TESTFILE);

        FoothillTunesStore myTunes = new FoothillTunesStore();

        File infile = new File(TESTFILE);

        try {
            Scanner input = new Scanner(infile);

            String line = "";
            int lineNum = 0;
            while (input.hasNextLine()) {
                lineNum++;
                line = input.nextLine();
                String[] tokens = line.split(",");

                String selection = tokens[0];
                String songName = tokens[1];

                String message = "at line #" + lineNum + ": " + line;

                SongEntry songFound = null;

                for (int i = 0; i < allSongs.length; i++) {
                    //find song title in allSongs that matches itemName from testing file
                    if (allSongs[i].getTitle().equals(songName)) {
                        songFound = allSongs[i];
                        break;
                    }
                }

                if (selection.equals("add")) {
                    myTunes.addToInventory(songFound);


                    // NOTE: Currently displaying the contents is disabled to reduce cluttering the output.
                    // Suggestion: To start, enable displaying the contents of the tree to help you debug.
                    if (SHOW_DETAILS)
                        myTunes.displayInventoryState("\nUpdate " + message, true);
                }
                // When an item is bought:
                // Decrement the count of the item.
                // If the item is out of stock,
                // remove the item from inventory.
                //
                // Note: buying an out of stock item, is invalid. Handle it appropriately.
                else if (selection.equals("buy")) {
                    try {
                        myTunes.removeFromInventory(songFound);

                        // NOTE: Currently displaying the contents is disabled to reduce cluttering the output.
                        // Suggestion: To start, enable displaying the contents of the tree to help you debug.
                        if (SHOW_DETAILS)
                            myTunes.displayInventoryState("\nUpdate " + message, true);
                    } catch (NoSuchElementException ex) {
                        // NOTE: Ideally we'd print to the error stream,
                        //       but to allow correct interleaving of the output
                        //       we'll use the regular output stream.
                        System.out.printf("\nWarning: Unable to fulfill request: %s \n", message);
                        System.out.printf("Warning: Song %s is out of stock.\n", songName);
                    }
                } else {
                    System.out.println("Warning: Inventory selection not recognized!");
                }

                // Display the first item and the last item before checking
                // if it's time to clean up our inventory.
                if (SHOW_DETAILS)
                    myTunes.showFirstAndLastItem(message);
            }
            input.close();

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        }

        // Display the inventory
        System.out.println("\n");
        myTunes.displayInventoryState("\nFinal state of inventory:", true);

        // flush the error stream
        System.err.flush();

        System.out.println("\nDone with SuperMarket.");
    }
}
