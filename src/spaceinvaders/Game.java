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
    
    private static void startGameLoop() throws InterruptedException {
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
                //Do things
                break;
            case "Right" :
                //Do things
                break;
            case "Toggle_Pause" :
                //Do things
                break;
            default :
                //Do things
                break;
        }
    }
    
    public static void runAllGameLoops(SpaceInvaders passedSession) throws InterruptedException {
        session = passedSession;
        setUpKeyboardListener();
        //startGameLoop();
    }
        
}
