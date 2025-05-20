package io.abdul.linked_list.logic_building.problem3;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Sort012 {
    public static void main(String[] args) {

//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test case 1: Mixed 0s, 1s, and 2s
        ListNode head1 = new ListNode(1, new ListNode(0, new ListNode(2, new ListNode(0, new ListNode(1)))));
        ListNode expected1 = new ListNode(0, new ListNode(0, new ListNode(1, new ListNode(1, new ListNode(2)))));
        assertTrue(areEqual(expected1, solution.sortList(head1)));

        // Test case 2: All 1s with one 0
        ListNode head2 = new ListNode(1, new ListNode(1, new ListNode(1, new ListNode(0))));
        ListNode expected2 = new ListNode(0, new ListNode(1, new ListNode(1, new ListNode(1))));
        assertTrue(areEqual(expected2, solution.sortList(head2)));

        // Test case 3: All 2s with one 1
        ListNode head3 = new ListNode(2, new ListNode(2, new ListNode(1, new ListNode(2))));
        ListNode expected3 = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(2))));
        assertTrue(areEqual(expected3, solution.sortList(head3)));

        // Test case 4: Already sorted list
        ListNode head4 = new ListNode(0, new ListNode(0, new ListNode(1, new ListNode(1, new ListNode(2)))));
        ListNode expected4 = new ListNode(0, new ListNode(0, new ListNode(1, new ListNode(1, new ListNode(2)))));
        assertTrue(areEqual(expected4, solution.sortList(head4)));

        // Test case 5: Single node
        ListNode head5 = new ListNode(1);
        ListNode expected5 = new ListNode(1);
        assertTrue(areEqual(expected5, solution.sortList(head5)));

        // Test case 6: Empty list
        ListNode head6 = null;
        assertNull(solution.sortList(head6));

        // Test case 7: Single node
        ListNode head7 = new ListNode(0);
        ListNode expected7 = new ListNode(0);
        assertTrue(areEqual(expected7, solution.sortList(head7)));

        // Test case 8: Single node
        ListNode head8 = new ListNode(2);
        ListNode expected8 = new ListNode(2);
        assertTrue(areEqual(expected8, solution.sortList(head8)));
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
Brute - Recreate nodes or replace data
T - O(n)
S - O(1)
 */
class Solution {
    public ListNode sortList(ListNode head) {
        int zeros = 0, ones = 0, twos = 0;

        ListNode current = head;
        while (current != null) {
            if (current.val == 0) {
                zeros++;
            } else if (current.val == 1) {
                ones++;
            } else {
                twos++;
            }
            current = current.next;
        }

        current = head;
        for (int i = 0; i < zeros; i++) {
            current.val = 0;
            current = current.next;
        }

        for (int i = 0; i < ones; i++) {
            current.val = 1;
            current = current.next;
        }

        for (int i = 0; i < twos; i++) {
            current.val = 2;
            current = current.next;
        }

        return head;
    }
}

/*
Optimal - Move 0 to head, Move 2 to tail
T - O(n)
S - O(1)
 */
class Solution2 {
    public ListNode sortList(ListNode head) {
        if (head == null) {
            return null;
        }

        int size = 1;
        ListNode tail = head;
        while (tail.next != null) {
            size++;
            tail = tail.next;
        }

        int c = 0;
        ListNode prev = null;
        ListNode curr = head;
        while (c < size) {
            if (curr.val == 0 && curr != head) { // current is zero and not at head
                ListNode newHead = curr;
                ListNode currNext = curr.next;
                newHead.next = head;
                head = newHead;
                if (prev != null) {
                    prev.next = currNext;
                }
                curr = currNext;
            } else if (curr.val == 2 && curr != tail) { // current is two and not at tail
                ListNode newTail = curr;
                ListNode currNext = curr.next;
                newTail.next = null;
                tail.next = newTail;
                tail = newTail;
                if (prev != null) {
                    prev.next = currNext;
                } else {
                    head = currNext;
                }
                curr = currNext;
            } else {
                prev = curr;
                curr = curr.next;
            }
            c++;
        }

        return head;
    }
}

/*
Optimal - Keep separate LL for zeros,ones,twos
T - O(n)
S - O(1)
 */
class Solution3 {
    public ListNode sortList(ListNode head) {
        ListNode onesDummyHead = new ListNode(-1);
        ListNode twosDummyHead = new ListNode(-1);
        ListNode zerosDummyHead = new ListNode(-1);

        ListNode current = head;
        ListNode zerosCurrent = zerosDummyHead;
        ListNode onesCurrent = onesDummyHead;
        ListNode twosCurrent = twosDummyHead;
        while (current != null) {
            if (current.val == 0) {
                zerosCurrent.next = current;
                zerosCurrent = zerosCurrent.next;
            } else if (current.val == 1) {
                onesCurrent.next = current;
                onesCurrent = onesCurrent.next;
            } else {
                twosCurrent.next = current;
                twosCurrent = twosCurrent.next;
            }
            current = current.next;
        }

        twosCurrent.next = null;
        onesCurrent.next = twosDummyHead.next;
        zerosCurrent.next = onesDummyHead.next;

        return zerosDummyHead.next;
    }
}