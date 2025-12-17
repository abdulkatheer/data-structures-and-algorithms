# Modular Arithmetic — Complete Notes (From Scratch)

These notes are written **from first principles**, with **step-by-step logic**, and are suitable
for:

* DSA preparation
* Competitive programming
* Interviews
* Personal revision

---

## 1. What is Modulo?

For integers `a` and positive integer `m`:

**Definition**

```
a mod m = remainder when a is divided by m
```

**Examples**

```
7 mod 5 = 2
12 mod 4 = 0
25 mod 7 = 4
```

**Key intuition**
Modulo arithmetic works like a **cycle**. Values wrap around after `m`.

---

## 2. Congruence Modulo m

Two numbers `a` and `b` are congruent modulo `m` if:

```
a ≡ b (mod m)
```

This means:

```
a − b is divisible by m
```

**Example**

```
17 ≡ 5 (mod 12)
```

---

## 3. Fundamental Modulo Properties

For any integers `a`, `b`, and positive `m`:

```
(a + b) mod m = ((a mod m) + (b mod m)) mod m
(a − b) mod m = ((a mod m) − (b mod m) + m) mod m
(a × b) mod m = ((a mod m) × (b mod m)) mod m
```

⚠️ Always reduce intermediate values to avoid overflow.

---

## 4. Modulo Addition

**Rule**

```
(a + b) mod m = ((a mod m) + (b mod m)) mod m
```

**Example**

```
(27 + 19) mod 10
= (7 + 9) mod 10
= 16 mod 10
= 6
```

---

## 5. Modulo Subtraction

Subtraction can produce negative numbers, so correction is required.

**Safe Formula**

```
(a − b) mod m = ((a mod m) − (b mod m) + m) mod m
```

**Example**

```
(5 − 9) mod 7
= (-4) mod 7
= 3
```

---

## 6. Modulo Multiplication

**Rule**

```
(a × b) mod m = ((a mod m) × (b mod m)) mod m
```

**Example**

```
(123 × 456) mod 7
= (4 × 1) mod 7
= 4
```

---

## 7. Overflow-Safe Modular Multiplication

When `a × b` overflows 64-bit, use **binary multiplication**.

**Idea**
Multiply using repeated doubling and addition.

**Algorithm**

```
result = 0
while b > 0:
    if b is odd:
        result = (result + a) mod m
    a = (a × 2) mod m
    b = b / 2
```

---

## 8. Modulo Exponentiation

Problem:

```
(a^b) mod m
```

Direct computation is infeasible for large `b`.

---

## 9. Binary (Fast) Exponentiation

**Key identity**

```
a^b =
    a × a^(b−1)    if b is odd
    (a^(b/2))^2    if b is even
```

**Algorithm**

```
result = 1
while b > 0:
    if b is odd:
        result = (result × a) mod m
    a = (a × a) mod m
    b = b / 2
```

**Time Complexity**

```
O(log b)
```

---

## 10. Modular Inverse

### Definition

The modular inverse of `a` modulo `m` is a number `x` such that:

```
(a × x) mod m = 1
```

---

## 11. Existence of Modular Inverse

A modular inverse exists **if and only if**:

```
gcd(a, m) = 1
```

If this condition fails, inverse does **not exist**.

---

## 12. Modulo Division

Division is not directly allowed in modulo arithmetic.

**Correct approach**

```
(a / b) mod m = (a × b⁻¹) mod m
```

Where `b⁻¹` is the modular inverse of `b`.

---

## 13. Modular Inverse Using Fermat’s Little Theorem

Applicable **only when `m` is prime**.

**Theorem**

```
a^(m−1) ≡ 1 (mod m)
```

Therefore:

```
a⁻¹ ≡ a^(m−2) mod m
```

**Example**

```
3⁻¹ mod 7 = 3^5 mod 7 = 5
```

---

## 14. Modular Inverse Using Extended Euclidean Algorithm

### Bézout’s Identity

If `gcd(a, m) = 1`, then:

```
ax + my = 1
```

Taking modulo `m`:

```
ax ≡ 1 (mod m)
```

So:

```
x = a⁻¹ mod m
```

---

## 15. Extended Euclid – Step-by-Step Example

Find:

```
10⁻¹ mod 17
```

Euclid steps:

```
17 = 10×1 + 7
10 = 7×1 + 3
7 = 3×2 + 1
```

Back substitution:

```
1 = 17×3 − 10×5
```

Inverse:

```
10⁻¹ mod 17 = -5 mod 17 = 12
```

---

## 16. Fermat vs Extended Euclid

| Aspect     | Fermat                  | Extended Euclid |
|------------|-------------------------|-----------------|
| Modulus    | Prime only              | Any             |
| Complexity | O(log m)                | O(log m)        |
| Use case   | Competitive programming | General math    |

---

## 17. Common Mistakes

* Doing division directly in modulo
* Forgetting `+ m` in subtraction
* Using Fermat for non-prime modulus
* Not checking gcd before inverse
* Overflow before applying modulo

---

## 18. Where Modulo Arithmetic Is Used

* Competitive programming
* Hashing algorithms
* Cryptography
* `nCr` problems
* Rolling hash
* Modular DP

---

## 19. Final Summary

```
Reduce early.
Reduce often.
Division = inverse × multiplication.
Prime modulus → Fermat.
Otherwise → Extended Euclid.
```

---

## 20. Practice Problems (With Worked Solutions)

These problems are ordered from basic to interview-level. Try solving first, then verify with
solutions.

---

### Problem 1: Basic Addition

Compute:

```
(345 + 678) mod 10
```

**Solution**

```
345 mod 10 = 5
678 mod 10 = 8
(5 + 8) mod 10 = 13 mod 10 = 3
```

---

### Problem 2: Safe Subtraction

Compute:

```
(25 − 40) mod 7
```

**Solution**

```
25 mod 7 = 4
40 mod 7 = 5
(4 − 5 + 7) mod 7 = 6
```

---

### Problem 3: Large Multiplication

Compute:

```
(10^9 × 10^9) mod (10^9 + 7)
```

**Solution**

```
Reduce operands first, then multiply
((10^9 mod M) × (10^9 mod M)) mod M
```

Handled safely without overflow.

---

### Problem 4: Modular Exponentiation

Compute:

```
(2^50) mod 13
```

**Solution (binary exponentiation)**

```
2^12 mod 13 = 1
2^48 mod 13 = 1
2^50 mod 13 = 2^2 mod 13 = 4
```

---

### Problem 5: Modular Inverse (Prime Modulus)

Find:

```
5⁻¹ mod 13
```

**Solution**

```
5^(13−2) mod 13 = 5^11 mod 13 = 8
```

---

### Problem 6: Modular Inverse (Non‑Prime Modulus)

Find:

```
7⁻¹ mod 26
```

**Solution (Extended Euclid)**

```
26 = 7×3 + 5
7 = 5×1 + 2
5 = 2×2 + 1
```

Back substitute:

```
1 = 26×3 − 7×11
```

```
7⁻¹ mod 26 = -11 mod 26 = 15
```

---

### Problem 7: Modulo Division

Compute:

```
(14 / 3) mod 11
```

**Solution**

```
3⁻¹ mod 11 = 4
14 × 4 mod 11 = 1
```

---

## 21. Common Modulo Tricks in Competitive Programming

These are patterns used repeatedly in contests and interviews.

---

### Trick 1: Mod Early, Mod Often

❌ Wrong

```
(a × b × c) mod m
```

✅ Correct

```
(((a mod m) × (b mod m)) mod m × (c mod m)) mod m
```

Prevents overflow and keeps values small.

---

### Trick 2: Handling Negative Values

Always normalize:

```
(x mod m + m) mod m
```

Used after subtraction or Extended Euclid.

---

### Trick 3: Division = Inverse × Multiplication

Never write:

```
a / b mod m
```

Always write:

```
a × inverse(b) mod m
```

---

### Trick 4: Fermat Shortcut for Inverse

If modulus is prime:

```
inverse(a) = pow(a, m−2, m)
```

Works in O(log m).

---

### Trick 5: Cyclic Powers

Many problems rely on:

```
a^k mod m repeats with cycle length (m−1)
```

(when `m` is prime and `a` not divisible by `m`).

---

### Trick 6: nCr Modulo (Preview)

Formula:

```
nCr mod m = n! × inv(r!) × inv((n−r)!) mod m
```

Requires precomputed factorials and inverses.

---

### Trick 7: Power of Two Optimization

Use bit shifts where possible:

```
2^k mod m → fast exponentiation
```

---

### Trick 8: Modulo in DP

Always store DP values as:

```
dp[i] = dp[i] mod m
```

Prevents overflow and keeps DP valid.

---

### Trick 9: Overflow‑Safe Multiplication

When constraints exceed 64‑bit:

```
Use binary multiplication
```

---

### Trick 10: Modulo Is Not Distributive Over Division

This is false:

```
(a / b) mod m ≠ (a mod m) / (b mod m)
```

Always use inverse.

---

## 22. Why a^(m−1) ≡ 1 (mod m) Implies a⁻¹ ≡ a^(m−2) (Step-by-Step Proof)

This section explains **exactly** how Fermat’s Little Theorem leads to the modular inverse formula.
No steps are skipped.

---

### Preconditions (Must Hold)

This derivation is valid **only if**:

```
1) m is prime
2) gcd(a, m) = 1
```

If these are not true, the modular inverse may not exist.

---

### Step 1: Fermat’s Little Theorem

For prime `m`:

```
a^(m−1) ≡ 1 (mod m)
```

This means:

```
a^(m−1) = 1 + k·m   (for some integer k)
```

---

### Step 2: Split the Exponent

Rewrite the power:

```
a^(m−1) = a × a^(m−2)
```

Substitute into Fermat’s result:

```
a × a^(m−2) ≡ 1 (mod m)
```

---

### Step 3: Compare with the Definition of Modular Inverse

By definition, the modular inverse `a⁻¹` satisfies:

```
a × a⁻¹ ≡ 1 (mod m)
```

Now compare the two equations:

```
a × a^(m−2) ≡ 1 (mod m)
a × a⁻¹     ≡ 1 (mod m)
```

The value multiplying `a` must be the same modulo `m`.

---

### Step 4: Conclude the Result

Therefore:

```
a^(m−2) ≡ a⁻¹ (mod m)
```

This is the modular inverse formula used in competitive programming.

---

### Step 5: Why This Division Is Legal

Normally, division is **not allowed** in modular arithmetic.

Here it works because:

```
gcd(a, m) = 1  ⇒  a has a modular inverse
```

So multiplying both sides by `a⁻¹` is valid.

---

### Step 6: Concrete Example

Let:

```
a = 3, m = 7
```

Fermat:

```
3^6 ≡ 1 (mod 7)
```

Split:

```
3 × 3^5 ≡ 1 (mod 7)
```

Compute:

```
3^5 = 243
243 mod 7 = 5
```

Check:

```
3 × 5 = 15 ≡ 1 (mod 7)
```

So:

```
3⁻¹ mod 7 = 5
```

---

### Step 7: Why This Fails When m Is Not Prime

Example:

```
a = 2, m = 4
```

```
2^(4−1) = 8 ≡ 0 (mod 4)
```

Here:

```
gcd(2,4) ≠ 1
```

So no modular inverse exists, and the derivation breaks.

---

### Final Takeaway

```
Fermat gives: a^(m−1) ≡ 1
Split one a → remaining factor must be the inverse

Therefore:

a⁻¹ ≡ a^(m−2) (mod m)
```

---

## 23. Formal Proof of Fermat’s Little Theorem

### Statement

If `m` is a prime and `gcd(a, m) = 1`, then:

```
a^(m−1) ≡ 1 (mod m)
```

---

### Proof (Using Modular Multiplicative Set)

Consider the set:

```
S = {1, 2, 3, ..., m−1}
```

Since `m` is prime:

* None of these elements are divisible by `m`
* Each element has a modular inverse modulo `m`

Now multiply every element of `S` by `a` modulo `m`:

```
aS = {a×1, a×2, a×3, ..., a×(m−1)} mod m
```

---

### Key Observations

1. All elements of `aS` are **distinct modulo m**

    * If `a×x ≡ a×y (mod m)`
    * Multiply both sides by `a⁻¹`
    * Then `x ≡ y (mod m)`

2. No element of `aS` is congruent to `0 (mod m)`

    * Because `gcd(a, m) = 1`

So `aS` is just a **reordering** of `S`.

---

### Multiply All Elements

Product of `S`:

```
P = 1 × 2 × 3 × ... × (m−1)
```

Product of `aS`:

```
P' = (a×1)(a×2)...(a×(m−1))
   = a^(m−1) × P
```

Since `aS` is a permutation of `S`:

```
P' ≡ P (mod m)
```

---

### Cancel the Common Factor

Since none of `1..(m−1)` are divisible by `m`, `P` has an inverse modulo `m`.

So we can cancel `P`:

```
a^(m−1) ≡ 1 (mod m)
```

✔ Proof complete.

---

## 24. One-Page Modulo Cheat Sheet

### Core Rules

```
(a + b) mod m = ((a mod m) + (b mod m)) mod m
(a − b) mod m = ((a mod m) − (b mod m) + m) mod m
(a × b) mod m = ((a mod m) × (b mod m)) mod m
```

---

### Exponentiation

```
(a^b) mod m → Binary Exponentiation (O(log b))
```

---

### Modular Inverse

Exists iff:

```
gcd(a, m) = 1
```

Prime modulus:

```
a⁻¹ = a^(m−2) mod m
```

General case:

```
Use Extended Euclid
```

---

### Division

```
a / b mod m = a × b⁻¹ mod m
```

---

### Safety Rules

```
Mod early
Mod often
Normalize negatives: (x%m + m) % m
Never divide directly
```

---

## 25. Interview-Quality Practice Problems

### Easy–Medium

1. Compute `(123456 + 654321) mod 100`
2. Compute `(1000000 − 1234567) mod 13`
3. Find `(999 × 888) mod 37`
4. Compute `(2^30) mod 17`
5. Find `3⁻¹ mod 11`
6. Find `7⁻¹ mod 15` (state if impossible)
7. Compute `(25 / 4) mod 13`
8. Check if inverse of `12 mod 18` exists
9. Compute `(5^100) mod 4`
10. Find last digit of `7^222`

---

### Medium

11. Compute `(a^b) mod m` where `a=10^9+9, b=10^18, m=10^9+7`
12. Find inverse of `123456 mod 10^9+7`
13. Compute `(n!) mod p` for large `n` and prime `p`
14. Find `(2^n − 1) mod 10^9+7`
15. Evaluate `(a / b) mod m` when `m` is non-prime
16. Find `(x + y) mod m` when `x, y` are negative
17. Compute `(3^(4^5)) mod 7`
18. Determine cycle length of `a^k mod m`
19. Compute `(a × b) mod m` where `a, b ≤ 10^18`
20. Check if `(a / b) mod m` is valid

---

### Hard / Interview Deep-Dive

21. Compute `nCr mod p` where `p` is prime
22. Compute `nCr mod m` where `m` is not prime
23. Find modular inverse for multiple queries efficiently
24. Evaluate product of range modulo prime
25. Solve modular linear equation `ax ≡ b (mod m)`
26. Find smallest `k` such that `a^k ≡ 1 (mod m)`
27. Compute modular inverse factorials
28. Find power tower modulo m
29. Handle modulo in DP with large states
30. Detect overflow-safe multiplication necessity

---

## 26. Problem → Technique Mapping Table

Use this table to **instantly recognize** which modulo technique a problem requires.

| Problem Pattern            | Typical Clues             | Technique to Use              |
|----------------------------|---------------------------|-------------------------------|
| Large power `a^b mod m`    | `b` up to 10^18           | Binary exponentiation         |
| Division in modulo         | `/` with mod              | Modular inverse               |
| Modulus is prime           | `m = 10^9+7`, `998244353` | Fermat’s Little Theorem       |
| Modulus not prime          | Arbitrary `m`             | Extended Euclidean Algorithm  |
| `a × b` with `a,b ≤ 10^18` | Overflow risk             | Binary modular multiplication |
| Repeated inverse queries   | Many test cases           | Precompute inverses           |
| `nCr mod p`                | Prime modulus             | Factorial + inverse factorial |
| `nCr mod m` (non-prime)    | Arbitrary modulus         | CRT / Prime factorization     |
| Negative values appear     | Subtraction / equations   | Normalize `(x%m + m)%m`       |
| Equation `ax ≡ b (mod m)`  | Linear congruence         | Extended Euclid               |
| Exponent like `a^(b^c)`    | Power tower               | Cycle / Fermat reduction      |
| DP with large values       | Counting ways             | Mod DP at every step          |

---

## 27. Final 2-Page Printable PDF Layout (Suggested Structure)

This section helps you **export these notes cleanly** into a concise 2-page PDF.

---

### Page 1: Core Theory & Formulas

**Top Section: Basics**

```
Definition of modulo
Congruence
```

**Middle Section: Arithmetic Rules**

```
Addition
Subtraction (+m trick)
Multiplication
```

**Bottom Section: Exponentiation**

```
Binary exponentiation algorithm
Time complexity O(log b)
```

---

### Page 2: Inverses, Proofs & Quick Reference

**Top Section: Modular Inverse**

```
Existence condition
Fermat (prime m)
Extended Euclid (general)
```

**Middle Section: Key Proof Insight**

```
a^(m−1) ≡ 1 ⇒ a^(m−2) ≡ a⁻¹
```

**Bottom Section: Cheat Sheet**

```
Division rule
Safety rules
Common pitfalls
```

This layout fits well for **last-day interview revision**.

---

## 28. DSA Interview Checklist for Modulo Problems

Use this checklist **before coding** any modulo problem.

---

### Step 1: Identify the Modulus

* Is `m` prime?
* Is it fixed (`10^9+7`) or variable?

---

### Step 2: Look for Division

* Is there `/` or a fraction?
* If yes → inverse required
* Check `gcd(b, m)`

---

### Step 3: Check Constraints

* Are numbers ≥ 10^18?
* Use binary multiplication if needed

---

### Step 4: Exponents

* Large power? → binary exponentiation
* Power tower? → reduce exponent using cycles

---

### Step 5: Negatives & Subtraction

* Always normalize:

```
(x % m + m) % m
```

---

### Step 6: Multiple Queries?

* Precompute factorials / inverses
* Avoid repeated expensive operations

---

### Step 7: Common Traps to Avoid

* Direct division
* Overflow before modulo
* Assuming inverse always exists
* Using Fermat on non-prime modulus

---

### Final Interview Rule

> If division appears, pause and reason.
> If powers are large, reduce aggressively.
> If unsure, normalize.

---

End of Notes.
