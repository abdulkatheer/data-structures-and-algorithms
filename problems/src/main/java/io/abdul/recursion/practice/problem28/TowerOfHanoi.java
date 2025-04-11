package io.abdul.recursion.practice.problem28;

// https://takeuforward.org/arrays/tower-of-hanoi/
/*
Recursion - Top-down approach

Case 1) 1 disk
To move disk 1 from A to C, no disk is above it. So, move it.
Move 1 from A to C using B

Case 2) 2 disks
To move disk 2 from A to C, 1 disk is above it. So, move 1 disk recursively from A to B [Case 1]
ToH(1, fromA, toB, usingC)
Move 2 from A to C using B
Now move 1 disk from B to C [Case 1]
ToH(1, fromB, toC, usingA)

Case3) 3 disks
To move disk 3 from A to C, 2 disks are above it. So, move 2 disks recursively from A to B [Case 2]
ToH(2, fromA, toB, usingC)
Move 3 from A to C using B
Now move 2 disks from B to C [Case 2]
ToH(2, fromB, toC, usingA)

So smaller sub-problems are helping to build solution to larger sub-problems, thereby ultimate solution
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
        move(3, 'A', 'C', 'B');
    }
}
