package com.java.map;

import java.util.List;

public interface MyHashMap<K, V> {

    void clear();

    boolean isEmpty();

    V get(Object key);

    V put(K key, V value);

    int size();

    V remove(Object key);

    Object[] sort();


}
