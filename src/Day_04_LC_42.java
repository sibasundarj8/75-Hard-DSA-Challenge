import java.util.Scanner;
/**
 *
 * <a href="https://leetcode.com/problems/trapping-rain-water/">LeetCode</a>
 * <h3>
 * # 42. Trapping Rain Water
 * </h3>
 * <p>
 *   Q. Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much
 *      water it can trap after raining.
 *    <br> Ex.
 *    <br>   Input : height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
 *    <br>   Output: 6
 *    <br>   Explanation: The above elevation map (black section) is represented by array [0,1,0,2,1,0,1,3,2,1,2,1].
 *    <br>                In this case, 6 units of rain water (blue section) are being trapped.
 * </p>
 * <p>
 *  Constraints:
 *     <br> n == height.length
 *     <br> 1 <= n <= 2 * 10⁴
 *     <br> 0 <= height[i] <= 10⁵
 * </p>
 */

public class Day_04_LC_42 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);

        System.out.println("Enter heights: ");
        String[] s = sc.nextLine().split(" ");

        int n = s.length;
        int[] heights = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = Integer.parseInt(s[i]);
        }

        System.out.println("Amount of water it can trap after raining: ");
        System.out.println(trap(heights));
    }

    /// Solution
/*
❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍-Left-Max--Right-Max-❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍
TC : O(3n)
SC : O(2n)
*/
    static int leftMaxRightMax(int[] height) {
        int n = height.length;
        int ans = 0;

        int[] left = leftWall(height);
        int[] right = rightWall(height);

        for (int i = 0; i < n; i++) {
            int wallHeight = Math.min(left[i], right[i]);
            ans += (wallHeight - height[i]);
        }

        return ans;
    }

    private static int[] leftWall(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];

        left[0] = arr[0];

        for (int i = 1; i < n; i++) {
            left[i] = Math.max(left[i - 1], arr[i]);
        }

        return left;
    }

    private static int[] rightWall(int[] arr) {
        int n = arr.length;
        int[] right = new int[n];

        right[n - 1] = arr[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            right[i] = Math.max(right[i + 1], arr[i]);
        }

        return right;
    }

/*
❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍-Space-Optimized-❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍❍
TC : O(n)
SC : O(1)
*/
    static int trap(int[] height) {
        int n = height.length;
        int ans = 0;
        int l = 0;
        int r = n - 1;
        int leftMax = height[0];
        int rightMax = height[n - 1];

        while (l < r) {
            if (leftMax < rightMax) {
                leftMax = Math.max(leftMax, height[++l]);
                ans +=  (leftMax - height[l]);
            } else {
                rightMax = Math.max(rightMax, height[--r]);
                ans += (rightMax - height[r]);
            }
        }

        return ans;
    }
}