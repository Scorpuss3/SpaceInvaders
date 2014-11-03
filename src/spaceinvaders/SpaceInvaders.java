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
    private static int width = 300;
    private static int height = 400;
    private static ArrayList enemies = new ArrayList();
    private static Player player;
    
    public static void initialiseGame() {
        int i;
        for (i = 0; i >=20 ; i++) {
            Enemy newEnemy= new Enemy();
            newEnemy.setX(i%20);
            newEnemy.setY(i/20);
            enemies.add(newEnemy);
        }
        player.setX(width/2); player.setY(height-20);
    }
    
    public static void startGameLoop() {
        while (true) {
            //TODO Add the actual game logic...
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
        frame.setSize(width,height);
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
