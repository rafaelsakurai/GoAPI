package br.com.goapi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rafaelsakurai
 */
public class Group {
    private final List<Move> moves = new ArrayList();
    private final Player player;
    
    public Group(final Player player) {
        this.player = player;
    }
    
    public void addMove(final Move m) {
        this.moves.add(m);
    }
    
    public void addAllMoves(final List<Move> moves) {
        this.moves.addAll(moves);
    }

    public List<Move> getMoves() {
        return moves;
    }

    public int getLiberties() {
        int liberties = 0;
        liberties = moves.stream().map((m) -> m.getLiberties()).reduce(liberties, Integer::sum);
        return liberties;
    }

    public int getOpponents() {
        int opponents = 0;
        opponents = moves.stream().map((m) -> m.getOpponents()).reduce(opponents, Integer::sum);
        return opponents;
    }
    
    public boolean isInAtari() {
        return getLiberties() == 1;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public int size() {
        return moves.size();
    }

    @Override
    public String toString() {
        return "Group{" + "moves=" + moves + ", liberties=" + getLiberties() + ", opponents=" + getOpponents() + ", inAtari=" + isInAtari()+ ", player=" + player + '}';
    }
}
