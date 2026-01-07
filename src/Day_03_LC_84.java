import java.util.Scanner;/*
 *
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 *
 * # 84. Largest Rectangle in Histogram
 *
 *   Q. Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
 *      return the area of the largest rectangle in the histogram.
 *    Ex.                                                                         .
 *      Input : heights = [2, 1, 5, 6, 2, 3]                                    # #
 *                                                                              # #
 *                                                                              # #   .
 *                                                                          .   # # . .
 *                                                                          . . # # . .
 *      Output: 10                                                          -----------
 *      Explanation: The above is a histogram where width of each bar is 1.
 *                   The largest rectangle is shown in the red area, which has an area = 10 units.
 *
 *  Constraints:
 *          1 <= heights.length <= 10⁵
 *          0 <= heights[i] <= 10⁴
 */
import java.util.Stack;

public class Day_03_LC_84 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter bar heights: ");
        String[] s =  sc.nextLine().split(" ");

        int n = s.length;
        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = Integer.parseInt(s[i]);
        }

        System.out.println("The area of largest rectangle in the histogram is:");
        System.out.println(largestRectangleArea(heights));
    }

    /// Solution
/*
▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫NSE-PSE▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫
TC : O(n)
SC : O(4 * n) {2 stacks + 2 arrays}
*/
    static int nse_pse(int[] heights) {
        int n = heights.length;
        int maxArea = 0;

        int[] nse = nextSmallerElement(heights);
        int[] pse = prevSmallerElement(heights);

        for (int i = 0; i < n; i++) {
            int length = nse[i] - pse[i] - 1;
            int height = heights[i];
            int area = length * height;
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    private static int[] nextSmallerElement(int[] heights) {
        int n = heights.length;

        int[] nse = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            int curr = heights[i];
            while (!stack.isEmpty() && heights[stack.peek()] >= curr) stack.pop();
            nse[i] = (stack.isEmpty()) ? n : stack.peek();
            stack.push(i);
        }

        return nse;
    }

    private static int[] prevSmallerElement(int[] heights) {
        int n = heights.length;
        int[] pse = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            int curr = heights[i];
            while (!stack.isEmpty() && heights[stack.peek()] >= curr) stack.pop();
            pse[i] = (stack.isEmpty()) ? -1 : stack.peek();
            stack.push(i);
        }

        return pse;
    }

/*
▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫One-pass-Stack▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫
TC : O(n)
SC : O(n)   one stack and one array
*/
    static int one_pass_stack(int[] heights) {
        int n = heights.length;
        int maxArea = 0;

        int[] pse = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            int curr = heights[i];

            while (!stack.isEmpty() && heights[stack.peek()] >= curr) {
                int height = heights[stack.peek()];
                int width = i - pse[stack.peek()] - 1;
                int area = height * width;
                maxArea = Math.max(maxArea, area);
                stack.pop();
            }

            pse[i] = (stack.isEmpty()) ? -1 : stack.peek();
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int height = heights[stack.peek()];
            int width = n - pse[stack.peek()] - 1;
            int area = height * width;
            maxArea = Math.max(maxArea, area);
            stack.pop();
        }

        return maxArea;
    }

/*
▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫Space-Optimized▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫▫
TC : O(n)
SC : O(n)   only one stack
*/
    static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            int curr = heights[i];

            while (!stack.isEmpty() && heights[stack.peek()] >= curr) {
                int cur = stack.pop();
                int nse = i;
                int pse = (stack.isEmpty()) ? -1 : stack.peek();

                int height = heights[cur];
                int width = nse - pse - 1;
                int area = height * width;

                maxArea = Math.max(maxArea, area);
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int nse = n;
            int pse = (stack.isEmpty()) ? -1 : stack.peek();

            int height = heights[cur];
            int width = nse - pse - 1;
            int area = height * width;

            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }
}