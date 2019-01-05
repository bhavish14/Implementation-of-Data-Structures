import java.util.*;

/**
 *  <h1> Binary Search Tree </h1>
 *  The BinarySearchTree implements various BST operations such
 *  as add, remove, etc.
 *  @see <a href="https://en.wikipedia.org/wiki/Binary_search_tree">
 *      Binary Search Tree</a>
 *
 *  @author     bhavish khanna narayanan    [bxn170002@utdallas.edu]
 *  @author     Meet Shah                   [mxs170043@utdallas.edu]
 *  @version    0.1
 *  @since      09-28-2018
 */
public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {

    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
        /**
         * This method is retrieve the elements of the nodes of a tree
         * @return T returns the element positioned at the node of the tree.
         */
        public T getE(){
            return element;
        }
    }

    Entry<T> root;                                                  // Stores the root node of the tree.
    int size;                                                       // Stores the size of the tree. Dynamic in nature.
    Deque<Entry<T>> stack;                                          // Stack used for storing the path to a element.
    Comparator<T> comp = (T a, T b) -> a.compareTo(b);              // Comparator used when traversing the tree.
    int position;

    public Iterator<T> iterator() {
        return null;
    }



    /**
     *  BinarySearchTree constructor
     *  Instantiates the elements of "this" with appropriate values.
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
        stack = new ArrayDeque<>(100);
    }

    /**
     *  Gets the element x from tree, if it exists.
     *  @param x the element to be retrieved
     *  @return the element of the node
     */
    public Entry<T> get(T x){
        stack.clear();
        stack.push(new Entry(null, null, null));
        return get(root, x);
    }

    /**
     *  Gets the element x from tree given a starting node to search from, if it exists.
     *  @param x node from where the element should be searched
     *  @param element the element to be searched
     *  @return node that contains the element
     */
    public Entry<T> get(Entry x, T element){
        if (x == null || comp.compare((T)x.getE(), element) == 0){
            return x;
        }
        while (true){
            if (comp.compare(element, (T)x.getE()) < 0){
                if(x.left == null){
                    break;
                }
                stack.push(x);
                x = x.left;
            }
            else if(comp.compare(element, (T)x.getE()) == 0){
                break;
            }
            else{
                if (x.right == null){
                    break;
                }
                stack.push(x);
                x = x.right;
            }
        }
        return x;
    }

    /**
     *  Checks whether a given element exists in the tree.
     *  @param x the element to be searched
     *  @return a boolean value; true if the element exists. false, otherwise.
     */
    public boolean contains(T x) {
        Entry element = get(x);
        if (element == null || comp.compare((T)element.getE(), x) != 0){
            return false;
        }
        return true;
    }

    /**
     *  Adds an element to the tree.
     *  <b>Note:</b> If tree contains a node with same key, the node is replaced by x and returns false.
     *  @param x element to be inserted into the tree
     *  @return a boolean value; true if the element is successfully inserted. false, otherwise.
     */
    public boolean add(T x) {
        Entry<T> element, newEntry;
        if (size == 0){
            root = new Entry<>(x, null, null);
            size = 1;
            return true;
        }
        else{
            stack.clear();
            element = get(x);
            if (element.element == x){
                newEntry = new Entry(x, element.left, element.right);
                element = newEntry;
                return false;
            }
            else {
                newEntry = new Entry<>(x, null, null);
                if (comp.compare(x, (T)element.getE()) > 0){
                    element.right = newEntry;
                }
                else{
                    element.left = newEntry;
                }
                size++;
                return true;
            }
        }
    }

    /**
     *  Removes an element from the tree.
     *  @param x element to be removed from the tree
     *  @return element, if it exists. else, null.
     */
    public T remove(T x) {
        Entry minRight;
        T result;
        stack.clear();

        if (size == 0){
            return null;
        }
        Entry t = get(x);

        if (comp.compare(x, (T)t.getE()) != 0) {
            return null;
        }
        result = (T)t.getE();
        if (t.left == null || t.right == null){
            bypass(t);
        }
        else{
            stack.clear();
            stack.push(t);
            minRight = get(t.right, (T)t.getE());
            t.element = minRight.element;
            bypass(minRight);
        }
        size--;
        return result;
    }

    /**
     *  Utility function for the remove.
     *  @param t tree node which is to be removed
     */
    public void bypass(Entry t){
        Entry parent = stack.peek();
        Entry child = t.left == null? t.right: t.left;

        if (parent == null){

            root = child;
        }
        else if (parent.left == t){
            parent.left = child;
        }
        else{
            parent.right = child;
        }
    }

    /**
     *  Returns the smallest element in the tree.
     *  @return element, if tree not empty. else, null.
     */
    public T min() {
        if (size == 0){
            return null;
        }
        Entry<T> t = root;
        while (t.left != null){
            t = t.left;
        }
        return t.element;
    }

    /**
     *  Returns the largest element in the tree.
     *  @return element, if tree not empty. else, null.
     */
    public T max() {
        if (size == 0){
            return null;
        }
        Entry<T> t = root;
        while(t.right != null){
            t = t.right;
        }
        return t.element;
    }

    // TODO: Create an array with the elements using in-order traversal of tree

    /**
     *  Populates a comparable array with the nodes of the tree
     *  <b>Note:</b> The elements are populated in the sorted order.
     *  @return array of nodes if tree exists. null, otherwise.
     */
    public Comparable[] toArray(){
        position = 0;
        if (size == 0){
            return null;
        }
        Comparable[] arr = new Comparable[size];
        populate(root, arr);
        return arr;
    }
    /**
     *  A recursive utility function for the toArray() which populates the array
     *  @param node node from which the traversal should start
     *  @param arr  array where the elements are to be stored
     */
    public void populate(Entry<T> node, Comparable[] arr){
        if (node != null){
            populate(node.left, arr);
            arr[position] = node.element;
            position++;
            populate(node.right, arr);
        }
    }


    /**
     * Recursive function to print the nodes of a tree in a inorder fashion.
     * @param node  the node from where the traversal should begin
     */
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

    /**
     *  Caller function of the printTree()
     */
    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    /**
     *  The main method where various BST operations are offered and invoked.
     *  @param args parameters that are used when executing the BST
     */
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();             // BinarySearchTree object where the tree will be stored.
        Scanner in = new Scanner(System.in);
        System.out.println("Binary Search Tree Operations:");
        System.out.println("1. Add");
        System.out.println("2. remove");
        System.out.println("3. contains");
        System.out.println("4. min");
        System.out.println("5. max");
        System.out.println("6. toArray");
        System.out.print("> ");
        whileLoop:
        while(in.hasNext()) {
            int choice = in.nextInt();
            switch (choice) {
                case 1: {
                    System.out.print("Enter the element to add: ");
                    int addTemp = in.nextInt();
                    System.out.print("Add " + addTemp + " : ");
                    t.add(addTemp);
                    t.printTree();
                    break;
                }
                case 2:{
                    System.out.print("Enter the element to remove: ");
                    int removeTemp = in.nextInt();
                    System.out.print("Remove " + removeTemp + " : ");
                    t.remove(Math.abs(removeTemp));
                    t.printTree();
                    break;
                }
                case 3: {
                    System.out.print("Enter the element to check: ");
                    int cont = in.nextInt();
                    System.out.print("contains " + cont + " : ");
                    System.out.print(t.contains(cont));
                    //t.printTree();
                    break;
                }
                case 4: {

                    System.out.print("Minimum "  + " : "+ t.min());
                    break;
                }
                case 5: {

                    System.out.print("Maximum "  + " : "+ t.max());
                    break;
                }
                case 6: {
                    Comparable[] arr = t.toArray();
                    System.out.print("Final: ");
                    for (int i = 0; i < t.size; i++) {
                        System.out.print(arr[i] + " ");
                    }break;
                }
                default: {
                    System.out.println("Invalid Choice!");
                    break whileLoop;
                }

            }
        }

    }
}

