package io.abdul.linked_list.fundamentals_doubly_ll.problem16;

public class InsertBeforeTail {
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
    public ListNode insertBeforeTail(ListNode head, int X) {
        if (head == null) {
            return new ListNode(X);
        }

        ListNode newNode = new ListNode(X);

        if (head.next == null) { // single element
            newNode.next = head;
            head.prev = newNode;
            return newNode;
        }

        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }


        newNode.prev = tail.prev;
        tail.prev.next = newNode;
        newNode.next = tail;
        tail.prev = newNode;
        return head;
    }
}
