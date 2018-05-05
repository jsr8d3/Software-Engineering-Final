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
public class Player {
    private boolean top;
    private int playerID;
    
    public Player(boolean facingTop){
        top = facingTop;
    }
    
    public boolean isFacingTop(){
        return top;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Player)
        {
            return ((Player)o).playerID == playerID;
        }
        return false;
    }
    
    
}
