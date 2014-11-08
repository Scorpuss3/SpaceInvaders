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
import spaceinvaders.Entities.*;

/**
 *
 * @author nobesj
 */
public class Game {
    private static SpaceInvaders session;
    private static boolean playing = true;
    private static boolean paused = false;
    
    static class EnemyMovement implements Runnable {
        private Thread et;
        @Override
        public void run() {
            int xMod = 1;
            int yMod = 0;
            while (!paused) {
                int totalPause = 0;
                if (( (Enemy) session.enemies.get(0) ).getX() <= session.borderWidth) {
                    //Invaders have reached left side of screen...
                    xMod = 1;
                    yMod = 5;
                    System.out.println("Enemies moving by vector: (" + Integer.toString(xMod) + "," + Integer.toString(yMod) + ")");
                    totalPause += 20; 
                } else if (( (Enemy) session.enemies.get(session.enemies.size()-2) ).getX() >= (session.canvasWidth - session.borderWidth)-Enemy.getGenericWidth()) {
                    //Invaders have reached right side of screen...
                    // For some reason, it is the second-last listed invader who has the bottom row, far-right coordinate
                    xMod = -1;
                    yMod = 5;
                    System.out.println("Enemies moving by vector: (" + Integer.toString(xMod) + "," + Integer.toString(yMod) + ")");
                    totalPause += 20; 
                } else {
                    yMod = 0;
                }
                for (Object object : session.enemies) {
                    Enemy selectedEnemy = (Enemy) object;
                    if (selectedEnemy.isActive()) {
                        selectedEnemy.move(xMod,yMod);
                    }
                }
                session.repaint();
                totalPause += 20;
                try {
                    Thread.sleep(totalPause);
                } catch (InterruptedException e){
                }
            }
        }
        
        public void start() {
            et = new Thread(this,"enemyMovementThread");
            et.start();
        }
    }
    
    static class PlayerMovement implements Runnable {
        private Thread pt;
        @Override
        public void run() {
            while (!paused) {
                if ( ((session.player.getX() <= session.borderWidth) && session.player.getDirection() == -1) ||
                        ((session.player.getX()+session.player.getWidth() >= session.canvasWidth - 2*session.borderWidth)) &&
                         (session.player.getDirection() == 1)) {
                    //Player is at left or right side, and the direction is pointing into the edge.
                    session.player.setDirection(0); //Deactivates any movement keypress
                }
                session.player.setX( session.player.getX() + session.player.getDirection()*session.player.getSpeed() );
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        }
        
        public void start() {
            pt = new Thread(this, "playerMovementThread");
            pt.start();
        }
    }
    
    static class EnemyFiring implements Runnable {
        private Thread eft;
        @Override
        public void run() {
            while (!paused) {
                for (Object object : session.enemies) {
                    Enemy selectedEnemy = (Enemy) object;
                    double randomDouble = Math.random();
                    if (randomDouble <= selectedEnemy.getProbability()) {
                        session.enemyBullets.add(new Bullet(selectedEnemy,1));
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
        }
        
        public void start() {
            eft = new Thread(this, "enemyFiringThread");
            eft.start();
        }
    }
    
    static class BulletMovement implements Runnable {
        private Thread bt;
        @Override
        public void run() {
            while (!paused) {
                for (Object object : session.enemyBullets) {
                    Bullet selectedBullet = (Bullet) object;
                    selectedBullet.move(0, selectedBullet.getDirection());
                }
                for (Object object : session.playerBullets) {
                    Bullet selectedBullet = (Bullet) object;
                    selectedBullet.move(0, selectedBullet.getDirection());
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        }
        
        public void start() {
            bt = new Thread(this, "bulletMovementThread");
            bt.start();
        }
    }
    
    static class SpriteCollisionDetection implements Runnable {
        private Thread cdt;
        @Override
        public void run() {
            while (!paused) {
                for (Object object : session.playerBullets) {
                    Bullet selectedBullet = (Bullet) object;
                    for (Object eobject : session.enemies) {
                        Enemy selectedEnemy = (Enemy) eobject;
                        if (selectedBullet.intersects(selectedEnemy)) {
                            selectedEnemy.setHealth(selectedEnemy.getHealth()-selectedBullet.getDamage());
                            session.enemyBullets.remove(selectedBullet);
                            selectedBullet.deactivate();
                            selectedBullet = null;
                        }
                    }
                }
                for (Object object : session.enemyBullets) {
                    Bullet selectedBullet = (Bullet) object;
                    if (session.player.intersects(selectedBullet)) {
                        session.player.setHealth(session.player.getHealth()-selectedBullet.getDamage());
                        session.playerBullets.remove(selectedBullet);
                        selectedBullet.deactivate();
                        selectedBullet = null;
                    }
                }
                // No delay, collisions must be detected as early as they happen...
            }
        }
        
        public void start() {
            cdt = new Thread(this, "spriteCollisionDetectionThread");
            cdt.start();
        }
    }
    
    public static void setUpKeyboardListener() {
        ActionMap actionMap = session.getActionMap();
        InputMap inputMap = session.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Fire");
        actionMap.put("Fire", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("Fire");
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("pressed LEFT"), "Left");
        actionMap.put("Left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("Left");
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("pressed RIGHT"), "Right");
        actionMap.put("Right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("Right");
            }
        });
        
        inputMap.put(KeyStroke.getKeyStroke("released LEFT"), "ArrowKeyReleased");
        inputMap.put(KeyStroke.getKeyStroke("released RIGHT"), "ArrowKeyReleased");
        actionMap.put("ArrowKeyReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("ArrowKeyReleased");
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
                session.playerBullets.add(new Bullet(session.player,-1));
                break;
            case "Left" :
                session.player.setDirection(-1);
                break;
            case "Right" :
                session.player.setDirection(1);
                break;
            case "ArrowKeyReleased" :
                session.player.setDirection(0);
                break;
            case "Toggle_Pause" :
                paused = !paused;
                break;
            default :
                break;
        }
        session.repaint();
    }
    
    public static void handleBulletCollision(Bullet bullet, Entity entity) {
        entity.setHealth(entity.getHealth()-bullet.getDamage());
        bullet.deactivate();
    }
    
    public static boolean isPaused() {
        return paused;
    }
    
    public static void runAllGameLoops(SpaceInvaders passedSession) throws InterruptedException {
        session = passedSession;
        setUpKeyboardListener();
        
        PlayerMovement pm = new PlayerMovement();
        pm.start();
        EnemyMovement em = new EnemyMovement();
        em.start();
        EnemyFiring ef = new EnemyFiring();
        ef.start();
        BulletMovement bm = new BulletMovement();
        bm.start();
        SpriteCollisionDetection cd = new SpriteCollisionDetection();
        cd.start();
    }
}
