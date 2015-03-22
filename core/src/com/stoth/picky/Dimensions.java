package com.stoth.picky;

/**
 * Dimensions of the game board.
 */
public final class Dimensions {
    private final int nRows, nCols;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dimensions that = (Dimensions) o;

        if (nCols != that.nCols) return false;
        if (nRows != that.nRows) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nRows;
        result = 31 * result + nCols;
        return result;
    }

    public int getnRows() {
        return nRows;
    }

    @Override
    public String toString() {
        return "Dimensions{" +
                "nRows=" + nRows +
                ", nCols=" + nCols +
                '}';
    }

    public int getnCols() {
        return nCols;
    }

    private Dimensions(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;
    }

    public static Dimensions create(int nRows, int nCols) {
        return new Dimensions(nRows, nCols);
    }

    public boolean isInBounds(Position p) {
        return p.getRow() >= 0 && p.getRow() < nRows && p.getCol() >= 0 && p.getCol() < nCols;
    }
}
