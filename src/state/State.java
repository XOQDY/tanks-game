package state;

import model.Player;

public abstract class State {
    public abstract void north(Player stateOwner);
    public abstract void south(Player stateOwner);
    public abstract void west(Player stateOwner);
    public abstract void east(Player stateOwner);
    public abstract boolean check(String stateCheck);
    public abstract int xDirection();
    public abstract int yDirection();
}
