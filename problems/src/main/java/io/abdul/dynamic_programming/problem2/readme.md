It seems like adding odd and even and taken max of them will give result.
But take below example.

2, 1, 1, 9, 1, 1, 2

Here if we taken 2, 1 can't be taken
if we take 1, 9 can't be taken! Missing the biggest number!
2 + 1 + 1 + 2 == 6
1 + 9 + 1 = 11

Actual answer is -> 2 + 9 + 2

So, lets build from smaller sub-problems
5 -> 5
2 9 -> 9
1 10 2 -> 10
10 2 1 20 -> 30 We seem some pattern here

Bottom up thinking (Start at last house!)
So if we want to select a house for robbery, we need to see if current house + robbery from prev-to-prev house is
greater than robbery from prev house

rob(i) = Max ( rob(i-2) + v[i] , rob(i-1))

<pre>
Ex 1: 1 10 2
If I've to take 2, 2 + 1 should be greater than 10. 

Ex 2: 10 2 1 20
       0 1 2  3 
rob(3) -> Max [ 20 + rob(1), rob(2) ] -> 30
rob(1) -> Max [ 2 + 0, 10] -> 10
rob(2) -> Max [ 1 + rob(0), rob(1) ] -> 11
rob(0) -> Max [ 10 + 0, 0 ] -> 10

</pre>
