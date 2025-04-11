package io.abdul.array.basic_traversal_and_manipulation.problem1;

// https://www.hackerrank.com/challenges/simple-array-sum/problem
public class SimpleArraySum {
    public static int sum(int[] numbers) {
        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(sum(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
        System.out.println(sum(new int[] {}));
        System.out.println(sum(new int[] {0}));
        System.out.println(sum(new int[] {-1}));
        System.out.println(sum(new int[] {0, -1, 1}));
    }
}
