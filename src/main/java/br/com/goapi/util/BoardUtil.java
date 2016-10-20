package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Move;
import br.com.goapi.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafaelsakurai
 */
public class BoardUtil {
    public static boolean willRemoveStones(final Board board, final Move move) {
        Move[][] moves = board.getBoard();
        Move tmp = moves[move.getLine()][move.getColumn()];
        moves[move.getLine()][move.getColumn()] = move;
        
        List<Move> removed = getPositionsToRemove(board.getMovements(), board.getBoard(), move.getPlayer());
        
        moves[move.getLine()][move.getColumn()] = tmp;
        
        return !removed.isEmpty();
    }
    
    public static boolean willRemoveStonesFromPlayer(final Board board, final Move move, final char player) {
        List<Move> removed = new ArrayList();
        
        
        
        //Find dead stones.
//        for (Move m : board.getMovements()) {
            if(!hasLiberties(board.getBoard(), move) && !move.isPass()) {
                removed.add(move);
            }
//        }
        
        if (removed.size() > 0) {
            for (Move m : removed) {
                if (m.getPlayer().getColor().getValue() == player) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public static List<Move> getPositionsToRemove(final List<Move> movements, final Move[][] board, final Player player) {
        List<Move> removedPositions = new ArrayList();
        
        //Find dead stones.
        for (Move m : movements) {
            if(!m.samePlayer(player) && !hasLiberties(board, m) && !m.isPass()) {
                removedPositions.add(m);
            }
        }
        
        return removedPositions;
    }
    
    public static boolean hasLiberties(final Move[][] board, final Move m) {
        return countLiberties(board, m) != 0;
    }
    
    public static void freePosition(final Board board, final Move move) {
        board.getBoard()[move.getLine()][move.getColumn()] = Move.free(move.getLine(), move.getColumn());
    }
    
    public static int countLiberties(final Move[][] board, final Move m) {
        return getLiberties(board, m, new ArrayList(), m.getPlayer());
    }
    
    private static int getLiberties(final Move[][] board, final Move m, final List<Move> visited, final Player player) {
        int cont = 0;
        
        if (!visited.contains(m)) {
            visited.add(m);
            cont += countLiberties(board, m.getLine() - 1, m.getColumn(), visited, player); //up
            cont += countLiberties(board, m.getLine() + 1, m.getColumn(), visited, player); //down
            cont += countLiberties(board, m.getLine(), m.getColumn() - 1, visited, player); //left
            cont += countLiberties(board, m.getLine(), m.getColumn() + 1, visited, player); //right
        }
        
        return cont;
    }
    
    private static int countLiberties(final Move[][] board, final int line, final int column, final List<Move> visited, final Player player) {
        if(line < 0 || line >= board.length || column < 0 || column >= board.length) {
            return 0;
        }
        
        int cont = 0;
        Move m = board[line][column];
        if (m.isFree()) {
           cont++;
        } else if (m.samePlayer(player)) {
            cont += getLiberties(board, m, visited, player);
        }
        return cont;
    }
    
    public static List<Move> getStonesInAtari(final Board board, final Player player) {
        List<Move> atari = new ArrayList();
        GroupUtil.getGroupsInAtari(board).stream().forEach((g) -> {
            atari.addAll(g.getMoves());
        });
        return atari;
    }
    
    public static char[][] getBoardChar(final Board board) {
        char[][] n = new char[board.getBoard().length][board.getBoard().length];
        
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == null || board.getBoard()[i][j].isFree()) {
                    n[i][j] = ' ';
                } else {
                    n[i][j] = board.getBoard()[i][j].getPlayer().getColor().getValue();
                }
            }
        }
        
        return n;
    }
    
    public static int[][] getBoardInt(final Board board) {
        int[][] n = new int[board.getBoard().length][board.getBoard().length];
        
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == null || board.getBoard()[i][j].isFree()) {
                    n[i][j] = 0;
                } else {
                    n[i][j] = board.getBoard()[i][j].getPlayer().getColor() == Player.Color.BLACK ? -1 : 1;
                }
            }
        }
        
        return n;
    }
}
