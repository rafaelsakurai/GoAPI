package br.com.goapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author rafaelsakurai
 */
public class Move {
    private int id;
    private final int line;
    private final int column;
    private final Player player;
    private final List<Move> removedPositions;
    private int liberties = 0;
    private int opponents = 0;
    
    private Move(final int line, final int column, final Player player) {
        this.line = line;
        this.column = column;
        this.player = player;
        this.removedPositions = new ArrayList();
    }
    
    public static Move create(final int line, final int column, final Player player) {
        return new Move(line, column, player);
    }
    
    public static Move free(final int line, final int column) {
        return new Move(line, column, null);
    }
    
    public void addRemovedPosition(final Move removed) {
        this.removedPositions.add(removed);
    }
    
    public void addAllRemovedPosition(final List<Move> removed) {
        this.removedPositions.addAll(removed);
    }
    
    public boolean isFree() {
        return player == null;
    }
    
    public static Move pass(final Player player) {
        return new Move(-1, -1, player);
    }
    
    public boolean isPass() {
        return line == -1 && column == -1;
    }
    
    public int[] getArrayPosition(final String position) {
        return new int[]{position.charAt(0) - 'a', position.charAt(1) - 'a'};
    }
    
    /**
     * Format: Column + Line
     * @return 
     */
    public String getStringPosition() {
        if(line != -1 && column != -1) {
            return String.valueOf((char) (column + 'a')) + String.valueOf((char)(line + 'a'));
        } else {
            return "";
        }
    }
    
    public boolean samePlayer(final Player player) {
        return this.player != null && player != null && getPlayer().getColor() == player.getColor();
    }

    public Player getPlayer() {
        return player;
    }
    
    public boolean isWhiteMove() {
        return Player.Color.WHITE == player.getColor();
    }
    
    public boolean isBlackMove() {
        return Player.Color.BLACK == player.getColor();
    }
    
    public List<Move> getRemovedPositions() {
        return removedPositions;
    }
    
    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getLiberties() {
        return liberties;
    }

    public void setLiberties(final int liberties) {
        this.liberties = liberties;
    }

    public int getOpponents() {
        return opponents;
    }

    public void setOpponents(final int opponents) {
        this.opponents = opponents;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    /**
     * Return the int value from move:
     * -1 for black move
     * 1 for white move
     * 
     * @return 
     */
    public int getIntMove() {
        return player.getColor() == Player.Color.BLACK ? -1 : 1;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.line;
        hash = 83 * hash + this.column;
        hash = 83 * hash + Objects.hashCode(this.player);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Move other = (Move) obj;
        if (this.line != other.line) {
            return false;
        }
        if (this.column != other.column) {
            return false;
        }
        if (!Objects.equals(this.player, other.player)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Move{id=" + id + ", line=" + line + ", column=" + column + ", player=" + player + ", liberties=" + liberties + ", opponents=" + opponents + ", removedPositions=" + removedPositions + '}';
    }
}
