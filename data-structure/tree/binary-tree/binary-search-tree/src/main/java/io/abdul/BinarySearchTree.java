package io.abdul;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;
    private int size;

    public void insert(E element) {
        Node<E> newNode = new Node<>(element);
        if (size == 0) {
            root = newNode;
            size++;
            return;
        }
        Node<E> current = root;
        while (current != null) {
            int compare = element.compareTo(current.value);
            if (compare > 0) { // Traverse right
                if (current.right == null) {
                    current.right = newNode;
                    break;
                } else {
                    current = current.right;
                }
            } else if (compare < 0) { // Traverse left
                if (current.left == null) {
                    current.left = newNode;
                    break;
                } else {
                    current = current.left;
                }
            } else {
                throw new IllegalArgumentException("Duplicate element");
            }
        }
        size++;
    }

    public boolean remove(E element) {
        // 1. Empty tree (✓)
        // 2. Deleting root node (another node may take root position)
        // 2a) leaf node (means only one node exists and root will become null)
        // 2b) one child (that child will become root)
        // 2c) two children (copy value of successor to root and remove successor)
        // 3. Deleting a non-root node
        // 3a) leaf node (parent's right or left will become null)
        // 3b) one child (parent's left or right will point to that child)
        // 3c) two children (copy value of successor to root and remove successor)
        boolean removed = false;
        if (size != 0) { // 1. Empty tree (✓)
            if (element.compareTo(root.value) == 0) { // 2. Deleting root node (another node may take root position)
                removeRootNode();
                removed = true;
                size--;
            } else { // 3. Deleting a non-root node
                removed = removeNonRootNode(root, element);
                if (removed) {
                    size--;
                }
            }
        }
        return removed;
    }

    public Optional<E> lookup(E element) {
        Node<E> current = root;
        while (current != null) {
            int compare = element.compareTo(current.value);
            if (compare > 0) { // Traverse right
                if (current.right == null) {
                    break;
                } else {
                    current = current.right;
                }
            } else if (compare < 0) { // Traverse left
                if (current.left == null) {
                    break;
                } else {
                    current = current.left;
                }
            } else {
                return Optional.of(current.value);
            }
        }
        return Optional.empty();
    }

    public void empty() {
        root = null;
        size = 0;
    }

    public List<E> getElements() {
        ArrayList<E> elements = new ArrayList<>(size);
        inOrderTraversal(root, elements);
        return elements;
    }

    public int size() {
        return size;
    }

    private void removeRootNode() {
        if (root.isLeaf()) { // 2a) leaf node (means only one node exists and root will become null)
            root = null;
        } else if (root.hasOneChild()) { // 2b) one child (that child will become root)
            root = root.getTheOnlyChild();
        } else { // 2c) two children (copy value of successor to root and remove successor)
            removeNodeWithTwoChildren(root);
        }
    }

    public static class Node<E> {
        private E value;
        private Node<E> left;
        private Node<E> right;

        public E getValue() {
            return value;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
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
        public Node<E> getTheOnlyChild() {
            if (hasOneChild()) {
                return left == null ? right : left;
            }
            throw new IllegalArgumentException("Node has zero or two children");
        }
    }

    private static <E> void inOrderTraversal(Node<E> node, List<E> elements) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, elements);
        elements.add(node.value);
        inOrderTraversal(node.right, elements);
    }

    private static <E extends Comparable<E>> Node<E> findSuccessor(Node<E> root) {
        Node<E> successor = root.right;
        if (successor == null) {
            throw new IllegalArgumentException("No successor found");
        }
        Node<E> current = successor.left;

        while (current != null) {
            if (current.value.compareTo(successor.value) < 0) {
                successor = current.left;
            }
            current = current.left;
        }
        return successor;
    }

    private static <E extends Comparable<E>> void removeNodeWithTwoChildren(Node<E> nodeToBeRemoved) {
        Node<E> successor = findSuccessor(nodeToBeRemoved);
        nodeToBeRemoved.value = successor.value;

        Node<E> parent = nodeToBeRemoved;
        Node<E> current = nodeToBeRemoved.right;
        boolean currentIsOnLeft = false;

        while (current != null) {
            int compare = current.value.compareTo(successor.value);
            if (compare == 0) { // successor found
                if (current.isLeaf()) {
                    if (currentIsOnLeft) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                } else if (current.hasOneChild()) {
                    Node<E> theOnlyChild = current.getTheOnlyChild();
                    if (currentIsOnLeft) {
                        parent.left = theOnlyChild;
                    } else {
                        parent.right = theOnlyChild;
                    }
                } else {
                    throw new IllegalArgumentException("Invalid BST, successor of a node should not have left sub-tree");
                }
                break;
            } else if (compare < 0) {
                parent = current;
                current = current.right;
                currentIsOnLeft = false;
            } else {
                parent = current;
                current = current.left;
                currentIsOnLeft = true;
            }
        }
    }

    private static <E extends Comparable<E>> boolean removeNonRootNode(Node<E> root, E element) {
        Node<E> parent;
        Node<E> current;
        boolean nodeIsOnLeft = false;
        boolean removed = false;
        int comp = root.value.compareTo(element);
        if (comp < 0) {
            parent = root;
            current = root.right;
        } else if (comp > 0) {
            parent = root;
            current = root.left;
            nodeIsOnLeft = true;
        } else {
            throw new IllegalArgumentException("root and element are same");
        }

        while (current != null) {
            int compare = current.value.compareTo(element);
            if (compare == 0) { // Match found
                if (current.isLeaf()) { // 3a) leaf node (parent's right or left will become null)
                    if (nodeIsOnLeft) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                } else if (current.hasOneChild()) { // 3b) one child (parent's left or right will point to that child)
                    Node<E> theOnlyChild = current.getTheOnlyChild();
                    if (nodeIsOnLeft) {
                        parent.left = theOnlyChild;
                    } else {
                        parent.right = theOnlyChild;
                    }
                } else { // 3c) two children (copy value of successor to root and remove successor)
                    removeNodeWithTwoChildren(current);
                }
                removed = true;
                break;
            } else if (compare < 0) { // Go towards right
                parent = current;
                current = current.right;
            } else { // Go towards left
                parent = current;
                current = current.left;
            }
        }
        return removed;
    }
}
