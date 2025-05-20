package io.abdul.linked_list.fundamentals_doubly_ll.problem12;

public class DeleteTailDLL {
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
    public ListNode deleteTail(ListNode head) {
        if (head == null) { // empty
            return null;
        }

        if (head.next == null) { // single element
            return null;
        }

        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }

        ListNode newTail = tail.prev;
        newTail.next = null;
        tail.prev = null;
        return head;
    }
}
