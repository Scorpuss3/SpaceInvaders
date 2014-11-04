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
    private final int canvasWidth = 300;
    private final int canvasHeight = 400;
    private final int enemyGridWidth = 200;
    private final int enemyGridHeight = 200;
    private final int borderWidth = 5;
    
    private static SpaceInvaders game;
    private ArrayList enemies = new ArrayList();
    private Player player;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Object object : enemies) {
            Enemy selectedEnemy = (Enemy) object;
            g2d.fillOval(selectedEnemy.getX(), selectedEnemy.getY(), 10, 5); //Last two numbers are width, height
        }
    }
    
    public void initialiseGame() {
        float i;
        System.out.println("Got HERE");
        for (i = 0; i <=20 ; i++) {
            System.out.println("And here");
            Enemy newEnemy= new Enemy();
            newEnemy.setX((int) ((i%5)/4)*enemyGridWidth);
            //System.out.println((i%5)/4);
            System.out.println(((i%5)/4)*enemyGridWidth);
            System.out.println(Math.round((i%5)/4)*enemyGridWidth);
            newEnemy.setY((int) (i/20*enemyGridHeight) + borderWidth);
            enemies.add(newEnemy);
            System.out.print("New enemy added:" + Float.toString(i));
            System.out.println("At Coordinates: (" + Integer.toString(newEnemy.getX()) + "," + Integer.toString(newEnemy.getY()) + ")");
        }
        player.setX(canvasWidth/2); player.setY(canvasHeight - borderWidth);
    }
    
    public void startGameLoop() throws InterruptedException {
        int xMod = 0;
        int yMod = 0;
        while (false) {
            //TODO Add the actual game logic...
            if (( (Enemy) enemies.get(1) ).getX() <= borderWidth) {
                //Invaders have reached left side of screen...
                xMod = 1;
                //yMod = 1;
            } else if (( (Enemy) enemies.get(1) ).getX() >= canvasWidth - borderWidth) {
                //Invaders have reached right side of screen...
                xMod = -1;
                //yMod = 1;
            }
            System.out.println("Enemies moving by vector: (" + Integer.toString(xMod) + "," + Integer.toString(yMod) + ")");
            for (Object object : enemies) {
                Enemy selectedEnemy = (Enemy) object;
                selectedEnemy.move(xMod,yMod);
            }
            game.repaint();
            Thread.sleep(10);
        }
    }
    
    public void getUserDetails() {
        //TODO add name collection
        player = new Player("Player 1");
    }
    
    public static void createUI() {
        JFrame frame = new JFrame("Space Invaders");
        game = new SpaceInvaders();
        frame.add(game);
        frame.setSize(game.canvasWidth,game.canvasHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) throws InterruptedException {
        createUI();
        game.getUserDetails();
        game.initialiseGame();
        game.startGameLoop();
    }
}
