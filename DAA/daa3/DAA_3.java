import java.util.*;

// Fractional Knapsack — Greedy Method
// Time: O(n log n)  Space: O(n)
public class DAA_3 {

    static double fractionalKnapsack(double[] wt, double[] val, double capacity, int n) {
        // Create index array and sort by value/weight ratio (descending)
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Double.compare(val[b] / wt[b], val[a] / wt[a]));

        double totalProfit = 0, rem = capacity;
        System.out.println("\nItem\tWeight\tProfit\tFraction\tEarned");

        for (int i : idx) {
            if (rem <= 0) break;
            if (wt[i] <= rem) {
                totalProfit += val[i];
                rem -= wt[i];
                System.out.printf("%d\t%.1f\t%.1f\t1.0 (Full)\t%.2f\n", i + 1, wt[i], val[i], val[i]);
            } else {
                double frac = rem / wt[i];
                double earned = val[i] * frac;
                totalProfit += earned;
                rem = 0;
                System.out.printf("%d\t%.1f\t%.1f\t%.4f\t\t%.2f\n", i + 1, wt[i], val[i], frac, earned);
            }
        }
        return totalProfit;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        double[] wt = new double[n], val = new double[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Item " + (i + 1) + " weight & profit: ");
            wt[i] = sc.nextDouble(); val[i] = sc.nextDouble();
        }
        System.out.print("Enter capacity: ");
        double capacity = sc.nextDouble();

        double maxProfit = fractionalKnapsack(wt, val, capacity, n);
        System.out.printf("\nMaximum Profit = %.2f\n", maxProfit);
    }
}
