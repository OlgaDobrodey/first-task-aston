package com.java.list;

public class Main {
    public static void main(String[] args) {
        // Create new MyArrayListImpl with capacity equals 2
        MyArrayList<Integer> list = new MyArrayListImpl<>(2);

        //Add
        list.add(1);
        list.add(100);
        list.add(12);
        list.add(2, 4);
        list.addAll(list);
        System.out.println("List after added list = " + list);

        //Get
        System.out.println("Element with index =4 - element = " + list.get(4));

        //Remove
        System.out.println("Remove element with index = 4 " + list.remove(4));
        System.out.println("List after remove - list = " + list);

        //Sort
        System.out.println("List after QuickSort - list = " + new SortImpl<Integer>().quickSort(list));

        //Clean
        list.clear();
        System.out.println("Clean list " + list);
    }
}
