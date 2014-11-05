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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import spaceinvaders.Entities.*;

public class SpaceInvaders extends JPanel{
    protected final int canvasWidth = 300;
    protected final int canvasHeight = 400;
    protected final int borderWidth = 5;
    protected final int enemyGridWidth = (canvasWidth - 2*borderWidth) - 80 ;
    protected final int enemyGridHeight = 200;
    protected final int halfEnemyWidth = 15; // Will be implemented somewhere else later
    protected static SpaceInvaders game;
    protected ArrayList enemies = new ArrayList();
    protected Player player;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Object object : enemies) {
            Enemy selectedEnemy = (Enemy) object;
            if (selectedEnemy.isActive()) {
                g2d.fillOval(selectedEnemy.getX(), selectedEnemy.getY(), 2*halfEnemyWidth, halfEnemyWidth); //Last two numbers are width, height
            }
        }
        g2d.fillRect(canvasWidth, canvasHeight, 2, 2);
        g2d.drawRect(0,0,canvasWidth,canvasHeight);
        g2d.drawRect(borderWidth,borderWidth,canvasWidth-2*borderWidth,canvasHeight-2*borderWidth);
    }
    
    public void initialiseGame() {
        float i;
        System.out.println("Got HERE");
        for (i = 0; i <=20 ; i++) {
            System.out.println("And here");
            Enemy newEnemy= new Enemy();
            
            newEnemy.setX(Math.round( (((i%5)/4) * enemyGridWidth) ) + borderWidth + halfEnemyWidth);
            
            newEnemy.setY((int) ((i-1)/5)*(enemyGridHeight/4) + borderWidth);
            enemies.add(newEnemy);
            System.out.print("New enemy added:" + Float.toString(i));
            System.out.println("At Coordinates: (" + Integer.toString(newEnemy.getX()) + "," + Integer.toString(newEnemy.getY()) + ")");
        }
        player.setX(canvasWidth/2); player.setY(canvasHeight - borderWidth);
    }
    
    public void getUserDetails() {
        //TODO add name collection
        player = new Player("Player 1");
    }
    
    public static void createUI() {
        JFrame frame = new JFrame("Space Invaders");
        game = new SpaceInvaders();
        game.setSize(game.canvasWidth, game.canvasHeight);
        frame.add(game);
        frame.setSize(game.canvasWidth+8,game.canvasHeight+30);
        //TODO ^^ extra parts here can be removed once the random gap is removed... (They compensate)
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
