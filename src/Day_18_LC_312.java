/*
 *
 * https://leetcode.com/problems/burst-balloons
 *
 * # 312. Burst Balloons
 *
 *   Q. You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an
 *      array nums. You are asked to burst all the balloons.
 *
 *      If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out
 *      of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.
 *
 *      Return the maximum coins you can collect by bursting the balloons wisely.
 *
 *    Ex.
 *      Input : nums = [3, 1, 5, 8]
 *      Output: 167
 *      Explanation:
 *              nums = [3,1,5,8] --> [3,5,8] --> [3,8] --> [8] --> []
 *              coins =  3*1*5    +   3*5*8   +  1*3*8  + 1*8*1 = 167
 *
 *  Constraints:
 *          n == nums.length
 *          1 <= n <= 300
 *          0 <= nums[i] <= 100
 */

import java.util.Arrays;
import java.util.Scanner;

public class Day_18_LC_312 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter balloons : ");
        String[] s = sc.nextLine().split(" ");

        int n = s.length;
        int[] balloons = new int[n];
        for (int i = 0; i < n; i++) {
            balloons[i] = Integer.parseInt(s[i]);
        }

        System.out.println("Maximum coin you can collect: " + maxCoins(balloons));
    }

    /// Solution
/*
✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘-Bruteforce-Approach-✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘
TC : O(n! * n)
SC : O(n)
*/
    static int bruteForce(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n];

        return rec(nums, visited, n);
    }

    private static int rec(int[] nums, boolean[] visited, int n) {
        int max = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;

                int[] leftRight = getLeftRight(i, nums, visited);
                int coins = rec(nums, visited, n) + leftRight[0] * nums[i] * leftRight[1];
                max = Math.max(max, coins);

                visited[i] = false;
            }
        }

        return max;
    }

    private static int[] getLeftRight(int currIndex, int[] nums, boolean[] visited) {
        int n = nums.length;
        int i = currIndex - 1;
        int j = currIndex + 1;

        while (i >= 0 || j < n) {
            boolean left = i < 0 || !visited[i];
            boolean right = j >= n || !visited[j];

            if (left && right) break;

            if (i >= 0 && visited[i]) i--;
            if (j < n && visited[j]) j++;
        }

        int[] ans =  new int[] {
                (i >= 0 && !visited[i]) ? nums[i] : 1,
                (j < n && !visited[j]) ? nums[j] : 1
        };

        return ans;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Interval-DP--with--Partition-Transition--✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n³)
SC : O(n²) + extra recursive call stack
*/
    static int memoization(int[] nums) {
        int n = nums.length;
        int m = n + 2;
        int[] temp = new int[m];
        int[][] dp = new int[m][m];

        temp[0] = temp[n + 1] = 1;
        System.arraycopy(nums, 0, temp, 1, n);

        for (int[] d : dp) {
            Arrays.fill(d, -1);
        }

        return rec(0, m - 1, temp, dp);
    }

    private static int rec(int l, int r, int[] nums, int[][]dp) {
        // base case
        if (l > r) return 0;
        if (dp[l][r] != -1) return dp[l][r];

        // recursive work
        int max = 0;

        for (int k = l + 1; k < r; k++) {
            int left = rec(l, k, nums, dp);
            int right = rec(k, r, nums, dp);
            max = Math.max(max, left + right + nums[l] * nums[k] * nums[r]);
        }

        return dp[l][r] = max;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Tabulation-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n³)
SC : O(n²)
*/
    static int maxCoins(int[] nums) {
        int n = nums.length;
        int m = n + 2;
        int[] temp = new int[m];
        int[][] dp = new int[m][m];

        temp[0] = temp[n + 1] = 1;
        System.arraycopy(nums, 0, temp, 1, n);

        for (int l = m - 1; l >= 0; l--) {
            for (int r = l + 1; r < m; r++) {

                int max = 0;

                for (int k = l + 1; k < r; k++) {
                    int left = dp[l][k];
                    int right = dp[k][r];
                    max = Math.max(max, left + right + temp[l] * temp[k] * temp[r]);
                }

                dp[l][r] = max;
            }
        }

        return dp[0][m - 1];
    }
}