package io.abdul.dfs.in_order;

import io.abdul.BinarySearchTree;
import io.abdul.api.BinaryTree;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTraversalTest {

    @Test
    void traverse() {
        BinaryTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(9);
        bst.insert(4);
        bst.insert(1);
        bst.insert(6);
        bst.insert(20);
        bst.insert(15);
        bst.insert(170);

        List<Integer> traverse = BinaryTreeTraversal.traverse(bst.getRoot());

        assertEquals(1, traverse.get(0));
        assertEquals(4, traverse.get(1));
        assertEquals(6, traverse.get(2));
        assertEquals(9, traverse.get(3));
        assertEquals(15, traverse.get(4));
        assertEquals(20, traverse.get(5));
        assertEquals(170, traverse.get(6));
    }

    @Test
    void traverseItrV1() {
        BinaryTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(9);
        bst.insert(4);
        bst.insert(1);
        bst.insert(6);
        bst.insert(20);
        bst.insert(15);
        bst.insert(170);

        List<Integer> traverse = BinaryTreeTraversal.traverseItrV1(bst.getRoot());

        assertEquals(1, traverse.get(0));
        assertEquals(4, traverse.get(1));
        assertEquals(6, traverse.get(2));
        assertEquals(9, traverse.get(3));
        assertEquals(15, traverse.get(4));
        assertEquals(20, traverse.get(5));
        assertEquals(170, traverse.get(6));
    }

    @Test
    void traverseItrV2() {
        BinaryTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(9);
        bst.insert(4);
        bst.insert(1);
        bst.insert(6);
        bst.insert(20);
        bst.insert(15);
        bst.insert(170);

        List<Integer> traverse = BinaryTreeTraversal.traverseItrV2(bst.getRoot());

        assertEquals(1, traverse.get(0));
        assertEquals(4, traverse.get(1));
        assertEquals(6, traverse.get(2));
        assertEquals(9, traverse.get(3));
        assertEquals(15, traverse.get(4));
        assertEquals(20, traverse.get(5));
        assertEquals(170, traverse.get(6));
    }
}