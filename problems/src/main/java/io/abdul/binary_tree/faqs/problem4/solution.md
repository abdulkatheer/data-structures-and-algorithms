## Why recursion / traversal of left and right boundary doesn't work?

Consider below tree

```
              1
            /   \
         48     null
        /  \     
     null   64
           /   \
        -15     93
        /  \
    null  null
      /
   -45
   /  \
 33  null

```

93 is in top view, but not in right boundary!

## Approach

Level order traversal and picking the top element in each level from left to right and top to
bottom.
Que says if 2 nodes appear in the same position and also visible from top, consider left one.
Meaning, we need to process from left to right.

Assume drawing a vertical line of each node level from left to right. In that line whoever comes
first is our answer.
Sometimes two nodes might cross, we'll take the left one (first added one)