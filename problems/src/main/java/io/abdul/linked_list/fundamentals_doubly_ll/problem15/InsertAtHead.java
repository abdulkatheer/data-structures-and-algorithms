package io.abdul.linked_list.fundamentals_doubly_ll.problem15;

public class InsertAtHead {
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
    public ListNode insertBeforeHead(ListNode head, int X) {
        if (head == null) { // empty
            return new ListNode(X);
        }

        ListNode newHead = new ListNode(X);
        newHead.next = head;
        head.prev = newHead;
        return newHead;
    }
}