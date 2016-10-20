package br.com.goapi.util;

import br.com.goapi.Board;
import br.com.goapi.Move;
import br.com.goapi.Player;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class KifuUtil {
    
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    
    public static Board loadFile(final String file) {
        Board board = getBoard(file);
        try {
            Player b = board.getPlayerB();
            Player w = board.getPlayerW();
            Scanner s = new Scanner(new File(file));
            
            while (s.hasNextLine()) {
                String[] partes = s.nextLine().split(";");
                for (int i = 0; i < partes.length; i++) {
                    if (partes[i].length() >= 5 && partes[i].substring(1, 5).startsWith("[") && partes[i].substring(0, 5).endsWith("]")) {
                        Player p = "B".equals(partes[i].substring(0, 1)) ? b : w;
                        String position = partes[i].substring(2, 4);
                        board.move(Move.create(position.charAt(1) - 'a', position.charAt(0) - 'a', p));
                    } else {
                        if (partes[i].length() == 3 && partes[i].endsWith("[]")) {
                            Player p = "B".equals(partes[i].substring(0, 1)) ? b : w;
                            board.move(Move.pass(p)); //pass move
                        }
                    }
                }
            }
            
            return board;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static Board getBoard(final String file) {
        try {
            Date date = null;
            int boardSize = 0;
            double komi = 0;
            String rules = null;
            
            Player b = null;
            String playerB = null;
            String rankingB = null;
            Player w = null;
            String playerW = null;
            String rankingW = null;
            Scanner s = new Scanner(new File(file));
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains("FF")) {
                    
                } 
                if (line.contains("GM")) {
                    
                } 
                if (line.contains("DT")) {
                    date = getContentAsDate(getParam(line, "DT"));
                } 
                if (line.contains("PC")) {
                    
                } 
                if (line.contains("PB")) {
                    playerB = getContentAsString(getParam(line, "PB"));
                } 
                if (line.contains("PW")) {
                    playerW = getContentAsString(getParam(line, "PW"));
                } 
                if (line.contains("BR")) {
                    rankingB = getContentAsString(getParam(line, "BR"));
                } 
                if (line.contains("WR")) {
                    rankingW = getContentAsString(getParam(line, "WR"));
                } 
                if (line.contains("CP")) {
                    
                }
                if (line.contains("RE")) {
                    
                }
                if (line.contains("SZ")) {
                    boardSize = getContentAsInt(getParam(line, "SZ"));
                }
                if (line.contains("KM")) {
                    komi = getContentAsDouble(getParam(line, "KM"));
                }
                if (line.contains("RU")) {
                    rules = getContentAsString(getParam(line, "RU"));
                }
                
//                if(line.contains(";")) {
//                    break;
//                }
            }
            
            return new Board(boardSize, komi, rules, new Player(playerB, rankingB, Player.Color.BLACK), new Player(playerW, rankingW, Player.Color.WHITE), date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private static String getParam(String line, String param) {
        int begin = line.indexOf(param);
        int end = line.indexOf("]", begin) + 1;
        return line.substring(begin, end);
    }
    
    private static String getContentAsString(final String line) {
        return line.substring(line.indexOf("[") + 1, line.indexOf("]"));
    }
    
    private static int getContentAsInt(final String line) {
        return Integer.valueOf(getContentAsString(line));
    }
    
    private static double getContentAsDouble(final String line) {
        return Double.valueOf(getContentAsString(line));
    }
    
    private static Date getContentAsDate(final String line) {
        try {
            return df.parse(getContentAsString(line));
        } catch(Exception e) {
            return null;
        }
    }
    
    public static String exportFile(final String player1, final String ranking1, final String player2, final String ranking2, final List<String> moves) {
        String export = "(;FF[4]\n" +
                        "GM[1]\n" +
                        "DT[" + df.format(new Date()) + "]\n" +
                        "PC[Go API]\n" +
                        "PB[" + player1 + "]\n" +
                        "PW[" + player2 + "]\n" +
                        "BR["+ranking1+"]\n" +
                        "WR["+ranking1+"]\n" +
                        "CP[https://github.com/rafaelsakurai/GoAPI]\n" +
                        "RE[]\n" + //Result: B+22.5
                        "SZ[]\n" + //?
                        "KM[5.5]\n" + //Komi
                        "RU[japanese]\n"; //TODO
        for (String move : moves) {
            export += ";" + move.charAt(0) + "[" + Character.toLowerCase(move.charAt(2)) + (char) ('a' + (Character.getNumericValue(move.charAt(3)) - 1)) + "]\n";
        }
        
        export += ")";
        
        return export;
    }
}
