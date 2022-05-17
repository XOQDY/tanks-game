import java.util.Random;

public class ShortestStrategy {
    private Random random = new Random();
    private long lastCommand;

    public MoveCommand getNextMoveCommand(World world, Player myPlayer) {
        if(world == null) {
            return null;
        }
        long diff = System.currentTimeMillis() - lastCommand;
        if(diff < 5000) {
            return null;
        }
        lastCommand = System.currentTimeMillis();
        MoveCommand command = new MoveCommand();
        command.toX = random.nextInt(world.getSize());
        command.toY = random.nextInt(world.getSize());
        return command;
    }
}
