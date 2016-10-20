package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Move;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author rafaelsakurai
 */
public class MoveUtil {
    public static boolean canMove(final Board board, final Move move) {
        if (move.isPass()) {
            return true;
        }
        
        boolean isFree = isFreePosition(board, move.getLine(), move.getColumn());
        if(!isFree) {
            throw new RuntimeException("Illegal moviment, occupied position!");
        }
        boolean isntSuicide = !isSuicide(board, move);
        boolean isntKo = !isKo(board, move);
        
        return isFree && isntSuicide && isntKo;
    }
    
    public static boolean isFreePosition(final Board board, final int line, int column) {
        return board.getMove(line, column).isFree();
    }
    
    public static boolean isSuicide(final Board board, final Move m) {
        List<Move> neighbors = getFreeOrSameNeighbor(board, m);
        
        if(BoardUtil.willRemoveStones(board, m)) {
            return false;
        }
        
        if(neighbors.isEmpty()) { 
            if(!BoardUtil.willRemoveStones(board, m)) {
                throw new RuntimeException("Illegal moviment [" + m.getLine() + "," + m.getColumn() + "], suicide is not permited!");
            }
        } else {
            for (Move n : neighbors) {
                if(!n.isFree() && m.samePlayer(n.getPlayer())) {
//                    System.out.println("Neighbor [" + n.getLine() + "," + n.getColumn() + "]");
                    List<Move> liberties = GroupUtil.getGroupLiberties(board, n.getLine(), n.getColumn());
                    if(liberties.size() == 1 && !BoardUtil.hasLiberties(board.getBoard(), m)) {
                        Move liberty = liberties.get(0);
//                        System.out.println("Liberty [" + liberty.getLine() + "," + liberty.getColumn() + "]");
                        if (liberty.getLine() == m.getLine() && liberty.getColumn() == m.getColumn()) {
                            throw new RuntimeException("Illegal moviment " + m.getPlayer().getColor() + " = [" + m.getLine() + "," + m.getColumn() + "], suicide is not permited!");
                        }
                    }
                }
            }
        }
        
        //TODO - Test this part
//        Move up = board.getMove(m.getLine() - 1, m.getColumn()); //up
//        Move down = board.getMove(m.getLine() + 1, m.getColumn()); //down
//        Move left = board.getMove(m.getLine(), m.getColumn() - 1); //left
//        Move right = board.getMove(m.getLine(), m.getColumn() + 1); //right
//        
//        if (!((up != null && (up.samePlayer(m.getPlayer()) || up.isFree())) || (down != null && (down.samePlayer(m.getPlayer()) || down.isFree())) ||
//                (left != null && (left.samePlayer(m.getPlayer()) || left.isFree())) || (right != null && (right.samePlayer(m.getPlayer()) || right.isFree())))) {
//            if(!BoardUtil.willRemoveStones(board, m)) {
//                throw new RuntimeException("Illegal moviment, suicide is not permited!");
//            }
//        }
//        
//        if (BoardUtil.willRemoveStonesFromPlayer(board, m, m.getPlayer().getColor())) {
//            throw new RuntimeException("Illegal moviment, suicide is not permited!");
//        }
        
        return false;
    }
    
    public static List<Move> getFreeOrSameNeighbor(final Board board, final Move m) {
        List<Move> moves = new ArrayList();
        
        addFreeOrSameNeighbor(moves, m, board.getMove(m.getLine() - 1, m.getColumn())); //up
        addFreeOrSameNeighbor(moves, m, board.getMove(m.getLine() + 1, m.getColumn())); //down
        addFreeOrSameNeighbor(moves, m, board.getMove(m.getLine(), m.getColumn() - 1)); //left
        addFreeOrSameNeighbor(moves, m, board.getMove(m.getLine(), m.getColumn() + 1)); //right
        
        return moves;
    }
    
    public static void addFreeOrSameNeighbor(final List<Move> moves, final Move m, final Move neighbor) {
        if (neighbor != null && (neighbor.samePlayer(m.getPlayer()) || neighbor.isFree())) {
            moves.add(neighbor);
        }
    }
    
    public static boolean isKo(final Board board, final Move m) {
        board.executeMove(m);
        
        char[][] backup = Arrays.stream(board.getBoardChar())
             .map((char[] row) -> row.clone())
             .toArray((int length) -> new char[length][]);
        
        board.undo();
        m.setId(0);
        m.getRemovedPositions().clear();
        
        boolean ko = board.lastStatesContains(backup);
        
        if (ko) {
            throw new RuntimeException("Illegal moviment, KO!");
        }
        
        return ko;
    }
}
