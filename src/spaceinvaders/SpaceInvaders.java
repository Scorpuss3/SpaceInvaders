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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import spaceinvaders.Entities.*;

public class SpaceInvaders extends JPanel{
    protected int canvasWidth = 300;
    protected int canvasHeight = 400;
    protected final int borderWidth = 5;
    protected final int enemyGridWidth = (canvasWidth - 2*borderWidth) - 80 ;
    protected final int enemyGridHeight = 200;
    protected static SpaceInvaders game;
    protected ArrayList enemies = new ArrayList();
    protected ArrayList enemyBullets = new ArrayList();
    protected ArrayList playerBullets = new ArrayList();
    protected Player player;
    public static int widthMultiplier = 1;
    public static int heightMultiplier = 1;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.blue);
        for (Object object : enemies) {
            Enemy selectedEnemy = (Enemy) object;
            if (selectedEnemy.isActive()) {
                //g2d.fillOval(selectedEnemy.getX(), selectedEnemy.getY(), Enemy.getGenericWidth(), Enemy.getGenericHeight()); //Last two numbers are width, height
                g2d.drawImage(selectedEnemy.getImage(),selectedEnemy.getX()*widthMultiplier,selectedEnemy.getY()*heightMultiplier, selectedEnemy.getImage().getWidth(null)*widthMultiplier,selectedEnemy.getImage().getHeight(null)*heightMultiplier ,this);
            }
        }
        
        for (Object object : enemyBullets) {
            Bullet selectedBullet = (Bullet) object;
            if (selectedBullet.isActive()) {
                //g2d.fillOval(selectedEnemy.getX(), selectedEnemy.getY(), Enemy.getGenericWidth(), Enemy.getGenericHeight()); //Last two numbers are width, height
                g2d.drawImage(selectedBullet.getImage(),selectedBullet.getX()*widthMultiplier,selectedBullet.getY()*heightMultiplier, selectedBullet.getImage().getWidth(null)*widthMultiplier,selectedBullet.getImage().getHeight(null)*heightMultiplier ,this);
            }
        }
        
        for (Object object : playerBullets) {
            Bullet selectedBullet = (Bullet) object;
            if (selectedBullet.isActive()) {
                //g2d.fillOval(selectedEnemy.getX(), selectedEnemy.getY(), Enemy.getGenericWidth(), Enemy.getGenericHeight()); //Last two numbers are width, height
                g2d.drawImage(selectedBullet.getImage(),selectedBullet.getX()*widthMultiplier,selectedBullet.getY()*heightMultiplier, selectedBullet.getImage().getWidth(null)*widthMultiplier,selectedBullet.getImage().getHeight(null)*heightMultiplier ,this);
            }
        }
        
        g2d.setColor(Color.red);
        //g2d.fillRoundRect(player.getX(),player.getY(),player.getWidth(),player.getHeight(),2,2); //TODO do player config for paint().
        g2d.drawImage(player.getImage(),player.getX()*widthMultiplier,player.getY()*heightMultiplier, player.getImage().getWidth(null)*widthMultiplier,player.getImage().getHeight(null)*heightMultiplier ,this);
        // drawImage (image, x, y, new height, new width, null)
        // drawImage (image, x, y, null)
        
        //g2d.fillRect(canvasWidth*widthMultiplier, canvasHeight*heightMultiplier, 2, 2);
        g2d.drawRect(0,0,canvasWidth*widthMultiplier,canvasHeight*heightMultiplier);
        g2d.drawRect(borderWidth,borderWidth,canvasWidth*widthMultiplier-2*borderWidth,canvasHeight*heightMultiplier-2*borderWidth);
        
        if (Game.isPaused()) {
            g2d.setColor(Color.green);
            g2d.drawString("PAUSED",canvasWidth/2-20,canvasHeight/2);
        }
    }
    
    public void initialiseGame() {
        float i;
        System.out.println("Got HERE");
        for (i = 0; i <=20 ; i++) {
            System.out.println("And here");
            Enemy newEnemy;
            if (i <= 5) {
                newEnemy= new Enemy(3);
            } else if ((5 < i) && (i <= 15)) {
                newEnemy= new Enemy(2);
            } else {
                newEnemy= new Enemy();
            }
            
            newEnemy.setX(Math.round( (((i%5)/4) * enemyGridWidth) ) + borderWidth + Enemy.getGenericWidth());
            
            newEnemy.setY((int) ((i-1)/5)*(enemyGridHeight/4) + borderWidth);
            enemies.add(newEnemy);
            System.out.print("New enemy added:" + Float.toString(i));
            System.out.println("At Coordinates: (" + Integer.toString(newEnemy.getX()) + "," + Integer.toString(newEnemy.getY()) + ")");
        }
        player.setX((canvasWidth-borderWidth*2)/2 - player.getWidth()/2); player.setY((canvasHeight - borderWidth)-20);
    }
    
    public void getUserDetails() {
        //TODO add name collection
        player = new Player("Player 1");
    }
    
    public static void setUpFullScreen(JFrame frame, SpaceInvaders game) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        frame.setSize(width,height);
        frame.setUndecorated(true);
        widthMultiplier = width / game.canvasWidth;
        heightMultiplier = height / game.canvasHeight;
        //game.canvasWidth = width-1;
        //game.canvasHeight = height-1;
        //game.setSize(game.canvasWidth, game.canvasHeight);
        game.setSize(game.canvasWidth*widthMultiplier, game.canvasHeight*heightMultiplier);
        System.out.print("Width: " + Integer.toString(game.canvasWidth*widthMultiplier));
        System.out.println("   Height: " + Integer.toString(game.canvasHeight*heightMultiplier));
    }
    
    public static void createUI() {
        JFrame frame = new JFrame("Space Invaders");
        game = new SpaceInvaders();
        game.setSize(game.canvasWidth, game.canvasHeight);
        game.setBackground(Color.black);
        frame.add(game);
        frame.setSize(game.canvasWidth+8,game.canvasHeight+30);
        setUpFullScreen(frame, game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) throws InterruptedException {
        createUI();
        game.getUserDetails();
        game.initialiseGame();
        Game.runAllGameLoops(game);
    }
}
