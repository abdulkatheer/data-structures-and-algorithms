package io.abdul.recursion.practice.problem17;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/integer-to-english-words/
// Tags: Math, String, Recursion
// #tag_recursion #tag_math #tag_string
class Solution {
    private static final Map<Integer, String> numbers = new HashMap<>();

    static {
        numbers.put(0, "");
        numbers.put(1, "One");
        numbers.put(2, "Two");
        numbers.put(3, "Three");
        numbers.put(4, "Four");
        numbers.put(5, "Five");
        numbers.put(6, "Six");
        numbers.put(7, "Seven");
        numbers.put(8, "Eight");
        numbers.put(9, "Nine");
        numbers.put(10, "Ten");
        numbers.put(11, "Eleven");
        numbers.put(12, "Twelve");
        numbers.put(13, "Thirteen");
        numbers.put(14, "Fourteen");
        numbers.put(15, "Fifteen");
        numbers.put(16, "Sixteen");
        numbers.put(17, "Seventeen");
        numbers.put(18, "Eighteen");
        numbers.put(19, "Nineteen");
        numbers.put(20, "Twenty");
        numbers.put(30, "Thirty");
        numbers.put(40, "Forty");
        numbers.put(50, "Fifty");
        numbers.put(60, "Sixty");
        numbers.put(70, "Seventy");
        numbers.put(80, "Eighty");
        numbers.put(90, "Ninety");
        numbers.put(100, "Hundred");
        numbers.put(1000, "Thousand");
        numbers.put(1000_000, "Million");
        numbers.put(1000_000_000, "Billion");
    }

    /**
     * 1 - 19 | direct
     * 1 - 1s | direct
     * 2 - 10s | direct
     * 3 - 100s | 100 and number of 100 separately
     * 4,5,6 - 1000s Thousands
     * 7,8,9 - 1000000s Millions
     * 10,11,12 - 1000_000_000s trillions
     * <p>
     * Next 3 number pairs are similar to first 3 pair
     * 234,000 - 234 + 1000 - Two Thirty Four + Thousand
     *
     * @param num
     * @return
     */
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }

        if (num < 1000) {
            return convert0_999(num);
        } else {
            return convertGreaterThan999(num, 0, "");
        }

    }

    /**
     * Split 3,3 digits
     * 123_456_789_111
     * 111 - One Hundred Eleven
     * 789 - Seven Hundred Eighty Nine Thousand
     * 456 - Four Hundred Fifty Six Million
     * 123 - One Hundred Twenty Three Trillion
     *
     * @param num
     * @return
     */
    private static String convertGreaterThan999(int num, int position, String outputSoFar) {
        if (num == 0) {
            return outputSoFar.trim();
        }
        int t = (int) Math.pow(10, position * 3);
        int lastThreeDigits = num % 1000;
        String output = "";
        if (lastThreeDigits != 0) {
            if (t != 1) {
                output = convert0_999(lastThreeDigits) + " " + numbers.get(t);
            } else {
                output = convert0_999(lastThreeDigits);
            }
        }
        return convertGreaterThan999(num / 1000, position + 1, (output + " " + outputSoFar).trim());
    }

    private static String convert0_999(int num) {
        int lastTwoDigits = num % 100;
        String output = convert0_99(lastTwoDigits);
        num = num / 100;
        if (num != 0) {
            int thirdLastDigit = num % 10;
            output = convert100s(thirdLastDigit) + " " + output;
        }
        return output.trim();
    }

    private static String convert0_99(int num) {
        if (num < 0 || num > 99) {
            throw new IllegalArgumentException("should be between 0 and 99");
        }
        if (num < 20) {
            return numbers.get(num);
        }

        int lastDigit = num % 10;
        String numSoFar = numbers.get(lastDigit);
        num = num / 10;
        if (num != 0) {
            int secondLastDigit = num % 10;
            numSoFar = numbers.get(secondLastDigit * 10) + " " + numSoFar;
        }
        return numSoFar;
    }

    private static String convert100s(int digit) {
        if (digit == 0) {
            return "";
        }
        return numbers.get(digit) + " " + numbers.get(100);
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(0 + "-->" + solution.numberToWords(0));
        System.out.println(1 + "-->" + solution.numberToWords(1));
        System.out.println(11 + "-->" + solution.numberToWords(11));
        System.out.println(19 + "-->" + solution.numberToWords(19));
        System.out.println(20 + "-->" + solution.numberToWords(20));
        System.out.println(21 + "-->" + solution.numberToWords(21));
        System.out.println(22 + "-->" + solution.numberToWords(22));
        System.out.println(29 + "-->" + solution.numberToWords(29));
        System.out.println(30 + "-->" + solution.numberToWords(30));
        System.out.println(31 + "-->" + solution.numberToWords(31));
        System.out.println(39 + "-->" + solution.numberToWords(39));
        System.out.println(40 + "-->" + solution.numberToWords(40));
        System.out.println(49 + "-->" + solution.numberToWords(49));
        System.out.println(50 + "-->" + solution.numberToWords(50));
        System.out.println(55 + "-->" + solution.numberToWords(55));
        System.out.println(66 + "-->" + solution.numberToWords(66));
        System.out.println(77 + "-->" + solution.numberToWords(77));
        System.out.println(88 + "-->" + solution.numberToWords(88));
        System.out.println(99 + "-->" + solution.numberToWords(99));
        System.out.println(100 + "-->" + solution.numberToWords(100));
        System.out.println(111 + "-->" + solution.numberToWords(111));
        System.out.println(200 + "-->" + solution.numberToWords(200));
        System.out.println(222 + "-->" + solution.numberToWords(222));
        System.out.println(300 + "-->" + solution.numberToWords(300));
        System.out.println(333 + "-->" + solution.numberToWords(333));
        System.out.println(400 + "-->" + solution.numberToWords(400));
        System.out.println(444 + "-->" + solution.numberToWords(444));
        System.out.println(500 + "-->" + solution.numberToWords(500));
        System.out.println(555 + "-->" + solution.numberToWords(555));
        System.out.println(600 + "-->" + solution.numberToWords(600));
        System.out.println(666 + "-->" + solution.numberToWords(666));
        System.out.println(700 + "-->" + solution.numberToWords(700));
        System.out.println(777 + "-->" + solution.numberToWords(777));
        System.out.println(800 + "-->" + solution.numberToWords(800));
        System.out.println(888 + "-->" + solution.numberToWords(888));
        System.out.println(900 + "-->" + solution.numberToWords(900));
        System.out.println(999 + "-->" + solution.numberToWords(999));
        System.out.println(1000 + "-->" + solution.numberToWords(1000));
        System.out.println(3000 + "-->" + solution.numberToWords(3000));
        System.out.println(4000 + "-->" + solution.numberToWords(4000));
        System.out.println(5000 + "-->" + solution.numberToWords(5000));
        System.out.println(6000 + "-->" + solution.numberToWords(6000));
        System.out.println(7000 + "-->" + solution.numberToWords(7000));
        System.out.println(8000 + "-->" + solution.numberToWords(8000));
        System.out.println(9000 + "-->" + solution.numberToWords(9000));
        System.out.println(10000 + "-->" + solution.numberToWords(10000));
        System.out.println(20000 + "-->" + solution.numberToWords(20000));
        System.out.println(30000 + "-->" + solution.numberToWords(30000));
        System.out.println(40000 + "-->" + solution.numberToWords(40000));
        System.out.println(50000 + "-->" + solution.numberToWords(50000));
        System.out.println(60000 + "-->" + solution.numberToWords(60000));
        System.out.println(100000 + "-->" + solution.numberToWords(100000));
        System.out.println(900000 + "-->" + solution.numberToWords(900000));
        System.out.println(1000000 + "-->" + solution.numberToWords(1_000_000));
        System.out.println(123_456_789 + "-->" + solution.numberToWords(123_456_789));
        System.out.println(12_345 + "-->" + solution.numberToWords(12_345));
        System.out.println(1000010 + "-->" + solution.numberToWords(1000010));
    }
}