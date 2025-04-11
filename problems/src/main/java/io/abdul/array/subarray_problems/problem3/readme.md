# Bruteforce approach

All possible subarrays in an 1D array
[1, 2, 3, 4, 5]
1 12 123 1234 12345 5
2 23 234 2345 4
3 34 345 3
4 45 2
5 1

n + (n-1) + (n-2) + ... 1 = n(n+1) / 2 = 5(6) / 2 = 15

# Better solution

This is a core idea for multiple similar problems

We maintain prefix sum s[n], where s[i] is the sum of all elements upto i
At each element, we check if prefix_sum - k exists in our store. If exists, a subarray exists whose sum is k.
How?
1, 2, 1, 4, 8, 7, 1, 2, 6, 0, 3, 4, 1, 1, 1, 5 & k=3

We take 0 with index -1 to match values matching k or at the starting like 1 2, where there'll be no prefix sum matching
zero

| Index | Element | Longest | Note                                                                     |
|-------|---------|---------|--------------------------------------------------------------------------|
|       |         | 0       |                                                                          |
| 0     | 1       | 0       |                                                                          |
| 1     | 2       | 2       | 3-3=0, 0 exists in prefix sum and position -1. So length is 1 - (-1) = 2 |
| 2     | 4       | 2       |                                                                          |
| 3     | 8       |         |                                                                          |
| 4     | 7       |         |                                                                          |
| 5     | 1       |         |                                                                          |
| 6     | 2       |         |                                                                          |
| 7     | 6       |         |                                                                          |
| 8     | 0       |         |                                                                          |
| 9     | 3       | 10      | 34-3=31, 31 exists. So length is 9-7=2                                   |
| 10    | 4       |         |                                                                          |
| 11    | 1       |         |                                                                          |
| 12    | 1       |         |                                                                          |
| 13    | 1       |         | 41-3=38, 38 exists. So length is  13-3=3                                 |
| 14    | 5       |         |                                                                          |

| Prefix sum | Index |
|------------|-------|
| 1          | 0     |
| 3          | 1     |
| 7          | 2     |
| 15         | 3     |
| 22         | 4     |
| 23         | 5     |
| 25         | 6     |
| 31         | 7,8   |
| 34         | 9     |
| 38         | 10    |
| 39         | 11    |
| 40         | 12    |
| 41         | 13    |
| 46         | 14    |

# Optimal Solution

Suits only of positive non-zero numbers

Idea is

- Have two pointers i and j
- If sum is equals k, count and set the longest length and Move i to the right and set j=i
- If sum lesser than k, move j to the right
- If sum greater than k, move i to the right and set j=i
- If all subarrays of position i doesn't enough, j will hit length. When j hits length, increment i and set j=i

Assumption is that, all are positive numbers. And if adding a number makes sum greater than k, no need to visit that
remaining subarray of the position i
Adding a number still keeps sum less than k, then we can visit one more element in the same subarray of i to see if we
can find a match
<pre>
0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15
1, 2, 1, 4, 8, 7, 1, 2, 6, 2, 3, 4, 1, 1, 1, 5 & k=3
</pre>

| i  | j  | sum | longest   |
|----|----|-----|-----------|
| 0  | 0  | 1   | -Infinity |
| 0  | 1  | 3   | 2 (Set)   |
| 1  | 1  | 2   | 2         |
| 1  | 2  | 3   | 2         |
| 2  | 2  | 1   | 2         |
| 2  | 3  | 5   | 2         |
| 3  | 3  | 4   | 2         |
| 4  | 4  | 8   | 2         |
| 5  | 5  | 7   | 2         |
| 6  | 6  | 1   | 2         |
| 6  | 7  | 3   | 2         |
| 7  | 7  | 2   | 2         |
| 7  | 8  | 8   | 2         |
| 8  | 8  | 6   | 2         |
| 9  | 9  | 2   | 2         |
| 9  | 10 | 5   | 2         |
| 10 | 10 | 3   | 2         |
| 11 | 11 | 4   | 2         |
| 12 | 12 | 1   | 2         |
| 12 | 13 | 2   | 2         |
| 12 | 14 | 3   | 3  (Set)  |
| 14 | 14 | 1   | 3         |
| 14 | 15 | 6   | 3         |
| 15 | 15 | 5   | 3         |

## Optimal - optimised

1) Two pointers i and j
2) Shrink i until sum >= k or i and j are at same position (starting a new journey from j)
3) Check sum and do the math
4) Increment
5) Add to sum
6) Repeat 2 to 5 until j < nums.length

## Time Complexity

May seem like O(n^2) as it has two loops
But technically,
j will move n times in the outer loop
i will move n times overall in the inner loop.
Meaning, if we count how times inner while loop ran, it'll not exceed n.
Bcz i starting from 0, will increment 1 by 1 and until j.
So it's state is not linked with outer loop, it has different state irrespective of the outer loop.
For ex, in the case where no subarray can equal k, then j and i will increment for 2*n times.
So it's O(2 * n) -> O(n)