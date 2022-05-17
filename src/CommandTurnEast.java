public class CommandTurnEast extends Command{

    public CommandTurnEast(Tank tank) {
        super(tank);
    }

    @Override
    public void execute() {
        getTank().turnEast();
        getTank().getPosition(getTank());
    }
}
