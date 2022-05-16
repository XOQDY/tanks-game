public class CommandTurnSouth extends Command{


    public CommandTurnSouth(Player player, int tick) {
        super(player, tick);
    }

    @Override
    public void execute() {
        getPlayer().turnSouth();
    }
}
