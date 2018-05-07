/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package software.engineering.pkgfinal.project;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Jeb
 */
public class CheckerBoardController{

    private ArrayList<Rectangle> checkerTiles;
    private ArrayList<Piece> pieces;
    private int boardSize;
    
    public CheckerBoardController(ArrayList<Rectangle> tiles, ArrayList<Piece> pieces,int boardSize){
        checkerTiles = tiles;
        this.pieces = pieces;
        this.boardSize = boardSize;
    }
    
    public void initTileMouseEvents(){
        for(Rectangle r: checkerTiles){
            r.setOnMousePressed((MouseEvent event)->{
                handleCheckerTileMouseEvent(event);
            });
        }
    }
    

    
    public void handleCheckerTileMouseEvent(MouseEvent event){
        Rectangle target = (Rectangle) event.getTarget();
        int rectanglex = (int)target.getX();
        int rectangley = (int)target.getY();
                    
        int row = rectanglex/CheckerBoard.TILE_LENGTH;
        int column = boardSize - 1 - rectangley/CheckerBoard.TILE_LENGTH;
        System.out.println("Row = " + row + " | Column = " + column);
    }
}
