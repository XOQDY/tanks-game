import java.util.Objects;

public class StateSouth extends State{
    @Override
    public void north(Player stateOwner) {
        stateOwner.setCurrentState(new StateNorth());
    }

    @Override
    public void south(Player stateOwner) {

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
        return Objects.equals(stateCheck, "south");
    }
  
    @Override
    public int xDirection() {
        return 0;
    }

    @Override
    public int yDirection() {
        return 1;
    }
}
