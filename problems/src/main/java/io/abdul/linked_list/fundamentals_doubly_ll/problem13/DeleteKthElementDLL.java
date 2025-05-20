package io.abdul.linked_list.fundamentals_doubly_ll.problem13;

public class DeleteKthElementDLL {
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
    public ListNode deleteKthElement(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        if (k == 1) { // delete head
            if (head.next != null) {
                head.next.prev = null;
            }
            return head.next;
        }

        ListNode current = head;
        int c = 0;
        while (current != null) {
            c++;
            if (c == k) {
                if (current.prev != null) { // for non-head
                    current.prev.next = current.next;
                }
                if (current.next != null) { // for non-tail
                    current.next.prev = current.prev;
                }
                break;
            }
            current = current.next;
        }

        return head;
    }
}
