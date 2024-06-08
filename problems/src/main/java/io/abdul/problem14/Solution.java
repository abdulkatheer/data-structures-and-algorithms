package io.abdul.problem14;

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}

// https://leetcode.com/problems/swap-nodes-in-pairs/description/
public class Solution {
    public ListNode swapPairs(ListNode head) {
        return swap(head, 1);
    }

    private static ListNode swap(ListNode root, int nodeNo) {
        if (root == null) {
            return root;
        }
        root.next = swap(root.next, nodeNo+1);
        if (nodeNo % 2 == 1) {
            if (root.next != null) {
                // a -> b -> c
                // b -> a -> c
                ListNode a = root;
                ListNode b = root.next;
                ListNode c = root.next.next;
                b.next = a;
                a.next = c;
                return b;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
//        System.out.println(s.swapPairs(new ListNode(9)));
        System.out.println(s.swapPairs(new ListNode(9, new ListNode(10))));
        System.out.println(s.swapPairs(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))))));
    }
}