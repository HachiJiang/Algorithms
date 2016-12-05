/**
 * Created by jianghong on 2016/11/17.
 */

import org.junit.Test;
import org.junit.Assert;

import java.util.Iterator;

public class TestDeque {

    @Test
    public void testIsEmptyAndSize() {
        Deque<Integer> dq = new Deque<Integer>();
        Assert.assertEquals(dq.isEmpty(), true);
        Assert.assertEquals(dq.size(), 0);
        dq.addFirst(3);
        Assert.assertEquals(dq.isEmpty(), false);
        Assert.assertEquals(dq.size(), 1);
        dq.removeFirst();
        Assert.assertEquals(dq.isEmpty(), true);
        Assert.assertEquals(dq.size(), 0);
    }

    @Test
    public void testAddRemoveFirst() {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addFirst(3);
        dq.addFirst(2);
        dq.addFirst(1);
        Assert.assertEquals(dq.isEmpty(), false);
        Assert.assertEquals(dq.size(), 3);

        int item = dq.removeFirst();
        Assert.assertEquals(item, 1);
        item = dq.removeLast();
        Assert.assertEquals(item, 3);
    }

    @Test
    public void testAddRemoveLast() {
        Deque<Integer> dq = new Deque<Integer>();
        dq.addLast(3);
        dq.addLast(2);
        dq.addLast(1);
        Assert.assertEquals(dq.isEmpty(), false);
        Assert.assertEquals(dq.size(), 3);

        int item = dq.removeFirst();
        Assert.assertEquals(item, 3);
        item = dq.removeLast();
        Assert.assertEquals(item, 1);
    }

    @Test
    public void testIterator() {
        Deque<Integer> dq = new Deque<Integer>();
        Iterator<Integer> i = dq.iterator();
        Assert.assertEquals(i.hasNext(), false);

        dq.addLast(3);
        dq.addLast(2);
        dq.addLast(1);
        i = dq.iterator();
        int count = 3;
        while (i.hasNext()) {
            int a = i.next();
            Assert.assertEquals(a, count--);
        }
    }
}
