package command;

import model.Player;

public class CommandTurnEast extends Command{

    public CommandTurnEast(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        getPlayer().turnEast();
    }
}
