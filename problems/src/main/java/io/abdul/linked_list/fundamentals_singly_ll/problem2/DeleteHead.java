package io.abdul.linked_list.fundamentals_singly_ll.problem2;

public class DeleteHead {
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
    public ListNode deleteHead(ListNode head) {
        if (head == null) {
            return head;
        }

        return head.next;
    }
}
