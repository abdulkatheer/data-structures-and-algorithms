package io.abdul.dfs.post_order;

import io.abdul.api.BinaryTreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeTraversal {
    public static <E> List<E> traverse(BinaryTreeNode<E> root) {
        List<E> elements = new ArrayList<>();
        postTraversal(root, elements);
        return elements;
    }

    private static <E> void postTraversal(BinaryTreeNode<E> root, List<E> container) {
        if (root == null) {
            return;
        }
        postTraversal(root.getLeft(), container);
        postTraversal(root.getRight(), container);
        container.add(root.getValue());
    }
}
