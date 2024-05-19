package io.abdul;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.abdul.api.exception.ElementNotFound;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BTreeTest {
    static ClassLoader classLoader = BTree.class.getClassLoader();
    static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void insert() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        testInsertingInRootLeafNode();

        testFirstRootSplit();

        testTwoSplits();

        testThreeSplits();

        testFourSplits();

        testFiveSplits();

        testSixSplits();

        testSevenSplits();

        testEightSplits();

        testTenSplits();

        testTwelveSplits();

        testThirteenSplits();
//        BTree.Node<Integer> rootNode = getRootNode(numbers);
//        System.out.println(objectMapper.writeValueAsString(rootNode));
    }

    @Test
    void lookup() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        assertEquals(1, numbers.lookup(1).get());
        numbers.insert(2);
        assertEquals(2, numbers.lookup(2).get());
        numbers.insert(3);
        assertEquals(3, numbers.lookup(3).get());
        numbers.insert(4);
        assertEquals(4, numbers.lookup(4).get());
        numbers.insert(5);
        assertEquals(5, numbers.lookup(5).get());
        numbers.insert(6);
        assertEquals(6, numbers.lookup(6).get());
        numbers.insert(7);
        assertEquals(7, numbers.lookup(7).get());
        numbers.insert(8);
        assertEquals(8, numbers.lookup(8).get());
        numbers.insert(9);
        assertEquals(9, numbers.lookup(9).get());
        numbers.insert(10);
        assertEquals(10, numbers.lookup(10).get());
        numbers.insert(11);
        assertEquals(11, numbers.lookup(11).get());
        numbers.insert(12);
        assertEquals(12, numbers.lookup(12).get());
        numbers.insert(13);
        assertEquals(13, numbers.lookup(13).get());
        numbers.insert(14);
        assertEquals(14, numbers.lookup(14).get());
        numbers.insert(15);
        assertEquals(15, numbers.lookup(15).get());
        numbers.insert(16);
        assertEquals(16, numbers.lookup(16).get());
        numbers.insert(17);
        assertEquals(17, numbers.lookup(17).get());
        numbers.insert(18);
        assertEquals(18, numbers.lookup(18).get());
        numbers.insert(19);
        assertEquals(19, numbers.lookup(19).get());
        numbers.insert(20);
        assertEquals(20, numbers.lookup(20).get());
        numbers.insert(21);
        assertEquals(21, numbers.lookup(21).get());
        numbers.insert(22);
        assertEquals(22, numbers.lookup(22).get());
        numbers.insert(23);
        assertEquals(23, numbers.lookup(23).get());
        numbers.insert(24);
        assertEquals(24, numbers.lookup(24).get());
        numbers.insert(25);
        assertEquals(25, numbers.lookup(25).get());
        numbers.insert(26);
        assertEquals(26, numbers.lookup(26).get());
        numbers.insert(27);
        assertEquals(27, numbers.lookup(27).get());
        numbers.insert(28);
        assertEquals(28, numbers.lookup(28).get());
        numbers.insert(29);
        assertEquals(29, numbers.lookup(29).get());
        numbers.insert(30);
        assertEquals(30, numbers.lookup(30).get());
        numbers.insert(31);
        assertEquals(31, numbers.lookup(31).get());
        numbers.insert(32);
        assertEquals(32, numbers.lookup(32).get());
        numbers.insert(33);
        assertEquals(33, numbers.lookup(33).get());
        numbers.insert(34);
        assertEquals(34, numbers.lookup(34).get());
        numbers.insert(35);
        assertEquals(35, numbers.lookup(35).get());
        assertThrows(ElementNotFound.class, () -> numbers.lookup(100));
        assertThrows(ElementNotFound.class, () -> numbers.lookup(-1));
    }

    private static void testThirteenSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        numbers.insert(15);
        numbers.insert(16);
        numbers.insert(17);
        numbers.insert(18);
        numbers.insert(19);
        numbers.insert(20);
        numbers.insert(21);
        numbers.insert(22);
        numbers.insert(23);
        numbers.insert(24);
        numbers.insert(25);
        numbers.insert(26);
        numbers.insert(27);
        numbers.insert(28);
        numbers.insert(29);
        numbers.insert(30);
        numbers.insert(31);
        numbers.insert(32);
        numbers.insert(33);
        numbers.insert(34);
        numbers.insert(35);
        assertEquals(35, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14 --> " +
                "15 --> 16 --> 17 --> 18 --> 19 --> 20 --> 21 --> 22 --> 23 --> 24 --> 25 --> 26 --> 27 --> 28 --> " +
                "29 --> 30 --> 31 --> 32 --> 33 --> 34 --> 35", numbers.toString());
        // insert-case-12.json
    }

    private static void testTwelveSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        numbers.insert(15);
        numbers.insert(16);
        numbers.insert(17);
        numbers.insert(18);
        numbers.insert(19);
        numbers.insert(20);
        numbers.insert(21);
        numbers.insert(22);
        numbers.insert(23);
        numbers.insert(24);
        numbers.insert(25);
        numbers.insert(26);
        numbers.insert(27);
        numbers.insert(28);
        numbers.insert(29);
        numbers.insert(30);
        numbers.insert(31);
        numbers.insert(32);
        assertEquals(32, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14 --> " +
                "15 --> 16 --> 17 --> 18 --> 19 --> 20 --> 21 --> 22 --> 23 --> 24 --> 25 --> 26 --> 27 --> 28 --> 29 --> 30 --> 31 --> 32", numbers.toString());
        // insert-case-11.json
    }

    private static void testTenSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        numbers.insert(15);
        numbers.insert(16);
        numbers.insert(17);
        numbers.insert(18);
        numbers.insert(19);
        numbers.insert(20);
        numbers.insert(21);
        numbers.insert(22);
        numbers.insert(23);
        numbers.insert(24);
        numbers.insert(25);
        numbers.insert(26);
        numbers.insert(27);
        numbers.insert(28);
        numbers.insert(29);
        assertEquals(29, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14 --> " +
                "15 --> 16 --> 17 --> 18 --> 19 --> 20 --> 21 --> 22 --> 23 --> 24 --> 25 --> 26 --> 27 --> 28 --> 29", numbers.toString());
        // insert-case-10.json
    }

    private static void testEightSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        numbers.insert(15);
        numbers.insert(16);
        numbers.insert(17);
        numbers.insert(18);
        numbers.insert(19);
        numbers.insert(20);
        numbers.insert(21);
        numbers.insert(22);
        numbers.insert(23);
        numbers.insert(24);
        numbers.insert(25);
        numbers.insert(26);
        assertEquals(26, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14 --> " +
                "15 --> 16 --> 17 --> 18 --> 19 --> 20 --> 21 --> 22 --> 23 --> 24 --> 25 --> 26", numbers.toString());
        // insert-case-9.json
    }

    private static void testSevenSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        numbers.insert(15);
        numbers.insert(16);
        numbers.insert(17);
        numbers.insert(18);
        numbers.insert(19);
        numbers.insert(20);
        numbers.insert(21);
        numbers.insert(22);
        numbers.insert(23);
        assertEquals(23, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14 --> " +
                "15 --> 16 --> 17 --> 18 --> 19 --> 20 --> 21 --> 22 --> 23", numbers.toString());
        // insert-case-8.json
    }

    private static void testSixSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        numbers.insert(15);
        numbers.insert(16);
        numbers.insert(17);
        numbers.insert(18);
        numbers.insert(19);
        numbers.insert(20);
        assertEquals(20, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14 --> " +
                "15 --> 16 --> 17 --> 18 --> 19 --> 20", numbers.toString());
        // insert-case-7.json
    }

    private static void testFiveSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        numbers.insert(15);
        numbers.insert(16);
        numbers.insert(17);
        assertEquals(17, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14 --> " +
                "15 --> 16 --> 17", numbers.toString());
        // insert-case-6.json
    }

    private static void testFourSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        numbers.insert(12);
        numbers.insert(13);
        numbers.insert(14);
        assertEquals(14, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11 --> 12 --> 13 --> 14", numbers.toString());
        // insert-case-4.json
    }

    private static void testThreeSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        numbers.insert(9);
        numbers.insert(10);
        numbers.insert(11);
        assertEquals(11, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8 --> 9 --> 10 --> 11", numbers.toString());
        // insert-case-3.json
    }

    private static void testTwoSplits() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        numbers.insert(6);
        numbers.insert(7);
        numbers.insert(8);
        assertEquals(8, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5 --> 6 --> 7 --> 8", numbers.toString());
        // insert-case-2.json
    }

    private static void testFirstRootSplit() throws NoSuchFieldException, IllegalAccessException, JsonProcessingException {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(2);
        numbers.insert(3);
        numbers.insert(4);
        numbers.insert(5);
        assertEquals(5, numbers.size());
        assertEquals("1 --> 2 --> 3 --> 4 --> 5", numbers.toString());
        // insert-case-1.json
    }

    private static void testInsertingInRootLeafNode() {
        BTree<Integer> numbers = new BTree<>(5);
        numbers.insert(1);
        numbers.insert(5);
        numbers.insert(2);
        numbers.insert(4);

        assertEquals(4, numbers.size());
    }


//    @Test
//    void childNodeForInsertionWithOneNode() {
//        BTree.Node<Integer> integers = new BTree.Node<>(4, 2);
//
//        integers.elements[0] = 10;
//        integers.size++;
//        BTree.Node node1 = new BTree.Node(1, 1);
//        BTree.Node node2 = new BTree.Node(1, 1);
//        integers.children[0] = node1;
//        integers.children[1] = node2;
//
//        assertEquals(node1, integers.childNodeForInsertion(8));
//        assertEquals(node2, integers.childNodeForInsertion(11));
//
//        assertThrows(IllegalArgumentException.class, () -> integers.childNodeForInsertion(10));
//    }
//
//    @Test
//    void childNodeForInsertionWithTwoNodes() {
//        BTree.Node<Integer> integers = new BTree.Node<>(4, 2);
//
//        integers.elements[0] = 10;
//        integers.size++;
//        integers.elements[1] = 20;
//        integers.size++;
//        BTree.Node node1 = new BTree.Node(1, 1);
//        BTree.Node node2 = new BTree.Node(1, 1);
//        BTree.Node node3 = new BTree.Node(1, 1);
//
//        integers.children[0] = node1;
//        integers.children[1] = node2;
//        integers.children[2] = node3;
//
//        assertEquals(node1, integers.childNodeForInsertion(8));
//        assertEquals(node2, integers.childNodeForInsertion(11));
//        assertEquals(node3, integers.childNodeForInsertion(25));
//
//        assertThrows(IllegalArgumentException.class, () -> integers.childNodeForInsertion(10));
//    }
//
//    @Test
//    void childNodeForInsertionWithFourNodes() {
//        BTree.Node<Integer> integers = new BTree.Node<>(4, 2);
//
//        integers.elements[0] = 10;
//        integers.size++;
//        integers.elements[1] = 20;
//        integers.size++;
//        integers.elements[2] = 30;
//        integers.size++;
//        BTree.Node node1 = new BTree.Node(1, 1);
//        BTree.Node node2 = new BTree.Node(1, 1);
//        BTree.Node node3 = new BTree.Node(1, 1);
//        BTree.Node node4 = new BTree.Node(1, 1);
//
//        integers.children[0] = node1;
//        integers.children[1] = node2;
//        integers.children[2] = node3;
//        integers.children[3] = node4;
//
//        assertEquals(node1, integers.childNodeForInsertion(8));
//        assertEquals(node2, integers.childNodeForInsertion(11));
//        assertEquals(node3, integers.childNodeForInsertion(25));
//        assertEquals(node4, integers.childNodeForInsertion(100));
//
//        assertThrows(IllegalArgumentException.class, () -> integers.childNodeForInsertion(10));
//    }

//    @Test
//    void addElement() {
//        BTree.Node<Integer> integers = new BTree.Node<>(4, 2);
//        integers.addElement(1);
//        integers.addElement(5);
//        integers.addElement(2);
//        integers.addElement(3);
//
//        System.out.println(integers.elements);
//    }

    private static <E extends Comparable<E>> BTree.Node<E> getRootNode(BTree<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("root");
        elementsField.setAccessible(true);
        return (BTree.Node<E>) elementsField.get(numbers);
    }
}