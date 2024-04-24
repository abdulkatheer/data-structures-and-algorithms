package io.abdul.constant_time;

public class Example2 {
    public static void main(String[] args) {
        printFirstFiveCharacters(new char[] {'H', 'e', 'l', 'l', 'o'});
    }
    private static void printFirstFiveCharacters(char[] data) {
        int max = Math.min(data.length, 5);
        for (int i = 0; i < max; i++) {
            System.out.print(data[i]);
        }
    }
}
