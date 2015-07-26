package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Group;
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
        
        List<Move> removed = getPositionsToRemove(board, move.getPlayer());
        
        moves[move.getLine()][move.getColumn()] = tmp;
        
        return !removed.isEmpty();
    }
    
    public static List<Move> getPositionsToRemove(final Board board, final Player player) {
        List<Move> removedPositions = new ArrayList();
        
        //Find dead stones.
        for (Move m : board.getMovements()) {
            if(!m.samePlayer(player) && !hasLiberties(board, m)) {
                removedPositions.add(m);
            }
        }
        
        return removedPositions;
    }
    
    public static boolean hasLiberties(final Board board, final Move m) {
        return countLiberties(board, m) != 0;
    }
    
    public static void freePosition(final Board board, final Move move) {
        board.getBoard()[move.getLine()][move.getColumn()] = Move.free(move.getLine(), move.getColumn());
    }
    
    public static int countLiberties(final Board board, final Move m) {
        return getLiberties(board, m, new ArrayList(), m.getPlayer());
    }
    
    private static int getLiberties(final Board board, final Move m, final List<Move> visited, final Player player) {
        int cont = 0;
        
        if (!visited.contains(m)) {
            visited.add(m);
            cont += countLiberties(board, board.getMove(m.getLine() - 1, m.getColumn()), visited, player); //up
            cont += countLiberties(board, board.getMove(m.getLine() + 1, m.getColumn()), visited, player); //down
            cont += countLiberties(board, board.getMove(m.getLine(), m.getColumn() - 1), visited, player); //left
            cont += countLiberties(board, board.getMove(m.getLine(), m.getColumn() + 1), visited, player); //right
        }
        
        return cont;
    }
    
    private static int countLiberties(final Board board, final Move m, final List<Move> visited, final Player player) {
        int cont = 0;
        if (m != null) {
            if (m.isFree()) {
               cont++;
            } else if (m.samePlayer(player)) {
                cont += getLiberties(board, m, visited, player);
            }
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
                    n[i][j] = board.getBoard()[i][j].getPlayer().getColor();
                }
            }
        }
        
        return n;
    }
}
