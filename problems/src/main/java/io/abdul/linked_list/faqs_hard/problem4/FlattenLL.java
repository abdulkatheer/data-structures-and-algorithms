package io.abdul.linked_list.faqs_hard.problem4;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FlattenLL {
    public static void main(String[] args) {

        Solution2 solution = new Solution2();

        // Test Case 1: Multiple levels of child lists
        ListNode head1 = createMultiLevelList(new int[][]{
                {1, 3, 5},
                {2, 4, 6},
                {7, 8, 9, 10}
        });
        ListNode result1 = solution.flattenLinkedList(head1);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: Single level list
        ListNode head2 = createMultiLevelList(new int[][]{
                {2, 4, 5, 10, 12, 13, 16, 17, 20}
        });
        ListNode result2 = solution.flattenLinkedList(head2);
        assertArrayEquals(new int[]{2, 4, 5, 10, 12, 13, 16, 17, 20}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: Single node
        ListNode head3 = createMultiLevelList(new int[][]{
                {1}
        });
        ListNode result3 = solution.flattenLinkedList(head3);
        assertArrayEquals(new int[]{1}, toArray(result3), "Test Case 3 Failed");

        // Test Case 4: Empty list
        ListNode head4 = null;
        ListNode result4 = solution.flattenLinkedList(head4);
        assertNull(result4, "Test Case 4 Failed: Expected null for empty list");

        // Test Case 5: Multiple levels with varying sizes
        ListNode head5 = createMultiLevelList(new int[][]{
                {1, 5, 7},
                {2, 6},
                {3, 4, 8, 9}
        });
        ListNode result5 = solution.flattenLinkedList(head5);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, toArray(result5), "Test Case 5 Failed");
    }

    private static ListNode createMultiLevelList(int[][] levels) {
        if (levels.length == 0) return null;
        ListNode head = new ListNode(levels[0][0]);
        ListNode current = head;
        for (int i = 1; i < levels[0].length; i++) {
            current.child = new ListNode(levels[0][i]);
            current = current.child;
        }
        ListNode prev = head;
        for (int i = 1; i < levels.length; i++) {
            ListNode levelHead = new ListNode(levels[i][0]);
            current = levelHead;
            for (int j = 1; j < levels[i].length; j++) {
                current.child = new ListNode(levels[i][j]);
                current = current.child;
            }
            prev.next = levelHead;
            prev = prev.next;
        }
        return head;
    }

    private static int[] toArray(ListNode head) {
        if (head == null) return new int[]{};
        int size = 0;
        ListNode current = head;
        while (current != null) {
            size++;
            current = current.child;
        }
        int[] result = new int[size];
        current = head;
        int index = 0;
        while (current != null) {
            result[index++] = current.val;
            current = current.child;
        }
        return result;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode child;

    ListNode() {
        val = 0;
        next = null;
        child = null;
    }

    ListNode(int data1) {
        val = data1;
        next = null;
        child = null;
    }

    ListNode(int data1, ListNode next1, ListNode next2) {
        val = data1;
        next = next1;
        child = next2;
    }
}

/*
Brute - Convert to linear and sort
T - O(n log n)
S - O(1)
 */
class Solution {
    public ListNode flattenLinkedList(ListNode head) {
        return null;
    }
}

/*
Optimal - Merge sorted arrays 1 by 1
T - O(n * m) -> n * 2m, n - number of LLs, 2m to merge to sorted LLs
S - O(1)

1 1 1 1 1 1 1 1 1 1  - number of nodes in each LL
  2 3 4 5 6 7 8 9 10 - complexity
 */
class Solution2 {
    public ListNode flattenLinkedList(ListNode head) {
        if (head == null || head.next == null) { // single LL
            return head;
        }

        ListNode list1 = head;
        ListNode list2 = head.next;
        list1 = mergeTwoLists(list1, list2);
        while (list2.next != null) {
            list1 = mergeTwoLists(list1, list2.next);
            list2 = list2.next;
        }
        return list1;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list2 == null) {
            return list1;
        } else if (list1 == null) {
            return list2;
        }

        ListNode ptr1 = list1;
        ListNode ptr2 = list2;
        ListNode result = new ListNode(-1);
        ListNode c = result;

        while (ptr1 != null && ptr2 != null) {
            if (ptr1.val <= ptr2.val) {
                c.child = ptr1;
                ptr1 = ptr1.child;
            } else {
                c.child = ptr2;
                ptr2 = ptr2.child;
            }
            c = c.child;
        }

        while (ptr1 != null) {
            c.child = ptr1;
            ptr1 = ptr1.child;
            c = c.child;
        }

        while (ptr2 != null) {
            c.child = ptr2;
            ptr2 = ptr2.child;
            c = c.child;
        }

        return result.child;
    }
}