package br.com.goapi;

import br.com.goapi.util.BoardUtil;
import br.com.goapi.util.MoveUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rafaelsakurai
 */
public class Board {
    private final int size;
    private final Move[][] board;
    private final List<Move> sequence;
    private final List<Move> undo;
    private final double komi;
    private final String rules;
    private final Player playerB;
    private final Player playerW;
    private final Date date;
    private final List<char[][]> lastStates = new ArrayList();
    
    /**
     * The default board start with a empty board, japanese rules, and no players.
     * @param boardSize 
     */
    public Board(final int boardSize) {
        this(boardSize, 0, "Japanese", Player.black("", ""), Player.white("", ""), new Date());
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
        if (canExecuteMove(move)) {
//            System.out.println("Execute " + move.getPlayer().getColor() + " Move at [" + (move.getLine() + 1) + "," + (move.getColumn() + 1) + "]");
            Move m = executeMove(move);
            undo.clear();
            return m;
        }
        
        return null;
    }
    
    public boolean canExecuteMove(final Move move) {
        return MoveUtil.canMove(this, move);
    }
    
    public Move executeMove(final Move move) {
        sequence.add(move);
        if (!move.isPass()) {
            board[move.getLine()][move.getColumn()] = move;
            List<Move> removed = removePositions(move.getPlayer());
            move.addAllRemovedPosition(removed);
            move.getPlayer().addCaptured(removed.size());
            saveLastStates();
        }
        
        return move;
    }
    
    public List<Move> removePositions(final Player player) {
        List<Move> removedPositions = BoardUtil.getPositionsToRemove(this.getMovements(), this.getBoard(), player);
        
        //Free positions on board.
        for (Move m : removedPositions) {
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
        board[m.getLine()][m.getColumn()] = Move.free(m.getLine(), m.getColumn());
        sequence.remove(m); //remove move from sequence of movements.
        if (!m.isPass() && !m.getRemovedPositions().isEmpty()) {
            m.getPlayer().addCaptured(-m.getRemovedPositions().size()); //remove player points
            for (Move move : m.getRemovedPositions()) {
                board[move.getLine()][move.getColumn()] = move;
            }
        }
        if (lastStates.size() > 0) {
            lastStates.remove(lastStates.size() - 1);
        }
    }
    
    public void redo() {
        if (!undo.isEmpty()) {
            Move m = undo.get(0);
            executeMove(m);
        }
    }
    
    public void saveLastStates() {
        char[][] backup = Arrays.stream(getBoardChar())
             .map((char[] row) -> row.clone())
             .toArray((int length) -> new char[length][]);
        
        //Save the last three board states
        if (lastStates.size() >= 3) {
            lastStates.remove(0);
        }
        
        lastStates.add(backup);
    }
    
    public boolean lastStatesContains(char[][] state) {
        if (lastStates.size() >= 2) {
            return isBoardEquals(state, lastStates.get(lastStates.size() - 1)) || isBoardEquals(state, lastStates.get(lastStates.size() - 2));
        }
        return false;
    }
    
    public boolean isBoardEquals(char[][] b1, char[][] b2) {
        int cont = 0;
        for (; cont < b1.length; cont++) {
            if(!Arrays.equals(b1[cont], b2[cont])) {
                break;
            }
        }
        return cont == b1.length;
    }

    public int getSize() {
        return size;
    }

    public Move[][] getBoard() {
        return board;
    }

    public List<Move> getMovements() {
        List<Move> moves = new ArrayList();
        for(Move[] ms : board) {
            for(Move m : ms) {
                if(!m.isFree()) {
                    moves.add(m);
                }
            }
        }
        return moves;
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
    
    public int[][] getBoardInt() {
        return BoardUtil.getBoardInt(this);
    }
    
    public void printBoard() {
        System.out.println("PB[" + playerB.getName() + "] BR[" + playerB.getRanking() + "] Points: " + playerB.getPoints());
        System.out.println("PW[" + playerW.getName() + "] WR[" + playerW.getRanking() + "] Points: " + playerW.getPoints());
        
        System.out.print("   ");
        for(int i = 1; i <= board.length; i++) {
            System.out.print('|');
            if(i < 10) {
                System.out.print('0');
            }
            System.out.print(i);
        }
        System.out.println('|');
        int cont = 1;
        for(Move[] line : board) {
            if (cont < 10) {
                System.out.print('0');
            }
            System.out.print(cont++ + " |");
            for(Move m : line) {
                System.out.print((m.isFree() ? " " : m.getPlayer().getColor()) + " |");
            }
            System.out.println("");
        }
    }
    
    public String boardToString() {
        String s = "PB[" + playerB.getName() + "] BR[" + playerB.getRanking() + "] Points: " + playerB.getPoints() + "\n";
        s += "PW[" + playerW.getName() + "] WR[" + playerW.getRanking() + "] Points: " + playerW.getPoints() + "\n";
        
        s += "   ";
        for(int i = 1; i <= board.length; i++) {
            s += '|';
            if(i < 10) {
                s += '0';
            }
            s += i;
        }
        s += "| \n";
        
        int cont = 1;
        for(Move[] line : board) {
            if (cont < 10) {
                s += '0';
            }
            s += cont++ + " |";
            for(Move m : line) {
                s += (m.isFree() ? " " : m.getPlayer().getColor()) + " |";
            }
            s += "\n";
        }
        return s;
    }
    
    public void printBoard(final char[][] board) {
        System.out.println("--------------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print((i+1) + "|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.printf("%c|", board[i][j]);
            }
            System.out.println("");
        }
        System.out.println("--------------------");
    }
    
    public void printBoard(final double[][] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print((i+1) + "|");
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] >= 0) {
                    System.out.print(" ");
                }
                System.out.printf("%.2f|", board[i][j]);
            }
            System.out.println("");
        }
    }
    
    public boolean isFinish() {
        return (sequence.size() > 2 && sequence.get(sequence.size() - 1).isPass() && sequence.get(sequence.size() - 2).isPass());
    }

    public List<Move> getFreeMovements() {
        List<Move> moves = new ArrayList();
        
        for (Move[] line : board) {
            for (Move m : line) {
                if (m.isFree()) {
                    moves.add(m);
                }
            }
        }
        
        return moves;
    }
}
