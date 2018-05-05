/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author Jeb
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ChoiceBox<?> playerOnePicker;    
    @FXML
    private ChoiceBox<?> playerTwoPicker;
    @FXML
    private Button playButton;
    

    
    //to later be retrieved from data base now just to make sure things work
    private static final String dummyNameData[] = {Game.COMPUTER_NAME, "Jeb", "Brighton", "Akshay", "Foo"};
     private static final List<String> names = new ArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //replace with retrieving code from the data base.
        for(String s: dummyNameData)
            names.add(s);
        ObservableList list = FXCollections.observableArrayList(names);
        playerOnePicker.setItems(list);
        playerTwoPicker.setItems(list);
    }    
    
    @FXML
    private void playButtonPressed(ActionEvent event) {
        //playerOnePicker.getValue();
        String nameForPlayerOne = (String) playerOnePicker.getValue();
        String nameForPlayerTwo = (String) playerTwoPicker.getValue();
        
        Game game = new Game(nameForPlayerOne, nameForPlayerTwo);
        System.out.println("Checking player names are correct p1 = " + nameForPlayerOne +"  and p2 = " + nameForPlayerTwo);
        game.start();
    }
    
}
