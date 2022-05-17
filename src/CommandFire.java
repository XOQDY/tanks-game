public class CommandFire extends Command{

    private World world;

    public CommandFire(Player player, World world) {
        super(player);
        this.world = world;
    }
    @Override
    public void execute() {
        world.fire_bullet(getPlayer());
    }
}
