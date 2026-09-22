import java.util.Scanner;

// N-Queens using Backtracking — first queen pre-placed
// Time: O(n!)  Space: O(n^2)
public class DAA_5 {
    static int n;
    static int[][] board;
    static int fRow, fCol;

    static boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++)
            if (board[i][col] == 1) return false;
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1) return false;
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++)
            if (board[i][j] == 1) return false;
        return true;
    }

    static boolean solve(int row) {
        if (row == n) { printBoard(); return true; }
        if (row == fRow) return solve(row + 1);

        for (int col = 0; col < n; col++) {
            if (isSafe(row, col)) {
                board[row][col] = 1;
                if (solve(row + 1)) return true;
                board[row][col] = 0;
            }
        }
        return false;
    }

    static void printBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(board[i][j] == 1 ? " Q" : " .");
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter N (board size): ");
        n = sc.nextInt();
        board = new int[n][n];

        System.out.print("Enter first queen row (1-" + n + "): ");
        fRow = sc.nextInt() - 1;
        System.out.print("Enter first queen col (1-" + n + "): ");
        fCol = sc.nextInt() - 1;

        board[fRow][fCol] = 1;
        System.out.println("\nFirst queen placed at (" + (fRow+1) + "," + (fCol+1) + ")\n");

        if (!solve(0))
            System.out.println("No solution exists!");
        sc.close();
        }
}
