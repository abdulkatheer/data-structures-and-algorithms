package io.abdul.linked_list.faqs_dll.problem2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteDuplicates {
    public static void main(String[] args) {

        Solution solution = new Solution();

        // Test Case 1: Duplicates appear consecutively
        ListNode head1 = createDoublyLinkedList(new int[]{1, 1, 3, 3, 4, 5});
        ListNode result1 = solution.removeDuplicates(head1);
        assertArrayEquals(new int[]{1, 3, 4, 5}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: All nodes have the same value
        ListNode head2 = createDoublyLinkedList(new int[]{1, 1, 1, 1, 1, 2});
        ListNode result2 = solution.removeDuplicates(head2);
        assertArrayEquals(new int[]{1, 2}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: No duplicates in the list
        ListNode head3 = createDoublyLinkedList(new int[]{1, 2, 3});
        ListNode result3 = solution.removeDuplicates(head3);
        assertArrayEquals(new int[]{1, 2, 3}, toArray(result3), "Test Case 3 Failed");

        // Test Case 4: Single node
        ListNode head4 = createDoublyLinkedList(new int[]{1});
        ListNode result4 = solution.removeDuplicates(head4);
        assertArrayEquals(new int[]{1}, toArray(result4), "Test Case 4 Failed");

        // Test Case 5: Empty list
        ListNode head5 = null;
        ListNode result5 = solution.removeDuplicates(head5);
        assertNull(result5, "Test Case 5 Failed: Expected null for empty list");

        // Test Case 6: Duplicates at the end of the list
        ListNode head6 = createDoublyLinkedList(new int[]{1, 2, 3, 3, 3});
        ListNode result6 = solution.removeDuplicates(head6);
        assertArrayEquals(new int[]{1, 2, 3}, toArray(result6), "Test Case 6 Failed");
    }

    private static ListNode createDoublyLinkedList(int[] values) {
        if (values.length == 0) return null;
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            ListNode newNode = new ListNode(values[i]);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }
        return head;
    }

    private static int[] toArray(ListNode head) {
        if (head == null) return new int[]{};
        int size = 0;
        ListNode current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        int[] result = new int[size];
        current = head;
        int index = 0;
        while (current != null) {
            result[index++] = current.val;
            current = current.next;
        }
        return result;
    }
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

/*
Optimal - Linear
T - O(n)
S - O(1)

Head can never be null, as even if we have all duplicates at least first element will exist. So we'll start with head.next only.
 */
class Solution {
    public ListNode removeDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode current = head.next;
        while (current != null) {
            if (current.prev != null && current.prev.val == current.val) {
                current.prev.next = current.next;
                if (current.next != null) {
                    current.next.prev = current.prev;
                }
            }
            current = current.next;
        }

        return head;
    }
}