- Pre computes largest elements take additional space
- If we closely observe, we calculate result using one of the maxes left or right
- If we can strike out the impossible side, we don't need to maintain both maxes for each index
- Primary idea behind this is, we always process the smaller building first. That could be on left
  or right side. In other words, we process the element in ascending order from front and back side.
- That makes sure that the other side elements are not smaller than the chosen side. As the other
  side
  is bigger, we don't need them for calculation.
- At any point, when we process a smaller element, the element or the side's max will not be larger
  than the
  other element and the other side's max. So we can safely calculate result by just using one side's
  element and the max.

Ex: 1 4 5 8 7 6 3 2

left=0, right=7
process left 0

left=1, right=7
process right 7

left=1, right=6
process right 6

left=1, right=5
process left 1

left=2, right=5
process left 2

left=3, right=5
process right 5

left=3, right=4
process right

left=3, right=3
The largest element in the array, no one will be bigger than this. So can't trap any water here!

Elements processed in the order 1 2 3 4 5 6 7 8

When an element is processed on a side

- the element is less than the other element
- the element is greater than all the elements of the other side. Bcz they're already processed. So
  it will be less the currently processing element.
- So

1 8 4 5 8 20 7 6 11 3 2

left=0, right=10
process left 1

left=1, right=10
process right 2

left=1, right=9
process right 3

left=1, right=8
process left 8

left=2, right=8
process left 4 -> At this point, 4 is smaller than leftMax and smaller than right. left < leftMax <
right
Bcz we process smaller element first, so right can never be smaller than leftMax.
So left is in between two taller buildings, and leftMax is the definite tallest building in the left
side and that'll be the smaller max than the right max.

left=3, right=8
process left 5
At this point 5 is in between 8 and 11. where 8 is the tallest buildint to its left, 11 is one the
taller buildings on the right.
left tallest is the smaller, as we process smaller elements first.

left=4, right=8
At this points, 8 is in between 8 and 11. adds no water

left=5, right=8
process right 11

left=6, right=7
process right 6
At this point 6 is in between 20 and 11. 11 is the tallest building to its right, and 20 is one of
taller buildings on the left.
11 is the smaller tallest.

left=6, right=6
20 is the tallest of all. can't trap any water here