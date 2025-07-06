## Optimal

At every pos, we try to find in how many subarrays, arr[pos] can the min element.
For that, we find the first smaller or equal element in the left -> that's the start of the subarray
we find the smaller element in the right -> that's the end of the subarray

Why smaller or equals in the left?
[1 1 1 1 1]
Total subarrays = 5 + 4 + 3 + 2 + 1 = 15

Let's take smaller on both ends and see what happens
0 1 2 3 4
pse = [-1 -1 -1 -1 -1]
nse = [5 5 5 5 5]

At pos=0, 1 * 5 -> 0 to 4
At pos=1, 2 * 4 -> 0 to 4
At pos=2, 3 * 3 -> 0 to 4
At pos=3, 4 * 2 -> 0 to 4
At pos=4, 5 * 1 -> 0 to 4
Total = 6 + 8 + 9 + 8 + 5 = 36 > possible subarrays

We counted duplicates!

Let's take smaller or equals on left, smaller on the right
0 1 2 3 4
pse = [-1 0 1 2 3]
nse = [ 5 5 5 5 5]

At pos=0, 1 * 5 -> 0 to 4
At pos=1, 1 * 4 -> 1 to 4
At pos=2, 1 * 3 -> 2 to 4
At pos=3, 1 * 2 -> 3 to 4
At pos=4, 1 * 1 -> 4 to 4
Total = 5 + 4 + 3 + 2 + 1 = 15 == possible subarrays

Let's take smaller on left, smaller or equals on the right
0 1 2 3 4
pse = [-1 -1 -1 -1 -1]
nse = [ 1 2 3 4 5]

At pos=0, 1 * 1 -> 0 to 4
At pos=1, 2 * 1 -> 1 to 4
At pos=2, 3 * 1 -> 2 to 4
At pos=3, 4 * 1 -> 3 to 4
At pos=4, 5 * 1 -> 4 to 4
Total = 1 + 2 + 3 + 4 + 5 = 15 == possible subarrays

So proven that, either we need skip duplicates by starting/ending, pse or nse side.

