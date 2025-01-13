package io.abdul.problem24;


import java.util.Stack;

class TreeNode {
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

/**
 * Time Complexity - O(n)
 * Space Complexity - O(n)
 */
public class Solution {

    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> s = new Stack<>();
        TreeNode current = root;
        Integer lastElement = null;

        while (current != null || !s.isEmpty()) { // Iterate until current exists OR stack has elements
            while (current != null) { // Traverse to the left of current and add to stack
                s.push(current);
                current = current.left;
            }

            current = s.pop();
            if (lastElement != null && lastElement >= current.val) { // Next value is smaller than lastElement.
                return false;
            }
            lastElement = current.val;
            current = current.right; // repeat the same with right of current, if not pick next element from the stack
        }
        return true; // all elements are in-order
    }
}