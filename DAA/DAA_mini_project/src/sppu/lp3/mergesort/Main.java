package sppu.lp3.mergesort;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Main
 * 
 * Interactive Console Application for Merge Sort & Multithreaded Merge Sort.
 * SPPU 2019 Pattern - BE Computer Engineering (Course: LP3 - DAA)
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();

        boolean exit = false;
        while (!exit) {
            printMenu();
            System.out.print("Enter your choice (1-5): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    handleFullBenchmark();
                    break;
                case "2":
                    handleCustomBenchmark();
                    break;
                case "3":
                    handleInteractiveDemo();
                    break;
                case "4":
                    displayTheoryAndComplexity();
                    break;
                case "5":
                    System.out.println("\nExiting program. Thank you!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please select an option between 1 and 5.\n");
            }
        }
    }

    private static void printBanner() {
        System.out.println("=".repeat(78));
        System.out.println("   SAVITRIBAI PHULE PUNE UNIVERSITY (SPPU) - 2019 PATTERN");
        System.out.println("   BE Computer Engineering | Laboratory Practice III (LP3 - DAA)");
        System.out.println("   Assignment: Merge Sort vs Multithreaded Merge Sort Performance Analysis");
        System.out.println("=".repeat(78));
    }

    private static void printMenu() {
        System.out.println("\n----------------------------- MAIN MENU -----------------------------");
        System.out.println(" 1. Run Comprehensive Benchmark (Best, Average, Worst across N=10K to 1M)");
        System.out.println(" 2. Run Custom Benchmark (Specify custom size & case)");
        System.out.println(" 3. Interactive Correctness Demo (Visual sorting of sample array)");
        System.out.println(" 4. View Theoretical Complexity Analysis & Viva Notes");
        System.out.println(" 5. Exit");
        System.out.println("---------------------------------------------------------------------");
    }

    private static void handleFullBenchmark() {
        System.out.println("\n[INFO] Starting Comprehensive SPPU LP3 Benchmark Suite...");
        int[] benchmarkSizes = {10_000, 50_000, 100_000, 500_000, 1_000_000};
        List<PerformanceAnalyzer.BenchmarkResult> results = 
                PerformanceAnalyzer.runComprehensiveBenchmark(benchmarkSizes);
        PerformanceAnalyzer.printReport(results);
    }

    private static void handleCustomBenchmark() {
        try {
            System.out.print("\nEnter array size N (e.g., 200000): ");
            int size = Integer.parseInt(scanner.nextLine().trim());
            if (size <= 0) {
                System.out.println("[ERROR] Size must be a positive integer!");
                return;
            }

            System.out.println("Select Input Case:");
            System.out.println(" 1. Best Case (Already Sorted)");
            System.out.println(" 2. Average Case (Random)");
            System.out.println(" 3. Worst Case (Reverse Sorted)");
            System.out.print("Choice (1-3): ");
            String caseChoice = scanner.nextLine().trim();

            ArrayGenerator.InputType type;
            switch (caseChoice) {
                case "1":
                    type = ArrayGenerator.InputType.BEST_CASE;
                    break;
                case "3":
                    type = ArrayGenerator.InputType.WORST_CASE;
                    break;
                default:
                    type = ArrayGenerator.InputType.AVERAGE_CASE;
                    break;
            }

            System.out.printf("\nExecuting benchmark for N = %,d [%s]...\n", size, type.getDisplayName());
            PerformanceAnalyzer.BenchmarkResult res = PerformanceAnalyzer.runSingleBenchmark(size, type);

            System.out.println("\n" + "-".repeat(50));
            System.out.println("              BENCHMARK RESULT");
            System.out.println("-".repeat(50));
            System.out.printf(" Array Size           : %,d\n", res.size);
            System.out.printf(" Case Type            : %s\n", res.type.getDisplayName());
            System.out.printf(" Sequential Time      : %.3f ms\n", res.sequentialTimeMs);
            System.out.printf(" Multithreaded Time   : %.3f ms\n", res.multithreadedTimeMs);
            System.out.printf(" Speedup Factor (S)   : %.2fx\n", res.speedup);
            System.out.printf(" Parallel Efficiency  : %.2f%%\n", res.efficiency);
            System.out.printf(" Correctness Verified : %s\n", res.verifiedCorrect ? "PASSED (Sorted)" : "FAILED");
            System.out.println("-".repeat(50));

        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid number format entered.");
        }
    }

    private static void handleInteractiveDemo() {
        System.out.println("\n--- Interactive Sorting Demo ---");
        System.out.println("Enter comma-separated integers (or press Enter for default sample [38, 27, 43, 3, 9, 82, 10, 15, 64, 1]):");
        String line = scanner.nextLine().trim();

        int[] original;
        if (line.isEmpty()) {
            original = new int[]{38, 27, 43, 3, 9, 82, 10, 15, 64, 1};
        } else {
            try {
                String[] parts = line.split("[,\\s]+");
                original = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    original[i] = Integer.parseInt(parts[i].trim());
                }
            } catch (Exception e) {
                System.out.println("[ERROR] Invalid array format. Using default sample.");
                original = new int[]{38, 27, 43, 3, 9, 82, 10, 15, 64, 1};
            }
        }

        System.out.println("\nOriginal Input Array: " + Arrays.toString(original));

        int[] seqArr = ArrayGenerator.copy(original);
        SequentialMergeSort.sort(seqArr);
        System.out.println("Sorted by Sequential Merge Sort   : " + Arrays.toString(seqArr));
        System.out.println("  -> Is correctly sorted: " + SequentialMergeSort.isSorted(seqArr));

        int[] parArr = ArrayGenerator.copy(original);
        // Using threshold 1 so demo triggers multithreading even for small array
        MultithreadedMergeSort.sort(parArr, 1);
        System.out.println("Sorted by Multithreaded Merge Sort: " + Arrays.toString(parArr));
        System.out.println("  -> Is correctly sorted: " + SequentialMergeSort.isSorted(parArr));
    }

    private static void displayTheoryAndComplexity() {
        System.out.println("\n" + "=".repeat(78));
        System.out.println("       SPPU LP3: THEORETICAL ANALYSIS & TIME COMPLEXITY SUMMARY");
        System.out.println("=".repeat(78));
        System.out.println(" 1. Sequential Merge Sort Recurrence:");
        System.out.println("    T(n) = 2T(n/2) + Theta(n)");
        System.out.println("    By Master Theorem (Case 2: a = 2, b = 2, f(n) = Theta(n)):");
        System.out.println("    Time Complexity: Theta(n * log2(n)) across Best, Average, and Worst cases.");
        System.out.println();
        System.out.println(" 2. Best Case vs Worst Case in Merge Sort:");
        System.out.println("    - Best Case (Already Sorted): With boundary check (arr[mid] <= arr[mid+1]),");
        System.out.println("      it requires minimal merge operations: O(n) or ~ (n/2)*log(n) comparisons.");
        System.out.println("    - Worst Case (Reverse Sorted / Interleaved): Requires the maximum");
        System.out.println("      number of comparisons in every merge: n*log2(n) - n + 1 comparisons.");
        System.out.println();
        System.out.println(" 3. Multithreaded Merge Sort (Work-Span Model):");
        System.out.println("    - Total Work W(n) = O(n * log(n))");
        System.out.println("    - Span (Critical Path) T_inf(n) = T_inf(n/2) + Theta(n) = O(n)");
        System.out.println("      (or O(log^2 n) if parallel merge is employed).");
        System.out.println("    - Speedup on P processors: S_p = T_1 / T_p <= P (limited by Amdahl's Law).");
        System.out.println("    - Overhead threshold cutoff is vital to prevent thread thrashing.");
        System.out.println("=".repeat(78));
    }
}
