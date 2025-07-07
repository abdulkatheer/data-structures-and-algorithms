## Brute-force

Always stores two values in the stack
arr[0] actual value, arr[1] the min value at that time

Just need 2n times to store elements

## Optimal

> This can't be discovered by just seeing problem, this is a trick and has to be remembered.

- We need to keep track of the min value at any given point in time
- When min is asked, return the min
- When top is asked, we need to return the actual top of stack
- When a min element is deleted, we need to go back to the previous min

So when stack is empty, the new value is the min.
When a larger number is pushed, it doesn't change the min. Just push it.
When a smaller number is pushed, we need to update min and push a value to the stack.
The value pushed should help us

- in identifying who is on the top of the stack. (as we push modified value for mins)
- rolling back to previous min

new_value < min
new_value - min < 0
new_value + new_value - min < value
ex: 10 + 10 - 12 == 8 < 10

if we insert 8,

1) 8 < 10, so we can identify if min is on the top and return min instead of actual top.
2) we can bring back previous min 12
   2 * min - top_value = 20 - 8 = 12, the previous min