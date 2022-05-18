package command;

import model.Player;

public class CommandTurnOpposite extends Command{
    public CommandTurnOpposite(Player player) {
        super(player);
    }

    @Override
    public void execute() {
        if (getPlayer().sameState("north")) {
            getPlayer().turnSouth();
        } else if (getPlayer().sameState("south")) {
            getPlayer().turnNorth();
        } else if (getPlayer().sameState("west")) {
            getPlayer().turnEast();
        } else if (getPlayer().sameState("east")) {
            getPlayer().turnWest();
        }
    }
}
