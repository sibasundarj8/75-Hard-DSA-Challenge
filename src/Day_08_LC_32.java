import java.util.Scanner;
import java.util.Stack;

/**
 * <a href="https://leetcode.com/problems/maximal-rectangle/">LeetCode</a>
 *
 * <h4># 85. Maximal Rectangle</h4>
 *
 * <p>
 *     Q. Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and
 *       return its area.
 * </p>
 * <p>
 *     <h6>Ex.</h6><br>
 *     <img src="https://assets.leetcode.com/uploads/2020/09/14/maximal.jpg" alt="-image-">
 * </p>
 * <p>
 *     Input : matrix = [
 *     <br>["1","0","1","0","0"],
 *     <br>["1","0","1","1","1"],
 *     <br>["1","1","1","1","1"],
 *     <br>["1","0","0","1","0"]]
 *     <br>Output: 6
 *     <br>Explanation: The maximal rectangle is shown in the above picture.
 * </p>
 * <p>
 *     <h6>Constraints:</h6>
 *        <br>rows == matrix.length
 *        <br>cols == matrix[i].length
 *        <br>1 <= rows, cols <= 200
 *        <br>matrix[i][j] is '0' or '1'.
 * </p>
 */

public class Day_08_LC_32 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter matrix dimension: ");
        int r =  sc.nextInt();
        int c =  sc.nextInt();

        char[][] matrix = new char[r][c];

        System.out.println("Enter matrix elements: (either 0 or 1)");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int input = sc.nextInt();
                if (input != 0 && input != 1) return;
                matrix[i][j] = (char) (input + '0');
            }
        }

        System.out.println("Largest rectangle area: " + maximalRectangle(matrix));
    }

    /// Solution
/*
⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬-Brute-Force-⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬⤬
TC : O(r³ * c³)
SC : O(1)
*/
    static int bruteForce(char[][] matrix) {
        int r = matrix.length, c = matrix[0].length;
        int max = 0;

        for (int r1 = 0; r1 < r; r1++) { // top-left corner  ---> O(r * c)
            for (int c1 = 0; c1 < c; c1++) {

                for (int r2 = r1; r2 < r; r2++) { // bottom-right corner  ---> O(r * c)
                    for (int c2 = c1; c2 < c; c2++) {

                        boolean ok = true;

                        outer:
                        // traversing the sub-matrix  ---> O(r * c)
                        for (int i = r1; i <= r2; i++) {
                            for (int j = c1; j <= c2; j++) {

                                if (matrix[i][j] == '0') {
                                    ok = false;
                                    break outer;
                                }

                            }
                        }

                        if (ok) {
                            int area = (r2 - r1 + 1) * (c2 - c1 + 1);
                            max = Math.max(max, area);
                        }

                    }
                }

            }
        }

        return max;
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Histogram + Monotonic Stack-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O(r * c)
SC : O(r)
*/
    static int maximalRectangle(char[][] matrix) {
        int r = matrix.length;
        int c = matrix[0].length;
        int max = 0;

        int[] arr = new int[c];

        for (char[] chars : matrix) {

            for (int j = 0; j < c; j++) {
                int cur = chars[j] - '0';
                arr[j] += (cur == 0) ? -arr[j] : cur;
            }

            max = Math.max(max, largestRectangleInHistogram(arr));
        }

        return max;
    }

    private static int largestRectangleInHistogram(int[] arr) {
        int n = arr.length;
        int max = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {

            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int cur = stack.pop();
                int pse = (stack.isEmpty()) ? -1 : stack.peek();

                int height = arr[cur];
                int width = i - pse - 1;
                int area = height * width;

                max = Math.max(max, area);
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            int pse = (stack.isEmpty()) ? -1 : stack.peek();

            int height = arr[cur];
            int width = n - pse - 1;
            int area = height * width;

            max = Math.max(max, area);
        }

        return max;
    }
}