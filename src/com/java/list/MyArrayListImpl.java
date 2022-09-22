package com.java.list;

import java.util.Arrays;

/**
 * This is my ArrayList implementation
 *
 * @author Dobrodey Olga
 */
public class MyArrayListImpl<E> implements MyArrayList<E>{

    /**
     * Empty array for init
     */
    private static final Object[] EMPTY_ELEMENT_DATA = {};

    /**
     * Array E[] that contains all the elements
     */
    private E[] elementData;
    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;

    public MyArrayListImpl() {
        elementData = (E[]) EMPTY_ELEMENT_DATA;
    }

    public MyArrayListImpl(int capacity) {
        if (capacity > 0) {
            this.elementData = (E[]) new Object[capacity];
        } else if (capacity == 0) {
            this.elementData = (E[]) EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
    }

    /**
     * Add new element to MyArrayListImpl
     *
     * @param element - added element
     */
    @Override
    public void add(E element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
    }

    /**
     * @param index   - element insertion position
     * @param element - element to be inserted
     * @throws IndexOutOfBoundsException – if the index is out of range (index < 0 || index > size())
     */

    @Override
    public void add(int index, E element) {
        if (index > 0 && index <= size()) {
            ensureCapacity(size + 1);
            System.arraycopy(elementData, index, elementData, index + 1, size - index);
            elementData[index] = element;
            size++;
        } else {
            throw new IndexOutOfBoundsException("Index must be between 0 and size");
        }
    }

    /**
     * @param arrayList - arrayList to be added in list
     * @return true if this list changed as a result of the call
     */
    @Override
    public boolean addAll(MyArrayList<? extends E> arrayList) {
        Object[] array = arrayList.toArray();
        int addLength = array.length;
        if (addLength == 0)
            return false;
        if (addLength > (elementData.length - size)) {
            createNewArray(addLength + size);
            System.arraycopy(array, 0, elementData, size, addLength);
        } else System.arraycopy(array, 0, elementData, size, addLength);
        size += addLength;
        return true;
    }

    @Override
    public void ensureCapacity(int minCapacity) {
        if (minCapacity > elementData.length) {
            int newCapacity = elementData.length * 3 / 2 + 1;
            createNewArray(newCapacity);
        }
    }

    private void createNewArray(int arrayLength) {
        E[] oldData = elementData;
        elementData = (E[]) new Object[arrayLength];
        System.arraycopy(oldData, 0, elementData, 0, size);
    }

    /**
     * Remove all elements of MyArrayListImpl
     */
    @Override
    public void clear() {
        final E[] es = elementData;
        for (int to = size, i = size = 0; i < to; i++)
            es[i] = null;
    }

    /**
     * Get element from MyArrayListImpl for index
     *
     * @param index - index of the element to return
     * @return the element at the specified position in this list
     */
    @Override
    public E get(int index) {
        return elementData[index];
    }

    /**
     * Remove element with index in
     *
     * @param index of the element to remove
     * @return remoted element
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size())
     */
    @Override
    public E remove(int index) {
        if (index > 0 && index < size) {
            int numberMove = size - index - 1;
            E oldValue = elementData[index];
            System.arraycopy(elementData, index + 1, elementData, index, numberMove);
            elementData[--size] = null;
            return oldValue;
        } else throw new IndexOutOfBoundsException("Index must be between 0 and size");
    }

    /**
     * Sort list in natural direction
     */
    @Override
    public void sort() {
        Arrays.sort(elementData);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Sorting - quicksort method for ArrayList <E>
     */
    @Override
    public void quickSort() {
        quickSortArray(elementData, 0, size - 1);
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
            while (((Comparable)arr[leftIndex]).compareTo(pivot) < 0) {
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

    /**
     * @return array of objects
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public String toString() {
        if(size==0){return "[]";}
        StringBuilder toString = new StringBuilder();
        toString.append("[");
        for (int i = 0; i <= size - 1; i++) {
            toString.append(elementData[i]).append(", ");
        }
        toString.delete(toString.length() - 2, toString.length()).append("]");
        return toString.toString();
    }
}



