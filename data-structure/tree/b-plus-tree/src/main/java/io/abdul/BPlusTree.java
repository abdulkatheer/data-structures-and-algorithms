package io.abdul;

import io.abdul.api.Tree;
import io.abdul.api.exception.NotImplemented;

import java.util.List;
import java.util.Optional;

public class BPlusTree <E extends Comparable<E>> implements Tree<E> {
    @Override
    public void insert(E element) {
        throw new NotImplemented("insert() not implemented yet!");
    }

    @Override
    public Optional<E> lookup(E element) {
        throw new NotImplemented("lookup() not implemented yet!");
    }

    @Override
    public boolean remove(E element) {
        throw new NotImplemented("remove() not implemented yet!");
    }

    @Override
    public void empty() {
        throw new NotImplemented("empty() not implemented yet!");
    }

    @Override
    public List<E> getElements() {
        throw new NotImplemented("getElements() not implemented yet!");
    }

    @Override
    public int size() {
        throw new NotImplemented("size() not implemented yet!");
    }
}
