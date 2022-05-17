public class Bullet extends WObject{

    private Player owner = null;

    public Bullet() {
    }

    public Bullet(int x, int y) {
        super(x, y);
        setSpeed(1);
    }

    public void refreshState(int x, int y, int dx, int dy, Player owner) {
        setPosition(x, y);
        setDx(dx);
        setDy(dy);
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
