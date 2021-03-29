package demoisellesChinoises;

import java.util.ArrayList;

public class Board {

    private Piece[] tabPieces;
    private int size;
    private ArrayList<Board> possiblesMoves;
    private Player curPlayer;

    public Board(int s){

        
        //Pour un plateau de taille s ( avec s >= 4 ) , alors il y a 18 + 16 + 13 +|(4-s)| * 2 cases, mais si s = 3 alors il y a 18 + 16 cases
        this.size = s;

        Player player1 = new Player();
        Player player2 = new Player();
        this.tabPieces = new Piece[18];

        //Corriger la boucle quand on sera sur de comment implanter les coordonnées
        for(int i = 0; i < 9; i++){
            this.tabPieces[0] = new Piece(i+1,i+1,player1);
            this.tabPieces[17] = new Piece(0,0,player2);
        }


    }

    public ArrayList<Board> getMoves(){
        return possiblesMoves;
    }

    public void makeMove(Board move){

    }

    public void evaluate(Board etat){
        //


    }

    public Player currentPlayer(){
        return this.curPlayer;
    }

    public boolean isGameOver(){
        return false;
    }

}
