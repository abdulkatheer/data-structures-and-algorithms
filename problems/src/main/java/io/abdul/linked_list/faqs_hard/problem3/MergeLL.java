package io.abdul.linked_list.faqs_hard.problem3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MergeLL {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test Case 1: Normal merge
        ListNode list1 = createLinkedList(new int[]{2, 4, 7, 9});
        ListNode list2 = createLinkedList(new int[]{1, 2, 5, 6});
        ListNode result1 = solution.mergeTwoLists(list1, list2);
        assertArrayEquals(new int[]{1, 2, 2, 4, 5, 6, 7, 9}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: One list is empty
        ListNode list3 = createLinkedList(new int[]{1, 2, 3, 4});
        ListNode list4 = null;
        ListNode result2 = solution.mergeTwoLists(list3, list4);
        assertArrayEquals(new int[]{1, 2, 3, 4}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: Both lists are empty
        ListNode list5 = null;
        ListNode list6 = null;
        ListNode result3 = solution.mergeTwoLists(list5, list6);
        assertArrayEquals(new int[]{}, toArray(result3), "Test Case 3 Failed");

        // Test Case 4: Lists with different lengths
        ListNode list7 = createLinkedList(new int[]{0, 2});
        ListNode list8 = createLinkedList(new int[]{1, 3, 5, 6});
        ListNode result4 = solution.mergeTwoLists(list7, list8);
        assertArrayEquals(new int[]{0, 1, 2, 3, 5, 6}, toArray(result4), "Test Case 4 Failed");

        // Test Case 5: Lists with overlapping elements
        ListNode list9 = createLinkedList(new int[]{1, 2, 3, 4});
        ListNode list10 = createLinkedList(new int[]{5, 6, 10});
        ListNode result5 = solution.mergeTwoLists(list9, list10);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 10}, toArray(result5), "Test Case 5 Failed");
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
Optimal - Two-pointer
T - O(n+m)
S - O(1)
 */
class Solution {
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
                c.next = ptr1;
                ptr1 = ptr1.next;
            } else {
                c.next = ptr2;
                ptr2 = ptr2.next;
            }
            c = c.next;
        }

        while (ptr1 != null) {
            c.next = ptr1;
            ptr1 = ptr1.next;
            c = c.next;
        }

        while (ptr2 != null) {
            c.next = ptr2;
            ptr2 = ptr2.next;
            c = c.next;
        }

        return result.next;
    }
}