/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import spaceinvaders.Entities.Enemy;

/**
 *
 * @author nobesj
 */
public class Game {
    public static void startGameLoop(SpaceInvaders session) throws InterruptedException {
        int xMod = 1;
        int yMod = 0;
        while (true) {
            //TODO Add the actual game logic...
            if (( (Enemy) session.enemies.get(0) ).getX() <= session.borderWidth) {
                //Invaders have reached left side of screen...
                xMod = 1;
                yMod = 5;
                Thread.sleep(480);
            } else if (( (Enemy) session.enemies.get(session.enemies.size()-2) ).getX() >= (session.canvasWidth - session.borderWidth)-2*session.halfEnemyWidth) {
                //Invaders have reached right side of screen...
                // For some reason, it is the second-last listed invader who has the bottom row, far-right coordinate
                xMod = -1;
                yMod = 5;
                Thread.sleep(480);
            } else {
                yMod = 0;
            }
            System.out.println("Enemies moving by vector: (" + Integer.toString(xMod) + "," + Integer.toString(yMod) + ")");
            for (Object object : session.enemies) {
                Enemy selectedEnemy = (Enemy) object;
                selectedEnemy.move(xMod,yMod);
            }
            session.repaint();
            Thread.sleep(20);
        }
    }
    
    public static void runAllGameLoops(SpaceInvaders session) throws InterruptedException {
        startGameLoop(session);
    }
        
}
