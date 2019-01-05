
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class HashingIntDriver {
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
    public static void main(String[] args) throws FileNotFoundException {
        // Initialize the timer
        Timer timer = new Timer();
        // Variables
        Random randGenerator = new Random();
        HashingInt obj = new HashingInt();
        Integer randomNumber = 0, i;

        int[] rnd = new int[1000000];

        Scanner sc = new Scanner(System.in);
        System.out.println("HashTable Operations:");
        System.out.println("1. Add");
        System.out.println("2. Remove");
        System.out.println("3. Contains?");
        System.out.print("> ");
        whileLoop:
        while(sc.hasNext()){

            int choice = sc.nextInt();
            switch (choice) {
                // Adds element to the hashtable.
                case 1: {
                    System.out.print("Enter the element: ");
                    int addTemp = sc.nextInt();
                    boolean status = obj.add(addTemp);
                    if (status){
                        System.out.println("Element Inserted Successfully");
                    }
                    else{
                        System.out.println("Element Not Inserted");
                    }
                    break;
                }
                // Removes an element from the hashtable.
                case 2: {
                    System.out.print("Enter the element: ");
                    int addTemp = sc.nextInt();
                    boolean status = obj.remove(addTemp);
                    if (status){
                        System.out.println("Element Deleted Successfully");
                    }
                    else{
                        System.out.println("No Element Found");
                    }
                    break;
                }
                // Contains an element?
                case 3: {
                    System.out.print("Enter the element: ");
                    int addTemp = sc.nextInt();
                    boolean status = obj.contains(addTemp);
                    if (status){
                        System.out.println("Element Found!");
                    }
                    else{
                        System.out.println("No Element Found");
                    }
                    break;
                }
                default: {
                    System.out.println("Invalid Choice!");
                    break whileLoop;
                }
            }

        }

    }
}