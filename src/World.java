import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private int size;

    private Player player;
    private Thread thread;
    private boolean notOver;
    private long delayed = 500;
    private int enemyCount = 20;

    private Enemy [] enemies;

    public World(int size) {
        this.size = size;
        player = new Player(size/2, size/2);
        enemies = new Enemy[enemyCount];
        Random random = new Random();
        for(int i = 0; i < enemies.length / 2; i++) {
            enemies[i] = new Enemy(random.nextInt(size), random.nextInt(size));
        }
        for(int i = enemies.length / 2; i < enemies.length; i++) {
            enemies[i] = new EnemyMoving(random.nextInt(size), size);
        }
    }

    public void start() {
        player.reset();
        player.setPosition(size/2, size/2);
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    player.move();
                    checkCollisions();
                    setChanged();
                    notifyObservers();
                    waitFor(delayed);
                    for (Enemy e: enemies) {
                        e.moving();
                    }
                }
            }
        };
        thread.start();
    }

    private void checkCollisions() {
        for(Enemy e : enemies) {
            if(e.hit(player)) {
                notOver = false;
            }
        }
    }

    private void waitFor(long delayed) {
        try {
            Thread.sleep(delayed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return size;
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy[] getEnemies() {
        return enemies;
    }

    public boolean isGameOver() {
        return !notOver;
    }
}
