package io.abdul.recursion.practice.problem13;

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

// #tag_recursion
// https://leetcode.com/problems/add-two-numbers/description/
public class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();
        add(l1, l2, result, 0);
        return result;
    }

    private static int add(ListNode l1, ListNode l2, ListNode result, int carry) {
        int n1 = l1 != null ? l1.val : 0;
        int n2 = l2 != null ? l2.val : 0;
        int sum = carry + n1 + n2;
        int a = sum / 10;
        int b = sum % 10;
        result.val = b;
        carry = a;
        ListNode l1Next = l1 == null ? null : l1.next;
        ListNode l2Next = l2 == null ? null : l2.next;
        if (l1Next == null && l2Next == null && carry == 0) {
            return 0;
        }
        result.next = new ListNode();
        return add(l1Next, l2Next, result.next, a);
    }

    public ListNode addItr(ListNode l1, ListNode l2) {
        ListNode result = new ListNode();

        int carry = 0;
        ListNode current = result;
        while (l1 != null || l2 != null || carry != 0) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = carry + n1 + n2;
            int a = sum / 10;
            int b = sum % 10;
            current.val = b;
            carry = a;

            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
            if (l1 == null && l2 == null && carry == 0) {
                break;
            }
            current.next = new ListNode();
            current = current.next;
        }
        return result;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        // 9,9,9,9,9,9,9
        // 9,9,9,9
//        ListNode result = s.addTwoNumbers(
//                new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))))))),
//                new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9)))));
//        System.out.println(result);

        System.out.println(s.addTwoNumbers(
                new ListNode(2, new ListNode(4, new ListNode(3))),
                new ListNode(5, new ListNode(6, new ListNode(4)))));

        System.out.println(s.addItr(
                new ListNode(2, new ListNode(4, new ListNode(3))),
                new ListNode(5, new ListNode(6, new ListNode(4)))));
    }
}