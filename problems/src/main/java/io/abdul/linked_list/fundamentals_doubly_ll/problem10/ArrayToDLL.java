package io.abdul.linked_list.fundamentals_doubly_ll.problem10;

public class ArrayToDLL {
}

class ListNode {
    int val;
    ListNode next;
    ListNode prev;

    ListNode() {
        val = 0;
        next = null;
        prev = null;
    }

    ListNode(int data1) {
        val = data1;
        next = null;
        prev = null;
    }

    ListNode(int data1, ListNode next1, ListNode prev1) {
        val = data1;
        next = next1;
        prev = prev1;
    }
}

class Solution {
    public ListNode arrayToLinkedList(int[] nums) {
        if (nums.length == 0) {
            return null;
        }
        ListNode head = new ListNode(nums[0]);
        ListNode prev = head;
        for (int i = 1; i < nums.length; i++) {
            ListNode current = new ListNode(nums[i]);
            current.prev = prev;
            prev.next = current;

            prev = current;
        }
        return head;
    }
}
