public class CommandTurnWest extends Command{

    public CommandTurnWest(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        getPlayer().turnWest();
    }
}
