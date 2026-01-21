/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openCount = 0;
    private WeightedQuickUnionUF ufPercolates;
    private WeightedQuickUnionUF ufFullness;
    private final int n;
    private int B;
    private int T;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Enter a number greater than zero");
        }
        this.n = n;
        grid = new boolean[n][n];
        B = (n * n) + 1;
        T = (n * n);
        ufFullness = new WeightedQuickUnionUF((n * n) + 1);
        ufPercolates = new WeightedQuickUnionUF((n * n) + 2);
    }

    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Invalid entry");
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        if (grid[row - 1][col - 1]) {
            return;
        }
        else {
            grid[row - 1][col - 1] = true;
            openCount++;
        }
        int index = ((row - 1) * n) + (col - 1);
        if (row == n) {
            ufPercolates.union(index, B);
        }
        if (row == 1) {
            ufPercolates.union(index, T);
            ufFullness.union(index, T);
        }
        if (row < n && grid[row][col - 1]) {
            int index2 = ((row) * n) + (col - 1);
            ufFullness.union(index, index2);
            ufPercolates.union(index, index2);
        }
        if (row > 1 && grid[row - 2][col - 1]) {
            int index2 = ((row - 2) * n) + (col - 1);
            ufFullness.union(index, index2);
            ufPercolates.union(index, index2);
        }
        if (col < n && grid[row - 1][col]) {
            int index2 = ((row - 1) * n) + col;
            ufFullness.union(index, index2);
            ufPercolates.union(index, index2);
        }
        if (col > 1 && grid[row - 1][col - 2]) {
            int index2 = ((row - 1) * n) + (col - 2);
            ufFullness.union(index, index2);
            ufPercolates.union(index, index2);
        }

    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return (grid[row - 1][col - 1]);
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
            int index = ((row - 1) * n) + (col - 1);
            return (ufFullness.find(T) == ufFullness.find(index));
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return (ufPercolates.find(B) == ufPercolates.find(T));
    }

}

