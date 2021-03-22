package demoisellesChinoises;

public class Piece {

    private int posx; //Il est possible qu'il y ai pas besoin de 2 coordonées, juste de 1 seule
    private int posy;

    private Player player;

    public Piece (int x, int y, Player p){
        this.posx = x;
        this.posy = y;
        this.player = p;
    }
}
