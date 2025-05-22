package io.abdul.linked_list.logic_building.problem2;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SegregateOddEven {
    public static void main(String[] args) {

//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test case 1: Multiple nodes with odd and even indices
        ListNode head1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode expected1 = new ListNode(1, new ListNode(3, new ListNode(5, new ListNode(2, new ListNode(4)))));
        assertTrue(areEqual(expected1, solution.oddEvenList(head1)));

        // Test case 2: Multiple nodes with odd and even indices
        ListNode head2 = new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(1))));
        ListNode expected2 = new ListNode(4, new ListNode(2, new ListNode(3, new ListNode(1))));
        assertTrue(areEqual(expected2, solution.oddEvenList(head2)));

        // Test case 3: Single node
        ListNode head3 = new ListNode(1);
        ListNode expected3 = new ListNode(1);
        assertTrue(areEqual(expected3, solution.oddEvenList(head3)));

        // Test case 4: Empty list
        ListNode head4 = null;
        assertNull(solution.oddEvenList(head4));
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
Brute - Create new nodes for result OR Store nums in array and do data replacement
T - O(n)
S - O(n)
 */
class Solution {
    public ListNode oddEvenList(ListNode head) {
        ArrayList<Integer> nums = new ArrayList<>();

        // Copy odd
        ListNode current = head;
        int i = 0;
        while (current != null) {
            i++;
            if ((i & 1) == 1) {
                nums.add(current.val);
            }
            current = current.next;
        }

        // Copy even
        current = head;
        i = 0;
        while (current != null) {
            i++;
            if ((i & 1) == 0) {
                nums.add(current.val);
            }
            current = current.next;
        }

        // Copy result back to LL
        current = head;
        i = 0;
        while (current != null) {
            current.val = nums.get(i);
            i++;
            current = current.next;
        }

        return head;
    }
}

/*
Optimal - In-place shifting of elements
T - O(n) -> 2n, n to find tail/size, n to split
S - O(1)
 */
class Solution2 {
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode tail = head;
        int size = 1;
        while (tail.next != null) {
            tail = tail.next;
            size++;
        }

        if (size <= 2) {
            return head;
        }

        int c = 1;
        ListNode prev = head;
        ListNode curr = head.next;
        while (c < size) {
            c++;

            if ((c & 1) != 1) { // even position
                ListNode newTail = curr;
                ListNode currNext = curr.next;
                prev.next = currNext;
                newTail.next = null;
                tail.next = newTail;
                tail = newTail;

                curr = currNext; // prev stays same
            } else {
                prev = curr;
                curr = curr.next;
            }
        }

        return head;
    }
}

/*
Optimal - In-place shifting of elements
T - O(n)
S - O(1)
 */
class Solution3 {
    public ListNode oddEvenList(ListNode head) {
        ListNode oddDummyHead = new ListNode(-1);
        ListNode evenDummyHead = new ListNode(-1);
        ListNode oddCurrent = oddDummyHead;
        ListNode evenCurrent = evenDummyHead;

        ListNode current = head;
        int c = 0;
        while (current != null) {
            c++;
            if ((c & 1) == 1) {
                oddCurrent.next = current;
                oddCurrent = oddCurrent.next;
            } else {
                evenCurrent.next = current;
                evenCurrent = evenCurrent.next;
            }
            current = current.next;
        }

        head = oddDummyHead.next;
        ListNode tail = evenCurrent;
        oddCurrent.next = evenDummyHead.next;
        tail.next = null;

        return head;
    }
}