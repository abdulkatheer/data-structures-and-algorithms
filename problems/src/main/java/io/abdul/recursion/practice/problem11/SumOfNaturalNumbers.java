package io.abdul.recursion.practice.problem11;

public class SumOfNaturalNumbers {
    public static void main(String[] args) {
        System.out.println(sumOfNaturalNumbers(5));
        System.out.println(sumOfNaturalNumbersItr(5));
    }

    /*
    T - O(n)
    S - O(n)
     */
    private static int sumOfNaturalNumbers(int n) {
        if (n <= 0) {
            return 0;
        }
        return n + sumOfNaturalNumbers(n - 1);
    }

    /*
    T - O(n)
    S - O(1)
     */
    private static int sumOfNaturalNumbersItr(int n) {
        int sum = 0;
        for (int i = n; i > 0; i--) {
            sum += i;
        }
        return sum;
    }
}
