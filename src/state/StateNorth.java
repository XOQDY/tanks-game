package state;

import model.Player;
import java.util.Objects;

public class StateNorth extends State{
    @Override
    public void north(Player stateOwner) {

    }

    @Override
    public void south(Player stateOwner) {
        stateOwner.setCurrentState(new StateSouth());
    }

    @Override
    public void west(Player stateOwner) {
        stateOwner.setCurrentState(new StateWest());
    }

    @Override
    public void east(Player stateOwner) {
        stateOwner.setCurrentState(new StateEast());
    }

    @Override
    public boolean check(String stateCheck) {
        return Objects.equals(stateCheck, "north");
    }

    @Override
    public int xDirection() {
        return 0;
    }

    @Override
    public int yDirection() {
        return -1;
    }
}
