public class Bullet extends WObject{

    public Bullet() {
    }

    public Bullet(int x, int y) {
        super(x, y);
        setSpeed(1);
    }

    public void refreshState(int x, int y, int dx, int dy) {
        setPosition(x, y);
        setDx(dx);
        setDy(dy);
    }
}
