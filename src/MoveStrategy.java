public interface MoveStrategy {
    Command getNextMoveCommand(World world, Player myPlayer);
}
