import java.util.Scanner;/*
 *
 * https://leetcode.com/problems/split-array-largest-sum/
 *
 * # 410. Split Array Largest Sum
 *
 *   Q. Given an integer array nums and an integer k, split nums into k non-empty subarrays such that the largest sum of
 *      any subarray is minimized.
 *
 *      Return the minimized largest sum of the split.
 *      A subarray is a contiguous part of the array.
 *   Ex.
 *      Input : nums = [7,2,5,10,8], k = 2
 *      Output: 18
 *      Explanation: There are four ways to split nums into two subarrays.
 *                   The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays
 *                   is only 18.
 *
 *  Constraints:
 *          1 <= nums.length <= 1000
 *          0 <= nums[i] <= 10⁶
 *          1 <= k <= min(50, nums.length)
 */

public class Day_01_LC_410 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter array elements: ");
        String[] s = sc.nextLine().split(" ");

        int[] arr = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }

        System.out.println("Enter k: ");
        int k = sc.nextInt();

        System.out.println("Minimum largest subarray sum is: " + splitArray(arr, k));
    }

    /// Solution
    static int splitArray(int[] nums, int k) {
        int ans = -1;
        int l = nums[0];
        int r = 0;

        for (int i : nums) r += i;

        while (l <= r) {
            int mid = l + (r - l) / 2;

            if (isPossible(nums, mid, k)) {
                ans = mid;
                r = mid - 1;
            } else l = mid + 1;
        }

        return ans;
    }

    private static boolean isPossible(int[] arr, int target, int k) {
        int sum = 0;

        for (int i : arr) {
            if (i > target || k <= 0) return false;
            sum += i;
            if (sum > target) {
                k--;
                sum = i;
            }
        }

        return k > 0;
    }
}