package io.abdul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.abdul.api.Tree;
import io.abdul.api.exception.DuplicateElement;
import io.abdul.api.exception.ElementNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class RedBlackTree<E extends Comparable<E>> implements Tree<E> {
    private Node<E> root;
    private int size;

    @Override
    public void insert(E element) {
        root = insertNode(root, element);
        if (isRed(root)) { // Case 1
            root.red = false;
        }
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
    public String toString() {
        return getElements().stream()
                .map(E::toString)
                .collect(joining(" --> "));
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
    private static <E extends Comparable<E>> Node<E> insertNode(Node<E> root, E element) {
        // Base case - Tree is empty, creating new Node<E>
        if (root == null) {
            return new Node<>(element);
        }
        int compare = element.compareTo(root.value);
        if (compare < 0) {
            // Recursive case 1 - Try creating node in the left
            root.left = insertNode(root.left, element);
            // Do the balancing
            if (isRed(root.left)) {
                if (isRed(root.left.left)) { // Left-Left
                    if (isRed(root.right)) { // Uncle is Red --> Recolor | Case 2
                        recolor(root, true, false);
                    } else { // Uncle is black --> Right Rotate & Recolor | Case 3a
                        root = rotateRight(root);
                        recolor(root, false, true);
                    }
                } else if (isRed(root.left.right)) { // Left-Right
                    if (isRed(root.right)) { // Uncle is Red --> Recolor
                        recolor(root, true, false);
                    } else { // Uncle is black --> Left Rotate (root.left) & Right rotate (new root) & Recolor | Case 3b
                        root.left = rotateLeft(root.left);
                        root = rotateRight(root);
                        recolor(root, false, true);
                    }
                }
            }
        } else if (compare > 0) {
            // Recursive case 2 - Try creating node in the right
            root.right = insertNode(root.right, element);
            // Do the balancing
            if (isRed(root.right)) {
                if (isRed(root.right.right)) { // Right-Right
                    if (isRed(root.left)) { // Uncle is Red --> Recolor | Case 2
                        recolor(root, true, false);
                    } else { // Uncle is black --> Left Rotate & Recolor | Case 3c
                        root = rotateLeft(root);
                        recolor(root, false, true);
                    }
                } else if (isRed(root.right.left)) { // Right-Left
                    if (isRed(root.left)) { // Uncle is Red --> Recolor
                        recolor(root, true, false);
                    } else { // Uncle is black --> Right Rotate (root.right) & Left rotate (new root) & Recolor | Case 3d
                        root.right = rotateRight(root.right);
                        root = rotateLeft(root);
                        recolor(root, false, true);
                    }
                }
            }
        } else {
            throw new DuplicateElement("Element=" + element + " already exists");
        }

        return root;
    }

    private static <E extends Comparable<E>> void recolor(Node<E> root, boolean rootColor, boolean childColor) {
        root.red = rootColor;
        if (root.left != null) {
            root.left.red = childColor;
        }
        if (root.right != null) {
            root.right.red = childColor;
        }
    }

    private static boolean isRed(Node<?> root) {
        return root != null && root.red;
    }

    private static <E> Node<E> rotateRight(Node<E> z) {
        Node<E> y = z.left;

        z.left = y.right;
        y.right = z;

        return y;
    }

    private static <E> Node<E> rotateLeft(Node<E> z) {
        Node<E> y = z.right;

        z.right = y.left;
        y.left = z;

        return y;
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

    private static <E> void inOrderTraversal(RedBlackTree.Node<E> node, List<E> elements) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, elements);
        elements.add(node.value);
        inOrderTraversal(node.right, elements);
    }

    public static class Node<E> {
        private boolean red = true;
        private E value;
        private RedBlackTree.Node<E> left;
        private RedBlackTree.Node<E> right;

        public E getValue() {
            return value;
        }

        public RedBlackTree.Node<E> getLeft() {
            return left;
        }

        public RedBlackTree.Node<E> getRight() {
            return right;
        }

        public boolean isRed() {
            return red;
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
        public RedBlackTree.Node<E> getTheOnlyChild() {
            if (hasOneChild()) {
                return left == null ? right : left;
            }
            throw new IllegalArgumentException("Node has zero or two children");
        }
    }
}