package sppu.lp3.mergesort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * MultithreadedMergeSort
 * 
 * Multithreaded implementation of Merge Sort using Java's ForkJoin framework (RecursiveAction).
 * Uses a work-stealing pool to execute parallel divide-and-conquer.
 * SPPU 2019 Pattern - LP3 (DAA)
 */
public class MultithreadedMergeSort {

    /**
     * Threshold below which the array is sorted sequentially.
     * Prevents excessive thread-creation and scheduling overhead on small partitions.
     */
    public static final int DEFAULT_THRESHOLD = 5000;

    /**
     * Internal RecursiveAction for parallel merge sort.
     */
    private static class MergeSortAction extends RecursiveAction {
        private final int[] arr;
        private final int[] aux;
        private final int low;
        private final int high;
        private final int threshold;

        public MergeSortAction(int[] arr, int[] aux, int low, int high, int threshold) {
            this.arr = arr;
            this.aux = aux;
            this.low = low;
            this.high = high;
            this.threshold = threshold;
        }

        @Override
        protected void compute() {
            int length = high - low + 1;

            // Base case or sequential threshold cutoff
            if (length <= threshold) {
                sequentialSort(arr, aux, low, high);
                return;
            }

            int mid = low + (high - low) / 2;

            // Fork two parallel subtasks for left and right halves
            MergeSortAction leftTask = new MergeSortAction(arr, aux, low, mid, threshold);
            MergeSortAction rightTask = new MergeSortAction(arr, aux, mid + 1, high, threshold);

            // Execute tasks in parallel using ForkJoin framework
            invokeAll(leftTask, rightTask);

            // Merge the two halves
            SequentialMergeSort.merge(arr, aux, low, mid, high);
        }

        /**
         * Sequential fallback sort when partition size <= threshold.
         */
        private void sequentialSort(int[] arr, int[] aux, int low, int high) {
            if (low >= high) {
                return;
            }
            int mid = low + (high - low) / 2;
            sequentialSort(arr, aux, low, mid);
            sequentialSort(arr, aux, mid + 1, high);
            SequentialMergeSort.merge(arr, aux, low, mid, high);
        }
    }

    /**
     * Sorts the array using the default ForkJoinPool and default threshold.
     *
     * @param arr the array to be sorted
     */
    public static void sort(int[] arr) {
        sort(arr, DEFAULT_THRESHOLD, ForkJoinPool.commonPool());
    }

    /**
     * Sorts the array with a specified threshold.
     *
     * @param arr       the array to be sorted
     * @param threshold partition cutoff size for switching to sequential sort
     */
    public static void sort(int[] arr, int threshold) {
        sort(arr, threshold, ForkJoinPool.commonPool());
    }

    /**
     * Sorts the array using a specific ForkJoinPool and threshold.
     *
     * @param arr       the array to be sorted
     * @param threshold partition cutoff size
     * @param pool      ForkJoinPool instance to execute within
     */
    public static void sort(int[] arr, int threshold, ForkJoinPool pool) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] aux = new int[arr.length];
        MergeSortAction mainTask = new MergeSortAction(arr, aux, 0, arr.length - 1, threshold);
        pool.invoke(mainTask);
    }
}
