/*
 *
 * https://leetcode.com/problems/sliding-window-median
 *
 * # 480. Sliding Window Median
 *
 *   Q. The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value.
 *      So the median is the mean of the two middle values.
 *
 *      ❍ For examples, if arr = [2,3,4], the median is 3.
 *      ❍ For examples, if arr = [1,2,3,4], the median is (2 + 3) / 2 = 2.5.
 *      ❍ You are given an integer array nums and an integer k. There is a sliding window of size k which is moving from the
 *        very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window
 *        moves right by one position.
 *
 *      Return the median array for each window in the original array. Answers within 10⁻⁵ of the actual value will be accepted.
 *    Ex.
 *      Input : nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 *      Output: [1.0, -1.0, -1.0, 3.0, 5.0, 6.0]
 *      Explanation:
 *                        Window position          Median
 *                  -------------------------      -----
 *                  [1  3  -1] -3  5  3  6  7        1
 *                   1 [3  -1  -3] 5  3  6  7       -1
 *                   1  3 [-1  -3  5] 3  6  7       -1
 *                   1  3  -1 [-3  5  3] 6  7        3
 *                   1  3  -1  -3 [5  3  6] 7        5
 *                   1  3  -1  -3  5 [3  6  7]       6
 *
 *  Constraints:
 *          1 <= k <= nums.length <= 10⁵
 *          -2³¹ <= nums[i] <= 2³¹ - 1
 */


import java.util.*;

public class Day_13_LC_480 {

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

        System.out.print("Enter k: ");
        int k = sc.nextInt();

        System.out.println("Median for every "+ k +" size window: ");
        System.out.println(Arrays.toString(new Day_13_LC_480().medianSlidingWindow(arr, k)));
    }

    /// Solution
/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-using-Tree-Map-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(n log k)
SC : O(k)
*/
    private int leftCount = 0;
    private int rightCount = 0;
    private final TreeMap<Integer, Integer> leftTree = new TreeMap<>();
    private final TreeMap<Integer, Integer> rightTree = new TreeMap<>();

    private void addNumber(int num) {
        if (rightCount == 0 || num >= rightTree.firstKey()) {
            rightTree.put(num, rightTree.getOrDefault(num, 0) + 1);
            rightCount++;
        } else {
            leftTree.put(num, leftTree.getOrDefault(num, 0) + 1);
            leftCount++;
        }

        this.rebalance();
    }

    private void removeNumber(int num) {
        Integer cnt = leftTree.get(num);

        if (cnt != null) {
            if (cnt == 1) leftTree.remove(num);
            else leftTree.put(num, cnt - 1);
            leftCount--;

        } else {
            cnt = rightTree.get(num);
            if (cnt == 1) rightTree.remove(num);
            else rightTree.put(num, cnt - 1);
            rightCount--;
        }

        this.rebalance();
    }

    private void rebalance() {
        if (leftCount > rightCount) {
            int val = leftTree.lastKey();

            leftTree.put(val, leftTree.get(val) - 1);
            leftCount--;

            rightTree.put(val, rightTree.getOrDefault(val, 0) + 1);
            rightCount++;

            if (leftTree.get(val) == 0) leftTree.remove(val);

        } else if (rightCount > leftCount + 1) {
            int val = rightTree.firstKey();

            rightTree.put(val, rightTree.get(val) - 1);
            rightCount--;

            leftTree.put(val, leftTree.getOrDefault(val, 0) + 1);
            leftCount++;

            if (rightTree.get(val) == 0) rightTree.remove(val);
        }
    }

    private double findMedian() {
        if (leftCount == rightCount) {
            int l = leftTree.lastKey();
            int r = rightTree.firstKey();
            return l + ((long) r - l) / 2.0;
        } else return rightTree.firstKey();
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        double[] ans = new double[n - k + 1];

        for (int i = 0; i < k; i++) {
            this.addNumber(nums[i]);
        }

        ans[0] = this.findMedian();

        for (int i = k; i < n; i++) {
            this.removeNumber(nums[i - k]);
            this.addNumber(nums[i]);
            ans[i - k + 1] = this.findMedian();
        }

        return ans;
    }
}