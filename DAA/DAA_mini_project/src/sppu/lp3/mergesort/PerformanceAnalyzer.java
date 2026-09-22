package sppu.lp3.mergesort;

import java.util.ArrayList;
import java.util.List;

/**
 * PerformanceAnalyzer
 * 
 * Benchmarks Sequential and Multithreaded Merge Sort across Best, Worst,
 * and Average cases for multiple input sizes. Computes Speedup and Efficiency.
 * 
 * SPPU 2019 Pattern - LP3 (DAA)
 */
public class PerformanceAnalyzer {

    public static class BenchmarkResult {
        public final int size;
        public final ArrayGenerator.InputType type;
        public final double sequentialTimeMs;
        public final double multithreadedTimeMs;
        public final double speedup;
        public final double efficiency;
        public final boolean verifiedCorrect;

        public BenchmarkResult(int size, ArrayGenerator.InputType type,
                               double sequentialTimeMs, double multithreadedTimeMs,
                               int availableProcessors, boolean verifiedCorrect) {
            this.size = size;
            this.type = type;
            this.sequentialTimeMs = sequentialTimeMs;
            this.multithreadedTimeMs = multithreadedTimeMs;
            this.speedup = (multithreadedTimeMs > 0) ? sequentialTimeMs / multithreadedTimeMs : 1.0;
            this.efficiency = (this.speedup / availableProcessors) * 100.0;
            this.verifiedCorrect = verifiedCorrect;
        }
    }

    private static final int WARMUP_ITERATIONS = 3;
    private static final int BENCHMARK_TRIALS = 3;

    /**
     * Warms up the JVM JIT compiler with small tasks before measuring.
     */
    public static void warmup() {
        System.out.println(">> Warming up JVM JIT compiler to ensure accurate timings...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            int[] data1 = ArrayGenerator.generateRandom(50000);
            int[] data2 = ArrayGenerator.copy(data1);
            SequentialMergeSort.sort(data1);
            MultithreadedMergeSort.sort(data2);
        }
        System.out.println(">> Warmup completed.\n");
    }

    /**
     * Measures the execution time for a single sort operation averaged over trials.
     */
    public static BenchmarkResult runSingleBenchmark(int size, ArrayGenerator.InputType type) {
        int processors = Runtime.getRuntime().availableProcessors();
        double totalSeqTimeNs = 0;
        double totalParTimeNs = 0;
        boolean allCorrect = true;

        for (int trial = 0; trial < BENCHMARK_TRIALS; trial++) {
            int[] original = ArrayGenerator.generate(size, type);
            int[] seqCopy = ArrayGenerator.copy(original);
            int[] parCopy = ArrayGenerator.copy(original);

            // Time Sequential Sort
            long startSeq = System.nanoTime();
            SequentialMergeSort.sort(seqCopy);
            long endSeq = System.nanoTime();
            totalSeqTimeNs += (endSeq - startSeq);

            // Time Multithreaded Sort
            long startPar = System.nanoTime();
            MultithreadedMergeSort.sort(parCopy);
            long endPar = System.nanoTime();
            totalParTimeNs += (endPar - startPar);

            // Verify correctness
            if (!SequentialMergeSort.isSorted(seqCopy) || !SequentialMergeSort.isSorted(parCopy)) {
                allCorrect = false;
            }
        }

        double avgSeqMs = (totalSeqTimeNs / BENCHMARK_TRIALS) / 1_000_000.0;
        double avgParMs = (totalParTimeNs / BENCHMARK_TRIALS) / 1_000_000.0;

        return new BenchmarkResult(size, type, avgSeqMs, avgParMs, processors, allCorrect);
    }

    /**
     * Runs standard benchmark across predefined sizes for Best, Average, and Worst cases.
     */
    public static List<BenchmarkResult> runComprehensiveBenchmark(int[] sizes) {
        warmup();
        List<BenchmarkResult> results = new ArrayList<>();

        ArrayGenerator.InputType[] types = {
            ArrayGenerator.InputType.BEST_CASE,
            ArrayGenerator.InputType.AVERAGE_CASE,
            ArrayGenerator.InputType.WORST_CASE
        };

        for (ArrayGenerator.InputType type : types) {
            System.out.println("==========================================================================");
            System.out.println(" EVALUATING: " + type.getDisplayName().toUpperCase());
            System.out.println("==========================================================================");

            for (int size : sizes) {
                System.out.printf("Testing size: %,d elements... ", size);
                BenchmarkResult res = runSingleBenchmark(size, type);
                results.add(res);
                System.out.printf("Done. [Seq: %.2f ms | Multi: %.2f ms | Speedup: %.2fx]\n",
                        res.sequentialTimeMs, res.multithreadedTimeMs, res.speedup);
            }
            System.out.println();
        }

        return results;
    }

    /**
     * Prints a formatted tabular report of benchmark results.
     */
    public static void printReport(List<BenchmarkResult> results) {
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("\n" + "=".repeat(95));
        System.out.println("          SPPU LP3: MERGE SORT VS MULTITHREADED MERGE SORT PERFORMANCE REPORT");
        System.out.printf("          Detected CPU Cores: %d | JRE: %s\n",
                processors, System.getProperty("java.version"));
        System.out.println("=".repeat(95));

        System.out.printf("%-12s | %-26s | %-12s | %-12s | %-8s | %-10s%n",
                "Size (N)", "Input Case", "Seq Time(ms)", "Multi Time", "Speedup", "Efficiency");
        System.out.println("-".repeat(95));

        for (BenchmarkResult r : results) {
            System.out.printf("%-12s | %-26s | %10.2f ms | %8.2f ms | %6.2fx | %8.2f%%%n",
                    String.format("%,d", r.size),
                    r.type.getDisplayName(),
                    r.sequentialTimeMs,
                    r.multithreadedTimeMs,
                    r.speedup,
                    r.efficiency);
        }
        System.out.println("=".repeat(95));
    }
}
