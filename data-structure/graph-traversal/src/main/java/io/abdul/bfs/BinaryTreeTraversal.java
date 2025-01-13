package io.abdul.bfs;

import io.abdul.QueueWithLinkedList;
import io.abdul.api.BinaryTreeNode;
import io.abdul.api.Queue;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeTraversal {
    public static <E> List<E> traverse(BinaryTreeNode<E> root) {
        List<E> elements = new ArrayList<>();
        Queue<BinaryTreeNode<E>> queue = new QueueWithLinkedList<>();

        queue.enqueue(root);

        while (!queue.isEmpty()) {
            BinaryTreeNode<E> current = queue.dequeue();
            elements.add(current.getValue());
            if (current.getLeft() != null) {
                queue.enqueue(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.enqueue(current.getRight());
            }
        }
        return elements;
    }
}
