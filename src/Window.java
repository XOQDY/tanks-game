import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class Window extends JFrame implements Observer {

    private int size = 500;
    private World world;
    private Renderer renderer;
    private Gui gui;

    public Window() {
        super();
        addKeyListener(new Controller());
        setLayout(new BorderLayout());
        renderer = new Renderer();
        add(renderer, BorderLayout.CENTER);
        gui = new Gui();
        add(gui, BorderLayout.SOUTH);
        world = new World(25);
        world.addObserver(this);
        setSize(size, size);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        renderer.repaint();

        if(world.isGameOver()) {
            gui.showGameOverLabel();
        }
    }

    class Renderer extends JPanel {
        public static final int CELL_PIXEL_SIZE = 20;
        private Image imageTank, imageBricks,imageSteel, imageTrees;

        public Renderer() {
            setDoubleBuffered(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintGrids(g);
            paintTank(g);
            paintBricks(g);
            paintSteel(g);
            paintTrees(g);
        }

        private void paintGrids(Graphics g) {
            // Background
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, size, size);

            // Lines
            g.setColor(Color.black);
            int perCell = size/world.getSize();
            for(int i = 0; i < world.getSize(); i++) {
                g.drawLine(i * perCell, 0, i * perCell, size);
                g.drawLine(0, i * perCell, size, i * perCell);
            }
        }

        private void paintTank(Graphics g) {
            imageTank = new ImageIcon("img/tank.png").getImage();
            int perCell = size/world.getSize();
            int x = world.getTank().getX();
            int y = world.getTank().getY();
            g.drawImage(world.getTank().getImageTank(), x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        private void paintBricks(Graphics g){
            imageBricks = new ImageIcon("img/bricks.jpg").getImage();
            int perCell = size/world.getSize();
            for(Bricks b: world.getBricks()){
                int x = b.getX();
                int y = b.getY();
                g.drawImage(imageBricks, x* perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null,null);
            }

        }

        private void paintSteel(Graphics g ){
            imageSteel = new ImageIcon("img/steel.jpg").getImage();
            int perCell = size/world.getSize();
            for(Steel s: world.getSteels()){
                int x = s.getX();
                int y = s.getY();
                g.drawImage(imageSteel, x* perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null,null);
            }
        }

        private void paintTrees(Graphics g){
            imageTrees = new ImageIcon("img/trees.jpg").getImage();
            int perCell = size/world.getSize();
            for(Trees t: world.getTrees()){
                int x = t.getX();
                int y = t.getY();
                g.drawImage(imageTrees, x* perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null,null);
            }
        }

    }

    class Gui extends JPanel {

        private JButton startButton;
        private JLabel gameOverLabel;

        public Gui() {
            setLayout(new FlowLayout());
            startButton = new JButton("Start");
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    world.start();
                    startButton.setEnabled(false);
                    Window.this.requestFocus();
                }
            });
            add(startButton);
            gameOverLabel = new JLabel("GAME OVER");
            gameOverLabel.setForeground(Color.red);
            gameOverLabel.setVisible(false);
            add(gameOverLabel);
        }

        public void showGameOverLabel() {
            gameOverLabel.setVisible(true);
        }
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                Command c = new CommandTurnNorth(world.getTank());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                Command c = new CommandTurnSouth(world.getTank());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                Command c = new CommandTurnWest(world.getTank());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                Command c = new CommandTurnEast(world.getTank());
                c.execute();
            }
        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }

}
