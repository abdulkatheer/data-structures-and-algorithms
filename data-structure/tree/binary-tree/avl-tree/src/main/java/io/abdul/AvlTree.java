package io.abdul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.abdul.api.Tree;
import io.abdul.api.exception.DuplicateElement;
import io.abdul.api.exception.ElementNotFound;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class AvlTree<E extends Comparable<E>> implements Tree<E> {
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
    public String toString() {
        return getElements().stream()
                .map(E::toString)
                .collect(joining(" --> "));
    }

    /**
     * Base case 1 - Tree is empty, creating new Node<E><br/>
     * Base case 2 - Node has been inserted in the left or right or root</br>
     * Recursive case 1 - If element is smaller than root, try inserting in the left and return the node<br/>
     * Recursive case 2 - If element is bigger than root, try inserting in the right and return the node<br/>
     * Recursive case 1 and 2 will boil down to Base case 2
     *
     * @param root    Root node of the tree or subtree to which the element has to be inserted
     * @param element Element to be inserted
     * @param <E>     Type of the element
     * @return Returns either the new node (empty tree) or the root of the subtree where node will be inserted
     */
    private static <E extends Comparable<E>> Node<E> createNode(Node<E> root, E element) {
        // Base case 1 - Tree is empty, creating new Node<E>
        if (root == null) {
            return new Node<>(element);
        }
        // Prologue - code before recursion
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

        // Epilogue - code after recursion
        updateHeight(root);
        int balanceFactor = getBalanceFactor(root);

        if (balanceFactor > 1) { // Left is higher
            if (element.compareTo(root.left.value) < 0) { // Element is smaller than left
                // Left-Left
                return rotateRight(root); // Rotate the root and return new root
            } else { // Element is bigger than left
                // Left-Right
                root.left = rotateLeft(root.left); // Rotate the left subtree and update left link
                return rotateRight(root); // Rotate the root and return new root
            }
        } else if (balanceFactor < -1) { // right is higher
            if (element.compareTo(root.right.value) > 0) { // Element is bigger than right
                // Right-Right
                return rotateLeft(root); // Rotate the root and return new root
            } else { // Element is smaller than right
                // Right-Left
                root.right = rotateRight(root.right); // Rotate the right subtree and update right link
                return rotateLeft(root); // Rotate the root and return new root
            }
        }

        // Base case 2 - Node has been inserted in the left or right or root
        return root; // Return new/old root
    }

    private static <E extends Comparable<E>> int getBalanceFactor(Node<E> root) {
        return root == null ? 0 : getHeight(root.left) - getHeight(root.right);
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
        // Step 1: Normal BST delete

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

            if (root.isLeaf()) { // Base case 2 - No child case
                root = null;
            } else if (root.hasOneChild()) {  // Base case 3 - Single child case
                root = root.getTheOnlyChild();
            } else {
                E successor = findSuccessor(root.right);
                root.value = successor; // copied value of successor

                // Recursive case 3 - Element is matched, so remove it
                root.right = deleteNode(root.right, successor);
            }
        }

        // If the tree had only one node then return
        if (root == null) {
            return root;
        }

        // Step 2: Update tree height
        updateHeight(root);

        // Step 3: Balance if not
        int balanceFactor = getBalanceFactor(root);
        if (balanceFactor > 1) { // Left is heavy
            if (getBalanceFactor(root.left) >= 0) { // Left of left is heavy or balanced
                // Left-Left case
                root = rotateRight(root);
            } else { // Right of left is heavy or balanced
                // Left-Right case
                root.left = rotateLeft(root.left);
                root = rotateRight(root);
            }
        } else if (balanceFactor < -1) { // Right is heavy
            if (getBalanceFactor(root.right) <= 0) { // Right of right is heavy or balanced
                // Right-Right case
                root = rotateLeft(root);
            } else {  // Left of right is heavy
                // Right-Left case
                root.right = rotateRight(root.right);
                root = rotateLeft(root);
            }
        }
        return root;
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

    private static <E> Node<E> rotateRight(Node<E> z) {
        Node<E> y = z.left;

        z.left = y.right;
        y.right = z;

        updateHeight(z);
        updateHeight(y);
        return y;
    }

    private static <E> Node<E> rotateLeft(Node<E> z) {
        Node<E> y = z.right;

        z.right = y.left;
        y.left = z;

        updateHeight(z);
        updateHeight(y);
        return y;
    }


    private static <E> int getHeight(Node<E> node) {
        return node == null ? 0 : node.height;
    }

    private static <E> void updateHeight(Node<E> root) {
        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
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

    private static <E> void inOrderTraversal(AvlTree.Node<E> node, List<E> elements) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, elements);
        elements.add(node.value);
        inOrderTraversal(node.right, elements);
    }

    public static class Node<E> {
        private E value;
        private AvlTree.Node<E> left;
        private AvlTree.Node<E> right;
        private int height;

        public E getValue() {
            return value;
        }

        public AvlTree.Node<E> getLeft() {
            return left;
        }

        public AvlTree.Node<E> getRight() {
            return right;
        }

        public int getHeight() {
            return height;
        }

        public Node(E value) {
            this.value = value;
            this.height = 1;
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
        public AvlTree.Node<E> getTheOnlyChild() {
            if (hasOneChild()) {
                return left == null ? right : left;
            }
            throw new IllegalArgumentException("Node has zero or two children");
        }
    }
}
