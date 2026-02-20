/*
 *
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv
 *
 * # 188. Best Time to Buy and Sell Stock IV
 *
 *   Q. You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
 *
 *      Find the maximum profit you can achieve. You may complete at most k transactions: i.e. you may buy at most k times and
 *      sell at most k times.
 *
 *      Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
 *
 *    Ex.
 *      Input : k = 2, prices = [2,4,1]
 *      Output: 2
 *      Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
 *
 *  Constraints:
 *          1 <= k <= 100
 *          1 <= prices.length <= 1000
 *          0 <= prices[i] <= 1000
 */

import java.util.Scanner;

public class Day_19_LC_188 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter prices: ");
        String[] s = sc.nextLine().split(" ");

        int n = s.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }

        System.out.println("K : ");
        int k = sc.nextInt();

        System.out.println("Max profit using k transactions: " + maxProfit(k, arr));
    }

    // Solution
/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Memoization-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n * k)
SC : O(n * k) + recursive call stack
*/
    static int memoization(int k, int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][k + 1][2];

        for (int[][] mat : dp)
            for (int[] arr : mat)
                arr[0] = arr[1] = -1;

        return rec(0, k, 0, n, prices, dp);
    }

    private static int rec(int idx, int cap, int bought, int n, int[] prices, int[][][] dp) {
        // base case
        if (idx >= n || cap <= 0) return 0;
        if (dp[idx][cap][bought] != -1) return dp[idx][cap][bought];

        // recursive case
        int profit;

        if (bought == 0) {
            int buy = -prices[idx] + rec(idx + 1, cap, 1, n, prices, dp);
            int skip = rec(idx + 1, cap, 0, n, prices, dp);
            profit = Math.max(buy, skip);
        } else {
            int sell = prices[idx] + rec(idx + 1, cap - 1, 0, n, prices, dp);
            int skip = rec(idx + 1, cap, 1, n, prices, dp);
            profit = Math.max(sell, skip);
        }

        return dp[idx][cap][bought] = profit;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Tabulation-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n * k)
SC : O(n * k)
*/
    static int tabulation(int k, int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][k + 1][2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int cap = 1; cap < k + 1; cap++) {
                dp[idx][cap][0] = Math.max(-prices[idx] + dp[idx + 1][cap][1], dp[idx + 1][cap][0]);
                dp[idx][cap][1] = Math.max(prices[idx] + dp[idx + 1][cap - 1][0], dp[idx + 1][cap][1]);
            }
        }

        return dp[0][k][0];
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Space--Optimized-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n * k)
SC : O(k)
*/
    static int spaceOptimized(int k, int[] prices) {
        int n = prices.length;
        int[][] curr = new int[k + 1][2];
        int[][] next = new int[k + 1][2];

        for (int idx = n - 1; idx >= 0; idx--) {
            for (int cap = 1; cap < k + 1; cap++) {
                curr[cap][0] = Math.max(-prices[idx] + next[cap][1], next[cap][0]);
                curr[cap][1] = Math.max(prices[idx] + next[cap - 1][0], next[cap][1]);
            }

            int[][] temp = curr;
            curr = next;
            next = temp;
        }

        return next[k][0];
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Finite-State-Machine--DP-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n * k)
SC : O(k)
*/
    static int maxProfit(int k, int[] prices) {
        int[] transactions = new int[2 * k];

        for (int i = 0; i < k; i++)
            transactions[2 * i] = Integer.MIN_VALUE;

        for (int price : prices) {
            transactions[0] = Math.max(transactions[0], -price);
            transactions[1] = Math.max(transactions[1], transactions[0] + price);

            for (int j = 2; j < 2 * k; j+=2) {
                // buy
                transactions[j] = Math.max(transactions[j], transactions[j - 1] - price);
                // sell
                transactions[j+1] = Math.max(transactions[j+1], transactions[j] + price);
            }
        }

        return transactions[2 * k - 1];
    }
}