### **Understanding \( C(n, k) = C(n-1, k-1) + C(n-1, k) \) in Detail**

The equation:

\[
C(n, k) = C(n-1, k-1) + C(n-1, k)
\]

is based on the **combinatorial identity** that helps compute the number of ways to choose \( k \) elements from \( n \)
elements.

---

## **Why Does This Formula Work?**

Consider a set of \( n \) elements:

\[
S = \{ e_1, e_2, e_3, \dots, e_n \}
\]

To select \( k \) elements from \( n \), we focus on **one specific element**, say \( e_n \) (the last element in the
set). We have **two choices**:

1. **Include \( e_n \) in the selection**
    - If we choose \( e_n \), we need to select the remaining \( k-1 \) elements from the **first \( n-1 \) elements**.
    - The number of ways to do this is **\( C(n-1, k-1) \)**.

2. **Exclude \( e_n \) from the selection**
    - If we do **not** choose \( e_n \), then we must select all \( k \) elements from the **first \( n-1 \) elements**.
    - The number of ways to do this is **\( C(n-1, k) \)**.

Since these two cases **cover all possibilities**, the total number of ways to choose \( k \) elements from \( n \)
elements is:

\[
C(n, k) = C(n-1, k-1) + C(n-1, k)
\]

---

## **Example 1: \( C(5, 2) \)**

We want to compute:

\[
C(5, 2) = C(4, 1) + C(4, 2)
\]

### **Breaking it down:**

#### Step 1: Compute \( C(4,1) \)

\[
C(4,1) = C(3,0) + C(3,1)
\]

\[
C(3,0) = 1, \quad C(3,1) = C(2,0) + C(2,1) = 1 + 2 = 3
\]

\[
C(4,1) = 1 + 3 = 4
\]

#### Step 2: Compute \( C(4,2) \)

\[
C(4,2) = C(3,1) + C(3,2)
\]

\[
C(3,1) = 3, \quad C(3,2) = C(2,1) + C(2,2) = 2 + 1 = 3
\]

\[
C(4,2) = 3 + 3 = 6
\]

#### Step 3: Compute \( C(5,2) \)

\[
C(5,2) = 4 + 6 = 10
\]

Thus,

\[
C(5,2) = 10
\]

which matches the formula:

\[
C(n, k) = \frac{n!}{k!(n-k)!} = \frac{5!}{2!(5-2)!} = \frac{5!}{2!3!} = \frac{5 \times 4}{2 \times 1} = 10
\]

---

## **Example 2: \( C(4, 2) = C(3,1) + C(3,2) \)**

Expanding:

\[
C(3,1) = C(2,0) + C(2,1) = 1 + 2 = 3
\]

\[
C(3,2) = C(2,1) + C(2,2) = 2 + 1 = 3
\]

\[
C(4,2) = 3 + 3 = 6
\]

---

## **Base Cases**

1. **When \( k = 0 \) (selecting nothing), there's exactly 1 way**:
   \[
   C(n, 0) = 1
   \]
   (Since we always have one way to select an empty subset.)

2. **When \( k = n \) (selecting all elements), there's exactly 1 way**:
   \[
   C(n, n) = 1
   \]

---

## **Recursive Computation Tree for \( C(5, 2) \)**

\[
C(5,2) = C(4,1) + C(4,2)
\]

\[
C(4,1) = C(3,0) + C(3,1)
\]

\[
C(4,2) = C(3,1) + C(3,2)
\]

Expanding this further:

```
                C(5,2)
               /      \
          C(4,1)      C(4,2)
         /     \       /     \
      C(3,0)  C(3,1) C(3,1)  C(3,2)
               /  \         /    \
           C(2,0) C(2,1)  C(2,1) C(2,2)
                   /  \      /  \
               C(1,0) C(1,1) C(1,0) C(1,1)
```

This shows how the problem is **broken down into smaller subproblems** recursively.

---

## **Time Complexity**

The recurrence relation is:

\[
T(n, k) = T(n-1, k-1) + T(n-1, k)
\]

This results in an **exponential complexity**:

\[
O(2^n)
\]

To optimize it, we can use **memoization (dynamic programming)**.

Would you like to see the optimized approach? 🚀