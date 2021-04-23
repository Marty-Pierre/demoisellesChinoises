package demoisellesChinoises;

import java.util.Objects;

public class Move {
    private int fromX;
    private int fromY;

    private int toX;
    private int toY;

    public Move(int fX, int fY, int tX, int tY){
        this.fromX = fX;
        this.fromY = fY;
        this.toX = tX;
        this.toY = tY;

    }


    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return fromX == move.fromX &&
                fromY == move.fromY &&
                toX == move.toX &&
                toY == move.toY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromX, fromY, toX, toY);
    }
}
