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

    public boolean remove(E element) {
        if (root == null) {
            return false;
        }

        if (element.compareTo(root.value) == 0) { // removing root element
            // No leaf
            // 1 child
            // 2 children
            if (root.isLeaf()) { // lead node
                root = null;
            } else if (root.hasOneChild()) { // only one child
                Node<E> grandChild;
                if (root.left != null) {
                    grandChild = root.left;
                } else {
                    grandChild = root.right;
                }
                root = grandChild;
            } else { // two children
                removeNodeWithTwoChildren(root);
            }
            size--;
            return true;
        }
        boolean removed = removeElement(root, element);
        if (removed) {
            size--;
        }
        return removed;
    }

    public List<E> getElements() {
        ArrayList<E> elements = new ArrayList<>(size);
        inOrderTraversal(root, elements);
        return elements;
    }

    public int size() {
        return size;
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

        public boolean hasOneChild() {
            return (left == null && right != null) || (left != null && right == null);
        }
    }

    private static void inOrderTraversal(Node<?> node, List elements) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, elements);
        elements.add(node.value);
        inOrderTraversal(node.right, elements);
    }

    private static <E extends Comparable<E>> boolean removeElement(Node<E> root, E element) {
        int comp = element.compareTo(root.value);
        Node<E> parent = root;
        Node<E> current;
        boolean currentLeftNode;
        boolean removed = false;
        if (comp < 0) { // Traverse left
            current = parent.left;
            currentLeftNode = true;
        } else { // Traverse right
            current = parent.right;
            currentLeftNode = false;
        }

        while (current != null) {
            int compare = element.compareTo(current.value);
            if (compare == 0) { // match found
                if (current.isLeaf()) { // lead node
                    removeLeafNode(parent, currentLeftNode);
                } else if (current.hasOneChild()) { // only one child
                    removeNodeWithOneChild(current, parent, currentLeftNode);
                } else { // two children
                    removeNodeWithTwoChildren(current);
                }
                removed = true;
                break;
            } else {
                parent = current;
                if (compare < 0) { // Traverse left
                    current = current.left;
                    currentLeftNode = true;
                } else { // Traverse right
                    current = current.right;
                    currentLeftNode = false;
                }
            }
        }
        return removed;
    }

    private static <E extends Comparable<E>> void removeNodeWithTwoChildren(Node<E> nodeToBeRemoved) {
        // find successor of current
        // copy value of successor to current
        // remove successor (no  child or 1-child case)
        Node<E> successor = findSuccessor(nodeToBeRemoved);
        nodeToBeRemoved.value = successor.value;
        removeElement(nodeToBeRemoved, successor.value);
    }

    private static <E extends Comparable<E>> Node<E> findSuccessor(Node<E> parent) {
        Node<E> successor = parent.right;
        Node<E> current = successor.left;

        while (current != null) {
            if (current.left != null && current.left.value.compareTo(successor.value) < 0) {
                successor = current.left;
            }
            current = current.left;
        }

        return successor;
    }

    private static <E> void removeNodeWithOneChild(Node<E> current, Node<E> parent, boolean currentLeftNode) {
        Node<E> grandChild;
        if (current.left != null) {
            grandChild = current.left;
        } else {
            grandChild = current.right;
        }
        if (currentLeftNode) { // done
            parent.left = grandChild; // Replacing current with its grandchild
        } else { // done
            parent.right = grandChild;
        }
    }

    private static <E> void removeLeafNode(Node<E> parent, boolean currentLeftNode) {
        if (currentLeftNode) { // done
            parent.left = null;
        } else { // done
            parent.right = null;
        }
    }
}
