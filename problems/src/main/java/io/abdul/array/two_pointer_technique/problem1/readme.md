# Brute-force

Find all possible pairs n(n+1) / 2
And match sum

Time - O(n^2)
Space - O(1)

# Better - Two Pointer approach

Idea here is, numbers are in ascending order.
So we maintain two pointers, one at the extreme lowest number and the other at the extreme largest number. This is the
biggest sum.
Now we need to shrink or expand to meet the sum.

<pre>
1 2 3 4 5 6 7 8 9 10, target=18
i=0, j=9 1 10 -> not enough, expand
i=1, j=9 2 10 -> not enough, expand
i=2, j=9 3 10 -> not enough, expand
i=3, j=9 4 10 -> not enough, expand
i=5, j=9 5 10 -> not enough, expand
i=6, j=9 6 10 -> not enough, expand
i=7, j=9 7 10 -> not enough, expand
i=8, j=9 8 10 -> match found!


1, 2, 3, 4, 5, 6, 7, 8, 9, 10, target=3
i=0, j=9 1 10 -> bigger, shrink
i=0, j=8 1 9  -> bigger, shrink
i=0, j=7 1 8  -> bigger, shrink
i=0, j=6 1 7  -> bigger, shrink
i=0, j=5 1 6  -> bigger, shrink
i=0, j=4 1 5  -> bigger, shrink
i=0, j=3 1 4  -> bigger, shrink
i=0, j=2 1 3  -> bigger, shrink
i=0, j=1 1 2  -> match found!
</pre>

### Note about expansion and shrinking
When looking for a subarray, we need to keep i and j at same are adjacent positions.
Bcz here when we say expand, j will move one step right and the total sum will be increased
When we say shrink, i will move one step right and the total sum will be decreased
Here we take sum of elements.

When looking for individual elements, we need to  keep i and j and extreme low and high positions.
Otherwise, if we keep i and j at adjacent positions.
When we say expand, j will move to the right and i+j will increase
When we say shrink, i will move to the right and i+j will also increase, but we expect it to decrease.
Both positions moving in same direction and always tend to increase.

So we need to take at low and high position
When we say expand, i will move to the right and i+j will increase
When we say shrink, j will move to the left and i+j will decrease

So better use term increase, decrease for single element sums