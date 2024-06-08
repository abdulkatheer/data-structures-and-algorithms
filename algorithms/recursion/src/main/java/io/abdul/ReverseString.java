package io.abdul;

public class ReverseString {
    private static String reverse(String original) {
        if (original.length() == 1) {
            return original;
        }
        return original.substring(original.length() - 1) + reverse(original.substring(0, original.length() - 1));
    }

    public static void main(String[] args) {
        System.out.println(reverse("k"));
        System.out.println(reverse("ka"));
        System.out.println(reverse("kat"));
        System.out.println(reverse("kath"));
        System.out.println(reverse("kathe"));
        System.out.println(reverse("kathee"));
        System.out.println(reverse("katheer"));
    }
}
