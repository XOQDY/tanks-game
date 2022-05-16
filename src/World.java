import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private int size;

    private BulletPool bulletPool;
    private Player player;
    private Thread thread;
    private boolean notOver;
    private long delayed = 500;
    private int enemyCount = 20;

    private Enemy [] enemies;
    private List<Bullet> bullets;

    public World(int size) {
        this.size = size;
        bullets = new ArrayList<Bullet>();
        bulletPool = new BulletPool();
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
                    moveBullets();
                    cleanupBullets();
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

    private void moveBullets() {
        for(Bullet bullet : bullets) {
            bullet.move();
        }
    }

    public Enemy[] getEnemies() {
        return enemies;
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
        bullets.add(bulletPool.requestBullet(player.getX(), player.getY(), player.getDx(), player.getDy()));
    }

    public void burstBullets(int x, int y) {
        bullets.add(bulletPool.requestBullet(x, y, 1, 0));
        bullets.add(bulletPool.requestBullet(x, y, 0, 1));
        bullets.add(bulletPool.requestBullet(x, y, -1, 0));
        bullets.add(bulletPool.requestBullet(x, y, 0, -1));
        bullets.add(bulletPool.requestBullet(x, y, 1, 1));
        bullets.add(bulletPool.requestBullet(x, y, 1, -1));
        bullets.add(bulletPool.requestBullet(x, y, -1, 1));
        bullets.add(bulletPool.requestBullet(x, y, -1, -1));
    }
}
