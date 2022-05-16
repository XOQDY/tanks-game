public class CommandTurnEast extends Command{

    public CommandTurnEast(Player player, int tick) {
        super(player, tick);
    }

    @Override
    public void execute() {
        getPlayer().turnEast();
    }
}
