package io.abdul.array.faq_hard.problem3;

import java.util.Arrays;
import java.util.HashSet;

// https://takeuforward.org/plus/dsa/arrays/faqs-hard/find-the-repeating-and-missing-number
/*
AP = n(n-1) / 2 => 0-5 (6 elements) = 15 => 0 + 1 + 2 + 3 + 4 + 5
We either need to find the repeating number or missing number to solve this
 */
public class RepeatingAndMissingNum {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
//        Solution3 solution = new Solution3();
        Solution4 solution = new Solution4();
        System.out.println(Arrays.toString(solution.findMissingRepeatingNumbers(new int[]{1, 2, 3, 4, 3, 6})));
        System.out.println(Arrays.toString(solution.findMissingRepeatingNumbers(new int[]{3, 5, 4, 1, 1})));
        System.out.println(Arrays.toString(solution.findMissingRepeatingNumbers(new int[]{1, 2, 3, 6, 7, 5, 7})));
        System.out.println(Arrays.toString(solution.findMissingRepeatingNumbers(new int[]{6, 5, 7, 1, 8, 6, 4, 3, 2})));
        System.out.println(Arrays.toString(solution.findMissingRepeatingNumbers(new int[]{1, 1})));
        System.out.println(Arrays.toString(solution.findMissingRepeatingNumbers(new int[]{2, 2, 1})));
        System.out.println(Arrays.toString(solution.findMissingRepeatingNumbers(new int[]{1, 3, 5, 6, 1, 2, 4})));
    }
}

/*
Brute - Find repeating number by checking number one by one
T - O(n^2)
S - O(1)
 */
class Solution {
    public int[] findMissingRepeatingNumbers(int[] nums) {
        if (nums.length < 2) {
            return new int[]{-1, -1};
        }

        boolean found = false;
        int repeatingNumber = -1;
        for (int i = 1; i <= nums.length; i++) {
            int count = 0;
            for (int num : nums) {
                if (num == i) {
                    count++;
                    if (count == 2) {
                        found = true;
                        repeatingNumber = i;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
        }

        int actualSum = 0;
        for (int num : nums) {
            actualSum = actualSum + num;
        }

        if (repeatingNumber == -1) { // Not found case
            return new int[]{-1, -1};
        }

        int n = nums.length;
        int expectedSum = (n * (n + 1)) / 2;

        actualSum = actualSum - repeatingNumber;
        int missingNumber = expectedSum - actualSum;

        return new int[]{repeatingNumber, missingNumber};
    }
}

/*
Brute - Find repeating number by checking number one by one
T - O(n^2)
S - O(1)
 */
class Solution5 {
    public int[] findMissingRepeatingNumbers(int[] nums) {
        if (nums.length < 2) {
            return new int[]{-1, -1};
        }

        boolean found = false;
        int repeatingNumber = -1;
        int missingNumber = -1;
        for (int i = 1; i <= nums.length; i++) {
            int count = 0;
            for (int num : nums) {
                if (num == i) {
                    count++;
                    if (count == 2) {
                        found = true;
                        repeatingNumber = i;
                        break;
                    }
                }
            }
            if (found) {
                break;
            }
            if (count == 0) {
                missingNumber = i;
            }
        }

        return new int[]{repeatingNumber, missingNumber};
    }
}

/*
Better - Sort and find repeating number faster
T - O(n logn)
S - O(1)
 */
class Solution2 {
    public int[] findMissingRepeatingNumbers(int[] nums) {
        if (nums.length < 2) {
            return new int[]{-1, -1};
        }
        Arrays.sort(nums);

        int repeatingNumber = -1;
        int actualSum = nums[0];
        int n = nums.length;
        for (int i = 1; i < n; i++) {
            actualSum = actualSum + nums[i];
            if (nums[i] == nums[i - 1]) {
                repeatingNumber = nums[i];
            }
        }
        if (repeatingNumber == -1) {
            return new int[]{-1, -1};
        }

        int expectedSum = (n * (n + 1)) / 2;
        int missingNumber = expectedSum - (actualSum - repeatingNumber);
        return new int[]{repeatingNumber, missingNumber};
    }
}

/*
Better - Store in HashSet for faster lookup
T - O(n)
S - O(n)
 */
class Solution3 {
    public int[] findMissingRepeatingNumbers(int[] nums) {
        if (nums.length < 2) {
            return new int[]{-1, -1};
        }

        int n = nums.length;
        HashSet<Integer> numsSet = new HashSet<>();
        int repeatingNumber = -1;
        int actualSum = 0;
        for (int num : nums) {
            if (numsSet.contains(num)) {
                repeatingNumber = num;
            }
            actualSum = actualSum + num;
            numsSet.add(num);
        }

        if (repeatingNumber == -1) {
            return new int[]{-1, -1};
        }

        int expectedSum = (n * (n + 1)) / 2;
        int missingNumber = expectedSum - (actualSum - repeatingNumber);
        return new int[]{repeatingNumber, missingNumber};
    }
}

/*
Optimal - Find repeating/missing number in linear time without additional space
Mathematical equation approach
T - O(n)
S - O(1)

1 2 3 3 4 6 -- 1 2 3 4 5 6 --After Cancellation--> 3 == 5
Left side is repeating number, right side is missing number
sumActual - sumExpected = repeatingNumber - missingNumber
Let repeatingNumber be X and missingNumber be Y
X - Y = sumActual - sumExpected
How to get X and Y?
We need another equation to find this.

sumOfSquaresActual -- sumOfSquaresExpected
1^2 2^2 3^2 3^2 4^2 6^2 -- 1^2 2^2 3^3 4^2 5^2 6^2
3^2 -- 5^2
sumOfSquaresActual -- sumOfSquaresExpected == X^2 - Y^2

X^2-Y^2 = 9-25 = -16
X-Y = -2; -- (1)

X^2 - Y^2 = (X+Y) * (X-Y)
-16 = (X+Y) * -2
X+Y = -16/-2
X+Y = 8 -- (2)

X + Y + (X - Y) = 8 + (-2) = 6 => X + Y + X - Y = 6
2X = 6
X = 6/2 = 3; -- (3)
X + Y = 8;
Y = 8-X = 8-3 = 5 -- (4)

Sum of n natural numbers = ( n * ( n + 1) ) / 2
Sum of squares of n natural numbers = (n ( n + 1 ) ( 2n + 1 ) ) / 6
 */
class Solution4 {
    public int[] findMissingRepeatingNumbers(int[] nums) {
        if (nums.length < 2) {
            return new int[]{-1, -1};
        }

        int n = nums.length;
        int x_minus_y = 0;
        int xsquare_minus_ysquare = 0;

        for (int i = 0; i < n; i++) {
            int naturalNum = i + 1;
            int actualNum = nums[i];
            x_minus_y = x_minus_y + (actualNum - naturalNum); // Minusing every time to avoid having larger nums and int overflow
            xsquare_minus_ysquare = xsquare_minus_ysquare + ((actualNum * actualNum - naturalNum * naturalNum));
        }

        int x_plus_y = xsquare_minus_ysquare / x_minus_y;

        int x = (x_plus_y + x_minus_y) / 2;
        int y = x_plus_y - x;

        return new int[]{x, y};
    }
}