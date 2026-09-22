import java.util.*;

// 0-1 Knapsack — Dynamic Programming & Branch and Bound
// DP: O(n*W) time, O(n*W) space | B&B: O(2^n) worst case
public class DAA_4 {

    // ==================== DYNAMIC PROGRAMMING ====================
    static int knapsackDP(int[] wt, int[] val, int W, int n) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++)
            for (int w = 0; w <= W; w++) {
                dp[i][w] = dp[i - 1][w];
                if (wt[i - 1] <= w)
                    dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - wt[i - 1]] + val[i - 1]);
            }

        // Backtrack to find selected items
        int w = W;
        System.out.print("  Selected items: ");
        for (int i = n; i > 0; i--)
            if (dp[i][w] != dp[i - 1][w]) { System.out.print(i + " "); w -= wt[i - 1]; }
        System.out.println();

        return dp[n][W];
    }

    // ==================== BRANCH AND BOUND ====================
    static int knapsackBB(int[] wt, int[] val, int W, int n) {
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> Double.compare((double) val[b] / wt[b], (double) val[a] / wt[a]));

        int[] sw = new int[n], sv = new int[n];
        for (int i = 0; i < n; i++) { sw[i] = wt[idx[i]]; sv[i] = val[idx[i]]; }

        // node: {level, profit, weight, bound}
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[3], a[3]));
        pq.add(new double[]{0, 0, 0, bound(0, 0, 0, sw, sv, W, n)});
        int maxProfit = 0;

        while (!pq.isEmpty()) {
            double[] cur = pq.poll();
            int lv = (int) cur[0], cp = (int) cur[1], cw = (int) cur[2];
            if (cur[3] <= maxProfit || lv >= n) continue;

            // Include item
            int nw = cw + sw[lv], np = cp + sv[lv];
            if (nw <= W) maxProfit = Math.max(maxProfit, np);
            double b1 = bound(lv + 1, np, nw, sw, sv, W, n);
            if (b1 > maxProfit && nw <= W) pq.add(new double[]{lv + 1, np, nw, b1});

            // Exclude item
            double b2 = bound(lv + 1, cp, cw, sw, sv, W, n);
            if (b2 > maxProfit) pq.add(new double[]{lv + 1, cp, cw, b2});
        }
        return maxProfit;
    }

    static double bound(int lv, int cp, int cw, int[] sw, int[] sv, int W, int n) {
        if (cw > W) return 0;
        double b = cp;
        int w = cw;
        for (int i = lv; i < n; i++) {
            if (w + sw[i] <= W) { w += sw[i]; b += sv[i]; }
            else { b += (double)(W - w) * sv[i] / sw[i]; break; }
        }
        return b;
    }

    // ==================== MAIN ====================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        int n = sc.nextInt();
        int[] wt = new int[n], val = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Item " + (i+1) + " weight & profit: ");
            wt[i] = sc.nextInt(); val[i] = sc.nextInt();
        }
        System.out.print("Enter capacity: ");
        int W = sc.nextInt();

        System.out.println("\n--- Dynamic Programming ---");
        System.out.println("  Max Profit (DP): " + knapsackDP(wt, val, W, n));

        System.out.println("\n--- Branch and Bound ---");
        System.out.println("  Max Profit (B&B): " + knapsackBB(wt, val, W, n));
    }
}
