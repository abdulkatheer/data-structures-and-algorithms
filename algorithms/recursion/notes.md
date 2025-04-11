# Recursion

- A function calls itself!
- Optimal substructure problems: Helps to solve bigger problems by solving smaller versions of the same problem
- Top-down approach
- Any recursive solution can be turned into a iterative solution and vice versa
- Not suitable for larger inputs due to limitation in stack size
- If in case, stack size is not a limitation, then this solution is easy to reason about and write as well compared to
  iterative solution
- A Recursive code will always have two components
    1) Base case
    2) Recursive case
- To write any recursive solution, first we need to build the recurrence relation based on the fact that solutions to
  larger sub-problem can be built by solving the smaller sub-problem(s)
- Base case is the known solution to finer sub-problem.
- Recursive case is the relation to build solution for the larger sub-problem

## How to trace?

- Need to draw a recursion or tracing tree
- Recursive function call may occur at the start of code or end of the code or middle of the code or at multiple places

## Recursion and the Stack

- Function calls are executed using stack and stackframes
- When a recursive call is being made, it keeps on pushing stackframes to the stack and when it's returning, stackframes
  will be popped off of stack
- Entire recursion is purely working on the stack behaviour
- In most language runtimes, stack size is limited and smaller. Hence, recursion can't be used to solve problems of
  larger size.

## Time Complexity Analysis - Recurrence relation

To analyze the time complexity of a recursive function, we often:

1. **Build a recurrence relation** from the recursive function.
2. **Solve the recurrence** using the **successive substitution method** (also called iteration or expansion).

---

### **Step 1: Building a Recurrence Relation**

A **recurrence relation** expresses the time complexity of a recursive function in terms of smaller subproblems.

#### **Example 1: Binary Search**

```java
int binarySearch(int arr[], int left, int right, int key) {
    if (left > right) return -1;
    int mid = left + (right - left) / 2;
    if (arr[mid] == key) return mid;
    if (arr[mid] > key) return binarySearch(arr, left, mid - 1, key);
    return binarySearch(arr, mid + 1, right, key);
}
```

- The function reduces the problem size by half in each recursive call.
- The recurrence relation is:  
  \[
  T(n) = T(n/2) + O(1)
  \]
  (Since we divide the array size by 2 and do constant work `O(1)` outside recursion).

---

### **Step 2: Applying Successive Substitution (Iteration Method)**

We expand the recurrence by **replacing T(n) recursively** until we reach the base case.

1. **First expansion:**  
   \[
   T(n) = T(n/2) + O(1)
   \]
2. **Substituting \( T(n/2) \) from the same recurrence:**  
   \[
   T(n) = (T(n/4) + O(1)) + O(1)
   \]
   \[
   = T(n/4) + 2O(1)
   \]
3. **Substituting \( T(n/4) \) from the same recurrence:**  
   \[
   T(n) = (T(n/8) + O(1)) + 2O(1)
   \]
   \[
   = T(n/8) + 3O(1)
   \]
4. **Continuing for \( k \) steps:**  
   \[
   T(n) = T(n/2^k) + kO(1)
   \]
5. **Stopping when \( n/2^k = 1 \) (Base Case: \( T(1) = O(1) \))**

- This happens when \( k = \log_2 n \), so substitute \( k = \log_2 n \):  
  \[
  T(n) = O(1) + O(\log n)
  \]
  \[
  T(n) = O(\log n)
  \]
  **Conclusion:** The **binary search** algorithm runs in **O(log n)** time.

---

### **Example 2: Merge Sort**

```java
void mergeSort(int arr[], int left, int right) {
    if (left >= right) return;
    int mid = left + (right - left) / 2;
    mergeSort(arr, left, mid);
    mergeSort(arr, mid + 1, right);
    merge(arr, left, mid, right); // O(n) merge step
}
```

- The recurrence relation is:  
  \[
  T(n) = 2T(n/2) + O(n)
  \]
  (Since we divide into two subproblems and spend \( O(n) \) merging them).
- Why T(n/2) and O(n)
    - Bcz, mergeSort is recursive and defines in T terms and merge is non-recursive and directly defined in O terms

#### **Applying Successive Substitution**

1. **Expanding once:**  
   \[
   T(n) = 2T(n/2) + O(n)
   \]
2. **Expanding \( T(n/2) \):**  
   \[
   T(n) = 2(2T(n/4) + O(n/2)) + O(n)
   \]
   \[
   = 4T(n/4) + 2O(n)
   \]
3. **Expanding \( T(n/4) \):**  
   \[
   T(n) = 4(2T(n/8) + O(n/2)) + 2O(n)
   \]
   \[
   = 8T(n/8) + 3O(n)
   \]
4. **Expanding \( k \) times:**  
   \[
   T(n) = 2^k T(n/2^k) + k O(n)
   \]
5. **Stopping when \( n/2^k = 1 \) (Base case \( T(1) = O(1) \)):**

   \( n/2^k = 1 -> n = 2^k -> k = log_2 n \), so:

   \[
   T(n) = 2^(log_2 n) O(1) + O(n log_2 n)
   \]

   \[
   T(n) = n/2 x 2 x O(1) + O(n log_2 n)
   \]

   \[
   T(n) = n x O(1) + O(n log_2 n) = O(n) + O(n log_2 n)
   \]

   \[
   = O(n log n)
   \]

- **Conclusion:** **Merge Sort runs in \( O(n log n) \).**

---

### **Summary**

| Recurrence Relation         | Expansion Leads To | Complexity                             |
|-----------------------------|--------------------|----------------------------------------|
| \( T(n) = T(n/2) + O(1) \)  | \( O(log n) \)     | **O(log n)** (Binary Search)           |
| \( T(n) = 2T(n/2) + O(n) \) | \( O(n log n) \)   | **O(n log n)** (Merge Sort)            |
| \( T(n) = T(n-1) + O(1) \)  | \( O(n) \)         | **O(n)** (Factorial, Linear Recursion) |

## Types of Recursion

1. Tail Recursion
2. Head Recursion
3. Tree Recursion
4. Indirect Recursion
5. Nested Recursion

### **Types of Recursion – Compared** 🚀

Recursion can be classified based on **how** the recursive function calls itself and **where** the recursive call occurs
in the function.

---

### **1️⃣ Direct vs. Indirect Recursion**

#### **🔹 Direct Recursion**

- A function **calls itself** directly.
- Example: Factorial
  ```java
  int factorial(int n) {
      if (n == 0) return 1;
      return n * factorial(n - 1);
  }
  ```

#### **🔹 Indirect Recursion**

- A function **calls another function**, which eventually calls the original function.
- Example: Two functions calling each other.
  ```java
  void funA(int n) {
      if (n <= 0) return;
      System.out.println("A: " + n);
      funB(n - 1);
  }
  void funB(int n) {
      if (n <= 0) return;
      System.out.println("B: " + n);
      funA(n - 2);
  }
  ```

---

### **2️⃣ Tail vs. Non-Tail Recursion**

#### **🔹 Tail Recursion**

- The **recursive call is the last statement** in the function (no further computation after it).
- Can be optimized by the compiler to **avoid extra stack space (Tail Call Optimization, TCO)**.
- Example: Tail recursive factorial
  ```java
  int factTail(int n, int result) {
      if (n == 0) return result;
      return factTail(n - 1, result * n);
  }
  // Call with factTail(n, 1)
  ```

#### **🔹 Non-Tail Recursion**

- The recursive call is **not the last operation** in the function.
- Extra work is done **after the recursive call**.
- Example: Normal factorial
  ```java
  int fact(int n) {
      if (n == 0) return 1;
      return n * fact(n - 1); // Multiplication happens after recursive call
  }
  ```

---

### **3️⃣ Linear vs. Tree vs. Nested Recursion**

#### **🔹 Linear Recursion**

- Each function call makes **only one** recursive call.
- Example: Factorial
  ```java
  int factorial(int n) {
      if (n == 0) return 1;
      return n * factorial(n - 1);
  }
  ```

#### **🔹 Tree Recursion**

- A function makes **multiple recursive calls** at each step.
- Example: Fibonacci
  ```java
  int fib(int n) {
      if (n <= 1) return n;
      return fib(n - 1) + fib(n - 2);
  }
  ```
    - **Time Complexity:** \( O(2^n) \) (Exponential)
    - **Optimization:** Use **Dynamic Programming (Memoization/Tabulation)**.

#### **🔹 Nested Recursion**

- A function's **argument** contains another **recursive call**.
- Example:
  ```java
  int nested(int n) {
      if (n > 100) return n - 10;
      return nested(nested(n + 11));
  }
  ```

---

### **4️⃣ Backtracking Recursion**

- Recursion is used to **explore all possibilities** and then backtrack.
- Example: N-Queens Problem, Sudoku Solver
  ```java
  void backtrack(int step) {
      if (step == solution) {
          printSolution();
          return;
      }
      for (each possibility) {
          if (valid(possibility)) {
              makeMove(possibility);
              backtrack(step + 1);
              undoMove(possibility); // Backtrack
          }
      }
  }
  ```

---

### **Key Takeaways**

| Type                       | Definition                               | Example                  |
|----------------------------|------------------------------------------|--------------------------|
| **Direct Recursion**       | Function calls itself directly           | Factorial                |
| **Indirect Recursion**     | Functions call each other in a cycle     | funA → funB → funA       |
| **Tail Recursion**         | Recursive call is the **last statement** | Tail-recursive factorial |
| **Non-Tail Recursion**     | Work is done **after** recursive call    | Normal factorial         |
| **Linear Recursion**       | Only **one** recursive call per step     | Factorial                |
| **Tree Recursion**         | **Multiple** recursive calls per step    | Fibonacci                |
| **Nested Recursion**       | Recursion **inside function arguments**  | Nested function calls    |
| **Backtracking Recursion** | Tries possibilities and backtracks       | N-Queens                 |

## Problem Solving:

### How to identify recursion-based problems?

### How to build the solution?

### Famous problems & patterns