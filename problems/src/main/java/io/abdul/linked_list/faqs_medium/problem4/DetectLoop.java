package io.abdul.linked_list.faqs_medium.problem4;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DetectLoop {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Test Case 1: Loop exists (pos = 1)
        ListNode head1 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        createLoop(head1, 1);
        assertTrue(solution.hasCycle(head1), "Test Case 1 Failed: Expected true for loop at pos 1");

        // Test Case 2: No loop (pos = -1)
        ListNode head2 = createLinkedList(new int[]{1, 3, 7, 4});
        assertFalse(solution.hasCycle(head2), "Test Case 2 Failed: Expected false for no loop");

        // Test Case 3: Loop exists (pos = 0)
        ListNode head3 = createLinkedList(new int[]{6, 3, 7});
        createLoop(head3, 0);
        assertTrue(solution.hasCycle(head3), "Test Case 3 Failed: Expected true for loop at pos 0");

        // Test Case 4: Single node with loop (pos = 0)
        ListNode head4 = new ListNode(1);
        createLoop(head4, 0);
        assertTrue(solution.hasCycle(head4), "Test Case 4 Failed: Expected true for single node with loop");

        // Test Case 5: Single node without loop (pos = -1)
        ListNode head5 = new ListNode(1);
        assertFalse(solution.hasCycle(head5), "Test Case 5 Failed: Expected false for single node without loop");

        // Test Case 6: Empty list
        ListNode head6 = null;
        assertFalse(solution.hasCycle(head6), "Test Case 6 Failed: Expected false for empty list");
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

    private static void createLoop(ListNode head, int pos) {
        if (pos == -1 || head == null) return;
        ListNode current = head;
        ListNode loopNode = null;
        int index = 0;

        // Traverse the list to find the loop node
        while (current.next != null) {
            if (index == pos) {
                loopNode = current;
            }
            current = current.next;
            index++;
        }

        // Special case: Single node or loop at the head
        if (pos == 0 && loopNode == null) {
            loopNode = head;
        }

        // Create the loop
        current.next = loopNode;
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
Brute - Keep track of visited nodes
T - O(n)
S - O(n)
 */
class Solution {
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> visited = new HashSet<>();

        ListNode current = head;
        while (current != null) {
            if (visited.contains(current)) {
                return true;
            }
            visited.add(current);
            current = current.next;
        }

        return false;
    }
}

/*
Optimal - Floyd's Cycle Detection Algorithm / Tortoise-Hare method
T - O(n)
S - O(1)
 */
class Solution2 {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}