/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] threshold;

    private int trials;

    public PercolationStats(int n, int trials) {
        validate(n, trials);
        this.trials = trials;
        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                p.open(row, col);
            }
            double pthreshold = (double) p.numberOfOpenSites() / (n * n);
            threshold[i] = pthreshold;
        }
    }

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid Entry");
        }

    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        double mean = ps.mean();
        double stddev = ps.stddev();
        double lo = ps.confidenceLo();
        double hi = ps.confidenceHi();
        StdOut.println("mean" + " = " + mean);
        StdOut.println("stddev" + " = " + stddev);
        StdOut.println(("95% confidence interval" + " = " + "[" + lo + ", " + hi + "]"));


    }

}
