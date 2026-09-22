package sppu.lp3.mergesort;

import java.util.Random;

/**
 * ArrayGenerator
 * 
 * Generates datasets for testing sorting algorithms under different cases:
 * - BEST CASE: Already sorted in ascending order.
 * - WORST CASE: Reverse sorted (descending order) and adversarial interleaved patterns.
 * - AVERAGE CASE: Uniform random distribution.
 * 
 * SPPU 2019 Pattern - LP3 (DAA)
 */
public class ArrayGenerator {

    public enum InputType {
        BEST_CASE("Best Case (Already Sorted)"),
        WORST_CASE("Worst Case (Reverse Sorted)"),
        AVERAGE_CASE("Average Case (Random Uniform)");

        private final String displayName;

        InputType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private static final Random RANDOM = new Random(42); // Deterministic seed for reproducible benchmarks

    /**
     * Generates an array according to the specified InputType.
     *
     * @param size number of elements
     * @param type Best, Worst, or Average case
     * @return generated integer array
     */
    public static int[] generate(int size, InputType type) {
        switch (type) {
            case BEST_CASE:
                return generateSorted(size);
            case WORST_CASE:
                return generateReverseSorted(size);
            case AVERAGE_CASE:
            default:
                return generateRandom(size);
        }
    }

    /**
     * Best Case: Already sorted array [0, 1, 2, ..., size - 1].
     */
    public static int[] generateSorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }

    /**
     * Worst Case: Strictly descending array [size, size - 1, ..., 1].
     */
    public static int[] generateReverseSorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }

    /**
     * Average Case: Randomly distributed array between 0 and size * 10.
     */
    public static int[] generateRandom(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = RANDOM.nextInt(size * 10);
        }
        return arr;
    }

    /**
     * Generates worst-case merge sort array where subarrays alternate maximally.
     * (Adversarial permutation maximizing comparisons).
     */
    public static int[] generateAdversarialWorstCase(int size) {
        int[] arr = generateSorted(size);
        generateWorstCaseHelper(arr, 0, size - 1);
        return arr;
    }

    private static void generateWorstCaseHelper(int[] arr, int low, int high) {
        if (high - low <= 0) {
            return;
        }
        if (high - low == 1) {
            int temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;
            return;
        }

        int mid = low + (high - low) / 2;
        int[] left = new int[mid - low + 1];
        int[] right = new int[high - mid];

        // Separate even and odd indices
        for (int i = 0, j = low; j <= high; i++, j += 2) {
            left[i] = arr[j];
        }
        for (int i = 0, j = low + 1; j <= high; i++, j += 2) {
            right[i] = arr[j];
        }

        generateWorstCaseHelper(left, 0, left.length - 1);
        generateWorstCaseHelper(right, 0, right.length - 1);

        System.arraycopy(left, 0, arr, low, left.length);
        System.arraycopy(right, 0, arr, low + left.length, right.length);
    }

    /**
     * Creates a deep copy of an array.
     */
    public static int[] copy(int[] source) {
        if (source == null) return null;
        int[] destination = new int[source.length];
        System.arraycopy(source, 0, destination, 0, source.length);
        return destination;
    }
}
