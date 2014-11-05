/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import spaceinvaders.Entities.Enemy;

/**
 *
 * @author nobesj
 */
public class Game {
    private static SpaceInvaders session;
    private static boolean playing = true;
    private static boolean paused = false;
    
    private static void enemyMovementLoop() throws InterruptedException {
        int xMod = 1;
        int yMod = 0;
        while (!paused) {
            if (( (Enemy) session.enemies.get(0) ).getX() <= session.borderWidth) {
                //Invaders have reached left side of screen...
                xMod = 1;
                yMod = 5;
                Thread.sleep(480);
            } else if (( (Enemy) session.enemies.get(session.enemies.size()-2) ).getX() >= (session.canvasWidth - session.borderWidth)-Enemy.getGenericWidth()) {
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
                if (selectedEnemy.isActive()) {
                    selectedEnemy.move(xMod,yMod);
                }
            }
            session.repaint();
            Thread.sleep(20);
        }
    }
    
    private static void movePlayer() {
        if (!paused) {
            if ( ((session.player.getX() <= session.borderWidth) && session.player.getDirection() == -1) ||
                    ((session.player.getX()+session.player.getWidth() >= session.canvasWidth - 2*session.borderWidth)) &&
                     (session.player.getDirection() == 1)) {
                //Player is at left or right side, and the direction is pointing into the edge.
                session.player.setDirection(0); //Deactivates any movement keypress
            }
            session.player.setX( session.player.getX() + session.player.getDirection()*session.player.getSpeed() );
        }
    }
    
    public static void setUpKeyboardListener() {
        ActionMap actionMap = session.getActionMap();
        InputMap inputMap = session.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        //Action must be a javax.swing.Action object

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Fire");
        actionMap.put("Fire", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("Fire");
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "Left");
        actionMap.put("Left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("Left");
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "Right");
        actionMap.put("Right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("Right");
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Toggle_Pause");
        actionMap.put("Toggle_Pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("Toggle_Pause");
            }
        });
    }
    
    private static void keyAction(String actionString) {
        System.out.println("Got Command: " + actionString);
        switch (actionString) {
            case "Fire" :
                //Do things
                break;
            case "Left" :
                session.player.setDirection(-1);
                movePlayer();
                break;
            case "Right" :
                session.player.setDirection(1);
                movePlayer();
                break;
            case "Toggle_Pause" :
                paused = !paused;
                break;
            default :
                //Do things
                break;
        }
        session.repaint();
    }
    
    public static boolean isPaused() {
        return paused;
    }
    
    public static void runAllGameLoops(SpaceInvaders passedSession) throws InterruptedException {
        session = passedSession;
        while (playing) {
            setUpKeyboardListener();
            enemyMovementLoop();
        }
        System.exit(0);
    }
        
}
