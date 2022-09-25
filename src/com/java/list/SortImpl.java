package com.java.list;

import java.util.Arrays;

/**
 * Class for sort MyArrayList
 *
 * @author Dobrodey Olga
 */
public class SortImpl<E> implements Sort<E> {

    /**
     * Array E[] that contains all the elements
     */
    private E[] elementDataList;

    /**
     * Sorting - quicksort method for ArrayList <E>
     */
    @Override
    public MyArrayList<E> quickSort(MyArrayList<E> arrayList) {
        this.elementDataList = (E[]) arrayList.toArray();

        quickSortArray(elementDataList, 0, elementDataList.length - 1);

        MyArrayList<E> resultList = new MyArrayListImpl<>();
        Arrays.stream(elementDataList).forEach(element -> resultList.add(element));

        return resultList;
    }

    /**
     * Sorting - quicksort method for array
     *
     * @param arr  - array to be sorted
     * @param from - sort start
     * @param to   - sort finish
     */
    private void quickSortArray(E[] arr, int from, int to) {
        if (from < to) {
            int divideIndex = partition(arr, from, to);

            quickSortArray(arr, from, divideIndex - 1);
            quickSortArray(arr, divideIndex, to);
        }
    }

    /**
     * Getting division index for array
     *
     * @param arr  - array to be sorted
     * @param from - sort start
     * @param to   - sort finish
     * @return number of left index
     */
    private int partition(E[] arr, int from, int to) {
        int rightIndex = to;
        int leftIndex = from;

        E pivot = arr[from + (to - from) / 2];
        while (leftIndex <= rightIndex) {
            while (((Comparable) arr[leftIndex]).compareTo(pivot) < 0) {
                leftIndex++;
            }

            while (((Comparable) arr[rightIndex]).compareTo(pivot) > 0) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(arr, rightIndex, leftIndex);
                leftIndex++;
                rightIndex--;
            }
        }
        return leftIndex;
    }

    /**
     * Move elements according to position
     *
     * @param array  - array to change
     * @param index1 - element's index
     * @param index2 - element's index
     */
    private void swap(E[] array, int index1, int index2) {
        E tmp = array[index1];
        array[index1] = array[index2];
        array[index2] = tmp;
    }
}