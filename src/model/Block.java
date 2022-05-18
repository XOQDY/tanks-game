package model;

public abstract class Block {

    private int x;
    private int y;

    private boolean destructible;
    private boolean penetrable;

    public Block() {
    }

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDestructible() {
        return destructible;
    }

    public boolean isPenetrable() {
        return penetrable;
    }

    public void setDestructible(boolean destructible) {
        this.destructible = destructible;
    }

    public void setPenetrable(boolean penetrable) {
        this.penetrable = penetrable;
    }
}
