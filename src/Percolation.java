/* Percolation API */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridOrder;
    private final boolean[][] grid;
    private int openCount;
    private final int virtualArrayOrder;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("n must be greater than 0.");

        gridOrder = n;
        grid = new boolean[gridOrder + 2][gridOrder + 2];

        openCount = 0;

        virtualArrayOrder = (gridOrder * gridOrder) + 2;
        weightedQuickUnionUF = new WeightedQuickUnionUF(virtualArrayOrder);

        // connect first grid to virtual top node
        for (int i = 1; i <= gridOrder; i++) {
            weightedQuickUnionUF.union(0, i);
        }
        // connect last grid to virtual bottom node
        for (int i = virtualArrayOrder - 2; i > (gridOrder * (gridOrder - 1)); i--) {
            weightedQuickUnionUF.union(i, virtualArrayOrder - 1);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBoundaries(row, col);

        if (isOpen(row, col)) return;

        grid[row][col] = true;
        openCount++;

        int p = xyTo1D(row, col);

        int r;
        int c;

        r = row - 1;
        c = col;
        if (!(r < 1 || r > gridOrder) && grid[r][c]) {
            int q = xyTo1D(r, c);
            weightedQuickUnionUF.union(p, q);
        }
        r = row;
        c = col - 1;
        if (!(c < 1 || c > gridOrder) && grid[r][c]) {
            int q = xyTo1D(r, c);
            weightedQuickUnionUF.union(p, q);
        }
        r = row;
        c = col + 1;
        if (!(c > gridOrder) && grid[r][c]) {
            int q = xyTo1D(r, c);
            weightedQuickUnionUF.union(p, q);
        }
        r = row + 1;
        c = col;
        if (!(r > gridOrder) && grid[r][c]) {
            int q = xyTo1D(r, c);
            weightedQuickUnionUF.union(p, q);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBoundaries(row, col);
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(xyTo1D(row, col));
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(0) == weightedQuickUnionUF.find(virtualArrayOrder - 1);
    }

    private int xyTo1D(int row, int col) {
        return ((row - 1) * gridOrder) + col;
    }

    private void checkBoundaries(int row, int col) {
        if (row < 1 || col < 1 || row > gridOrder || col > gridOrder) throw new IllegalArgumentException();
    }
}
