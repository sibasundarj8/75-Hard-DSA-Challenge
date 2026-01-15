/*
 *
 * https://leetcode.com/problems/minimum-window-substring
 *
 * # 76. Minimum Window Substring
 *
 *   Q. Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that
 *      every character in t (including duplicates) is included in the window. If there is no such substring, return
 *      the empty string "".
 *
 *      The testcases will be generated such that the answer is unique.
 *
 *    Ex.
 *      Input : s = "ADOBECODEBANC", t = "ABC"
 *      Output: "BANC"
 *      Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
 *
 *  Constraints:
 *          m == s.length
 *          n == t.length
 *          1 <= m, n <= 10⁵
 *          s and t consist of uppercase and lowercase English letters.
 */

import java.util.HashMap;
import java.util.Scanner;

public class Day_11_LC_76 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("S: ");
        String s = sc.next();

        System.out.print("T: ");
        String t = sc.next();

        System.out.println("Minimum window of S which contains T: ");
        System.out.println(minWindow(s, t));
    }

    /// Solution
/*
⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯-version-1-⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
TC : O(n)
SC : O(k) ---> because k (number of distinct characters in text) may defer per test case.
*/
    static String version_1(String s, String t) {
        // code here
        int n = s.length();
        int m = t.length();

        HashMap<Character, Integer> txtFreq = new HashMap<>();
        HashMap<Character, Integer> strFreq = new HashMap<>();

        for (int i = 0; i < m; i++) {
            char ch = t.charAt(i);
            txtFreq.put(ch, txtFreq.getOrDefault(ch, 0) + 1);
        }

        int left = 0;
        int right = 0;
        int charCount = 0;
        int minSize = Integer.MAX_VALUE;
        int minLeft = 0;
        int minRight = 0;

        while (right < n) {

            // expanding the window
            while (right < n && charCount != txtFreq.size()) {
                char ch = s.charAt(right++);
                if (txtFreq.containsKey(ch)) {
                    strFreq.put(ch, strFreq.getOrDefault(ch, 0) + 1);

                    if (strFreq.get(ch).equals(txtFreq.get(ch))) {
                        charCount++;
                    }
                }
            }

            // shrinking the window
            while (left < right && charCount == txtFreq.size()) {

                // updating min
                int windowSize = right - left;
                if (windowSize < minSize) {
                    minSize = windowSize;
                    minLeft = left;
                    minRight = right;
                }

                char ch = s.charAt(left++);
                if (txtFreq.containsKey(ch)) {
                    strFreq.put(ch, strFreq.get(ch) - 1);

                    if (strFreq.get(ch) < (txtFreq.get(ch))) {
                        charCount--;
                    }
                }
            }
        }

        return (minSize == Integer.MAX_VALUE) ? "" : s.substring(minLeft, minRight);
    }

/*
⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯-version-2-⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
TC : O(n)
SC : O(1) ---> we gonna use a fixed size array to store the character frequency
*/
    static String minWindow(String s, String t) {
        // code here
        int n = s.length();
        int m = t.length();
        int[] charFreq = new int[123];

        for (int i = 0; i < m; i++) {
            char ch = t.charAt(i);
            charFreq[ch]++;
        }

        int left = 0;
        int right = 0;
        int minSize = Integer.MAX_VALUE;
        int minLeft = 0;
        int minRight = 0;
        int requiredChar = m;

        while (right < n) {
            char ch = s.charAt(right++);
            if (charFreq[ch] > 0) requiredChar--;
            charFreq[ch]--;

            while (left < right && requiredChar == 0) {
                int length = right - left;
                if (length < minSize) {
                    minSize = length;
                    minLeft = left;
                    minRight = right;
                }

                char ch1 = s.charAt(left++);
                charFreq[ch1]++;
                if (charFreq[ch1] > 0) requiredChar++;
            }
        }

        return (minSize == Integer.MAX_VALUE) ? "" : s.substring(minLeft, minRight);
    }
}