/**
 *
 * <a href="https://leetcode.com/problems/binary-tree-maximum-path-sum">LeeCode🔗</a>
 *
 * <h1># 124. Binary Tree Maximum Path Sum</h1>
 *
 * <h6>
 *      Q. A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting
 *         them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.
 * </h6>
 * <p>
 *     The path sum of a path is the sum of the node's values in the path.
 * </p>
 * <p>
 *     Given the root of a binary tree, return the maximum path sum of any non-empty path.
 * </p>
 * <h5>Example:</h5>
 * <img src="https://assets.leetcode.com/uploads/2020/10/13/exx2.jpg" alt="not found">
 * <ul>
 *     <li>Input: root = [-10,9,20,null,null,15,7]</li>
 *     <li>Output: 42</li>
 *     <li>Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.</li>
 * </ul>
 * <h5>Constraints:</h5>
 * <ul>
 *     <li>The number of nodes in the tree is in the range [1, 3 * 10⁴].</li>
 *     <li>-1000 <= Node.val <= 1000</li>
 * </ul>
 */

public class Day_17_LC_124 {

    ///  Structure
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /// main Method
    public static void main(String[] args) {
        TreeNode[] nodes = {
                new TreeNode(-10),
                new TreeNode(9),
                new TreeNode(20),
                null,
                null,
                new TreeNode(15),
                new TreeNode(7)
        };

        nodes[0].left = nodes[1];
        nodes[0].right = nodes[2];

        nodes[2].left = nodes[5];
        nodes[2].right = nodes[6];

        System.out.println("Maximum path sum: " + maxPathSum(nodes[0]));
    }

    /// Solution
    static int maxPathSum(TreeNode root) {
        int[] maxPathSum = {Integer.MIN_VALUE};
        maxGain(root, maxPathSum);

        return maxPathSum[0];
    }

    private static int maxGain(TreeNode root, int[] maxPathSum) {
        // base case
        if (root == null) return 0;

        // recursive case
        int leftGain = Math.max(0, maxGain(root.left, maxPathSum));
        int rightGain = Math.max(0, maxGain(root.right, maxPathSum));

        // self work
        maxPathSum[0] = Math.max(maxPathSum[0], leftGain + root.val + rightGain);
        return Math.max(leftGain, rightGain) + root.val;
    }
}