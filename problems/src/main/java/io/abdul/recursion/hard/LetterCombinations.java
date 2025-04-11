package io.abdul.recursion.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LetterCombinations {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.letterCombinations("2"));
        System.out.println(solution.letterCombinations("23"));
    }
}

/*
T - O(3^n) 3 is the number of chars in each digit, n is the number of digits in the input
S - O(n) - n is the number of digits in the input
 */
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        HashMap<Character, String> numberPad = new HashMap<>();
        numberPad.put('2', "abc");
        numberPad.put('3', "def");
        numberPad.put('4', "ghi");
        numberPad.put('5', "jkl");
        numberPad.put('6', "mno");
        numberPad.put('7', "pqrs");
        numberPad.put('8', "tuv");
        numberPad.put('9', "wxyz");

        letterCombinations(digits, numberPad, 0, result, new StringBuilder());
        return result;
    }

    public void letterCombinations(String digits, HashMap<Character, String> numberPad, int position, List<String> result, StringBuilder buffer) {
        if (buffer.length() == digits.length()) {
            result.add(buffer.toString());
            return;
        }

        String letters = numberPad.get(digits.charAt(position));
        for (int i = 0; i < letters.length(); i++) {
            buffer.append(letters.charAt(i));
            letterCombinations(digits, numberPad, position + 1, result, buffer);
            buffer.deleteCharAt(buffer.length() - 1);
        }
    }
}
