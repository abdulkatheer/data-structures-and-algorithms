package io.abdul.linked_list.fundamentals_singly_ll.problem7;

public class InsertAtTail {
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
    public ListNode insertAtTail(ListNode head, int X) {
        ListNode newNode = new ListNode(X);

        if (head == null) {
            return newNode;
        }

        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }

        tail.next = newNode;
        return head;
    }
}
