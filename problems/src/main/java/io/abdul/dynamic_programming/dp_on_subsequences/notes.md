1) Subsequence vs subset vs subarray
2) Count
   Positive base case 1, negative base case 0
3) Minimize
   Positive base case the value, negative base case possible max. Make sure this max doesn't overflow and become the
   result.
4) Maximize
   Positive base case the value, negative base case possible min. Make sure this min doesn't underflow and become the
   result.
5) Same element can be picked as much as possible
   When can be picked, stay on same position (i). If can't be picked move to next (i+1)
6) Take or skip
   Skip (i+1), take only if possible (i+1)
7) Base case
   Try to keep it in the bounds