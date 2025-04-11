package io.abdul.array.practice.problem3;

import java.util.Arrays;

// #tag_array
public class MergeSortedArrays {
    // O(m + n)
    private static int[] merge(int[] set1, int[] set2) {
        if (set2.length == 0) {
            return set1;
        }
        if (set1.length == 0) {
            return set2;
        }

        int i = 0;
        int j = 0;
        int[] merged = new int[set1.length + set2.length];

        int k = 0;
        while (i != -1 || j != -1) {
            if (i == -1) {
                merged[k] = set2[j];
                j++;
                if (j == set2.length) {
                    j = -1;
                }
            } else if (j == -1) {
                merged[k] = set1[i];
                i++;
                if (i == set1.length) {
                    i = -1;
                }
            } else {
                if (set1[i] < set2[j]) {
                    merged[k] = set1[i];
                    i++;
                    if (i == set1.length) {
                        i = -1;
                    }
                } else {
                    merged[k] = set2[j];
                    j++;
                    if (j == set2.length) {
                        j = -1;
                    }
                }
            }
            k++;
        }

        return merged;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(merge(new int[]{0, 3, 4, 31}, new int[]{4, 6, 30})));
        System.out.println(Arrays.toString(merge(new int[]{0, 3, 4, 31}, new int[]{})));
        System.out.println(Arrays.toString(merge(new int[]{}, new int[]{4, 6, 30})));
        System.out.println(Arrays.toString(merge(new int[]{0, 0, -1, -45}, new int[]{-89, 0, -100}))); // Non-sorted
        System.out.println(Arrays.toString(merge(new int[]{-45, -1, 0, 0}, new int[]{-100, -89, 0, 0, 0}))); // Non-sorted
    }
}
