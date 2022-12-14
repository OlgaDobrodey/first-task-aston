package com.java.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This is my HashMap implementation
 *
 * @author Dobrodey Olga
 */
public class MyHashMapImpl<K, V> implements MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 11;
    private int size = 0;
    private Node<K, V>[] table;

    public MyHashMapImpl() {
        this.table = new Node[DEFAULT_CAPACITY];
    }

    private int Hashing(int hashCode) {
        int location = hashCode % (DEFAULT_CAPACITY - 1);
        return location + 1;
    }

    /**
     * @return true if hash map is Empty
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Remove all elements
     */
    @Override
    public void clear() {
        Node<K, V>[] tab;
        if ((tab = table) != null && size > 0) {
            size = 0;
            Arrays.fill(tab, null);
        }
    }

    /**
     * Getting value for element key
     *
     * @param key - element key
     * @return value
     */
    @Override
    public V get(Object key) {
        V ret = null;
        if (key == null) {
            ret = getNodeWithKeyNull().getVal();
        } else {
            int location = Hashing(key.hashCode());
            Node<K, V> e = null;
            try {
                e = table[location];
            } catch (NullPointerException ex) {
            }
            if (e != null) {
                ret = getNode(e, key);
            }
        }
        return ret;
    }

    private Node<K, V> getNodeWithKeyNull() {
        Node<K, V> e = null;
        try {
            e = table[0];
        } catch (NullPointerException ex) {
        }
        return e;
    }

    private V getNode(Node<K, V> node, Object key) {
        V ret = null;
        if (node.getKey().equals(key)) {
            ret = node.getVal();
        } else if (node.getNext() != null) {
            ret = getNode(node.getNext(), key);
        }
        return ret;
    }

    /**
     * Adding element to HashMap
     *
     * @param key   - element key
     * @param value - element value
     * @return - element value
     */
    @Override
    public V put(K key, V value) {
        V resultValue;
        if (key == null) {
            resultValue = putForNullKey(value);
        } else {
            int location = Hashing(key.hashCode());
            Node<K, V> nodeInLocation = null;
            try {
                nodeInLocation = table[location];
            } catch (NullPointerException ex) {

            }
            if (nodeInLocation != null) {
                if (nodeInLocation.getKey().equals(key)) {
                    nodeInLocation.setVal(value);
                    resultValue = nodeInLocation.getVal();
                } else {
                    Node<K, V> kvNode = table[location];
                    resultValue = createdNodeForLocation(key, value, kvNode);
                    size++;
                }
            } else {
                Node<K, V> eNew = new Node<>(key.hashCode(), key, value, null);
                table[location] = eNew;
                resultValue = eNew.getVal();
                size++;
            }
        }
        return resultValue;
    }

    private V createdNodeForLocation(K key, V value, Node<K, V> kvNode) {
        V resultValue;
        if (kvNode.getNext() == null) {
            Node<K, V> newNode = new Node<>(key.hashCode(), key, value, null);
            kvNode.setNext(newNode);
            resultValue = newNode.getVal();
        } else {
            resultValue = createdNodeForLocation(key, value, kvNode.getNext());
        }

        return resultValue;
    }

    private V putForNullKey(V val) {
        Node<K, V> newNode = null;
        V resultValue = null;
        try {
            newNode = table[0];
        } catch (NullPointerException ex) {

        }
        if (newNode != null && newNode.getKey() == null) {
            resultValue = newNode.getVal();
            newNode.setVal(val);
        } else {
            Node<K, V> eNew = new Node<>(0, null, val, null);
            table[0] = eNew;
            size++;
        }
        return resultValue;
    }

    /**
     * @return hash map size
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove element with key
     *
     * @param key - element key
     * @return value element
     */
    @Override
    public V remove(Object key) {
        V resultValue = null;
        if (key == null) {
            Node<K, V> e = null;
            try {
                e = table[0];
            } catch (NullPointerException ex) {

            }
            if (e != null) {
                table[0] = null;
                size--;
                return e.getVal();
            }
        } else {
            int location = Hashing(key.hashCode());
            Node<K, V> e = null;
            try {
                e = table[location];
            } catch (NullPointerException ex) {
            }
            if (e != null) {
                if (e.getKey().equals(key)) {
                    resultValue = e.getVal();
                    table[location] = null;
                    size--;
                } else if (e.getNext() != null) {
                    resultValue = removeNode(e, e.getNext(), key);
                }
            }
        }
        return resultValue;
    }

    private V removeNode(Node<K, V> node, Node<K, V> nodeNext, Object key) {
        V ret = null;
        if (nodeNext.getKey().equals(key)) {
            ret = nodeNext.getVal();
            if (nodeNext.getNext() != null) {
                node.setNext(nodeNext.getNext());
            } else {
                node.setNext(null);
            }
            size--;
        } else if (nodeNext.getNext() != null) {
            ret = removeNode(nodeNext, nodeNext.getNext(), key);
        }
        return ret;
    }

    /**
     * Sorting an array of map keys
     *
     * @return array
     */
    @Override
    public Object[] sort() {
        final List<K> setKey = new ArrayList<>();
        int counterKey = 0;
        if (table[0].getKey() == null)
            for (Node<K, V> kvNode : table) {
                if (kvNode != null) {
                    counterKey = getCounterKey(setKey, counterKey, kvNode);
                }
            }
        List<K> set = setKey.stream()
                .filter(Objects::nonNull)
                .sorted((key1, key2) -> ((Comparable) key1).compareTo(key2)).collect(Collectors.toList());

        if (setKey.size() > set.size()) {
            set.add(null);
        }
        return set.toArray(new Object[size]);
    }

    private int getCounterKey(List<K> setKey, int counterKey, Node<K, V> node) {
        setKey.add(counterKey++, node.getKey());
        if (node.getNext() != null) {
            getCounterKey(setKey, counterKey, node.getNext());
        }
        return counterKey;
    }


    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            StringBuilder result = new StringBuilder();
            result.append("[");
            for (Node<K, V> kvNode : table) {
                result.append(printNodeForOneLocation(kvNode));
            }
            result.replace(result.length() - 2, result.length(), "]");
            return result.toString();
        }
    }

    private String printNodeForOneLocation(Node<K, V> findNode) {
        String string = "";
        try {
            if (findNode.next != null) {
                Node<K, V> node = findNode.getNext();
                string = findNode + ", " + printNodeForOneLocation(node);
            } else string = findNode + ", ";
        } catch (NullPointerException e) {
        }
        return string;
    }

    /**
     * HashMap element class
     *
     * @param <K> ??? the type of keys maintained by this map
     * @param <V> ??? the type of mapped values
     */
    static class Node<K, V> {
        int hash;
        K key;
        V val;
        Node<K, V> next;

        Node(int hash, K key, V val, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }

        public void setVal(V val) {
            this.val = val;
        }

        @Override
        public int hashCode() {
            int prime = 13;
            int mul = 11;
            if (key != null) {
                return prime * mul + key.hashCode();
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !this.getClass().getName().equals(o.getClass().getName())) {
                return false;
            }
            Node e = (Node) o;
            return this.key == e.key;
        }

        @Override
        public String toString() {
            return key + "=" + val;
        }
    }
}


