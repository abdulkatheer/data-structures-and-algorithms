package io.abdul;

import io.abdul.api.Trie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrieWithArrayTest {

    @Test
    void insert() {
        Trie names = new TrieWithArray();
        names.insert("maria");
        names.insert("mariana");

        assertTrue(names.lookup("maria"));
        assertTrue(names.lookup("mariana"));
        assertFalse(names.lookup("mari"));
        assertFalse(names.lookup("marian"));

        List<String> elements = names.elementsStartsWith("mar");
        assertEquals(2, elements.size());
        assertEquals("maria", elements.get(0));
        assertEquals("mariana", elements.get(1));

        elements = names.elementsStartsWith("s");
        assertEquals(0, elements.size());

        elements = names.elementsStartsWith("m");
        assertEquals(2, elements.size());

        elements = names.elementsStartsWith("marian");
        assertEquals(1, elements.size());
    }
}