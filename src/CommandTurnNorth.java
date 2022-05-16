public class CommandTurnNorth extends Command{

    public CommandTurnNorth(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        getPlayer().turnNorth();
    }
}
