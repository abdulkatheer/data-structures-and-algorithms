package io.abdul.linear_time;

// O(n)
public class Example1 {
    public static void main(String[] args) {
        sumOfAllNumbers(new int[] {10, 1, 20, 34, 12, 45, 67});
    }

    private static void sumOfAllNumbers(int[] numbers) {
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        System.out.println("Sum=" + sum);
    }
}
