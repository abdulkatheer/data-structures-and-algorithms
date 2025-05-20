package io.abdul.linked_list.faqs_medium.problem6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FindMiddle {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Test Case 1: Odd number of nodes
        ListNode head1 = createLinkedList(new int[]{3, 8, 7, 1, 3});
        assertEquals(7, solution.middleOfLinkedList(head1).val, "Test Case 1 Failed: Expected middle node value 7");

        // Test Case 2: Even number of nodes
        ListNode head2 = createLinkedList(new int[]{2, 9, 1, 4, 0, 4});
        assertEquals(4, solution.middleOfLinkedList(head2).val, "Test Case 2 Failed: Expected middle node value 4");

        // Test Case 3: Odd number of nodes
        ListNode head3 = createLinkedList(new int[]{3, 8, 1, 7, 0});
        assertEquals(1, solution.middleOfLinkedList(head3).val, "Test Case 3 Failed: Expected middle node value 1");

        // Test Case 4: Single node
        ListNode head4 = createLinkedList(new int[]{5});
        assertEquals(5, solution.middleOfLinkedList(head4).val, "Test Case 4 Failed: Expected middle node value 5");

        // Test Case 5: Two nodes
        ListNode head5 = createLinkedList(new int[]{1, 2});
        assertEquals(2, solution.middleOfLinkedList(head5).val, "Test Case 5 Failed: Expected middle node value 2");

        // Test Case 6: Empty list
        ListNode head6 = null;
        assertNull(solution.middleOfLinkedList(head6), "Test Case 6 Failed: Expected null for empty list");
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
Brute - Find size and then find middle
T - O(n)
S - O(1)
 */
class Solution {
    public ListNode middleOfLinkedList(ListNode head) {
        int size = 0;
        ListNode current = head;
        while (current != null) {
            size++;
            current = current.next;
        }

        int mid = (size / 2) + 1;

        int i = 0;
        current = head;
        while (current != null) {
            i++;
            if (mid == i) {
                return current;
            }
            current = current.next;
        }

        return null; // dummy
    }
}

/*
Optimal - fast-slow pointer approach
T - O(n)
S - O(1)

fast & slow starts together
fast moves 2x
slow moves 1x
by the time fast reaches tail or tail.next, slow would be in the middle. (right-middle for even)
 */
class Solution2 {
    public ListNode middleOfLinkedList(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
