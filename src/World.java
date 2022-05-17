import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class World extends Observable {

    private int size;

    private Tank tank;
    private List<Bricks> bricks;
    private List<Steel> steels;
    private List<Trees> trees;

    private Thread thread;

    private boolean notOver;

    private long delayed = 500;

    public World(int size) {
        Map  map = new Map();
        this.size = size;
        tank = new Tank(size/2, size/2);
        int[][] tree = map.Trees;
        int[][] brick = map.brick;
        int[][] steel = map.steel;
        initBrick(brick);
        initSteel(steel);
        initTrees(tree);
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

    public int getSize() {
        return size;
    }

    public Tank getTank() {
        return tank;
    }

    public List<Bricks> getBricks(){
        return bricks;
    }

    public List<Steel> getSteels(){
        return steels;
    }

    public List<Trees> getTrees(){
        return trees;
    }

    public boolean isGameOver() {
        return !notOver;
    }
}
