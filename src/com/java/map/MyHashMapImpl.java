package com.java.map;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        Node<K, V>[] tab;
        if ((tab = table) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }

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

    @Override
    public V put(K key, V value) {
        V resultValue = null;
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
                Node<K, V> eNew = new Node<K, V>(key.hashCode(), key, value, null);
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
            Node<K, V> eNew = new Node<K, V>(0, null, val, null);
            table[0] = eNew;
            size++;
        }
        return resultValue;
    }

    @Override
    public int size() {
        return size;
    }

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
                nodeNext = null;
                node.setNext(nodeNext);
            }
            size--;
        } else if (nodeNext.getNext() != null) {
            ret = removeNode(nodeNext, nodeNext.getNext(), key);
        }
        return ret;
    }

    @Override
    public Object[] sort() {
        final List<K> setKey = new ArrayList<>();
        int counterKey = 0;
        if (table[0].getKey() == null)
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    counterKey = getCounterKey(setKey, counterKey, i, table[i]);
                }
            }
        List<K> set = setKey.stream()
                .filter(k -> k != null)
                .sorted((key1, key2) -> ((Comparable) key1).compareTo(key2)).collect(Collectors.toList());

        if (setKey.size() > set.size()) {
            set.add(null);
        }
        return set.toArray(new Object[size]);
    }

    private int getCounterKey(List<K> setKey, int counterKey, int i, Node<K, V> node) {
        setKey.add(counterKey++, node.getKey());
        if (node.getNext() != null) {
            getCounterKey(setKey, counterKey, i, node.getNext());
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
            for (int i = 0; i < table.length; i++) {
                result.append(printNodeForOneLocation(table[i]));
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

    class Node<K, V> {
        int hash;
        K key;
        V val;
        Node<K, V> next;

        public Node() {
        }

        Node(int hash, K key, V val, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.val = val;
            this.next = next;
        }

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
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

        public void setKey(K key) {
            this.key = key;
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
                int hashCode = prime * mul + key.hashCode();
                return hashCode;
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass().getName() != o.getClass().getName()) {
                return false;
            }
            Node e = (Node) o;
            if (this.key == e.key) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return key + "=" + val;
        }
    }
}


