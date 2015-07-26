package br.com.goapi;

import java.util.Objects;

/**
 *
 * @author rafaelsakurai
 */
public class Player {
    private final String name;
    private final String ranking;
    private final char color;
    private int captured = 0;
    private int territory = 0;
    private double komi = 0;
    
    public Player(final String name, final String ranking, final char color) {
        this.name = name;
        this.ranking = ranking;
        this.color = color;
    }
    
    public Player addCaptured(final int points) {
        this.captured += points;
        return this;
    }
    
    public void setTerritory(final int points) {
        this.territory = points;
    }

    public void setKomi(final double komi) {
        this.komi = komi;
    }

    public char getColor() {
        return color;
    }

    public double getPoints() {
        return captured + territory + komi;
    }

    public int getCaptured() {
        return captured;
    }

    public int getTerritory() {
        return territory;
    }

    public double getKomi() {
        return komi;
    }

    public String getName() {
        return name;
    }

    public String getRanking() {
        return ranking;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.ranking);
        hash = 19 * hash + this.color;
        hash = 19 * hash + this.captured;
        hash = 19 * hash + this.territory;
        hash = 19 * hash + (int) (Double.doubleToLongBits(this.komi) ^ (Double.doubleToLongBits(this.komi) >>> 32));
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
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.ranking, other.ranking)) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        if (this.captured != other.captured) {
            return false;
        }
        if (this.territory != other.territory) {
            return false;
        }
        if (this.komi != other.komi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Player{" + "name=" + name + ", ranking=" + ranking + ", color=" + color + ", captured=" + captured + ", territory=" + territory + ", komi=" + komi + '}';
    }
}
