import java.util.Objects;

public class StateWest extends State{
    @Override
    public void north(Player stateOwner) {
        stateOwner.setCurrentState(new StateNorth());
    }

    @Override
    public void south(Player stateOwner) {
        stateOwner.setCurrentState(new StateSouth());
    }

    @Override
    public void west(Player stateOwner) {

    }

    @Override
    public void east(Player stateOwner) {
        stateOwner.setCurrentState(new StateEast());
    }

    public boolean check(String stateCheck) {
        return Objects.equals(stateCheck, "west");
    }

    @Override
    public int xDirection() {
        return -1;
    }

    @Override
    public int yDirection() {
        return 0;
    }
}
