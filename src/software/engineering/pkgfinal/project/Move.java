/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

/**
 *
 * @author jebro
 */
public class Move {
    private Piece movingPiece;
    private Tile newTile;
    private boolean isJump;
    
    public Move(Piece piece, Tile tile){
        movingPiece = piece;
        newTile = tile;
        
       isJump = calculateIsJump();
        
    }
    
    private boolean calculateIsJump(){
        Tile currentTile = movingPiece.getPosition();
        int deltaRow = newTile.getRow() - currentTile.getRow();
        int deltaColumn = newTile.getColumn() - currentTile.getColumn();

        return Math.abs(deltaRow) > 1 || Math.abs(deltaColumn) > 1;
    }
}
