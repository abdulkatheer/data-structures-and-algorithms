package io.abdul.problem24;


/**
 * Using Morris Traversal
 * Time Complexity - O(n)
 * Space Complexity - O(1)
 */
public class OptimizedSolution {

    public boolean isValidBST(TreeNode root) {
        Integer lastElement = null;

        TreeNode current = root;
        while (current != null) {
            if (current.left == null) {
                if (lastElement != null && lastElement >= current.val) { // Next value is smaller than lastElement.
                    return false;
                }
                lastElement = current.val;
                current = current.right;
            } else {
                TreeNode predecessor = findPredecessor(current);
                if (predecessor.right != null) { // a temp link already made to the current node
                    predecessor.right = null; // resetting the link
                    if (lastElement != null && lastElement >= current.val) { // Next value is smaller than lastElement.
                        return false;
                    }
                    lastElement = current.val;
                    current = current.right; // When a cycle is completed, the left subtree of the node is completed
                } else {
                    predecessor.right = current; // created a temp link to the current node, to come back to it later
                    current = current.left;
                }
            }
        }
        return true; // all elements are in-order
    }

    private static TreeNode findPredecessor(TreeNode root) {
        TreeNode predecessor = root.left;
        if (predecessor == null) {
            throw new IllegalArgumentException("No predecessor found");
        }
        // Till there is a right element to the predecessor AND the right element is not root
        while (predecessor.right != null && predecessor.right != root) { // modified to check cycle back to root
            predecessor = predecessor.right;
        }
        return predecessor;
    }
}