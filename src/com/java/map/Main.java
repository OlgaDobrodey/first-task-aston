package com.java.map;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        MyHashMap<Integer, String> hashMap = new MyHashMapImpl<>();
        //Put elements
        hashMap.put(2, "Apple");
        hashMap.put(1, "Orange");
        // Put two elements with one key
        hashMap.put(79, "Grape");
        hashMap.put(79, "Grape2");
        hashMap.put(88, "Tomato");
        // Put three elements to one basket
        hashMap.put(10, "Tomato2");
        hashMap.put(30, "Tomato2");
        hashMap.put(20, "Tomato3");
        //Put two elements with one null
        hashMap.put(null, "Pear");
        hashMap.put(null, "Pear2");
        System.out.println("Size = " + hashMap.size());
        System.out.println("HashMap = " + hashMap);
//      Size = 8
//      HashMap = [null=Pear2, 10=Tomato2, 30=Tomato2, 20=Tomato3, 1=Orange, 2=Apple, 88=Tomato, 79=Grape2]

        //Get elements
        System.out.println("Val at 79 " + hashMap.get(79));
        System.out.println("Val at 1 " + hashMap.get(1));
        System.out.println("Val at 10 " + hashMap.get(10));
        System.out.println("Val at 20 " + hashMap.get(20));
        System.out.println("Val at 2 " + hashMap.get(2));
        System.out.println("Val at null " + hashMap.get(null));
        System.out.println("Val at 40 " + hashMap.get(40));

        //Sort HashMap
        System.out.print("Sort list key = ");
        Arrays.stream(hashMap.sort())
                .forEach(key -> System.out.print(key + " "));
        System.out.println();

        // Remove
        hashMap.remove(2);
        hashMap.remove(30);
        hashMap.remove(null);
        System.out.println("HashMap after remove = " + hashMap);
        System.out.println("Size = " + hashMap.size());
//      HashMap after remove = [10=Tomato2, 20=Tomato3, 1=Orange, 88=Tomato, 79=Grape2]
//      Size = 5

        //Clean HashMap
        hashMap.clear();
        System.out.println("HashMap after clean " + hashMap);
        System.out.println("isEmpty = " + hashMap.isEmpty());
//      HashMap after clean []
//      isEmpty = true
    }
}
