package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Player;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Set of tests from GroupUtil.
 * 
 * @author rafaelsakurai
 */
public class GroupUtilTest {
    
    private static final int DEFAULT_SIZE = 9;
    private Board board = null;
    private Player w = null;
    private Player b = null;
    
    @Before
    public void initialize() {
        b = new Player("Rafael", "0k", Player.Color.BLACK);
        w = new Player("Sakurai", "0k", Player.Color.WHITE);
        board = new Board(DEFAULT_SIZE, 5.5, "Japanese", b, w, new Date());
    }
    
    /**
     * Test method getGroups that receive a board and return a list of Group.
     */
    @Test
    public void testFindGroups() {
        /*
         *  This board have 6 black groups and 2 white groups.
         * 
         *     0 1 2 3 4 5 6 7 8
         *  0 | | | | | |b|b|w| |
         *  1 | | | |b| |b|w|w| |
         *  2 | | | | | |b|w|w|b|
         *  3 | | |b|b|b|b|b|w| |
         *  4 | |b|b|w|b|w|w|w| |
         *  5 |b| |b|w|b|w|b|w| |
         *  6 |b|b|w|w|w|w|b|b| |
         *  7 |b|w|w|b|b|w|w|w| |
         *  8 |b|w| |w| | | | | |
         */
        board = KifuUtil.loadFile("target/test-classes/1808698-053-rafasakurai-stefanko3200.sgf");
        assertEquals(8, GroupUtil.getGroups(board).size());
        
        /**
         *  This board have 9 black groups and 7 white groups.
         * 
         *     0 1 2 3 4 5 6 7 8
         *  0 | |w|w|b|b|w|b|b| |
         *  1 | |w|b|b| |w|b| |b|
         *  2 |w|w|b| |b|b|b|b|w|
         *  3 | |w|w|b|w| |b|w|w|
         *  4 |w| |w|b|b|b|b| |b|
         *  5 |b|w|w|w|b|b| |b| |
         *  6 | |b|w|w|w|w|b| |b|
         *  7 |b|b|w| | |w|b| |b|
         *  8 | |w| |w|w|b|b|b| |
         */
        board = KifuUtil.loadFile("target/test-classes/1502011-085-rafasakurai-Theraiser.sgf");
        board.printBoard();
        assertEquals(16, GroupUtil.getGroups(board).size());
    }
    
    /**
     * Test method getGroupsByPlayer that receive a board and a player and return a list of groups belonging to the player.
     */
    @Test
    public void testFindGroupsPlayer() {
        board = KifuUtil.loadFile("target/test-classes/1502011-085-rafasakurai-Theraiser.sgf");
        assertEquals(9, GroupUtil.getGroupsByPlayer(board, board.getPlayerB()).size());
        assertEquals(7, GroupUtil.getGroupsByPlayer(board, board.getPlayerW()).size());
    }
    
    /**
     * Test method getGroupByPosition that receive a board and a x,y position and return a group if found in this position.
     */
    @Test
    public void testFindGroupByPosition() {
        board = KifuUtil.loadFile("target/test-classes/1502011-085-rafasakurai-Theraiser.sgf");
        //Verify if the group finded have 17 stones.
        assertEquals(17, GroupUtil.getGroupByPosition(board, 0, 1).size());
        
        assertNull(GroupUtil.getGroupByPosition(board, 0, 0));
    }
    
//    @Test
    public void testSuicide() {
        board = KifuUtil.loadFile("target/test-classes/1453986170552.sgf");
        assertFalse(board.getMove(8, 3).isFree());
    }
    
    /**
     *  This board have 9 black groups and 7 white groups.
     * 
     *     0 1 2 3 4 5 6 7 8
     *  0 | |w|w|b|b|w|b|b| |
     *  1 | |w|b|b| |w|b| |b|
     *  2 |w|w|b| |b|b|b|b|w|
     *  3 | |w|w|b|w| |b|w|w|
     *  4 |w| |w|b|b|b|b| |b|
     *  5 |b|w|w|w|b|b| |b| |
     *  6 | |b|w|w|w|w|b| |b|
     *  7 |b|b|w| | |w|b| |b|
     *  8 | |w| |w|w|b|b|b| |
     * 
     * 
     *  2,0 1,9 2,0 2,0 1,6 1,4 1,9 1,6 1,2 
     *  2,0 2,0 2,0 1,2 1,0 1,7 2,0 1,0 1,2 
     *  2,0 2,0 2,0 1,0 1,0 1,0 1,4 1,0 1,0 
     *  2,0 2,0 1,8 1,0 1,0 1,8 1,0 1,0 1,0 
     *  2,0 2,0 1,2 1,3 1,9 1,0 1,0 1,0 1,0 
     *  1,9 2,0 2,0 1,1 1,0 1,0 1,0 1,3 1,0 
     *  2,0 2,0 1,4 1,0 1,0 1,0 1,0 1,0 1,2 
     *  2,0 1,0 1,0 1,0 1,7 1,0 1,1 1,0 1,4 
     *  2,0 1,4 1,0 1,0 1,4 2,0 1,0 1,0 1,0 
     */
//    @Test
//    public void testHasAtLeastTwoEyesGroup() {
//        board = KifuUtil.loadFile("target/test-classes/1502011-085-rafasakurai-Theraiser.sgf");
//        assertEquals(17, GroupUtil.getGroupByPosition(board, 3, 4).getHasTwoEyes());
//    }
}
