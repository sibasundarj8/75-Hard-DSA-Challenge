/*
 *
 * https://leetcode.com/problems/minimum-falling-path-sum-ii/
 *
 * # 1289. Minimum Falling Path Sum II
 *
 *   Q. Given an n x n integer matrix grid, return the minimum sum of a falling path with non-zero shifts.
 *      A falling path with non-zero shifts is a choice of exactly one element from each row of grid such that no two
 *      elements chosen in adjacent rows are in the same column.
 *    Ex.
 *      Input : grid = [[<1>,  2,  3],
 *                      [ 4,  <5>, 6],
 *                      [<7>,  8,  9]]
 *      Output: 13
 *      Explanation:
 *              The possible falling paths are:
 *              [1,5,9], [1,5,7], [1,6,7], [1,6,8],
 *              [2,4,8], [2,4,9], [2,6,7], [2,6,8],
 *              [3,4,8], [3,4,9], [3,5,7], [3,5,9]
 *              The falling path with the smallest sum is [1,5,7], so the answer is 13.
 *
 *  Constraints:
 *      n == grid.length == grid[i].length
 *      1 <= n <= 200
 *      -99 <= grid[i][j] <= 99
 */

import java.util.Scanner;

public class Day_12_LC_1289 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the size of squire matrix: ");
        int n = sc.nextInt();

        int[][] matrix = new int[n][n];

        System.out.println("Enter the elements of the matrix: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        System.out.println("Minimum falling path sum: " + minFallingPathSum(matrix));
    }

    /// Solution
/*
✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘-Brute-Force-✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘
TC : O(nⁿ)
SC : O(1)
*/
    static int bruteForce(int[][] grid) {
        int n = grid.length;
        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, bfRec(1, i, n, grid) + grid[0][i]);
        }

        return ans;
    }

    private static int bfRec(int r, int ex, int n, int[][] mat) {
        if (r == n) return 0;

        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (i != ex) {
                ans = Math.min(ans, bfRec(r + 1, i, n, mat) + mat[r][i]);
            }
        }

        return ans;
    }

/*
✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓-Memoization-✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓
TC : O(n³)
SC : O(n²)
*/
    static int memoization(int[][] grid) {
        int n = grid.length;
        int ans = Integer.MAX_VALUE;
        Integer[][] dp = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, rec(1, i, n, grid, dp) + grid[0][i]);
        }

        return ans;
    }

    private static int rec(int r, int ex, int n, int[][] mat, Integer[][] dp) {
        if (r == n) return 0;
        if (dp[r][ex] != null) return dp[r][ex];

        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (i != ex) {
                ans = Math.min(ans, rec(r + 1, i, n, mat, dp) + mat[r][i]);
            }
        }

        return dp[r][ex] = ans;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Two-minimums-technique-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n²)
SC : O(1)
*/
    public static int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int firstMin = 0;
        int secondMin = 0;
        int firstMinIdx = -1;

        for (int[] arr : grid) {

            int newFirstMin = Integer.MAX_VALUE;
            int newSecondMin = Integer.MAX_VALUE;
            int newFirstMinIdx = -1;

            for (int i = 0; i < n; i++) {
                int val = arr[i] + ((firstMinIdx == i) ? secondMin : firstMin);
                if (val < newFirstMin) {
                    newSecondMin = newFirstMin;
                    newFirstMin = val;
                    newFirstMinIdx = i;
                } else if (val < newSecondMin) {
                    newSecondMin = val;
                }
            }

            firstMin = newFirstMin;
            secondMin = newSecondMin;
            firstMinIdx = newFirstMinIdx;
        }

        return firstMin;
    }
}