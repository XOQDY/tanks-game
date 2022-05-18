package model;

public class Steel extends Block{

    public Steel() {
    }

    public Steel(int x, int y) {
        super(x, y);
        setDestructible(false);
        setPenetrable(false);
    }
}
