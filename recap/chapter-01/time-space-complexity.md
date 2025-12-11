# Time Complexity (non-recursive)

## Complexity list (in increasing growth)

**O(1) < O(log n) < O(n) < O(n log n) < O(n²) < O(n³) < O(2ⁿ) < O(3ⁿ) < O(n!)**

---

## **O(1) — Constant Time**

### **Definition**

Work does **not depend on input size**. Same time for n = 10 or n = 1,000,000.

### **When it happens**

* Direct access (array index, map get/put average)
* Constant number of operations

### **Example**

```java

int f(int[] a) {
  return a[0];

}
```

---

## **O(log n) — Logarithmic**

### **Definition**

Input size **shrinks by a constant factor** (usually /2) every step.

### **When it happens**

* Binary search
* Balanced tree operations
* `i = i/2`, `i *= 2` loops

### **Example**

```java
while(n >1)n /=2;
```

---

## **O(n) — Linear**

### **Definition**

Time grows **directly proportional** to n.

### **When it happens**

* Single loop from 1 to n
* Scanning arrays/lists

### **Example**

```java
for(int i = 0;
i<n;i++)sum +=a[i];
```

---

## **O(n log n) — Linearithmic**

### **Definition**

Perform **n operations**, each costing **log n**.

### **When it happens**

* Efficient sorting (merge sort, quicksort avg)
* Loop + binary search

### **Example**

```java
for(int i = 0;
i<n;i++)

binarySearch(a, a[i]);
```

---

## **O(n²) — Quadratic**

### **Definition**

Two nested loops over n elements → **every element compared with every other**.

### **When it happens**

* All pairs
* Building n×n structures
* Nested loops

### **Example**

```java
for(int i = 0;
i<n;i++)
    for(
int j = 0;
j<n;j++)

doWork();
```

---

## **O(n³) — Cubic**

### **Definition**

Three nested loops → **n × n × n** operations.

### **When it happens**

* Triple loops
* Naive matrix multiplication
* All triplets combinations

### **Example**

```java
for(int i = 0;
i<n;i++)
    for(
int j = 0;
j<n;j++)
    for(
int k = 0;
k<n;k++)

doWork();
```

---

## **O(2ⁿ) — Exponential (Base 2)**

### **Definition**

Time doubles for every increment in n.
Usually: iterate **all subsets**. Exact opposite to log_2 n, where time halves every iteration.

### **When it happens**

* Subset generation
* Include/exclude binary choices

### **Example**

```java
for(int mask = 0; mask < (1<<n);mask++)

doWork();   // runs 2^n times
```

---

## **O(3ⁿ) — Exponential (Base 3)**

### **Definition**

Time triples for every increment in n. Exact opposite to log_3 n, where time reduces by 3 every
iteration.
Each element has **3 choices**, total combinations = 3ⁿ.

### **When it happens**

* Ternary state problems
* Try all arrangements with 3 states/item

### **Example**

```java
for(int x = 0; x <Math.

pow(3,n);

x++)

doWork();
```

---

## **O(n!) — Factorial**

### **Definition**

Time grows based on **permutations of n elements**.
Largest common complexity in DSA.

### **When it happens**

* Permutation generation
* Brute-force TSP
* Try all orderings

### **Example**

```java
List<int[]> perms = generatePermutations(n); // n! perms
for(
int[] p :perms)

doWork();
```

---

## Step-by-step approach to identify complexity (for **normal** functions)

1. **Find the loops**

    * Single loop over `n` → O(n).
    * Loop over constant bound (e.g., 100) → O(1).

2. **Nested loops multiply**

    * If an outer loop runs `n` and inner runs `n` → O(n) × O(n) = O(n²).
    * If inner bound depends on outer (`for i.. for j<i`) → sum of 0..n → O(n²) (still quadratic).

3. **Sequential parts add; take largest**

    * `O(n) + O(n²) + O(log n)` → overall **O(n²)**.

4. **Check loop bounds carefully**

    * `for (i=0;i<n;i+=2)` → ~n/2 steps → **O(n)** (constants dropped).
    * `for (i=1;i<n;i*=2)` → O(log n).

5. **Look for halving/doubling inside loops**

    * `while (x>0) x/=2;` → O(log n).
    * If such a `log` loop is nested inside an `n` loop → O(n log n).

6. **Detect iteration over combinatorial ranges**

    * `for mask in 0..(1<<n)-1` → O(2ⁿ).
    * `for code in 0..3ⁿ-1` → O(3ⁿ).
    * Iterating permutations/arrangements → O(n!).

7. **Function calls and helper loops**

    * Replace the call by its complexity. E.g., `doSort(a)` inside a loop → multiply by sort’s
      complexity.

8. **Ignore constants and lower-order terms**

    * Drop multipliers and addends: `O(2n + 100) → O(n)`, `O(n² + n) → O(n²)`.

9. **Amortized vs worst-case**

    * Note amortized O(1) for push on dynamic array; worst-case may be O(n) on re-alloc.

---

## Quick checklist / shortcuts

* Single loop → **O(n)**
* Nested loops with independent ranges → multiply → **O(n^k)**
* Loop that halves/doubles → **O(log n)**
* Loop inside loop where inner is log → **O(n log n)**
* Loop over bitmasks 0..2ⁿ−1 → **O(2ⁿ)**
* Iterating permutations → **O(n!)**

---

## Simplification rules (formal)

* Drop constants: `c·f(n)` → `f(n)`
* Keep dominating term: `f(n) + g(n)` → `max(f,g)` asymptotically
* Multiplication rule for nested independent loops: `O(f) * O(g)`
* If loop bound is function of n (like `n/2`, `n−k`) treat as `n`.

---

# **How to Identify Complexity Quickly**

| Pattern                 | Complexity     |
|-------------------------|----------------|
| Single loop             | **O(n)**       |
| Loop cuts input in half | **O(log n)**   |
| Loop × Log-loop         | **O(n log n)** |
| k nested loops          | **O(nᵏ)**      |
| Loop over 0..2ⁿ−1       | **O(2ⁿ)**      |
| Loop over permutations  | **O(n!)**      |
| Constant operations     | **O(1)**       |

---

Got it.
Let’s **completely reset** and produce **clean, polished Markdown notes** — no PDF, no
auto-generated images — and **use your original Master’s Theorem images exactly as-is**, referenced
in the notes.

Below is the final, high-quality, readable Markdown document you can paste into
Notion/Obsidian/GitHub/Google Docs.

---

# 📘 **Recursive Time Complexity Analysis — Complete Notes (Markdown Version)**

This guide covers:

* Recursion Tree Method (in depth)
* Master’s Theorem (using **your two images only**)
* When to choose which technique
* Most-common recurrence relations

---

# ------------------------------------------------------------

# 🧩 **1. Recursion Tree Method — Detailed Notes**

# ------------------------------------------------------------

The recursion tree method visualizes how work grows or shrinks across recursive calls.
It is the most flexible technique and works even when Master’s Theorem fails.

---

## 🔹 **Steps to Analyze Using Recursion Tree**

### **1️⃣ Expand the recurrence**

Replace each call with its children repeatedly.

Example:

```
T(n) = 2T(n/2) + n
```

Becomes:

```
           n
       /       \
     n/2       n/2
    /   \     /   \
  n/4  n/4  n/4  n/4
```

---

### **2️⃣ Compute work per level**

Example:

```
Level 0: n
Level 1: n/2 + n/2 = n
Level 2: n/4 + n/4 + n/4 + n/4 = n
...
```

---

### **3️⃣ Determine the number of levels**

* If each call is `T(n/b)` → height = `log_b(n)`
* If each call is `T(n - 1)` → height = `n`
* If uneven splits → height determined by slowest shrinking branch

---

### **4️⃣ Sum all levels**

* If each level ≈ same cost → multiply
* If levels shrink → geometric series (dominant root level)
* If levels grow → leaf dominated
* If uneven → must track separately

---

## 🔹 **Recursion Tree Patterns You Must Know**

### **Pattern A — Equal Work Per Level**

```
T(n) = 2T(n/2) + n → Θ(n log n)
```

### **Pattern B — Shrinking Work (Geometric)**

```
T(n) = T(n/2) + n → Θ(n)
```

### **Pattern C — Growing Work (Leaf Heavy)**

```
T(n) = 2T(n/2) + 1 → Θ(n)
```

### **Pattern D — Linear Decrease**

```
T(n) = T(n−1) + n → Θ(n²)
```

### **Pattern E — Uneven Splits (Master’s fails)**

```
T(n) = T(n/2) + T(n/3) + n → Θ(n)
```

### **Pattern F — Exponential Branching**

```
T(n) = T(n−1) + T(n−2) → Θ(2ⁿ)
```

### **Pattern G — Multiplicative Recursion**

```
T(n) = n T(n/2) + n → n^(Θ(log n))
```

---

# ------------------------------------------------------------

# 🧮 **2. Master’s Theorem — Using Your Images EXACTLY**

# ------------------------------------------------------------

### 📌 **Use these images in your notes:**

## **Master’s Theorem (Decreasing Functions)**

![](Masters%20theorem%20decreasing%20function.png)

## **Master’s Theorem (Dividing Functions)**

![](Masters%20theorem%20dividing%20function.png)

---

### ✔️ Your Decreasing Master Theorem Covers:

```
T(n) = a T(n − b) + n^k
```

Cases:

* a < 1 → Θ(n^k)
* a = 1 → Θ(n^(k+1))
* a > 1 → Θ(n^k * a^(n/b))

---

### ✔️ Your Dividing Master Theorem Covers:

```
T(n) = a T(n/b) + n^k (log n)^p
```

Compare:

```
log_b(a) vs k
```

Three cases:

* log_b(a) > k → n^(log_b(a))
* log_b(a) = k → n^k log n (or variants)
* log_b(a) < k → n^k (log n)^p

---

# ------------------------------------------------------------

# 🎯 **3. When to Use Recursion Tree vs. Master’s**

# ------------------------------------------------------------

## ✔️ **Use Master’s Theorem (Dividing) when:**

```
T(n) = aT(n/b) + n^k(log n)^p
```

* `a` and `b` are constants
* Subproblems are **equal sized**
* No uneven splits

Examples:

```
2T(n/2)+n
4T(n/2)+n
3T(n/3)+n log n
```

---

## ✔️ **Use Master’s Theorem (Decreasing) when:**

```
T(n) = aT(n−b) + n^k
```

Examples:

```
T(n) = T(n−1) + n
T(n) = 3T(n−1) + 1
```

---

## ✔️ **Use Recursion Tree when:**

* Uneven subproblem sizes

  ```
  T(n) = T(n/2) + T(n/3) + n
  ```
* Mixed recursion

  ```
  T(n) = T(n−1) + T(n/2)
  ```
* Recursive calls inside loops

  ```
  T(n) = nT(n/2) + n
  ```
* Master’s theorem does NOT apply
* Exponential recurrences

  ```
  T(n) = T(n−1) + T(n−2)
  ```

---

# ------------------------------------------------------------

# 📊 **4. Most Common Recurrences + Solutions**

# ------------------------------------------------------------

```
1.  T(n) = 2T(n/2) + n            → Θ(n log n)
2.  T(n) = T(n/2) + n             → Θ(n)
3.  T(n) = T(n−1) + n             → Θ(n²)
4.  T(n) = T(n−1) + log n         → Θ(n log n)
5.  T(n) = 2T(n/2) + 1            → Θ(n)
6.  T(n) = 4T(n/2) + n            → Θ(n²)
7.  T(n) = 2T(n/2) + n²           → Θ(n²)
8.  T(n) = T(n/2) + T(n/3) + n    → Θ(n)
9.  T(n) = T(n−1) + T(n/2)        → Exponential
10. T(n) = nT(n/2) + n            → n^(Θ(log n))
11. T(n) = 3T(n−1) + 1            → Θ(3ⁿ)
12. T(n) = T(n/2) + 1             → Θ(log n)
```

---

# ------------------------------------------------------------

# 🧾 **5. Summary Cheat Sheet**

# ------------------------------------------------------------

## ✔ Recursion Tree Works For:

* Uneven splits
* Mixed recursion
* Strange forms
* Anything that Master can't handle

---

## ✔ Master’s (Dividing) Works For:

```
T(n) = aT(n/b) + n^k (log n)^p
```

Compare:

```
log_b(a) vs k
```

---

## ✔ Master’s (Decreasing) Works For:

```
T(n) = aT(n−b) + n^k
```

Three simple cases:

```
a < 1   → n^k
a = 1   → n^(k+1)
a > 1   → n^k * a^(n/b)
```

---

Nice — here are concise, correct answers for **Dividing** practice problems **1–10**. For each I
show:

* `a`, `b`, `log_b(a)`
* `f(n)` as `n^k (log n)^p` → `k` and `p`
* Which **case** (1 / 2 / 3) applies (using your image-style rule: compare `log_b(a)` vs `k`)
* Final complexity `Θ(...)` and one-line reason

---

### 1) `T(n) = 2 T(n/2) + n`

* `a=2, b=2` → `log_b(a)=log_2(2)=1`
* `f(n)=n` → `k=1, p=0`
* **Case:** `k = log_b(a)` → **Case 2**
* **Answer:** `Θ(n log n)` — balanced levels.

---

### 2) `T(n) = 4 T(n/2) + n`

* `a=4, b=2` → `log_b(a)=log_2(4)=2`
* `f(n)=n` → `k=1`
* **Case:** `k < log_b(a)` → **Case 1**
* **Answer:** `Θ(n^2)` — leaves dominate.

---

### 3) `T(n) = 3 T(n/3) + n^2`

* `a=3, b=3` → `log_b(a)=log_3(3)=1`
* `f(n)=n^2` → `k=2`
* **Case:** `k > log_b(a)` → **Case 3**
* **Answer:** `Θ(n^2)` — root/top work dominates.

---

### 4) `T(n) = 9 T(n/3) + n^2`

* `a=9, b=3` → `log_b(a)=log_3(9)=2`
* `f(n)=n^2` → `k=2, p=0`
* **Case:** `k = log_b(a)` → **Case 2**
* **Answer:** `Θ(n^2 log n)` — balanced (extra `log n`).

---

### 5) `T(n) = 2 T(n/4) + n`

* `a=2, b=4` → `log_b(a)=log_4(2)=1/2`
* `f(n)=n` → `k=1`
* **Case:** `k > log_b(a)` → **Case 3**
* **Answer:** `Θ(n)` — root/top work `n` dominates the smaller leaf-term (so `Θ(n^k)` with `k=1`).

---

### 6) `T(n) = 8 T(n/2) + n^3`

* `a=8, b=2` → `log_b(a)=log_2(8)=3`
* `f(n)=n^3` → `k=3`
* **Case:** `k = log_b(a)` → **Case 2**
* **Answer:** `Θ(n^3 log n)` — balanced.

---

### 7) `T(n) = 27 T(n/3) + n^2 log n`

* `a=27, b=3` → `log_b(a)=log_3(27)=3`
* `f(n)=n^2 log n` → `k=2, p=1`
* **Case:** `k < log_b(a)` → **Case 1**
* **Answer:** `Θ(n^3)` — leaves dominate (`n^{log_b a} = n^3`).

---

### 8) `T(n) = 16 T(n/4) + n^2`

* `a=16, b=4` → `log_b(a)=log_4(16)=2`
* `f(n)=n^2` → `k=2`
* **Case:** `k = log_b(a)` → **Case 2**
* **Answer:** `Θ(n^2 log n)` — balanced.

---

### 9) `T(n) = 3 T(n/2) + n log n`

* `a=3, b=2` → `log_b(a)=log_2(3) ≈ 1.585`
* `f(n)=n log n` → behaves like `n^1 (log n)^1` → `k=1, p=1`
* **Case:** `k < log_b(a)` (1 < 1.585) → **Case 1**
* **Answer:** `Θ(n^{log_2 3})` (≈ `Θ(n^1.585)`) — leaf-dominated.

---

### 10) `T(n) = 5 T(n/5) + n^5 (log n)^2`

* `a=5, b=5` → `log_b(a)=log_5(5)=1`
* `f(n)=n^5 (log n)^2` → `k=5, p=2`
* **Case:** `k > log_b(a)` → **Case 3**
* **Answer:** `Θ(n^5 (log n)^2)` — root/top work dominates.

---

Nice — here are **concise, validated answers** for the remaining **10 Decreasing-function** practice
problems (from your list). I follow **your simplified Master’s (decreasing) rules** exactly: compare
`a` to `1` and use `b` from `T(n)=a T(n−b)+n^k` with `f(n)` interpreted accordingly.

For each recurrence I show:

* `a` and `b`
* Which **case** (`a < 1`, `a = 1`, or `a > 1`)
* Final complexity `Θ(...)`
* One-line reason

---

### 1) `T(n) = T(n−1) + n`

* `a = 1`, `b = 1`
* **Case:** `a = 1`
* **Answer:** `Θ(n * n) = Θ(n²)`
* **Reason:** sum of `n` over `n` steps → `n·f(n)`.

---

### 2) `T(n) = T(n−1) + log n`

* `a = 1`, `b = 1`
* **Case:** `a = 1`
* **Answer:** `Θ(n * log n)`
* **Reason:** `f(n)=log n`, so sum over `n` levels → `n·log n`.

---

### 3) `T(n) = 2 T(n−1) + 1`

* `a = 2`, `b = 1`
* **Case:** `a > 1`
* **Answer:** `Θ(1 * 2^{n/1}) = Θ(2^n)`
* **Reason:** strong branching (a>1) causes exponential growth: `a^{n/b}`.

---

### 4) `T(n) = 3 T(n−1) + n^2`

* `a = 3`, `b = 1`
* **Case:** `a > 1`
* **Answer:** `Θ(n^2 * 3^{n})`  (equivalently `Θ(3^n · n^2)`)
* **Reason:** exponential blowup multiplied by top-level polynomial `n^2`.

---

### 5) `T(n) = 0.5 T(n−2) + n`

* `a = 0.5`, `b = 2`
* **Case:** `a < 1`
* **Answer:** `Θ(n)`
* **Reason:** weak branching (`a<1`) ⇒ non-recursive work dominates → `Θ(n^k)` with `k=1`.

---

### 6) `T(n) = T(n−3) + n^3`

* `a = 1`, `b = 3`
* **Case:** `a = 1`
* **Answer:** `Θ(n * n^3) = Θ(n^4)`
* **Reason:** `a=1` sums `f(n)` over ~`n/b` ≈ `Θ(n)` steps → `n·f(n)`.

---

### 7) `T(n) = 4 T(n−1) + 1`

* `a = 4`, `b = 1`
* **Case:** `a > 1`
* **Answer:** `Θ(1 * 4^{n}) = Θ(4^n)`
* **Reason:** exponential growth with base `a`.

---

### 8) `T(n) = T(n−2) + n^4`

* `a = 1`, `b = 2`
* **Case:** `a = 1`
* **Answer:** `Θ(n * n^4) = Θ(n^5)`
* **Reason:** `a=1` → `n·f(n)` (roughly `n` steps) so `n * n^4`.

---

### 9) `T(n) = 5 T(n−5) + 1`

* `a = 5`, `b = 5`
* **Case:** `a > 1`
* **Answer:** `Θ(1 * 5^{n/5})` (i.e. `Θ(5^{n/5})`)
* **Reason:** exponential growth `a^{n/b}`; can be written as `Θ((5^{1/5})^n)`.

---

### 10) `T(n) = 2 T(n−2) + n`

* `a = 2`, `b = 2`
* **Case:** `a > 1`
* **Answer:** `Θ(n * 2^{n/2})`
* **Reason:** exponential factor `2^{n/2}` multiplied by top-level polynomial `n`.

---

## Quick recap / sanity checks

* For `a < 1`: non-recursive term `n^k` dominates → `Θ(n^k)`.
* For `a = 1`: you sum `f(n)` over ≈ `Θ(n)` steps → `Θ(n · f(n))` (or `Θ(n^{k+1})` when `f(n)=n^k`).
* For `a > 1`: exponential growth dominated — `Θ(n^k · a^{n/b})`.

---


