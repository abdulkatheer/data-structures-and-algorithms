package io.abdul.problem1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommonItem {
    public static void main(String[] args) {
        char[] set1 = new char[]{'a', 'c', 'd', 'g', 'f', 'x'};
        char[] set2 = new char[]{'n', 's', 'z', 'x'};

        System.out.println(containsCommonItemV1(set1, set2));
        System.out.println(containsCommonItemV2(set1, set2));
        System.out.println(containsCommonItemV3(set1, set2));
    }

    // O(m*n)
    private static boolean containsCommonItemV1(char[] set1, char[] set2) {
        for (int i = 0; i < set1.length; i++) {
            for (int j = 0; j < set2.length; j++) {
                if (set1[i] == set2[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // O(m+n)
    private static boolean containsCommonItemV2(char[] set1, char[] set2) {
        Map<Character, Boolean> set1AsMap = new HashMap<>();
        for (char c : set1) { // O(m)
            set1AsMap.put(c, true);
        }
        for (char c : set2) { // O(n)
            if (set1AsMap.containsKey(c)) { // O(1)
                return true;
            }
        }
        return false;
    }

    // O(m+n)
    private static boolean containsCommonItemV3(char[] set1, char[] set2) {
        Set<Character> set1AsMap = new HashSet<>();
        for (char c : set1) { // O(m)
            set1AsMap.add(c);
        }
        for (char c : set2) { // O(n)
            if (set1AsMap.contains(c)) { // O(1)
                return true;
            }
        }
        return false;
    }
}
