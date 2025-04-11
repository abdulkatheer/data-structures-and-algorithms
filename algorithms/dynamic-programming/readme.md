# DP

DP is an Optimization Technique to a problem which has overlapping sub-problems.
This is actually a technique to optimize a problem which is solved using Recursive Algorithms.
We use recursive algorithms, if a problem can be split into sub-problems and sub-problem together forms the whole
solution.
When solving sub-problems, if they overlap, meaning if we repeat solving same sub-problem, that can be optimized by
memoization or tabulation, which is called as DP.

Recursive Solution -> Iterative Solution (Saving stack space) -> Iterative with Memoization -> Iterative with
Tabulation (if possible) -> Iterative with limited Tabulation (if possible)

Ex 1:
Fibonacci

Step 1: Identifying that this can be solved using Recursion Algorithm
<pre>
Recursive function: <p>
f(n) = n, where n=0, n=1 <p>
f(n) = f(n-1) + f(n-2), where n > 1
</pre>

Solve it with recursion

Step 2: [Optional] If same sub-problems solved multiple times, memoize them to save computations

Step 3: Convert recursive to iterative solution
Any recursion algorithm can be converted to iteration algorithm and vice versa. So convert as-is, no other
optimizations!

Step 4: Add tabulation (or replace Memoization) to save computations

Step 5: [Optional] Optimize tabulation space (store only what is required)