package demoisellesChinoises;

public class Move {
    private int fromX;
    private int fromY;

    private int toX;
    private int toY;

    public Move(int fX, int fY, int tX, int tY){
        this.fromX = fX;
        this.fromY = fY;
        this.toX = tX;
        this.toX = tY;

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
}
