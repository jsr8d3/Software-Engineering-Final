/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import java.util.ArrayList;

/**
 *
 * @author Jeb
 */
public class AILogic{
    Player player;
    
    public AILogic(Player player){
        this.player = player;
    }
    
    public Move getNextBoard(Board current){
        return maxValue(current, 6).getLastMove();
    }
    
    public Board maxValue(Board board, int depth){
        int heuristic = board.getBoardHeursticValue(player);
        if(Math.abs(heuristic) == Board.HEURISTIC_OF_END || depth == 0)
            return board;
        
        ArrayList<Board> nextBoards = board.getPossibleNextBoards(player);
        Board max = null;
        for(Board b: nextBoards){
            Board candidate = minValue(b, depth-1);
            if(max == null ||
                    max.getBoardHeursticValue(player) < candidate.getBoardHeursticValue(player)){
                max = b;
            }
        }
        return max;
    }
    
    public Board minValue(Board board, int depth){
        int heuristic = board.getBoardHeursticValue(player);
        if(Math.abs(heuristic) == Board.HEURISTIC_OF_END || depth == 0)
            return board;
        
        ArrayList<Board> nextBoards = board.getPossibleNextBoards(player);
        Board min = null;
        for(Board b: nextBoards){
            Board candidate = maxValue(b, depth-1);
            if(min == null ||
                    min.getBoardHeursticValue(player) < candidate.getBoardHeursticValue(player)){
                min = b;
            }
        }
        return min;
    }
}
