package io.abdul.problem22;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// https://leetcode.com/problems/elimination-game/
class Solution {
    public int lastRemaining(int n) {
        IntStream evenIntStream2 = IntStream.iterate(1, i -> i + 1);

        Stream<Integer> stream2 = evenIntStream2.limit(n).boxed();

        LinkedList<Integer> linkedList = stream2.collect(Collectors.toCollection(LinkedList::new));
        removeAlternative(linkedList, true);
        return linkedList.get(0);
    }

    private static void removeAlternative(LinkedList<Integer> elements, boolean start) {
        if (elements.size() == 1) {
            return;
        }

        if (start) { // head to tail
            Iterator<Integer> iterator = elements.iterator();

            int i = 0;
            while (iterator.hasNext()) {
                if (i % 2 == 0) {
                    iterator.next();
                    iterator.remove();
                } else {
                    iterator.next();
                }
                i++;
                if (elements.size() == 1) {
                    break;
                }
            }
        } else { // tail to head
            Iterator<Integer> iterator = elements.descendingIterator();

            int i = 0;
            while (iterator.hasNext()) {
                if (i % 2 == 0) {
                    iterator.next();
                    iterator.remove();
                } else {
                    iterator.next();
                }
                i++;
                if (elements.size() == 1) {
                    break;
                }
            }
        }
        removeAlternative(elements, !start);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.lastRemaining(9));
        System.out.println(solution.lastRemaining(47));
        System.out.println(solution.lastRemaining(17));
        System.out.println(solution.lastRemaining(25));
    }
}