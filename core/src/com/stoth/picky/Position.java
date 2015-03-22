package com.stoth.picky;

import com.google.common.base.Preconditions;

/**
 * A position in the grid.
 */
public final class Position {
    private final int row, col;

    private Position(int row, int col) {
        this.row = Preconditions.checkNotNull(row);
        this.col = Preconditions.checkNotNull(col);
    }

    public static Position create(int row, int col) {
        return new Position(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private Position incRow(int r) {
        return create(row + r, col);
    }

    private Position incCol(int c) {
        return create(row, col + c);
    }

    public Position move(Direction dir) {
        switch (dir) {
            case LEFT:
                return incCol(-1);
            case RIGHT:
                return incCol(1);
            case UP:
                return incRow(-1);
            case DOWN:
                return incRow(1);
        }
        throw new IllegalArgumentException("Unexpected direction " + dir);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (col != position.col) return false;
        if (row != position.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
