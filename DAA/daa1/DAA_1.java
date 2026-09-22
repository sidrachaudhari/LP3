import java.util.Scanner;

// Fibonacci — Iterative O(n) & Recursive O(2^n)
public class DAA_1 {

    static long fibIterative(int n) {
        if (n <= 1) return n;
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) { long t = a + b; a = b; b = t; }
        return b;
    }

    static long fibRecursive(int n) {
        if (n <= 1) return n;
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter n: ");
        int n = sc.nextInt();

        // Iterative
        long start1 = System.nanoTime();
        long r1 = fibIterative(n);
        long t1 = System.nanoTime() - start1;

        // Recursive
        long start2 = System.nanoTime();
        long r2 = fibRecursive(n);
        long t2 = System.nanoTime() - start2;

        System.out.println("\nIterative: F(" + n + ") = " + r1 + "  Time: " + t1/1_000_000.0 + " ms");
        System.out.println("Recursive: F(" + n + ") = " + r2 + "  Time: " + t2/1_000_000.0 + " ms");

        // Print series
        System.out.print("\nSeries: ");
        for (int i = 0; i <= n; i++) System.out.print(fibIterative(i) + " ");
        System.out.println();
    }
}
