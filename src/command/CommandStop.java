package command;

import model.Player;

public class CommandStop extends Command{

    public CommandStop(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        getPlayer().stop();
    }
}
