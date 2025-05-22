package io.abdul.linked_list.logic_building.problem5;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReverseLL {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();

        // Test case 1: Multiple nodes
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode expected1 = new ListNode(5, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(1)))));
        assertTrue(areEqual(expected1, solution.reverseList(head1)));

        // Test case 2: Two nodes
        ListNode head2 = new ListNode(6, new ListNode(8));
        ListNode expected2 = new ListNode(8, new ListNode(6));
        assertTrue(areEqual(expected2, solution.reverseList(head2)));

        // Test case 3: Single node
        ListNode head3 = new ListNode(1);
        ListNode expected3 = new ListNode(1);
        assertTrue(areEqual(expected3, solution.reverseList(head3)));

        // Test case 4: Empty list
        ListNode head4 = null;
        assertNull(solution.reverseList(head4));
    }

    private static boolean areEqual(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
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
Brute - Data replacement
T - O(n) - 2n
S - O(n)
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        ArrayList<Integer> nums = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            nums.add(current.val);
            current = current.next;
        }

        current = head;
        for (int i = nums.size() - 1; i >= 0; i--) {
            current.val = nums.get(i);
            current = current.next;
        }

        return head;
    }
}

/*
Better - Recursive
T - O(n)
S - O(n) (stack)

Case 1:
5 -> 2 -> null | 2 -> 5 -> null
3 -> 4 -> 5 -> null
5 -> null
5 -> 4 -> null
5 -> 4 -> 3
 */
class Solution2 {
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        return reverse(head);
    }

    private ListNode reverse(ListNode head) {
        if (head.next == null) { // Single node is reversed
            return head;
        }
        ListNode reversed = reverse(head.next); // head.next will be the new tail for reversed
        ListNode newTail = head.next; // We know which is moved to tail,
        newTail.next = head; // adding head to the tail of reversed
        head.next = null;
        return reversed; // the new head after reversal
    }
}

/*
Optimal - Iterative
T - O(n)
S - O(1)

1 -> 2 -> 3 -> null
2 -> 1 -> 3 -> null
 */
class Solution3 {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode reversedTail = head; // Only head is reversed, so that's the tail
        ListNode current = head.next; // 2
        while (current != null) {
            ListNode currentNext = current.next; // move each node to the head one by one 3
            current.next = head; // 2 -> 1 -> 2 -> 1...
            reversedTail.next = currentNext; // As one element is reversed now, we move reversed tail right side
            head = current; // current becomes new head
            current = currentNext; // Head of unreversed, will be reversing this in next iteration
        }

        return head;
    }
}

/*
Optimal - Iterative
T - O(n)
S - O(1)

1 -> 2 -> 3 -> null
1 -> null
2 -> 1 -> null
3 -> 2 -> 1 -> null
 */
class Solution4 {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode currentNext = current.next;
            current.next = prev;
            prev = current;
            current = currentNext;

        }
        return prev;
    }
}