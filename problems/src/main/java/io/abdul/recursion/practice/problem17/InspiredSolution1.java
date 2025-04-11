package io.abdul.recursion.practice.problem17;

// https://leetcode.com/problems/integer-to-english-words/solutions/5058107/kotlin-convert-integer-to-words-detailed-explanation-and-code/
public class InspiredSolution1 {
    private static String[] LESS_THAN_20 = new String[]{"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
            "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

    private static String[] TENS = new String[]{"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    private static String[] THOUSANDS = new String[]{"", "Thousand", "Million", "Billion"};


    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }

        int thousand = 0;
        String result = "";
        do {
            int threeDigitPart = num % 1000;
            String p = convert_0_to_999(threeDigitPart);
            if (!p.isBlank()) {
                result = (convert_0_to_999(threeDigitPart) + " " + THOUSANDS[thousand]).trim() + " " + result;
            }
            thousand++;
            num = num / 1000;
        } while (num > 0);
        return result.trim();
    }

    private static String convert_0_to_999(int num) {
        if (num == 0) { // Base case
            return "";
        } else if (num < 20) { // Base case
            return LESS_THAN_20[num];
        } else if (num < 100) { // 20 to 99
            int lastDigit = num % 10;
            num = num / 10;
            return (TENS[num] + " " + convert_0_to_999(lastDigit)).trim();
        } else { // 100 to 999
            int lastTwoDigits = num % 100;
            int thirdLastDigit = num / 100;
            return (LESS_THAN_20[thirdLastDigit] + " Hundred " + convert_0_to_999(lastTwoDigits)).trim();
        }
    }

    public static void main(String[] args) {
        InspiredSolution1 solution = new InspiredSolution1();
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
