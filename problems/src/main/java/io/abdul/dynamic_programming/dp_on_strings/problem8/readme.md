Difference between MinInsertionsToConvertAtoB vs EditDistance:
1) MinInsertionsToConvertAtoB has two operations delete & insert
So whatever is extra in str1 has to be removed and whatever extra in str2 has to be inserted.
So LCS finding will help here.
2) EditDistance has three operations replace, insert, delete. 
So LCS will not help here.

abcdefghij
acegikmoqs

MinInsertionsToConvertAtoB:
LCS - a c e g i (5)
Extra in str1 - b d f h j
Extra in str2 - k m o g s
So result = (str1.length - 5) + (str2.length - lcs)

EditDistance:
First instinct, find LCS and take the max of diff considering replacements.
LCS - a c e g i (5)
Extra in str1 - b d f h j
Extra in str2 - k m o g s
Result = Max ((str1.length - 5), (str2.length - lcs)) = 5
We thought that these characters can be just replaced with each other. But they're not in the sequence to be replaced.

True solution:
Match 1 by 1 and pick best of three options available

a a - match
b c - no match, Options[Insert c, Delete b, Replace b with c], best option Delete b
c c - match
d e - no match, Options[Insert e, Delete d, Replace d with e], best option Delete d
e e - match
f g - no match, Options[Insert g, Delete f, Replace f with g], best option Delete f
g g - match
h i - no match, Options[Insert i, Delete h, Replace h with i], best option Delete h
i i - match
j k - no match, Options[Insert k, Delete j, Replace j with k], best option Replace j with k
Remaining - m o g s - Inserted

Total - 9 operations
