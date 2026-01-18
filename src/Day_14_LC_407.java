/*
 *
 * https://leetcode.com/problems/trapping-rain-water-ii
 *
 * # 407. Trapping Rain Water II
 *
 *   Q. Given an m x n integer matrix heightMap representing the height of each unit cell in a 2D elevation map,
 *      return the volume of water it can trap after raining.
 *    Ex.
 *      Input : heightMap = [[1, 4, 3, 1, 3, 2],
 *                           [3, 2, 1, 3, 2, 4],
 *                           [2, 3, 3, 2, 3, 1]]
 *      Output: 4
 *      Explanation: After the rain, water is trapped between the blocks.
 *                   We have two small ponds 1 and 3 units trapped.
 *                   The total volume of water trapped is 4.
 *
 *  Constraints:
 *          m == heightMap.length
 *          n == heightMap[i].length
 *          1 <= m, n <= 200
 *          0 <= heightMap[i][j] <= 2 * 10⁴
 */

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Day_14_LC_407 {

    /// main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter dimensions of matrix: ");
        int n =  sc.nextInt();
        int m =  sc.nextInt();
        int [][] matrix = new int[n][m];

        System.out.println("Enter matrix elements: ");
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = sc.nextInt();
            }
        }

        System.out.println("Total volume of water it can trap after raining: ");
        System.out.println(trapRainWater(matrix));
    }

    /// Solution
/*
✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘-Path-Compression-✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘✘
TC : O((n*m) * (n*m log(n*m)))
SC : O(n * m)
*/
    static int bruteforce(int[][] heightMap) {
        int n = heightMap.length;
        int m = heightMap[0].length;
        int total = 0;

        int[][] visitedMap = new int[n][m];

        // filling the visited array with -1 except the boundary.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1)
                    visitedMap[i][j] = heightMap[i][j];
                else visitedMap[i][j] = -1;
            }
        }

        // putting water on every cell and storing the min height
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (visitedMap[i][j] == -1) dijkstra(i, j, n, m, heightMap, visitedMap);
                total += (visitedMap[i][j] - heightMap[i][j]);
            }
        }

        return total;
    }

    // checking the nearest point by which water can flow outside.
    private static void dijkstra(int i, int j, int r, int c, int[][] heightMap, int[][] visitedMap) {
        int x = i;
        int y = j;
        int[][] parentRow = new int[r][c];
        int[][] parentCol = new int[r][c];
        boolean[][] visited = new boolean[r][c];
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(h -> Math.max(heightMap[h[0]][h[1]], visitedMap[h[0]][h[1]])));
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, 1, 0, -1};

        pq.offer(new int[]{i, j});
        visited[i][j] = true;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();

            // break if we reach a visited cell
            if (visitedMap[cur[0]][cur[1]] != -1) {
                x = cur[0];
                y = cur[1];
                break;
            }

            // add every direction.
            for (int d = 0; d < 4; d++) {
                int newI = cur[0] + dRow[d];
                int newJ = cur[1] + dCol[d];

                if (0 <= newI && newI < r && 0 <= newJ && newJ < c && !visited[newI][newJ]) {
                    visited[newI][newJ] = true;

                    // keeping a track of parent's position
                    parentRow[newI][newJ] = cur[0];
                    parentCol[newI][newJ] = cur[1];

                    pq.offer(new int[]{newI, newJ});
                }
            }
        }

        // update the whole path.
        while (x != i || y != j) {
            int val = visitedMap[x][y];
            int temp = x;
            x = parentRow[x][y];
            y = parentCol[temp][y];
            visitedMap[x][y] = Math.max(heightMap[x][y], val);
        }
    }

/*
✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔-Multi-Source-Dijkstra-✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔✔
TC : O((R*C) log (R*C))
SC : O(R * C)
*/
    static int trapRainWater(int[][] heightMap) {
        int n = heightMap.length;
        int m = heightMap[0].length;
        int total = 0;

        int[][] visitedMap = new int[n][m];
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(v -> visitedMap[v[0]][v[1]]));
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, 1, 0, -1};

        // adding boundary cells to the queue and mark them as visited
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                    visitedMap[i][j] = heightMap[i][j];
                    pq.offer(new int[]{i, j});
                } else {
                    visitedMap[i][j] = -1;
                }
            }
        }

        // tracking where the water comes from
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();

            // updating the answer
            total += (visitedMap[cur[0]][cur[1]] - heightMap[cur[0]][cur[1]]);

            for (int d = 0; d < 4; d++) {
                int newI = cur[0] + dRow[d];
                int newJ = cur[1] + dCol[d];

                if (newI >= 0 && newI < n && newJ >= 0 && newJ < m && visitedMap[newI][newJ] == -1) {
                    visitedMap[newI][newJ] = Math.max(visitedMap[cur[0]][cur[1]], heightMap[newI][newJ]);
                    pq.offer(new int[]{newI, newJ});
                }
            }
        }

        return total;
    }
}