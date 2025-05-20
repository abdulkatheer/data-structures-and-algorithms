package io.abdul.linked_list.logic_building.problem4;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoveNthNodeFromBack {
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        // Test case 1: Remove 2nd node from the back
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode expected1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(5))));
        assertTrue(areEqual(expected1, solution.removeNthFromEnd(head1, 2)));

        // Test case 2: Remove 5th node from the back (head node)
        ListNode head2 = new ListNode(5, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(1)))));
        ListNode expected2 = new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(1))));
        assertTrue(areEqual(expected2, solution.removeNthFromEnd(head2, 5)));

        // Test case 3: Remove last node
        ListNode head3 = new ListNode(9, new ListNode(8, new ListNode(7)));
        ListNode expected3 = new ListNode(9, new ListNode(8));
        assertTrue(areEqual(expected3, solution.removeNthFromEnd(head3, 1)));

        // Test case 4: Single node list
        ListNode head4 = new ListNode(1);
        assertNull(solution.removeNthFromEnd(head4, 1));

        // Test case 5: Remove middle node
        ListNode head5 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        ListNode expected5 = new ListNode(1, new ListNode(2, new ListNode(4)));
        assertTrue(areEqual(expected5, solution.removeNthFromEnd(head5, 2)));
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
Brute - Find size and then remove
T - O(n) - 2n
S - O(1)
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }

        int size = 0;
        ListNode curr = head;

        while (curr != null) {
            size++;
            curr = curr.next;
        }

        int c = size - n + 1;

        if (c == 1) { // head
            return head.next;
        }

        // delete cth node from front
        ListNode prev = head;
        ListNode current = head.next;
        int i = 1;
        while (current != null) {
            i++;
            if (i == c) {
                prev.next = current.next;
                break;
            }
            prev = current;
            current = current.next;
        }

        return head;
    }
}

/*
Optimal - Slow/fast pointer approach
T - O(n)
S - O(1)

Find n+1 th from the last and set n+1th.next = n+1th.next.next
 */
class Solution2 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        int i = 1;
        while (i < n + 1) {
            fast = fast.next;
            i++;
        }
        // now fast is at (n+1)th position from left

        if (fast == null) { // n=size of LL, so deleting the head
            return head.next;
        }

        ListNode slow = head;
        while (fast.next != null) { // moving fast to tail
            slow = slow.next;
            fast = fast.next;
        }
        // slow.next is delete (nth) node
        slow.next = slow.next.next;

        return head;
    }
}
