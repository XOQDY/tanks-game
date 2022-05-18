package model;

public class Tree extends Block{

    public Tree() {
    }

    public Tree(int x, int y) {
        super(x, y);
        setDestructible(false);
        setPenetrable(true);
    }
}
