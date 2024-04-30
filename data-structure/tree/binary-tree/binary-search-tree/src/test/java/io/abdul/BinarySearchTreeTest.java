package io.abdul;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    @Test
    void insert() {
        testInteger();
        testString();
    }

    @Test
    void lookup() {
        BinarySearchTree<String> stringTree = new BinarySearchTree<>();

        assertTrue(stringTree.lookup("A").isEmpty());

        stringTree.insert("A");
        assertEquals("A", stringTree.lookup("A").get());

        stringTree.insert("B");
        assertEquals("B", stringTree.lookup("B").get());

        stringTree.insert("C");
        assertEquals("C", stringTree.lookup("C").get());

        stringTree.insert("D");
        assertEquals("D", stringTree.lookup("D").get());

        stringTree.insert("E");
        assertEquals("E", stringTree.lookup("E").get());

        stringTree.insert("F");
        assertEquals("F", stringTree.lookup("F").get());
    }

    @Test
    void remove() {
        BinarySearchTree<String> stringTree = new BinarySearchTree<>();

        testEmptyTree(stringTree);

        testRemovingOnlyElementInTheTree(stringTree);

        testRemovingLeafNodeWhichIsOnTheLeftOfParent(stringTree);

        testRemovingLeafNodeWhichIsOnTheRightOfParent(stringTree);

        testRemovingRootNodeWithOnlyOneChildOnTheRight(stringTree);

        testRemovingRootNodeWithOnlyOneChildOnTheLeft(stringTree);

        testRemovingParentNodeWithOnlyOneChildOnTheRight(stringTree);

        testRemovingParentNodeWithOnlyOneChildOnTheLeft(stringTree);

        testRemovingRootWithTwoChildren(stringTree);

        testRemovingRightParentWithTwoChildren(stringTree);

        testRemovingLeftParentWithTwoChildren(stringTree);
    }

    private static void testRemovingRightParentWithTwoChildren(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("E");
        stringTree.insert("G");
        stringTree.insert("F");
        stringTree.insert("H");
        assertTrue(stringTree.remove("G"));
        assertEquals(3, stringTree.size());
        assertTrue(stringTree.remove("F"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("E"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("H"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingLeftParentWithTwoChildren(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("E");
        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");
        assertTrue(stringTree.remove("B"));
        assertEquals(3, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("E"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingRootWithTwoChildren(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");
        assertTrue(stringTree.remove("B"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingParentNodeWithOnlyOneChildOnTheRight(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("A");
        stringTree.insert("B");
        stringTree.insert("C");
        assertTrue(stringTree.remove("B"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingParentNodeWithOnlyOneChildOnTheLeft(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("C");
        stringTree.insert("B");
        stringTree.insert("A");
        assertTrue(stringTree.remove("B"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingLeafNodeWhichIsOnTheLeftOfParent(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");
        assertTrue(stringTree.remove("A"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("B"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingLeafNodeWhichIsOnTheRightOfParent(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");
        assertTrue(stringTree.remove("C"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("B"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingRootNodeWithOnlyOneChildOnTheRight(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("A");
        stringTree.insert("B");
        assertTrue(stringTree.remove("A"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("B"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingRootNodeWithOnlyOneChildOnTheLeft(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        assertTrue(stringTree.remove("B"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private static void testRemovingOnlyElementInTheTree(BinarySearchTree<String> stringTree) {
        stringTree.empty();

        stringTree.insert("A");
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private static void testEmptyTree(BinarySearchTree<String> stringTree) {
        assertFalse(stringTree.remove("A"));
    }

    private static void testString() {
        BinarySearchTree<String> stringTree = new BinarySearchTree<>();

        stringTree.insert("A");
        assertEquals(1, stringTree.size());

        stringTree.insert("B");
        stringTree.insert("C");

        stringTree.insert("D");
        stringTree.insert("E");

        stringTree.insert("F");
        stringTree.insert("G");

        stringTree.insert("H");
        stringTree.insert("I");

        stringTree.insert("J");
        stringTree.insert("K");

        assertEquals(11, stringTree.size());
        List<String> elements = stringTree.getElements();
        assertEquals("A", elements.get(0));
        assertEquals("B", elements.get(1));
        assertEquals("C", elements.get(2));
        assertEquals("D", elements.get(3));
        assertEquals("E", elements.get(4));
        assertEquals("K", elements.get(10));
    }

    private static void testInteger() {
        BinarySearchTree<Integer> integerTree = new BinarySearchTree<>();

        integerTree.insert(5);
        assertEquals(1, integerTree.size());

        integerTree.insert(6);
        integerTree.insert(4);

        integerTree.insert(7);
        integerTree.insert(3);

        integerTree.insert(8);
        integerTree.insert(2);

        integerTree.insert(9);
        integerTree.insert(1);

        integerTree.insert(10);
        integerTree.insert(0);

        assertEquals(11, integerTree.size());
        List<Integer> elements = integerTree.getElements();
        assertEquals(0, elements.get(0));
        assertEquals(1, elements.get(1));
        assertEquals(2, elements.get(2));
        assertEquals(3, elements.get(3));
        assertEquals(4, elements.get(4));
        assertEquals(10, elements.get(10));
    }
}