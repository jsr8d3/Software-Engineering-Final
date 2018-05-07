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
public class ComputerPlayer extends Player{
    int difficulty;
    AILogic logic;

    public ComputerPlayer(Board board, boolean facingTop) {
        super(board, facingTop);
        logic = new AILogic(this);
    }

    @Override
    public boolean handleTilePress(int row, int column) {
        return false;
    }
    
    @Override
    public boolean startTurn(){
        Move m = logic.getNextBoard(board);
        m = board.convertMove(m);
        board.applyMove(m);
        return true;
    }
}
