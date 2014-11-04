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

import java.util.ArrayList;
import javax.swing.JFrame;
import spaceinvaders.Entities.*;

public class SpaceInvaders {
    private static int canvasWidth = 300;
    private static int canvasHeight = 400;
    private static int enemyGridWidth = 200;
    private static int enemyGridHeight = 200;
    private static int borderWidth = 5;
    private static ArrayList enemies = new ArrayList();
    private static Player player;
    
    public static void initialiseGame() {
        int i;
        for (i = 0; i >=20 ; i++) {
            Enemy newEnemy= new Enemy();
            newEnemy.setX((i%20)/enemyGridWidth);
            newEnemy.setY((i/20*enemyGridHeight) + borderWidth);
            enemies.add(newEnemy);
        }
        player.setX(canvasWidth/2); player.setY(canvasHeight-20);
    }
    
    public static void startGameLoop() {
        while (true) {
            //TODO Add the actual game logic...
            int xMod = 0;
            int yMod = 0;
            if (( (Enemy) enemies.get(1) ).getX() <= borderWidth) {
                //Invaders have reached left side of screen...
                xMod = 1;
                yMod = 1;
            } else if (( (Enemy) enemies.get(1) ).getX() <= canvasWidth - borderWidth) {
                //Invaders have reached right side of screen...
                xMod = -1;
                yMod = 1;
            }
            for (Object object : enemies) {
                Enemy selectedEnemy = (Enemy) object;
                selectedEnemy.move(xMod,yMod);
            }
        }
    }
    
    public static void getUserDetails() {
        //TODO add name collection
        player = new Player("Player 1");
    }
    
    public static void createUI() {
        JFrame frame = new JFrame("Space Invaders");
        GameFrame game = new GameFrame();
        frame.add(game);
        frame.setSize(canvasWidth,canvasHeight);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        createUI();
        getUserDetails();
        initialiseGame();
        startGameLoop();
    }
}
