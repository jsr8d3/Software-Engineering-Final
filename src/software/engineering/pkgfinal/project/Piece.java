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
public class Piece{
    private Tile position;
    private boolean isKing;
    private Player owner;
    private static final int pieceSize = 25;
    private int boardSize;
    private Color color;
    private Circle circle;
    
    public Piece(Piece piece){
        position = piece.position;
        isKing = piece.isKing;
        owner = piece.owner;
        boardSize = piece.boardSize;
        color = piece.color;
        circle = null;
    }
    
    public Piece(Player owner, Tile position, int boardSize){
        
        this.owner = owner;
        this.boardSize = boardSize;
        this.position = position;
        
        circle = new Circle(pieceSize);
        circle.prefHeight(pieceSize);
        circle.prefWidth(pieceSize);
        circle.minHeight(pieceSize);
        circle.minWidth(pieceSize);
        
        if(owner.isFacingTop()){
            color = Color.DARKGRAY;
        } else {
            color = Color.ALICEBLUE;
        }
        circle.setFill(color);
        setBoardPosition();
    }
    
    private void setBoardPosition(){
        if(circle != null){
            int tilex = position.getColumn()*CheckerBoard.TILE_LENGTH;
            int tiley = (boardSize - position.getRow() - 1)*CheckerBoard.TILE_LENGTH;
        
            circle.setCenterX(tilex + CheckerBoard.TILE_LENGTH/2);
            circle.setCenterY(tiley + CheckerBoard.TILE_LENGTH/2);
        }
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
    
    public void setActive(boolean active){
        if(circle != null){
            if(active){
                circle.setFill(Color.GOLD);
            } else {
                circle.setFill(color);
            }
        }
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
    
    public void makeKing(){
        if(circle != null){
            circle.setStroke(Color.GOLDENROD);
            circle.setStrokeWidth(5);
        }
        isKing = true;
    }
            
    public boolean isKing(){
        return isKing;
    }
    
    public Circle getCircle(){
        return circle;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Piece))
            return false;
        Piece p = (Piece)obj;
        return position.equals(p.position) && owner.equals(p.owner); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
