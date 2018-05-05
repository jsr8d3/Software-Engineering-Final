/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author jebro
 */
public class CheckerBoard {
    
    private Stage stage = new Stage();
    private Group root = new Group();
    private Scene scene;
    private final GridPane checkerBoard;
    private int boardSize;
    public static final int TILE_LENGTH = 80;
    private ArrayList<Piece> pieces;
    
    public CheckerBoard(int boardSize, ArrayList<Piece> pieces){
        checkerBoard = new GridPane();
        scene = new Scene(root);
        scene.setFill(Color.ANTIQUEWHITE);
        this.boardSize = boardSize;
        this.pieces = pieces;
        
        fillCheckerBoard();
        
        for(Piece p: pieces){
            System.out.println(p);
        }
        
        root.getChildren().addAll(pieces);
        
        stage.setTitle("CheckerBoard");
        stage.setScene(scene);
        stage.show();
    }
    
    private void fillCheckerBoard(){
        
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                Rectangle r = new Rectangle(TILE_LENGTH*i, (boardSize-j-1)*TILE_LENGTH, TILE_LENGTH, TILE_LENGTH);
                if((i + j) % 2 == 0){
                    r.setFill(Color.BLACK);
                } else {
                    r.setFill(Color.RED);
                }
                root.getChildren().add(r);
                r.setOnMouseClicked((MouseEvent event) -> { //precursor for what comes next. Just making sure it was possible to read in mouse events on a rectangle.
                    System.out.println("Mouse Clicked here "+event.toString());
                });
            }
        }
        
    }
}
