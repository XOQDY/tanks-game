import command.*;
import model.*;

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
    private Sound deadSound;

    public Window() {
        super();
        setTitle("Tank Game");
        addKeyListener(new Controller());
        setLayout(new BorderLayout());
        renderer = new Renderer();
        add(renderer, BorderLayout.CENTER);
        gui = new Gui();
        add(gui, BorderLayout.SOUTH);
        world = new World(25);
        world.addObserver(this);
        setSize(size+15, size+72);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        deadSound = new Sound("src/sound/dead-sound.wav");
    }

    @Override
    public void update(Observable o, Object arg) {
        renderer.repaint();

        if(world.isGameOver()) {
            if (!world.getPlayer1().isAlive() || !world.getPlayer2().isAlive()) {
                deadSound.playSound();
            }
            gui.showGameOverLabel();
            int replay = JOptionPane.showConfirmDialog(this,
                    "Do you want to replay?","GameOver", JOptionPane.YES_NO_OPTION);
            if (replay == JOptionPane.YES_OPTION){
                gui.gameOverLabel.setVisible(false);
                single = false;
                world = new World(25);
                world.addObserver(this);
                addKeyListener(new Controller());
                gui.singlePlayer.setEnabled(true);
                gui.multiPlayer.setEnabled(true);
                repaint();
            } else {System.exit(1);}

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
            paintPlayer1(g);
            if (single) {
                paintEnemies(g);
            } else if (multi) {
                paintPlayer2(g);
            }
            paintBullets(g);
            paintBlock(g);
        }

        private void paintGrids(Graphics g) {
            // Background
            g.setColor(Color.black);
            g.fillRect(0, 0, size, size);
        }

        private void paintBlock(Graphics g) {
            Image brick = new ImageIcon(getClass().getResource("image/block/brick.jpg")).getImage();
            Image steel = new ImageIcon(getClass().getResource("image/block/steel.jpg")).getImage();
            Image tree = new ImageIcon(getClass().getResource("image/block/tree.png")).getImage();

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
            if (!(world.getPlayer1().isAlive())) {
                imageTank = new ImageIcon(getClass().getResource("image/grave.png")).getImage();
            } else if (world.getPlayer1().sameState("north")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank1/tank_north.png")).getImage();
            } else if (world.getPlayer1().sameState("south")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank1/tank_south.png")).getImage();
            } else if (world.getPlayer1().sameState("west")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank1/tank_west.png")).getImage();
            } else if (world.getPlayer1().sameState("east")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank1/tank_east.png")).getImage();
            }

            int perCell = size/world.getSize();
            int x = world.getPlayer1().getX();
            int y = world.getPlayer1().getY();
            g.drawImage(imageTank, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        private void paintPlayer2(Graphics g) {
            if (!(world.getPlayer2().isAlive())) {
                imageTank = new ImageIcon(getClass().getResource("image/grave.png")).getImage();
            } else if (world.getPlayer2().sameState("north")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank2/tank_north.png")).getImage();
            } else if (world.getPlayer2().sameState("south")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank2/tank_south.png")).getImage();
            } else if (world.getPlayer2().sameState("west")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank2/tank_west.png")).getImage();
            } else if (world.getPlayer2().sameState("east")) {
                imageTank = new ImageIcon(getClass().getResource("image/tank2/tank_east.png")).getImage();
            }

            int perCell = size/world.getSize();
            int x = world.getPlayer2().getX();
            int y = world.getPlayer2().getY();
            g.drawImage(imageTank, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
        }

        private void paintEnemies(Graphics g) {
            int perCell = size/world.getSize();
            for(Enemy e : world.getEnemies()) {
                if (!e.isAlive()) {
                    imageTank = new ImageIcon(getClass().getResource("image/grave.png")).getImage();
                } else if (e.sameState("north")) {
                    imageTank = new ImageIcon(getClass().getResource("image/tank3/tank_north.png")).getImage();
                } else if (e.sameState("south")) {
                    imageTank = new ImageIcon(getClass().getResource("image/tank3/tank_south.png")).getImage();
                } else if (e.sameState("west")) {
                    imageTank = new ImageIcon(getClass().getResource("image/tank3/tank_west.png")).getImage();
                } else if (e.sameState("east")) {
                    imageTank = new ImageIcon(getClass().getResource("image/tank3/tank_east.png")).getImage();
                }

                int x = e.getX();
                int y = e.getY();
                g.drawImage(imageTank, x * perCell, y * perCell, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
            }
        }

        private void paintBullets(Graphics g) {
            Image imageBullet = null;
            int perCell = size/world.getSize();
            g.setColor(Color.gray);
            for(Bullet bullet : world.getBullets()) {
                int x = bullet.getX();
                int y = bullet.getY();
                if (bullet.sameState("north")) {
                    imageBullet = new ImageIcon(getClass().getResource("image/bullet/bullet_north.png")).getImage();
                } else if (bullet.sameState("south")) {
                    imageBullet = new ImageIcon(getClass().getResource("image/bullet/bullet_south.png")).getImage();
                } else if (bullet.sameState("west")) {
                    imageBullet = new ImageIcon(getClass().getResource("image/bullet/bullet_west.png")).getImage();
                } else if (bullet.sameState("east")) {
                    imageBullet = new ImageIcon(getClass().getResource("image/bullet/bullet_east.png")).getImage();
                }
                g.drawImage(imageBullet, (x * perCell) + (perCell / 3), (y * perCell) + (perCell / 3), CELL_PIXEL_SIZE/3, CELL_PIXEL_SIZE/3, null, null);
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
                    int num = Integer.parseInt(JOptionPane.showInputDialog(Window.this, "Number of Enemy", "Number of Enemy", JOptionPane.QUESTION_MESSAGE));
                    if(num > 0){
                        setSinglePlayer(num);
                        world.startSingle();
                        single = true;
                        singlePlayer.setEnabled(false);
                        multiPlayer.setEnabled(false);
                        Window.this.requestFocus();
                    }
                }
            });
            add(singlePlayer);
            multiPlayer = new JButton("2 Players");
            multiPlayer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    single = false;
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

        public void setSinglePlayer(int num) {
            world.setWorldSingle(num);
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
            Player player1 = world.getPlayer1();
            Player player2 = world.getPlayer1();
            if (multi) {
                player2 = world.getPlayer2();
            }

            if(e.getKeyCode() == KeyEvent.VK_UP) {
                Command c = new CommandTurnNorth(player1);
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                Command c = new CommandTurnSouth(player1);
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                Command c = new CommandTurnWest(player1);
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                Command c = new CommandTurnEast(player1);
                c.execute();
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                Command c = new CommandTurnNorth(player2);
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_S) {
                Command c = new CommandTurnSouth(player2);
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_A) {
                Command c = new CommandTurnWest(player2);
                c.execute();
            } else if(e.getKeyCode() == KeyEvent.VK_D) {
                Command c = new CommandTurnEast(player2);
                c.execute();
            }

            if (e.getKeyCode() == KeyEvent.VK_SLASH) {
                // shoot bullet
                world.fire_bullet(player1);
            }
            if (e.getKeyCode() == KeyEvent.VK_E) {
                world.fire_bullet(player2);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Player player1 = world.getPlayer1();
            Player player2 = world.getPlayer1();
            if (multi) {
                player2 = world.getPlayer2();
            }
            if ((e.getKeyCode() == KeyEvent.VK_UP && player1.sameState("north"))
                    || (e.getKeyCode() == KeyEvent.VK_DOWN && player1.sameState("south"))
                    || (e.getKeyCode() == KeyEvent.VK_LEFT && player1.sameState("west"))
                    || (e.getKeyCode() == KeyEvent.VK_RIGHT && player1.sameState("east"))){
                Command c = new CommandStop(player1);
                c.execute();
            }
            if ((e.getKeyCode() == KeyEvent.VK_W && player2.sameState("north"))
                    || (e.getKeyCode() == KeyEvent.VK_S && player2.sameState("south"))
                    || (e.getKeyCode() == KeyEvent.VK_A && player2.sameState("west"))
                    || (e.getKeyCode() == KeyEvent.VK_D && player2.sameState("east"))){
                Command c = new CommandStop(player2);
                c.execute();
            }
        }
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }

}
