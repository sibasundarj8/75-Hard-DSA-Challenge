import java.util.Comparator;
import java.util.PriorityQueue;/*
 * https://leetcode.com/problems/find-median-from-data-stream/
 *
 * # 295. Find Median from Data Stream
 *
 *   Q. The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value,
 *      and the median is the mean of the two middle values.
 *
 *      For example, for arr = [2,3,4], the median is 3.
 *      For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
 *
 *      Implement the MedianFinder class:
 *          1. MedianFinder() initializes the MedianFinder object.
 *          2. void addNum(int num) adds the integer num from the data stream to the data structure.
 *          3. double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will
 *                                 be accepted.
 *    Ex.
 *      Input : ["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
 *              [[], [1], [2], [], [3], []]
 *      Output: [null, null, null, 1.5, null, 2.0]
 *      Explanation:
 *              MedianFinder medianFinder = new MedianFinder();
 *              medianFinder.addNum(1);    // arr = [1]
 *              medianFinder.addNum(2);    // arr = [1, 2]
 *              medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
 *              medianFinder.addNum(3);    // arr[1, 2, 3]
 *              medianFinder.findMedian(); // return 2.0
 *
 *  Constraints:
 *          -10⁵ <= num <= 10⁵
 *          There will be at least one element in the data structure before calling findMedian.
 *          At most 5 * 10⁴ calls will be made to addNum and findMedian.
 */

public class Day_06_LC_295 {
    ///  main Method
    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        int[][] queries = {{-1}, {}, {-2}, {}, {-3}, {}, {-4}, {}, {-5}, {}};

        for (int[] query : queries) {
            if (query.length == 0) System.out.println(mf.findMedian() + "  ");
            else mf.addNum(query[0]);
        }
        System.out.println();
    }
}

///  Solution
// medianFinder class
class MedianFinder {
    private final PriorityQueue<Integer> minHeap;
    private final PriorityQueue<Integer> maxHeap;

    // Constructor
    public MedianFinder() {
        this.minHeap = new PriorityQueue<>();
        this.maxHeap = new PriorityQueue<>(Comparator.comparingInt(a -> -a));
    }

    // method to add number
    public void addNum(int num) {
        if (minHeap.isEmpty() || num >= minHeap.peek()) {
            minHeap.offer(num);
        } else maxHeap.offer(num);

        if ((minHeap.size() - maxHeap.size()) != 0 && (minHeap.size() - maxHeap.size()) != 1) {
            if (minHeap.size() < maxHeap.size()) minHeap.offer(maxHeap.poll());
            else maxHeap.offer(minHeap.poll());
        }
    }

    // method to get median right now
    public double findMedian() {
        if (minHeap.size() == maxHeap.size()) return (maxHeap.peek() + minHeap.peek()) / 2.0;
        else return minHeap.peek();
    }
}