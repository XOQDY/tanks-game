public class Brick extends Block{

    public Brick() {
    }

    public Brick(int x, int y) {
        super(x, y);
        setDestructible(true);
        setPenetrable(false);
    }
}
