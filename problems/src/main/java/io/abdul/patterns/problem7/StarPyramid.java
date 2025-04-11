package io.abdul.patterns.problem7;

// https://takeuforward.org/pattern/pattern-7-star-pyramid/
public class StarPyramid {
    public static void main(String[] args) {
        pattern(6);
        patternV2(6);
    }

    private static void pattern(int n) {
        // Two patterns combined
        // n-1 left angled triangle + n right angled triangle
        for (int i = 0; i < n; i++) {
            StringBuilder line = new StringBuilder();

            // n-1 left angled triangle
            int start = n - i;
            if (start <= n - 1) { // Will ignore for first iteration as end is n-1
                for (int k = 0; k < start - 1; k++) { // 0 to start-1
                    line.append("  ");
                }
                for (int j = start - 1; j < n - 1; j++) { // start to n-1
                    line.append("* ");
                }
            } else {
                for (int j = 0; j < n - 1; j++) {
                    line.append("  ");
                }
            }

            // n right angled triangle
            for (int x = 0; x <= i; x++) {
                line.append("* ");
            }
            line.append("\n");
            System.out.println(line);
        }
    }

    /*
    Start count -> 2*i + 1, 2*0+1 (1), 2*1+1 (3),2*2+1 (5) ...
    Space count -> given n=5; i=0 4 space before, star, 4 space after; i=1 3 space before , stars, 3 space after; So n-i-1
     */
    private static void patternV2(int n) {
        for (int i = 0; i < n; i++) {
            int numOfSpaces = n - i - 1;
            int numOfStars = 2 * i + 1;

            for (int j = 0; j < numOfSpaces; j++) {
                System.out.print(" ");
            }

            for (int k = 0; k < numOfStars; k++) {
                System.out.print("*");
            }

            for (int j = 0; j < numOfSpaces; j++) {
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
