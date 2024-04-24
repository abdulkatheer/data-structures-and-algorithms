package io.abdul.constant_time;

public class Example1 {
    public static void main(String[] args) {
        printNthCharacter(new char[] {'A', 'B', 'D', 'U', 'L'}, 3);
    }

    private static void printNthCharacter(char[] data, int location) {
        int a = 10;
        int b = 20;
        if (a != b) {
            System.out.println("Dummy");
        }
        System.out.printf("Character at location %d is %c", location, data[location - 1]);
    }
}
