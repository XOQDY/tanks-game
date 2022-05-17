import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {

    private int size;

    private BulletPool bulletPool;
    private Player player1, player2;
    private Thread thread;
    private boolean notOver;
    private long delayed = 30;

    private List<Player> tanks;
    private List<Bullet> bullets;

    public World(int size) {
        this.size = size;
        tanks = new ArrayList<Player>();
        bullets = new ArrayList<Bullet>();
        bulletPool = new BulletPool();
        player1 = new Player(size/2, size/2);
        player2 = new Player(size/4, size/4);
        tanks.add(player1);
        tanks.add(player2);
    }

    public void start() {
        player1.setPosition(size/2, size/2);
        player2.setPosition(size/4, size/4);
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    player1.move();
                    player2.move();
                    moveBullets();
                    cleanupBullets();
                    checkHit();
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

    private void checkHit() {
        List<Bullet> toRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets) {
            for (Player p : tanks) {
                if(bullet.hit(p)) {
                    toRemove.add(bullet);
                    p.setAlive(false);
                    notOver = false;
                }
            }

        }
        for(Bullet bullet : toRemove) {
            bullets.remove(bullet);
            bulletPool.releaseBullet(bullet);
        }
    }

    public int getSize() {
        return size;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
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

    public void fire_bullet(Player player) {
        bullets.add(bulletPool.requestBullet(player.getX(), player.getY(), player.xDirection(), player.yDirection()));
    }
}
