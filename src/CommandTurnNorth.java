public class CommandTurnNorth extends Command{

    public CommandTurnNorth(Tank tank) {
        super(tank);
    }

    @Override
    public void execute() {
        getTank().turnNorth();
    }
}
