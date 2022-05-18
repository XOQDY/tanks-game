import java.util.Random;

public class RandomStrategy implements MoveStrategy {

    private Random random = new Random();
    private int side;
    private long lastCommand;
    private long lastFire;

    public Command getNextMoveCommand(World world, Player myPlayer) {
        Command c;
        long diff = System.currentTimeMillis() - lastCommand;
        long diffFire = System.currentTimeMillis() - lastFire;
        if(diff < 3000) {
            if (myPlayer.isFired()) {
                return null;
            }
            else if (diffFire > 1000){
                lastFire = System.currentTimeMillis();
                c = new CommandFire(myPlayer, world);
                return c;
            }
        }
        lastCommand = System.currentTimeMillis();
        side = random.nextInt(5);
        while (checkSameCommand(myPlayer, side)) {
            side = random.nextInt(5);
        }

        if (side == 1) {
            c = new CommandTurnNorth(myPlayer);
        } else if (side == 2) {
            c = new CommandTurnSouth(myPlayer);
        } else if (side == 3) {
            c = new CommandTurnWest(myPlayer);
        } else if (side == 4) {
            c = new CommandTurnEast(myPlayer);
        } else {
            c = new CommandStop(myPlayer);
        }
        return c;
    }

    private boolean checkSameCommand(Player myPlayer, int side) {
        if (side == 1 && myPlayer.sameState("north")) {
            return true;
        } else if (side == 2 && myPlayer.sameState("south")) {
            return true;
        } else if (side == 3 && myPlayer.sameState("west")) {
            return true;
        } else return side == 4 && myPlayer.sameState("east");
    }
}
