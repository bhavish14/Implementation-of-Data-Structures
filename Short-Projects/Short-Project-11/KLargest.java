import java.util.*;
import bxn170002.shuffle.Shuffle;
import bxn170002.timer.Timer;

/**
 *  <h1> Find K Largest Elements </h1>
 *  Comparing the runtimes of finding the K-Largest elements using inplace quick sort
 *  and Priority Queues.	
 *
 *  @author     bhavish khanna narayanan    [bxn170002@utdallas.edu]
 *  @author		Shruti jaiswal 				[sxj170027]
 *  @version    0.1
 *  @since      11-18-2018
 */


public class KLargest {
	static PriorityQueue<Integer> pq;
	static Timer timer;

	/**
	 *	Given the indexes of two elements in an array, swaps them both.
	 * 	@param arr array of elements
	 *	@param index1 index of 1st element to be sorted
	 *	@param index2 index of 2nd element to be sorted
	 */
	private static void swap(int[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
	
	/**
	 *	Given an array of unsorted elements, sorts it using insertion sort.
	 * 	@param arr array of elements to be sorted
	 *	@param p left index of the array
	 *	@param r right index of the array
	 */
	private static void insertionSort(int arr[], int p, int r) {
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
	 *	Selects the right most element as the pivot element and sorts the other element of the array wrt to this element. 
	 * 	@param arr array of elements to be sorted
	 *	@param p left index of the array
	 *	@param r right index of the array
	 */
	private static int partition(int[] arr, int p, int r) {
		int x = arr[r];
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			if (arr[j] < x) {
				swap(arr, j, ++i);				
			}
		}
		swap(arr, i + 1, r);
		return i + 1;
	}
	
	/**
	 *	Randomly selects an element from the array and swaps it with right most element before performing insertion sort.
	 * 	@param arr array of elements to be sorted
	 *	@param p left index of the array
	 *	@param r right index of the array
	 */
	private static int randomPartition(int[] arr, int p, int r) {
		Random random = new Random();
		int pos = random.nextInt(r - p + 1) + p;
		System.out.println(pos);
		swap(arr, pos, r);
		return partition(arr, p, r);
		
	}
	
	/**
	 *	Recursively splits the elements of the array till the threshold is hit
	 * 	@param arr array of elements
	 *	@param p left index of the array
	 *	@param r right index of the array A
	 *  @return q index of the split
	 */
	private static int quickSort(int[] arr, int p, int r) {
		if (r - p + 1 >= 10) {
			int q = randomPartition(arr, p, r);
			quickSort(arr, p, q - 1);
			quickSort(arr, q + 1, r);
			System.out.println(q);
			return q;
		}
		else {
			insertionSort(arr, p, r);
			return 0;
		}
	}
	
	/**
	 *	Invokes the quickSort with array of elements as the param.
	 * 	@param arr array of elements to be sorted
	 */
	private static void quickSort(int[] arr) {
		int t = quickSort(arr, 0, arr.length - 1);
	}
	

	/**
	 *	Given an array of elements, finds the k largest elements.
	 * 	@param arr array of elements to be sorted
	 *	@param p left index of the array
	 *	@param r right index of the array
	 *  @param k # of elements 
	 *  @return index of the kth largest element in the array.
	 */
    private static int select(int[] arr, int p, int n, int k) {
        int q = 0, right = 0, left = 0;

        q = partition(arr, p, p + n - 1);
        left = q - p;
        right = n - left - 1;
		
        if (right >= k) {
            return select(arr, q + 1, right, k);
        }
        else if (right + 1 == k) {
            return q;
        }
        else {
            return select(arr, p, left, k - right - 1);
        }
	}
	
	/**
	 *	Invokes the select with array of elements as the param.
	 * 	@param arr array of elements
	 *  @param k # of elements
	 *  @return index of kth largest element
	 */
    private static int select(int[] arr, int k) {
        return select(arr, 0, arr.length, k);
    }
	
	/**
	 *	Given an array of elements, constructs a max-heap and populates it with the elements. 
	 * 	@param arr array of elements to be sorted
	 *	@param p left index of the array
	 *	@param r right index of the array
	 *  @return result an array of k largest elements
	 */
	private static Integer[] populatePQ(Integer[] arr, int p, int q, int k) {
		int i, j;
		pq = new PriorityQueue<>(
			new Comparator<Integer>() {
			    public int compare(Integer lhs, Integer rhs) {
			        if (lhs < rhs) return +1;
			        if (lhs.equals(rhs)) return 0;
			        return -1;
			    }
			}
		);
		
		for (j = 0; j < q; j++) {
			pq.add(arr[j]);
		}
		Integer[] result = new Integer[k];
		for (j = 0; j < k; j++) {
			result[j] = pq.poll();
		}
		return result;
	}
	
	public static void main(String[] args) {
		// 512G: 536870912; 256G: 268435456; 128G: 33554432; 64G: 16777216; 32G: 8388608; 
		// 16G: 4194304; 8G:2097152; 4G: 1048576; 2G: 524288; 1G: 262144; 512M: 131072; 
		// 256M:65536; 128M: 32768; 64M:16384; 32M:8192; 16M: 4096; 8M: 2048;
		int numTrails = 1;
		int[] sizeArray = {
			536870912,
			268435456,
			134217728,
			67108864,
			33554432,
			16777216,
			8388608,
			4194304,
			2097152,
			1048576,
			524288,
			262144,
			131072,
			65536,
			32768,
			16384,
			8192			
		};
		
		int i, j, index = 0;
		int[] arr;
		Integer[] result, pqArray;


		System.out.println("O(n) Approach \n");
		
		for (i = 0; i < sizeArray.length; i++) {
			
			timer = new Timer();
			timer.start();
			arr = new int[sizeArray[i]];
			for (j = 1; j <= sizeArray[i]; j++) {
				arr[j - 1] = j;
			}
			for (j = 1; j <= numTrails; j++) {
				Shuffle.shuffle(arr);
			
				//quickSort(arr);
				index = select(arr, 20);
			}				
			timer.end();			
			timer.scale(numTrails);
			System.out.println(
				"# of elements: " + sizeArray[i] + "\n" +
					timer + "\n"				 
			);
			System.out.print("Elements: [");
			
			for (j = index; j < sizeArray[i]; j++) {
				System.out.print(arr[j] + ",");
			}
			System.out.println("]");
			System.out.println();
		}
		
		System.out.println("Priority Queue\n");
		
		for (i = 2; i < sizeArray.length; i++) {
			if (sizeArray[i] <= 536870912) {
				timer = new Timer();
				timer.start();
				pqArray = new Integer[sizeArray[i]];
				for (j = 1; j <= sizeArray[i]; j++) {
					pqArray[j - 1] = j;
				}
				Shuffle.shuffle(pqArray);
				result = populatePQ(pqArray, 0, pqArray.length, 20);		
				timer.end();

				System.out.println(
					"# of elements: " + sizeArray[i] + "\n" + 
					timer + "\n"				 
				);
				System.out.println("Elements:");
				System.out.println(Arrays.toString(result));
			}
			
		}
		

		
	}

}