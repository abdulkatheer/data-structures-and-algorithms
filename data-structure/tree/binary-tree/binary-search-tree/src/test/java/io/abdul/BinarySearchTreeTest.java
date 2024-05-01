package io.abdul;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {
    ClassLoader classLoader = getClass().getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

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
    void remove() throws NoSuchFieldException, IllegalAccessException, IOException {
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

        testRemovingNonRootNodeWithTwoChildrenAndSuccessorIsImmediateRightAndSuccessorIsHavingOneChild();

        testRemovingNonRootNodeWithTwoChildrenAndSuccessorIsFarFromNodeAndSuccessorIsHavingOneChild();

        testRemovingNonRootNodeWithTwoChildrenAndSuccessorFarFromNodeAndSuccessorIsHavingNoChild();
    }

    private void testRemovingNonRootNodeWithTwoChildrenAndSuccessorIsFarFromNodeAndSuccessorIsHavingOneChild() throws NoSuchFieldException, IllegalAccessException, IOException {
        BinarySearchTree<Integer> numbers = new BinarySearchTree<>();
        numbers.insert(100);
        numbers.insert(9);
        numbers.insert(105);
        numbers.insert(7);
        numbers.insert(40);
        numbers.insert(30);
        numbers.insert(10);
        numbers.insert(15);
        numbers.insert(20);

        removeAndTest(numbers, "remove/case1_input.json", "remove/case1_output.json", 9, 8);
    }

    private <E extends Comparable<E>> void removeAndTest(BinarySearchTree<E> numbers, String inputFilePath,
                                                         String outputFilePath, E elementToBeRemoved, int expectedSize)
            throws NoSuchFieldException, IllegalAccessException, IOException {
        BinarySearchTree.Node<E> rootNode;
        rootNode = getRootNode(numbers);

        String input = objectMapper.writeValueAsString(rootNode);
        try (InputStream is = new FileInputStream(classLoader.getResource(inputFilePath).getFile())) {
            byte[] expectedInputTree = is.readAllBytes();
            assertEquals(objectMapper.readTree(expectedInputTree), objectMapper.readTree(input));
        }

        assertTrue(numbers.remove(elementToBeRemoved));

        rootNode = getRootNode(numbers);
        String output = objectMapper.writeValueAsString(rootNode);
        if (outputFilePath == null) {
            assertEquals("null", output);
        } else {
            try (InputStream is = new FileInputStream(classLoader.getResource(outputFilePath).getFile())) {
                byte[] expectedOutputTree = is.readAllBytes();
                assertEquals(objectMapper.readTree(expectedOutputTree), objectMapper.readTree(output));
            }
        }
        assertEquals(expectedSize, numbers.size());
    }

    private void testRemovingNonRootNodeWithTwoChildrenAndSuccessorFarFromNodeAndSuccessorIsHavingNoChild() throws IOException, NoSuchFieldException, IllegalAccessException {
        BinarySearchTree<Integer> numbers = new BinarySearchTree<>();
        numbers.insert(12);
        numbers.insert(11);
        numbers.insert(1);
        numbers.insert(0);
        numbers.insert(6);
        numbers.insert(5);
        numbers.insert(7);
        numbers.insert(4);
        numbers.insert(8);
        numbers.insert(3);
        numbers.insert(9);

        assertEquals(List.of(0, 1, 3, 4, 5, 6, 7, 8, 9, 11, 12), numbers.getElements());

        removeAndTest(numbers, "remove/case3_input.json", "remove/case3_output.json", 6, 10);
    }

    private void testRemovingNonRootNodeWithTwoChildrenAndSuccessorIsImmediateRightAndSuccessorIsHavingOneChild() throws NoSuchFieldException, IllegalAccessException, IOException {
        BinarySearchTree<Integer> numbers = new BinarySearchTree<>();

        numbers.insert(12);
        numbers.insert(5);
        numbers.insert(3);
        numbers.insert(7);
        numbers.insert(2);
        numbers.insert(1);
        numbers.insert(9);
        numbers.insert(8);
        numbers.insert(11);
        numbers.insert(14);
        numbers.insert(13);
        numbers.insert(17);
        numbers.insert(20);
        numbers.insert(18);

        assertEquals(14, numbers.size());
        assertEquals(List.of(1, 2, 3, 5, 7, 8, 9, 11, 12, 13, 14, 17, 18, 20), numbers.getElements());

        removeAndTest(numbers, "remove/case2_input.json", "remove/case2_output.json", 14, 13);
    }

    private void testRemovingRightParentWithTwoChildren(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IOException, IllegalAccessException {
        stringTree.empty();

        stringTree.insert("E");
        stringTree.insert("G");
        stringTree.insert("F");
        stringTree.insert("H");
        removeAndTest(stringTree, "remove/case12_input.json", "remove/case12_output.json", "G", 3);
        assertTrue(stringTree.remove("F"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("E"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("H"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingLeftParentWithTwoChildren(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IOException, IllegalAccessException {
        stringTree.empty();

        stringTree.insert("E");
        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");
        removeAndTest(stringTree, "remove/case13_input.json", "remove/case13_output.json", "B", 3);
        assertTrue(stringTree.remove("A"));
        assertEquals(2, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("E"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingRootWithTwoChildren(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IOException, IllegalAccessException {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");
        removeAndTest(stringTree, "remove/case11_input.json", "remove/case11_output.json", "B", 2);
        assertTrue(stringTree.remove("A"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingParentNodeWithOnlyOneChildOnTheRight(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IOException, IllegalAccessException {
        stringTree.empty();

        stringTree.insert("A");
        stringTree.insert("B");
        stringTree.insert("C");
        removeAndTest(stringTree, "remove/case9_input.json", "remove/case9_output.json", "B", 2);
        assertTrue(stringTree.remove("C"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingParentNodeWithOnlyOneChildOnTheLeft(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IOException, IllegalAccessException {
        stringTree.empty();

        stringTree.insert("C");
        stringTree.insert("B");
        stringTree.insert("A");
        removeAndTest(stringTree, "remove/case10_input.json", "remove/case10_output.json", "B", 2);
        assertTrue(stringTree.remove("C"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingLeafNodeWhichIsOnTheLeftOfParent(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IllegalAccessException, IOException {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");

        removeAndTest(stringTree, "remove/case5_input.json", "remove/case5_output.json", "A", 2);

        assertTrue(stringTree.remove("B"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("C"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingLeafNodeWhichIsOnTheRightOfParent(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IllegalAccessException, IOException {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        stringTree.insert("C");
        removeAndTest(stringTree, "remove/case6_input.json", "remove/case6_output.json", "C", 2);
        assertTrue(stringTree.remove("B"));
        assertEquals(1, stringTree.size());
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingRootNodeWithOnlyOneChildOnTheRight(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IllegalAccessException, IOException {
        stringTree.empty();

        stringTree.insert("A");
        stringTree.insert("B");
        removeAndTest(stringTree, "remove/case7_input.json", "remove/case7_output.json", "A", 1);
        assertTrue(stringTree.remove("B"));
        assertEquals(0, stringTree.size());
    }

    private void print(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        BinarySearchTree.Node<String> rootNode = getRootNode(stringTree);
        System.out.println(objectMapper.writeValueAsString(rootNode));
    }

    private void testRemovingRootNodeWithOnlyOneChildOnTheLeft(BinarySearchTree<String> stringTree) throws NoSuchFieldException, IOException, IllegalAccessException {
        stringTree.empty();

        stringTree.insert("B");
        stringTree.insert("A");
        removeAndTest(stringTree, "remove/case8_input.json", "remove/case8_output.json", "B", 1);
        assertTrue(stringTree.remove("A"));
        assertEquals(0, stringTree.size());
    }

    private void testRemovingOnlyElementInTheTree(BinarySearchTree<String> stringTree) throws IOException, NoSuchFieldException, IllegalAccessException {
        stringTree.empty();

        stringTree.insert("A");

        removeAndTest(stringTree, "remove/case4_input.json", null, "A", 0);
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

    private static <E extends Comparable<E>> BinarySearchTree.Node<E> getRootNode(BinarySearchTree<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("root");
        elementsField.setAccessible(true);
        return (BinarySearchTree.Node<E>) elementsField.get(numbers);
    }
}