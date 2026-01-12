/*
 *
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/
 *
 * # 154. Find Minimum in Rotated Sorted Array II
 *
 *   Q. Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array
 *      nums = [0,1,4,4,5,6,7] might become:
 *          • n[4,5,6,7,0,1,4] if it was rotated 4 times.
 *          • n[0,1,4,4,5,6,7] if it was rotated 7 times.
 *
 *      Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1],
 *      a[2], ..., a[n-2]].
 *
 *      Given the sorted rotated array nums that may contain duplicates, return the minimum element of this array.
 *
 *      You must decrease the overall operation steps as much as possible.
 *    Ex.
 *      Input : nums = [2,2,2,0,1]
 *      Output: 0
 *
 *  Constraints:
 *          n == nums.length
 *          1 <= n <= 5000
 *          -5000 <= nums[i] <= 5000
 *          nums is sorted and rotated between 1 and n times.
 */

public class Day_09_LC_154 {

    /// main Method
    public static void main(String[] args) {
        int[] arr = {3, 1, 3};

        System.out.println("Minimum number: " + findMin(arr));
    }

    /// Solution
/*
✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘-Brute-Force-✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘
TC : O(N) for every case
SC : O(1)
*/
    static int bruteForce(int[] arr) {
        int ans = arr[0];

        for(int i : arr)  {
            ans = Math.min(i, ans);
        }

        return ans;
    }

/*
✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎-Binary-Search-✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎
TC : O(log N)  ---> O(N) in worst case only if maximum elements are same.
SC : O(1)
*/
    static int findMin(int[] arr) {
        int i = 0;
        int j = arr.length - 1;

        while (i < j) {
            int mid = i + (j - i) / 2;

            if (arr[mid] > arr[j]) i = mid + 1;
            else if (arr[mid] < arr[j]) j = mid;
            else j--;
        }

        return arr[j];
    }
}