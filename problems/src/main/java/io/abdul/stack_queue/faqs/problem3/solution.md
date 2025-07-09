## How to avoid precomputation of both maxes for every element?

- We only need smaller of the leftMax, rightMax for each element
- We've two pointers starting at left = 0 and right = n-1
- We always process the smaller side first. Why? this makes sure that smaller sides element are all
  smaller than the other element.
- if at any position in the iteration, arr[left] < arr[right] means
    - arr[left] and any element left to it are smaller or equals than arr[right]
    - We don't care about the elements right to arr[right]. But they'll be lesser than right as
      well.
    - To calculate trapped rainwater for arr[left], we need the leftMax. we have it.
    - We need a building on right which is equals or greater than leftMax. Yes we have it. leftMax <
      right.
    - right may not be the rightMax of arr[left], but it's just as equal/greater to hold the
      rainwater that leftMax can hold
- if at any position in the iteration, arr[right] < arr[left] means
    - arr[right] and all elements right to it are smaller or equals than arr[left]
    - We don't care about elements left to arr[left]
    - To calculate trapped rainwater for arr[right], we need the rightMax. we have it.
    - We just need a building on the left, which is equals or greater than rightMax to hold the
      entire rainwater rightMax holds.
    - As arr[left] > arr[right] and all right to it, including rightMax, we can confidently say that
      we've a building on the left which can hold all the water held by rightMax
    - arr[left] may not be the leftMax of arr[right], but it can hold the water that rightMax can
      hold

Ex: 1 8 4 5 8 20 7 6 11 3 2

left = 0 right=10
process left, leftMax = 1, total = 0

left=1 right=10
process right, rightMax=2, total=0

left=1 right=9
process right, rightMax=3, total=0

left=1 right=8
process left, leftMax=8, total=0

left=2 right=8
process left
At this point 
- arr[left] is smaller than arr[right]
- all elements left to arr[left] are smaller than arr[right]
- So the leftMax 8 is also smaller than arr[right]
- arr[left] can best hold upto 8 in the left side and that's smaller that arr[right]. So pick it up

total = 4

left=3 right=8
process left

total = 4 + 3

left=4 right=8
process left

total = 4 + 3 + 0

left=5 right=8
process right, rightMax=11

left=5 right=7
process right

At this point,
- arr[right] and all elements right to it are smaller than arr[left]
- arr[right] can best hold upto rightMax in the right side
- We've a higher building in the left, so definitely store upto rightMax

total = 4 + 3 + 0 + 5

left=5 right=6
process right

total = 4 + 3 + 0 + 5 + 4

left=5 right=5
tallest building, so can't trap anything

answer=16