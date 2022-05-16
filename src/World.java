import java.util.Observable;
import java.util.Random;

public class World extends Observable {

    private int size;

    private Tank tank;
    private Thread thread;
    private boolean notOver;
    private long delayed = 500;

    public World(int size) {
        this.size = size;
        tank = new Tank(size/2, size/2);
        Random random = new Random();
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

    public boolean isGameOver() {
        return !notOver;
    }
}
