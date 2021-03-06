package demoisellesChinoises;

import java.util.ArrayList;
import java.lang.Math;

public class Board {

    private Piece[] tabPieces;
    private int size;
    private ArrayList<Move> possiblesMoves;
    private Player curPlayer;
    private Player player1;
    private Player player2;

    public Board(int s){

        
        //Pour un plateau de taille s ( avec s >= 4 ) , alors il y a 18 + 16 + 13 +|(4-s)| * 2 cases, mais si s = 3 alors il y a 18 + 16 cases
        this.size = s;

        this.player1 = new Player("Joueur 1");
        this.player2 = new Player("Joueur 2");
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

    public Board(Board b){
         this.curPlayer = b.curPlayer;
         this.player1 = b.getPlayer1();
         this.player2 = b.getPlayer2();
         this.possiblesMoves = b.getMoves();
         this.size = b.getSize();
         this.tabPieces = b.getTabPieces();
    }


    public void makeMove(Move m){
        int fromX = m.getFromX();
        int fromY = m.getFromY();
        if(this.curPlayer.equals(this.player1)){
            for(int i = 0; i <= 8; i++){
                if((this.tabPieces[i].getPosx() == fromX) && (this.tabPieces[i].getPosy() == fromY)){
                    //On d??place la piece
                    this.tabPieces[i].setPosx(m.getToX());
                    this.tabPieces[i].setPosy(m.getToY());
                }
            }
        }
        else{
            for(int i = 9; i<= 17; i++){
                if((this.tabPieces[i].getPosx() == fromX) && (this.tabPieces[i].getPosy() == fromY)){
                    //On d??place la piece
                    this.tabPieces[i].setPosx(m.getToX());
                    this.tabPieces[i].setPosy(m.getToY());
                }
            }
        }


        //Si la partie n'est pas termin??, c'est au joueur suivant de jouer
        if(!this.isGameOver()) {
            if (this.curPlayer.equals(this.player1)) {
                this.curPlayer = this.player2;
            } else if (this.curPlayer.equals(this.player2)) {
                this.curPlayer = this.player1;
            }
        }
    }


    //Evalue la valeur de l'etat actuel (positif si favorable, negatif si defavorable, neutre sinon)
    public int evaluate(){
        int scorePlayer1 = 0;
        int scorePlayer2 = 0;

        //Distance qui separe nos pions de notre base
        for(int i = 0; i <= 8; i++){
            scorePlayer1 += this.tabPieces[i].getPosy();
        }
        //Distance qui separe les pions de notre adversaire de sa base
        for(int i = 9; i <= 17; i++){
            scorePlayer2 += ((this.size * 2) + 2) - this.tabPieces[i].getPosy();
        }

        //Si l'adversaire a un meilleur score que nous (c'est a dire que ses pions sont plus avanc??s) alors la fonction renvoie une valeur negative
        return scorePlayer1 - scorePlayer2;
    }


    public Player currentPlayer(){
        return this.curPlayer;
    }


    //Si on appelle la fonction isGameOver apres chaque action de chaque joueur, alors selon les regles du jeu, il ne peut y avoir
    //qu'un seul joueur qui possede tout ses pions de l'autre cot?? du plateau
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
        if(joueur.equals(this.player2)){
            if (p.getPosy() < 4){
                res = true;
            }
        }
        else if(joueur.equals(this.player1)){
            if(p.getPosy() > ((this.size * 2 ) - 2)){
                res = true;
            }
        }
        return res;
    }

    public int evaluation_alpha_beta(int c, int a, int b) {
        int alpha = a;
        int beta = b;
        if (this.isGameOver()) {
            //On a gagne
            if (this.curPlayer.equals(this.player1)) {
                return 1000000; //Cens?? retourn?? l'infini, mais une valeur suffisemment grande suffit
            }
            //On a perdu
            if (this.curPlayer.equals(this.player2)) {
                return -1000000; //Cens?? retourn?? moins l'infini, mais une valeur suffisemment grande suffit
            }
        }

        if (c == 0) {
            return evaluate();
        }

        ArrayList<Move> successeursMoves = this.getMoves();
        Board tempBoard;
        if (this.curPlayer.equals(this.player1)) {
            int score_max = -1000000;
            for (Move m : successeursMoves) {
                tempBoard = new Board(this);
                tempBoard.makeMove(m);
                score_max = Math.max(score_max, tempBoard.evaluation_alpha_beta(c - 1,alpha, beta));
                if(score_max >= beta){ //Coupure beta
                    return score_max;
                }
                alpha = Math.max(alpha, score_max);
            }
            return score_max;
        }
        else{
            int score_min = 1000000;
            for(Move m : successeursMoves){
                tempBoard = new Board(this);
                tempBoard.makeMove(m);
                score_min = Math.min(score_min, tempBoard.evaluation_alpha_beta(c - 1, alpha, beta));
                if(score_min <= alpha){//Coupure alpha
                    return score_min;
                }
                beta = Math.min(beta, score_min);
            }
            return score_min;
        }
    }

    public Piece[] getTabPieces() {
        return tabPieces;
    }

    public int getSize() {
        return size;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public ArrayList<Move> getMoves(){
        this.possiblesMoves = new ArrayList<>(60); //60 est une estimation grossiere du facteur moyen de branchement
        Piece p;

        if(this.curPlayer.equals(this.player1)){
            //C'est au tour du joueur 1
            for(int i = 0; i <= 8; i++){
                p = tabPieces[i];
                if(p.getPosy() <= this.size){
                    //On est dans la partie basse du plateau
                    this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() + 1, p.getPosy() + 1)); //Haut droit
                    this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx(), p.getPosy() + 1)); //Haut gauche
                    if(p.getPosy() != 1){
                        //On n'est pas a la premiere ligne du plateau
                        if(p.getPosx() != 1){
                            //On n'est pas adjacent au mur gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy() - 1)); //Bas gauche
                        }
                        if(p.getPosy() != (p.getPosx() - 1)){
                            //On n'est pas pret du mur droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(),p.getPosy() - 1)); //Bas droit
                        }
                    }
                    else{
                        //On est sur la premiere ligne
                        if(p.getPosx() == 1){
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                        }
                        else {
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                        }
                    }
                }
                else{
                    if(p.getPosy() >= this.size + 2){
                        //On est dans la partie haute du plateau
                        this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() + 1, p.getPosy() - 1));//Bas droit
                        this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(),p.getPosy() - 1)); //Bas gauche
                        if(p.getPosy() != (this.size * 2) + 1){
                            //On n'est pas a la derniere ligne du plateau
                            if(p.getPosx() != 1){
                                //On n'est pas adjacent au mur gauche
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() - 1, p.getPosy() + 1)); //Haut gauche
                            }
                            if(p.getPosx() != ((this.size * 2 )+ 3)){
                                //On n'est pas adjacent au mur droit
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(), p.getPosy() + 1)); //Haut droit
                            }
                        }
                        else{
                            //On est a la derniere ligne du plateau
                            if(p.getPosx() == 1){
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                            }
                            else{
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                            }
                        }
                    }

                    else {
                        //On est a la ligne au milieu du plateau
                        if(p.getPosx() != 1){
                            //On n'est pas adjacent au mur gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() - 1, p.getPosy() + 1)); //Haut gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy() - 1)); //Bas gauche
                        }
                        if(p.getPosy() != (p.getPosx() - 1)){
                            //On n'est pas adjacent au mur droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(), p.getPosy() + 1)); //Haut droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(),p.getPosy() - 1)); //Bas droit
                        }
                    }
                }
            }
        }
        else {
            //C'est au tour du joueur 2
            for(int i = 9; i <= 17; i++){
                p = tabPieces[i];
                if(p.getPosy() <= this.size){
                    //On est dans la partie basse du plateau
                    this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() + 1, p.getPosy() + 1)); //Haut droit
                    this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx(), p.getPosy() + 1)); //Haut gauche
                    if(p.getPosy() != 1){
                        //On n'est pas a la premiere ligne du plateau
                        if(p.getPosx() != 1){
                            //On n'est pas adjacent au mur gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy() - 1)); //Bas gauche
                        }
                        if(p.getPosy() != (p.getPosx() - 1)){
                            //On n'est pas pret du mur droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(),p.getPosy() - 1)); //Bas droit
                        }
                    }
                    else{
                        //On est sur la premiere ligne
                        if(p.getPosx() == 1){
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                        }
                        else {
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                        }
                    }
                }
                else{
                    if(p.getPosy() >= this.size + 2){
                        //On est dans la partie haute du plateau
                        this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() + 1, p.getPosy() - 1));//Bas droit
                        this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(),p.getPosy() - 1)); //Bas gauche
                        if(p.getPosy() != (this.size * 2) + 1){
                            //On n'est pas a la derniere ligne du plateau
                            if(p.getPosx() != 1){
                                //On n'est pas adjacent au mur gauche
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() - 1, p.getPosy() + 1)); //Haut gauche
                            }
                            if(p.getPosx() != ((this.size * 2 )+ 3)){
                                //On n'est pas adjacent au mur droit
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(), p.getPosy() + 1)); //Haut droit
                            }
                        }
                        else{
                            //On est a la derniere ligne du plateau
                            if(p.getPosx() == 1){
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                            }
                            else{
                                this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                            }
                        }
                    }

                    else {
                        //On est a la ligne au milieu du plateau
                        if(p.getPosx() != 1){
                            //On n'est pas adjacent au mur gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy())); //Gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() - 1, p.getPosy() + 1)); //Haut gauche
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx() - 1, p.getPosy() - 1)); //Bas gauche
                        }
                        if(p.getPosy() != (p.getPosx() - 1)){
                            //On n'est pas adjacent au mur droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(), p.getPosx() + 1, p.getPosy())); //Droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(), p.getPosy() + 1)); //Haut droit
                            this.possiblesMoves.add(new Move(p.getPosx(),p.getPosy(),p.getPosx(),p.getPosy() - 1)); //Bas droit
                        }
                    }
                }
            }
        }
        ArrayList<Move> alm = new ArrayList<>(this.possiblesMoves);
        int x;
        int y;
        for(int i = 0; i <= 17; i++){
            x = this.tabPieces[i].getPosx();
            y = this.tabPieces[i].getPosy();
            for(Move m : alm){
                if((m.getToX() == x) && (m.getToY() == y)){
                    //Mouvement impossible
                    this.possiblesMoves.remove(m);
                }
            }
            alm = new ArrayList<>(this.possiblesMoves);
        }
        return this.possiblesMoves;
    }
}
