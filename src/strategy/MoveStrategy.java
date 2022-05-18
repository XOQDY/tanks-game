package strategy;

import model.World;
import model.Player;
import command.Command;

public interface MoveStrategy {
    Command getNextMoveCommand(World world, Player myPlayer);
}
