package model;

import command.Command;
import strategy.MoveStrategy;

public class Enemy extends Player {

    private MoveStrategy moveStrategy;
    private Command command ;

    public Enemy() {
    }

    public Enemy(int x, int y, MoveStrategy moveStrategy) {
        super(x, y);
        this.moveStrategy = moveStrategy;
    }

    public Command moving(World world){
        command = moveStrategy.getNextMoveCommand(world, this);
        return command;
    }

}
