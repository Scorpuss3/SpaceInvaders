/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Acer Laptop
 */
package spaceinvaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import spaceinvaders.Entities.*;
import spaceinvaders.HighScores.HighScores;
import spaceinvaders.Levels.LevelSet;

public class SpaceInvaders extends JPanel{
    protected int canvasWidth = 600;
    protected int canvasHeightGame = 400;
    protected int canvasHeightGameAndHUD = 450;
    protected final int borderWidth = 5;
    protected int enemyGridWidth;
    protected int enemyGridHeight;
    protected int lineOfDefense = canvasHeightGame - 50;
    protected static SpaceInvaders game;
    private static boolean fullscreen = true;
    protected Enemy bonusEnemy;
    protected ConcurrentHashMap<Integer, Enemy> enemies = new ConcurrentHashMap<Integer, Enemy>();
    protected ConcurrentHashMap<Integer, Bullet> enemyBullets = new ConcurrentHashMap<Integer, Bullet>();
    protected ConcurrentHashMap<Integer, Bullet> playerBullets = new ConcurrentHashMap<Integer, Bullet>();
    protected ConcurrentHashMap<Integer, Barrier> barriers = new ConcurrentHashMap<Integer, Barrier>();
    protected ConcurrentHashMap<Integer, Bonus> bonuses = new ConcurrentHashMap<Integer, Bonus>();
    public Player player;
    public static float aspectMultiplier = 1;
    private static JFrame frame, loadFrame, blank;
    private static HUD hud;
    public static volatile int level = 0; // Is deactivated until started by the menu.
    //Essentially, volatile is used to indicate that a variable's value will be modified by different threads.
    // needed here to keep the main loop running- otherwise, the program sees no edit to 'level' in the future,
    // because it it only looking for references in this class.
    public static LevelSet currentLevelSet;
    private final Dimension[] starDimensions = new Dimension[100];
    public final static boolean muted = true;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        SpaceInvaders freezeFrame = this;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRect(0,0,(int) (canvasWidth*aspectMultiplier),(int) (canvasHeightGame*aspectMultiplier));
        g2d.setColor(Color.LIGHT_GRAY);
        for (Dimension d : starDimensions) {
            g2d.drawRect(d.height,d.width, 1, 1);
        }
        for (Map.Entry<Integer, Enemy> e : freezeFrame.enemies.entrySet()) {
            Enemy selectedEnemy = (Enemy) e.getValue();
            if (selectedEnemy.isActive()) {
                g2d.drawImage(selectedEnemy.getImage(),(int) (selectedEnemy.getX()*aspectMultiplier),(int) (selectedEnemy.getY()*aspectMultiplier), (int) (selectedEnemy.getImage().getWidth(null)*aspectMultiplier),(int) (selectedEnemy.getImage().getHeight(null)*aspectMultiplier) ,this);
                //g2d.drawString(Integer.toString(selectedEnemy.getHealth()),(int) (selectedEnemy.getX()*aspectMultiplier),(int) (selectedEnemy.getY()*aspectMultiplier));
            }
        }
        
        for (Map.Entry<Integer, Bullet> e : freezeFrame.enemyBullets.entrySet()) {
            Bullet selectedBullet = (Bullet) e.getValue();
            if (selectedBullet.isActive()) {
                g2d.drawImage(selectedBullet.getImage(),(int) (selectedBullet.getX()*aspectMultiplier),(int) (selectedBullet.getY()*aspectMultiplier), (int) (selectedBullet.getImage().getWidth(null)*aspectMultiplier),(int) (selectedBullet.getImage().getHeight(null)*aspectMultiplier) ,this);
            }
        }
        
        for (Map.Entry<Integer, Bullet> e : freezeFrame.playerBullets.entrySet()) {
            Bullet selectedBullet = (Bullet) e.getValue();
            if (selectedBullet.isActive()) {
                g2d.drawImage(selectedBullet.getImage(),(int) (selectedBullet.getX()*aspectMultiplier),(int) (selectedBullet.getY()*aspectMultiplier), (int) (selectedBullet.getImage().getWidth(null)*aspectMultiplier),(int) (selectedBullet.getImage().getHeight(null)*aspectMultiplier) ,this);
            }
        }
        
        for (Map.Entry<Integer, Barrier> e : freezeFrame.barriers.entrySet()) {
            Barrier selectedBarrier = (Barrier) e.getValue();
            if (selectedBarrier.isActive()) {
                g2d.drawImage(selectedBarrier.getImage(),(int) (selectedBarrier.getX()*aspectMultiplier),(int) (selectedBarrier.getY()*aspectMultiplier), (int) (selectedBarrier.getImage().getWidth(null)*aspectMultiplier),(int) (selectedBarrier.getImage().getHeight(null)*aspectMultiplier) ,this);
            }
        }
        
        for (Map.Entry<Integer, Bonus> e : freezeFrame.bonuses.entrySet()) {
            Bonus selectedBonus = (Bonus) e.getValue();
            if (selectedBonus.isActive()) {
                g2d.drawImage(selectedBonus.getImage(),(int) (selectedBonus.getX()*aspectMultiplier),(int) (selectedBonus.getY()*aspectMultiplier), (int) (selectedBonus.getImage().getWidth(null)*aspectMultiplier),(int) (selectedBonus.getImage().getHeight(null)*aspectMultiplier) ,this);
            }
        }
        
        if (bonusEnemy.isActive()) {
            g2d.drawImage(freezeFrame.bonusEnemy.getImage(), (int) (freezeFrame.bonusEnemy.getX()*aspectMultiplier), (int) (freezeFrame.bonusEnemy.getY()*aspectMultiplier),(int) (freezeFrame.bonusEnemy.getImage().getWidth(null)*aspectMultiplier), (int) (freezeFrame.bonusEnemy.getImage().getHeight(null)*aspectMultiplier) ,this);
        }
        
        g2d.setColor(Color.red);
        g2d.drawImage(freezeFrame.player.getImage(),(int) (player.getX()*aspectMultiplier),(int) (player.getY()*aspectMultiplier), (int) (player.getImage().getWidth(null)*aspectMultiplier),(int) (player.getImage().getHeight(null)*aspectMultiplier) ,this);
        // drawImage (image, x, y, new height, new width, null)
        // drawImage (image, x, y, null)
        
        g2d.drawRect(2,2,(int) (canvasWidth*aspectMultiplier) -4,(int) (canvasHeightGame*aspectMultiplier) -4);
        g2d.drawRect(borderWidth,borderWidth,(int) (canvasWidth*aspectMultiplier-2*borderWidth),(int) (canvasHeightGame*aspectMultiplier-2*borderWidth));
        
        g2d.drawLine(0, (int) (lineOfDefense*aspectMultiplier), (int) (canvasWidth*aspectMultiplier), (int) (lineOfDefense * aspectMultiplier));
        
        if (Game.isPaused()) {
            g2d.setColor(Color.green);
            g2d.setFont(new Font("Gill Sans", Font.BOLD,(int) (20*SpaceInvaders.aspectMultiplier)));
            g2d.drawString("PAUSED!!",(int) ((float)canvasWidth*aspectMultiplier/2)-40,(int) ((float)canvasHeightGame*aspectMultiplier/2));
        }
        hud.repaint();
    }
    
    public void initialiseGame(LoadingBar loadPanel, int levelToLoad) {
        System.out.println("Initialising level " + Integer.toString(levelToLoad));
        currentLevelSet = new LevelSet(levelToLoad, loadPanel);
        int numInRow = currentLevelSet.numInRow;
        int numInColumn = currentLevelSet.numInColumn;
        enemyGridWidth = currentLevelSet.enemyGridWidth;
        enemyGridHeight = currentLevelSet.enemyGridHeight;
        int[] enemyLevelMap = currentLevelSet.rowLevels;
        float i;
        float row;
        for (row = 1 ; row <= numInColumn ; row++) {
            System.out.println(row);
            for (i = 1; i <=numInRow ; i++) {
                Enemy newEnemy;
                newEnemy= new Enemy(enemyLevelMap[(int) (row-1)]);

                newEnemy.setX(Math.round( ((i/numInRow) * enemyGridWidth) ) + borderWidth + Enemy.getGenericWidth());

                newEnemy.setY(((int) ((row / numInColumn)*enemyGridHeight)) + borderWidth);
                System.out.println("Y was set to: " + Integer.toString(newEnemy.getY()));
                enemies.put(enemies.size(),newEnemy);
                
                loadPanel.increment("Adding enemies");
            }
        }
        bonusEnemy = new Enemy(4); bonusEnemy.deactivate();
        
        player.setX((canvasWidth-borderWidth*2)/2 - player.getWidth()/2); player.setY((canvasHeightGame - borderWidth)-20);
        loadPanel.increment("Adding stars");
        for (int ii = 0; ii < 100; ii++) {
            Dimension d = new Dimension((int) (Math.random() * canvasHeightGame * aspectMultiplier),(int) (Math.random() * canvasWidth * aspectMultiplier));
            starDimensions[ii] = d;
        }
        for (float iii = 0; iii < 4 ; iii++){
            loadPanel.increment("Adding Barriers");
            Barrier newBarrier = new Barrier();
            newBarrier.setX(Math.round( ((iii/4) * canvasWidth-(2*borderWidth)) ) + borderWidth + newBarrier.getWidth());
            newBarrier.setY(lineOfDefense);
            barriers.put(barriers.size(),newBarrier);
        }
        loadPanel.increment("Starting game...");
    }
    
    public void getUserDetails(LoadingBar loadBar) {
        System.out.println("Creating player...");
        if (level <= 1) {
            player = new Player(JOptionPane.showInputDialog(null,"Enter Player Name:"));
        }
        System.out.println("Created player...");
    }
    
    public static void setUpFullScreen(JPanel ctp, SpaceInvaders game) {
        ctp.setBackground(Color.BLACK);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        frame.setSize(width,height);
        frame.setUndecorated(true);
        //If statement keps aspect ratio:
        if ((float)width / game.canvasWidth <= (float)height / game.canvasHeightGameAndHUD) {
            // Aspect dictated by the width difference.
            aspectMultiplier = (float)width / game.canvasWidth;
        } else {
            // Aspect dictated by the height difference.
            aspectMultiplier = (float)height / game.canvasHeightGameAndHUD;
        }
        game.setSize(game.canvasWidth*(int)aspectMultiplier, game.canvasHeightGame*(int)aspectMultiplier);
        System.out.print("Width: " + Float.toString(game.canvasWidth*aspectMultiplier));
        System.out.println("   Height: " + Float.toString(game.canvasHeightGame*aspectMultiplier));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.ipadx = (int) (game.canvasWidth * aspectMultiplier)-8; gbc.ipady = (int) (game.canvasHeightGame * aspectMultiplier);
        ctp.add(game, gbc);
        GridBagConstraints hudGbc = new GridBagConstraints();
        hudGbc.gridx = 0; hudGbc.gridy = 1; hudGbc.ipadx = width; hudGbc.ipady = height - gbc.ipady;
        ctp.add(hud,hudGbc);
    }
    
    public static void createUI(LoadingBar loadBar) {
        System.out.println("Creating frame:");
        frame = new JFrame("Space Invaders");
        JPanel ctp = new JPanel(new GridBagLayout());
        frame.add(ctp);
        
        loadBar.increment("Creating game");
        if (level == 1) {
            game = new SpaceInvaders();
        } else {
            game = new SpaceInvaders(game.player);
        }
        loadBar.increment("setting up canvas");
        game.setSize(game.canvasWidth, game.canvasHeightGame);
        game.setBackground(Color.black);
        loadBar.increment("Adding Game");
        frame.setSize(game.canvasWidth+8,game.canvasHeightGameAndHUD+30);
        
        hud = new HUD(game,(game.canvasHeightGameAndHUD - game.canvasHeightGame),game.canvasWidth);
        if (fullscreen) {
            setUpFullScreen(ctp, game);
        }
        
        
        if (!fullscreen) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0; gbc.gridy = 0; gbc.ipadx = game.canvasWidth; gbc.ipady = game.canvasHeightGame;
            ctp.add(game, gbc);
            GridBagConstraints hudGbc = new GridBagConstraints();
            hudGbc.gridx = 0; hudGbc.gridy = 1; hudGbc.ipadx = game.canvasWidth; hudGbc.ipady = game.canvasHeightGameAndHUD - game.canvasHeightGame;
            ctp.add(hud,hudGbc);
        }
        
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        loadBar.increment("Finished Frame");
    }
    
    public static LoadingBar setUpLoadingScreen() {
        loadFrame = new JFrame();
        loadFrame.setSize(400,50);
        loadFrame.setUndecorated(true);
        LoadingBar loadBar = new LoadingBar("Level " + Integer.toString(level));
        loadBar.setSize(loadBar.totalLength,loadBar.totalHeight);
        loadFrame.add(loadBar);
        loadFrame.setLocationRelativeTo(null);
        loadFrame.setVisible(true);
        return loadBar;
    }
    
    private static void startMenu() {
        blank = new JFrame();
        blank.setUndecorated(true);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        blank.setSize(width,height);
        JPanel blankPanel = new JPanel();
        blankPanel.setBackground(Color.BLACK);
        blank.add(blankPanel);
        blank.setLocationRelativeTo(null);
        blank.setVisible(true);
        
        MainMenu.start(width,height);
    }
    
    public SpaceInvaders (Player oldPlayer) {
        player = oldPlayer;
    }
    
    public SpaceInvaders () {
    }
    
    public static void main(String[] args) throws InterruptedException {
        int loadedLevel = 0;
        startMenu();
        while (true) {
            if (loadedLevel != level && level > 0) {
                loadedLevel = level;
                if (level > 0 && !LevelSet.levelExists(level)) {
                    System.out.println("Level does not exist: " + Integer.toString(level));
                    JOptionPane.showMessageDialog(game,"YOU WIN!!!");
                    if (JOptionPane.showConfirmDialog(game, "Save High Score?") == JOptionPane.YES_OPTION) {
                        HighScores.addRecord(game.player.getName(),game.player.getScore());
                    }
                    System.exit(0);
                }
                if (level > 1) {
                    blank = new JFrame();
                    blank.setUndecorated(true);
                    blank.setSize(frame.getSize());
                    JPanel blankPanel = new JPanel();
                    blankPanel.setBackground(Color.BLACK);
                    blank.add(blankPanel);
                    blank.setLocationRelativeTo(null);
                    blank.setVisible(true);
                    Thread.sleep(400);
                    frame.setVisible(false);
                }
                
                LoadingBar loadBar = setUpLoadingScreen();
                createUI(loadBar);
                loadBar.increment("Getting player name");
                game.getUserDetails(loadBar);
                loadBar.increment("Beginning game init");
                game.initialiseGame(loadBar, level);

                loadFrame.setVisible(false);
                frame.setVisible(true);
                if (level > 1) {
                    Thread.sleep(400);
                    blank.setVisible(false);
                }

                Game.runAllGameLoops(game);
            }
        }
    }
}
