package br.com.goapi;

import br.com.goapi.util.BoardUtil;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author rafaelsakurai
 */
public class BoardTest {

    private static final int DEFAULT_SIZE = 19;
    private Board board = null;
    private Player w = null;
    private Player b = null;

    @Before
    public void initialize() {
        board = new Board(DEFAULT_SIZE);
        b = new Player("Rafael", "0k", Player.Color.BLACK);
        w = new Player("Sakurai", "0k", Player.Color.WHITE);
    }

    /**
     * Test make a valid move in an empty position.
     */
    @Test
    public void testMove() {
        assertTrue(board.move(Move.create(0, 0, b)) != null);
        assertEquals(Move.create(0, 0, b), board.getMovements().get(0));
        assertEquals(Move.create(0, 0, b), board.getMove(0, 0));

        System.out.println(board);
    }

    /**
     * Test make an invalid move in an occupied position.
     */
    @Test(expected = RuntimeException.class)
    public void testPlayOccupiedMoviment() {
        board.move(Move.create(0, 0, b));
        board.move(Move.create(0, 0, w));
    }

    /**
     * Test capture a stone in the middle of board.
     */
    @Test
    public void testRemoveDeadStoneMiddleBoard() {
        board.move(Move.create(2, 2, w)); //remove this stone.

        board.move(Move.create(1, 2, b));
        board.move(Move.create(3, 2, b));
        board.move(Move.create(2, 1, b));
        Move m = Move.create(2, 3, b);
        board.move(m);
        
        //Verify if the stone was removed.
        assertEquals(Move.create(2, 2, w), m.getRemovedPositions().get(0));

        //Verify if the position on board is free.
        assertTrue(board.getMove(2, 2).isFree());
    }

    /**
     * Test capture a stone in the corner of board.
     */
    @Test
    public void testRemoveDeadStoneCornerBoard() {
        board.move(Move.create(0, 0, w)); //remove this stone.

        board.move(Move.create(1, 0, b));
        Move m = Move.create(0, 1, b);
        board.move(m);
        assertEquals(Move.create(0, 0, w), m.getRemovedPositions().get(0));

        assertTrue(board.getMove(0, 0).isFree());
    }

    
    @Test
    public void testCountLibertiesCorner() {
        Move m = Move.create(0, 0, w);
        board.move(m);
        assertEquals(2, BoardUtil.countLiberties(board.getBoard(), m));
        
        board.move(Move.create(0, 1, b));
        assertEquals(1, BoardUtil.countLiberties(board.getBoard(), m));
    }

    @Test
    public void testCountLibertiesBorder() {
        Move m = Move.create(1, 0, w);
        board.move(m);
        assertEquals(3, BoardUtil.countLiberties(board.getBoard(), m));
        board.move(Move.create(0, 0, b));
        assertEquals(2, BoardUtil.countLiberties(board.getBoard(), m));
        board.move(Move.create(1, 1, b));
        assertEquals(1, BoardUtil.countLiberties(board.getBoard(), m));
    }

    @Test
    public void testCountLibertiesMiddle() {
        Move m = Move.create(1, 1, w);
        board.move(m);
        assertEquals(4, BoardUtil.countLiberties(board.getBoard(), m));
        board.move(Move.create(0, 1, b));
        assertEquals(3, BoardUtil.countLiberties(board.getBoard(), m));
        board.move(Move.create(1, 2, b));
        assertEquals(2, BoardUtil.countLiberties(board.getBoard(), m));
        board.move(Move.create(2, 1, b));
        assertEquals(1, BoardUtil.countLiberties(board.getBoard(), m));
    }

    @Test
    public void testCountLibertiesGroup() {
        Move m = Move.create(2, 2, w);
        board.move(m);
        Move m2 = Move.create(2, 3, w);
        board.move(m2);
        assertEquals(6, BoardUtil.countLiberties(board.getBoard(), m));

        Move m3 = Move.create(2, 4, w);
        board.move(m3);
        assertEquals(8, BoardUtil.countLiberties(board.getBoard(), m));
    }

    @Test
    public void testCountLibertiesGroup2() {
        board.move(Move.create(2, 2, w));
        board.move(Move.create(2, 3, w));
        board.move(Move.create(1, 2, b));
        board.move(Move.create(1, 3, b));

        Move m3 = Move.create(2, 4, w);
        board.move(m3);
        assertEquals(6, BoardUtil.countLiberties(board.getBoard(), m3));
    }

    @Test
    public void testRemoveDeadStoneMiddleBoardGroup() {
        board.move(Move.create(2, 2, w)); //remove this stone.
        board.move(Move.create(3, 2, w)); //remove this stone.

        board.move(Move.create(1, 2, b));
        board.move(Move.create(4, 2, b));
        board.move(Move.create(2, 1, b));
        board.move(Move.create(2, 3, b));
        board.move(Move.create(3, 1, b));
        Move m = Move.create(3, 3, b);
        board.move(m);
        assertEquals(2, m.getRemovedPositions().size());
        assertTrue(m.getRemovedPositions().contains(Move.create(2, 2, w)));
        assertTrue(m.getRemovedPositions().contains(Move.create(3, 2, w)));

        assertTrue(board.getMove(2, 2).isFree());
        assertTrue(board.getMove(3, 2).isFree());

        assertEquals(2, b.getPoints(), 0.1);
    }

    /**
     * Atari upper left corner:
     * 
     * ?-b-w- -
     * b-w- - -
     * w- - - -
     */
    @Test
    public void testAtari() {
        board.move(Move.create(0, 2, w));
        board.move(Move.create(1, 1, w));
        board.move(Move.create(2, 0, w));

        board.move(Move.create(0, 1, b)); //remove this stone.
        board.move(Move.create(1, 0, b)); //remove this stone.

        List<Move> atari = BoardUtil.getStonesInAtari(board, b);

        assertEquals(2, atari.size());
    }

    /**
     * This fake eye can be broken if play at ?.
     * 
     * - - - - - 
     * - -w-w- - 
     * -w-b-b-w- 
     * -b-?-b-w- 
     * -b-b-w- -
     */
    @Test
    public void testFakeEye() {
        board.move(Move.create(2, 2, b)); //Remove this stone
        board.move(Move.create(2, 3, b)); //Remove this stone
        board.move(Move.create(3, 1, b));
        board.move(Move.create(3, 3, b)); //Remove this stone
        board.move(Move.create(4, 1, b));
        board.move(Move.create(4, 2, b));

        board.move(Move.create(1, 2, w));
        board.move(Move.create(1, 3, w));
        board.move(Move.create(2, 1, w));
        board.move(Move.create(2, 4, w));
        board.move(Move.create(3, 4, w));
        board.move(Move.create(4, 3, w));

        Move m = Move.create(3, 2, w);
        board.move(m);

        assertEquals(3, m.getRemovedPositions().size());

        assertTrue(board.getMove(2, 2).isFree());
        assertTrue(board.getMove(2, 3).isFree());
        assertTrue(board.getMove(3, 3).isFree());
    }
}
