package io.abdul.linked_list.faqs_hard.problem1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/*
head -> 1 -> 2 -> 3 -> 4 -> 5, k = 2
prevTail=null, head=2, tail=1
prevTail=1   , head=4, tail=3
prevTail=3   , head=5
 */
public class ReverseLLInGroups {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: k = 2, odd number of nodes
        ListNode head1 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = solution.reverseKGroup(head1, 2);
        assertArrayEquals(new int[]{2, 1, 4, 3, 5}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: k = 3, odd number of nodes
        ListNode head2 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result2 = solution.reverseKGroup(head2, 3);
        assertArrayEquals(new int[]{3, 2, 1, 4, 5}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: k = 4, even number of nodes
        ListNode head3 = createLinkedList(new int[]{6, 1, 2, 3, 4, 7});
        ListNode result3 = solution.reverseKGroup(head3, 4);
        assertArrayEquals(new int[]{3, 2, 1, 6, 4, 7}, toArray(result3), "Test Case 3 Failed");

        // Test Case 4: k = 1 (no reversal)
        ListNode head4 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result4 = solution.reverseKGroup(head4, 1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, toArray(result4), "Test Case 4 Failed");

        // Test Case 5: k > length of list (no reversal)
        ListNode head5 = createLinkedList(new int[]{1, 2, 3});
        ListNode result5 = solution.reverseKGroup(head5, 5);
        assertArrayEquals(new int[]{1, 2, 3}, toArray(result5), "Test Case 5 Failed");

        // Test Case 6: Empty list
        ListNode head6 = null;
        ListNode result6 = solution.reverseKGroup(head6, 2);
        assertNull(result6, "Test Case 6 Failed: Expected null for empty list");
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
T - O(n) -> 2n times, main loop n/k times and reverse runs 2k times. (n/k) x 2k = 2n
S - O(1)
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (k == 1) {
            return head;
        }

        ListNode current = head, prevTail = null, newHead = null;
        while (current != null) { // when k times number of nodes, it'll end here
            ListNode h = reverse(current, k, prevTail);
            if (prevTail == null) {
                newHead = h;
            }
            if (h == current) { // if not k times number of nodes, final set will not be reversed
                break;
            }
            prevTail = current;
            current = prevTail.next;
        }

        return newHead;
    }

    /*
    Accepts: head -> 1 -> 2 -> 3 -> 4 -> 5 -> null, k=2, prevTail=null
    Modified: head -> 2 -> 1 -> 3 -> 4 -> 5 -> null

    Accepts: 3 -> 4 -> 5 -> null, k=2, prevTail=1
    Modified: 1 -> 4 -> 3 -> 5 -> null

    Accepts: 5 -> null, k=2, prevTail=3
     */
    private ListNode reverse(ListNode head, int k, ListNode prevTail) {
        int i = 0;
        ListNode current = head;
        boolean hasEnoughNodes = false;
        while (current != null) {
            i++;
            if (i == k) {
                hasEnoughNodes = true;
                break;
            }
            current = current.next;
        }
        if (!hasEnoughNodes) {
            return head;
        }

        i = 0;
        current = head;
        ListNode prev = null;
        ListNode currentNext = null;
        while (i < k) {
            i++;
            currentNext = current.next;
            current.next = prev;
            prev = current;
            current = currentNext;
        }

        head.next = currentNext;// linking remaining LL back to tail of reversed

        // prev is new head and head is new tail
        if (prevTail != null) {
            prevTail.next = prev;
        }
        return prev;
    }
}