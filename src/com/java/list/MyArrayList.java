package com.java.list;

/**
 * This is my ArrayList interface
 *
 * @author Dobrodey Olga
 */
public interface MyArrayList<E> {
    void add(E element);

    void add(int index, E element);

    boolean addAll(MyArrayList<? extends E> arrayList);

    void ensureCapacity(int minCapacity);

    void clear();

    E get(int index);

    E remove(int index);

    int size();

    Object[] toArray();
}
