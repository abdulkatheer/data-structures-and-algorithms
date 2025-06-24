## Permutation

- How many ways you arrange a set of elements
- n! permutations possible

ABC
ACB
BAC
BCA
CAB
CBA


> The number of ways to **arrange** `r` elements from a set of `n` distinct elements.

### 📌 Formula:

$$
P(n, r) = \frac{n!}{(n - r)!}
$$

### 🧠 Meaning:

* You care about the **sequence/order**.
* Example: "ABC" ≠ "BAC"

### 🔸 Example:

From 4 letters (A, B, C, D), number of 2-letter permutations:

$$
P(4, 2) = \frac{4!}{(4 - 2)!} = \frac{24}{2} = 12
$$

## Combinations

- In how many ways you can pick k elements from a set of n elements

> The number of ways to **select** `r` elements from a set of `n` distinct elements.

### 📌 Formula:

$$
C(n, r) = \binom{n}{r} = \frac{n!}{r!(n - r)!}
$$

### 🧠 Meaning:

* You only care about **which elements are chosen**, not the order.
* Example: "AB" = "BA"

### 🔸 Example:

From 4 letters (A, B, C, D), number of 2-letter combinations:

$$
C(4, 2) = \frac{4!}{2!(4 - 2)!} = \frac{24}{2×2} = 6
$$

---

## 🔁 Summary Table

| Concept     | Formula                           | Order Matters? | Example   |
|-------------|-----------------------------------|----------------|-----------|
| Permutation | $P(n, r) = \frac{n!}{(n - r)!}$   | ✅ Yes          | ABC ≠ BAC |
| Combination | $C(n, r) = \frac{n!}{r!(n - r)!}$ | ❌ No           | AB = BA   |

---

## Combinations vs Subsequences vs Subsets

Combination gives a single selection out of n elements like how many ways you can pick k elements from n elements
Subsequences is all possible such combinations like (C(n,0) + C(n,1) + C(n,2) + ... + C(n,n))
Subsets is same as Subsequences, but order doesn't matter and uniqueness matters!