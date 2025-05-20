package io.abdul.linked_list.faqs_medium.problem1;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Add1 {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();

        // Test Case 1: Simple Case
        ListNode head1 = createLinkedList(new int[]{1, 2, 3});
        ListNode result1 = solution.addOne(head1);
        assertTrue(areEqual(createLinkedList(new int[]{1, 2, 4}), result1), "Test Case 1 Failed");

        // Test Case 2: Carry Over
        ListNode head2 = createLinkedList(new int[]{9, 9});
        ListNode result2 = solution.addOne(head2);
        assertTrue(areEqual(createLinkedList(new int[]{1, 0, 0}), result2), "Test Case 2 Failed");

        // Test Case 3: Single Digit
        ListNode head3 = createLinkedList(new int[]{9});
        ListNode result3 = solution.addOne(head3);
        assertTrue(areEqual(createLinkedList(new int[]{1, 0}), result3), "Test Case 3 Failed");

        // Test Case 4: No Carry
        ListNode head4 = createLinkedList(new int[]{1, 0, 0});
        ListNode result4 = solution.addOne(head4);
        assertTrue(areEqual(createLinkedList(new int[]{1, 0, 1}), result4), "Test Case 4 Failed");

        // Test Case 5: Zero Case
        ListNode head5 = createLinkedList(new int[]{0});
        ListNode result5 = solution.addOne(head5);
        assertTrue(areEqual(createLinkedList(new int[]{1}), result5), "Test Case 5 Failed");
    }

    private static ListNode createLinkedList(int[] nums) {
        ListNode head = new ListNode(nums[0]);
        ListNode current = head;
        for (int i = 1; i < nums.length; i++) {
            current.next = new ListNode(nums[i]);
            current = current.next;
        }
        return head;
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
Brute - Reverse, Add, Reverse
T - O(n)
S - O(1)
 */
class Solution {
    public ListNode addOne(ListNode head) {
        ListNode reversed = reverse(head);

        int carry = 1;
        ListNode current = reversed;
        while (carry > 0) {
            int sum = current.val + carry;
            // carry will always be 1, as adding 1 to max 9 can give 10 only
            if (sum == 10) {
                current.val = 0;
                if (current.next == null) { // if at the tail
                    current.next = new ListNode(0);
                }
            } else {
                current.val = sum;
                carry = 0; // loop will exit after this
            }
            current = current.next;
        }

        return reverse(reversed);
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
Better - Recursive, Less time and more space compared to Brute
T - O(n)
S - O(n)
 */
class Solution2 {
    public ListNode addOne(ListNode head) {
        int carry = add(head);
        if (carry == 1) {
            return new ListNode(1, head);
        }
        return head;
    }

    /*
    Returns carry
     */
    private int add(ListNode node) {
        if (node == null) { // Base case
            return 1; // Adding 1 at the tail
        }

        int carry = add(node.next);
        int sum = node.val + carry;

        if (sum == 10) { // Sum can never beyond 10
            node.val = 0;
            return 1;
        } else {
            node.val = sum;
            return 0;
        }
    }
}