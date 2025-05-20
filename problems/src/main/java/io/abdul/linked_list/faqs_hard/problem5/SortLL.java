package io.abdul.linked_list.faqs_hard.problem5;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SortLL {
    public static void main(String[] args) {

        Solution solution = new Solution();

        // Test Case 1: Mixed positive and duplicate values
        ListNode head1 = createLinkedList(new int[]{5, 6, 1, 2, 1});
        ListNode result1 = solution.sortList(head1);
        assertArrayEquals(new int[]{1, 1, 2, 5, 6}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: Mixed positive and negative values
        ListNode head2 = createLinkedList(new int[]{6, 5, -1, -2, -3});
        ListNode result2 = solution.sortList(head2);
        assertArrayEquals(new int[]{-3, -2, -1, 5, 6}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: All negative values with duplicates
        ListNode head3 = createLinkedList(new int[]{-1, -2, -3, -1});
        ListNode result3 = solution.sortList(head3);
        assertArrayEquals(new int[]{-3, -2, -1, -1}, toArray(result3), "Test Case 3 Failed");

        // Test Case 4: Single node
        ListNode head4 = createLinkedList(new int[]{1});
        ListNode result4 = solution.sortList(head4);
        assertArrayEquals(new int[]{1}, toArray(result4), "Test Case 4 Failed");

        // Test Case 5: Empty list
        ListNode head5 = null;
        ListNode result5 = solution.sortList(head5);
        assertNull(result5, "Test Case 5 Failed: Expected null for empty list");

        // Test Case 6: Already sorted list
        ListNode head6 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result6 = solution.sortList(head6);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, toArray(result6), "Test Case 6 Failed");

        // Test Case 7: Reverse sorted list
        ListNode head7 = createLinkedList(new int[]{5, 4, 3, 2, 1});
        ListNode result7 = solution.sortList(head7);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, toArray(result7), "Test Case 7 Failed");
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
Optimal - Merge sort
T - O(n logn)
S - O(1)

8 9 1 4 1 4 5 2 1 3 -> null
8 9 1 4 1 -> null 4 5 2 1 3 -> null
8 9 1 -> null 4 1 -> null
8 9 -> null 1 -> null 4 -> null 1 -> null
8 -> null 9 -> null 1 -> null 4 -> null 1 -> null
8 -> 9 -> null 1 -> null 4 -> null 1 -> null
1 -> 8 -> 9 -> null 4 -> null 1 -> null
1 -> 8 -> 9 -> null 1 -> 4 -> null
1 -> 1 -> 4 -> 8 -> 9 -> null
 */
class Solution {
    public ListNode sortList(ListNode head) {
        return sort(head);
    }

    private ListNode sort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // find mid
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        // slow is at mid
        // prev is before mid

        // Disconnect part 1 & 2 and make them two diff LLs
        ListNode head2 = prev.next;
        prev.next = null;

        ListNode part1 = sort(head);
        ListNode part2 = sort(head2);
        return merge(part1, part2);
    }

    private ListNode merge(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode(-1);
        ListNode tail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        while (list1 != null) {
            tail.next = list1;
            tail = tail.next;
            list1 = list1.next;
        }

        while (list2 != null) {
            tail.next = list2;
            tail = tail.next;
            list2 = list2.next;
        }

        return dummyHead.next;
    }
}
