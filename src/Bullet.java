public class Bullet {

    private int x;
    private int y;

    private int dx;
    private int dy;

    private int speed = 10;

    public Bullet(int x, int y, int dx, int dy) {
        System.out.println("Performance HIT!!!");
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void refreshState(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx * speed;
        y += dy * speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
