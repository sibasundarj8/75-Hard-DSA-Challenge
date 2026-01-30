/*
 *
 * https://leetcode.com/problems/odd-even-jump
 *
 * # 975. Odd Even Jump
 *
 *   Q. You are given an integer array arr. From some starting index, you can make a series of jumps. The (1st, 3rd, 5th,
 *      ...) jumps in the series are called odd-numbered jumps, and the (2nd, 4th, 6th, ...) jumps in the series are called
 *      even-numbered jumps. Note that the jumps are numbered, not the indices.
 *
 *      You may jump forward from index i to index j (with i < j) in the following way:
 *
 *      ⇨ During odd-numbered jumps (i.e., jumps 1, 3, 5, ...), you jump to the index j such that arr[i] <= arr[j] and arr[j]
 *        is the smallest possible value. If there are multiple such indices j, you can only jump to the smallest such index j.
 *
 *      ⇨ During even-numbered jumps (i.e., jumps 2, 4, 6, ...), you jump to the index j such that arr[i] >= arr[j] and arr[j]
 *        is the largest possible value. If there are multiple such indices j, you can only jump to the smallest such index j.
 *
 *      ⇨ It may be the case that for some index i, there are no legal jumps.
 *
 *      ⇨ A starting index is good if, starting from that index, you can reach the end of the array (index arr.length - 1) by
 *        jumping some number of times (possibly 0 or more than once).
 *
 *      ⇨ Return the number of good starting indices.
 *   Ex.
 *      Input : arr = [10, 13, 12, 14, 15]
 *      Output: 2
 *      Explanation:
 *              From starting index i = 0, we can make our 1st jump to i = 2 (since arr[2] is the smallest among arr[1],
 *              arr[2], arr[3], arr[4] that is greater or equal to arr[0]), then we cannot jump anymore.
 *
 *              From starting index i = 1 and i = 2, we can make our 1st jump to i = 3, then we cannot jump anymore.
 *
 *              From starting index i = 3, we can make our 1st jump to i = 4, so we have reached the end.
 *
 *              From starting index i = 4, we have reached the end already.
 *
 *              In total, there are 2 different starting indices i = 3 and i = 4, where we can reach the end with some number of
 *              jumps.
 *
 *  Constraints:
 *          1 <= arr.length <= 2 * 10⁴
 *          0 <= arr[i] < 10⁵
 */

import java.util.*;

public class Day_23_LC_975 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter array elements: ");
        String[] s = sc.nextLine().split(" ");

        int n = s.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s[i]);
        }

        System.out.println("Number of good starting indices: " + oddEvenJumps(arr));
    }

    /// Solution
    static int oddEvenJumps(int[] arr) {
        int n = arr.length;
        int count = 0;

        int[] gs = new int[n];
        int[] sl = new int[n];
        TreeMap<Integer, Integer> map = new TreeMap<>();

        gs[n - 1] = n - 1;
        sl[n - 1] = n - 1;
        map.put(arr[n - 1], n - 1);

        for (int i = n - 2; i >= 0; i--) {
            if (map.containsKey(arr[i])) {
                sl[i] = map.get(arr[i]);
                gs[i] = map.get(arr[i]);
            } else {
                Integer nextGreaterSmallest = map.higherKey(arr[i]);
                Integer nextSmallerLargest = map.lowerKey(arr[i]);

                // next Greater Smallest index of current element
                if (nextGreaterSmallest == null) gs[i] = -1;
                else gs[i] = map.get(nextGreaterSmallest);

                // next Smaller Largest index of current element
                if (nextSmallerLargest == null) sl[i] = -1;
                else sl[i] = map.get(nextSmallerLargest);
            }

            map.put(arr[i], i);
        }

        for (int i = 0; i < n; i++) {
            if (rec(i, 0, n, gs, sl)) {
                count++;
            }
        }

        return count;
    }

    private static boolean rec(int idx, int turn, int n, int[] gs, int[] sl) {
        // base case
        if (idx == n - 1) return true;
        if (idx == -1) return false;

        // recursive work
        if (turn == 0) return rec(gs[idx], 1, n, gs, sl);
        else return rec(sl[idx], 0, n, gs, sl);
    }
}