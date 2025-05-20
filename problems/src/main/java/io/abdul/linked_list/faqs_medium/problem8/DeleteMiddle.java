package io.abdul.linked_list.faqs_medium.problem8;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DeleteMiddle {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();

        // Test Case 1: Odd number of nodes
        ListNode head1 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = solution.deleteMiddle(head1);
        assertArrayEquals(new int[]{1, 2, 4, 5}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: Even number of nodes
        ListNode head2 = createLinkedList(new int[]{7, 6, 5, 4});
        ListNode result2 = solution.deleteMiddle(head2);
        assertArrayEquals(new int[]{7, 6, 4}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: Single node
        ListNode head3 = createLinkedList(new int[]{7});
        ListNode result3 = solution.deleteMiddle(head3);
        assertNull(result3, "Test Case 3 Failed: Expected null for single node");

        // Test Case 4: Two nodes
        ListNode head4 = createLinkedList(new int[]{1, 2});
        ListNode result4 = solution.deleteMiddle(head4);
        assertArrayEquals(new int[]{1}, toArray(result4), "Test Case 4 Failed");

        // Test Case 5: Empty list
        ListNode head5 = null;
        ListNode result5 = solution.deleteMiddle(head5);
        assertNull(result5, "Test Case 5 Failed: Expected null for empty list");
    }

    private static ListNode createLinkedList(int[] nums) {
        if (nums.length == 0) return null;
        ListNode head = new ListNode(nums[0]);
        ListNode current = head;
        for (int i = 1; i < nums.length; i++) {
            current.next = new ListNode(nums[i]);
            current = current.next;
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

/*
Brute - Find size and find mid-1
T - O(n)
S - O(1)
 */
class Solution {
    public ListNode deleteMiddle(ListNode head) {
        return null;
    }
}

/*
Optimal - fast-slow pointer method
T - O(n)
S - O(1)
 */
class Solution2 {
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head, fast = head, prev = null;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = prev.next.next;
        return head;
    }
}
