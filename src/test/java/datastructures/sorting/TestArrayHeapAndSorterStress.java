package datastructures.sorting;

import java.util.Random;

import misc.BaseTest;
import misc.Sorter;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapAndSorterStress extends BaseTest {

    @Test(timeout=15*SECOND)
    public void testStressInsertAndRemoveMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();
        for (int i = 0; i < 200000; i++) {
            heap.insert(i);
        }
        for (int i = 0; i < 200000; i++) {
            assertEquals(i, heap.removeMin());
        }
    }

    @Test(timeout=15*SECOND)
    public void testStressInsertandPeekMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();
        for (int i = 300000; i >= 0; i--) {
            heap.insert(i);
        }
        assertEquals(0, heap.peekMin());
    }

    @Test(timeout=15*SECOND)
    public void testStressInsertFromBackAndRemoveMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();
        for (int i = 200000; i >= 0; i--) {
            heap.insert(i);
        }
        for (int i = 0; i <= 200000; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertEquals(0, heap.size());
    }

    @Test(timeout=15*SECOND)
    public void testStressSort5000Elements() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 0; i < 200000; i++) {
            list.add(i);
        }
        IList<Integer> sortedList = Sorter.topKSort(5000, list);
        assertEquals(5000, sortedList.size());
        for (int i = 0; i < sortedList.size(); i++) {
            assertEquals(i + 195000, sortedList.get(i));
        }
    }

    @Test(timeout=15*SECOND)
    public void testStressSort5Elements() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 0; i < 200000; i++) {
            list.add(i);
        }
        IList<Integer> sortedList = Sorter.topKSort(5, list);
        assertEquals(5, sortedList.size());
        for (int i = 0; i < sortedList.size(); i++) {
            assertEquals(i + 200000 - 5, sortedList.get(i));
        }
    }

}
