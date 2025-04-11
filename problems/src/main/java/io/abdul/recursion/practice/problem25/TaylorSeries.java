package io.abdul.recursion.practice.problem25;

import java.util.HashMap;

// Taylor Series e^x
public class TaylorSeries {
    public static void main(String[] args) {
        System.out.println(taylorSeries(2, 10));
        System.out.println(taylorSeriesV2(2, 10));
        System.out.println(taylorSeriesV3(2, 10));
        System.out.println(taylorSeriesV3Itr(2, 10));
    }

    /*
    Repetitive factorial and power calculations
    We gonna cache it!
     */
    private static double taylorSeries(int x, int n) {
        if (n == 0) {
            return 1;
        }
        double prevTs = taylorSeries(x, n - 1);
        double pow = powerV2(x, n);
        double fact = factorial(n);
        double tS = prevTs + pow / fact;
        System.out.println("tS for x=" + x + " n=" + n + " is " + tS + " with prevTs=" + prevTs + " pow=" + pow + " fact=" + fact);
        return tS;
    }

    private static double powerV2(int base, int power) {
        if (power == 0) {
            return 1;
        }
        if (power % 2 == 0) {
            return powerV2(base * base, power / 2);
        } else {
            return base * powerV2(base * base, power / 2);
        }
    }

    private static double factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    private static double taylorSeriesV2(int x, int n) {
        HashMap<String, Integer> state = new HashMap<>();
        state.put("power", 1);
        state.put("factorial", 1);
        return taylorSeriesCached(x, n, state);
    }

    private static double taylorSeriesCached(int x, int n, HashMap<String, Integer> state) {
        if (n == 0) {
            return 1;
        }
        double prevTs = taylorSeriesCached(x, n - 1, state);
        Integer powerSoFar = state.get("power");
        Integer factorialSoFar = state.get("factorial");
        powerSoFar = powerSoFar * x;
        factorialSoFar = factorialSoFar * n;
        state.put("power", powerSoFar);
        state.put("factorial", factorialSoFar);
        double tS = prevTs + (double) powerSoFar / factorialSoFar;
        System.out.println("tS for x=" + x + " n=" + n + " is " + tS + " with prevTs=" + prevTs + " pow=" + powerSoFar + " fact=" + factorialSoFar);
        return tS;
    }

    /*
    Horner's rule
    e^x = 1 + x/1 + x^2/2! + x^3/3! + ... + x^n/n!
    e^x = 1 + x/1 (1 + x/2 (1 + x/3 (1 + x/4 .... (1 + x/n))))
     */
    private static double taylorSeriesV3(int x, int n) {
        return taylorSeriesHornersRule(x, n, 1);
    }

    private static double taylorSeriesHornersRule(int x, int n, int i) {
        if (i > n) {
            return 1;
        }
        return 1 + (double) x / i * (taylorSeriesHornersRule(x, n, i + 1));
    }

    private static double taylorSeriesV3Itr(int x, int n) {
        double tS = 1;

        for (int i = n; i >= 1; i--) {
            tS = 1 + (double) x / i * (tS);
        }

        return tS;
    }

}
