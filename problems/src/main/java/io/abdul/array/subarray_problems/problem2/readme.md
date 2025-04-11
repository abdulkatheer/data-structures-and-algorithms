# Brute-force approach

Find all possible subarray sums for 1D array n(n+1) / 2
Match and get count

# DP approach

Similar to the Largest Subarray Sum Equals K method (including all numbers)
We need to store count instead of index as there may be multiple occurrences of same number which may result in multiple
subarray

Ex 1:
0 0 0 0 and k=0
0 00 000 0000
0 00 000
0 00
0

Ex 2:
1 2 -3 0 3 -3 1 1 1

1 2
1 2 -3 0 3
0 3
3
1 2 -3 0 3 -3 1 1 1 (all before 1 1 1 forms zero)
3 -3 1 1 1
1 1 1
-3 0 3 3 -3 1 1 1