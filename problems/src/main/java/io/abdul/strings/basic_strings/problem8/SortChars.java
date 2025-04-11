package io.abdul.strings.basic_strings.problem8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-strings/sort-characters-by-frequency
public class SortChars {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.frequencySort("tree"));
        System.out.println(solution.frequencySort("raaaajj"));
        System.out.println(solution.frequencySort("bbccddaaa"));
    }
}

/*
T - O(n log(n))
S - O(1)
 */
class Solution {
    public List<Character> frequencySort(String s) {
        CharCount[] charCounts = new CharCount[26];
        for (int i = 0; i < charCounts.length; i++) {
            charCounts[i] = new CharCount();
        }

        int length = s.length();
        int asciiStart = 97;
        for (int i = 0; i < length; i++) {
            CharCount c = charCounts[((int) s.charAt(i)) - asciiStart];
            if (c.c == '-') {
                c.c = s.charAt(i);
            }
            c.count++;
        }

        Arrays.sort(charCounts);

        List<Character> charsSorted = new ArrayList<>(s.length());
        for (CharCount charCount : charCounts) {
            if (charCount.c != '-') {
                charsSorted.add(charCount.c);
            }
        }

        return charsSorted;
    }

    private static class CharCount implements Comparable<CharCount> {
        private char c = '-';
        private int count;

        @Override
        public int compareTo(CharCount o) {
            int countCompare = Integer.compare(o.count, count);
            return countCompare != 0 ? countCompare : Character.compare(c, o.c);
        }
    }
}