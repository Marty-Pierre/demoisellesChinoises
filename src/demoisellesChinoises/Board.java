package demoisellesChinoises;

import java.util.ArrayList;

public class Board {

    private Piece[] tabPieces;
    private int size;
    private ArrayList<Board> possiblesMoves;


    public Board(int s){

        
        //Pour un plateau de taille s, alors il y a 18 + 16   (13 -> 15 -> 17 -> 19 -> ...)
        this.size = s;

        Player player1 = new Player();
        Player player2 = new Player();
        this.tabPieces = new Piece[18];


    }

    public ArrayList<Board> getMoves(){
        return possiblesMoves;
    }

    public void makeMove(Board move){

    }

    public void evaluate(){

    }

    public void currentPlayer(){

    }

    public boolean isGameOver(){
        return false;
    }

}
