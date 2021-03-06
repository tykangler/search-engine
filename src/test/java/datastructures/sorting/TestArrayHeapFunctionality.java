package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import org.junit.Test;


/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(0);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }

    @Test(timeout=SECOND)
    public void testBasicUpdateSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        int initSize = heap.size();
        heap.insert(0);
        assertEquals(1, initSize + 1);
        heap.removeMin();
        assertEquals(0, initSize);
    }

    @Test(timeout=SECOND)
    public void testBasicRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(3, heap.removeMin());
        assertTrue(heap.isEmpty());

        try {
            heap.removeMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing.
        }
    }

    @Test(timeout=SECOND)
    public void testBasicPeekMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();

        try {
            heap.peekMin();
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing.
        }

        heap.insert(0);
        assertEquals(0, heap.peekMin());
        assertEquals(1, heap.size());
        assertFalse(heap.isEmpty());

    }

    @Test(timeout=SECOND)
    public void testBasicInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(0);
        assertEquals(1, heap.size());

        heap.insert(1);
        assertEquals(2, heap.size());

        try {
            heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing.
        }
    }

    @Test(timeout=12000*SECOND)
    public void testMultipleInsertAndRemoveMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();
        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }
        assertEquals(10, heap.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }

    @Test(timeout=SECOND)
    public void testPeekMinAndRemoveMin() {
        IPriorityQueue<Integer> heap = new ArrayHeap<Integer>();

        for (int i = 0; i < 10; i++) {
            heap.insert(i);
        }

        for (int i = 0; i < 10; i++) {
            assertEquals(heap.peekMin(), heap.removeMin());
        }

        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());

    }

    @Test(timeout=SECOND)
    public void testRemoveMinDuplicateItems() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 5; i++) {
            heap.insert(0);
        }
        int initSize = heap.size();
        assertEquals(0, heap.removeMin());
        assertTrue(!heap.isEmpty());
        assertEquals(4, initSize - 1);
    }

}
