public class EnemyMoving extends Enemy{

    public EnemyMoving(int x, int y) {
        super(x, y);
    }

    public void moving () {
        turnNorth();
        move();
    }
}
