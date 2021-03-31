package demoisellesChinoises;

import java.util.ArrayList;

public class Board {

    private Piece[] tabPieces;
    private int size;
    private ArrayList<Board> possiblesMoves;
    private Player curPlayer;
    private Player player1;
    private Player player2;

    public Board(int s){

        
        //Pour un plateau de taille s ( avec s >= 4 ) , alors il y a 18 + 16 + 13 +|(4-s)| * 2 cases, mais si s = 3 alors il y a 18 + 16 cases
        this.size = s;

        this.player1 = new Player();
        this.player2 = new Player();
        this.curPlayer = this.player1; //On suppose que le joueur 1 est le premier a jouer

        this.tabPieces = new Piece[18];

        this.tabPieces[0] = new Piece(1,1 ,player1);
        this.tabPieces[1] = new Piece(2,1, player1);
        this.tabPieces[2] = new Piece(1,2, player1);
        this.tabPieces[3] = new Piece(2,2, player1);
        this.tabPieces[4] = new Piece(3,2, player1);
        this.tabPieces[5] = new Piece(1,3, player1);
        this.tabPieces[6] = new Piece(2,3, player1);
        this.tabPieces[7] = new Piece(3,3, player1);
        this.tabPieces[8] = new Piece(4,3, player1);

        this.tabPieces[9] = new Piece(1,(this.size * 2) + 1, player2);
        this.tabPieces[10] = new Piece(2,(this.size * 2) + 1, player2);
        this.tabPieces[11] = new Piece(1,(this.size * 2), player2);
        this.tabPieces[12] = new Piece(2,(this.size * 2), player2);
        this.tabPieces[13] = new Piece(3,(this.size * 2), player2);
        this.tabPieces[14] = new Piece(1,(this.size * 2) -1, player2);
        this.tabPieces[15] = new Piece(2,(this.size * 2) -1, player2);
        this.tabPieces[16] = new Piece(3,(this.size * 2) -1, player2);
        this.tabPieces[17] = new Piece(4,(this.size * 2) -1, player2);



    }

    public ArrayList<Board> getMoves(){
        return possiblesMoves;
    }

    public void makeMove(Board move){



        //Si la partie n'est pas terminé, c'est au joueur suivant de jouer
        if(!this.isGameOver()) {
            if (this.curPlayer == this.player1) {
                this.curPlayer = this.player2;
            } else if (this.curPlayer == this.player2) {
                this.curPlayer = this.player1;
            }
        }
    }

    public void evaluate(Board etat){
        //


    }

    public Player currentPlayer(){
        return this.curPlayer;
    }

    //Si on appelle la fonction isGameOver apres chaque action de chaque joueur, alors selon les regles du jeu, il ne peut y avoir
    //qu'un seul joueur qui possede tout ses pions de l'autre coté du plateau
    public boolean isGameOver(){
        boolean res = true;
        int i = 0;
        //On continue tant qu'on a pas trouve un pion du joueur 1 qui n'est pas dans la zone adverse
        while(res && i <= 8){
            res = res && isPawnInOppositeSide(this.tabPieces[i]);
            i++;
        }
        //Si le joueur 1 n'a pas gagne
        if(!res){
            res = true;
            i = 9;
            while(res && i <= 17){
                res = res && isPawnInOppositeSide(this.tabPieces[i]);
                i++;
            }
        }
        return res;
    }

    public boolean isPawnInOppositeSide(Piece p){
        Player joueur = p.getPlayer();
        boolean res = false;
        if(joueur == this.player2){
            if (p.getPosy() < 4){
                res = true;
            }
        }
        else if(joueur == this.player1){
            if(p.getPosy() > ((this.size * 2 ) - 2)){
                res = true;
            }
        }
        return res;
    }

}
