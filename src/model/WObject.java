package model;

public abstract class WObject {

    private int x;
    private int y;

    private int dx;
    private int dy;

    private int speed;

    private boolean isAlive = true;
    private boolean Fired = false;

    public WObject() {
    }

    public WObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void turnNorth() {
        dx = 0;
        dy = -1;
    }

    public void turnSouth() {
        dx = 0;
        dy = 1;
    }

    public void turnWest() {
        dx = -1;
        dy = 0;
    }

    public void turnEast() {
        dx = 1;
        dy = 0;
    }

    public void stop() {
        dx = 0;
        dy = 0;
    }

    public void move() {
        this.x += dx * speed;
        this.y += dy * speed;
    }

    public void moveBack() {
        this.x -= dx * speed;
        this.y -= dy * speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean isFired() {
        return Fired;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setFired(boolean fired) {
        Fired = fired;
    }

    public boolean hit(WObject wObj) {
        return x == wObj.x && y == wObj.y;
    }
}
