package io.abdul.linked_list.fundamentals_singly_ll.problem8;

public class InsertAtKth {
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
    public ListNode insertAtKthPosition(ListNode head, int X, int K) {
        ListNode newNode = new ListNode(X);
        if (K == 1) { // Insert at head
            newNode.next = head;
            return newNode;
        }

        ListNode prevNode = head;
        ListNode currentNode = head.next;
        int c = 1;

        while (currentNode != null) {
            if (c == K - 1) {
                break;
            }
            c++;
            prevNode = currentNode;
            currentNode = currentNode.next;
        }

        if (c == K-1) {
            prevNode.next = newNode;
            newNode.next = currentNode;
        }

        return head;
    }
}