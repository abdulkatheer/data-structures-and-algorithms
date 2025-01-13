package io.abdul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.abdul.api.BinaryTree;
import io.abdul.api.BinaryTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BinarySearchTree<E extends Comparable<E>> implements BinaryTree<E> {
    private BinaryTreeNode<E> root;
    private int size;

    @Override
    public void insert(E element) {
        BinaryTreeNode<E> newNode = new Node<>(element);
        if (size == 0) {
            root = newNode;
            size++;
            return;
        }
        BinaryTreeNode<E> current = root;
        while (current != null) {
            int compare = element.compareTo(current.getValue());
            if (compare > 0) { // Traverse right
                if (current.getRight() == null) {
                    current.setRight(newNode);
                    break;
                } else {
                    current = current.getRight();
                }
            } else if (compare < 0) { // Traverse left
                if (current.getLeft() == null) {
                    current.setLeft(newNode);
                    break;
                } else {
                    current = current.getLeft();
                }
            } else {
                throw new IllegalArgumentException("Duplicate element");
            }
        }
        size++;
    }

    @Override
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
            if (element.compareTo(root.getValue()) == 0) { // 2. Deleting root node (another node may take root position)
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

    @Override
    public Optional<E> lookup(E element) {
        BinaryTreeNode<E> current = root;
        while (current != null) {
            int compare = element.compareTo(current.getValue());
            if (compare > 0) { // Traverse right
                if (current.getRight() == null) {
                    break;
                } else {
                    current = current.getRight();
                }
            } else if (compare < 0) { // Traverse left
                if (current.getLeft() == null) {
                    break;
                } else {
                    current = current.getLeft();
                }
            } else {
                return Optional.of(current.getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public void empty() {
        root = null;
        size = 0;
    }

    @Override
    public List<E> getElements() {
        ArrayList<E> elements = new ArrayList<>(size);
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

    private void removeRootNode() {
        if (root.isLeaf()) { // 2a) leaf node (means only one node exists and root will become null)
            root = null;
        } else if (((Node<E>) root).hasOneChild()) { // 2b) one child (that child will become root)
            root = ((Node<E>) root).getTheOnlyChild();
        } else { // 2c) two children (copy value of successor to root and remove successor)
            removeNodeWithTwoChildren(root);
        }
    }

    public static class Node<E> implements BinaryTreeNode<E> {
        private E value;
        private BinaryTreeNode<E> left;
        private BinaryTreeNode<E> right;

        @Override
        public void setLeft(BinaryTreeNode<E> newLeft) {
            this.left = newLeft;
        }

        @Override
        public void setValue(E newValue) {
            this.value = newValue;
        }

        public Node(E value) {
            this.setValue(value);
        }

        @Override
        public E getValue() {
            return value;
        }

        @Override
        public BinaryTreeNode<E> getLeft() {
            return left;
        }

        @Override
        public BinaryTreeNode<E> getRight() {
            return right;
        }

        @Override
        public void setRight(BinaryTreeNode<E> newRight) {
            this.right = newRight;
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
        public BinaryTreeNode<E> getTheOnlyChild() {
            if (hasOneChild()) {
                return left == null ? right : left;
            }
            throw new IllegalArgumentException("Node has zero or two children");
        }
    }

    private static <E> void inOrderTraversal(BinaryTreeNode<E> node, List<E> elements) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.getLeft(), elements);
        elements.add(node.getValue());
        inOrderTraversal(node.getRight(), elements);
    }

    private static <E extends Comparable<E>> BinaryTreeNode<E> findSuccessor(BinaryTreeNode<E> root) {
        BinaryTreeNode<E> successor = root.getRight();
        if (successor == null) {
            throw new IllegalArgumentException("No successor found");
        }
        BinaryTreeNode<E> current = successor.getLeft();

        while (current != null) {
            if (current.getValue().compareTo(successor.getValue()) < 0) {
                successor = current.getLeft();
            }
            current = current.getLeft();
        }
        return successor;
    }

    private static <E extends Comparable<E>> void removeNodeWithTwoChildren(BinaryTreeNode<E> nodeToBeRemoved) {
        BinaryTreeNode<E> successor = findSuccessor(nodeToBeRemoved);
        nodeToBeRemoved.setValue(successor.getValue());

        BinaryTreeNode<E> parent = nodeToBeRemoved;
        BinaryTreeNode<E> current = nodeToBeRemoved.getRight();
        boolean currentIsOnLeft = false;

        while (current != null) {
            int compare = current.getValue().compareTo(successor.getValue());
            if (compare == 0) { // successor found
                if (current.isLeaf()) {
                    if (currentIsOnLeft) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                } else if (((Node<E>) current).hasOneChild()) {
                    BinaryTreeNode<E> theOnlyChild = ((Node<E>) current).getTheOnlyChild();
                    if (currentIsOnLeft) {
                        parent.setLeft(theOnlyChild);
                    } else {
                        parent.setRight(theOnlyChild);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid BST, successor of a node should not have left sub-tree");
                }
                break;
            } else if (compare < 0) {
                parent = current;
                current = current.getRight();
                currentIsOnLeft = false;
            } else {
                parent = current;
                current = current.getLeft();
                currentIsOnLeft = true;
            }
        }
    }

    private static <E extends Comparable<E>> boolean removeNonRootNode(BinaryTreeNode<E> root, E element) {
        BinaryTreeNode<E> parent;
        BinaryTreeNode<E> current;
        boolean nodeIsOnLeft = false;
        boolean removed = false;
        int comp = root.getValue().compareTo(element);
        if (comp < 0) {
            parent = root;
            current = root.getRight();
        } else if (comp > 0) {
            parent = root;
            current = root.getLeft();
            nodeIsOnLeft = true;
        } else {
            throw new IllegalArgumentException("root and element are same");
        }

        while (current != null) {
            int compare = current.getValue().compareTo(element);
            if (compare == 0) { // Match found
                if (current.isLeaf()) { // 3a) leaf node (parent's right or left will become null)
                    if (nodeIsOnLeft) {
                        parent.setLeft(null);
                    } else {
                        parent.setRight(null);
                    }
                } else if (((Node<E>) current).hasOneChild()) { // 3b) one child (parent's left or right will point to that child)
                    BinaryTreeNode<E> theOnlyChild = ((Node<E>) current).getTheOnlyChild();
                    if (nodeIsOnLeft) {
                        parent.setLeft(theOnlyChild);
                    } else {
                        parent.setRight(theOnlyChild);
                    }
                } else { // 3c) two children (copy value of successor to root and remove successor)
                    removeNodeWithTwoChildren(current);
                }
                removed = true;
                break;
            } else if (compare < 0) { // Go towards right
                parent = current;
                current = current.getRight();
            } else { // Go towards left
                parent = current;
                current = current.getLeft();
            }
        }
        return removed;
    }
}
