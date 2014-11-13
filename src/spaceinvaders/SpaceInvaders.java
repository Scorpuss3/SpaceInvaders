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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import spaceinvaders.Entities.*;
import spaceinvaders.Levels.LevelSet;

public class SpaceInvaders extends JPanel{
    protected int canvasWidth = 600;
    protected int canvasHeight = 400;//500
    protected final int borderWidth = 5;
    protected int enemyGridWidth;// = (canvasWidth - 2*borderWidth) - 80 ;(Also make final)
    protected int enemyGridHeight;// = 100;(Also make final)
    protected static SpaceInvaders game;
    protected ArrayList enemies = new ArrayList();
    protected ArrayList enemyBullets = new ArrayList();
    protected ArrayList playerBullets = new ArrayList();
    protected Player player;
    public static float aspectMultiplier = 1;
    private static JFrame frame, loadFrame, blank;
    public static volatile int level = 1;
    //Essentially, volatile is used to indicate that a variable's value will be modified by different threads.
    // needed here to keep the main loop running- otherwise, the program sees no edit to 'level' in the future,
    // because it it only looking for references in this class.
    public static LevelSet currentLevelSet;
    private final Dimension[] starDimensions = new Dimension[100];
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        SpaceInvaders freezeFrame = this;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        //g2d.setColor(Color.blue);
        g2d.drawRect(0,0,(int) (canvasWidth*aspectMultiplier),(int) (canvasHeight*aspectMultiplier));
        g2d.setColor(Color.LIGHT_GRAY);
        for (Dimension d : starDimensions) {
            //g2d.drawRect(d.width,d.height, 1, 1);
            g2d.drawRect(d.height,d.width, 1, 1);
        }
        for (Object object : freezeFrame.enemies) {
            Enemy selectedEnemy = (Enemy) object;
            if (selectedEnemy.isActive()) {
                g2d.drawImage(selectedEnemy.getImage(),(int) (selectedEnemy.getX()*aspectMultiplier),(int) (selectedEnemy.getY()*aspectMultiplier), (int) (selectedEnemy.getImage().getWidth(null)*aspectMultiplier),(int) (selectedEnemy.getImage().getHeight(null)*aspectMultiplier) ,this);
                //g2d.drawString(Integer.toString(selectedEnemy.getHealth()),(int) (selectedEnemy.getX()*aspectMultiplier),(int) (selectedEnemy.getY()*aspectMultiplier));
            }
        }
        
        for (Object object : freezeFrame.enemyBullets) {
            Bullet selectedBullet = (Bullet) object;
            if (selectedBullet.isActive()) {
                g2d.drawImage(selectedBullet.getImage(),(int) (selectedBullet.getX()*aspectMultiplier),(int) (selectedBullet.getY()*aspectMultiplier), (int) (selectedBullet.getImage().getWidth(null)*aspectMultiplier),(int) (selectedBullet.getImage().getHeight(null)*aspectMultiplier) ,this);
            }
        }
        
        for (Object object : freezeFrame.playerBullets) {
            Bullet selectedBullet = (Bullet) object;
            if (selectedBullet.isActive()) {
                g2d.drawImage(selectedBullet.getImage(),(int) (selectedBullet.getX()*aspectMultiplier),(int) (selectedBullet.getY()*aspectMultiplier), (int) (selectedBullet.getImage().getWidth(null)*aspectMultiplier),(int) (selectedBullet.getImage().getHeight(null)*aspectMultiplier) ,this);
            }
        }
        
        g2d.setColor(Color.red);
        g2d.drawImage(freezeFrame.player.getImage(),(int) (player.getX()*aspectMultiplier),(int) (player.getY()*aspectMultiplier), (int) (player.getImage().getWidth(null)*aspectMultiplier),(int) (player.getImage().getHeight(null)*aspectMultiplier) ,this);
        // drawImage (image, x, y, new height, new width, null)
        // drawImage (image, x, y, null)
        
        g2d.drawRect(2,2,(int) (canvasWidth*aspectMultiplier) -4,(int) (canvasHeight*aspectMultiplier) -4);
        g2d.drawRect(borderWidth,borderWidth,(int) (canvasWidth*aspectMultiplier-2*borderWidth),(int) (canvasHeight*aspectMultiplier-2*borderWidth));
        
        if (Game.isPaused()) {
            g2d.setColor(Color.green);
            g2d.drawString("PAUSED",canvasWidth/2-20,canvasHeight/2);
        }
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
                enemies.add(newEnemy);
                
                loadPanel.increment("Adding enemies");
            }
        }
        player.setX((canvasWidth-borderWidth*2)/2 - player.getWidth()/2); player.setY((canvasHeight - borderWidth)-20);
        loadPanel.increment("Adding stars");
        for (int ii = 0; ii < 100; ii++) {
            Dimension d = new Dimension((int) (Math.random() * canvasHeight * aspectMultiplier),(int) (Math.random() * canvasWidth * aspectMultiplier));
            starDimensions[ii] = d;
        }
        loadPanel.increment("Starting game...");
    }
    
    public void getUserDetails(LoadingBar loadBar) {
        //TODO add name collection
        System.out.println("Creating player...");
        player = new Player("Player 1");
        System.out.println("Created player...");
    }
    
    public static void setUpFullScreen(JFrame frame, SpaceInvaders game) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        frame.setSize(width,height);
        frame.setUndecorated(true);
        //If statement keps aspect ratio:
        if ((float)width / game.canvasWidth <= (float)height / game.canvasHeight) {
            // Aspect dictated by the width difference.
            aspectMultiplier = (float)width / game.canvasWidth;
        } else {
            // Aspect dictated by the height difference.
            aspectMultiplier = (float)height / game.canvasHeight;
        }
        game.setSize(game.canvasWidth*(int)aspectMultiplier, game.canvasHeight*(int)aspectMultiplier);
        System.out.print("Width: " + Float.toString(game.canvasWidth*aspectMultiplier));
        System.out.println("   Height: " + Float.toString(game.canvasHeight*aspectMultiplier));
    }
    
    public static void createUI(LoadingBar loadBar) {
        System.out.println("Creating frame:");
        frame = new JFrame("Space Invaders");
        loadBar.increment("Creating game");
        game = new SpaceInvaders();
        loadBar.increment("setting up canvas");
        game.setSize(game.canvasWidth, game.canvasHeight);
        game.setBackground(Color.black);
        loadBar.increment("Adding Game");
        frame.add(game);
        frame.setSize(game.canvasWidth+8,game.canvasHeight+30);
        //setUpFullScreen(frame, game);
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
    
    public static void main(String[] args) throws InterruptedException {
        int loadedLevel = 0;
        while (true) {
            if (loadedLevel != level) {
                loadedLevel = level;
                if (level > 0 && !LevelSet.levelExists(level)) {
                    System.out.println("Level does not exist: " + Integer.toString(level));
                    System.exit(0); //Later replace with some exit script...
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
                    Thread.sleep(100);
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
                    blank.setVisible(false);
                }

                Game.runAllGameLoops(game);
            }
        }
    }
}
