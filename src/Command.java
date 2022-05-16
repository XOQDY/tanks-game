public abstract class Command {
    private Tank tank;  // Command know the receiver

    // Constructor
    public Command(Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }

    public abstract void execute();
}
