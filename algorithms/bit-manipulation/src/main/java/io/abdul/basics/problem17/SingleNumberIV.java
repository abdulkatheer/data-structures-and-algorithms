package io.abdul.basics.problem17;

/*
Every number appears 4 times, but only one appears once
 */
public class SingleNumberIV {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.singleNumber(new int[]{1, 1, 1, 1, 3}));
        System.out.println(solution.singleNumber(new int[]{2, 2, 2, 2, 3}));
    }
}

/*
3-bit Finite State Machine

                Number  Once    Twice   Thrice
Initial state           0000    0000    0000
                0001    0001    0000    0000 Set
                0001    0000    0001    0000 Flip
                0001    0000    0000    0001 Flip
                0001    0000    0000    0000 Reset

Set Once if Twice & Thrice is not set
Set Twice if Once & Thrice is not set
Set Thrice if Once & Twice is not set
 */
class Solution {
    public int singleNumber(int[] nums) {
        int once = 0, twice = 0, thrice = 0;
        for (int num : nums) {
            once = (once ^ num) & ~twice & ~thrice; // Add to once if not in twice & thrice
            twice = (twice ^ num) & ~once & ~thrice; // Add to twice if not in once & thrice
            thrice = (thrice ^ num) & ~once & ~twice; // Add to thrice if not in once & twice
            System.out.printf("once=%s twice=%s thrice=%s%n", Integer.toBinaryString(once), Integer.toBinaryString(twice), Integer.toBinaryString(thrice));
        }

        return once;
    }
}