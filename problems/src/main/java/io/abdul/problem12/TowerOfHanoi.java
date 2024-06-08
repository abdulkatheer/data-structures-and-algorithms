package io.abdul.problem12;

/**
 * Tower of Hanoi is a mathematical puzzle where we have three rods (A, B, and C) and N disks. Initially, all the disks
 * are stacked in decreasing value of diameter i.e., the smallest disk is placed on the top and they are on rod A.
 * The objective of the puzzle is to move the entire stack to another rod (here considered C), obeying the following simple rules:
 * <p>
 * Only one disk can be moved at a time.
 * Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack
 * i.e. a disk can only be moved if it is the uppermost disk on a stack.
 * No disk may be placed on top of a smaller disk.
 * <p>
 * Examples:
 * <p>
 * Input: 2
 * Output: Disk 1 moved from A to B
 * Disk 2 moved from A to C
 * Disk 1 moved from B to C
 * <p>
 * Input: 3
 * Output: Disk 1 moved from A to C
 * Disk 2 moved from A to B
 * Disk 1 moved from C to B
 * Disk 3 moved from A to C
 * Disk 1 moved from B to A
 * Disk 2 moved from B to C
 * Disk 1 moved from A to C
 * <p>
 * <p>
 * <p>
 * Analysis of Recursion
 * <p>
 * Recursive Equation : T(n) = 2T(n-1) + 1     ——-equation-1
 * <p>
 * Solving it by Back-substitution :
 * T(n-1) = 2T(n-2) + 1     ———–equation-2
 * T(n-2) = 2T(n-3) + 1     ———–equation-3
 * <p>
 * Put the value of T(n-2) in the equation–2 with help of equation-3
 * T(n-1)= 2( 2T(n-3) + 1 ) + 1     ——equation-4
 * <p>
 * Put the value of T(n-1) in equation-1 with help of equation-4
 * T(n)= 2( 2( 2T(n-3) + 1 ) + 1 ) + 1
 * T(n) = 2^3 T(n-3) + 2^2 + 2^1 + 1
 * <p>
 * After Generalization :
 * T(n)= 2^k T(n-k) + 2^{(k-1)} + 2^{(k-2)} + ............ +2^2 + 2^1 + 1
 * <p>
 * Base condition T(1) =1
 * n – k = 1
 * k = n-1
 * put, k = n-1
 * T(n) =2^{(n-1)}T(1) + + 2^{(n-2)} + ............ +2^2 +2^1 + 1
 * <p>
 * It is a GP series, and the sum is 2^n - 1
 * <p>
 * T(n)= O( 2^n - 1)     , or you can say O(2^n)     which is exponential
 * <p>
 * for 5 disks i.e. n=5 It will take 2^5-1=31 moves.
 *
 *
 */
public class TowerOfHanoi {
    private static void move(int n, char fromRod, char toRod, char auxRod) {
        if (n == 0) {
            return;
        }
        move(n - 1, fromRod, auxRod, toRod);
        System.out.println("Moving rod " + n + " from " + fromRod + " to " + toRod);
        move(n - 1, auxRod, toRod, fromRod);
    }

    public static void main(String[] args) {
        move(5, 'A', 'C', 'B');
    }
}
