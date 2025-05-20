package io.abdul.linked_list.fundamentals_doubly_ll.problem18;

public class InsertBeforeNode {
}

class ListNode {
    int val;
    ListNode next;
    ListNode prev;

    ListNode() {
        val = 0;
        next = null;
        prev = null;
    }

    ListNode(int data1) {
        val = data1;
        next = null;
        prev = null;
    }

    ListNode(int data1, ListNode next1, ListNode prev1) {
        val = data1;
        next = next1;
        prev = prev1;
    }
}

class Solution {
    public void insertBeforeGivenNode(ListNode node, int X) {
        ListNode prev = node.prev;
        ListNode newNode = new ListNode(X);
        if (prev != null) {
            prev.next = newNode;
            newNode.prev = prev;
        }
        node.prev = newNode;
        newNode.next = node;
    }
}