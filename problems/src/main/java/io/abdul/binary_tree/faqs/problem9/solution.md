Level order traversal helps us find first and last at each level
But how do we count?

In Binary Tree Array representation,
if i is the node, then 2i + 1 is the left child and 2i + 2 is the right child

so we can number like
0
1 2
3 4 5 6
7 8 9 10 11 12 13 14
...

Then we can say the width is right - left + 1

But what is it's skewed>

0
1
3
7
...

He actual max width is 1, So if there's only one node we'll take the width as 1

PS: Iterating only the left and right boundary doesn't work as the nodes contributing the width can
be anywhere in between the boundary as well.