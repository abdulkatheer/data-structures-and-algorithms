Great ask! XOR is one of those hidden gems in DSA — **tiny operator, massive power**. It’s a **bitwise operator** (`^`
in Java, C++, etc.), and mastering its properties helps you solve many **clever tricks** in problems like:

- Finding single/non-repeating elements
- Swapping values without temp
- Checking parity (even/odd)
- Bit manipulation shortcuts

---

## 🔑 **Must-Know XOR Properties in DSA**

| Expression                  | Result               | Meaning                          |
|-----------------------------|----------------------|----------------------------------|
| `x ^ 0 = x`                 | Identity             | XOR with 0 gives the same number |
| `x ^ x = 0`                 | Self-inverse         | XOR with itself cancels out      |
| `x ^ y = y ^ x`             | Commutative          | Order doesn't matter             |
| `(x ^ y) ^ z = x ^ (y ^ z)` | Associative          | Grouping doesn't matter          |
| `x ^ 1`                     | Flips the lowest bit | Useful for toggling bits         |

---

## 🔥 **Most Common XOR Tricks in DSA**

### 1. **Find the Single Number (non-repeating)**

In an array where **every number appears twice except one**, use:

```java
int result = 0;
for(
int num :arr)result ^=num;
return result;
```

✅ Works because all duplicates cancel each other.

---

### 2. **Swap Two Numbers Without Temp**

```java
a =a ^b;
b =a ^b;
a =a ^b;
```

👉 Works because XOR is reversible and associative.

---

### 3. **Find Two Unique Numbers (All Others Appear Twice)**

(Like in Leetcode’s [260. Single Number III](https://leetcode.com/problems/single-number-iii/))

```java
int xor = 0;
for(
int num :nums)xor ^=num;  // xor = a ^ b

int diffBit = xor & (-xor);       // isolate rightmost set bit

        int a = 0, b = 0;
for(
int num :nums){
        if((num &diffBit)==0)
a ^=num;
    else
b ^=num;
}
```

---

### 4. **Check if a Number is Power of Two**

```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

✅ Also uses bit manipulation, related to XOR thinking.

---

### 5. **Odd or Even**

```java
boolean isEven = (x & 1) == 0;
```

(Though not XOR, it's from the same bit manipulation toolkit.)

---

### 6. **Detect Duplicates Using XOR Hashing**

You can XOR values and indexes to detect mismatch/duplicate/missing element patterns.

---

## 🧠 Tip: XOR is its Own Inverse

You can "undo" an XOR by reapplying it:

```java
x ^y ^y =x
```

So:

```java
a =a ^b;
a =a ^b; // ← this actually returns a again!
```

---

Want to try a **real XOR-based DSA problem** next? I can give you one to practice with a hint!

Absolutely! Let's dive deep into the intuition and working of examples **3, 4, and 5** using XOR and bit manipulation —
these are super useful patterns for interviews and problem-solving.

---

## ✅ 3. **Find Two Unique Numbers (All Others Appear Twice)**

### 📘 Problem:

Given an array where every number appears **twice**, **except two numbers** which appear only once, find them.

**Example:**  
Input: `[2, 4, 7, 9, 2, 4]`  
Output: `7 and 9`

---

### 🧠 Intuition:

1. **XOR all numbers** → this will cancel out all duplicates and give you `a ^ b`, where `a` and `b` are the two unique
   numbers.

2. But now you need to **separate a and b** — because XOR of a and b doesn’t tell you which is which.

3. Find the **rightmost set bit** in `a ^ b` — this bit is different in `a` and `b`, so you can use it to **divide the
   array into 2 groups**:
    - One group where that bit is `0`
    - One where it's `1`

4. Now XOR each group separately → you'll get `a` in one, `b` in the other.

---

### 🧪 Code Example:

```java
public int[] findTwoUnique(int[] nums) {
    int xor = 0;
    for (int num : nums) xor ^= num; // xor = a ^ b

    int diffBit = xor & -xor; // isolate rightmost set bit

    int a = 0, b = 0;
    for (int num : nums) {
        if ((num & diffBit) == 0)
            a ^= num;
        else
            b ^= num;
    }

    return new int[]{a, b};
}
```

### 📌 Output for `[2, 4, 7, 9, 2, 4]` → `[7, 9]` (order may vary)

---

## ✅ 4. **Check If a Number Is Power of Two**

### 📘 Problem:

You’re given a number `n`, check if it's a power of 2.

### 🧠 Intuition:

- A power of 2 in binary has exactly **one set bit**.
    - `1` → `0001`
    - `2` → `0010`
    - `4` → `0100`
    - `8` → `1000`

If a number is a power of two, then:

```java
n &(n -1)==0
```

Because subtracting `1` flips the rightmost set bit and sets all bits to the right of it to 1. ANDing it with original
gives 0.

---

### ✅ Code:

```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

### 🧪 Examples:

- `4 → 100 & 011 = 000 → true`
- `5 → 101 & 100 = 100 → false`

---

## ✅ 5. **Odd or Even Check Using Bitwise**

This is **basic but powerful** — often used in recursive/base cases or toggling behavior.

### 🧠 Intuition:

- If the **last bit is 0**, the number is even.
- If the **last bit is 1**, it’s odd.

Why? Because binary representation of odd numbers always ends in `1`.

---

### ✅ Code:

```java
boolean isEven(int x) {
    return (x & 1) == 0;
}
```

### 🧪 Examples:

- `x = 6 → 0110 & 0001 = 0 → even`
- `x = 7 → 0111 & 0001 = 1 → odd`

---

Would you like me to walk through a **custom input and trace each step visually** for any of these? It’ll help you
internalize it!

Great! Let’s fully break down **Example 3: Finding Two Unique Numbers Using XOR**, with **binary representation** so you
see exactly how and why it works.

---

## ✅ Problem Recap:

Given an array where every element appears **twice**, except **two elements appear only once**, find those two unique
elements.

### ✨ Example:

```java
arr =[2,4,7,9,2,4]
```

Duplicates: 2, 4  
Unique: 7 and 9

---

## Step-by-Step with Binary:

### 🔹 Step 1: XOR All Elements

Let’s XOR all elements:

| Num | Binary |
|-----|--------|
| 2   | `0010` |
| 4   | `0100` |
| 7   | `0111` |
| 9   | `1001` |
| 2   | `0010` |
| 4   | `0100` |

Now XOR them one by one:

```
(2 ^ 4)     = 0010 ^ 0100 = 0110
(0110 ^ 7)  = 0110 ^ 0111 = 0001
(0001 ^ 9)  = 0001 ^ 1001 = 1000
(1000 ^ 2)  = 1000 ^ 0010 = 1010
(1010 ^ 4)  = 1010 ^ 0100 = 1110
```

Final XOR: `1110`  
→ This is `7 ^ 9`, because 2 and 4 canceled themselves.

---

## Step 2: Find a Bit That Differs in 7 and 9

We got:

```
xor = 1110 → means 7 and 9 differ in **at least one bit**
```

To find a bit that differs (a set bit in `xor`), do:

```java
diffBit =xor &-xor;
```

- `xor = 1110`
- `-xor = 0010` (2's complement of 1110 is 0010)
- `1110 & 0010 = 0010` → isolated **2nd bit from right**

This bit is **set in one of the unique numbers**, and **unset in the other**.

---

## Step 3: Split Array into Two Buckets Based on That Bit

We now divide array elements into two groups:

- Group A: elements where `bit 0010` is `0`
- Group B: elements where `bit 0010` is `1`

Let’s go through the array again:

| Num | Binary | 0010 bit     |
|-----|--------|--------------|
| 2   | 0010   | 1        → B 
| 4   | 0100   | 0        → A 
| 7   | 0111   | 1        → B 
| 9   | 1001   | 0        → A 
| 2   | 0010   | 1        → B 
| 4   | 0100   | 0        → A 

Now:

- Group A: `[4, 9, 4]`
- Group B: `[2, 7, 2]`

---

## Step 4: XOR Each Group

Group A:

```
4 ^ 9 ^ 4 = (0100 ^ 1001) ^ 0100 = 1101 ^ 0100 = 1001 = 9
```

Group B:

```
2 ^ 7 ^ 2 = (0010 ^ 0111) ^ 0010 = 0101 ^ 0010 = 0111 = 7
```

✅ **Final Result:** `7 and 9`

---

## TL;DR Summary

1. XOR everything → cancels duplicates → gives `a ^ b`
2. Find a set bit where `a` and `b` differ
3. Split into 2 groups based on that bit
4. XOR within each group → gets `a` and `b` separately

---

Want to try one yourself? I can give you a similar XOR-based puzzle to solve next!