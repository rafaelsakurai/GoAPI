package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Move;
import br.com.goapi.Player;
import br.com.goapi.util.BoardUtil;

/**
 *
 * @author rafaelsakurai
 */
public class ScoreUtil {
    public static char[][] getPlayerInfluenceZone(final Board board) {
//        double[][] influence = getInfluenceZone();
//        
//        return convertToPlayerInfluence(influence);
        
        return getResult(board);
    }
    
    public static char[][] getResult(final Board board) {
        char[][] cBoard = BoardUtil.getBoardChar(board);
        
        for (int line = 0; line < cBoard.length; line++) {
            for (int column = 0; column < cBoard[line].length; column++) {
                if (cBoard[line][column] == ' ') {
                    cBoard[line][column] = findOwnerPosition(cBoard, line, column);
                }
            }
        }
        
        return cBoard;
    }
    
    public static char findOwnerPosition(char[][] cBoard, int line, int column) {
//        System.out.println("findOwnerPosition " + line + " - " + column);
        char owner = '.';
        char[] map = new char[cBoard.length * cBoard.length];
        map[getPos(line, column, cBoard.length)] = cBoard[line][column];
        fillMap(map, cBoard, line, column);
        
        boolean hasBlack = false;
        boolean hasWhite = false;
        
        for(int i = 0; i < map.length; i++) {
            if (map[i] == 'b') {
                hasBlack = true;
            }
            if (map[i] == 'w') {
                hasWhite = true;
            }
        }
        
        if(hasBlack && !hasWhite) {
            owner = 'b';
        } else if(!hasBlack && hasWhite) {
            owner = 'w';
        }
        
        return owner;
    }
    
    public static void fillMap(char[] map, char[][] cBoard, int line, int column) {
        char empty = '\0';
//        System.out.println("fillMap: " + line + " - " + column + " = " + map[getPos(line, column, cBoard.length)]);
        if (line - 1 >= 0 && map[getPos(line - 1, column, cBoard.length)] == empty) {
            map[getPos(line - 1, column, cBoard.length)] = cBoard[line - 1][column];
            if (cBoard[line - 1][column] == ' ') {
                fillMap(map, cBoard, line - 1, column);
            }
        }
        
        if (line + 1 < cBoard.length && map[getPos(line + 1, column, cBoard.length)] == empty) {
            map[getPos(line + 1, column, cBoard.length)] = cBoard[line + 1][column];
            if (cBoard[line + 1][column] == ' ') {
                fillMap(map, cBoard, line + 1, column);
            }
        }
        
        if (column - 1 >= 0 && map[getPos(line, column - 1, cBoard.length)] == empty) {
            map[getPos(line, column - 1, cBoard.length)] = cBoard[line][column - 1];
            if (cBoard[line][column - 1] == ' ') {
                fillMap(map, cBoard, line, column - 1);
            }
        }
        
        if (column + 1 < cBoard.length && map[getPos(line, column + 1, cBoard.length)] == empty) {
            map[getPos(line, column + 1, cBoard.length)] = cBoard[line][column + 1];
            if (cBoard[line][column + 1] == ' ') {
                fillMap(map, cBoard, line, column + 1);
            }
        }
    }
    
    
    public static int getPos(int line, int column, int length) {
        return (line * length) + column;
    }
    
    public static char[][] convertToPlayerInfluence(final Board board, double[][] influence) {
        char[][] players = new char[influence.length][influence.length];
        for (int i = 0; i < influence.length; i++) {
            for (int j = 0; j < influence[i].length; j++) {
                Move m = board.getMove(i, j);
                if(m.isFree()) {
                    if (influence[i][j] < 0) {
                        players[i][j] = 'b';
                    }

                    if (influence[i][j] > 0) {
                        players[i][j] = 'w';
                    }
                }
//                else {
//                    players[i][j] = m.getPlayer().getColor();
//                }
                else {
//                    if (m.isWhiteMove() && influence[i][j] > -0.45) {
//                        players[i][j] = 'w';
//                    } else if (m.isWhiteMove() && influence[i][j] <= -0.45) {
//                        players[i][j] = 'b';
//                    } else if(m.isBlackMove() && influence[i][j] > 1.45) {
//                        players[i][j] = 'w';
//                    } else if(m.isBlackMove() && influence[i][j] <= 1.45) {
//                        players[i][j] = 'b';
//                    }
                    if (m.isWhiteMove() && influence[i][j] > 0) {
                        players[i][j] = 'w';
                    } else if (m.isWhiteMove() && influence[i][j] < 0) {
                        players[i][j] = 'b';
                    } else if(m.isBlackMove() && influence[i][j] > 0) {
                        players[i][j] = 'w';
                    } else if(m.isBlackMove() && influence[i][j] < 0) {
                        players[i][j] = 'b';
                    }
                }
            }
        }
        return players;
    }
    
    public static double[][] getInfluenceZone(final Board board) {
        char[][] cBoard = BoardUtil.getBoardChar(board);
        double[][] influence = new double[board.getSize()][board.getSize()];
        
        for (int i = 0; i < cBoard.length; i++) {
            for (int j = 0; j < cBoard[i].length; j++) {
                char m = cBoard[i][j];
                if(m != ' '){
                    int value = m == 'w' ? 1 : -1;

                    addValue(influence, i, j, 1 * value);

                    addValue(influence, i-1, j, 1 * value);
                    addValue(influence, i+1, j, 1 * value);
                    addValue(influence, i, j-1, 1 * value);
                    addValue(influence, i, j+1, 1 * value);

                    addValue(influence, i-1, j-1, 0.5 * value);
                    addValue(influence, i-1, j+1, 0.5 * value);
                    addValue(influence, i+1, j-1, 0.5 * value);
                    addValue(influence, i+1, j+1, 0.5 * value);

                    addValue(influence, i-2, j, 0.4 * value);
                    addValue(influence, i+2, j, 0.4 * value);
                    addValue(influence, i, j-2, 0.4 * value);
                    addValue(influence, i, j+2, 0.4 * value);

                    addValue(influence, i-2, j-1, 0.2 * value);
                    addValue(influence, i-2, j+1, 0.2 * value);
                    addValue(influence, i+2, j-1, 0.2 * value);
                    addValue(influence, i+2, j+1, 0.2 * value);
                    addValue(influence, i-1, j-2, 0.2 * value);
                    addValue(influence, i-1, j+2, 0.2 * value);
                    addValue(influence, i+1, j-2, 0.2 * value);
                    addValue(influence, i+1, j+2, 0.2 * value);

                    addValue(influence, i-3, j, 0.1 * value);
                    addValue(influence, i+3, j, 0.1 * value);
                    addValue(influence, i, j-3, 0.1 * value);
                    addValue(influence, i, j+3, 0.1 * value);

                    addValue(influence, i-3, j-1, 0.05 * value);
                    addValue(influence, i-3, j+1, 0.05 * value);
                    addValue(influence, i+3, j-1, 0.05 * value);
                    addValue(influence, i+3, j+1, 0.05 * value);
                    addValue(influence, i-1, j-3, 0.05 * value);
                    addValue(influence, i-1, j+3, 0.05 * value);
                    addValue(influence, i+1, j-3, 0.05 * value);
                    addValue(influence, i+1, j+3, 0.05 * value);
                }
            }
        }
        board.printBoard(influence);
        cBoard = convertToPlayerInfluence(board, influence);
        board.printBoard(cBoard);
        System.out.println("-=-");
        influence = new double[board.getSize()][board.getSize()];
        
        for (int i = 0; i < cBoard.length; i++) {
            for (int j = 0; j < cBoard[i].length; j++) {
                char m = cBoard[i][j];
                if(m != ' '){
                    int value = m == 'w' ? 1 : -1;

                    addValue(influence, i, j, 1 * value);

                    addValue(influence, i-1, j, 1 * value);
                    addValue(influence, i+1, j, 1 * value);
                    addValue(influence, i, j-1, 1 * value);
                    addValue(influence, i, j+1, 1 * value);

                    addValue(influence, i-1, j-1, 0.5 * value);
                    addValue(influence, i-1, j+1, 0.5 * value);
                    addValue(influence, i+1, j-1, 0.5 * value);
                    addValue(influence, i+1, j+1, 0.5 * value);

                    addValue(influence, i-2, j, 0.4 * value);
                    addValue(influence, i+2, j, 0.4 * value);
                    addValue(influence, i, j-2, 0.4 * value);
                    addValue(influence, i, j+2, 0.4 * value);

                    addValue(influence, i-2, j-1, 0.2 * value);
                    addValue(influence, i-2, j+1, 0.2 * value);
                    addValue(influence, i+2, j-1, 0.2 * value);
                    addValue(influence, i+2, j+1, 0.2 * value);
                    addValue(influence, i-1, j-2, 0.2 * value);
                    addValue(influence, i-1, j+2, 0.2 * value);
                    addValue(influence, i+1, j-2, 0.2 * value);
                    addValue(influence, i+1, j+2, 0.2 * value);

                    addValue(influence, i-3, j, 0.1 * value);
                    addValue(influence, i+3, j, 0.1 * value);
                    addValue(influence, i, j-3, 0.1 * value);
                    addValue(influence, i, j+3, 0.1 * value);

                    addValue(influence, i-3, j-1, 0.05 * value);
                    addValue(influence, i-3, j+1, 0.05 * value);
                    addValue(influence, i+3, j-1, 0.05 * value);
                    addValue(influence, i+3, j+1, 0.05 * value);
                    addValue(influence, i-1, j-3, 0.05 * value);
                    addValue(influence, i-1, j+3, 0.05 * value);
                    addValue(influence, i+1, j-3, 0.05 * value);
                    addValue(influence, i+1, j+3, 0.05 * value);
                }
            }
        }
        board.printBoard(influence);
        cBoard = convertToPlayerInfluence(board, influence);
        board.printBoard(cBoard);
        return influence;
    }
    
    private static void addValue(double[][] influence, int line, int column, double value) {
        if(line >=0 && line < influence.length && column >=0 && column < influence.length) {
            influence[line][column] += value;
        }
    }
    
    public static int getPoints(final Board board, final char player) {
        int points = 0;
        Player p = 'b' == player ? board.getPlayerB() : board.getPlayerW();
        char[][] influence = getPlayerInfluenceZone(board);
        board.printBoard(influence);
        for (int i = 0; i < influence.length; i++) {
            for (int j = 0; j < influence[i].length; j++) {
                if (influence[i][j] == player) {
                    Move m = board.getMove(i, j);
                    if(m.isFree()) {
                        points += 1;
                    }else if(m.getPlayer().getColor() != player) {
//                        if(!hasLiberties(m)) {
                            points += 1;
                            System.out.println("player " + player + " - captured: [" + i + "]["+ j + "] == " + m.getPlayer().getColor());
                            p.addCaptured(1);
//                        }
                    }
                }
            }
        }
        p.setTerritory(points);
        return points;
    }
    
    public static void printInfluenceZone(final Board board) {
        double[][] influence = getInfluenceZone(board);
        board.printBoard(influence);
    }
    
    public static void printPlayerInfluenceZone(final Board board) {
        char[][] influence = getPlayerInfluenceZone(board);
        board.printBoard(influence);
    }
}
