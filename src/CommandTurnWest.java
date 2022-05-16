public class CommandTurnWest extends Command{

    public CommandTurnWest(Player player, int tick) {
        super(player, tick);
    }

    @Override
    public void execute() {
        getPlayer().turnWest();
    }
}
