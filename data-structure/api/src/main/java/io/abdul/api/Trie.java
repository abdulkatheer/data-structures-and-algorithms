package io.abdul.api;

import java.util.List;

public interface Trie {
    void insert(String element);

    boolean lookup(String element);

    List<String> elementsStartsWith(String element);
}
