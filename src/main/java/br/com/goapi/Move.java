package br.com.goapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author rafaelsakurai
 */
public class Move {
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
    
    public String getStringPosition() {
        return String.valueOf((line + 'a')) + String.valueOf(column + 'a');
    }
    
    public boolean samePlayer(final Player player) {
        return this.player != null && player != null && getPlayer().getColor() == player.getColor();
    }

    public Player getPlayer() {
        return player;
    }
    
    public boolean isWhiteMove() {
        return 'w' == player.getColor();
    }
    
    public boolean isBlackMove() {
        return 'b' == player.getColor();
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
        return "Move{" + "x=" + line + ", y=" + column + ", player=" + player + ", liberties=" + liberties + ", opponents=" + opponents + ", removedPositions=" + removedPositions + '}';
    }
}
