public class CommandTurnSouth extends Command{


    public CommandTurnSouth(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        getPlayer().turnSouth();
    }
}
