import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {

    private int size;

    private BulletPool bulletPool;
    private Player player;
    private Thread thread;
    private boolean notOver;
    private long delayed = 30;

    private List<Bullet> bullets;

    public World(int size) {
        this.size = size;
        bullets = new ArrayList<Bullet>();
        bulletPool = new BulletPool();
        player = new Player(size/2, size/2);
    }

    public void start() {
        player.setPosition(size/2, size/2);
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    player.move();
                    moveBullets();
                    cleanupBullets();
                    setChanged();
                    notifyObservers();
                    waitFor(delayed);
                }
            }
        };
        thread.start();
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

    private void moveBullets() {
        for(Bullet bullet : bullets) {
            bullet.move();
        }
    }

    public boolean isGameOver() {
        return !notOver;
    }

    private void cleanupBullets() {
        List<Bullet> toRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets) {
            if(bullet.getX() <= 0 ||
                    bullet.getX() >= size ||
                    bullet.getY() <= 0 ||
                    bullet.getY() >= size) {
                toRemove.add(bullet);
            }
        }
        for(Bullet bullet : toRemove) {
            bullets.remove(bullet);
            bulletPool.releaseBullet(bullet);
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void fire_bullet() {
        bullets.add(bulletPool.requestBullet(player.getX(), player.getY(), player.xDirection(), player.yDirection()));
    }
}
