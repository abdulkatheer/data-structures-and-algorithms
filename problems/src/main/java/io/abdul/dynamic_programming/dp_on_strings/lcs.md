## Longest Common Subsequence

Subsequence - Picking any element, by only optionally skipping 1 or more elements in between. Selected elements should
appear in same order as in original collection.
Input => 1 2 3
Subsequences - 2^n = 8
1
1 2
1 3
1 2 3
2
2 3
3
{} - empty

This is based on the subsets problem. (Take or skip)
In LIS, we take only if it's greater than previously taken - Optional take
In Longest Divisible Subset, we take only if it's divisible by last taken

Ultimately, we'll have 2^n combinations when we have two options take or skip.
We limit the options by conditions.

Similarly, this problem states a condition that the character from 2 substring should match. Like flames, but respect
the character ordering.
So we take when character matches, if not we've two skip options. Skip character from str1 and skip from str2.
fce
bdc

At pos 0, as both are not matching, we've two options. Compare f and d, Compare b and c.

But wait, we generally take the max of taken, skipped result right. But here if its taken why are we returning as is
without checking values of skip.

In a string pair, there's only one optimal solution that x characters match.

So when a match found, we add 1 to the result and find next match.
So when a match found, if we also skip and calculate the result, it'll always be lesser than or equals the taken one.
Bcz we
missed the current match, that match may happen in future or not. See below example. When b and b matched, we take that.
If we skip we have two options (b and b, x and b). Here b and b gives same answer, x and b gives 1 lesser (as we missed
current match)
This is why we can just ignore skip option when there is a match. Even if do Math.max(take, skip) it'll just work fine,
but takes more running time.

### Longest Common Substring

Substring / Subarray - Picking any list of contiguous elements in the collection. We can skip any part of the array. But
we can skip the elements in between.
Input => 1 2 3 4 5
Subarrays
1
1 2
1 2 3
1 2 3
1 2 3 4
1 2 3 4 5
2
2 3
2 3 4
2 3 4 5
3
3 4
3 4 5
4
4 5
5
{} - empty

This is similar to Longest Common Subsequence, but need to be contiguous. We can count as long as we see a match, and we
need to restart counting when a mismatch found.

Here we do take if match
Skip for sure (two skips)
Take max of all three.

Why don't we just take and ignore skip when match found as we did in Longest Common Subsequence?
In Subsequence, we don't need contiguous. So any matching element is considered in the result and can't be ignored in
any case. So skip will always be at least 1 less than take.
In Substring, we need contiguous. So there can be multiple Substrings possible, but one (or more) of them will be the
optimal solution. If we take due to a match, it's possible that the taken element will not be in result due to shorter
Substring length. As it's not cent percent sure that the taken element will be in result, we need to assess the length
of skips as well and take max of 3.

a b x d e f
a b b x d e f

a and b are matching. So at b, if I just stop move both cursor to x and b, we'll never find x d e f combination.
So we need to skip and move like (b and b OR b and X). Here b and b will give the optimal solution.

Same example in Subsequence
a and b matched - 2 counts
Move both to x and b
Now two options x and x, d and b
x and x will give count 4
and optimal is 6

Here 2 is counted, so even if we try skip and max of them, answer will not exceed taken.

---
Final words, in Subsequence, there is only one optimal solution. Meaning when a character matches, that'll definitely be
a part of optimal solution. If we miss we may or may not find the solution. But if we find, that'll be same solution as
taken.
a b x d e f
a b b x d e f
Optimal - a b x d e f, irrespective of skip or don't skip during match, will give same solution
In Subset, there can be multiple solutions, and 1 or more optimal solutions. So when we pick an element due to a match,
that may or may not be part of the final solution. So we need to skip and try other possibilities as well.
a b x d e f
a b b x d e f
Optimal - b x d e f, the initial match a b is not in the final solution

---

## Longest Palindromic Subsequence

A palindrome can't be built incrementally.
anna
you can't a, n, n, a. You can only validate in full and not partially.

So we can build by expanding both end
n n - palindrome
a n n a - palindrome
This way we can validate at each point of building them.

Brute - get all combinations and check if they're palindrome at the end - O(n * 2^n)

We can use LCS here.
If we take original string and reversal of it, the LCS of them is the LPS. You can keep the original array itself and play with indexes (like for second string consider from n-1 to 0)
