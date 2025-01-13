package io.abdul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.abdul.api.BinaryTree;
import io.abdul.api.BinaryTreeNode;
import io.abdul.api.Tree;
import io.abdul.api.exception.DuplicateElement;
import io.abdul.api.exception.ElementNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BstUsingRecursion<E extends Comparable<E>> implements BinaryTree<E> {
    private Node<E> root;
    private int size;

    @Override
    public void insert(E element) {
        root = createNode(root, element);
        size++;
    }

    @Override
    public Optional<E> lookup(E element) {
        try {
            return Optional.of(findElement(root, element).value);
        } catch (ElementNotFound e) {
            System.err.println(e);
            return Optional.empty();
        }
    }

    @Override
    public boolean remove(E element) {
        try {
            root = deleteNode(root, element);
            size--;
            return true;
        } catch (ElementNotFound e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public void empty() {
        root = null;
    }

    @Override
    public List<E> getElements() {
        ArrayList<E> elements = new ArrayList<>();
        inOrderTraversal(root, elements);
        return elements;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public BinaryTreeNode<E> getRoot() {
        return root;
    }

    /**
     * If root is null, returns a new Node<E><br/>
     * If element is smaller than root, try inserting in the left and return the node<br/>
     * If element is bigger than root, try inserting in the right and return the node<br/>
     * Recursive case 2 and 3 will boil down to Base case
     *
     * @param root    Root node of the tree or subtree to which the element has to be inserted
     * @param element Element to be inserted
     * @param <E>     Type of the element
     * @return Returns either the new node (empty tree) or the root of the subtree where node will be inserted
     */
    private static <E extends Comparable<E>> Node<E> createNode(Node<E> root, E element) {
        // Base case - Tree is empty, creating new Node<E>
        if (root == null) {
            return new Node<>(element);
        }
        int compare = element.compareTo(root.value);
        if (compare < 0) {
            // Recursive case 1 - Try creating node in the left
            root.left = createNode(root.left, element);
        } else if (compare > 0) {
            // Recursive case 2 - Try creating node in the right
            root.right = createNode(root.right, element);
        } else {
            throw new DuplicateElement("Element=" + element + " already exists");
        }
        return root;
    }

    /**
     * Deletes element from the tree with given root Node<E><br/>
     * Recursive case 1, 2 & 3 always boils down to Base case 1, 2 & 3<br/>
     *
     * @param root    Root node of the tree or subtree from which the element has to be deleted
     * @param element Element to be deleted
     * @param <E>     Element type
     * @return New root of the tree
     */
    private static <E extends Comparable<E>> Node<E> deleteNode(Node<E> root, E element) {
        // Base case 1 - Not found case
        if (root == null) {
            throw new ElementNotFound("Element=" + element + " not found");
        }
        int compare = element.compareTo(root.value);
        if (compare < 0) { // Recursive case 1 - Element is smaller, so look in the left subtree
            root.left = deleteNode(root.left, element);
        } else if (compare > 0) {
            root.right = deleteNode(root.right, element); // Recursive case 2 - Element is bigger, so look in the right subtree
        } else {
            // Base case 2 - No child case
            if (root.isLeaf()) {
                return null;
            }
            // Base case 3 - Single child case
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            E successor = findSuccessor(root.right);
            root.value = successor; // copied value of successor

            // Recursive case 3 - Element is matched, so remove it
            root.right = deleteNode(root.right, successor);
        }
        return root;
    }

    /**
     * Finds element in the given subtree with root<br/>
     * Base case 1 - Throws ElementNotFound when subtree is null<br/>
     * Base case 2 - Returns element if matched<br/>
     * Recursive case 1 - Searches in the left subtree is element is smaller than the root<br/>
     * Recursive case 2 - Searches in the right subtree is element is smaller than the root
     *
     * @param root    Root node of the tree or subtree from which the element has to be searched
     * @param element Element to be searched
     * @param <E>     Type of the element
     * @return Matching node holding the element
     */
    private static <E extends Comparable<E>> Node<E> findElement(Node<E> root, E element) {
        // Base case 1 - Not found
        if (root == null) {
            throw new ElementNotFound("Element=" + element + " not found");
        }

        int compare = element.compareTo(root.value);
        // Base case 2 - Matched
        if (compare == 0) {
            return root;
        } else if (compare < 0) { // Recursive case 1 - Smaller than root
            return findElement(root.left, element);
        } else { // Recursive case 2 - Bigger than root
            return findElement(root.right, element);
        }
    }

    private static <E extends Comparable<E>> E findSuccessor(Node<E> root) {
        E successor = root.value;
        while (root.left != null) {
            if (root.left.value.compareTo(successor) < 0) {
                successor = root.left.value;
            }
            root = root.left;
        }
        return successor;
    }

    private static <E> void inOrderTraversal(BstUsingRecursion.Node<E> node, List<E> elements) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, elements);
        elements.add(node.value);
        inOrderTraversal(node.right, elements);
    }

    public static class Node<E> implements BinaryTreeNode<E> {
        private E value;
        private BstUsingRecursion.Node<E> left;
        private BstUsingRecursion.Node<E> right;

        @Override
        public E getValue() {
            return value;
        }

        @Override
        public BstUsingRecursion.Node<E> getLeft() {
            return left;
        }

        @Override
        public BstUsingRecursion.Node<E> getRight() {
            return right;
        }

        public Node(E value) {
            this.value = value;
        }

        @JsonIgnore
        public boolean isLeaf() {
            return left == null && right == null;
        }

        @JsonIgnore
        public boolean hasOneChild() {
            return (left == null && right != null) || (left != null && right == null);
        }

        @JsonIgnore
        public BstUsingRecursion.Node<E> getTheOnlyChild() {
            if (hasOneChild()) {
                return left == null ? right : left;
            }
            throw new IllegalArgumentException("Node has zero or two children");
        }
    }
}
