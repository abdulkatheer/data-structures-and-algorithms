# Recursive solution

This is a classical take or skip type of problem.
At any position, we take and move to next after next
we skip and move to next
We need total, so add both take and skip

## Note about recursion parameters

If you send any changing parameters like steps or count left like that, which changes just before
calling the recursion and the base case will return an accumulated sum.
In this case, memoization will take more space, as we need 1 or more additional dimension to track that value. And we
won't cache much as well.

So always prefer tail recursion with no additional state being passed except iteration parameters.

# Iterative solution

Known solution is within bounds, so we can go with normal case and use 0-based indexing
dp[0] - 1 possible if we have just 1 step
dp[1] - 2 possibles ways if we have just 2 steps
dp[i] - x possible ways if we've i steps

dp[i] = dp[i-1] + dp[i-2]
unique ways if we've come to i by making 1 step + unique ways if we've come to i by making 2 steps