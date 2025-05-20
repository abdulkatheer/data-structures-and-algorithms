package io.abdul.linked_list.faqs_hard.problem6;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CloneLL {
    public static void main(String[] args) {

        Solution solution = new Solution();
//        Solution2 solution = new Solution2();

        // Test Case 1: Random pointers pointing to various nodes
        ListNode head1 = createLinkedList(new int[][]{{1, -1}, {2, 0}, {3, 4}, {4, 1}, {5, 2}});
        ListNode result1 = solution.copyRandomList(head1);
        assertTrue(areListsEqual(head1, result1), "Test Case 1 Failed");

        // Test Case 2: Random pointers pointing to null
        ListNode head2 = createLinkedList(new int[][]{{5, -1}, {3, -1}, {2, 1}, {1, 1}});
        ListNode result2 = solution.copyRandomList(head2);
        assertTrue(areListsEqual(head2, result2), "Test Case 2 Failed");

        // Test Case 3: All random pointers are null
        ListNode head3 = createLinkedList(new int[][]{{-1, -1}, {-2, -1}, {-3, -1}, {10, -1}});
        ListNode result3 = solution.copyRandomList(head3);
        assertTrue(areListsEqual(head3, result3), "Test Case 3 Failed");

        // Test Case 4: Single node with null random pointer
        ListNode head4 = createLinkedList(new int[][]{{1, -1}});
        ListNode result4 = solution.copyRandomList(head4);
        assertTrue(areListsEqual(head4, result4), "Test Case 4 Failed");

        // Test Case 5: Empty list
        ListNode head5 = null;
        ListNode result5 = solution.copyRandomList(head5);
        assertNull(result5, "Test Case 5 Failed: Expected null for empty list");
    }

    private static ListNode createLinkedList(int[][] nodes) {
        if (nodes.length == 0) return null;
        ListNode[] nodeArray = new ListNode[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            nodeArray[i] = new ListNode(nodes[i][0]);
        }
        for (int i = 0; i < nodes.length; i++) {
            if (i < nodes.length - 1) {
                nodeArray[i].next = nodeArray[i + 1];
            }
            if (nodes[i][1] != -1) {
                nodeArray[i].random = nodeArray[nodes[i][1]];
            }
        }
        return nodeArray[0];
    }

    private static boolean areListsEqual(ListNode original, ListNode copy) {
        while (original != null && copy != null) {
            if (original == copy || original.val != copy.val) {
                return false;
            }
            if ((original.random == null && copy.random != null) ||
                    (original.random != null && copy.random == null) ||
                    (original.random != null && original.random.val != copy.random.val)) {
                return false;
            }
            original = original.next;
            copy = copy.next;
        }
        return original == null && copy == null;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode random;

    ListNode() {
        val = 0;
        next = null;
        random = null;
    }

    ListNode(int val) {
        this.val = val;
        next = null;
        random = null;
    }

    ListNode(int val, ListNode next, ListNode random) {
        this.val = val;
        this.next = next;
        this.random = random;
    }
}

/*
Brute - Array to access random pointers at constant time
T - O(n) -> 2n, n to create and store nodes, n to update random pointer
S - O(n)

Keep LL in array for random access

 */
class Solution {
    public ListNode copyRandomList(ListNode head) {
        // Create new nodes
        HashMap<ListNode, ListNode> newNodeByOldNode = new HashMap<>();
        ListNode current = head;
        ListNode dummyHead = new ListNode(-1);
        ListNode tail = dummyHead;
        while (current != null) {
            ListNode newNode = new ListNode(current.val);
            tail.next = newNode;
            tail = tail.next;
            newNodeByOldNode.put(current, newNode);
            current = current.next;
        }

        // populate random pointer
        current = head;
        while (current != null) {
            if (current.random != null) {
                newNodeByOldNode.get(current).random = newNodeByOldNode.get(current.random);
            }
            current = current.next;
        }

        return dummyHead.next;
    }
}

/*
Optimal - Keep new nodes next to original nodes, so that find random is easy by tracking old pointers
T - O(n) -> 3n, n to create and add nodes; n to update random pointers; n to segregate new nodes out
S - O(1)
 */
class Solution2 {
    public ListNode copyRandomList(ListNode head) {
        // Create nodes
        ListNode current = head;
        while (current != null) {
            ListNode currentNext = current.next;
            current.next = new ListNode(current.val);
            current.next.next = currentNext;
            current = currentNext;
        }

        // Update random pointers
        current = head;
        while (current != null) {
            if (current.random != null) {
                current.next.random = current.random.next;
                // current is the old node and current.next is the new node
                // current.random will at its next position
            }
            current = current.next.next;
        }

        // Extract new nodes
        ListNode dummyHead = new ListNode(-1);
        ListNode tail = dummyHead;
        current = head;
        while (current != null) {
            tail.next = current.next;
            current.next = current.next.next;
            current = current.next;
            tail = tail.next;
        }

        return dummyHead.next;
    }
}