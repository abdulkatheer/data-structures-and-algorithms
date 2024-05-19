package io.abdul;

import io.abdul.api.Tree;
import io.abdul.api.exception.ElementNotFound;
import io.abdul.api.exception.NotImplemented;

import java.util.*;

import static java.util.stream.Collectors.joining;

// Based on "Order of the tree" approach
// https://www.cs.usfca.edu/~galles/visualization/BTree.html
public class BTree<E extends Comparable<E>> implements Tree<E> {
    private final int order; // Order of the tree
    private int size;
    private Node<E> root;
    private final int maxNodeSize;
    private final int minNodeSize;

    /**
     * Maximum possible elements in a node: order - 1
     * Minimum elements in a node: ROUND_CEILING(order / 2)
     *
     * @param order - Order of the tree
     */
    public BTree(int order) {
        if (order < 4) {
            throw new IllegalArgumentException("Order should be at least 4");
        }
        this.order = order;
        this.maxNodeSize = order - 1;
        this.minNodeSize = order / 2;
    }

    @Override
    public void insert(E element) {
        if (root == null) {
            root = new Node<>(maxNodeSize, minNodeSize, element);
        } else if (root.leaf) { // Only root node exists
            if (root.isFull()) { // Root itself is full
                root = splitRoot(root);
                insertInNonRootNode(root, element);
            } else { // Root is not full
                root.addElement(element);
            }
        } else {
            if (root.isFull()) {
                root = splitRoot(root);
            }
            insertInNonRootNode(root, element);
        }
        size++;
    }

    @Override
    public Optional<E> lookup(E element) {
        if (root == null) {
            return Optional.empty();
        }
        return find(root, element);
    }

    @Override
    public boolean remove(E element) {
        throw new NotImplemented("remove() not implemented yet!");
    }

    @Override
    public void empty() {
        root = null;
    }

    @Override
    public List<E> getElements() {
        if (root == null) {
            return Collections.emptyList();
        }
        List<E> container = new ArrayList<>();
        traverse(root, container);
        return container;
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

    private static <E extends Comparable<E>> Optional<E> find(Node<E> root, E element) {
        Node<E> current = root;
        while (current != null) {
            Node<E> nextNode = null;
            for (int i = 0; i < current.elements.size(); i++) {
                int result = element.compareTo(current.elements.get(i));
                if (result < 0) {
                    if (current.leaf) { // Element not found even at the end
                        throw new ElementNotFound("Element=" + element + " not found");
                    }
                    nextNode = current.children.get(i);
                    break;
                } else if (result == 0) { // Element found
                    return Optional.of(element);
                }
            }
            if (nextNode == null) { // Element is bigger than all
                if (current.leaf) { // Element not found even at the end
                    throw new ElementNotFound("Element=" + element + " not found");
                }
                nextNode = current.children.get(current.children.size() - 1);
            }
            current = nextNode;
        }
        throw new ElementNotFound("Element=" + element + " not found");
    }

    private static <E extends Comparable<E>> void traverse(Node<E> node, List<E> container) {
        if (node.leaf) { // Base case
            container.addAll(node.elements);
        } else {
            for (int i = 0; i < node.children.size(); i++) {
                traverse(node.children.get(i), container); // Recursive case
                if (i < node.elements.size()) {
                    container.add(node.elements.get(i));
                }
            }
        }
    }

    private static <E extends Comparable<E>> Node<E> splitRoot(Node<E> root) {
        E midElement = root.midElement();
        Node<E> newRoot = new Node<>(root.maxSize, root.minSize, midElement);
        newRoot.children.add(root.firstHalf());
        newRoot.children.add(root.secondHalf());
        newRoot.leaf = false;
        return newRoot;
    }

    private static <E extends Comparable<E>> void insertInNonRootNode(Node<E> parent, E element) {
        Node<E> child = parent.childNodeForInsertion(element);
        Node<E> nodeToBeInserted;
        while (true) {
            // Split if child is full
            if (child.isFull()) {
                Node<E> firstHalf = child.firstHalf();
                Node<E> secondHalf = child.secondHalf();
                E midElement = child.midElement();
                int positionInParent = parent.whereToInsert(midElement);
                parent.insertElement(positionInParent, midElement);
                parent.updateChildren(positionInParent, firstHalf); // Updating previous child link
                parent.insertChildren(positionInParent + 1, secondHalf);
                if (child.leaf) {
                    int compare = element.compareTo(midElement);
                    if (compare < 0) {
                        nodeToBeInserted = firstHalf;
                    } else if (compare > 0) {
                        nodeToBeInserted = secondHalf;
                    } else {
                        throw new IllegalArgumentException("Duplicate element");
                    }
                    break; // Child is split and exited
                }
            } else {
                if (child.leaf) {
                    nodeToBeInserted = child;
                    break; // Child is not split and exited
                }
            }
            // Update parent and child
            parent = child;
            child = parent.childNodeForInsertion(element);
        }
        nodeToBeInserted.addElement(element);
    }

    public static class Node<E extends Comparable<E>> {
        private final List<E> elements;
        private final List<Node<E>> children;
        private final int maxSize;
        private final int minSize;

        private boolean leaf = true;

        public Node(int maxSize, int minSize, E firstElement) {
            this(maxSize, minSize);
            addElement(firstElement);
        }

        public Node(int maxSize, int minSize) {
            this.maxSize = maxSize;
            this.minSize = minSize;
            this.elements = new ArrayList<>(maxSize);
            this.children = new ArrayList<>(maxSize + 1);
        }

        private Node<E> childNodeForInsertion(E element) {
            int position = -1;
            for (int i = 0; i < elements.size(); i++) {
                int result = element.compareTo(elements.get(i));
                if (result > 0) {
                    position = i;
                } else if (result < 0) {
                    break;
                } else {
                    throw new IllegalArgumentException("Duplicate element");
                }
            }
            return children.get(position + 1);
        }

        private boolean isFull() {
            return elements.size() == maxSize;
        }

        private void addElement(E firstElement) {
            if (!leaf) {
                throw new IllegalArgumentException("Not a lead node");
            }
            this.elements.add(elements.size(), firstElement);
            this.elements.sort(Comparator.naturalOrder());
        }

        private Node<E> firstHalf() {
            Node<E> newNode = new Node<>(maxSize, minSize);
            int mid = elements.size() / 2;
            for (int i = 0; i < mid; i++) {
                newNode.elements.add(i, elements.get(i));
            }
            if (!leaf) {
                for (int i = 0; i <= mid; i++) {
                    newNode.children.add(i, children.get(i));
                }
            }
            newNode.leaf = leaf;
            return newNode;
        }

        private Node<E> secondHalf() {
            Node<E> newNode = new Node<>(maxSize, minSize);
            int mid = elements.size() / 2;
            for (int i = 0, j = mid + 1; j < elements.size(); i++, j++) {
                newNode.elements.add(i, elements.get(j));
            }
            if (!leaf) {
                for (int i = 0, j = mid + 1; j <= elements.size(); i++, j++) {
                    newNode.children.add(i, children.get(j));
                }
            }
            newNode.leaf = leaf;
            return newNode;
        }

        private E midElement() {
            int mid = elements.size() / 2;
            return elements.get(mid);
        }

        private int whereToInsert(E element) {
            int p = -1;
            for (int i = 0; i < elements.size(); i++) {
                int result = element.compareTo(elements.get(i));
                if (result > 0) {
                    p = i;
                } else if (result < 0) {
                    break;
                } else {
                    throw new IllegalArgumentException("Duplicate element");
                }
            }
            return p + 1;
        }

        private void insertElement(int position, E midElement) {
            elements.add(position, midElement);
        }

        private void insertChildren(int position, Node<E> child) {
            children.add(position, child);
        }

        private void updateChildren(int position, Node<E> child) {
            children.set(position, child);
        }
    }
}
