package io.abdul.linked_list.fundamentals_singly_ll.problem3;

public class DeleteTail {
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
        val = 0;
        next = null;
    }

    ListNode(int data1) {
        val = data1;
        next = null;
    }

    ListNode(int data1, ListNode next1) {
        val = data1;
        next = next1;
    }
}

class Solution {
    public ListNode deleteTail(ListNode head) {
        if (head == null) { // empty
            return head;
        }

        if (head.next == null) { // single element
            return null;
        }

        // more than 1 element
        ListNode secondLast = head;
        while (secondLast.next != null && secondLast.next.next != null) {
            secondLast = secondLast.next;
        }
        secondLast.next = null;

        return head;
    }
}