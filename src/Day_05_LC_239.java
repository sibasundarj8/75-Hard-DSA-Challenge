import java.util.*;
/**
 * <a href="https://leetcode.com/problems/sliding-window-maximum/">LeatCode</a>
 *
 * <H3># 239. Sliding Window Maximum</H3>
 *
 * <p>
 *   Q. You are given an array of integers nums, there is a sliding window of size k which is moving from the very left
 *      of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves
 *      right by one position.
 *      <p>Return the max sliding window.</p>
 * </p>
 * <h5>Ex.</h5>
 * <p>
 * <br> Input: nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 * <br> Output: [3, 3, 5, 5, 6, 7]
 * <br> Explanation:
 * <br>         Window position --            Max
 * <br>         ------------------            -----
 * <br>         [1  3  -1] -3  5  3  6  7 ...   3
 * <br>          1 [3  -1  -3] 5  3  6  7 ...   3
 * <br>          1  3 [-1  -3  5] 3  6  7 ...   5
 * <br>          1  3  -1 [-3  5  3] 6  7 ...   5
 * <br>          1  3  -1  -3 [5  3  6] 7 ...   6
 * <br>          1  3  -1  -3  5 [3  6  7] ...   7
 * </p>
 * <p>
 * <h5>Constraints:</h5>
 * <br>      1 <= nums.length <= 10⁵
 * <br>      -10⁴ <= nums[i] <= 10⁴
 * <br>      1 <= k <= nums.length
 * </p>
 */

public class Day_05_LC_239 {

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

        System.out.print("Enter K : ");
        int k = sc.nextInt();

        System.out.println("Max number at each window : ");
        int[] ans = maxSlidingWindow(arr, k);
        System.out.println(Arrays.toString(ans));
    }

    /// Solution
/*
◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇-Priority-Queue-◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇
TC : O(n * k)
SC : O(k)
*/
    static int[] usingPriorityQueue(int[] arr, int k) {
        int n = arr.length;
        int[] ans = new int[n - k + 1];
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(a -> -a));

        for (int i = 0; i < k; i++) {
            pq.add(arr[i]);
        }
        ans[0] = pq.peek();

        for (int i = k; i < n; i++) {
            pq.remove(arr[i - k]);
            pq.add(arr[i]);
            ans[i - k + 1] = pq.peek();
        }

        return ans;
    }

/*
◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇-Deque-◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇◇
TC : O(n)
SC : O(k)
*/
    static int[] maxSlidingWindow(int[] arr, int k) {
        int n = arr.length;
        int[] ans = new int[n - k + 1];
        ArrayDeque<Integer> dq = new ArrayDeque<>();

        for (int i = 0; i < k; i++) {
            while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) dq.pollLast();
            dq.addLast(i);
        }
        ans[0] = arr[dq.peekFirst()];

        for (int i = k; i < n; i++) {
            if (dq.peekFirst() == i - k) dq.pollFirst();
            while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) dq.pollLast();
            dq.addLast(i);
            ans[i - k + 1] = arr[dq.peekFirst()];
        }

        return ans;
    }
}