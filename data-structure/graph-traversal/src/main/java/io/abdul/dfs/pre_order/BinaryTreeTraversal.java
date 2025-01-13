package io.abdul.dfs.pre_order;

import io.abdul.api.BinaryTreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeTraversal {
    public static <E> List<E> traverse(BinaryTreeNode<E> root) {
        List<E> elements = new ArrayList<>();
        preTraversal(root, elements);
        return elements;
    }

    private static <E> void preTraversal(BinaryTreeNode<E> root, List<E> container) {
        if (root == null) {
            return;
        }
        container.add(root.getValue());
        preTraversal(root.getLeft(), container);
        preTraversal(root.getRight(), container);
    }
}
