package io.abdul.maths.basic_maths.problem12;

// https://takeuforward.org/plus/dsa/beginner-problem/basic-maths/lcm-of-two-numbers
public class LCM {
    public static void main(String[] args) {
//        Solution solution = new Solution();
//        Solution2 solution = new Solution2();
        Solution3 solution = new Solution3();
        System.out.println(solution.LCM(4, 6));
        System.out.println(solution.LCM(9, 10));
    }
}

class Solution {
    public int LCM(int n1, int n2) {
        int smaller = Math.min(n1, n2);
        int maxMultiple = n1 * n2;
        int lcm = maxMultiple;
        for (int i = maxMultiple; i >= smaller; i--) {
            if (i % n1 == 0 && i % n2 == 0) {
                if (i < lcm) {
                    lcm = i;
                }
            }
        }
        return lcm;
    }
}

class Solution2 {
    public int LCM(int n1, int n2) {
        int max = Math.max(n1, n2);
        int i = 1;
        while (true) {
            int multiple = i * max;
            if (multiple % n1 == 0 && multiple % n2 == 0) {
                return multiple;
            }
            i++;
        }
    }
}

/*
Euclidean algo
LCM(n1,n2) = (n1 x n2) / GCD(n1,n2)
 */
class Solution3 {
    public int LCM(int n1, int n2) {
        return (n1 * n2) / GCD(n1, n2);
    }

    public int GCD(int n1, int n2) {
        while (n1 > 0 && n2 > 0) {
            if (n1 >= n2) {
                n1 = n1 % n2;
            } else {
                n2 = n2 % n1;
            }
        }

        return n1 == 0 ? n2 : n1;
    }
}