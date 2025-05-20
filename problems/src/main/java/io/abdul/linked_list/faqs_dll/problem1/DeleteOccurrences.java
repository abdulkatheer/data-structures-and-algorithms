package io.abdul.linked_list.faqs_dll.problem1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteOccurrences {
    public static void main(String[] args) {

        Solution solution = new Solution();

        // Test Case 1: Target value appears multiple times
        ListNode head1 = createDoublyLinkedList(new int[]{1, 2, 3, 1, 4});
        ListNode result1 = solution.deleteAllOccurrences(head1, 1);
        assertArrayEquals(new int[]{2, 3, 4}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: Target value appears at the head and tail
        ListNode head2 = createDoublyLinkedList(new int[]{2, 3, -1, 4, 2});
        ListNode result2 = solution.deleteAllOccurrences(head2, 2);
        assertArrayEquals(new int[]{3, -1, 4}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: All nodes have the target value
        ListNode head3 = createDoublyLinkedList(new int[]{7, 7, 7, 7});
        ListNode result3 = solution.deleteAllOccurrences(head3, 7);
        assertNull(result3, "Test Case 3 Failed: Expected null for all nodes removed");

        // Test Case 4: Target value does not exist in the list
        ListNode head4 = createDoublyLinkedList(new int[]{1, 2, 3, 4});
        ListNode result4 = solution.deleteAllOccurrences(head4, 5);
        assertArrayEquals(new int[]{1, 2, 3, 4}, toArray(result4), "Test Case 4 Failed");

        // Test Case 5: Single node with target value
        ListNode head5 = createDoublyLinkedList(new int[]{1});
        ListNode result5 = solution.deleteAllOccurrences(head5, 1);
        assertNull(result5, "Test Case 5 Failed: Expected null for single node removed");

        // Test Case 6: Single node without target value
        ListNode head6 = createDoublyLinkedList(new int[]{1});
        ListNode result6 = solution.deleteAllOccurrences(head6, 2);
        assertArrayEquals(new int[]{1}, toArray(result6), "Test Case 6 Failed");

        // Test Case 7: Empty list
        ListNode head7 = null;
        ListNode result7 = solution.deleteAllOccurrences(head7, 1);
        assertNull(result7, "Test Case 7 Failed: Expected null for empty list");
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
 */
class Solution {
    public ListNode deleteAllOccurrences(ListNode head, int target) {
        ListNode current = head;
        while (current != null) {
            if (current.val == target) {
                if (current.prev == null) { // current is head
                    head = current.next;
                    if (current.next != null) { // next is not tail
                        current.next.prev = null;
                    }
                    current = current.next;
                } else { // current is not head
                    current.prev.next = current.next;
                    if (current.next != null) { // Not tail
                        current.next.prev = current.prev;
                    }
                    current = current.next;
                }
            } else {
                current = current.next;
            }
        }

        return head;
    }
}
