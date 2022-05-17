public class Player extends WObject {

    private State currentState;

    public Player() {
    }

    public Player(int x, int y) {
        super(x, y);
        setSpeed(1);
        currentState = new StateNorth();
    }

    @Override
    public void turnNorth() {
        super.turnNorth();
        currentState.north(this);
    }

    @Override
    public void turnSouth() {
        super.turnSouth();
        currentState.south(this);
    }

    @Override
    public void turnWest() {
        super.turnWest();
        currentState.west(this);
    }


    @Override
    public void turnEast() {
        super.turnEast();
        currentState.east(this);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public boolean sameState(String state) {
        return currentState.check(state);
    }

    public int xDirection() {
        return currentState.xDirection();
    }

    public int yDirection() {
        return currentState.yDirection();
    }
}
