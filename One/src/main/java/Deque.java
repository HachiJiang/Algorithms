/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/17/2016
 *  Last updated:  11/17/2016
 *
 *  Compilation:   javac Deque.java
 *  Execution:     java Deque
 *
 *  Model a double-ended queue
 *
 *
 *----------------------------------------------------------------*/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;    // the first and last item
    private int n;                     // size of queue

    private class Node<Item> {
        private Item item;
        private Node<Item> left;
        private Node<Item> right;
    }

    /**
     * Construct an empty deque
     */
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    /**
     * Is the deque empty
     * @return {boolean}
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Return the number of items on the deque
     * @return {int}
     */
    public int size() {
        return n;
    }

    /**
     * Add the item to the front
     * @param item {Item}
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.left = oldFirst;
        first.right = null;
        if (isEmpty()) last = first;
        else oldFirst.right = first;
        n++;
    }

    /**
     * Add the item to the end
     * @param item {Item}
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.right = oldLast;
        last.left = null;
        if (isEmpty()) first = last;
        else oldLast.left = last;
        n++;
    }

    /**
     * Remove and return the item from the front
     * @return {Item}
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node<Item> oldFirst = first;
        first = oldFirst.left;
        n--;
        if (isEmpty()) {
            last = null;
            first = null;
        } else {
            first.right = null;
        }
        return oldFirst.item;
    }

    /**
     * Remove and return the item from the end
     * @return {Item}
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Node<Item> oldLast = last;
        last = oldLast.right;
        n--;
        if (isEmpty()) {
            last = null;
            first = null;
        } else {
            last.left = null;
        }
        return oldLast.item;
    }

    /**
     * Return an iterator over items in order from front to end
     * @return {Iterator}
     */
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }


    private class DequeueIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item item = current.item;
            current = current.left;
            return item;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}
