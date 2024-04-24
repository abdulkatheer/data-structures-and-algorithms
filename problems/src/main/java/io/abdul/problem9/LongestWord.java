package io.abdul.problem9;

public class LongestWord {

    // Time - O(n)
    // Space - O(1)
    private static String longestWordV1(String sentence) {
        char[] chars = sentence.toCharArray();

        if (sentence.isEmpty() || sentence.length() == 1) {
            return sentence;
        }

        int maxSoFar = Integer.MIN_VALUE;
        int start = -1;
        int end = -1;
        int counter = 0;
        int temp = -1;

        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= 'a' && chars[i] <= 'z') || (chars[i] >= 'A' && chars[i] <= 'Z')) {
                counter++;
                if (temp == -1) {
                    temp = i;
                }
            } else { // word ended
                if (temp != -1 && counter > maxSoFar) {
                    start = temp;
                    end = i - 1;
                    temp = -1;
                    maxSoFar = counter;
                    counter = 0;
                }
            }
            if (i == chars.length - 1) {
                if (counter > maxSoFar) {
                    start = temp;
                    end = i;
                }
            }
        }

        return String.copyValueOf(chars, start, maxSoFar + 1);

    }

    public static void main(String[] args) {
        System.out.println(longestWordV1("fun&!! time"));
        System.out.println(longestWordV1("I love dogs"));
    }
}
