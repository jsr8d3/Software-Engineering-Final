/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import java.util.ArrayList;

/**
 *
 * @author jebro
 */
public class HumanPlayer extends Player{
    
    public String name;
    private Piece active;
    private ArrayList<Move> possibleMovesForActivePiece;
    
    
    public HumanPlayer(Game game, Board board, boolean facingTop,String name){
        super(game, board, facingTop);
        this.name = name;
        possibleMovesForActivePiece = new ArrayList();
    }
    
    private void setActivePiece(Piece piece){
        active = piece;
        active.setActive(true);
        
        possibleMovesForActivePiece = board.movesAPieceCanMake(piece);
    }
    
    private void removeActivePiece(){
        active.setActive(false);
        active = null;
        possibleMovesForActivePiece = null;
    }
    
    
    private void showAvailableSquares(int row, int column){
        Piece p = board.isPieceAtPosition(new Tile(row, column));
        if(p == null || !p.getOwner().equals(this))
            return;
        
        ArrayList<Tile> possibleTile= board.tilesPieceCanMoveTo(p);
        board.makeTilesGlow(possibleTile);
        setActivePiece(p);
    }
    
    
    
    private boolean makeMove(int row, int column){
        Tile tile = new Tile(row, column);
        boolean madeAMove = false;

        for(Move m: possibleMovesForActivePiece){
            if(m.getNewTile().equals(tile)){
                board.applyMove(m);
                madeAMove=true;
            }
        }
        board.resetTiles();
        removeActivePiece();
        return madeAMove;
    }

    @Override
    public boolean handleTilePress(int row, int column) {
        if(active == null){
            showAvailableSquares(row, column);
            return false;
        } else {
            return makeMove(row, column);
        }
    }

    //since it needs to wait on user input wait
    @Override
    public boolean startTurn() {
        System.out.println("Hello there");
        return false;
    }
    
    
    
    
    
}
