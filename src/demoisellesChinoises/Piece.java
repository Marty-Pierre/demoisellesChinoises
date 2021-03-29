package demoisellesChinoises;

public class Piece {

    private int posx;
    private int posy;

    private Player player;

    public Piece (int x, int y, Player p){
        this.posx = x;
        this.posy = y;
        this.player = p;
    }
}
