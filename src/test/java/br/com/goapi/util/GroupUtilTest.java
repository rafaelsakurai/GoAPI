package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Group;
import br.com.goapi.Move;
import br.com.goapi.Player;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
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
        b = new Player("Rafael", "0k", 'B');
        w = new Player("Sakurai", "0k", 'W');
        board = new Board(DEFAULT_SIZE, 5.5, "Japanese", b, w, new Date());
    }
    
    /**
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
    @Test
    public void testFindGroups() {
        board.move(Move.create(0, 5, b));
        board.move(Move.create(0, 6, b));
        board.move(Move.create(1, 3, b));
        board.move(Move.create(1, 5, b));
        board.move(Move.create(2, 5, b));
        board.move(Move.create(2, 8, b));
        board.move(Move.create(3, 2, b));
        board.move(Move.create(3, 3, b));
        board.move(Move.create(3, 4, b));
        board.move(Move.create(3, 5, b));
        board.move(Move.create(3, 6, b));
        board.move(Move.create(4, 1, b));
        board.move(Move.create(4, 2, b));
        board.move(Move.create(4, 4, b));
        board.move(Move.create(5, 0, b));
        board.move(Move.create(5, 2, b));
        board.move(Move.create(5, 4, b));
        board.move(Move.create(5, 6, b));
        board.move(Move.create(6, 0, b));
        board.move(Move.create(6, 1, b));
        board.move(Move.create(6, 6, b));
        board.move(Move.create(6, 7, b));
        board.move(Move.create(7, 0, b));
        board.move(Move.create(7, 3, b));
        board.move(Move.create(7, 4, b));
        board.move(Move.create(8, 0, b));
        
        board.move(Move.create(0, 7, w));
        board.move(Move.create(1, 6, w));
        board.move(Move.create(1, 7, w));
        board.move(Move.create(2, 6, w));
        board.move(Move.create(2, 7, w));
        board.move(Move.create(3, 7, w));
        board.move(Move.create(4, 3, w));
        board.move(Move.create(4, 5, w));
        board.move(Move.create(4, 6, w));
        board.move(Move.create(4, 7, w));
        board.move(Move.create(5, 3, w));
        board.move(Move.create(5, 5, w));
        board.move(Move.create(6, 2, w));
        board.move(Move.create(6, 3, w));
        board.move(Move.create(6, 4, w));
        board.move(Move.create(6, 5, w));
        board.move(Move.create(7, 1, w));
        board.move(Move.create(7, 2, w));
        board.move(Move.create(7, 5, w));
        board.move(Move.create(7, 6, w));
        board.move(Move.create(7, 7, w));
        board.move(Move.create(8, 1, w));
        board.move(Move.create(8, 3, w));
        
        board.printBoard();
        List<Group> groups = GroupUtil.getGroups(board);
        assertEquals(8, groups.size());
        
        System.out.println(groups);
    }
}
