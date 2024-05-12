package io.abdul;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedBlackTreeTest {
    static ClassLoader classLoader = RedBlackTree.class.getClassLoader();
    static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void insert() throws IOException, NoSuchFieldException, IllegalAccessException {
        testInsertingRoot();

        testInsertingLeftLeftToRootWithUncleAsRed();

        testInsertingLeftLeftToRootWithUncleAsBlack();

        testInsertingLeftRightToRootWithUncleAsRed();

        testInsertingLeftRightToRootWithUncleAsBlack();

        testInsertingRightRightToRootWithUncleAsRed();

        testInsertingRightRightToRootWithUncleAsBlack();

        testInsertingRightLeftToRootWithUncleAsRed();

        testInsertingRightLeftToRootWithUncleAsBlack();
    }

    private static void testInsertingRightLeftToRootWithUncleAsBlack() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(110);
        rbt.insert(120);
        rbt.insert(115);

        testInsert(rbt, "insert-case-8.json");

        // insert-case-8.json
    }

    private static void testInsert(RedBlackTree<?> rbt, String filePath) throws NoSuchFieldException, IllegalAccessException, IOException {
        String output = objectMapper.writeValueAsString(getRootNode(rbt));
        try (InputStream is = new FileInputStream(classLoader.getResource(filePath).getFile())) {
            byte[] expectedOutputTree = is.readAllBytes();
            assertEquals(objectMapper.readTree(expectedOutputTree), objectMapper.readTree(output));
        }
    }


    private static void testInsertingRightLeftToRootWithUncleAsRed() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        rbt.insert(110);
        rbt.insert(120);
        rbt.insert(115);

        testInsert(rbt, "insert-case-7.json");

        // insert-case-7.json
    }

    private static void testInsertingRightRightToRootWithUncleAsBlack() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        rbt.insert(110);
        rbt.insert(120);

        testInsert(rbt, "insert-case-6.json");

        // insert-case-6.json
    }

    private static void testInsertingRightRightToRootWithUncleAsRed() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        rbt.insert(90);
        rbt.insert(110);
        rbt.insert(120);

        testInsert(rbt, "insert-case-5.json");

        // insert-case-5.json
    }

    private static void testInsertingLeftRightToRootWithUncleAsBlack() {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        rbt.insert(90);
        rbt.insert(95);
        // insert-case-4.json
    }

    private static void testInsertingLeftRightToRootWithUncleAsRed() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        rbt.insert(110);
        rbt.insert(90);
        rbt.insert(95);

        testInsert(rbt, "insert-case-3.json");

        // insert-case-3.json
    }

    private static void testInsertingLeftLeftToRootWithUncleAsBlack() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        rbt.insert(90);
        rbt.insert(80);

        testInsert(rbt, "insert-case-2.json");

        // insert-case-2.json
    }

    private static void testInsertingLeftLeftToRootWithUncleAsRed() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        rbt.insert(110);
        rbt.insert(90);
        rbt.insert(80);

        testInsert(rbt, "insert-case-1.json");

        // insert-case-1.json
    }

    private static void testInsertingRoot() throws IOException, NoSuchFieldException, IllegalAccessException {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();
        rbt.insert(100);
        assertEquals("100", rbt.toString());

        testInsert(rbt, "insert-case-0.json");
        // insert-case-0-input.json
    }

    @Test
    void lookup() {
    }

    @Test
    void remove() {
    }

    private static <E extends Comparable<E>> RedBlackTree.Node<E> getRootNode(RedBlackTree<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("root");
        elementsField.setAccessible(true);
        return (RedBlackTree.Node<E>) elementsField.get(numbers);
    }
}