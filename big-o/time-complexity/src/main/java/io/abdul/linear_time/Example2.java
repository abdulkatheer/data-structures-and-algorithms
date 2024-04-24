package io.abdul.linear_time;

// O(n/2) -> O(n)
public class Example2 {
    public static void main(String[] args) {
        printHalfNumbers(new int[] {10, 1, 20, 34, 12, 45, 67});
    }

    private static void printHalfNumbers(int[] numbers) {
        if (numbers.length != 0) {
            for (int i = 0; i < numbers.length / 2; i++) {
                System.out.print(numbers[i] + " ");
            }
        }
    }
}
