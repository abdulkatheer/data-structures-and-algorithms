package io.abdul.gcd_lcm.concept02;

import java.util.Arrays;

// Finding coefficients x and y from ax + by = gcd(a,b)
public class Solutions {

  public static void main(String[] args) {
    Solution solution = new Solution();

    System.out.println(Arrays.toString(solution.extendedGcd(56, 15)));
  }
}

/*
Extended Euclidean Algorithm - Recursive

 */
class Solution {

  int[] extendedGcd(int a, int b) {
    if (a > b) {
      return extendedGcdRec(a, b);
    } else if (b > a) {
      return extendedGcdRec(b, a);
    } else { // same
      return new int[]{a, 1, 0};
    }
  }

  int[] extendedGcdRec(int a, int b) {
    if (b == 0) {
      return new int[]{a, 1, 0};
    }

    int[] result = extendedGcdRec(b, a % b);
    int gcd = result[0];
    int x1 = result[1];
    int y1 = result[2];

    // reversal of b, a%b
    // b as a -> y as x
    // a as a%b -> x as a/b * y
    int x = y1;
    int y = x1 - (a / b) * y1;

    int[] res = {gcd, x, y};
    System.out.printf("a=%d b=%d result=%s%n", a, b, Arrays.toString(res));

    return res;
  }
}


/*
Solve ax = 1 mod m

Using Extended Euclidean Algorithm

a*x = 1 mod m

Inverse exists only if gcd(a,m) = 1

a*x = k*m + 1
a*x - k*m = 1
ax + by = 1

x is a^-1
 */
class ModularInverse {

  public int solve(int a, int m) {
    Solution solution = new Solution();
    int[] res = solution.extendedGcd(a, m);

    if (res[0] != 1) {
      throw new IllegalArgumentException("Modular Inverse exists for gcd(" + a + "," + m + ")");
    }

    return ((res[1] % m) + m) % m;
  }
}

/*
Solve ax = b mod m

Reduce b to 1. To do that, we need to find the GCD and divide all by gcd

(a/gcd)x = (b/gcd) mod (m/gcd)

For this b%m == 0

a`x = 1 mod m`
a` = 1/x mod m`
Modulo division can't be done
a` = a`^-1 mod m`

 */
class LinearCongruence {

  public int solve(int a, int b, int m) {
    Solution solution = new Solution();
    int gcd = solution.extendedGcdRec(a, m)[0];// we only need GCD

    if (b % gcd != 0) {
      throw new IllegalArgumentException("Can't solve this");
    }

    a /= gcd;
    b /= gcd;
    m /= gcd;

    ModularInverse inverse = new ModularInverse();
    int aInverse = inverse.solve(a, m);

    return (int) (((long) aInverse * b) % m);

  }
}

/*
Solve (a/b) mod m = x
 */
class ModularDivision {

  public int solve(int a, int b, int m) {
    ModularInverse inverse = new ModularInverse();
    int bInverse = inverse.solve(b, m);

    return (int) (((long) a * bInverse) % m);
  }
}

/*
Solve d = e^-1 mod (pi(n))
 */
class RsaKeyGeneration {

  public int solve(int e, int pi) {
    ModularInverse inverse = new ModularInverse();
    return inverse.solve(e, pi);
  }
}