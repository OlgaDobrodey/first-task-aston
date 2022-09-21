package com.java.list;

public class Main {
    public static void main(String[] args) {
        // Create new MyArrayListImpl with capacity equals 2
        MyArrayList<Integer> list = new MyArrayListImpl<>(2);

        list.add(1);
        list.add(100);
        list.add(12);
        list.add(2, 4);
        list.addAll(list);
        System.out.println("List after added list = " + list);

        System.out.println("Element with index =4 - element = " + list.get(4));
        System.out.println("Remove element with index = 4 " + list.remove(4));
        System.out.println("List after remove - list = " + list);

        //Sort
        list.quickSort();
        System.out.println("List after QuickSort - list = " + list);
        list.clear();
        System.out.println("Clean list " + list);
    }
}
