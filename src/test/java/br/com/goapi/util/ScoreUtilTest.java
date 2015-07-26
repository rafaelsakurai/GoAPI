package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Move;
import br.com.goapi.Player;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafaelsakurai
 */
public class ScoreUtilTest {
    
//    @Test
    public void testLoadKifu() {
        Board board = KifuUtil.loadFile("target/test-classes/1808698-053-rafasakurai-stefanko3200.sgf");
        board.printBoard();
        assertNotNull(board);
//        board.printInfluenceZone();
//        board.printPlayerInfluenceZone();
        System.out.println("Points Black: " + ScoreUtil.getPoints(board, 'b'));
        System.out.println("Points White: " + ScoreUtil.getPoints(board, 'w'));
        
        System.out.println("black komi " + board.getPlayerB().getKomi() + " captured: " + board.getPlayerB().getCaptured() + " territory: " + board.getPlayerB().getTerritory());
        System.out.println("white komi " + board.getPlayerW().getKomi() + " captured: " + board.getPlayerW().getCaptured() + " territory: " + board.getPlayerW().getTerritory());
        
        assertEquals(1, board.getPlayerB().getCaptured());
        assertEquals(18, board.getPlayerB().getTerritory());
        assertEquals(19, board.getPlayerB().getPoints(), 0.01);
        
        assertEquals(6, board.getPlayerW().getCaptured());
        assertEquals(19, board.getPlayerW().getTerritory());
        assertEquals(30.5, board.getPlayerW().getPoints(), 0.01);
    }
    
//    @Test
    public void testLoadKifu2() {
        Board board = KifuUtil.loadFile("target/test-classes/1502011-085-rafasakurai-Theraiser.sgf");
//        board.printBoard();
        assertNotNull(board);
//        board.printInfluenceZone();
//        board.printPlayerInfluenceZone();
        System.out.println("Points Black: " + ScoreUtil.getPoints(board, 'b'));
        System.out.println("Points White: " + ScoreUtil.getPoints(board, 'w'));
        
        System.out.println("black komi " + board.getPlayerB().getKomi() + " captured: " + board.getPlayerB().getCaptured() + " territory: " + board.getPlayerB().getTerritory());
        System.out.println("white komi " + board.getPlayerW().getKomi() + " captured: " + board.getPlayerW().getCaptured() + " territory: " + board.getPlayerW().getTerritory());
        
        assertEquals(20, board.getPlayerB().getCaptured());
        assertEquals(10, board.getPlayerW().getCaptured());
        assertEquals(17, board.getPlayerB().getTerritory());
        assertEquals(13, board.getPlayerW().getTerritory());
        assertEquals(37, board.getPlayerB().getPoints(), 0.01);
        assertEquals(28.5, board.getPlayerW().getPoints(), 0.01);
    }
    
//    @Test
    public void testLoadKifu3() {
        Board board = KifuUtil.loadFile("target/test-classes/1462134-059-rafasakurai-iliyas.ulan.sgf");
        board.printBoard();
        assertNotNull(board);
        ScoreUtil.printInfluenceZone(board);
        ScoreUtil.printPlayerInfluenceZone(board);
        System.out.println("Points Black: " + ScoreUtil.getPoints(board, 'b'));
        System.out.println("Points White: " + ScoreUtil.getPoints(board, 'w'));
        
        System.out.println("black komi " + board.getPlayerB().getKomi() + " captured: " + board.getPlayerB().getCaptured() + " territory: " + board.getPlayerB().getTerritory());
        System.out.println("white komi " + board.getPlayerW().getKomi() + " captured: " + board.getPlayerW().getCaptured() + " territory: " + board.getPlayerW().getTerritory());
        
        
        assertEquals(28, board.getPlayerW().getCaptured());
        assertEquals(53, board.getPlayerW().getTerritory());
        assertEquals(86.5, board.getPlayerW().getPoints(), 0.01);
    }
    
//    @Test
    public void testBoardInfluence() {
        Board board = new Board(19);
        board.move(Move.create(3, 3, new Player("", "", 'b')));
        board.move(Move.create(4, 3, new Player("", "", 'b')));
        board.move(Move.create(4, 7, new Player("", "", 'w')));
        ScoreUtil.printInfluenceZone(board);
        ScoreUtil.printPlayerInfluenceZone(board);
    }
}
