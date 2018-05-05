/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Jeb
 */
public class Piece extends Circle{
    private Tile position;
    private boolean isKing;
    private Player owner;
    private static final int pieceSize = 25;
    private int boardSize;
    
    public Piece(Player owner, Tile position, int boardSize){
        super(pieceSize);
        this.owner = owner;
        this.boardSize = boardSize;
        this.position = position;
        
        prefHeight(pieceSize);
        prefWidth(pieceSize);
        minHeight(pieceSize);
        minWidth(pieceSize);
        
        if(owner.isFacingTop()){
            setFill(Color.DARKGRAY);
        } else {
            setFill(Color.ALICEBLUE);
        }
        setBoardPosition();
    }
    
    private void setBoardPosition(){
        int tilex = position.getColumn()*CheckerBoard.TILE_LENGTH;
        int tiley = (boardSize - position.getRow() - 1)*CheckerBoard.TILE_LENGTH;
        
        setCenterX(tilex + CheckerBoard.TILE_LENGTH/2);
        setCenterY(tiley + CheckerBoard.TILE_LENGTH/2);
    }
    
    public Tile getPosition(){
        return position;
    }
    
    public boolean onSameTeam(Piece p)
    {
        return owner.equals(p.getOwner());
    }
    
    public Player getOwner(){
        return owner;
    }
    
    public boolean canMoveUpwards(){
        return (isKing || owner.isFacingTop());
    }
    
    public boolean canMoveDownwards(){
        return (isKing || !(owner.isFacingTop()));
    }
    
    public void setNewPosition(Tile newPosition){
        position = newPosition;
        setBoardPosition();
    }
            
    public boolean isKing(){
        return isKing;
    }
}
