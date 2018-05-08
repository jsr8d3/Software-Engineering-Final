/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
/**
 *
 * @author jebro
 */
public class Game implements TilePress{
    private Player playerOne;
    private Player playerTwo;
    
    private Board board;
    
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;
    private int turn = PLAYER_TWO;
    
    public static final String COMPUTER_EASY = "computer easy";
    public static final String COMPUTER_NORMAL = "computer normal";
    public static final String COMPUTER_HARD = "computer hard";
    
    public Game(Player playerOne, Player playerTwo){
        
    }
    
    public Game(String nameOfPlayerOne, String nameOfPlayerTwo){
        playerOne = createPlayerFromString(nameOfPlayerOne, true);
        playerTwo = createPlayerFromString(nameOfPlayerTwo, false);
    }
    
    private Player createPlayerFromString(String name, boolean top){

        if(name == null || name.equals(COMPUTER_EASY)){
            return new ComputerPlayer(this, board, top);
        } else if(name.equals(COMPUTER_NORMAL)){
            return new ComputerPlayer(this, board, top);
        } else if(name.equals(COMPUTER_HARD)){
            return new ComputerPlayer(this, board, top);
        }
        
        return new HumanPlayer(this, board, top, name);
    }
    
    public void start(){
        board = new Board(this, playerOne, playerTwo);
        playerOne.setBoard(board);
        playerTwo.setBoard((board));
        changeTurns();
    }
    
    public void changeTurns()
    {
        int state = board.getBoardHeursticValue(playerOne);
      if(state == Board.HEURISTIC_OF_END){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player 1 won!", ButtonType.OK);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.showAndWait();
            destroyGame();
            return;
            
        } else if(state == -Board.HEURISTIC_OF_END){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player 2 won!", ButtonType.OK);
            alert.showAndWait();
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            destroyGame();
            return;
        }
        
        Player nextPlayer;
        if(turn == PLAYER_ONE){
            turn = PLAYER_TWO;
            nextPlayer = playerTwo;
        }else{
            turn = PLAYER_ONE;
            nextPlayer = playerOne;
        }
        
        Task<Boolean> startTurn = new Task<Boolean>(){
            @Override
            protected Boolean call() throws Exception {
                if(nextPlayer.startTurn()){
                    changeTurns();
                }
                return true;
            }
        };
      
        Thread thread = new Thread(startTurn);
        thread.setDaemon(true);
        thread.start();
    }
        

    public void destroyGame(){
        board.destroy();
    }
    
    @Override
    public void onTilePressed(int row, int column) {
        if(turn == PLAYER_ONE){
            if(playerOne.handleTilePress(row, column))
               changeTurns();
        } else {
            if(playerTwo.handleTilePress(row, column))
                changeTurns();
        }
    }
    
    
}
