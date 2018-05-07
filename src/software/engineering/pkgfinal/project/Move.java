/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author jebro
 */
public class Move {
    private Piece movingPiece;
    private Tile newTile;
    private boolean jumps;
    private ArrayList<Tile> jumpedTile;
    
    public Move(Piece piece, Tile tile){
        movingPiece = piece;
        newTile = tile;
        jumpedTile = new ArrayList();
        jumps = calculateIsJump();
        //calculateJumpedTile();
    }
    
    private boolean calculateIsJump(){
        Tile currentTile = movingPiece.getPosition();
        int deltaRow = newTile.getRow() - currentTile.getRow();
        int deltaColumn = newTile.getColumn() - currentTile.getColumn();

        return Math.abs(deltaRow) > 1 || Math.abs(deltaColumn) > 1;
    }
    
    /*private void calculateJumpedTile(){
        Tile currentTile = movingPiece.getPosition();
        int deltaRow = newTile.getRow() - currentTile.getRow();
        int deltaColumn = newTile.getColumn() - currentTile.getColumn();
        
        if(Math.abs(deltaRow) > 1 && Math.abs(deltaColumn) > 1){
            jumpedTile = new Tile(currentTile.getRow()+deltaRow/2, currentTile.getColumn() + deltaColumn/2);
        } else{
            jumpedTile = null;
        }
    }*/
    
    public void addJumpTile(Tile tile){
        jumpedTile.add(tile);
    }
    
    public void addMultipleJumpTiles(Collection<Tile> tiles){
        jumpedTile.addAll(tiles);
    }
    
    public Piece getMovingPiece(){
        return movingPiece;
    }
    
    public Tile getNewTile(){
        return newTile;
    }
    
    public ArrayList<Tile> getJumpedTile(){
        return jumpedTile;
    }
    
    public boolean isJumping(){
        return jumpedTile.isEmpty();
    }
}
