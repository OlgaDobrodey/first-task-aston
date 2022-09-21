package com.java.map;

/**
 * This is my HashMap interface
 *
 * @author Dobrodey Olga
 */public interface MyHashMap<K, V> {

    void clear();

    boolean isEmpty();

    V get(Object key);

    V put(K key, V value);

    int size();

    V remove(Object key);

    Object[] sort();


}
