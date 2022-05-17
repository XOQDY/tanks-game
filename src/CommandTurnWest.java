public class CommandTurnWest extends Command{

    public CommandTurnWest(Tank tank) {
        super(tank);
    }

    @Override
    public void execute() {
        getTank().turnWest();
        getTank().getPosition(getTank());
    }
}
