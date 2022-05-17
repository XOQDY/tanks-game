import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private int size;

    private Tank tank;
    private Bricks [] bricks;
    private int bricksCount = 10;
    private Thread thread;
    private boolean notOver;
    private long delayed = 500;

    public World(int size) {
        this.size = size;
        tank = new Tank(size/2, size/2);
        bricks = new Bricks[bricksCount];
        Random random = new Random();
        for (int i = 0; i < bricks.length; i++){
            bricks[i] = new Bricks(random.nextInt(size), random.nextInt(size));
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

    public boolean isGameOver() {
        return !notOver;
    }
}
