/**
 * Created by jianghong on 2016/11/17.
 */

import com.assignment2.RandomizedQueue;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;
import org.junit.Assert;

import java.util.Iterator;

public class TestRandomizedQueue<Item> {
    @Test
    public void testIsEmptySize() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        Assert.assertEquals(rq.size(), 0);
        Assert.assertEquals(rq.isEmpty(), true);
        rq.enqueue(3);
        Assert.assertEquals(rq.size(), 1);
        Assert.assertEquals(rq.isEmpty(), false);
        rq.dequeue();
        Assert.assertEquals(rq.size(), 0);
        Assert.assertEquals(rq.isEmpty(), true);
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testDequeue() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        Assert.assertEquals(rq.size(), 0);
        Assert.assertEquals(rq.isEmpty(), true);
        rq.dequeue();
    }

    private <Item> RandomizedQueue<Item> getRQ(Item[] arr) {
        RandomizedQueue<Item> rq = new RandomizedQueue<Item>();
        for (int i = 0, len = arr.length; i < len; i++) {
            rq.enqueue(arr[i]);
        }
        return rq;
    }

    private <Item> void testIteratorTpl(Item[] arr, int count) {
        RandomizedQueue<Item> rq = getRQ(arr);
        Iterator<Item> it = rq.iterator();
        while (count-- > 0) {
            StdOut.println(it.next());
        }
    }

    @Test
    public void testIterator() {
        String[] str = new String[]{"A", "B", "C", "D", "E" ,"F", "G", "H", "I"};
        testIteratorTpl(str, 3);
    }

    @Test
    public void testIterator2() {
        String[] str = new String[]{"AA", "BB", "BB", "BB", "BB", "BB", "CC", "CC"};
        testIteratorTpl(str, 8);
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testIterator3() {
        Double[] arr = new Double[]{0.1, 0.7, 0.0, 0.1, 0.1};
        testIteratorTpl(arr, 50);
    }

    @Test
    public void testIterator4() {
        Double[] arr = new Double[]{};
        RandomizedQueue<Double> rq = getRQ(arr);
        rq.enqueue(0.0);
        Assert.assertEquals(rq.dequeue(), 0.0, 0);
        Assert.assertTrue(rq.isEmpty());
        rq.enqueue(40.0);
    }

    @Test
    public void testIterator5() {
        Integer[] arr = new Integer[]{};
        RandomizedQueue<Integer> rq = getRQ(arr);
        rq.enqueue(171);
        Assert.assertEquals(rq.dequeue(), 171, 0);
        Assert.assertTrue(rq.isEmpty());
        rq.enqueue(40);
    }

    @Test
    public void testIterator6() {
        Integer[] arr = new Integer[]{};
        RandomizedQueue<Integer> rq = getRQ(arr);
        Assert.assertTrue(rq.isEmpty());
        Assert.assertEquals(rq.size(), 0);
        Assert.assertTrue(rq.isEmpty());
        Assert.assertTrue(rq.isEmpty());
        rq.enqueue(524);
        Assert.assertEquals(rq.sample(), 524, 0);
        Assert.assertEquals(rq.dequeue(), 524, 0);
        Assert.assertTrue(rq.isEmpty());
        rq.enqueue(270);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testIterator7() {
        System.out.println("Test 7: Check that NullPointerException is thrown when inserting null items");
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(null);
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testIterator8() {
        System.out.println("Test 8: dequeue() and sample() from an empty randomized queue");
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.dequeue();
        rq.sample();
    }
}
