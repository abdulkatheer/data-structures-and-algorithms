package io.abdul.linked_list.fundamentals_doubly_ll.problem17;

public class InsertBeforeKth {
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
    public ListNode insertBeforeKthPosition(ListNode head, int X, int K) {
        if (head == null) {
            if (K == 1) {
                return new ListNode(X);
            }
            return null;
        }

        if (K == 1) {
            ListNode newHead = new ListNode(X);
            newHead.next = head;
            head.prev = newHead;
            return newHead;
        }

        ListNode current = head;
        int c = 0;
        while (current != null) {
            c++;
            if (c == K) {
                ListNode newNode = new ListNode(X);
                newNode.prev = current.prev;
                if (current.prev != null) {
                    current.prev.next = newNode;
                }
                newNode.next = current;
                current.prev = newNode;
                break;
            }
            current = current.next;
        }

        return head;
    }
}