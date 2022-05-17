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
    private Image imageTank = null;
    private boolean single = false;
    private boolean multi = false;

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

        public Renderer() {
            setDoubleBuffered(true);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            paintGrids(g);
            paintBlock(g);
            paintPlayer1(g);
            if (single) {
                paintEnemies(g);
            } else if (multi && world.getPlayer2().isAlive()) {
                paintPlayer2(g);
            }
            paintBullets(g);
        }

        private void paintGrids(Graphics g) {
            // Background
            g.setColor(Color.black);
            g.fillRect(0, 0, size, size);

            // Lines
//            g.setColor(Color.black);
//            int perCell = size/world.getSize();
//            for(int i = 0; i < world.getSize(); i++) {
//                g.drawLine(i * perCell, 0, i * perCell, size);
//                g.drawLine(0, i * perCell, size, i * perCell);
//            }
        }

        private void paintBlock(Graphics g) {
            Image brick = new ImageIcon("image/block/brick.jpg").getImage();
            Image steel = new ImageIcon("image/block/steel.jpg").getImage();
            Image tree = new ImageIcon("image/block/tree.jpg").getImage();

            int perCell = size/world.getSize();
            int x;
            int y;
            for (Block[] blocks : world.getBlocks()) {
                for (Block block : blocks) {
                    if (block != null) {
                        x = block.getX();
                        y = block.getY();
                        if (block instanceof Brick) {
                            g.drawImage(brick, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                        } else if (block instanceof Steel) {
                            g.drawImage(steel, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                        } else if (block instanceof Tree) {
                            g.drawImage(tree, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                        }
                    }

                }
            }
        }

        private void paintPlayer1(Graphics g) {
            if (world.getPlayer1().sameState("north")) {
                imageTank = new ImageIcon("image/tank1/tank_north.png").getImage();
            } else if (world.getPlayer1().sameState("south")) {
                imageTank = new ImageIcon("image/tank1/tank_south.png").getImage();
            } else if (world.getPlayer1().sameState("west")) {
                imageTank = new ImageIcon("image/tank1/tank_west.png").getImage();
            } else if (world.getPlayer1().sameState("east")) {
                imageTank = new ImageIcon("image/tank1/tank_east.png").getImage();
            }

            int perCell = size/world.getSize();
            int x = world.getPlayer1().getX();
            int y = world.getPlayer1().getY();
            g.drawImage(imageTank, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        private void paintPlayer2(Graphics g) {
            if (world.getPlayer2().sameState("north")) {
                imageTank = new ImageIcon("image/tank2/tank_north.png").getImage();
            } else if (world.getPlayer2().sameState("south")) {
                imageTank = new ImageIcon("image/tank2/tank_south.png").getImage();
            } else if (world.getPlayer2().sameState("west")) {
                imageTank = new ImageIcon("image/tank2/tank_west.png").getImage();
            } else if (world.getPlayer2().sameState("east")) {
                imageTank = new ImageIcon("image/tank2/tank_east.png").getImage();
            }
            int perCell = size/world.getSize();
            int x = world.getPlayer2().getX();
            int y = world.getPlayer2().getY();
            g.drawImage(imageTank, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        private void paintEnemies(Graphics g) {
            int perCell = size/world.getSize();
            for(Enemy e : world.getEnemies()) {
                if (e.isAlive()) {
                    int x = e.getX();
                    int y = e.getY();
                    if (e.sameState("north")) {
                        imageTank = new ImageIcon("image/tank3/tank_north.png").getImage();
                    } else if (e.sameState("south")) {
                        imageTank = new ImageIcon("image/tank3/tank_south.png").getImage();
                    } else if (e.sameState("west")) {
                        imageTank = new ImageIcon("image/tank3/tank_west.png").getImage();
                    } else if (e.sameState("east")) {
                        imageTank = new ImageIcon("image/tank3/tank_east.png").getImage();
                    }
                    g.drawImage(imageTank, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                }

            }
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

        private JButton singlePlayer;
        private JButton multiPlayer;
        private JLabel gameOverLabel;

        public Gui() {
            setLayout(new FlowLayout());
            singlePlayer = new JButton("1 Player");
            singlePlayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setSinglePlayer();
                    world.startSingle();
                    single = true;
                    singlePlayer.setEnabled(false);
                    multiPlayer.setEnabled(false);
                    Window.this.requestFocus();
                }
            });
            add(singlePlayer);
            multiPlayer = new JButton("2 Players");
            multiPlayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setMultiPlayer();
                    world.startMulti();
                    multi = true;
                    singlePlayer.setEnabled(false);
                    multiPlayer.setEnabled(false);
                    Window.this.requestFocus();
                }
            });
            add(multiPlayer);
            gameOverLabel = new JLabel("GAME OVER");
            gameOverLabel.setForeground(Color.red);
            gameOverLabel.setVisible(false);
            add(gameOverLabel);
        }

        public void setSinglePlayer() {
            world.setWorldSingle();
        }

        public void setMultiPlayer() {
            world.setWorldMulti();
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

            if (e.getKeyCode() == KeyEvent.VK_SLASH) {
                // shoot bullet
                world.fire_bullet(world.getPlayer1());
            }

            if (multi) {
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

                if (e.getKeyCode() == KeyEvent.VK_E) {
                    world.fire_bullet(world.getPlayer2());
                }
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
            if (multi) {
                if ((e.getKeyCode() == KeyEvent.VK_W && player2.sameState("north"))
                        || (e.getKeyCode() == KeyEvent.VK_S && player2.sameState("south"))
                        || (e.getKeyCode() == KeyEvent.VK_A && player2.sameState("west"))
                        || (e.getKeyCode() == KeyEvent.VK_D && player2.sameState("east"))){
                    Command c = new CommandStop(world.getPlayer2());
                    c.execute();
                }
            }

        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }

}
