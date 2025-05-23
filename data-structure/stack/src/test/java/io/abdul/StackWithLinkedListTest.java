package io.abdul;

import io.abdul.api.LinkedList;
import io.abdul.api.Stack;
import io.abdul.api.exception.OperationNotSupported;
import io.abdul.api.exception.StackUnderflow;
import io.abdul.stack.StackWithLinkedList;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class StackWithLinkedListTest {
    @Test
    void push() throws NoSuchFieldException, IllegalAccessException {
        Stack<Integer> numberStack = new StackWithLinkedList<>();

        LinkedList<Integer> stack = getStack(numberStack);

        numberStack.push(5);
        assertEquals(1, stack.size());
        assertEquals(0, stack.lookup(5).get());

        numberStack.push(4);
        assertEquals(2, stack.size());
        assertEquals(0, stack.lookup(4).get());
        assertEquals(1, stack.lookup(5).get());

        numberStack.push(3);
        assertEquals(3, stack.size());
        assertEquals(0, stack.lookup(3).get());
        assertEquals(1, stack.lookup(4).get());
        assertEquals(2, stack.lookup(5).get());

        numberStack.push(2);
        assertEquals(4, stack.size());
        assertEquals(0, stack.lookup(2).get());
        assertEquals(1, stack.lookup(3).get());
        assertEquals(2, stack.lookup(4).get());
        assertEquals(3, stack.lookup(5).get());

        numberStack.push(1);
        assertEquals(5, stack.size());
        assertEquals(0, stack.lookup(1).get());
        assertEquals(1, stack.lookup(2).get());
        assertEquals(2, stack.lookup(3).get());
        assertEquals(3, stack.lookup(4).get());
        assertEquals(4, stack.lookup(5).get());
    }

    @Test
    void pop() throws NoSuchFieldException, IllegalAccessException {
        Stack<Integer> numberStack = new StackWithLinkedList<>();

        LinkedList<Integer> stack = getStack(numberStack);

        numberStack.push(5);
        assertEquals(1, stack.size());
        assertEquals(0, stack.lookup(5).get());
        assertEquals(5, numberStack.pop());
        assertEquals(0, stack.size());

        numberStack.push(5);
        numberStack.push(4);
        numberStack.push(3);
        assertEquals(3, stack.size());
        assertEquals(3, numberStack.pop());
        assertEquals(2, stack.size());
        assertEquals(4, numberStack.pop());
        assertEquals(1, stack.size());
        assertEquals(5, numberStack.pop());
        assertEquals(0, stack.size());

        assertThrows(StackUnderflow.class, numberStack::pop);
    }

    @Test
    void peek() throws NoSuchFieldException, IllegalAccessException {
        Stack<Integer> numberStack = new StackWithLinkedList<>();

        LinkedList<Integer> stack = getStack(numberStack);

        assertThrows(StackUnderflow.class, numberStack::peek);

        numberStack.push(5);
        assertEquals(1, stack.size());
        assertEquals(5, numberStack.peek());
        assertEquals(1, stack.size());

        numberStack.push(4);
        assertEquals(2, stack.size());
        assertEquals(4, numberStack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    void lookup() {
        Stack<Integer> numberStack = new StackWithLinkedList<>();

        numberStack.push(5);
        numberStack.push(4);
        numberStack.push(3);
        numberStack.push(2);
        numberStack.push(1);

        assertEquals(0, numberStack.lookup(1).get());
        assertEquals(1, numberStack.lookup(2).get());
        assertEquals(2, numberStack.lookup(3).get());
        assertEquals(3, numberStack.lookup(4).get());
        assertEquals(4, numberStack.lookup(5).get());
        assertTrue(numberStack.lookup(6).isEmpty());
    }

    @Test
    void isEmpty() {
        Stack<Integer> numberStack = new StackWithLinkedList<>();

        assertTrue(numberStack.isEmpty());

        numberStack.push(5);
        assertFalse(numberStack.isEmpty());
    }

    @Test
    void isFull() {
        Stack<Integer> numberStack = new StackWithLinkedList<>();

        assertThrows(OperationNotSupported.class, numberStack::isFull);
    }

    @Test
    void size() {
        Stack<Integer> numberStack = new StackWithLinkedList<>();

        assertEquals(0, numberStack.size());
        numberStack.push(5);
        assertEquals(1, numberStack.size());
        numberStack.push(4);
        assertEquals(2, numberStack.size());
        numberStack.push(3);
        assertEquals(3, numberStack.size());
        numberStack.push(2);
        assertEquals(4, numberStack.size());
        numberStack.push(1);
        assertEquals(5, numberStack.size());
    }

    private static <E> LinkedList<E> getStack(Stack<E> numbers) throws NoSuchFieldException, IllegalAccessException {
        Field elementsField = numbers.getClass().getDeclaredField("stack");
        elementsField.setAccessible(true);
        return (LinkedList<E>) elementsField.get(numbers);
    }
}