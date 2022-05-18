import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private int size;

    private BulletPool bulletPool;
    private Player player1, player2;
    private Thread thread;
    private boolean notOver;
    private long delayed = 250;
    private int enemyCount = 3;

    private Enemy [] enemies;
    private Block [][] blocks;

    private List<Player> tanks;
    private List<Bullet> bullets;

    public World(int size) {
        this.size = size;
        blocks = new Block[size+1][size+1];
        initBlock();
        tanks = new ArrayList<Player>();
        bullets = new ArrayList<Bullet>();
        bulletPool = new BulletPool();
        player1 = new Player(-999, -999);
        player2 = new Player(-999,-999);
        tanks.add(player1);
    }

    private void initBlock() {
        Map map = new Map();
        for (int [] position : map.bricks) {
            blocks[position[1]][position[0]] = new Brick(position[1], position[0]);
        }
        for (int [] position : map.trees) {
            blocks[position[1]][position[0]] = new Tree(position[1], position[0]);
        }
        for (int [] position : map.steels) {
            blocks[position[1]][position[0]] = new Steel(position[1], position[0]);
        }
    }

    public void setWorldSingle() {
        enemies = new Enemy[enemyCount];
        for(int i = 0; i < enemies.length; i++) {
            int[] position = randomSpawn();
            enemies[i] = new Enemy(position[0], position[1], new RandomStrategy());
            tanks.add(enemies[i]);
        }
    }

    public void setWorldMulti() {
        player2 = new Player(-999, -999);
        tanks.add(player2);
    }

    public void startSingle() {
        int[] position = randomSpawn();
        player1.setPosition(position[0], position[1]);
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    player1.move();
                    checkCollisions(player1);
                    for (Enemy e : enemies) {
                        if (e.isAlive()) {
                            e.move();
                            checkCollisions(e);
                            Command command = e.moving(World.this);
                            if (command != null) {
                                command.execute();
                            }
                        }
                    }
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

    public void startMulti() {
        int[] position = randomSpawn();
        player1.setPosition(position[0], position[1]);
        position = randomSpawn();
        player2.setPosition(position[0], position[1]);
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    player1.move();
                    checkCollisions(player1);
                    player2.move();
                    checkCollisions(player2);
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

    private void waitFor(long delayed) {
        try {
            Thread.sleep(delayed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int[] randomSpawn() {
        Random random = new Random();
        int[] position = new int[2];
        position[0] = random.nextInt(size);
        position[1] = random.nextInt(size);
        while (!checkCanDeploy(position[0], position[1])) {
            position[0] = random.nextInt(size);
            position[1] = random.nextInt(size);
        }
        return position;
    }

    private boolean checkCanDeploy(int x, int y) {
        for (Player tank : tanks) {
            if (tank.getX() == x && tank.getY() == y) {
                return false;
            }
        }
        return blocks[x][y] == null;
    }

    private void checkCollisions(Player player) {
        for (Player tank : tanks) {
            if (player != tank && player.hit(tank)) {
                player.moveBack();
            }
        }
        if (blocks[player.getX()][player.getY()] != null && !(blocks[player.getX()][player.getY()].isPenetrable())) {
            player.moveBack();
        }
    }

    private void checkHit() {
        List<Bullet> toRemoveBullet = new ArrayList<Bullet>();
        List<Player> toRemoveTank = new ArrayList<Player>();
        for(Bullet bullet : bullets) {
            for (Player p : tanks) {
                if(bullet.hit(p)) {
                    if (!(bullet.getOwner() instanceof Enemy && p instanceof Enemy)) {
                        toRemoveBullet.add(bullet);
                        toRemoveTank.add(p);
                    }
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
            tank.setAlive(false);
        }
    }

    private void checkOver() {
        int player = 0;
        if (tanks.size() == 1) {
            notOver = false;
        } else {
            for (Player tank : tanks) {
                if (!(tank instanceof Enemy)) {
                    ++player;
                }
            }
            if (player == 0) {
                notOver = false;
            }
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

    public Enemy[] getEnemies() {
        return enemies;
    }

    public Block[][] getBlocks() {
        return blocks;
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
            if(bullet.isExpired()) {
                toRemove.add(bullet);
            } else if (blocks[bullet.getX()][bullet.getY()] != null && !blocks[bullet.getX()][bullet.getY()].isPenetrable()) {
                if (blocks[bullet.getX()][bullet.getY()].isDestructible()) {
                    blocks[bullet.getX()][bullet.getY()] = null;
                    toRemove.add(bullet);
                } else {
                    bullet.moveBack();
                    Command command = bullet.bounce();
                    command.execute();
                }
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

    public void fire_bullet(Player player) {
        if (!player.isFired()) {
            Bullet bullet = bulletPool.requestBullet(player.getX(), player.getY(), player.xDirection(), player.yDirection(), player);
            bullet.move();
            bullet.setCurrentState(player.getCurrentState());
            bullets.add(bullet);
            player.setFired(true);
        }
        cleanupBullets();
        checkHit();
    }
}
