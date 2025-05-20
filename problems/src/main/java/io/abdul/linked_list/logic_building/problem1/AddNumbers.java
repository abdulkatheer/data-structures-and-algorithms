package io.abdul.linked_list.logic_building.problem1;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddNumbers {
    public static void main(String[] args) {
//        Solution solution = new Solution();
        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        // Test case 1: l1 = 5 -> 4, l2 = 4
        ListNode l1 = new ListNode(5, new ListNode(4));
        ListNode l2 = new ListNode(4);
        ListNode expected1 = new ListNode(9, new ListNode(4));
        assertTrue(areEqual(expected1, solution.addTwoNumbers(l1, l2)));

        // Test case 2: l1 = 4 -> 5 -> 6, l2 = 1 -> 2 -> 3
        l1 = new ListNode(4, new ListNode(5, new ListNode(6)));
        l2 = new ListNode(1, new ListNode(2, new ListNode(3)));
        ListNode expected2 = new ListNode(5, new ListNode(7, new ListNode(9)));
        assertTrue(areEqual(expected2, solution.addTwoNumbers(l1, l2)));

        // Test case 3: l1 = 1, l2 = 8 -> 7
        l1 = new ListNode(1);
        l2 = new ListNode(8, new ListNode(7));
        ListNode expected3 = new ListNode(9, new ListNode(7));
        assertTrue(areEqual(expected3, solution.addTwoNumbers(l1, l2)));

        // Test case 4: l1 = 0, l2 = 0
        l1 = new ListNode(0);
        l2 = new ListNode(0);
        ListNode expected4 = new ListNode(0);
        assertTrue(areEqual(expected4, solution.addTwoNumbers(l1, l2)));

        // Test case 5: l1 = 9 -> 9 -> 9, l2 = 1
        l1 = new ListNode(9, new ListNode(9, new ListNode(9)));
        l2 = new ListNode(1);
        ListNode expected5 = new ListNode(0, new ListNode(0, new ListNode(0, new ListNode(1))));
        assertTrue(areEqual(expected5, solution.addTwoNumbers(l1, l2)));

        // Convert Nums1 and Nums2 arrays to linked lists
        int[] nums1 = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        int[] nums2 = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};

        l1 = createLinkedList(nums1);
        l2 = createLinkedList(nums2);

        int[] expectedNums = {8, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 1};
        ListNode expected = createLinkedList(expectedNums);

        // Add the two numbers
        ListNode result = solution.addTwoNumbers(l1, l2);

        // Assert the result
        assertTrue(areEqual(expected, result));
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

    private static void printLinkedList(ListNode head) {
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print(" -> ");
            }
            head = head.next;
        }
        System.out.println();
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

// !!! Will not work for large numbers !!!
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        long num1 = 0, num2 = 0, multiplier = 1;

        ListNode t = l1;
        while (t != null) {
            num1 = num1 + (t.val * multiplier);
            multiplier *= 10;
            t = t.next;
        }

        multiplier = 1;
        t = l2;
        while (t != null) {
            num2 = num2 + (t.val * multiplier);
            multiplier *= 10;
            t = t.next;
        }

        long sum = num1 + num2;
        if (sum == 0) {
            return new ListNode(0);
        }

        ListNode head = new ListNode((int) (sum % 10));
        ListNode current = head;
        sum /= 10;
        while (sum > 0) {
            ListNode newNode = new ListNode((int) (sum % 10));
            current.next = newNode;
            current = newNode;
            sum /= 10;
        }

        return head;
    }
}

/*
Optimal - Practical add method with carry
T - O(max(l1,l2))
S - O(1)
 */
class Solution2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        // sum for first element
        int s = l1.val + l2.val;
        int carry = s / 10;
        ListNode result = new ListNode(s % 10);
        ListNode tail = result;

        ListNode t1 = l1.next, t2 = l2.next;
        while (t1 != null && t2 != null) {
            s = t1.val + t2.val + carry;
            ListNode newTail = new ListNode(s % 10);
            carry = s / 10;
            tail.next = newTail;
            tail = newTail;
            t1 = t1.next;
            t2 = t2.next;
        }

        while (t1 != null) { // t1 is a larger number
            s = t1.val + carry;
            ListNode newTail = new ListNode(s % 10);
            carry = s / 10;
            tail.next = newTail;
            tail = newTail;
            t1 = t1.next;
        }

        while (t2 != null) { // t2 is a larger number
            s = t2.val + carry;
            ListNode newTail = new ListNode(s % 10);
            carry = s / 10;
            tail.next = newTail;
            tail = newTail;
            t2 = t2.next;
        }

        if (carry != 0) {
            ListNode newNode = new ListNode(carry);
            tail.next = newNode;
            tail = newNode;
        }

        return result;
    }
}

/*
Optimal - Simplified
T - O(max(l1,l2))
S - O(1)
 */
class Solution3 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        int s = 0;
        int carry = 0;
        ListNode dummyHead = new ListNode(-1); // to keep reference to head pointer in the first iteration
        ListNode tail = dummyHead;

        ListNode t1 = l1, t2 = l2;
        while (t1 != null && t2 != null) {
            s = t1.val + t2.val + carry;
            ListNode newTail = new ListNode(s % 10);
            carry = s / 10;
            tail.next = newTail;
            tail = newTail;
            t1 = t1.next;
            t2 = t2.next;
        }

        while (t1 != null) { // t1 is a larger number
            s = t1.val + carry;
            ListNode newTail = new ListNode(s % 10);
            carry = s / 10;
            tail.next = newTail;
            tail = newTail;
            t1 = t1.next;
        }

        while (t2 != null) { // t2 is a larger number
            s = t2.val + carry;
            ListNode newTail = new ListNode(s % 10);
            carry = s / 10;
            tail.next = newTail;
            tail = newTail;
            t2 = t2.next;
        }

        if (carry != 0) {
            tail.next = new ListNode(carry);
        }

        return dummyHead.next;
    }
}
