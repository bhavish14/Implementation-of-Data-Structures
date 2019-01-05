/** bounded-size Binary Heap implementation
 * @authors: bhavish khanna narayanan [bxn170002]
 *           meet shah                [mxs170043]
 *
 */


//package bxn170002;
import java.util.Comparator;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;                                                     // Stores the elements of the priority queue
    Comparator<T> comp;                                         // Comparator for the heap
    int pqSize;                                                 // Stores the current size of the heap
    static final int pqCapacity = 20;                           // Maximum no of elements that the queue can hold

    // Constructor for building an empty priority queue using natural ordering of T
    // Parameters: array of type T
    // Return Value: None
    public BinaryHeap(T[] q) {
        this(q, (T a, T b) -> a.compareTo(b));                  // lambda expression to create comparator from compareTo
        this.pqSize = 0;

    }

    // Constructor for building an empty priority queue with custom comparator
    // Parameters: Array of type T and comparator object.
    // Return value: None
    public BinaryHeap(T[] q, Comparator<T> c) {
        pq = q;
        comp = c;
    }

    // Adds a new element to the priority queue and invokes percolateUp if order constraint is not satisfied.
    // Parameters: element to be added, x
    // Return value: None
    // Pre-condition: Queue must not be full
    public void add(T x) {
        if (pqSize >= pqCapacity){                              // throws exception if pq is full
            throw new java.lang.Error("Priority Queue Full!!");
        }
        pq[pqSize] = x;
        if (pqSize > 0) {
            percolateUp(pqSize);                                // To satisfy order constraint
        }
        pqSize++;
    }

    // Adds a new element to the priority queue and invokes percolateUp if order constraint is not satisfied.
    // Parameters: element to be added, x
    // Return value: True on successful insert. False on failure
    public boolean offer(T x) {
        if (pqSize >= pqCapacity) {
            return false;
        }
        add(x);
        return true;
    }

    // Removes a element from the priority queue and invokes percolateDowm if order constraint is not satisfied.
    // Parameters: None
    // Return value: removed element
    // Pre-condition: Queue must not be empty
    public T remove() {
        if (pqSize == 0){                                       // throws exception if pq is empty
            throw new java.lang.Error("Priority Queue Empty!!");
        }
        T root = pq[0];                                         // assign 0th element of the queue to root
        pq[0] = pq[pqSize - 1];
        pqSize--;

        if (pqSize > 0){
            percolateDown(0);                               // To satisfy order constraint
        }
        return root;
    }

    // Removes a element from the priority queue and invokes percolateDown if order constraint is not satisfied.
    // Parameters: None
    // Return value: removed element on success. False on failure
    public T poll() {
        if (pqSize == 0){                                     // throws exception if pq is empty
            return null;
        }
        T root = pq[0];
        root = this.remove();
        return root;
    }

    // Returns the root element from the priority queue.
    // Parameters: None
    // Return value: root element on success. False on failure
    public T peek() { /* return null if pq is empty */
        if (pqSize == 0) {
            return null;
        }
        return pq[0];
    }

    // Satisfies the order constraint by moving elements when a new element is added.
    // Parameters: position of inserted element, i
    // Return value: None
    // Precondition: The child may violate the heap order.
    void percolateUp(int i) {
        int parentPosition = parent(i);                         // Stores the parent of pq[i]
        int pos = i;
        T num = pq[i];

        while(pos > 0 && comp.compare(pq[parentPosition], num) == 1) {
            pq[pos] = pq[parentPosition];
            pos = parentPosition;
            parentPosition = parent(pos);
        }
        pq[pos] = num;
    }

    // Satisfies the order constraint by moving elements when a element is removed.
    // Parameters: position of element from where percolateDown to be performed.
    // Return value: None
    // Precondition: The parent may violate the heap order.
    void percolateDown(int i) {
        int child = leftChild(i);                               // Stores the left child of the parent
        T num = pq[i];
        while (child <= pqSize - 1) {
            // Compares both left and right child of a parent and selects the one with highest value
            if (child < pqSize - 1 && (comp.compare(pq[child], pq[child + 1]) > 0)){
                child = child + 1;
            }
            if (comp.compare(pq[child], num) >= 0){
                break;
            }
            pq[i] = pq[child];
            i = child;
            child = leftChild(i);
        }
        pq[i] = num;
    }

    // Returns the parent address of node i
    // Parameters: position of node i
    // Return value: parent address
    int parent(int i) {
        return (i - 1) / 2;
    }

    // Returns the child address of node i
    // Parameters: position of node i
    // Return value: child address
    int leftChild(int i) {
        return 2 * i + 1;
    }

    public static void main(String[] args) {

        Integer[] arr = new Integer[20];
        BinaryHeap<Integer> obj = new BinaryHeap<>(arr);

        Scanner in = new Scanner(System.in);
        System.out.println("Binary Heap Operations:");
        System.out.println("1. Add");
        System.out.println("2. Offer");
        System.out.println("3. remove");
        System.out.println("4. poll");
        System.out.println("5. Peek");
        System.out.println("6. Display Elements");
        System.out.print("> ");
        whileLoop:
        while (in.hasNext()) {
            int choice = in.nextInt();
            switch (choice) {
                // Adds element at the end of the queue.
                case 1: {
                    System.out.print("Enter the element: ");
                    int addTemp = in.nextInt();
                    obj.add(addTemp);
                    System.out.println("Element added successfully");
                    break;
                }
                // Offers Element to the queue
                case 2: {
                    System.out.print("Enter the element: ");
                    int addTemp = in.nextInt();
                    if (!obj.offer(addTemp)) {
                        System.out.println("Element not added; Priority queue full");
                        break;
                    }
                    System.out.println("Element added successfully");
                    break;
                }
                // Remove elements from the front of the queue.
                case 3: {
                    System.out.println("Removed Element:" + obj.remove());
                    break;
                }
                // Polls Element from the queue
                case 4: {
                    System.out.println("Removed Element:" + obj.poll());
                    break;
                }
                // Peek the top most element
                case 5: {
                    System.out.println("Top Most Element:" + obj.peek());
                    break;
                }
                // Display the elements of the queue
                case 6:{
                    for (int i = 0; i < obj.pqSize; i++) {
                        System.out.print(" " + obj.pq[i]);
                    }
                    System.out.println();
                    break;
                }
                default: {
                    System.out.println("Invalid Choice!");
                    break whileLoop;
                }

            }


        }
    }
};
