package io.abdul.problem17;

// https://leetcode.com/problems/integer-to-english-words/submissions/1279084771/
public class InspiredSolution2 {
    private static final String[] LESS_THAN_20 = new String[]{"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
            "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private static final String[] TENS = new String[]{"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private static final int ONE_MILLION = 1_000_000;
    private static final int ONE_BILLION = 1_000_000_000;
    private static final int ONE_THOUSAND = 1_000;
    private static final int ONE_HUNDRED = 100;
    private static final int TEN = 10;
    private static final int TWENTY = 20;

    /**
     * From front to back
     * As we know Billion is the max limit, we can go from top to bottom
     * Find how many billions
     * Find how many millions
     * Find how many thousands
     * Find how many tens
     * @param num
     * @return
     */
    public String numberToWords(int num) {
        if (num == 0) {
            return "Zero";
        }
        return convert(num);
    }

    private static String convert(int num) {
        StringBuilder r = new StringBuilder();
        if (num < TWENTY) { // 0 to 19
            r.append(LESS_THAN_20[num]);
        } else if (num < ONE_HUNDRED) { // 20 to 99
            r.append(TENS[num / TEN]).append(" ").append(convert(num % TEN));
        } else if (num < ONE_THOUSAND) { // 100 to 999
            r.append(LESS_THAN_20[num / ONE_HUNDRED]).append(" Hundred ").append(convert(num % ONE_HUNDRED));
        } else if (num < ONE_MILLION) { // 1_000 to 999_999
            r.append(convert(num / ONE_THOUSAND)).append(" Thousand ").append(convert(num % ONE_THOUSAND));
        } else if (num < ONE_BILLION) { // 1_000_000 to 999_999_999
            r.append(convert(num / ONE_MILLION)).append(" Million ").append(convert(num % ONE_MILLION));
        } else { // 1_000_000_000 to 2_147_483_647 (MAX Integer)
            r.append(convert(num / ONE_BILLION)).append(" Billion ").append(convert(num % ONE_BILLION));
        }
        return r.toString().trim();
    }

    public static void main(String[] args) {
        InspiredSolution2 solution = new InspiredSolution2();
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
        System.out.println(2147483647 + "-->" + solution.numberToWords(2_147_483_647));
        System.out.println(Integer.MAX_VALUE);
    }
}
