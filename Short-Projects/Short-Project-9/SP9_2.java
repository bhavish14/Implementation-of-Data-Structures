/** Sample starter code for SP9.
 *  @author
 */

import java.util.Random;
import java.util.*;

public class SP9_2 {
    public static Random random = new Random();
    public static int numTrials = 100;



   /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() {
            if(!ready) {
                end();
            }
            return elapsedTime;
        }

        public long memory() {
             if(!ready) {
                 end();
             }
             return memUsed;
         }

	public void scale(int num) { elapsedTime /= num; }

        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }

    /** @author rbk : based on algorithm described in a book
     */


    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {

    	public static void shuffle(int[] arr) {
    	    shuffle(arr, 0, arr.length-1);
    	}

    	public static<T> void shuffle(T[] arr) {
    	    shuffle(arr, 0, arr.length-1);
    	}

    	public static void shuffle(int[] arr, int from, int to) {
    	    int n = to - from  + 1;
    	    for(int i=1; i<n; i++) {
        		int j = random.nextInt(i);
        		swap(arr, i+from, j+from);
    	    }
    	}

    	public static<T> void shuffle(T[] arr, int from, int to) {
    	    int n = to - from  + 1;
    	    Random random = new Random();
    	    for(int i=1; i<n; i++) {
    		int j = random.nextInt(i);
    		swap(arr, i+from, j+from);
    	    }
    	}

    	static void swap(int[] arr, int x, int y) {
    	    int tmp = arr[x];
    	    arr[x] = arr[y];
    	    arr[y] = tmp;
    	}

    	static<T> void swap(T[] arr, int x, int y) {
    	    T tmp = arr[x];
    	    arr[x] = arr[y];
    	    arr[y] = tmp;
    	}

    	public static<T> void printArray(T[] arr, String message) {
    	    printArray(arr, 0, arr.length-1, message);
    	}

    	public static<T> void printArray(T[] arr, int from, int to, String message) {
    	    System.out.print(message);
    	    for(int i=from; i<=to; i++) {
    		          System.out.print(" " + arr[i]);
    	    }
    	    System.out.println();
    	}
    }


    public static void insertionSort(int[] arr, int p, int r) {
        int i, j, temp;
        for (i = 1; i <= r; i++) {
            j = i - 1;
            temp = arr[i];
            while (j >= p && temp < arr[j]) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }


        /*
        T temp;
        for (i = 1; i <= r; i++) {
            j = i - 1;
            temp = arr[i];
            while (j >= p && temp.compareTo(arr[j]) == -1) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
            for (T item: arr){
                System.out.print(item + " ");
            }
            System.out.println();
        }
        */
    }

    public static void insertionSort(int[] arr) {
        insertionSort(arr, 0, arr.length - 1);
    }


    public static void merge1(int[] arr, int left, int middle, int right) {
        int[] leftArray = new int[middle - left + 1];
        int[] rightArray = new int[right - middle];
        System.arraycopy(arr, left, leftArray, 0, middle - left + 1);
        System.arraycopy(arr, middle + 1, rightArray, 0, right - middle);

        int i = 0, j = 0;
        for(int k = left; k <= right; k++) {

            if (j > right - middle - 1|| (i <= middle - left && leftArray[i] < rightArray[j])) {
                arr[k] = leftArray[i++];
            }
            else {
                arr[k] = rightArray[j++];
            }
        }

    }

    public static void mergeSort1(int[] arr, int left, int right) {
        if (left < right){
            int middle = (left + right) / 2;
            mergeSort1(arr, left, middle);
            mergeSort1(arr, middle + 1, right);
            merge1(arr, left, middle, right);
        }
    }


    public static void mergeSort1(int[] arr) {
        mergeSort1(arr, 0, arr.length - 1);
    }


    public static void main(String[] args) {
    	int n = 20;
        int choice = 1 + random.nextInt(4);
    	if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }
    	if(args.length > 1) {
             choice = Integer.parseInt(args[1]);
        }

        int[] arr = new int[n];

        for(int i=1; i<=n; i++) {
	           arr[i - 1] = i;
	    }

    	Timer timer = new Timer();
    	switch(choice) {
    	case 1:
    	    Shuffle.shuffle(arr);
    	    numTrials = 1;
    	    insertionSort(arr);
    	    break;
    	case 2:
            Shuffle.shuffle(arr);
            for (int i = 1; i <= numTrials; i++) {
                mergeSort1(arr);
            }
    	    break;
        case 3:
            Shuffle.shuffle(arr);
            insertionSort(arr);
            break;
            /*
            Shuffle.<Integer>printArray(
                Arrays.stream( arr ).boxed().toArray( Integer[]::new ),
                "Sorted"
            );
            */
    	}
    	timer.end();
    	timer.scale(numTrials);

    	System.out.println("Choice: " + choice + "\n" + timer);
    }




}
