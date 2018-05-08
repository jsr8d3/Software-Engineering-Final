/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import javafx.application.Platform;

/**
 *
 * @author jebro
 */
public class ComputerPlayer extends Player{
    int difficulty;
    AILogic logic;

    public ComputerPlayer(Game game, Board board, boolean facingTop) {
        super(game, board, facingTop);
        logic = new AILogic(this);
    }

    @Override
    public boolean handleTilePress(int row, int column) {
        return false;
    }
    
    @Override
    public boolean startTurn(){
        Move m = logic.getNextBoard(board);
        final Move converted = board.convertMove(m);
        
        //board.applyMove(m);
        Platform.runLater(new Runnable() {
        @Override
            public void run() {
              board.applyMove(converted);
              try{
                  Thread.sleep(500);
              }catch(InterruptedException ie){
                  System.out.println("Why");
              }
              game.changeTurns();
            } 
        });
        return false;
    }
}
