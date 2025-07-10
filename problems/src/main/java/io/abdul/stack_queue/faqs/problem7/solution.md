## Ex

| r/<br/>c | 0 | 1 | 2 | 3 |
|----------|---|---|---|---|
| 0        | 0 | 1 | 1 | 0 |
| 1        | 0 | 0 | 0 | 0 |
| 2        | 1 | 1 | 0 | 0 |
| 3        | 0 | 1 | 1 | 0 |

## Brute-force - Count all knows and knownBy

The result is the candidate whose knows is 0 and knownBy is n-1

## Optimal - Eliminate impossible candidates

Start at top-left [0,3]. Bcz to eliminate / select a candidate, we need to know if he knows someone
or known by someone. If we start from right

if M[left][right] is 1

- left knows right, left can't be the celebrity. right may be a celebrity. check right's eligibility
  further.
  else
- left doesn't know right, so right can't be a celebrity. left may be a celebrity. check left's
  eligibility further.

[0,3]
0 doesn't know 3, 3 is not the celebrity and 0 may be
Eliminated 3

[0,2]
0 knows 2, 0 is not the celebrity and 2 may be
Eliminated 0

[1,2]
1 doesn't know 2, 2 is not the celebrity, but 1 may be
Eliminated 2

At this point, we could eliminate n-1 candidates and left with 1 alone.
But we're not totally sure 1 is the celebrity, so we need to make sure 1 knows no one and known by
all