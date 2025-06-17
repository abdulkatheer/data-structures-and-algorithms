# Defining base-case in divide and conquer based problems

In some problems, defining base-case for smallest sub-problem (the first or last element) is straightforward.
But in some cases, that itself will require recursion or complex logic similar to recursive case. And it can be just
solved by the recursive case.

So wherever defining base-case with first or last element is easy and simple, we define it and go with 0-based indexing
for dp
Otherwise let the last/first element too handled by recursive case, and use 1-based indexing to hold -1 or n-th
base-case solution.
