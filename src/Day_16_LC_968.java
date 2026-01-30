/*
 * https://leetcode.com/problems/binary-tree-cameras
 *
 * # 968. Binary Tree Cameras
 *
 *   Q. You are given the root of a binary tree. We install cameras on the tree nodes where each camera at a node can monitor
 *      its parent, itself, and its immediate children.
 *
 *      Return the minimum number of cameras needed to monitor all nodes of the tree.
 *   Ex.
 *      Input : root = [0, 0, null, 0, 0]
 *      Output: 1
 *      Explanation: One camera is enough to monitor all nodes if placed as shown.
 *
 *  Constraints:
 *          The number of nodes in the tree is in the range [1, 1000].
 *          Node.val == 0
 */

public class Day_16_LC_968 {

    /// Structure
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /// main Method
    public static void main(String[] args) {
        TreeNode[] nodes = {
                new TreeNode(0),
                new TreeNode(0),
                null,
                new TreeNode(0),
                new TreeNode(0)
        };

        nodes[0].left = new TreeNode(1);
        nodes[0].right = new TreeNode(2);
        nodes[1].left = new TreeNode(3);
        nodes[1].right = new TreeNode(4);

        System.out.println("Minimum number of camera to Cover: " + minCameraCover(nodes[0]));
    }

    /// Solution
    static int minCameraCover(TreeNode root) {
        int put = countCamera(root, 1);
        int notPut = countCamera(root, 2);

        System.out.println(put + " " +  notPut);

        return Math.min(put, notPut);
    }

    private static int countCamera(TreeNode root, int requirement) {
        // base case
        if (root == null) return 0;

        //recursive case


        // self work

        return 0;
    }
}