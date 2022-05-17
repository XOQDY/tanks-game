import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private int size;

    private Tank tank;
    private Bricks [] bricks;
    private int bricksCount = 20;

    private Steel [] steels;

    private int steelsCount = 10;

    private Trees [] trees;
    private int treesCount=20;

    private Thread thread;

    private boolean notOver;

    private long delayed = 500;

    public World(int size) {
        this.size = size;
        tank = new Tank(size/2, size/2);
        bricks = new Bricks[bricksCount];
        steels = new Steel[steelsCount];
        trees = new Trees[treesCount];
        Random random = new Random();
        for (int i = 0; i < bricks.length; i++){
            bricks[i] = new Bricks(random.nextInt(size), random.nextInt(size));
        }
        for (int i = 0; i < steels.length; i++){
            steels[i] = new Steel(random.nextInt(size), random.nextInt(size));
        }
        for (int i = 0; i < trees.length; i++){
            trees[i] = new Trees(random.nextInt(size), random.nextInt(size));
        }
    }

    public void start() {
        tank.reset();
        tank.setPosition(size/2, size/2);
        notOver = true;
        thread = new Thread() {
            @Override
            public void run() {
                while(notOver) {
                    tank.move();
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

    public Tank getTank() {
        return tank;
    }

    public Bricks[] getBricks(){
        return bricks;
    }

    public Steel[] getSteels(){
        return steels;
    }

    public Trees[] getTrees(){
        return trees;
    }

    public boolean isGameOver() {
        return !notOver;
    }
}
