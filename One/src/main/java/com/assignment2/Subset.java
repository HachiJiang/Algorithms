package com.assignment2;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/19/2016
 *  Last updated:  11/19/2016
 *
 *  Compilation:   javac com.assignment2.Subset.java
 *  Execution:     java com.assignment2.Subset
 *
 *  Client program
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Subset {
    public static void main(String[] args) {
        int count = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        Iterator<String> it = rq.iterator();
        while (count > 0 && it.hasNext()) {
            StdOut.println(it.next());
            count--;
        }
    }
}
