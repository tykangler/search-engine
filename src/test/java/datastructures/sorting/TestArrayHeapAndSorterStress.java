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

    @Test(timeout=2*SECOND)
    public void testStressInsertAndRemoveMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();
        for (int i = 0; i <= 300000; i++) {
            heap.insert(i);
        }
        for (int i = 0; i <= 300000; i++) {
            assertEquals(i, heap.removeMin());
        }
    }

    @Test(timeout=3*SECOND)
    public void testStressInsertFromBackandPeekMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();
        for (int i = 0; i <= 300000; i++) {
            heap.insert(i);
        }
        assertEquals(0, heap.peekMin());
    }

    @Test(timeout=5*SECOND)
    public void testStressInsertFromBackAndRemoveMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();
        for (int i = 300000; i >= 0; i++) {
            heap.insert(i);
        }
        for (int i = 0; i <= 300000; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertEquals(0, heap.size());
    }

    @Test(timeout=5*SECOND)
    public void testStressSortAllElements() {
        Random rand = new Random();
        IList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 0; i < 300000; i++) {
            list.add(rand.nextInt());
        }
        IList<Integer> sortedList = Sorter.topKSort(300000, list);
        assertEquals(300000, sortedList.size());
        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i) < sortedList.get(i + 1));
        }
    }

    @Test(timeout=5*SECOND)
    public void testStressSortVariableElements() {
        Random rand = new Random();
        IList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 0; i < 300000; i++) {
            list.add(rand.nextInt());
        }
        IList<Integer> sortedList = Sorter.topKSort(30765, list);
        assertEquals(30765, list.size());
        for (int i = 0; i < sortedList.size() - 1; i++) {
            assertTrue(sortedList.get(i) < sortedList.get(i + 1));
        }
    }
}
