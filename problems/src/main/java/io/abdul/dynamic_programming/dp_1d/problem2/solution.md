Similar to Climbing stairs, but added cost. We need to minimize cost.

How do we handle base cases, we can't just return 0 now. We need a way to tell that a path is not possible.
We can go with Int.MAX or -1 to distinguish.
In either way, add a check to make sure if we handle invalid cases differently than valid cases.

At any step,
if we're at i==j, last step, then it's definitely a valid path.
if i<j, we can at least make 1 step.
2 steps may or may not be possible. So check and make it.
If not possible, keep def value as Int.MAX and return min, which will be other path.
This is a base case merged within the logic.

You can also add separate base like
if (i>j) return Int.MAX
and check the result of method
if (result == Int.MAX) don't add, or return step1 value. If we add, due to overflow, the result will be negative and
will become min of both.
