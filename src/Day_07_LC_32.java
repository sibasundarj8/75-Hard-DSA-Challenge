import java.util.Scanner;/*
 *
 * https://leetcode.com/problems/longest-valid-parentheses/
 *
 * # 32. Longest Valid Parentheses
 *
 *    Q.Given a string containing just the characters '(' and ')', return the length of the longest valid (well-formed)
 *      parentheses substring.
 *    Ex.
 *      Input: s = ")()())"
 *      Output: 4
 *      Explanation: The longest valid parentheses substring is "()()".
 *
 *  Constraints:
 *          0 <= s.length <= 3 * 10⁴
 *          s[i] is '(', or ')'.
 */

import java.util.Stack;

public class Day_07_LC_32 {

    /// <h6>main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter string: (must contain either '(' or ')')");
        String s = sc.next();

        System.out.println("Longest Valid Parentheses length: " + longestValidParentheses(s));
    }

    /// <h6>Solution
/*
⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯-Using-Stack-⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
TC : O(n)
SC : O(n)
*/
    static int usingStack(String s) {
        int n = s.length();
        int ans = 0;
        Stack<Integer> stack = new Stack<>();

        stack.push(-1);

        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(' || stack.isEmpty()) stack.push(i);
            else {
                stack.pop();
                if (stack.isEmpty()) stack.push(i);
                else ans = Math.max(ans, i - stack.peek());
            }
        }

        return ans;
    }

/*
⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯-Space-Optimized-(Two-Pass)-⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯
TC : O(n)
SC : O(1)
*/
    static int longestValidParentheses(String s) {
        int n = s.length();
        int start, close, maxLeft, maxRight;
        start = close = maxRight = maxLeft = 0;

        // left ⎯> right
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);

            if (ch == '(') start++;
            else close++;

            if (start == close) maxLeft = Math.max(maxLeft, start + close);
            if (close > start) start = close = 0;
        }

        start = close = 0;

        // right ⎯> left
        for (int i = n - 1; i >= 0; i--) {
            char ch = s.charAt(i);

            if (ch == ')') start++;
            else close++;

            if (start == close) maxRight = Math.max(maxRight, start + close);
            if (close > start) start = close = 0;
        }

        return Math.max(maxLeft, maxRight);
    }
}