package io.abdul.linked_list.faqs_medium.problem7;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LengthOfCycle {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Test Case 1: Loop starts at index 1
        ListNode head1 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        createLoop(head1, 1);
        assertEquals(4, solution.findLengthOfLoop(head1), "Test Case 1 Failed: Expected loop length 4");

        // Test Case 2: No loop
        ListNode head2 = createLinkedList(new int[]{1, 3, 7, 4});
        assertEquals(0, solution.findLengthOfLoop(head2), "Test Case 2 Failed: Expected loop length 0");

        // Test Case 3: Loop starts at index 0
        ListNode head3 = createLinkedList(new int[]{6, 3, 7});
        createLoop(head3, 0);
        assertEquals(3, solution.findLengthOfLoop(head3), "Test Case 3 Failed: Expected loop length 3");

        // Test Case 4: Single node with loop
        ListNode head4 = new ListNode(1);
        createLoop(head4, 0);
        assertEquals(1, solution.findLengthOfLoop(head4), "Test Case 4 Failed: Expected loop length 1");

        // Test Case 5: Single node without loop
        ListNode head5 = new ListNode(1);
        assertEquals(0, solution.findLengthOfLoop(head5), "Test Case 5 Failed: Expected loop length 0");

        // Test Case 6: Empty list
        ListNode head6 = null;
        assertEquals(0, solution.findLengthOfLoop(head6), "Test Case 6 Failed: Expected loop length 0");
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
Brute - HashSet to keep track of visited nodes
T - O(n)
S - O(n)
 */
class Solution {
    public int findLengthOfLoop(ListNode head) {
        HashMap<ListNode, Integer> visited = new HashMap<>();

        ListNode current = head;
        int i = 0;
        while (current != null) {
            i++;
            if (visited.containsKey(current)) {
                return i - visited.get(current); // loop started
            }
            visited.put(current, i);
            current = current.next;
        }

        return 0; // no loop
    }
}

/*
Optimal - Floyd's Cycle detection algorithm
T - O(n)
S - O(1)
 */
class Solution2 {
    public int findLengthOfLoop(ListNode head) {
        // Detect cycle
        ListNode slow = head;
        ListNode fast = head;
        boolean cycleExists = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                cycleExists = true;
                break; // cycle detected
            }
        }

        if (!cycleExists) {
            return 0;
        }

        // Find start of cycle
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        // both fast and slow at start of cycle. Move any one until it comes back to start
        int i = 1;
        while (fast != slow.next) {
            i++;
            slow = slow.next;
        }

        return i;
    }
}