/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

/**
 *
 * @author Jeb
 */
public class Piece {
    private Tile position;
    private boolean isKing;
    private Player owner;
    
    public Piece(){
        
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
    }
            
    public boolean isKing(){
        return isKing;
    }
}
