package misc;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

public class Sorter {
    /**
     * This method takes the input list and returns the top k elements
     * in sorted order.
     *
     * So, the first element in the output list should be the "smallest"
     * element; the last element should be the "largest".
     *
     * If the input list contains fewer than 'k' elements, return
     * a list containing all input.length elements in sorted order.
     *
     * This method must not modify the input list.
     *
     * @throws IllegalArgumentException  if k < 0
     * @throws IllegalArgumentException  if input is null
     */
    public static <T extends Comparable<T>> IList<T> topKSort(int k, IList<T> input) {
        if (k < 0 || input == null) {
            throw new IllegalArgumentException();
        }
        IList<T> sortedList = new DoubleLinkedList<T>();
        IPriorityQueue<T> heap = new ArrayHeap<T>();
        for (int i = input.size() - k; i < input.size(); i++) {
            heap.insert(input.get(i));
        }
        int size = heap.size();
        for (int i = 0; i < size; i++) {
            sortedList.add(heap.removeMin());
        }
        return sortedList;
    }
}
