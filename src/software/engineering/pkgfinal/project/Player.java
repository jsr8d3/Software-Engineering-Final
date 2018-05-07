/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import java.util.Random;

/**
 *
 * @author jebro
 */
public abstract class Player{
    private boolean top;
    private final int playerID;
    protected Board board;
    
    public Player(Board board, boolean facingTop){
        top = facingTop;
        this.board = board;
        Random r = new Random();
        playerID = r.nextInt();
    }
    
    public boolean isFacingTop(){
        return top;
    }
    
    public void setBoard(Board board){
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Player)
        {
            return ((Player)o).playerID == playerID;
        }
        return false;
    }
    
    public abstract boolean startTurn();
    
    public abstract boolean handleTilePress(int row, int column);
}
