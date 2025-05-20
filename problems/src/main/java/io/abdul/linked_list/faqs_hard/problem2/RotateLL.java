package io.abdul.linked_list.faqs_hard.problem2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RotateLL {
    public static void main(String[] args) {

//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();

        // Test Case 1: k = 2
        ListNode head1 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result1 = solution.rotateRight(head1, 2);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, toArray(result1), "Test Case 1 Failed");

        // Test Case 2: k = 4
        ListNode head2 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result2 = solution.rotateRight(head2, 4);
        assertArrayEquals(new int[]{2, 3, 4, 5, 1}, toArray(result2), "Test Case 2 Failed");

        // Test Case 3: k = 7 (k > length of list)
        ListNode head3 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result3 = solution.rotateRight(head3, 7);
        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, toArray(result3), "Test Case 3 Failed");

        // Test Case 4: k = 0 (no rotation)
        ListNode head4 = createLinkedList(new int[]{1, 2, 3, 4, 5});
        ListNode result4 = solution.rotateRight(head4, 0);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, toArray(result4), "Test Case 4 Failed");

        // Test Case 5: Single node
        ListNode head5 = createLinkedList(new int[]{1});
        ListNode result5 = solution.rotateRight(head5, 3);
        assertArrayEquals(new int[]{1}, toArray(result5), "Test Case 5 Failed");

        // Test Case 6: Empty list
        ListNode head6 = null;
        ListNode result6 = solution.rotateRight(head6, 2);
        assertNull(result6, "Test Case 6 Failed: Expected null for empty list");
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
Better - Reverse entire LL, Reverse 1 to k, Reverse k+1 to n
T - O(n) -> 2n, n to reverse entire LL, n to reverse parts
S - O(1)
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        return null;
    }
}

/*
Optimal - Update links
T - O(n) -> 2n, n -> find size, ~n to find k+1th
S - O(1)

reduce k between 0 and n-1
if 0, no rotations

find k+1 th from right and tail
k+1.next = null, k+1 will be te new tail
k+1.next is the new head
tail.next = head
 */
class Solution2 {
    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null) {
            return head;
        }

        int size = 0;
        ListNode current = head;
        while (current != null) {
            size++;
            current = current.next;
        }

        k = k % size;

        if (k == 0) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;
        int i = 1;
        while (i < k + 1) {
            fast = fast.next;
            i++;
        }
        // fast is at k+1 from left
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        // slow is at k+1 from right and fast is at tail

        ListNode newHead = slow.next;
        slow.next = null; // slow is the new tail
        fast.next = head; // fast was tail, now not a tail
        return newHead;
    }
}

/*
Optimal - Update links simplified
T - O(n) -> n + (n- n%k), n to find size; (n- n%k) to find n-kth node;
S - O(1)

find size and tail
tail.next = head
n-kth node is new tail
n-k+1th node is new head
find n-kth node and point it to null
 */
class Solution3 {
    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null) {
            return head;
        }

        int size = 1;
        ListNode tail = head;
        while (tail.next != null) {
            size++;
            tail = tail.next;
        }

        k = k % size;

        if (k == 0) {
            return head;
        }

        int i = 1;
        ListNode newTail = head;
        int t = size - k;
        while (i < t) {
            newTail = newTail.next;
            i++;
        }

        ListNode newHead = newTail.next;
        tail.next = head;
        newTail.next = null;

        return newHead;
    }
}