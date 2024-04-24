package io.abdul;

import java.util.HashSet;
import java.util.Set;

public class Hashtable<K, V> {
    private final Element<?, ?>[] table;

    public Hashtable(int capacity) {
        table = new Element<?, ?>[capacity];
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key should not be null");
        }
        int bucket = getBucket(key);

        Element<K, V> element = (Element<K, V>) table[bucket];

        if (element != null) {
            Element<K, V> e = (Element<K, V>) element;
            V oldValue = null;
            while (e != null) {
                if (e.key.equals(key)) {
                    V v = e.value;
                    e.value = value; // Exiting key case: replacing existing value for the key
                    oldValue = v;
                    break;
                }
                e = e.next;
            }
            if (oldValue != null) { // returns old value
                return oldValue;
            } else { // New element case: Adding new element to the head of list
                Element<K, V> newElement = new Element<>(key, value, element);
                table[bucket] = newElement;
                return null;
            }
        } else { // New element case: Creates the linked list and adds element to the head
            Element<K, V> newElement = new Element<>(key, value, null);
            table[bucket] = newElement;
            return null;
        }
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key should not be null");
        }
        int bucket = getBucket(key);

        Element<?, ?> element = table[bucket];

        if (element != null) {
            Element<K, V> e = (Element<K, V>) element;
            V value = null;
            while (e != null) {
                if (e.key.equals(key)) {
                    value = e.value;
                    break;
                }
                e = e.next;
            }
            return value;
        } else { // No list found at bucket and hence element does not exist
            return null;
        }
    }

    public Set<K> keys() {
        Set<K> keys = new HashSet<>();
        for (Element<?, ?> element : table) {
            if (element != null) {
                Element<K, V> e = (Element<K, V>) element;
                while (e != null) {
                    keys.add(e.getKey());
                    e = e.next;
                }
            }
        }
        return keys;
    }

    private int getBucket(K key) {
        int hash = key.hashCode();
        int bucket = (key.hashCode() < 0 ? ~hash : hash) % table.length;
        return bucket;
    }

    public static class Element<K, V> {
        private K key;
        private V value;
        private Element<K, V> next;

        public Element(K key, V value, Element<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public Element<K, V> getNext() {
            return next;
        }
    }
}
