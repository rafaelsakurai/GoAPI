package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Move;
import br.com.goapi.Player;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author rafaelsakurai
 */
public class MoveUtilTest {
    
    private static final int DEFAULT_SIZE = 19;
    private Board board = null;
    private Player w = null;
    private Player b = null;
    
    @Before
    public void initialize() {
        b = new Player("Rafael", "0k", Player.Color.BLACK);
        w = new Player("Sakurai", "0k", Player.Color.WHITE);
        board = new Board(DEFAULT_SIZE, 0, "Japanese", b, w, new Date());
    }
    
//    @Test
    public void testFreePosition() {
        assertTrue(MoveUtil.isFreePosition(board, 0, 0));
        
        board.move(Move.create(1, 1, b));
        assertFalse(MoveUtil.isFreePosition(board, 1, 1));
    }
    
//    @Test(expected = RuntimeException.class)
    public void testCanMove() {
        Move m1 = Move.create(0, 0, b);
        assertTrue(MoveUtil.canMove(board, m1));
        board.move(m1);
        
        Move m2 = Move.create(0, 0, w);
        MoveUtil.canMove(board, m2);
    }
    
    /**
     * Suicide put a white stone in ? position.
     * 
     * - -b- -
     * -b-?-b-
     * - -b- -
     */
    @Test(expected = RuntimeException.class)
    public void testSuicide() {
        board.move(Move.create(1, 2, b));
        board.move(Move.create(2, 1, b));
        board.move(Move.create(3, 2, b));
        board.move(Move.create(2, 3, b));
        board.move(Move.create(2, 2, w));
    }
    
//    @Test(expected = RuntimeException.class)
    public void testSuicide2() {
        board.move(Move.create(16, 0, w));
        board.move(Move.create(16, 1, w));
        board.move(Move.create(16, 2, w));
        board.move(Move.create(17, 2, w));
        board.move(Move.create(18, 0, w));
        board.move(Move.create(18, 1, w));
        board.move(Move.create(18, 2, w));
        
        board.move(Move.create(17, 1, b));
        board.printBoard();
        board.move(Move.create(17, 0, b));
        board.printBoard();
    }
    
    /**
     * Board upper left corner:
     * ?-b-w- - 
     * b-w- - - 
     * w- - - - 
     */
//    @Test
    public void testAtariCornerBoard() {
        board.move(Move.create(0, 2, w));
        board.move(Move.create(1, 1, w));
        board.move(Move.create(2, 0, w));
        
        board.move(Move.create(0, 1, b)); //remove this stone.
        board.move(Move.create(1, 0, b)); //remove this stone.
        
        assertEquals(1, BoardUtil.countLiberties(board.getBoard(), board.getMove(0, 1)));
        
        Move m = Move.create(0, 0, w);
        board.move(m);
        
        assertEquals(2, m.getRemovedPositions().size()); //removed 2 stones.
        
        assertTrue(board.getMove(0, 1).isFree()); //now is empty.
        assertTrue(board.getMove(1, 0).isFree()); //now is empty.
        
        assertEquals(2, w.getPoints(), 0.1); //user have 2 points.
    }
    
    /**
     * Board bottom left corner
     * 
     * - -w-w-w- 
     * -w-b-b-b-w
     * -w-b-?-b-w
     * -w-b-b-w- 
     */
//    @Test
    public void testAtariSideBoard() {
        board.move(Move.create(18, 14, w));
        board.move(Move.create(17, 14, w));
        board.move(Move.create(16, 14, w));
        board.move(Move.create(15, 15, w));
        board.move(Move.create(15, 16, w));
        board.move(Move.create(15, 17, w));
        board.move(Move.create(16, 18, w));
        board.move(Move.create(17, 18, w));
        board.move(Move.create(18, 17, w));
        
        board.move(Move.create(18, 15, b)); //Remove this stone
        board.move(Move.create(17, 15, b)); //Remove this stone
        board.move(Move.create(16, 15, b)); //Remove this stone
        board.move(Move.create(16, 16, b)); //Remove this stone
        board.move(Move.create(16, 17, b)); //Remove this stone
        board.move(Move.create(17, 17, b)); //Remove this stone
        board.move(Move.create(18, 16, b)); //Remove this stone
        
        Move m = Move.create(17, 16, w);
        board.move(m);
        
        assertEquals(7, m.getRemovedPositions().size());
        
        assertTrue(board.getMove(18, 15).isFree());
        assertTrue(board.getMove(17, 15).isFree());
        assertTrue(board.getMove(16, 15).isFree());
        assertTrue(board.getMove(16, 16).isFree());
        assertTrue(board.getMove(16, 17).isFree());
        assertTrue(board.getMove(17, 17).isFree());
        assertTrue(board.getMove(18, 16).isFree());
        
        assertEquals(7, w.getPoints(), 0.1);
    }
    
    /**
     * Board upper right corner
     * 
     * -w-b-?
     * -w-b-w
     * -w-b-b
     * -w-b-b
     * - -w-w
     * 
     */
//    @Test
    public void testAtari2() {
        board.move(Move.create(0, 17, b)); //Remove this stone
        board.move(Move.create(0, 16, w));
        board.move(Move.create(1, 17, b)); //Remove this stone
        board.move(Move.create(1, 16, w));
        board.move(Move.create(2, 17, b)); //Remove this stone
        board.move(Move.create(2, 16, w));
        board.move(Move.create(3, 17, b)); //Remove this stone
        board.move(Move.create(3, 16, w));
        board.move(Move.create(3, 18, b)); //Remove this stone
        board.move(Move.create(4, 17, w));
        board.move(Move.create(4, 18, w));
        board.move(Move.create(1, 18, w));
        board.move(Move.create(2, 18, b)); //Remove this stone
        
        Move m = Move.create(0, 18, w);
        board.move(m);
        
        assertEquals(6, m.getRemovedPositions().size());
        assertTrue(board.getMove(0, 17).isFree());
        assertTrue(board.getMove(1, 17).isFree());
        assertTrue(board.getMove(2, 17).isFree());
        assertTrue(board.getMove(3, 17).isFree());
        assertTrue(board.getMove(3, 18).isFree());
        assertTrue(board.getMove(2, 18).isFree());
        assertEquals(6, w.getPoints(), 0.1);
    }
    
    /**
     * ?-b-b-w-
     * b-?-b-w-
     * b-b-w- -
     * w-w-w- -
     */
//    @Test(expected = RuntimeException.class)
    public void testTwoEyes() {
        board.move(Move.create(0, 1, b));
        board.move(Move.create(0, 2, b));
        board.move(Move.create(0, 3, w));
        board.move(Move.create(1, 0, b));
        board.move(Move.create(1, 2, b));
        board.move(Move.create(1, 3, w));
        board.move(Move.create(2, 0, b));
        board.move(Move.create(2, 1, b));
        board.move(Move.create(2, 2, w));
        board.move(Move.create(3, 0, w));
        board.move(Move.create(3, 1, w));
        board.move(Move.create(3, 2, w));
        
        board.move(Move.create(1, 1, w)); //Suicide
    }
    
    @Test
    public void testGetEmptyPositions() {
        assertEquals(361, board.getFreeMovements().size());
        
        board = KifuUtil.loadFile("target/test-classes/1808698-053-rafasakurai-stefanko3200.sgf");
        assertEquals(31, board.getFreeMovements().size());
        
        board = KifuUtil.loadFile("target/test-classes/1502011-085-rafasakurai-Theraiser.sgf");
        assertEquals(20, board.getFreeMovements().size());
    }
    
    /**
     * 
     *  -b-w-
     * b-w-?-w
     *  -b-w- 
     * 
     */
//    @Test(expected = RuntimeException.class)
    public void testKO() {
        board.move(Move.create(0, 1, b));
        board.move(Move.create(0, 2, w));
        board.move(Move.create(1, 0, b));
        board.move(Move.create(1, 1, w));
        board.move(Move.create(1, 3, w));
        board.move(Move.create(2, 1, b));
        board.move(Move.create(2, 2, w));
        
        board.move(Move.create(1, 2, b)); // OK
        assertTrue(board.getMove(1, 1).isFree());
        /*
          -b-w-
         b-?-b-w
          -b-w- 
        */
        board.move(Move.create(1, 1, w)); // KO
    }
}
