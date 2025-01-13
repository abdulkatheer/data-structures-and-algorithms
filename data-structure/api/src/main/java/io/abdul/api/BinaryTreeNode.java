package io.abdul.api;

import io.abdul.api.exception.NotImplemented;

public interface BinaryTreeNode<E> {
    E getValue();

    BinaryTreeNode<E> getLeft();

    BinaryTreeNode<E> getRight();

    default void setRight(BinaryTreeNode<E> newRight) {
        throw new NotImplemented("setRight not implemented yet");
    }

    default void setLeft(BinaryTreeNode<E> newLeft) {
        throw new NotImplemented("setLeft not implemented yet");
    }

    default void setValue(E newValue) {
        throw new NotImplemented("setValue not implemented yet");
    }

    default boolean isLeaf() {
        throw new NotImplemented("isLeaf not implemented yet");
    }
}
