public class CommandTurnSouth extends Command{


    public CommandTurnSouth(Tank tank) {
        super(tank);
    }

    @Override
    public void execute() {
        getTank().turnSouth();
        getTank().getPosition(getTank());
    }
}
