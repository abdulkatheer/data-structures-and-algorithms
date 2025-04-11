package io.abdul.dfs.in_order;

import io.abdul.stack.StackWithLinkedList;
import io.abdul.api.BinaryTreeNode;
import io.abdul.api.Stack;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeTraversal {
    /*
    Time Complexity - O(n)
    Space Complexity - O(n) - for the method callstack being created
     */
    public static <E> List<E> traverse(BinaryTreeNode<E> root) {
        List<E> elements = new ArrayList<>();
        preorderTraversal(root, elements);
        return elements;
    }

    /*
    Time Complexity - O(n)
    Space Complexity - O(n) - for the additional stack being created
     */
    public static <E> List<E> traverseItrV1(BinaryTreeNode<E> root) {
        List<E> elements = new ArrayList<>();
        Stack<BinaryTreeNode<E>> s = new StackWithLinkedList<>();
        BinaryTreeNode<E> current = root;

        while (current != null || !s.isEmpty()) { // Iterate until current exists OR stack has elements
            while (current != null) { // Traverse to the left of current and add to stack
                s.push(current);
                current = current.getLeft();
            }

            current = s.pop();
            elements.add(current.getValue()); // Pop one from stack and that's our next element in-order
            current = current.getRight(); // repeat the same with right of current, if not pick next element from the stack
        }

        return elements;
    }

    /*
    Morris Traversal Algorithm
    https://www.youtube.com/watch?v=wGXB9OWhPTg
    Time Complexity - O(n)
    Space Complexity - O(n) - for the additional stack being created
     */
    public static <E extends Comparable<E>> List<E> traverseItrV2(BinaryTreeNode<E> root) {
        List<E> elements = new ArrayList<>();
        BinaryTreeNode<E> current = root;
        while (current != null) {
            if (current.getLeft() == null) {
                elements.add(current.getValue());
                current = current.getRight();
            } else {
                BinaryTreeNode<E> predecessor = findPredecessor(current);
                if (predecessor.getRight() != null) { // a temp link already made to the current node
                    predecessor.setRight(null); // resetting the link
                    elements.add(current.getValue());
                    current = current.getRight(); // When a cycle is completed, the left subtree of the node is completed
                } else {
                    predecessor.setRight(current); // created a temp link to the current node, to come back to it later
                    current = current.getLeft();
                }
            }
        }
        return elements;
    }

    private static <E> void preorderTraversal(BinaryTreeNode<E> root, List<E> container) {
        if (root == null) {
            return;
        }
        preorderTraversal(root.getLeft(), container);
        container.add(root.getValue());
        preorderTraversal(root.getRight(), container);
    }

    private static <E extends Comparable<E>> BinaryTreeNode<E> findPredecessor(BinaryTreeNode<E> root) {
        BinaryTreeNode<E> predecessor = root.getLeft();
        if (predecessor == null) {
            throw new IllegalArgumentException("No predecessor found");
        }
        // Till there is a right element to the predecessor AND the right element is not root
        while (predecessor.getRight() != null && predecessor.getRight() != root) { // modified to check cycle back to root
            predecessor = predecessor.getRight();
        }
        return predecessor;
    }
}
