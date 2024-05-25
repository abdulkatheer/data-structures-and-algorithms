package io.abdul;

import io.abdul.api.Trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TrieWithArray implements Trie {
    private static final char BASE_CHAR = '-';
    private static final char END_OF_WORD_CHAR = '.';

    private final Node root = new Node(BASE_CHAR, false);

    @Override
    public void insert(String element) {
        char[] chars = element.toCharArray();
        Node current = root;
        for (char aChar : chars) {
            Node child = current.children[aChar - 96];
            if (child == null) {
                current.children[aChar - 96] = new Node(aChar, false);
            }
            current = current.children[aChar - 96];
        }
        current.children[0] = new Node(END_OF_WORD_CHAR, true);
    }

    @Override
    public boolean lookup(String element) {
        Node current = root;
        char[] chars = element.toCharArray();
        boolean found = true;
        for (char aChar : chars) {
            Node child = current.children[aChar - 96];
            if (child == null) {
                found = false;
                break;
            }
            current = child;
        }
        return found && current.children[0] != null && current.children[0].endOfWord;
    }

    @Override
    public List<String> elementsStartsWith(String element) {
        char[] chars = element.toCharArray();

        ArrayList<Character> characters = new ArrayList<>();
        Node current = root;
        for (char aChar : chars) {
            current = current.children[aChar - 96];
            if (current == null) {
                break;
            }
            characters.add(current.c);
        }

        if (current == null) { // No elements matching with the prefix
            return Collections.emptyList();
        }

        // Now find all possible paths from current Node
        // pre-order traversal -> Visit parent, Visit entire tree of its children from left to right
        characters.remove(characters.size() - 1);
        ArrayList<String> words = new ArrayList<>();
        preorderTraversal(current, characters, words);
        System.out.println(words);
        return words;
    }

    private static void preorderTraversal(Node root, List<Character> characters, ArrayList<String> words) {
        if (root == null) { // base case
            return;
        }
        if (root.endOfWord) { // base case
            String word = characters.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));
            words.add(word);
            return;
        }
        // visit root
        characters.add(root.c);
        // visit all its children from left to right
        for (Node child : root.children) {
            preorderTraversal(child, characters, words); // recursive case
        }
        characters.remove(characters.size() - 1); // remove last character for next possible char
    }

    static class Node {
        /**
         * 0 - . (separator)
         * 1 - a (ascii - 96)
         * 2 - b
         * .
         * .
         * .
         * 26 - z
         */
        private final char c;
        private final Node[] children;
        private final boolean endOfWord;

        Node(char c, boolean endOfWord) {
            this.c = c;
            if (endOfWord) { // null node to specify an end of word
                this.children = null;
                this.endOfWord = true;
            } else {
                this.children = new Node[26];
                this.endOfWord = false;
            }
        }
    }
}
