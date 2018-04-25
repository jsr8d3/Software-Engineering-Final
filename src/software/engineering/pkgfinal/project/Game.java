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
    Player playerOne;
    Player playerTwo;
    
    public static final String COMPUTER_NAME = "computer";
    
    public Game(Player playerOne, Player playerTwo){
        
    }
    
    public Game(String nameOfPlayerOne, String nameOfPlayerTwo){
        playerOne = createPlayerFromString(nameOfPlayerOne);
        playerTwo = createPlayerFromString(nameOfPlayerTwo);
    }
    
    private Player createPlayerFromString(String name){

        if(name.equals(COMPUTER_NAME)){
            return new ComputerPlayer();
        }
        
        return new HumanPlayer(name);
    }
}
