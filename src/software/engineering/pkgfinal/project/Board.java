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
public class Board {
    public static final int DEFAULT_BOARD_SIZE = 8;
    public static final int DIAGNOL_UP_LEFT = 0;
    public static final int DIAGNOL_UP_RIGHT = 1;
    public static final int DIAGNOL_DOWN_RIGHT = 2;
    public static final int DIAGNOL_DOWN_LEFT = 3;
    
    public static final int HEURISTIC_OF_END = 1000;
    
    
    private final int boardSize;
    private ArrayList<Piece> pieces;
    private Tile tiles[][];
    //positive for player facing upwards
    private int heuristicValue;
    
    public Board(int boardSize){
        this.boardSize = boardSize;
        fillBoard();
        calculateBoardHeurstic();
    }
    
    public Board(){
        this(DEFAULT_BOARD_SIZE);
    }
    
    //creates a copy of the board
    public Board(Board board){
        boardSize = board.boardSize;
        pieces = (ArrayList<Piece>)board.pieces.clone();
        tiles = board.tiles.clone();
        calculateBoardHeurstic();
    }
    
    public void applyMove(Move move){
        Piece movedPiece = move.getMovingPiece();
        movedPiece.setNewPosition(move.getNewTile());
        Tile jumped = move.getJumpedTile();
        Piece jumpedPiece = isPieceAtPosition(jumped);
        if(jumpedPiece != null)
            pieces.remove(jumpedPiece);
    }
    
    private void fillBoard(){
        tiles = new Tile[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                tiles[i][j] = new Tile(i, j);
            }
        }
        //TODO code that initializes the board
    }
    
    public int getNumberOfPieces(){
        int score = 0;
        for(Piece p: pieces){
            if(p.getOwner().isFacingTop()){
                if(p.isKing()){
                    score += 5;
                } else{
                    score += 3;
                }
            } else{
                if(p.isKing()){
                    score -= 5;
                } else{
                    score -= 3;
                }
            }
        }
        return score;
    }
    
    public int distanceFromBecomingKing(){
        int score = 0;
        for(Piece p: pieces){
            if(p.getOwner().isFacingTop()){
                if(p.isKing()){
                    score += 8;
                } else{
                    score += p.getPosition().getColumn();
                }
            } else {
                if(p.isKing()){
                    score -= 5;
                } else{
                    score -= boardSize - p.getPosition().getColumn();
                }
            }
        }
        return score;
    }
    
    private void calculateBoardHeurstic(){
        heuristicValue = getNumberOfPieces()*5;
        heuristicValue += distanceFromBecomingKing();
    }
    
    public int getBoardHeursticValue(Player player){
        return heuristicValue;
    }
    
    private Piece isPieceAtPosition(Tile position){
        for(Piece p: pieces){
            if(position.equals(p.getPosition()))
                return p;
        }
        return null;
    }
        
    private Tile getTileForDirection(Piece piece, int rowChange, int columnChange){
        Tile currentPosition = piece.getPosition();
        int currentRow = currentPosition.getRow();
        int currentColumn = currentPosition.getColumn();
        int newRow = currentRow + rowChange;
        int newColumn = currentColumn + columnChange;
        
        if(newRow < 0 || newRow > boardSize)
            return null;
        
        if(newColumn < 0 || newColumn > boardSize)
            return null;
        
        Tile newPosition = tiles[newRow][newColumn];
        Piece p = isPieceAtPosition(newPosition);
        if(p == null)
            return newPosition;
        
        if(piece.onSameTeam(p))
            return null;
        
        newRow += rowChange;
        newColumn += columnChange;
        
        if(newRow < 0 || newRow > boardSize)
            return null;
        
        if(newColumn < 0 || newColumn > boardSize)
            return null;
        
        newPosition = tiles[newRow][newColumn];
        p = isPieceAtPosition(newPosition);
        if(p == null)
            return newPosition;
        else
            return null;
        
    }
    
    public ArrayList<Tile> tilesPieceCanMoveTo(Piece piece){
        Tile possibleSpots[] = new Tile[4];
        
        if(piece.canMoveUpwards())
        {
            possibleSpots[DIAGNOL_UP_LEFT] = getTileForDirection(piece, -1, 1);
            possibleSpots[DIAGNOL_UP_RIGHT] = getTileForDirection(piece, 1, 1);
        }
        if(piece.canMoveDownwards()){
            possibleSpots[DIAGNOL_DOWN_RIGHT] = getTileForDirection(piece, 1, -1);
            possibleSpots[DIAGNOL_DOWN_LEFT] = getTileForDirection(piece, -1, -1);
        }
        
        ArrayList<Tile> possibleTiles = new ArrayList();
        for(Tile t: possibleSpots){
            if(t != null)
                possibleTiles.add(t);
        }
        
        return possibleTiles;
    }
    
    public ArrayList<Move> possibleMoves(Player player){
        ArrayList<Move> moves = new ArrayList();
        for(Piece p: pieces){
            if(p.getOwner().equals(player)){
                ArrayList<Tile> tilesPieceCanMove = tilesPieceCanMoveTo(p);
                for(Tile t: tilesPieceCanMove){
                    moves.add(new Move(p, t));
                }
            }
        }
        return moves;
    }
    
    public ArrayList<Board> getPossibleNextBoards(Player player){
        ArrayList<Move> moves = possibleMoves(player);
        ArrayList<Board> possibleBoards = new ArrayList();
        for(Move m: moves){
            Board outcome = new Board(this);
            outcome.applyMove(m);
            possibleBoards.add(outcome);
        }
        return possibleBoards;
    }
}
