/*
 *
 * https://leetcode.com/problems/count-vowels-permutation
 *
 * # 1220. Count Vowels Permutation
 *
 *   Q. Given an integer n, your task is to count how many strings of length n can be formed under the following rules:
 *
 *      Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
 *      Each vowel 'a' may only be followed by an 'e'.
 *      Each vowel 'e' may only be followed by an 'a' or an 'i'.
 *      Each vowel 'i' may not be followed by another 'i'.
 *      Each vowel 'o' may only be followed by an 'i' or a 'u'.
 *      Each vowel 'u' may only be followed by an 'a'.
 *      Since the answer may be too large, return it modulo 10⁹ + 7.
 *
 *   Ex.
 *      Input : n = 2
 *      Output: 10
 *      Explanation: All possible strings are: "ae", "ea", "ei", "ia", "ie", "io", "iu", "oi", "ou" and "ua".
 *
 *  Constraints:
 *          1 <= n <= 2 * 10⁴
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Day_24_LC_1220 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the length: ");
        int n = sc.nextInt();

        System.out.println("Number of combinations: ");
        System.out.println(tabulation(n));
    }

    /// Solution
/*
✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘-Brute-Force-✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘
TC : O(4ⁿ)
SC : O(n)    (recursive depth)
*/
    static int bruteForce(int n) {
        int count = 0;
        int mod = 1000000007;
        HashMap<Character, List<Character>> adjMap = new HashMap<>();

        // constructing graph according to our requirement
        adjMap.put('a', List.of('e'));
        adjMap.put('e', List.of('a', 'i'));
        adjMap.put('i', List.of('a', 'e', 'o', 'u'));
        adjMap.put('o', List.of('i', 'u'));
        adjMap.put('u', List.of('a'));

        for (char c : adjMap.keySet()) {
            count += dfs2(n - 1, c, adjMap, mod);
        }

        return count;
    }

    private static int dfs2(int n, char ch, HashMap<Character, List<Character>> adjMap, int mod) {
        // base case
        if (n == 0) return 1;

        // recursive case
        int count = 0;
        for (char c : adjMap.get(ch)) {
            count += (dfs2(n - 1, c, adjMap, mod) % mod);
        }

        return count;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-memoization-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n)
SC : O(n) + recursive depth
*/
    static int memoization(int n) {
        int count = 0;
        int mod = 1000000007;
        HashMap<Integer, List<Integer>> adjMap = new HashMap<>();
        int[][] dp = new int[n][5];

        // constructing graph according to our requirement
        adjMap.put(0, List.of(1));      // a  ->  e
        adjMap.put(1, List.of(0, 2));       // e  ->  a, i
        adjMap.put(2, List.of(0, 1, 3, 4)); // i  ->  a, e, o, u
        adjMap.put(3, List.of(2, 4));       // o  ->  i, u
        adjMap.put(4, List.of(0));      // u  ->  a

        for (int c : adjMap.keySet()) {
            count += dfs(n - 1, c, adjMap, dp, mod);
            count %= mod;
        }

        return count;
    }

    private static int dfs(int n, int ch, HashMap<Integer, List<Integer>> adjMap, int[][] dp, int mod) {
        // base case
        if (n == 0) return 1;
        if (dp[n][ch] != 0) return dp[n][ch];

        // recursive case
        int count = 0;
        for (int c : adjMap.get(ch)) {
            count += dfs(n - 1, c, adjMap, dp, mod) % mod;
            count %= mod;
        }

        return dp[n][ch] = count;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-tabulation-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n)
SC : O(n)
*/
    static int tabulation(int n) {
        int count = 0;
        int mod = 1000000007;
        int[][] dp = new int[n][5];

        // base case from memoization
        for (int i = 0; i < 5; i++) {
            dp[0][i] = 1;
        }

        // iterative version of recursion
        for (int i = 1; i < n; i++) {

            // a -> e
            dp[i][0] = dp[i - 1][1];

            // e -> a, i
            dp[i][1] = dp[i - 1][0] + dp[i - 1][2];
            dp[i][1] %= mod;

            // i -> a, e, o, u
            int ae = (dp[i - 1][0] + dp[i - 1][1]) % mod;
            int ou = (dp[i - 1][3] + dp[i - 1][4]) % mod;
            dp[i][2] = (ae + ou) % mod;

            // o -> i, u
            dp[i][3] = dp[i - 1][2] + dp[i - 1][4];
            dp[i][3] %= mod;

            // u -> a
            dp[i][4] = dp[i - 1][0];
        }

        // counting all the combinations ending at each vowel for length n
        for (int i = 0; i < 5; i++) {
            count += dp[n - 1][i];
            count %= mod;
        }

        return count;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-space-optimized-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n)
SC : O(1)
*/
    static int countVowelPermutation(int n) {
        int count = 0;
        int mod = 1000000007;
        int[] curr = new int[5];
        int[] prev = new int[5];

        // base case from memoization
        Arrays.fill(prev, 1);

        // iterative version of recursion
        for (int i = 1; i < n; i++) {

            // a -> e
            curr[0] = prev[1];

            // e -> a, i
            curr[1] = prev[0] + prev[2];
            curr[1] %= mod;

            // i -> a, e, o, u
            int ae = (prev[0] + prev[1]) % mod;
            int ou = (prev[3] + prev[4]) % mod;
            curr[2] = (ae + ou) % mod;

            // o -> i, u
            curr[3] = prev[2] + prev[4];
            curr[3] %= mod;

            // u -> a
            curr[4] = prev[0];

            // copping the current value to previous value
            prev = curr.clone();
        }

        // counting all the combinations ending at each vowel for length n
        for (int i = 0; i < 5; i++) {
            count += prev[i];
            count %= mod;
        }

        return count;
    }
}