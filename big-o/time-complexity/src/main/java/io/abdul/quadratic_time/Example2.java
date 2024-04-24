package io.abdul.quadratic_time;

// O(n^3)
public class Example2 {
    public static void main(String[] args) {
        printThreeDCombinations(new int[]{1, 2, 3, 4, 5});
    }

    public static void printThreeDCombinations(int[] numbers) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                for (int k = 0; k < numbers.length; k++) {
                    System.out.print("" + numbers[i] + numbers[j] + numbers[k] + " ");
                    count++;
                }
            }
        }
        System.out.println();
        System.out.println("Total executions = " + count);
    }
}
