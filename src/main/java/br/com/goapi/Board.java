package br.com.goapi;

import br.com.goapi.util.BoardUtil;
import br.com.goapi.util.MoveUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rafaelsakurai
 */
public class Board {
    private final int size;
    private final Move[][] board;
    private final List<Move> movements;
    private final List<Move> sequence;
    private final List<Move> undo;
    private final double komi;
    private final String rules;
    private final Player playerB;
    private final Player playerW;
    private final Date date;
    
    /**
     * The default board start with a empty board, japanese rules, and no players.
     * @param boardSize 
     */
    public Board(final int boardSize) {
        this(boardSize, 0, "Japanese", null, null, new Date());
    }

    /**
     * Create a board especifying all values.
     * 
     * @param boardSize
     * @param komi
     * @param rules
     * @param playerB
     * @param playerW
     * @param date 
     */
    public Board(final int boardSize, final double komi, final String rules, final Player playerB, final Player playerW, final Date date) {
        this.size = boardSize;
        this.board = new Move[boardSize][boardSize];
        this.komi = komi;
        this.rules = rules;
        this.playerB = playerB;
        this.playerW = playerW;
        if (komi > 0) {
            this.playerW.setKomi(komi);
        }
        this.date = date;
        this.movements = new ArrayList();
        this.sequence = new ArrayList();
        this.undo = new ArrayList();
        
        for (int line = 0; line < boardSize; line++) {
            for(int column = 0; column < boardSize; column++) {
                board[line][column] = Move.free(line, column);
            }
        }
    }
    
    /**
     * Move on board.
     * 
     * @param move
     * @return 
     */
    public Move move(final Move move) {
        /* verify if is not a move pass and can execute the move. */
        if (!move.isPass() && MoveUtil.canMove(this, move)) {
            return executeMove(move);
        }
        
        return null;
    }
    
    public Move executeMove(final Move move) {
        sequence.add(move);
        movements.add(move);
        board[move.getLine()][move.getColumn()] = move;
        List<Move> removed = removePositions(move.getPlayer());
        move.addAllRemovedPosition(removed);
        move.getPlayer().addCaptured(removed.size());
        
        undo.clear();
        
        return move;
    }
    
    public List<Move> removePositions(final Player player) {
        List<Move> removedPositions = BoardUtil.getPositionsToRemove(this, player);
        
        //Free positions on board.
        for (Move m : removedPositions) {
            movements.remove(m);
            BoardUtil.freePosition(this, m);
        }
        
        return removedPositions;
    }
    
    public Move getMove(final int line, final int column) {
        if(line < 0 || line >= size || column < 0 || column >= size) {
            return null;
        }
        
        return board[line][column];
    }
    
    public void undo() {
        Move m = sequence.get(sequence.size() - 1);
        undo.add(m);
        sequence.remove(m); //remove move from sequence of movements.
        if(!m.getRemovedPositions().isEmpty()) {
            m.getPlayer().addCaptured(-m.getRemovedPositions().size()); //remove player points
            for (Move move : m.getRemovedPositions()) {
                movements.add(move);
            }
        }
    }
    
    public void redo() {
        if (!undo.isEmpty()) {
            Move m = undo.get(0);
            sequence.add(m);
            undo.remove(m);
            if(!m.getRemovedPositions().isEmpty()) {
                m.getPlayer().addCaptured(m.getRemovedPositions().size()); //add player points
                for (Move move : m.getRemovedPositions()) {
                    movements.remove(move);
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Move[][] getBoard() {
        return board;
    }

    public List<Move> getMovements() {
        return movements;
    }

    public double getKomi() {
        return komi;
    }

    public String getRules() {
        return rules;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public Player getPlayerW() {
        return playerW;
    }

    public Date getDate() {
        return date;
    }
    
    public char[][] getBoardChar() {
        return BoardUtil.getBoardChar(this);
    }
    
    public void printBoard() {
        System.out.println("PB[" + playerB.getName() + "] BR[" + playerB.getRanking() + "] Points: " + playerB.getPoints());
        System.out.println("PW[" + playerW.getName() + "] WR[" + playerW.getRanking() + "] Points: " + playerW.getPoints());
        for(Move[] line : board) {
            System.out.print("|");
            for(Move m : line) {
                System.out.print((m.isFree() ? " " : m.getPlayer().getColor()) + "|");
            }
            System.out.println("");
        }
    }
    
    public void printBoard(final char[][] board) {
        System.out.println("--------------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.printf("%c|", board[i][j]);
            }
            System.out.println("");
        }
        System.out.println("--------------------");
    }
    
    public void printBoard(final double[][] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] >= 0) {
                    System.out.print(" ");
                }
                System.out.printf("%.2f|", board[i][j]);
            }
            System.out.println("");
        }
    }
}
