package io.abdul.linear_time;

// O(m+n)
public class Example3 {
    public static void main(String[] args) {
        printHalfNumbers(new int[]{10, 1, 20, 34, 12, 45, 67}, new int[]{1, 2, 3, 4, 5});
    }

    private static void printHalfNumbers(int[] numbers1, int[] numbers2) {
        if (numbers1.length != 0) {
            for (int i = 0; i < numbers1.length / 2; i++) {
                System.out.print(numbers1[i] + " ");
            }
        }

        System.out.println();
        for (int i = 0; i < numbers2.length; i++) {
            System.out.print(numbers1[i] + " ");
        }
    }
}
