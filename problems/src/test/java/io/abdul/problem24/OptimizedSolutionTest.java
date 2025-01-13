package io.abdul.problem24;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptimizedSolutionTest {

    @Test
    void isValidBST() {
        OptimizedSolution os = new OptimizedSolution();
        TreeNode n1 = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        assertTrue(os.isValidBST(n1));
    }

    @Test
    void isValidBSTTestTwo() {
        TreeNode n5 = new TreeNode(5);
        TreeNode n1 = new TreeNode(1);
        TreeNode n4 = new TreeNode(4);
        TreeNode n3 = new TreeNode(3);
        TreeNode n6 = new TreeNode(6);

        n5.left = n1;
        n5.right = n4;
        n4.left = n3;
        n4.right = n6;

        OptimizedSolution os = new OptimizedSolution();
        assertFalse(os.isValidBST(n5));
    }
}