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
    private ArrayList<Rectangle> checkerTiles;
    private ArrayList<Piece> pieces;
    //private CheckerBoardController controller;
    
    public CheckerBoard(int boardSize, ArrayList<Piece> pieces, TilePress tileHandler){
        checkerBoard = new GridPane();
        scene = new Scene(root);
        scene.setFill(Color.ANTIQUEWHITE);
        checkerTiles = new ArrayList();
        this.boardSize = boardSize;
        this.pieces = pieces;
        //controller = new CheckerBoardController(checkerTiles, pieces, boardSize);
        
        fillCheckerBoard();
        
        
        for(Piece p: pieces){
            System.out.println(p);
        }
        
        for(Piece p: pieces){
            root.getChildren().add(p.getCircle());
        }
        //controller.initTileMouseEvents();
        
        //putting the mouse handler on the scene. Putting it one each rectangle makes the ui unresponsive
        scene.setOnMousePressed((MouseEvent event)->{
                    int rectanglex = (int)event.getSceneX();
                    int rectangley = (int)event.getSceneY();
                    
                    int column = rectanglex/TILE_LENGTH;
                    int row = boardSize - 1 - rectangley/TILE_LENGTH;
                    
                    tileHandler.onTilePressed(row, column);
        });
        
        stage.setTitle("CheckerBoard");
        stage.setScene(scene);
        stage.show();
    }
    
    public void lightUpTiles(ArrayList<Tile> tiles){
        for(Rectangle r: checkerTiles){
            int rectanglex = (int)r.getX();
            int rectangley = (int)r.getY();

            int column = rectanglex/TILE_LENGTH;
            int row = boardSize - 1 - rectangley/TILE_LENGTH;
            Tile rectangleTile = new Tile(row, column);
            for(Tile t: tiles){
                if(t.equals(rectangleTile)){
                    r.setFill(Color.DARKSLATEBLUE);
                }
            }
        }
    }
    
    public void recolorTiles(){
        for(Rectangle r: checkerTiles){
            int rectanglex = (int)r.getX();
            int rectangley = (int)r.getY();

            int column = rectanglex/TILE_LENGTH;
            int row = boardSize - 1 - rectangley/TILE_LENGTH;

            if((row+column)%2 == 0){
                r.setFill(Color.BLACK);
            } else {
                r.setFill(Color.RED);
            }
        }
    }
    
    public void destroy(){
        stage.close();
    }
    
    public void removePieceFromBoard(Piece p){
        if(!pieces.contains(p))
            return;
        root.getChildren().remove(p.getCircle());
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
                /*r.setOnMouseClicked((MouseEvent event) -> { 
                    Rectangle target = (Rectangle) event.getTarget();
                    int rectanglex = (int)target.getX();
                    int rectangley = (int)target.getY();
                    
                    int row = rectanglex/TILE_LENGTH;
                    int column = boardSize - 1 - rectangley/TILE_LENGTH;
                    
                    System.out.println("Row = " + row + " | Column = " + column);
                });*/
                checkerTiles.add(r);
            }
        }
        
    }
}
