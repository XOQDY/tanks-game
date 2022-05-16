public class CommandTurnNorth extends Command{

    public CommandTurnNorth(Player player, int tick) {
        super(player, tick);
    }

    @Override
    public void execute() {
        getPlayer().turnNorth();
    }
}
