This is actually backtracking/subsequence type of question, that can best be solved using greedy
algorithms.
The greedy approach uses monotonic stack!
In Divide and Conquer approach (recursive and DP), we explore all subsequences. Best we can do is in
O(n^2) time.
But with Greedy, we can do in O(n) time.

What's the greedy intuition here?
If we can remove the first k larger elements, then the resulting number will be the smallest.

71843261534321 and k = 3
what's the first 3 larger elements 7,8,4. How do we find it?
Using Monotonic Increasing Stack.
s[]
s[7]
s[1], remove 7
s[1,8]
s[1,4], remove 8
s[1,3], remove 4

Now k elements are removed. Add the remaining as is.
s[1,3,2,6,1,5,3,4,3,2,1] -> rebuild the string and reverse it

Edge case:
We may not be able to remove k chars during full iteration.
1 2 3 4 5 6 7 8 9 -> here stack will be partially/completely monotonic!

if we remove 1 2 3, we get 4 5 6 7 8 9
if we remove 7 8 9, we get 1 2 3 4 5 6
So, we need to remove the last added 3 elements.

rebuild the string and reverse it!