package io.abdul.prime_number.concept01;

/* Primality test
 */
public class Solutions {

}

/*
Method: 6k +- 1

When to use this method

- Single number primality check
- n ≤ 10¹² (fits in time)
- Not for generating many primes → use Sieve
 */
class Solution {

  // T - O(sqrt(n))
  // S - O(1)
  public boolean isPrime(int num) {
    if (num < 2) { // 0,1 are not primes
      return false;
    }

    if (num <= 3) { // 2 and 3 are primes
      return true;
    }

    if (num % 2 == 0 || num % 3 == 0) { // anything divisible by prime 2 or 3 are not prime
      return false;
    }

    int sqrt = (int) Math.sqrt(num); // A number will be divisible by a prime number <= sqrt(n)
    for (int i = 5; i <= sqrt; i += 6) {
      // Prime numbers from 5 onwards are in format 6*k +- 1
      // To check divisibility we only need prime numbers
      // We don't know the prime numbers, but we eliminate the ones which are not definitely prime numbers
      if (num % (i) == 0) {
        return false;
      } else if (num % (i + 2) == 0) {
        return false;
      }
    }

    return true;
  }
}

/*
Method - Miller–Rabin
Probabilistic method, not deterministic
Determinism comes from carefully chosen bases.

When to use this method

- Single number primality check
- n > 10^12 and 10^18 (fits in time)
- for long numbers
 */
class Solution2 {

  public static void main(String[] args) {
    int[] nums = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
        79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173,
        179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271,
        277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383,
        389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491,
        499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613,
        617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733,
        739, 743, 751, 757, 761, 769, 773, 787, 797};
    Solution2 solution = new Solution2();
    for (int num : nums) {
      if (!solution.isPrime(num)) {
        System.out.println(num);
      }
    }
  }

  // T - O(log n)
  // S - O(1)
  public boolean isPrime(long num) {
    if (num < 2) {
      return false;
    }
    if (num == 2 || num == 3) {
      return true;
    }
    if (num % 2 == 0 || num % 3 == 0) {
      return false;
    }

//    int[] bases = {2, 3, 5, 7, 11, 13, 17}; // naive, works up to (~3.4 × 10¹⁴)
    long[] bases = {2, 325, 9375, 28178, 450775, 9780504,
        1795265022}; // guaranteed and proven for 64-bit numbers

    // reduce n-1 to the form 2^s * d
    long d = num - 1;
    long s = 0;
    while ((d & 1) == 0) {
      s++;
      d >>= 1; // num/2
    }

    // prime will pass bases for sure, but composite may pass 1 but not all
    for (long base : bases) {
      if (base >= num) {
        continue;
      }
      if (!millerTest(num, base, d, s)) { // fail-fast
        return false;
      }
    }

    return true; // passed all bases
  }

  private boolean millerTest(long num, long a, long d, long s) {
    long x = modPow(a, d, num); // a^d mod n

    // prime directly ends in 1 mod n, or -1 mod n (n-1) and then to 1
    if (x == 1 || x == num - 1) {
      return true;
    }

    for (int i = 1; i < s; i++) {
      x = modMultiply(x, x, num); // x^2 mod n
      if (x == num - 1) { // -1 mod n
        return true;
      }
    }

    return false;
  }

  /*
  10^13
  exponent = 13
  base = 10
  result = 1

  result = 10
  base = 100
  exponent = 6

  result = 10
  base = 10000
  exponent = 3

  result = 100000
  base = 100000000
  exponent = 1

  result = 100000_0000_0000
  exponent = 0
   */
  private long modPow(long base, long exponent, long mod) {
    long result = 1;
    base %= mod;

    while (exponent != 0) {
      if ((exponent & 1) == 1) {
        // Odd
        result = modMultiply(result, base, mod);
      }

      base = modMultiply(base, base, mod);
      exponent >>= 1; // exponent / 2
    }

    return result;
  }

  /*
  10 * 13

  r = 0
  a = 10
  b = 13

  r = 10
  a = 10
  b = 12

  r = 10
  a = 10 * 2 = 20
  b = 6

  r = 10
  a = 20 * 2 = 40
  b = 3

  r = 50
  a = 40
  b = 2

  r = 50
  a = 40 * 2 = 80
  b = 1

  r = 50 + 80 = 130
  a = 80
  b = 0
   */
  private long modMultiply(long a, long b, long mod) {
    long result = 0;
    a %= mod;

    while (b != 0) {
      if ((b & 1) == 1) {
        result = (result + a) % mod; // result + a
      }
      a = (a << 1) % mod; // a * 2
      b >>= 1; // b/2
    }

    return result;
  }
}