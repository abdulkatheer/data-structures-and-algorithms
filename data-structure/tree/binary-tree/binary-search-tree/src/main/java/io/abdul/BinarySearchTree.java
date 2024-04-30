package io.abdul;

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
        Node<E> parent = null;
        Node<E> current = root;
        boolean currentLeftNode = false;
        boolean removed = false;

        while (current != null) {
            int compare = element.compareTo(current.value);
            if (compare == 0) { // match found
                if (current.isLeaf()) { // lead node
                    removeLeafNode(parent, currentLeftNode);
                } else if (current.hasOneChild()) { // only one child
                    removeNodeWithOneChild(current, parent, currentLeftNode);
                } else { // two children
                    removeNodeWithTwoChildren(current, parent, currentLeftNode);
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

        public Node(E value) {
            this.value = value;
        }

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

    private void removeNodeWithTwoChildren(Node<E> current, Node<E> parent, boolean currentLeftNode) {
        Node<E> successor = current.right;
        Node<E> predecessor = current.left;
        successor.left = predecessor; // making left node pointing to right node
        if (parent == null) { // deleting root node and it has two children, making the successor child as root
            root = successor;
        } else if (currentLeftNode) {
            parent.left = successor;
        } else {
            parent.right = successor;
        }
        size--;
    }

    private void removeNodeWithOneChild(Node<E> current, Node<E> parent, boolean currentLeftNode) {
        Node<E> grandChild;
        if (current.left != null) {
            grandChild = current.left;
        } else {
            grandChild = current.right;
        }
        if (parent == null) { // deleting root node and it has one child, making the grand child as root // done
            root = grandChild;
        } else if (currentLeftNode) { // done
            parent.left = grandChild; // Replacing current with its grandchild
        } else { // done
            parent.right = grandChild;
        }
        size--;
    }

    private void removeLeafNode(Node<E> parent, boolean currentLeftNode) {
        if (parent == null) { // deleting root node and it's leaf node // done
            root = null;
        } else if (currentLeftNode) { // done
            parent.left = null;
        } else { // done
            parent.right = null;
        }
        size--;
    }
}
