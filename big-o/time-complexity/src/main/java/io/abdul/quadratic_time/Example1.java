package io.abdul.quadratic_time;

// O(n^2)
public class Example1 {
    public static void main(String[] args) {
        printSumOfPairs(new int[]{1, 2, 3, 4, 5});
    }

    public static void printSumOfPairs(int[] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                System.out.print((numbers[i] + numbers[j]) + " ");
            }
        }
    }
}
