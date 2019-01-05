import java.util.Random;
import java.util.*;

/**
 *  <h1> Merge and Insertion Sort </h1>
 *  Comparing the runtimes of multiple versions of merge sort and insertion sort.
 *
 *  @author     bhavish khanna narayanan    [bxn170002@utdallas.edu]
 *  @author		swapna chintapalli			[sxc180048@utdallas.edu]
 *  @version    0.2
 *  @since      11-04-2018
 */
public class SP9 {

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

		public long duration() { if(!ready) { end(); }  return elapsedTime; }

		public long memory()   { if(!ready) { end(); }  return memUsed; }

		public void scale(int num) { elapsedTime /= num; }

		public String toString() {
			if(!ready) { end(); }
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
		}
	}

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

	public static int threshold = 3;			// Stores the minimun no elements
												// required for MergeSort
	public static Random random = new Random();	// Stores the random vble. generator
	public static int numTrials = 100;			// Stores the number of trials

	/**
	 *	Given an array of unsorted elements, sorts it using insertion sort.
	 * 	@param arr array of elements to be sorted
	 *	@param p left index of the array
	 *	@param r right index of the array
	 */
	public static void insertionSorting(int[] arr, int p,int r) {
		int temp=0;
		int j=0;
		for(int i=p+1;i<r+1;i++)
		{
			temp = arr[i];
			j=i-1;
			while(j>=p && temp<arr[j])
			{
				arr[j+1] = arr[j];
				j=j-1;
			}
			arr[j+1] = temp;
		}
	}
	/**
	 *	Invokes the InsertionSort with array of elements as the param.
	 * 	@param arr array of elements to be sorted
	 */
	public static void insertionSort(int[] arr) {
		insertionSorting(arr,0,arr.length-1);
	}

	/**
	 *	Copies the elements of B into A.
	 * 	@param a array of elements
	 *	@param b array where the result of previous merge is stored
	 * 	@param p left index of the array B
	 *	@param q center of the array B
	 *	@param r right index of the array B
	 */
	public static void merge3(int[] a,int[] b,int p, int q, int r) {
		int i=p;
		int j=q+1;
		int k=p;
		while(i <= q && j <= r)		// While both i and j are within the bounds of B
		{
			if(b[i]<=b[j])
			{
				a[k] = b[i];
				i++;
			}
			else
			{
				a[k] = b[j];
				j++;
			}
			k++;
		}
		while(i <= q)		// Copying the remaining elements on the LHS of B int a
		{
			a[k] = b[i];
			k++;
			i++;
		}
		while(j<=r)			// Copying the remaining elements on the RHS of B int a
		{
			a[k] = b[j];
			k++;
			j++;
		}
	}

	/**
	 *	Splits the array B recursively and merges them.
	 * 	@param a array of elements
	 *	@param b array on which the split is performed
	 * 	@param left left index of the array A
	 *	@param r right index of the array A
	 */
	public static void mergeSortTake3(int[] a,int[] b,int left,int n) {
		if(n < threshold)
		{
			insertionSort(a);//,left,left+n-1);
		}
		else
		{
			int ln = n/2;
			mergeSortTake3(b,a,left,ln);			// LHS of the middle
			mergeSortTake3(b,a,left+ln,n-ln);		// RHS of the middle
			merge3(a,b,left,left+ln-1,left+n-1);
		}
	}

	/**
	 *	Invokes the MergeSortTake3 with array of elements as the param.
	 * 	@param arr array of elements to be sorted
	 */
	public static void mergeSort3(int[] arr) {
		int[] b = new int[arr.length];
		System.arraycopy(arr, 0, b, 0,	arr.length);
		mergeSortTake3(arr,b,0,arr.length);
	}

	/**
	 *	Copies the elements of B into A.
	 * 	@param a array of elements
	 *	@param b array where the result of previous merge is stored
	 * 	@param p left index of the array B
	 *	@param q center of the array B
	 *	@param r right index of the array B
	 */
	public static void merge2(int[] a,int[] b,int p, int q,int r) {
		System.arraycopy(a, p, b, p, r-p+1);
		int i=p;
		int j=q+1;
		for(int k=p;k<=r;k++)
		{
			if(j>r || ( i<=q && b[i]<=b[j]))
			{
				a[k]= b[i++];
			}
			else
			{
				a[k]=b[j++];
			}
		}
	}

	/**
	 *	Splits the array B recursively and merges them.
	 * 	@param a array of elements
	 *	@param b array on which the split is performed
	 * 	@param left left index of the array A
	 *	@param r right index of the array A
	 */
	public static void mergeSortTake2(int[] arr,int p,int r) {
		if(r-p+1 < threshold)
		{
			insertionSort(arr);
		}
		else
		{
			int q=(p+r)/2;
			int[] b_arr = new int[arr.length];
			mergeSortTake2(arr,p,q);
			mergeSortTake2(arr,q+1,r);
			merge2(arr,b_arr,p,q,r);
		}
	}

	/**
	 *	Invokes the MergeSortTake2 with array of elements as the param.
	 * 	@param arr array of elements to be sorted
	 */
	public static void mergeSort2(int[] arr) {
		mergeSortTake2(arr,0,arr.length-1);
	}

	/**
	 *	Copies the elements of B into A.
	 * 	@param arr array of elements
	 * 	@param p left index of the array B
	 *	@param q center of the array B
	 *	@param r right index of the array B
	 */
	public static void merge1(int[] arr,int p,int q,int r) {
		int[] left = new int[q-p+1];
		int[] right = new int[r-q];
		System.arraycopy(arr, p, left, 0,q-p+1);
		System.arraycopy(arr, q+1, right, 0, r-q);
		int i=0,j=0;
		for(int k=p;k<=r;k++)
		{
			if( (j>=right.length) || ((i<left.length) && (left[i]<=right[j])) )
			{
				arr[k] = left[i];
				i++;
			}
			else
			{
				arr[k] = right[j];
				j++;
			}
		}
	}

	/**
	 *	Splits the array A recursively and merges them.
	 * 	@param arr array of elements
	 * 	@param left left index of the array A
	 *	@param r right index of the array A
	 */
	public static void mergeSortTake1(int[] arr,int p,int r) {
		//int q=0;
		if(p<r)
		{
			int q = (p+r)/2;
			mergeSortTake1(arr,p,q);
			mergeSortTake1(arr,q+1,r);
			merge1(arr,p,q,r);
		}
	}

	/**
	 *	Invokes the MergeSortTake1 with array of elements as the param.
	 * 	@param arr array of elements to be sorted
	 */
	public static void mergeSort1(int[] arr) {
		mergeSortTake1(arr,0,arr.length-1);
	}



	public static void main(String[] args) {
		int n = 1048576; // 1G: 1048576; 512M: 524288; 256M:262144;//128M: 131072;//64M:65536;//32M:32768; 16M: 16384; 8M: 8192;
		int choice = 1 + random.nextInt(4);

		/*
		if(args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		*/
		if(args.length > 1) {
			choice = Integer.parseInt(args[1]);
		}

		int[] arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = i;
		}

		Timer timer = new Timer();
		switch(choice) {
			case 1:
				// insertion sort
				Shuffle.shuffle(arr);
				numTrials = 1;
				insertionSort(arr);

				break;
			case 2:
				// merge sort take 1
				for(int i=0; i<numTrials; i++) {
					Shuffle.shuffle(arr);
					mergeSort1(arr);
				}
				break;
			case 3:
				// Merge sort take 2
				for(int i=0; i<numTrials; i++) {
					Shuffle.shuffle(arr);
					mergeSort2(arr);
				}
				break;
			case 4:
				// Merge sort take 3
				for(int i=0; i<numTrials; i++) {
					Shuffle.shuffle(arr);
					mergeSort3(arr);
				}
				break;
			default:
				System.out.println("Invalid Choice");
		}
		timer.end();
		timer.scale(numTrials);
		//timer.end();
		System.out.println("Choice: " + choice + "\n" + timer);
	}




}
