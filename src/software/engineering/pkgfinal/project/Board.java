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
    private Player playerUp;
    private Player playerDown;
    //positive for player facing upwards
    private int heuristicValue;
    
    //class holds all the graphics
    private CheckerBoard checkerBoard;
    private Move lastMove;
    
    public Board(TilePress handler, Player up, Player down, int boardSize){
        if(up == null)
            playerUp = new ComputerPlayer(this, true);
        else
            playerUp = up;
        
        if(down == null)
            playerDown = new ComputerPlayer(this, false);
        else
            playerDown = down;
        
        this.boardSize = boardSize;
        pieces = new ArrayList();
        
        fillBoard();
        calculateBoardHeurstic();
        checkerBoard = new CheckerBoard(boardSize, pieces, handler);
        lastMove = null;
    }
    
    public Board(TilePress handler, Player up, Player down){
        this(handler, up, down, DEFAULT_BOARD_SIZE);
    }
    
    public Board(TilePress handler, int boardSize){
        this(handler, null, null, boardSize);   
    }
    
    public Board(TilePress handler){
        this(handler, DEFAULT_BOARD_SIZE);
    }
    
    //creates a copy of the board
    public Board(Board board){
        boardSize = board.boardSize;
        pieces = new ArrayList();
        for(Piece p: board.pieces)
            pieces.add(new Piece(p));
        tiles = board.tiles;
        playerUp = board.playerUp;
        playerDown = board.playerDown;
        
        calculateBoardHeurstic();
        this.lastMove = board.lastMove;
        
    }
    
    public void removePiece(Piece p){
        if(checkerBoard != null)
            checkerBoard.removePieceFromBoard(p);
        pieces.remove(p);
    }
    
    public Move convertMove(Move m){
        Move converted = null;
        for(Piece p: pieces){
            if(p.equals(m.getMovingPiece()))
            {
                converted = new Move(p, m.getNewTile());
                converted.addMultipleJumpTiles(m.getJumpedTile());
            }
        }
        return converted;
    }
    
    public void applyMove(Move move){
        Piece movedPiece = move.getMovingPiece();
        movedPiece.setNewPosition(move.getNewTile());
        ArrayList<Tile> jumpedTiles = move.getJumpedTile();
        Piece jumpedPiece = null;
        if(jumpedTiles != null){
            for(Tile jumped: jumpedTiles){
                if(jumped != null)
                    jumpedPiece = isPieceAtPosition(jumped);
                if(jumpedPiece != null)
                    removePiece(jumpedPiece);
            }
        }
        if(shouldPieceBecomeKing(movedPiece))
            movedPiece.makeKing();
        lastMove = move;
    }
    
    public Board applyTestMove(Move move){
        Board newBoard = new Board(this);
        Piece target = move.getMovingPiece();
        Piece movedPiece = null;
        for(Piece p: newBoard.pieces){
            if(p.equals(target)){
                movedPiece = p;
            }
        }
        ArrayList<Tile> jumpedTiles = move.getJumpedTile();
        Piece jumpedPiece = null;
        if(jumpedTiles != null){
            for(Tile jumped: jumpedTiles){
                if(jumped != null)
                    jumpedPiece = newBoard.isPieceAtPosition(jumped);
                if(jumpedPiece != null)
                    newBoard.removePiece(jumpedPiece);
            }
        }
        if(movedPiece == null)
            return null;
        if(shouldPieceBecomeKing(movedPiece))
            movedPiece.makeKing();
        lastMove = newBoard.convertMove(move);
        return newBoard;
    }
    
    public boolean shouldPieceBecomeKing(Piece p){
        if(p.isKing())
            return false;
        int row = p.getPosition().getRow();
        if(p.getOwner().isFacingTop() && row == boardSize-1)
            return true;
        if(!p.getOwner().isFacingTop() && row == 0)
            return true;
        
        return false;
    }
    
    private void fillBoard(){

        tiles = new Tile[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                tiles[i][j] = new Tile(i, j);
                if((i + j)%2 == 0){
                    if(i < 3){
                        Piece p = new Piece(playerUp, tiles[i][j], boardSize);
                        pieces.add(p);
                    } else if( i > boardSize - 4){
                        Piece p = new Piece(playerDown, tiles[i][j], boardSize);
                        pieces.add(p);
                    }
                }
            }
        }
        
        pieces.add(new Piece(playerDown, tiles[3][1], boardSize));
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
    
    public void applyBoard(Board board){
        pieces.clear();
        pieces.addAll(board.pieces);
        tiles = board.tiles;
        playerUp = board.playerUp;
        playerDown = board.playerDown;
    }
    
    public Piece isPieceAtPosition(Tile position){
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
        //ArrayList<Tile> possibleTiles = new ArrayList();
        ArrayList<Move> expandedMove = new ArrayList();
        
        if(newRow < 0 || newRow >= boardSize)
            return null;
        
        if(newColumn < 0 || newColumn >= boardSize)
            return null;
        
        Tile newPosition = tiles[newRow][newColumn];
        Piece p = isPieceAtPosition(newPosition);
        if(p == null){
            //expandedMove.add(new Move(piece, newPosition));
            return newPosition;
        }
        
        if(piece.onSameTeam(p))
            return null;
        
        newRow += rowChange;
        newColumn += columnChange;
        
        if(newRow < 0 || newRow >= boardSize)
            return null;
        
        if(newColumn < 0 || newColumn >= boardSize)
            return null;
        
         newPosition = tiles[newRow][newColumn];
        p = isPieceAtPosition(newPosition);
        if(p == null){
           return newPosition;
        } else{
            return null;
        }
        
    }
    
    private Tile validTile(int row, int column){
        if((row >= 0 && row < boardSize) && (column >= 0 && column < boardSize))
        {
            return tiles[row][column];
        }
        else
            return null;
    }
    
    private Tile getValidJump(Piece piece, Tile current, Tile jump){
        int currentRow = current.getRow();
        int currentColumn = current.getColumn();
        int newRow = jump.getRow();
        int newColumn = jump.getColumn();
        
        if(Math.abs(currentRow - newRow) != 2 || Math.abs(currentColumn - newColumn) != 2)
            return null;
        
        Tile inbetween = validTile((currentRow+newRow)/2, (currentColumn+newColumn)/2);
        Piece p = isPieceAtPosition(inbetween);
        if(p != null && !p.getOwner().equals(piece.getOwner()))
            return inbetween;
        return null;
    }
    
    public ArrayList<Move> treeSearch(Piece piece, ArrayList<Tile> past, Tile current){
        Tile possibleSpots[] = new Tile[4];
        Tile inbetweenSpots[] = new Tile[4];
        int currentColumn = current.getColumn();
        int currentRow = current.getRow();
        ArrayList<Move> moves = new ArrayList();
        
        if(piece.canMoveUpwards()){

            possibleSpots[DIAGNOL_UP_LEFT] = validTile(currentRow+2, currentColumn-2);
            inbetweenSpots[DIAGNOL_UP_LEFT] = validTile(currentRow+1, currentColumn-1);
            
            possibleSpots[DIAGNOL_UP_RIGHT] = validTile(currentRow+2, currentColumn+2);
            inbetweenSpots[DIAGNOL_UP_RIGHT] = validTile(currentRow+1, currentColumn+1);
        }
        if(piece.canMoveDownwards()){
            possibleSpots[DIAGNOL_DOWN_RIGHT] = validTile(currentRow-2, currentColumn+2);
            inbetweenSpots[DIAGNOL_DOWN_RIGHT] = validTile(currentRow-1, currentColumn+1);
            
            possibleSpots[DIAGNOL_DOWN_LEFT] = validTile(currentRow-2, currentColumn-2);
            inbetweenSpots[DIAGNOL_DOWN_LEFT] = validTile(currentRow-1, currentColumn-1);
        }
        
        int newJumps = 0;
        if(possibleSpots != null){
            for(Tile t: possibleSpots){
                if(t != null){
                    Piece landing = isPieceAtPosition(t);
                    if(landing == null){
                        Tile inbetween = validTile((currentRow+t.getRow())/2, (currentColumn+t.getColumn())/2);
                        Piece jumped = isPieceAtPosition(inbetween);
                        if(jumped != null && !past.contains(inbetween) && !jumped.getOwner().equals(piece.getOwner())){
                            newJumps++;
                            ArrayList<Tile> coppied = (ArrayList<Tile>)past.clone();
                            coppied.add(inbetween);
                            moves.addAll(treeSearch(piece, coppied, t));
                        }
                    }
                }
            }
        }
      
        Move move = new Move(piece, current);
        move.addMultipleJumpTiles(past);
        moves.add(move);
        
        
        return moves;
    }
    
    public ArrayList<Tile> tilesPieceCanMoveTo(Piece piece){
        ArrayList<Move> moves = movesAPieceCanMake(piece);
        ArrayList<Tile> tiles = new ArrayList();
        for(Move m: moves){
            tiles.add(m.getNewTile());
        }
        return tiles;
    }
    
    public ArrayList<Move> movesAPieceCanMake(Piece piece){
        Tile possibleSpots[] = new Tile[4];
        
        if(piece.canMoveUpwards())
        {
            possibleSpots[DIAGNOL_UP_LEFT] = getTileForDirection(piece, 1, -1);
            possibleSpots[DIAGNOL_UP_RIGHT] = getTileForDirection(piece, 1, 1);
        }
        if(piece.canMoveDownwards()){
            possibleSpots[DIAGNOL_DOWN_RIGHT] = getTileForDirection(piece, -1, 1);
            possibleSpots[DIAGNOL_DOWN_LEFT] = getTileForDirection(piece, -1, -1);
        }
        
        ArrayList<Move> possibleTiles = new ArrayList();
        for(Tile t: possibleSpots){
            if(t != null){
                Tile inbetween = getValidJump(piece, piece.getPosition(), t);
                if(inbetween != null){
                    ArrayList<Tile> past = new ArrayList();
                    past.add(inbetween);
                    possibleTiles.addAll(treeSearch(piece, past, t));
                }else{
                    possibleTiles.add(new Move(piece, t));
                }
            }
        }
        
        return possibleTiles;
    }
    
    public ArrayList<Move> possibleMoves(Player player){
        ArrayList<Move> moves = new ArrayList();
        for(Piece p: pieces){
            if(p.getOwner().equals(player)){
                moves.addAll(movesAPieceCanMake(p));
            }
        }
        return moves;
    }
    
    public Move getLastMove(){
        return lastMove;
    }
    
    public ArrayList<Board> getPossibleNextBoards(Player player){
        Board test = new Board(this);
        ArrayList<Move> moves = test.possibleMoves(player);
        ArrayList<Board> possibleBoards = new ArrayList();
        for(Move m: moves){
            Board outcome = applyTestMove(m);
            //outcome.applyMove(m);
            possibleBoards.add(outcome);
        }
        return possibleBoards;
    }
    
    public ArrayList<Piece> getPieces(){
        return pieces;
    }
}
