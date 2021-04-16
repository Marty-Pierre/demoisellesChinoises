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

    public int getPosx(){
        return this.posx;
    }

    public int getPosy(){
        return this.posy;
    }

    public Player getPlayer(){
        return this.player;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }
}
