/* Estimate percolation threshold */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private int iterations;
    private int gridOrder;
    private double[] percolationThresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        gridOrder = n;
        iterations = trials;
        percolationThresholds = new double[iterations];

        if (n < 1 || trials < 1) throw new IllegalArgumentException();

        for (int i = 0; i < iterations; i++) {
            Percolation percolation = new Percolation(gridOrder);

            while (!percolation.percolates()) {
                // plus one since the auto grader includes the upper boundary and makes it fail
                int row = StdRandom.uniformInt(1, gridOrder + 1);
                int col = StdRandom.uniformInt(1, gridOrder + 1);

                if (!percolation.isOpen(row, col)) percolation.open(row, col);
            }

            int openSites = percolation.numberOfOpenSites();
            double percolationThreshold = (double) (openSites) / (double) (gridOrder * gridOrder);
            percolationThresholds[i] = percolationThreshold;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean()) - (CONFIDENCE * (stddev()) / (Math.sqrt(iterations)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean()) + (CONFIDENCE * (stddev()) / (Math.sqrt(iterations)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        double mean = ps.mean();
        double stddev = ps.stddev();
        double[] confidenceInterval = {ps.confidenceLo(), ps.confidenceHi()};

        String format = "%-24s= %s%n";

        System.out.printf(format, "mean", mean);
        System.out.printf(format, "stddev", String.format("%.16f", stddev));
        System.out.printf(format, "95% confidence interval", String.format("[%.16f, %.16f]", confidenceInterval[0], confidenceInterval[1]));
    }
}
