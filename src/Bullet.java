public class Bullet extends Player{

    private Player owner = null;
    private long lastFire;

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
        lastFire = System.currentTimeMillis();
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isExpired() {
        long diff = System.currentTimeMillis() - lastFire;
        return diff >= 5000;
    }

    public Command bounce() {
        return new CommandTurnOpposite(this);
    }
}
