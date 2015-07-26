package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Group;
import br.com.goapi.Move;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafaelsakurai
 */
public class GroupUtil {
    /**
     * With base in a board, get all groups create by black and white stones.
     * @param board
     * @return 
     */
    public static List<Group> getGroups(final Board board) {
        List<Group> groups = new ArrayList();
        
        for (Move[] moves : board.getBoard()) {
            for (Move m : moves) {
                if(!m.isFree())
                    getGroup(groups, board, m).addMove(m);
            }
        }
        
        addGroupInformation(groups, board);
        
        return groups;
    }
    
    /**
     * Add some informations to the group, like:
     * - liberties;
     * - opponents;
     * 
     * @param groups
     * @param board 
     */
    public static void addGroupInformation(final List<Group> groups, final Board board) {
        for (Group g : groups) {
            for (Move m : g.getMoves()) {
                addInformation(board, m, m.getLine() - 1, m.getColumn());
                addInformation(board, m, m.getLine() + 1, m.getColumn());
                addInformation(board, m, m.getLine(), m.getColumn() - 1);
                addInformation(board, m, m.getLine(), m.getColumn() + 1);
            }
        }
    }
    
    /**
     * Add some information in a specific move, like:
     * - liberties;
     * - opponents;
     * 
     * @param board
     * @param move
     * @param line
     * @param column 
     */
    public static void addInformation(final Board board, final Move move, final int line, final int column) {
        if (line >= 0 && line < board.getBoard().length && column >= 0 && column < board.getBoard().length) {
            Move m = board.getMove(line, column);
            if (m.isFree()) {
                move.setLiberties(move.getLiberties() + 1);
            } else if (!m.getPlayer().equals(move.getPlayer())) {
                move.setOpponents(move.getOpponents() + 1);
            }
        }
    }
    
    /**
     * Find one or more groups that will be connected with the move parameter.
     * 
     * @param groups
     * @param board
     * @param move
     * @return 
     */
    public static Group getGroup(final List<Group> groups, final Board board, final Move move) {
        List<Group> selected = new ArrayList();
        
        for (Group g : groups) {
            getPlayerGroup(move, g, board, selected, move.getLine() - 1, move.getColumn());
            getPlayerGroup(move, g, board, selected, move.getLine() + 1, move.getColumn());
            getPlayerGroup(move, g, board, selected, move.getLine(), move.getColumn() - 1);
            getPlayerGroup(move, g, board, selected, move.getLine(), move.getColumn() + 1);
        }
        
        if(selected.isEmpty()) {            
            Group group = new Group(move.getPlayer());
            groups.add(group);
            return group;
        }
        
        if(selected.size() == 1) {
            return selected.get(0);
        } else {
            return mergeGroups(groups, selected);
        }
    }

    /**
     * Verifify if the move with line and column parameter isn't free, if is inside of the board and is from same player to create a group.
     * 
     * @param move - the original move parameter.
     * @param group - that can contain the move especified by line and column.
     * @param board - the entire board.
     * @param selected - add the group in this list, if contain the move especified by line and column.
     * @param line - of neighbor move.
     * @param column  - of neighbor move.
     */
    public static void getPlayerGroup(final Move move, final Group group, final Board board, final List<Group> selected, final int line, final int column) {
        if(!move.isFree() && line >= 0 && line < board.getBoard().length && column >= 0 && column < board.getBoard().length) {
            Move m = board.getMove(line, column);
            if(!m.isFree() && m.getPlayer().equals(move.getPlayer()) && groupHasMove(group, m)){
                selected.add(group);
            }
        }
    }
    
    /**
     * Verify if group has the move.
     * 
     * @param group
     * @param move
     * @return 
     */
    public static boolean groupHasMove(final Group group, final Move move) {
        return group.getMoves().stream().anyMatch((m) -> (m.equals(move)));
    }
    
    /**
     * Merge two or more groups in order to create a new group.
     * 
     * @param groups
     * @param selected
     * @return 
     */
    public static Group mergeGroups(final List<Group> groups, final List<Group> selected) {
        Group group = new Group(selected.get(0).getPlayer());
        
        groups.removeAll(selected); //Remove the small groups.
        groups.add(group); //Add the new big group.
        
        for (Group s : selected) {
            group.addAllMoves(s.getMoves());
        }
        
        return group;
    }
    
    public static List<Group> getGroupsInAtari(final Board board) {
        List<Group> atari = new ArrayList();
        
        List<Group> groups = getGroups(board);
        for (Group g : groups) {
            if (g.isInAtari())
                atari.add(g);
        }
        
        return atari;
    }
}
