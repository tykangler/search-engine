package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Sorter;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSorterFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Sorter.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }

    @Test(timeout=SECOND)
    public void testSortKGreaterThanSize() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 49; i >= 0; i--) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(60, list);
        assertEquals(50, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }

    @Test(timeout=SECOND)
    public void testSortThrowsException() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }
        try {
            IList<Integer> top = Sorter.topKSort(-1, list);
            fail("negative inputs should have thrown exception");
        } catch (IllegalArgumentException ex) {

        }
    }

    @Test(timeout=SECOND)
    public void testSortKEqualToSize() {
        IList<Integer> list = new DoubleLinkedList<Integer>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        IList<Integer> top = Sorter.topKSort(20, list);
        assertEquals(20, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
}
