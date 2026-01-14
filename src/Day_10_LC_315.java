/*
 *
 * https://leetcode.com/problems/count-of-smaller-numbers-after-self/
 *
 * # 315. Count of Smaller Numbers After Self
 *
 *   Q. Given an integer array nums, return an integer array counts where counts[i] is the number of smaller elements to
 *      the right of nums[i].
 *    Ex.
 *      Input: nums = [5,2,6,1]
 *      Output: [2,1,1,0]
 *      Explanation: To the right of 5 there are 2 smaller elements (2 and 1).
 *                   To the right of 2 there is only 1 smaller element (1).
 *                   To the right of 6 there is 1 smaller element (1).
 *                   To the right of 1 there is 0 smaller element.
 *
 *  Constraints:
 *          1 <= nums.length <= 10⁵
 *          -10⁴ <= nums[i] <= 10⁴
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day_10_LC_315 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Array Elements: ");
        String[] s =  sc.nextLine().split(" ");

        int n = s.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }

        System.out.println("Smaller elements after self: ");
        System.out.println(countSmaller(arr));
    }

    /// Solution
/*
✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕-Brute-Force-✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕✕
TC : O(n²)
SC : O(1)
*/
    static List<Integer> bruteForce(int[] nums) {
        int n = nums.length;
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int count = 0;

            for (int j = i + 1; j < n; j++) {
                if (nums[j] <  nums[i]) count++;
            }

            list.add(count);
        }

        return list;
    }

/*
✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎-using-Merge-sort-✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎✔︎
TC : O(n log n)
SC : O(n)
*/
    static List<Integer> countSmaller(int[] nums) {
        // potd.code.hub
        int n = nums.length;
        int[] index = new int[n];
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            index[i] = i;
            list.add(0);
        }

        update(index, 0, n - 1, list, nums);

        return list;
    }

    private static void update(int[] index, int l, int r, List<Integer> list, int[] nums) {
        if (l >= r) return;

        int mid = l + (r - l) / 2;

        update(index, l, mid, list, nums);
        update(index, mid + 1, r, list, nums);

        merge(index, l, mid, r, list, nums);
    }

    private static void merge(int[] index, int l, int mid, int r, List<Integer> list, int[] nums) {
        int n1 = mid - l + 1;
        int n2 = r - mid;

        int[] left = new int[n1];
        int[] right = new int[n2];

        System.arraycopy(index, l, left, 0, n1);
        System.arraycopy(index, mid + 1, right, 0, n2);

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) {
            if (nums[left[i]] <= nums[right[j]]) {
                index[k++] = left[i];
                list.set(left[i], list.get(left[i]) + j);
                i++;
            } else index[k++] = right[j++];
        }

        while (i < n1) {
            index[k++] = left[i];
            list.set(left[i], list.get(left[i]) + j);
            i++;
        }
        while (j < n2) {
            index[k++] = right[j++];
        }
    }
}