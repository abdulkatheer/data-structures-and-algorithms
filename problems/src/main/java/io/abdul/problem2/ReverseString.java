package io.abdul.problem2;

public class ReverseString {
    // Copy
    // Time - O(n), Space - O(n)
    private static String reverseV1(String value) {
        char[] valueAsArr = value.toCharArray();
        char[] reversed = new char[valueAsArr.length];

        for (int i = 0, j = reversed.length - 1; i < valueAsArr.length; i++, j--) {
            reversed[j] = valueAsArr[i];
        }

        return String.copyValueOf(reversed);
    }

    // In-memory Replace
    // Time - O(n), Space - O(n)
    private static String reverseV2(String value) {
        char[] valueAsArr = value.toCharArray();

        int indexOfMid = valueAsArr.length / 2;

        for (int i = 0; i < indexOfMid; i++) {
            char temp = valueAsArr[i];
            int compIndex = valueAsArr.length - 1 - i;
            valueAsArr[i] = valueAsArr[compIndex];
            valueAsArr[compIndex] = temp;
        }

        return String.copyValueOf(valueAsArr);
    }

    public static void main(String[] args) {
        System.out.println(reverseV1("A"));
        System.out.println(reverseV1("AB"));
        System.out.println(reverseV1("Abdul Katheer"));
        System.out.println(reverseV1(""));

        System.out.println(reverseV2("A"));
        System.out.println(reverseV2("AB"));
        System.out.println(reverseV2("Abdul Katheer"));
        System.out.println(reverseV2(""));
    }
}
