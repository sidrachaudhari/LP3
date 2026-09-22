package sppu.lp3.mergesort;

/**
 * SequentialMergeSort
 * 
 * Standard recursive implementation of Merge Sort using Divide and Conquer.
 * SPPU 2019 Pattern - LP3 (DAA)
 */
public class SequentialMergeSort {

    /**
     * Sorts the input array using sequential merge sort.
     *
     * @param arr the array to be sorted
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] aux = new int[arr.length];
        mergeSort(arr, aux, 0, arr.length - 1);
    }

    /**
     * Recursive helper for merge sort.
     *
     * @param arr  the original array
     * @param aux  auxiliary array for merging
     * @param low  starting index
     * @param high ending index
     */
    private static void mergeSort(int[] arr, int[] aux, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;

        // Divide: recursively sort left and right halves
        mergeSort(arr, aux, low, mid);
        mergeSort(arr, aux, mid + 1, high);

        // Conquer: merge the two sorted halves
        merge(arr, aux, low, mid, high);
    }

    /**
     * Merges two sorted subarrays arr[low..mid] and arr[mid+1..high].
     *
     * @param arr  the array containing subarrays
     * @param aux  auxiliary buffer array
     * @param low  start index
     * @param mid  middle index
     * @param high end index
     */
    public static void merge(int[] arr, int[] aux, int low, int mid, int high) {
        // Optimization: if already sorted across the boundary, skip merge
        if (arr[mid] <= arr[mid + 1]) {
            return;
        }

        // Copy elements to auxiliary array
        for (int k = low; k <= high; k++) {
            aux[k] = arr[k];
        }

        int i = low;
        int j = mid + 1;

        // Merge back into original array arr
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                arr[k] = aux[j++];
            } else if (j > high) {
                arr[k] = aux[i++];
            } else if (aux[j] < aux[i]) {
                arr[k] = aux[j++];
            } else {
                arr[k] = aux[i++];
            }
        }
    }

    /**
     * Utility method to verify if an array is sorted in ascending order.
     *
     * @param arr the array to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
