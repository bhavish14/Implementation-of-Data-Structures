/** @authors Bhavish Khanna Narayanan [bxn170002];
*   Short Project 2: Bounded-Sized Queue
**/

package bxn170002;
import java.util.Scanner;
import java.util.Random;



public class BoundedQueue <T> {
    //Loop Invariants
    //queueRearPosition: Holds the rear index of the queue. New element will be inserted at the index referenced by
    //the variable.
    //queueFrontPosition: Initially positioned at the 0th index. Increments as the elements are removed.

    int queueRearPosition, queueFrontPosition;

    //Queue Size Parameters

    int queueCapacity, queueSize;

    //Bounded-Queue declaration
    T[] boundedQueue;


    //BoundedQueue Constructor; Initializes an array of type T and sets the loop invariants & parameters
    public BoundedQueue (int size)
    {
        boundedQueue = (T[]) new Object[size];
        queueRearPosition = 0;
        queueFrontPosition = 0;
        queueSize = 0;
        queueCapacity = size;

    }

    //Inserts an element at the index pointed by queueRearPosition.
    public boolean offer(T x) {

        if (queueSize==queueCapacity) {
            System.out.println("Queue Full");
            return false;
        }
        else{

            boundedQueue[queueRearPosition]=x;
            queueRearPosition=(queueRearPosition+1)%queueCapacity;
//            queueRearPosition++;
            queueSize++;
            return true;
        }//return false;
    }
    //Removes the front most element pointed by queueFrontPosition.
    public T poll() {
        if(isEmpty())
            System.out.println("Queue Empty");
        else{
            T temp = boundedQueue[queueFrontPosition];
            queueFrontPosition=(queueFrontPosition+1)%queueCapacity;
            queueSize--;
            return temp;

        }
        return null;
    }

    //Returns the front most element pointed by the queueFrontPosition vble.
    public T peek() {

        //if (queueFrontPosition != queueRearPosition) {
        if (queueSize > 0) {
            T temp = boundedQueue[queueFrontPosition];
            return temp;
        }
        return null;
    }

    //Returns the size of the queue.
    public int size() {
        if (queueSize > 0){
            return queueSize;
        }
        return 0;

    }

    //Checks for queue empty and returns a boolean value {true/false} accordingly.
    public boolean isEmpty() {
       return queueSize == 0;
       // return (queueFrontPosition==queueRearPosition);
    }

    //Prints the elements of the queue.
    public void printQueue() {
        //Loop Invariants
        //fp points to the front of the queue.
        //rp points to the rear of the queue.
        int fp = queueFrontPosition, rp = queueRearPosition;
        if (queueSize > 0){
            if (fp >= rp) {
                while (fp < queueCapacity) {
                    System.out.println(boundedQueue[fp]);
                    fp++;
                }
                fp = 0;
            }
            while(fp <rp) {
                System.out.println(boundedQueue[fp]);
                fp++;
            }
        }
    }


    //Copies the queue elements into a array a.
    public void toArray(T[] a) {
        //local loop invariants
        int fp = queueFrontPosition, rp = queueRearPosition;
        //Array a position counter
        int aCounter = 0;

        if (queueSize > 0){

            if (fp >= rp) {
                while (fp < queueCapacity) {
                    a[aCounter] = boundedQueue[fp];
                    fp++;
                    aCounter++;
                }
                fp = 0;
            }

            while(fp < rp) {
                a[aCounter] = boundedQueue[fp];
                fp++;
                aCounter++;
            }
        }
    }


    public static void main(String[] args) {
        int size =10;

        //BoundedQueue Object Deceleration
        BoundedQueue<Integer> obj = new BoundedQueue<>(size);

        //Inserting random elements into the queue.
        for (int i = 0; i < size; i++) {
            //obj.offer(Integer.valueOf(rand.nextInt(1000)));
            obj.offer(i+1);
        }

        Scanner in = new Scanner(System.in);
        System.out.println("Bounded Queue Operations:");
        System.out.println("1. Offer");
        System.out.println("2. Poll");
        System.out.println("3. Peek");
        System.out.println("4. Print Size");
        System.out.println("5. Clear Queue");
        System.out.println("6. Convert to Array");
        System.out.println("7. Print Queue");
        System.out.println("8. Is Empty?");
        System.out.print("> ");
        whileLoop:
        while(in.hasNext()) {
            int choice = in.nextInt();
            switch (choice) {
                //Adds elements at the end of the queue.
                case 1: {
                    System.out.print("Enter the element: ");
                    int addTemp = in.nextInt();
                    if (!obj.offer(Integer.valueOf(addTemp))) {
                        System.out.println("Element not added; queue full");
                        break;
                    }
                    System.out.println("Element added successfully");
                    break;
                }
                //Remove elements from the front of the queue.
                case 2: {
                    System.out.println("Removed Element:" + obj.poll());
                    break;
                }
                //Peek the top most element
                case 3: {
                    System.out.println("Top Most Element:" + obj.peek());
                    break;
                }
                //Print the size of the queue
                case 4: {
                    System.out.println("Size of the Queue: " + obj.size());
                    break;
                }
                //Clear the queue
                case 5: {
                    obj = new BoundedQueue<>(size);
                    break;
                }
                //Copy the values of the queue into an array.
                case 6: {
                    if (obj.size() > 0) {
                        Integer[] array = new Integer[obj.queueCapacity];
                        //System.out.println("Copied Array:" + array);
                        obj.toArray(array);
                        break;
                    }
                    System.out.println("Queue Empty");
                    break;
                }
                case 7: {
                    if (obj.size() > 0) {
                        System.out.println("Queue Elements: ");
                        obj.printQueue();
                        break;
                    }
                    System.out.println("Queue Empty");
                    break;
                }
                case 8: {
                    if (obj.isEmpty()) {
                        System.out.println("Queue Empty");
                        break;
                    }
                    System.out.println("Queue Not Empty");
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