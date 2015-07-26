package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Move;

/**
 *
 * @author rafaelsakurai
 */
public class MoveUtil {
    public static boolean canMove(final Board board, final Move move) {
        return isFreePosition(board, move.getLine(), move.getColumn()) && !isSuicide(board, move);
    }
    
    public static boolean isFreePosition(final Board board, final int line, int column) {
        return board.getMove(line, column).isFree();
    }
    
    public static boolean isSuicide(final Board board, final Move m) {
        Move up = board.getMove(m.getLine() - 1, m.getColumn()); //up
        Move down = board.getMove(m.getLine() + 1, m.getColumn()); //down
        Move left = board.getMove(m.getLine(), m.getColumn() - 1); //left
        Move right = board.getMove(m.getLine(), m.getColumn() + 1); //right
        
        if (!((up != null && (up.samePlayer(m.getPlayer()) || up.isFree())) || (down != null && (down.samePlayer(m.getPlayer()) || down.isFree())) ||
                (left != null && (left.samePlayer(m.getPlayer()) || left.isFree())) || (right != null && (right.samePlayer(m.getPlayer()) || right.isFree())))) {
            if(!BoardUtil.willRemoveStones(board, m)) {
                throw new RuntimeException("Illegal moviment, suicide is not permited!");
            }
        }
        
        return false;
    }
}
