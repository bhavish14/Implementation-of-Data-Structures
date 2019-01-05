import java.util.*;


import bxn170002.shuffle.Shuffle;
import bxn170002.timer.Timer;

public class KLargest {
	
	private static void swap(Integer[] arr, int index1, int index2) {
		int temp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = temp;
	}
	
	
	private static void insertionSort(Integer arr[], int p, int r) {
		Integer temp= new Integer(0);
		int j=0;
		for(int i=p+1;i<r+1;i++)
		{
			temp = arr[i];
			j=i-1;
			while(j>=p && temp.compareTo(arr[j]) == -1)
			{
				arr[j+1] = arr[j];
				j=j-1;
			}
			arr[j+1] = temp;
		}
	}
	
	
	private static int partition(Integer[] arr, int p, int r) {
		Integer x = arr[r];
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			//if (arr[j] < x) {
			if (arr[j].compareTo(x) == -1){					
				swap(arr, j, ++i);				
			}
		}
		swap(arr, i + 1, r);
		return i + 1;
	}
	
	private static int randomPartition(Integer[] arr, int p, int r) {
		Random random = new Random();
		int pos = random.nextInt(r - p + 1) + p;
		System.out.println(pos);
		swap(arr, pos, r);
		return partition(arr, p, r);
		
	}
	
	private static int quickSort(Integer[] arr, int p, int r) {
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
	
	private static void quickSort(Integer[] arr) {
		int t = quickSort(arr, 0, arr.length - 1);
		System.out.print("T: " + t);
	}
	

    private static int select(Integer[] arr, int p, int n, int k, int c) {
        int q = 0, right = 0, left = 0;

        q = partition(arr, p, p + n - 1);
        left = q - p;
        right = n - left - 1;
		
        if (right >= k) {
            return select(arr, q + 1, right, k, ++c);
        }
        else if (right + 1 == k) {
            return q;
        }
        else {
            return select(arr, p, left, k - right - 1, ++c);
        }
	}
	
    private static int select(Integer[] arr, int k) {
        return select(arr, 0, arr.length, k, 0);
    }
	
	public static void main(String[] args) {
		
		// 16T: 4294967296; 8T: 2147483648; 4T: 1073741824; 2T: 536870912; 1T: 268435456; 
		// 512G: 134217728; 256G: 67108864; 128G: 134217728; 64G: 67108864; 32G: 33554432; 
		// 16G: 16777216; 8G:8388608; 4G: 4194304; 2G: 2097152; 1G: 1048576; 512M: 524288; 
		// 256M:262144; 128M: 131072; 64M:65536; 32M:32768; 16M: 16384; 8M: 8192;
		
		// 512G: 536870912; 256G: 268435456; 128G: 33554432; 64G: 16777216; 32G: 8388608; 
		// 16G: 4194304; 8G:2097152; 4G: 1048576; 2G: 524288; 1G: 262144; 512M: 131072; 
		// 256M:65536; 128M: 32768; 64M:16384; 32M:8192; 16M: 4096; 8M: 2048;
		
		/*
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
		*/
		int[] sizeArray = {
			//1073741824,
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
		
		int i, j;
		Integer[] arr;
		
		for (i = 0; i < sizeArray.length; i++) {
			arr = new Integer[sizeArray[i]];
			for (j = 1; j <= sizeArray[i]; j++) {
				arr[j - 1] = new Integer(j);
			}
			Shuffle.shuffle(arr);
			Timer timer = new Timer();
			timer.start();
			//quickSort(arr);
			Integer index = select(arr, 5);
			timer.end();
			
			
			
			System.out.println(
				"# of elements: " + sizeArray[i] + "\n" +
					timer + "\n"				 
			);

			PriorityQueue<Integer> pq = new PriorityQueue<>();
			
			


			/*
			System.out.println("Size: " + sizeArray[i]);
			for (j = index; j < arr.length; j++) {
				System.out.print(arr[j] + " ");
			}
			*/

			System.out.println();
		}
		
	}

}