package io.abdul.linked_list.fundamentals_singly_ll.problem4;

public class DeleteKth {
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
    public ListNode deleteKthNode(ListNode head, int k) {
        if (head == null) { // Empty list
            return head;
        }

        if (k == 1) { // Delete head
            return head.next;
        }

        ListNode prevElement = head;
        ListNode currentElement = head.next;
        int c = 1;
        while (currentElement != null) { // more than 1 element
            if (c == k-1) {
                prevElement.next = currentElement.next;
                break;
            }
            prevElement = currentElement;
            currentElement = currentElement.next;
            c++;
        }

        return head;
    }
}
