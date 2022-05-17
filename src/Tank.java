import javax.swing.*;
import java.awt.*;

public class Tank extends WObject {
    private Image imageTank = new ImageIcon("img/tank.png").getImage();

    public Tank() {
    }

    public Tank(int x, int y) {
        super(x, y);
    }

    public void getPosition(Tank tank){
        if (tank.isEast()){
            imageTank = new ImageIcon("img/tankEast.png").getImage();
        }else if(tank.isTurnSouth()){
            imageTank = new ImageIcon("img/tankSouth.png").getImage();
        }else  if(tank.isTurnWest()){
            imageTank = new ImageIcon("img/tankWest.png").getImage();
        } else {
            imageTank = new ImageIcon("img/tank.png").getImage();
        }
    }

    public Image getImageTank(){
        return imageTank;
    }


}
