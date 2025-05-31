### ✅ **Optimal Substructure: Explained in Detail**

---

### 🧠 Definition:

A problem has **optimal substructure** if **the optimal solution to the problem can be constructed from the optimal
solutions of its subproblems**.

---

### 🔍 In Other Words:

If you divide a problem into smaller parts, and the **best way to solve the whole** is by **combining the best way to
solve the parts**, then the problem has optimal substructure.

---

### 🔁 This property is **essential** for:

* **Greedy algorithms**
* **Dynamic Programming**
* **Divide & Conquer algorithms**

But how it's used differs slightly:

| Paradigm            | Uses Optimal Substructure? | But Also Requires...    |
|---------------------|----------------------------|-------------------------|
| Greedy              | ✅ Yes                      | Greedy-choice property  |
| Dynamic Programming | ✅ Yes                      | Overlapping subproblems |
| Divide & Conquer    | ✅ Yes                      | Independent subproblems |

---

## 🧵 Intuition with Examples

---

### ✅ **1. Fibonacci Numbers**

`fib(n) = fib(n - 1) + fib(n - 2)`

* To compute `fib(5)`, you compute `fib(4)` and `fib(3)`
* The optimal value of `fib(5)` depends on optimal values of its subproblems
* ✔️ **Has optimal substructure**

---

### ✅ **2. Shortest Path in Graph (Dijkstra’s Algorithm)**

Let’s say we want shortest path from A → D via B.

If A → B is the shortest path, and B → D is the shortest path,
then A → B → D is the shortest path.

* ✔️ **Optimal path contains optimal subpaths**
* ✔️ **Has optimal substructure**

---

### ✅ **3. 0/1 Knapsack**

Let `dp[i][w]` be the max value with first `i` items and capacity `w`.

* You either take the ith item or skip it.
* The best answer is based on subproblems: with `i - 1` items.
* ✔️ **Optimal substructure**

---

### ❌ **When Optimal Substructure Fails**

Let’s say you need to find the **best route** that goes through **specific cities in specific order**, or when path
constraints are non-local.

Example: **Traveling Salesman Problem**

* Even if A→B and B→C are shortest, A→B→C may not be part of global optimum due to circular constraints.
* ❌ Doesn’t strictly have **greedy** optimal substructure.
* But still used in DP with additional state.

---

## 🔍 How to Recognize Optimal Substructure in Problems

Ask these questions:

* Can I break the problem into smaller subproblems?
* Does solving the smaller subproblems help solve the bigger one optimally?
* Are the subproblems overlapping? (for DP)
* Are they independent? (for Divide & Conquer)

---

### 🎓 A Quick Comparison of Examples:

| Problem                          | Has Optimal Substructure? | Notes                                     |
|----------------------------------|---------------------------|-------------------------------------------|
| Fibonacci                        | ✅                         | Classic DP example                        |
| 0/1 Knapsack                     | ✅                         | DP table solution                         |
| Longest Common Subseq.           | ✅                         | Substring comparison + DP                 |
| Dijkstra                         | ✅                         | Shortest path uses smaller shortest paths |
| Greedy Coin Change               | ✅ but sometimes ❌         | Only works if coin system is canonical    |
| TSP (Traveling Salesman Problem) | ❌ Greedy                  | Requires DP with bitmask or heuristics    |

---

## 🔧 Summary

* **Optimal Substructure** is a property that enables the **divide-and-conquer** approach.
* If a problem has optimal substructure, and subproblems **overlap**, then you can apply **dynamic programming**.
* If **subproblems are independent**, then **divide and conquer** applies.
* If you can always make a **locally optimal choice** that leads to a global solution, it’s suitable for **greedy
  algorithms**, but that’s an **extra condition**.

---

Would you like to test your understanding by checking whether a few problems have optimal substructure or not?
