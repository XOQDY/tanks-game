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

        public Renderer() {
            setDoubleBuffered(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintGrids(g);
            paintPlayer1(g);
            paintPlayer2(g);
            paintBullets(g);
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

        private void paintPlayer1(Graphics g) {
            int perCell = size/world.getSize();
            int x = world.getPlayer1().getX();
            int y = world.getPlayer1().getY();
            g.setColor(Color.green);
            g.fillRect(x * perCell,y * perCell,perCell, perCell);
        }

        private void paintPlayer2(Graphics g) {
            int perCell = size/world.getSize();
            int x = world.getPlayer2().getX();
            int y = world.getPlayer2().getY();
            g.setColor(Color.red);
            g.fillRect(x * perCell,y * perCell,perCell, perCell);
        }

        private void paintBullets(Graphics g) {
            int perCell = size/world.getSize();
            g.setColor(Color.gray);
            for(Bullet bullet : world.getBullets()) {
                g.fillOval((bullet.getX() * perCell) + (perCell / 4), (bullet.getY() * perCell) + (perCell / 4), 10, 10);
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
                Command c = new CommandTurnNorth(world.getPlayer1());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                Command c = new CommandTurnSouth(world.getPlayer1());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                Command c = new CommandTurnWest(world.getPlayer1());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                Command c = new CommandTurnEast(world.getPlayer1());
                c.execute();
            }

            if (e.getKeyCode() == KeyEvent.VK_W) {
                Command c = new CommandTurnNorth(world.getPlayer2());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_S) {
                Command c = new CommandTurnSouth(world.getPlayer2());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_A) {
                Command c = new CommandTurnWest(world.getPlayer2());
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_D) {
                Command c = new CommandTurnEast(world.getPlayer2());
                c.execute();
            }

            if (e.getKeyCode() == KeyEvent.VK_SLASH) {
                // shoot bullet
                world.fire_bullet(world.getPlayer1());
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                world.fire_bullet(world.getPlayer2());
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Player player1 = world.getPlayer1();
            Player player2 = world.getPlayer2();
            if ((e.getKeyCode() == KeyEvent.VK_UP && player1.sameState("north"))
                    || (e.getKeyCode() == KeyEvent.VK_DOWN && player1.sameState("south"))
                    || (e.getKeyCode() == KeyEvent.VK_LEFT && player1.sameState("west"))
                    || (e.getKeyCode() == KeyEvent.VK_RIGHT && player1.sameState("east"))){
                Command c = new CommandStop(world.getPlayer1());
                c.execute();
            }
            if ((e.getKeyCode() == KeyEvent.VK_W && player2.sameState("north"))
                    || (e.getKeyCode() == KeyEvent.VK_S && player2.sameState("south"))
                    || (e.getKeyCode() == KeyEvent.VK_A && player2.sameState("west"))
                    || (e.getKeyCode() == KeyEvent.VK_D && player2.sameState("east"))){
                Command c = new CommandStop(world.getPlayer2());
                c.execute();
            }
        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }

}
