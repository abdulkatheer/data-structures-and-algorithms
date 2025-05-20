package io.abdul.linked_list.fundamentals_singly_ll.problem1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Traversal {
    public static void main(String[] args) {
        Solution solution = new Solution();

        // Test case 1: Multiple nodes in the Linked List
        ListNode head1 = new ListNode(5, new ListNode(4, new ListNode(3, new ListNode(1, new ListNode(0)))));
        assertEquals(List.of(5, 4, 3, 1, 0), solution.LLTraversal(head1));

        // Test case 2: Single node in the Linked List
        ListNode head2 = new ListNode(1);
        assertEquals(List.of(1), solution.LLTraversal(head2));

        // Test case 3: Empty Linked List
        ListNode head3 = null;
        assertEquals(List.of(), solution.LLTraversal(head3));

        // Test case 4: Linked List with multiple nodes
        ListNode head4 = new ListNode(0, new ListNode(2, new ListNode(5)));
        assertEquals(List.of(0, 2, 5), solution.LLTraversal(head4));

        // Test case 5: Linked List with duplicate values
        ListNode head5 = new ListNode(1, new ListNode(1, new ListNode(1)));
        assertEquals(List.of(1, 1, 1), solution.LLTraversal(head5));

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
    public List<Integer> LLTraversal(ListNode head) {
        List<Integer> elements = new ArrayList<>();
        ListNode next = head;
        while (next != null) {
            elements.add(next.val);
            next = next.next;
        }

        return elements;
    }
}
