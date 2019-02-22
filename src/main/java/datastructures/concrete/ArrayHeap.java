package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;

/**
 * @see IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int size;

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        heap = makeArrayOfT(NUM_CHILDREN + 1);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int arraySize) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[arraySize]);
    }

    @Override
    public T removeMin() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        T ret = heap[0];
        size--;
        heap[0] = heap[size];
        int currIndex = 0;
        int minChildIndex = getMinChildIndex(0);
        while (minChildIndex != currIndex) {
            T temp = heap[currIndex];
            heap[currIndex] = heap[minChildIndex];
            heap[minChildIndex] = temp;
            currIndex = minChildIndex;
            minChildIndex = getMinChildIndex(currIndex);
        }
        return ret;
    }

    /* returns the index of the child with the smallest value, given a parent. 
    If the parent has a smaller value than all of its children, return parent index. */
    private int getMinChildIndex(int parent) {
        int minIndex = parent;
        for (int child = parent * 4 + 1; child < size && child < child + NUM_CHILDREN; child++) {
            if (heap[minIndex].compareTo(heap[child]) <= 0) {
                minIndex = child;
            }
        }
        return minIndex;
    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new EmptyContainerException();
        }
        return heap[0];
    }

    @Override
    public void insert(T item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size >= heap.length) {
            increaseCapacity();
        }
        heap[size - 1] = item;
        // (index of the item) - 1 because array starts at zero
        int parent = ((size - 1) - 1) / NUM_CHILDREN;
        while (heap[parent].compareTo(heap[size - 1]) > 0) { // while parent is bigger than child
            heap[size - 1] = heap[parent];
            heap[parent] = item;
            parent = parent - (NUM_CHILDREN - 1);
        }
    }

    private void increaseCapacity() {
        T[] replace = makeArrayOfT(2 * heap.length);
        for (int i = 0; i < heap.length; i++) {
            replace[i] = heap[i];
        }
        heap = replace;
    }

    @Override
    public int size() {
        return size;
    }
}
