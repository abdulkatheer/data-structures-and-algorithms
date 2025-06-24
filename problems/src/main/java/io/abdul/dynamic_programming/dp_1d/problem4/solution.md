# Recursive solution
Classical take or skip type of problem

If we take 0, we need to go to 2
If we skip 0, we need to go to 1

Return max of take, skip

base case - if we're at last pos, i=n-1, return the num itself
if i==n, return 0.

# Iterative solution

Known solution:
As base case is out of bounds, we can go with 1-based indexing
We can make base case in the bounds like
dp[0] - with only one element, result is itself
dp[1] - with two elements, result is max of both

dp[i] = max(nums[i] + dp[i-2], dp[i-1])