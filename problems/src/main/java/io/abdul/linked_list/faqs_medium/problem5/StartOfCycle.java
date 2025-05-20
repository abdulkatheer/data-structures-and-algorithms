package io.abdul.linked_list.faqs_medium.problem5;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StartOfCycle {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Test Case 1: Cycle starts at the head
        ListNode head1 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        createLoop(head1, 0);
        assertEquals(1, solution.findStartingPoint(head1).val, "Test Case 1 Failed: Expected starting node value 1");

        // Test Case 2: Cycle starts at the tail
        ListNode head2 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        createLoop(head2, 4);
        assertEquals(5, solution.findStartingPoint(head2).val, "Test Case 2 Failed: Expected starting node value 5");

        // Test Case 3: Cycle starts in the middle
        ListNode head3 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        createLoop(head3, 2);
        assertEquals(3, solution.findStartingPoint(head3).val, "Test Case 3 Failed: Expected starting node value 3");

        // Test Case 4: Single element with a cycle
        ListNode head4 = new ListNode(1);
        createLoop(head4, 0);
        assertEquals(1, solution.findStartingPoint(head4).val, "Test Case 4 Failed: Expected starting node value 1");

        // Test Case 5: Single element without a cycle
        ListNode head5 = new ListNode(1);
        assertNull(solution.findStartingPoint(head5), "Test Case 5 Failed: Expected null for single node without loop");

        // Test Case 6: No cycle
        ListNode head6 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        assertNull(solution.findStartingPoint(head6), "Test Case 6 Failed: Expected null for no loop");

        // Test Case 7: Empty list
        ListNode head7 = null;
        assertNull(solution.findStartingPoint(head7), "Test Case 7 Failed: Expected null for empty list");
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
        while (current != null) {
            if (index == pos) {
                loopNode = current;
            }
            if (current.next == null) {
                // Create the loop when reaching the tail
                current.next = loopNode;
                break;
            }
            current = current.next;
            index++;
        }
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
Brute - Additional space to keep track of visited nodes
T - O(n)
S - O(n)
 */
class Solution {
    public ListNode findStartingPoint(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        HashSet<ListNode> visited = new HashSet<>();
        ListNode current = head;
        while (current != null) {
            if (visited.contains(current)) {
                return current; // cycle found, stop
            }
            visited.add(current);
            current = current.next;
        }

        return null; //  no cycle found
    }
}

/*
Optimal - Floyd's Cycle Detection Algorithm
T - O(n)
S - O(1)

More details in readme.md of fast-slow-pointer chapter
 */
class Solution2 {
    public ListNode findStartingPoint(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        // Detect the cycle
        ListNode slow = head, fast = head;

        boolean cycleExists = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                cycleExists = true;
                break;
            }
        }

        if (!cycleExists) {
            return null;
        }

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }
}