package com.assignment2;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/17/2016
 *  Last updated:  11/17/2016
 *
 *  Compilation:   javac com.assignment2.RandomizedQueue.java
 *  Execution:     java com.assignment2.RandomizedQueue
 *
 *  Model a randomized queue
 *
 *
 *----------------------------------------------------------------*/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int last;          // index of the last item
    private Item[] items;      // array to store randomized queue

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        last = -1;
    }

    /**
     * Is the queue empty
     * @return {boolean}
     */
    public boolean isEmpty() {
        return last == -1;
    }

    /**
     * Return the number of items on the queue
     * @return {int}
     */
    public int size() {
        return last + 1;
    }

    /**
     * Add the item
     * @param it {Item}
     */
    public void enqueue(Item it) {
        if (it == null) {
            throw new java.lang.NullPointerException();
        }

        if (last >= items.length - 1) resize(items.length * 2);
        items[++last] = it;
    }

    /**
     * Resize array
     * @param size {int}
     */
    private void resize(int size) {
        if (size == 0) size = 1;
        Item[] copy = (Item[]) new Object[size];
        for (int i = 0, j = 0; i <= last; i++) {
            if (items[i] != null) {
                copy[j++] = items[i];
            }
        }
        items = copy;
    }

    /**
     * Return the index of random item
     * @param size {int}
     * @return {int}
     */
    private int sampleIdx(int size) {
        return StdRandom.uniform(size);
    }

    /**
     * Move item
     * @param arr {Item[]}
     * @param tail {int}
     * @return {Item}
     */
    private Item removeRandomItem(Item[] arr, int tail) {
        int toRemove = sampleIdx(tail + 1);        // position to remove
        Item it = arr[toRemove];
        arr[toRemove] = arr[tail];                 // move the last item to the position of removed item
        arr[tail] = null;
        return it;
    }

    /**
     * Remove and return a random item
     * @return {Item}
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item it = removeRandomItem(items, last);
        last--;
        int len = items.length;
        if (last < len / 4) resize(len / 2);
        return it;
    }

    /**
     * Return (but not remove) a random item
     * @return {Item}
     */
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return items[sampleIdx(last + 1)];
    }

    /**
     * Return an independent iterator over items in random order
     * @return {Iterator}
     */
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private Item[] copy = (Item[]) new Object[last + 1]; // use copy for each iterator
        private int tail = last;

        public RandomizedIterator() {
            if (!isEmpty()) {
                for (int i = 0; i <= tail; i++) {
                    copy[i] = items[i];
                }
            }
        }

        public boolean hasNext() {
            return tail != -1;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            return removeRandomItem(copy, tail--);
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}
