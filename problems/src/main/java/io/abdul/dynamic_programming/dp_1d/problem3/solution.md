# Recursive solution

Similar to FrogJumpI, but we can make 1 to k steps and not just 1 or 2 steps.
Find min in an iteration. Just sure any step is not going out of bounds.
The base case will be merged into this loop itself.

k=3, n=5
At i=0,
we can to 1, 2, 3
At i=1,
we can go to 2,3,4
At i=2
we can go to 3,4
At i=3
we can go to 4
At i=4 -- base case, return 0

# Iterative solution

Base case is in the bounds, so we can go with normal dp array with 0-based indexing
dp[0] = 0;
at i=1, we can only come from 0
at i=2, we can come from 0,1
at i=3, we can come from 0,1,2
at i=4, we can come from 1,2,3

dp[4] stores the min cost to come to 4 from 0
