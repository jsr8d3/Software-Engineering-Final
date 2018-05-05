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
public class Game {
    private Player playerOne;
    private Player playerTwo;
    
    private Board board;
    private CheckerBoard checkerBoard;
    
    
    public static final String COMPUTER_NAME = "computer";
    
    public Game(Player playerOne, Player playerTwo){
        
    }
    
    public Game(String nameOfPlayerOne, String nameOfPlayerTwo){
        playerOne = createPlayerFromString(nameOfPlayerOne, true);
        playerTwo = createPlayerFromString(nameOfPlayerTwo, false);
    }
    
    private Player createPlayerFromString(String name, boolean top){

        if(name == null || name.equals(COMPUTER_NAME)){
            return new ComputerPlayer(top);
        }
        
        return new HumanPlayer(name, top);
    }
    
    public void start(){
        board = new Board(playerOne, playerTwo);
        checkerBoard = new CheckerBoard(8, board.getPieces());
    }
}
