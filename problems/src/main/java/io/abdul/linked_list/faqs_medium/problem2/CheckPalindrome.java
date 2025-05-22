package io.abdul.linked_list.faqs_medium.problem2;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckPalindrome {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test Case 1: Palindrome
        ListNode head1 = createLinkedList(new int[]{3, 7, 5, 7, 3});
        assertTrue(solution.isPalindrome(head1), "Test Case 1 Failed: Expected true for palindrome");

        // Test Case 2: Not a Palindrome
        ListNode head2 = createLinkedList(new int[]{1, 1, 2, 1});
        assertFalse(solution.isPalindrome(head2), "Test Case 2 Failed: Expected false for non-palindrome");

        // Test Case 3: All Same Digits
        ListNode head3 = createLinkedList(new int[]{9, 9, 9, 9});
        assertTrue(solution.isPalindrome(head3), "Test Case 3 Failed: Expected true for all same digits");

        // Test Case 4: Single Node
        ListNode head4 = createLinkedList(new int[]{1});
        assertTrue(solution.isPalindrome(head4), "Test Case 4 Failed: Expected true for single node");

        // Test Case 5: Empty List
        ListNode head5 = null;
        assertTrue(solution.isPalindrome(head5), "Test Case 5 Failed: Expected true for empty list");
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
Brute - Store in stack and compare
T - O(n)
S - O(n)
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        Stack<Integer> stack = new Stack<>();
        ListNode current = head;
        while (current != null) {
            stack.push(current.val);
            current = current.next;
        }

        current = head;
        while (current != null) {
            if (current.val != stack.pop()) {
                return false;
            }
            current = current.next;
        }
        return true;
    }
}

/*
Optimal - Find size and keep one fast-pointer at mid+1
T - O(n) - 2n + n/2; n to find size; n/2 to find mid+1; n/2 to reverse second half; n/2 to compare two halves
S - O(1)
 */
class Solution2 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        int size = 0;
        ListNode current = head;
        while (current != null) {
            size++;
            current = current.next;
        }

        int mid = (size + 1) / 2;
        ListNode right = head;
        int i = 0;
        while (i < mid) {
            right = right.next;
            i++;
        }
        // right is at mid+1 now
        right = reverse(right);

        ListNode left = head;
        while (right != null) {
            if (left.val != right.val) {
                return false;
            }
            left = left.next;
            right = right.next;
        }
        return true;
    }

    private ListNode reverse(ListNode head) {
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

/*
Optimal - fast-slow pointer method
T - O(n) - n + n/2; n/2 to find middle; n/2 to reverse; n/2 to compare

1 2 3 4 5 6
s=1 f=1
s=2 f=3
s=3 f=5 5.next.next == null
s+1 is the start of second half

1 2 3 4 5 6 7
s=1 f=1
s=2 f=3
s=3 f=5
s=4 f=7 7.next == null
s+1 is the start of second half
 */
class Solution3 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // Find middle using slow/fast pointer method
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // now slow.next is at the start of second part

        ListNode reversedSecondHalfHead = reverse(slow.next);

        ListNode first = head;
        ListNode second = reversedSecondHalfHead;

        while (second != null) {
            if (first.val != second.val) {
                return false;
            }
            first = first.next;
            second = second.next;
        }

        return true;
    }

    private ListNode reverse(ListNode head) {
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