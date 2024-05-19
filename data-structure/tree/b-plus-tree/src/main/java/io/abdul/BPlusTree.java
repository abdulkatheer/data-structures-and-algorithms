package io.abdul;

import io.abdul.api.Tree;

import java.util.List;
import java.util.Optional;

public class BPlusTree <E extends Comparable<E>> implements Tree<E> {
    @Override
    public void insert(E element) {

    }

    @Override
    public Optional<E> lookup(E element) {
        return Optional.empty();
    }

    @Override
    public boolean remove(E element) {
        return false;
    }

    @Override
    public void empty() {

    }

    @Override
    public List<E> getElements() {
        return List.of();
    }

    @Override
    public int size() {
        return 0;
    }
}
