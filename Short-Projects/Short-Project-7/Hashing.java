
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
 *  @version    0.2
 *  @since      10-21-2018
 */
public class Hashing<T>{
    // Stores the hashtable
    static class Entry<T>{
        T element;
        int status;

        public Entry(){
            element = null;
            status = 0;
        }
    }
    static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;

        public Timer() {
            startTime = System.currentTimeMillis();
        }

        public void start() {
            startTime = System.currentTimeMillis();
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            return this;
        }

        public String toString() {
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }

    }

    int maxDisplacement, len, capacity;
    static int index, twoPower;
    static final int MAX = (int)Math.pow(2, 20)/4;      // Stores the MAX limit
    Entry<T>[] table;

    /**
     * This method is a constructor for the Hashing class. It initializes the various
     * fields of the class fields with appropriate elements.
     */
    public Hashing() {
        maxDisplacement = 0;
        capacity = (int)Math.pow(2, 25);
        len = 0;
        twoPower = 15;
        table = new Entry[capacity];
        for (int i = 0; i < capacity; i++){
            table[i] = new Entry();
        }

    }

    /**
     *  Returns the element that is equivalent to the supplied element.
     *  @param x element to be searched in the tree
     *  @return index, element's position, if hashtable contains the element. else, null.
     */
    public int find(T x){
        int xspot, i;
        index = hashFunction(x);
        i = index;
        if (index > capacity){
            this.rehash();
        }

        while(true) {
            if (table[i].element != null && table[i].element.equals(x) || table[i].status == 0) {
                return i;
            }
            else if (table[i].status == 1) {
                break;
            }
            i++;
        }
        xspot = i;
            while (true){
            if (table[i].element.equals(x)){
                return i;
            }
            else if(table[i].status == 0){
                return xspot;
            }
        }
    }

    /**
     *  Returns a boolean whether the element is present or not.
     *  @param x element to be searched in the hashtable
     *  @return status: true, if the element is present. Else, false.
     */
    public boolean contains(T x){
        if (len > 0) {
            int index = find(x);
            System.out.println(table[index].element);
            if (table[index].element != null && table[index].element.equals(x)){
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
    public boolean add(T x){
        if (contains(x)){
            return false;
        }

        int  i, volunteerLoc;
        boolean failed;
        index = find(x);

        if (index > capacity){
            this.rehash();
        }

        while (table[index].status == 2){
            System.out.println("add: while1");
            index = index + 1 % capacity;
        }
        while (displacement(x, index) > maxDisplacement){
            System.out.println("add: while2");
            failed = true;
            for (i = 1; i <= maxDisplacement; i++){
                volunteerLoc = index - i < 0 ? capacity + (index - i) : index - i;
                if (displacement(table[volunteerLoc].element, index) <= maxDisplacement){
                    table[index].element = table[volunteerLoc].element;
                    table[index].status = 2;
                    table[volunteerLoc].status = 1;
                    index = volunteerLoc;
                    failed = false;
                    break;
                }
            }
            if (failed){
            maxDisplacement += 1;
            }
        }
        table[index].element = x;
        table[index].status = 2;
        len++;
        System.out.println("adds element at: " + index);
        return true;
    }

    /**
     *   Removes an element from the Hashtable
     *   @param element element to be removed
     *   @return status, if element is removed. Else, false.
     */
    public boolean remove(T element){
        index = find(element);
        System.out.println(index);
        if (table[index].element.equals(element)){
            table[index].status = 1;
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
    public int hash(int h){
        h ^= (h >>> 20) ^ (h >>> 12);
        return (h ^ (h >>> 7) ^ (h >>> 4));
    }

    /**
     *   Returns the index where the element has to be inserted in the hashtable
     *   @param h element to be hashed
     *   @param length length of the table
     *   @return index, index of the element
     */
    public int indexFor(int h, int length) {
        return h & (length - 1);
    }


    public int hashFunction(T h){
        return indexFor(hash(h.hashCode()), len);
    }

    /**
     *   Returns the displacement of the element from it's original index
     *   @param x element to be considered
     *   @param loc location where it is found
     *   @return displacement, difference between h and loc
     */
    public int displacement(T x, int loc){
        //T h = hash(x);
        //int index = indexFor(h, capacity);
        int index = hashFunction(x);
        return loc >= index ? loc - index : capacity + (loc - index);
    }


    /**
     *   Rehashes the table if the size requirements of the hashtable exceeds the default
     */
    public void rehash(){
        T[] tempTable = (T[]) new Object[capacity];
        int i,pos = 0;
        for (i = 0; i < capacity; i++){
            if (table[i].status == 2){
                tempTable[pos] = table[i].element;
                pos++;
            }
        }

        // Increasing table's limits
        twoPower += 10;
        capacity = (int)Math.pow(2, twoPower);

        table = new Entry[capacity];
        for (i = 0; i < capacity; i++){
            table[i] = new Entry();
        }

        for (T item: tempTable){
            this.add(item);
        }

    }


    public static void main(String args[]){
        // Variables
        Random randGenerator = new Random();
        Timer timer = new Timer();
        Hashing<Integer> obj = new Hashing<>();
        Integer randomNumber = 0, i;

        int[] rnd = new int[1000000];

        // Bootstraped hashset
        timer.start();
        for (i = 1; i < 100000; i++){
            System.out.println(i);
            randomNumber = randGenerator.nextInt((int)Math.pow(2, 20)) + 1;
            rnd[i] = randomNumber;
            //System.out.println(randomNumber);
            obj.add(randomNumber);

            System.out.println((float)obj.len / obj.capacity);

            if ((float)obj.len / obj.capacity > 0.75){
                obj.rehash();
            }

        }

        timer.end();
        System.out.println("Bootstraped Hashset \n" + timer + "\n");

        for (i = 0; i < 100000; i++){
            System.out.println(obj.find(rnd[i]));
        }

        /*
        // Java's hashset implementation
        HashSet<Integer> table = new HashSet<>();

        timer.start();
        for (i = 0; i < Math.pow(2, 20); i++){
            randomNumber = randGenerator.nextInt((int)Math.pow(2, 20)) + 1;
            table.add(randGenerator.nextInt(randomNumber));
        }
        timer.end();
        System.out.println("Java's Hashset \n" + timer);

        //System.out.println(obj.contains(randGenerator.nextInt((int)Math.pow(2, 20)) + 1));
        */
      }
}
