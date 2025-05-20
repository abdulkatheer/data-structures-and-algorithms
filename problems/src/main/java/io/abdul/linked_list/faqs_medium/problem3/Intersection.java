package io.abdul.linked_list.faqs_medium.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Intersection {
    public static void main(String[] args) {

        Solution solution = new Solution();

        // Test Case 1: Intersection at node with value 4
        ListNode common = createLinkedList(new int[]{4, 5});
        ListNode headA1 = createLinkedList(new int[]{1, 2, 3});
        appendList(headA1, common);
        ListNode headB1 = createLinkedList(new int[]{7, 8});
        appendList(headB1, common);
        assertEquals(common, solution.getIntersectionNode(headA1, headB1), "Test Case 1 Failed: Expected intersection at node with value 4");

        // Test Case 2: No intersection
        ListNode headA2 = createLinkedList(new int[]{1, 2, 3});
        ListNode headB2 = createLinkedList(new int[]{8, 9});
        assertNull(solution.getIntersectionNode(headA2, headB2), "Test Case 2 Failed: Expected no intersection");

        // Test Case 3: Intersection at the head
        ListNode headA3 = createLinkedList(new int[]{4, 5, 6});
        ListNode headB3 = headA3; // Both lists are the same
        assertEquals(headA3, solution.getIntersectionNode(headA3, headB3), "Test Case 3 Failed: Expected intersection at the head");

        // Test Case 4: Single node intersection
        ListNode commonNode = new ListNode(1);
        ListNode headA4 = commonNode;
        ListNode headB4 = commonNode;
        assertEquals(commonNode, solution.getIntersectionNode(headA4, headB4), "Test Case 4 Failed: Expected intersection at single node");

        // Test Case 5: No intersection with empty list
        ListNode headA5 = createLinkedList(new int[]{1, 2, 3});
        ListNode headB5 = null;
        assertNull(solution.getIntersectionNode(headA5, headB5), "Test Case 5 Failed: Expected no intersection with empty list");
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

    private static void appendList(ListNode head, ListNode toAppend) {
        ListNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = toAppend;
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

class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int sizeA = 0;
        int sizeB = 0;
        ListNode currentA = headA;
        ListNode currentB = headB;

        while (currentA != null || currentB != null) {
            if (currentA != null) {
                sizeA++;
                currentA = currentA.next;
            }
            if (currentB != null) {
                sizeB++;
                currentB = currentB.next;
            }
        }

        currentA = headA;
        if (sizeA > sizeB) {
            int extra = sizeA - sizeB;
            int i = 0;
            while (i < extra) {
                currentA = currentA.next;
                i++;
            }
        }

        currentB = headB;
        if (sizeB > sizeA) {
            int extra = sizeB - sizeA;
            int i = 0;
            while (i < extra) {
                currentB = currentB.next;
                i++;
            }
        }

        // Now both currentA and currentB at same position from left
        ListNode intersection = null;
        while (currentA != null) {
            if (currentA.val != currentB.val) {
                intersection = null;
            } else if (intersection == null) {
                intersection = currentA;
            }

            currentA = currentA.next;
            currentB = currentB.next;
        }

        return intersection;
    }
}
