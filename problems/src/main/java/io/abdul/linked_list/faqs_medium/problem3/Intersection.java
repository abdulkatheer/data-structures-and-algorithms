package io.abdul.linked_list.faqs_medium.problem3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Intersection {
    public static void main(String[] args) {

//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

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

        ListNode headA6 = createLinkedList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        ListNode headB6 = createLinkedList(new int[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1});
        assertNull(solution.getIntersectionNode(headA6, headB6), "Test Case 2 Failed: Expected no intersection");
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

/*
Brute - Find size and eliminate excess
T - O(n+m) - n+m + n+m; n+m to find size; n+m to find intersection
 */
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

/*
Optimal - fast-slow pointer approach
T - O(n+m)
S - O(1)

Case 1: Half of both intersects
1 2 3 4 5 6 7 8 9 10
11 12 13 8 9 10

Common 6
Excess is 4
Both ptr has to travel 10 steps to reach same position
ptr1 -> 1 2 3 4 5 6 7 8 9 10 11
ptr2 -> 11 12 13 8 9 10 1 2 3 4 5
So total n+m steps
when ptr1 goes to null, starts at head2
when ptr2 goes to null, starts at head1

Case 2: None intersects
1 2 3 4 5 6 7 8 9 10
10 1 2 3 4 5 6 7 8 9

Common is 10
Excess 0
ptr1 and ptr2 travels 9 steps and reaches null.
When both are null, means no intersection is found

1 2 3 4 5 6 7 8 9 10
10 1 2
ptr1 and ptr2 to travel 10 steps to reach common ptrs
ptr1 -> 1 2 3 4 5 6 7 8 9 10 10 1 2  null
ptr2 -> 10 1 2 1 2 3 4 5 6 7 8  9 10 null
Both meets null together

Case 3: Full of one intersects
8 9 10
1 2 3 4 5 6 7 8 9 10
ptr1 and ptr2 to travel 10 steps to reach common ptrs
ptr1 -> 8 9 10 1 2 3 4 5 6 7 8
ptr2 -> 1 2 3 4 5 6 7 8 9 10 8
Both matched, that's the starting point

Case 4: Full of two intersects
1 2 3 4 5 6 7 8 9 10
8 9 10
ptr1 and ptr2 to travel 10 steps to reach common ptrs
ptr1 -> 1 2 3 4 5 6 7 8 9 10 8
ptr2 -> 8 9 10 1 2 3 4 5 6 7 8
Both matched, that's the starting point

Case 5: Both fully intersects
1 2 3 4 5 6 7 8 9 10
1 2 3 4 5 6 7 8 9 10
ptr1 -> 1
ptr2 -> 1
Both matched, that's the starting point


Simply put, when lengths are same, both travel together.
Will either find match or go to null together

When lengths are different, a ptr exchange happens and both come to a common point.
From there they travel together. Will either find match or go to null together
 */
class Solution2 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode ptr1 = headA;
        ListNode ptr2 = headB;
        ListNode intersection = null;

        boolean foundAnswer = false;
        while (!foundAnswer) {
            if (ptr1 == ptr2) { // Match found (can't be null here)
                foundAnswer = true;
                intersection = ptr1;
                continue;
            }

            ptr1 = ptr1.next;
            ptr2 = ptr2.next;

            if (ptr1 == null && ptr2 == null) { // No intersection
                foundAnswer = true;
            } else if (ptr1 == null) {
                ptr1 = headB;
            } else if (ptr2 == null) {
                ptr2 = headA;
            }
        }

        return intersection;
    }
}