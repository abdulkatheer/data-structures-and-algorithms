package io.abdul.linked_list.fundamentals_singly_ll.problem9;

public class InsertBeforeX {
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
    public ListNode insertBeforeX(ListNode head, int X, int val) {
        if (head == null) {
            return head;
        }

        ListNode newNode = new ListNode(val);
        if (head.val == X) { // Insert at head
            newNode.next = head;
            return newNode;
        }

        ListNode prevNode = head;
        ListNode currentNode = head.next;
        while (currentNode != null) {
            if (currentNode.val == X) {
                prevNode.next = newNode;
                newNode.next = currentNode;
                break;
            }
            prevNode = currentNode;
            currentNode = currentNode.next;
        }

        return head;
    }
}
