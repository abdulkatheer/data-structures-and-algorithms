1) left and right starts at 0
2) expand (right++) when condition is true (within the bounds or limits)
3) shrink (left++) to make condition to true when condition is false

When shrinking, we've two choices.

1) Shrink until the condition becomes false or out of bounds.
2) Shrink only once to keep the window size not less than the current max result window size.

Choice 1 takes 2n time, as we shrink and expand to keep the window in true mode all the time
Choice 2 takes n time. Bcz there's no point in shrinking below the current max result window size. To get a better
result than current, the window has to grow beyond current window for sure. So why shrink and expand? Just shrink once
and try to make condition true. If true then update result. If not try next pos.