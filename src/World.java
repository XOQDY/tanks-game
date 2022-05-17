import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private int size;
    private List<Bricks> bricks;
    private List<Steel> steels;
    private List<Trees> trees;
    private BulletPool bulletPool;
    private Player player1, player2;
    private Thread thread;

    private boolean notOver;
    private long delayed = 300;
    private int enemyCount = 3;

    private List<Player> tanks;
    private List<Bullet> bullets;

    private Enemy [] enemies;

    public World(int size) {
        Map  map = new Map();
        this.size = size;
        int[][] tree = map.Trees;
        int[][] brick = map.brick;
        int[][] steel = map.steel;
        initBrick(brick);
        initSteel(steel);
        initTrees(tree);
        tanks = new ArrayList<Player>();
        bullets = new ArrayList<Bullet>();
        bulletPool = new BulletPool();
        player1 = new Player(size/2, size/2);
        tanks.add(player1);
    }

    public void setWorldSingle() {
        enemies = new Enemy[enemyCount];
        Random random = new Random();
        for(int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(random.nextInt(size), random.nextInt(size));
            tanks.add(enemies[i]);
        }
    }

    public void setWorldMulti() {
        player2 = new Player(size/4, size/4);
        tanks.add(player2);
    }

    public void startSingle() {
        player1.setPosition(size/2, size/2);
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    player1.move();
                    moveBullets();
                    cleanupBullets();
                    checkHit();
                    checkOver();
                    setChanged();
                    notifyObservers();
                    waitFor(delayed);
                }
            }
        };
        thread.start();
    }

    private void initBrick(int [][] Bricks){
        bricks = new ArrayList<Bricks>();
        for (int[] ints : Bricks) {
            bricks.add(new Bricks(ints[1], ints[0]));
        }
    }

    private void initTrees(int [][] Trees){
        trees = new ArrayList<Trees>();
        for (int[] ints : Trees){
            trees.add(new Trees(ints[1], ints[0]));
        }
    }

    public void startMulti() {
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
                    checkOver();
                    setChanged();
                    notifyObservers();
                    waitFor(delayed);
                }
            }
        };
        thread.start();
    }

    private void initSteel(int [][] Steel){
        steels = new ArrayList<Steel>();
        for (int[] ints: Steel){
            steels.add(new Steel(ints[1],ints[0]));
        }
    }


    private void waitFor(long delayed) {
        try {
            Thread.sleep(delayed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkHit() {
        List<Bullet> toRemoveBullet = new ArrayList<Bullet>();
        List<Player> toRemoveTank = new ArrayList<Player>();
        for(Bullet bullet : bullets) {
            for (Player p : tanks) {
                if(bullet.hit(p)) {
                    toRemoveBullet.add(bullet);
                    toRemoveTank.add(p);
                    p.setAlive(false);
                }
            }

        }
        for(Bullet bullet : toRemoveBullet) {
            bullet.getOwner().setFired(false);
            bullets.remove(bullet);
            bulletPool.releaseBullet(bullet);
        }
        for(Player tank : toRemoveTank) {
            tanks.remove(tank);
        }
    }

    private void checkOver() {
        if (tanks.size() == 1) {
            notOver = false;
        }
    }

    public int getSize() {
        return size;
    }

    public List<Bricks> getBricks(){
        return bricks;
    }

    public List<Steel> getSteels() {
        return steels;
    }
    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Trees> getTrees(){
        return trees;
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
            bullet.getOwner().setFired(false);
            bullets.remove(bullet);
            bulletPool.releaseBullet(bullet);
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Enemy [] getEnemies(){
        return enemies;
    }

    public void fire_bullet(Player player) {
        if (!player.isFired()) {
            bullets.add(bulletPool.requestBullet(player.getX(), player.getY(), player.xDirection(), player.yDirection(), player));
            player.setFired(true);
        }
    }
}
