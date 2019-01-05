import java.util.HashSet;
import java.util.Random;

/**
 *  <h1> Binary Search Tree </h1>
 *  The BinarySearchTree implements various BST operations such
 *  as add, remove, etc.
 *  @see <a href="https://en.wikipedia.org/wiki/Hopscotch_hashing">
 *  Hopscotch Hashing</a>
 *
 *  @author     bhavish khanna narayanan    [bxn170002@utdallas.edu]
 *  @author     swapna chintapalli          [swapna.chintapalli@utdallas.edu]
 *  @version    0.1
 *  @since      10-21-2018
 */
public class HashingInt{

    private int maxDisplacement, len, capacity;
    private int[][] table;                              // Stores the hashtable
    private static int h, index, twoPower;
    static final int MAX = (int)Math.pow(2, 20)/4;      // Stores the MAX limit

    /**
     * This method is a constructor for the Hashing class. It initializes the various
     * fields of the class fields with appropriate elements.
     */
    public HashingInt() {
        maxDisplacement = 0;
        capacity = (int)Math.pow(2, 25);
        table = new int[capacity][2];
        for (int i = 0; i < capacity; i++){
            table[i][1] = 0;
        }
        len = 0;
        twoPower = 25;

    }

    /**
     *  Returns the element that is equivalent to the supplied element.
     *  @param x element to be searched in the tree
     *  @return index, element's position, if hashtable contains the element. else, null.
     */
    public int find(int x){
        int xspot, i;
        h = hash(x);
        index = indexFor(h, capacity);
        i = index;

        while(true) {
            if (table[i][0] == x && table[i][1] == 2 || table[i][1] == 0) {
                return i;
            } else if (table[i][1] == 1) {
                break;
            }
            i++;
        }
        xspot = i;
        while (true){
            if (table[i][0] == x && table[i][1] == 2){
                return i;
            }
            else if(table[i][1] == 0){
                return xspot;
            }
        }
    }

    /**
     *  Returns a boolean whether the element is present or not.
     *  @param x element to be searched in the hashtable
     *  @return status: true, if the element is present. Else, false.
     */
    public boolean contains(int x){
        if (len > 0) {
            int index = find(x);
            if (table[index][0] == x){
                return true;
            }
        }
        return false;
    }

    /**
    *   Inserts an element to the Hashtable according to the hash value.
    *   @param x element to be inserted
    *   @return status: true, on successful addition. Else, false.
    */
    public boolean add(int x){
        if (contains(x)){
            return false;
        }

        int  i, volunteerLoc;
        boolean failed;
        index = find(x);

        while (table[index][1] == 2){
            index = index + 1 % capacity;
        }
        while (displacement(x, index) > maxDisplacement){
            failed = true;
            for (i = 1; i <= maxDisplacement; i++){
                volunteerLoc = index - i < 0 ? capacity + (index - i) : index - i;
                if (displacement(table[volunteerLoc][0], index) <= maxDisplacement){
                    table[index][0] = table[volunteerLoc][0];
                    table[index][1] = 2;
                    table[volunteerLoc][1] = 1;
                    index = volunteerLoc;
                    failed = false;
                    break;
                }
            }
            if (failed){
                maxDisplacement += 1;
            }
        }
        table[index][0] = x;
        table[index][1] = 2;
        len++;
        return true;
    }

    /**
    *   Removes an element from the Hashtable
    *   @param element element to be removed
    *   @return status, if element is removed. Else, false.
    */
    public boolean remove(int element){
        index = find(element);
        if (table[index][0] == element){
            table[index][1] = 1;
            return true;
        }
        return false;
    }

    // Utility methods

    /**
    *   Returns the hash value of an element
    *   @param h element to be hashed
    *   @return h, hashcode of the element
    */
    static int hash(int h){
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
    *   Returns the index where the element has to be inserted in the hashtable
    *   @param h element to be hashed
    *   @param length length of the table
    *   @return index, index of the element
    */
    static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    /**
    *   Returns the displacement of the element from it's original index
    *   @param x element to be considered
    *   @param loc location where it is found
    *   @return displacement, difference between h and loc
    */
    public int displacement(int x, int loc){
        int h = hash(x);
        int index = indexFor(h, capacity);
        return loc >= index ? loc - index : capacity + (loc - index);
    }

    /**
    *   Rehashes the table if the size requirements of the hashtable exceeds the default
    */
    public void rehash(){
        int[] tempTable = new int[capacity];
        int i,pos = 0;
        for (i = 0; i < capacity; i++){
            if (table[i][1] == 2){
                tempTable[pos] = table[i][0];
                pos++;
            }
        }

        // Increasing table's limits
        twoPower += 10;
        capacity = (int)Math.pow(2, twoPower);
        table = new int[capacity][2];
        for (int[] item: table){
            table[i][1] = 0;
        }
        for (int item: tempTable){
            this.add(item);
        }
    }
}
