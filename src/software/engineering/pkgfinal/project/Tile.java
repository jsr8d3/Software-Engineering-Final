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
public class Tile {
    
    private int row, column;
    
    public Tile(int row, int column){
        this.row = row;
        this.column = column;
    }
    
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Tile){
            Tile tile = (Tile)o;
            return ((tile.row == row) && (tile.column == column));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.row;
        hash = 41 * hash + this.column;
        return hash;
    }
    
    
}
