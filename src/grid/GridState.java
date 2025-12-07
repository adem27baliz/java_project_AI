package grid;

import search.State;

import java.util.Objects;

public class GridState implements State {
    public final int x;
    public final int y;

    public GridState(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GridState)) return false;
        GridState s = (GridState) o;
        return this.x == s.x && this.y == s.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

