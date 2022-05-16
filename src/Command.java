public abstract class Command {
    private Player player;  // Command know the receiver
    private int tick;

    // Constructure
    public Command(Player player, int tick) {
        this.player = player;
        this.tick = tick;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract void execute();

    public int getTick() {
        return tick;
    }
}
