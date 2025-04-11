package io.abdul.strings.practice.problem10;

import java.util.HashSet;
import java.util.Set;

// #tag_string
public class FirstRecurringCharacter {
    // Time: O(n^2)
    // Space: O(1)
    private static char repeatedCharacterV1(String s) {
        char[] chars = s.toCharArray();

        int smallestRecurringCharacterIndex = Integer.MAX_VALUE;
        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    if (j < smallestRecurringCharacterIndex) {
                        smallestRecurringCharacterIndex = j;
                    }
                }
            }
        }
        return chars[smallestRecurringCharacterIndex];
    }

    // Time: O(n)
    // Space: O(n)
    private static char repeatedCharacterV2(String s) {
        char[] chars = s.toCharArray();
        Set<Character> visitedUniqueCharsSoFar = new HashSet<>();

        int smallestRecurringCharacterIndex = -1;
        for (int i = 0; i < chars.length; i++) {
            if (visitedUniqueCharsSoFar.contains(chars[i])) {
                smallestRecurringCharacterIndex = i;
                break;
            } else {
                visitedUniqueCharsSoFar.add(chars[i]);
            }
        }

        return chars[smallestRecurringCharacterIndex];
    }

    public static void main(String[] args) {
        System.out.println(repeatedCharacterV1("abccbaacz"));
        System.out.println(repeatedCharacterV2("abccbaacz"));
    }
}
