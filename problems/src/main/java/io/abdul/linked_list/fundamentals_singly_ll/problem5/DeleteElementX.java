package io.abdul.linked_list.fundamentals_singly_ll.problem5;

public class DeleteElementX {
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
    public ListNode deleteNodeWithValueX(ListNode head, int X) {
        if (head == null) { // empty list
            return null;
        }

        if (head.val == X) { // delete head
            return head.next;
        }

        // more than 1 element
        ListNode prevElement = head;
        ListNode currentElement = head.next;
        while (currentElement != null) {
            if (currentElement.val == X) {
                prevElement.next = currentElement.next;
                break;
            }
            prevElement = currentElement;
            currentElement = currentElement.next;
        }

        return head;
    }
}
