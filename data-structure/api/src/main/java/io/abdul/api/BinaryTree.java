package io.abdul.api;

public interface BinaryTree<E extends Comparable<E>> extends Tree<E> {
    BinaryTreeNode<E> getRoot();
}
