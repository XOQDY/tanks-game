import java.util.Objects;

public class StateEast extends State{
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
        stateOwner.setCurrentState(new StateWest());
    }

    @Override
    public void east(Player stateOwner) {

    }

    @Override
    public boolean check(String stateCheck) {
        return Objects.equals(stateCheck, "east");
    }
}
