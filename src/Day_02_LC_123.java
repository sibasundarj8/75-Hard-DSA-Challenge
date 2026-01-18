import java.util.Scanner;/*
 *
 * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/
 *
 * # 123. Best Time to Buy and Sell Stock III
 *
 *   Q. You are given an array prices where prices[i] is the price of a given stock on the ith day.
 *      Find the maximum profit you can achieve. You may complete at most two transactions.
 *
 *      Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy
 *            again).
 *   Ex.
 *      Input : prices = [3, 3, 5, 0, 0, 3, 1, 4]
 *      Output: 6
 *      Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 *                   Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
 *
 *  Constraints:
 *          1 <= prices.length <= 10⁵
 *          0 <= prices[i] <= 10⁵
 */

public class Day_02_LC_123 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter stock prices: ");
        String[] s = sc.nextLine().split(" ");

        int n = s.length;
        int[] stock = new int[n];
        for (int i = 0; i < n; i++) stock[i] = Integer.parseInt(s[i]);

        System.out.println("Max profit if only 2 transactions allowed: ");
        System.out.println(maxProfit(stock));
    }

    /// Solution
/*
◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦memoization◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦
TC : O(n)
SC : O(n) + extra recursive call stack
*/
    static int memoization(int[] prices) {
        int n = prices.length;
        Integer[][][]  dp = new Integer[n][3][2];
        return rec(0, prices, n, 2, 0, dp);
    }

    // helper
    private static int rec(int i, int[] arr, int n, int k, int bought, Integer[][][] dp) {
        if(i >= n || k <= 0) return 0;
        if(dp[i][k][bought] != null) return dp[i][k][bought];
        int ans;

        if(bought == 1) {
            ans = Math.max(
                    arr[i] + rec(i + 1, arr, n, k - 1, 0, dp),
                    rec(i + 1, arr, n, k, 1, dp)
            );
        } else {
            ans = Math.max(
                    -arr[i] + rec(i + 1, arr, n, k, 1, dp),
                    rec(i + 1, arr, n, k, 0, dp)
            );
        }

        return dp[i][k][bought] = ans;
    }

/*
◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦Tabulation◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦
TC : O(n)
SC : O(n)
*/
    static int tabulation(int[] arr) {
        int n = arr.length;
        Integer[][][]  dp = new Integer[n + 1][3][2];

        for (int k = 0; k <= 2; k++) {
            dp[n][k][0] = 0;
            dp[n][k][1] = 0;
        }
        for(int i = 0; i < n; i++){
            dp[i][0][0] = 0;
            dp[i][0][1] = 0;
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int k = 1; k <= 2; k++) {
                dp[i][k][1]  = Math.max(arr[i] + dp[i + 1][k - 1][0], dp[i + 1][k][1]);
                dp[i][k][0]  = Math.max(-arr[i] + dp[i + 1][k][1], dp[i + 1][k][0]);
            }
        }

        return dp[0][2][0];
    }

/*
◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦Space-Optimized◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦◦
TC : O(n)
SC : O(1)
*/
    static int maxProfit(int[] arr) {
        int b1 = Integer.MIN_VALUE;
        int b2 = Integer.MIN_VALUE;
        int s1 = 0;
        int s2 = 0;

        for (int price : arr) {
            b1 = Math.max(b1, -price);
            s1 = Math.max(s1, b1 + price);
            b2 = Math.max(b2, s1 - price);
            s2 = Math.max(s2, b2 + price);
        }

        return s2;
    }
}