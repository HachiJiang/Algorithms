package com.assignment1;

/*----------------------------------------------------------------
 *  Author:        Jiang Hong
 *  Written:       11/6/2016
 *  Last updated:  11/6/2016
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats
 *
 *  ....
 *
 *
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double meanVal;                       // average of thresholds
    private double stddevVal;                     // sample standard deviation
    private double[] confidence = new double[2];  // store low and high value of 95% confidence interval

    /**
     * Perform trials independent experiments on an n-by-n grid
     * @param n {int} grid size of percolation system
     * @param trials {int} trial times of experiments
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("invalid grid size or trial times");
        }

        double[] thresholds = new double[trials]; // store threshold of each experiments
        for (int i = 0; i < trials; i++) {
            thresholds[i] = performExperiment(n);
        }
        calculateResults(thresholds);
    }

    /**
     * Perform single experiment, and return the threshold
     * @param n {int} grid size of percolation system
     * @return {double} threshold of current experiment
     */
    private double performExperiment(int n) {
        Percolation perco = new Percolation(n);
        double count = 0; // count of opened sites
        int length = n * n;
        while (count < length) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            if (!perco.isOpen(row, col)) {
                perco.open(row, col);
                count += 1;

                if (perco.percolates()) {
                    break;
                }
            }
        }
        return count / length;
    }

    /**
     * Calculate result params
     * @param thresholds {double[]}
     */
    private void calculateResults(double[] thresholds) {
        meanVal = StdStats.mean(thresholds);
        stddevVal = StdStats.stddev(thresholds);
        confidence = calConfidence(meanVal, stddevVal, thresholds.length);
    }

    /**
     * Calculate 95% confidence
     * @param avg {double}
     * @param stdv {double}
     * @param trials {int}
     * @return {double[]} confidence array with [low, high]
     */
    private double[] calConfidence(double avg, double stdv, int trials) {
        double interval = 1.96 * stdv / Math.sqrt(trials);
        return new double[]{avg - interval, avg + interval};
    }

    /**
     * Sample mean of percolation threshold
     * @return {double}
     */
    public double mean() {
        return meanVal;
    }

    /**
     * Sample standard deviation of percolation threshold
     * @return {double}
     */
    public double stddev() {
        return stddevVal;
    }

    /**
     * Low endpoint of 95% confidence interval
     * @return {double}
     */
    public double confidenceLo() {
        return confidence[0];
    }

    /**
     * High endpoint of 95% confidence interval
     * @return {double}
     */
    public double confidenceHi() {
        return confidence[1];
    }

    /**
     * Test client
     * @param args {String[]}
     */
    public static void main(String[] args) {
        Stopwatch sw = new Stopwatch();

        int n = 200;
        int trials = 100;
        PercolationStats percoStats = new PercolationStats(n, trials);
        StdOut.println("means = " + percoStats.mean());
        StdOut.println("stddev = " + percoStats.stddev());
        StdOut.println("95% confidence interval = " + percoStats.confidenceLo() + ", " + percoStats.confidenceHi());
        StdOut.println("Total running time = " + sw.elapsedTime() + "s");
    }
}