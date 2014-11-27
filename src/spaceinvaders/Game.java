/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import spaceinvaders.Entities.*;
import spaceinvaders.Sounds.Sound;

/**
 *
 * @author nobesj
 */
public class Game {
    private static SpaceInvaders session;
    private static boolean playing = true;
    private static boolean paused = false;
    private static final int fps = 30;
    private static final int pausedFps = 1000;
    
    static class EnemyMovement implements Runnable {
        private Thread et;
        @Override
        public void run() {
            while (playing)  {
                int xMod = 1;
                int yMod = 0;
                while (!paused) {
                    int totalPause = 0;
                    if (( (Enemy) session.enemies.get(0) ).getX() <= session.borderWidth) {
                        //Invaders have reached left side of screen...
                        xMod = 1;
                        yMod = 10;
                        System.out.println("Enemies moving by vector: (" + Integer.toString(xMod) + "," + Integer.toString(yMod) + ")");
                        totalPause += 20; 
                    } else if (( (Enemy) session.enemies.get(session.enemies.size()-1) ).getX() >= (session.canvasWidth - session.borderWidth)-Enemy.getGenericWidth()) {
                        //Invaders have reached right side of screen...
                        // For some reason, it is the second-last listed invader who has the bottom row, far-right coordinate
                        xMod = -1;
                        yMod = 10;
                        System.out.println("Enemies moving by vector: (" + Integer.toString(xMod) + "," + Integer.toString(yMod) + ")");
                        totalPause += 20; 
                    } else {
                        yMod = 0;
                    }
                    for (Map.Entry<Integer, Enemy> e : session.enemies.entrySet()) {
                        Enemy selectedEnemy = (Enemy) e.getValue();
                        selectedEnemy.move(xMod,yMod);
                    }
                    //session.repaint();
                    totalPause += 20;
                    try {
                        Thread.sleep(totalPause);
                    } catch (InterruptedException e){
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
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
            while (playing) {
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
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
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
            while (playing) {
                while (!paused) {
                    for (Map.Entry<Integer, Enemy> e : session.enemies.entrySet()) {
                        Enemy selectedEnemy = (Enemy) e.getValue();
                        double randomDouble = Math.random();
                        if (randomDouble <= selectedEnemy.getProbability() && selectedEnemy.isActive()) {
                            selectedEnemy.setTempSkin(Enemy.tempSkin.FIRING);
                            Sound.playSound(selectedEnemy,Sound.soundType.SHOOT);
                            session.enemyBullets.put(session.enemyBullets.size(),new Bullet(selectedEnemy,1));
                        }
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
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
            while (playing) {
                while (!paused) {
                    for (Map.Entry<Integer, Bullet> e : session.enemyBullets.entrySet()) {
                        Bullet selectedBullet = (Bullet) e.getValue();
                        selectedBullet.move(0, selectedBullet.getDirection()*selectedBullet.getSpeed());
                    }
                    for (Map.Entry<Integer, Bullet> e : session.playerBullets.entrySet()) {
                        Bullet selectedBullet = (Bullet) e.getValue();
                        selectedBullet.move(0, selectedBullet.getDirection()*selectedBullet.getSpeed());
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        public void start() {
            bt = new Thread(this, "bulletMovementThread");
            bt.start();
        }
    }
    
    static class BonusEnemyHandler implements Runnable {
        private Thread bet;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    if (session.bonusEnemy.isActive()) {
                        if (session.bonusEnemy.getX() >= session.canvasWidth) {
                            session.bonusEnemy.deactivate();
                        }
                        for (Map.Entry<Integer, Bullet> e : session.playerBullets.entrySet()) {
                            Bullet selectedBullet = (Bullet) e.getValue();
                            if (session.bonusEnemy.intersects(selectedBullet) && selectedBullet.isActive()) {
                                session.bonusEnemy.deactivate();
                                Bonus newBonus = new Bonus();
                                newBonus.setX(session.bonusEnemy.getX()+(session.bonusEnemy.getWidth()/2));
                                newBonus.setY(session.bonusEnemy.getY()+session.bonusEnemy.getWidth());
                                newBonus.activate();
                                session.bonuses.put(session.bonuses.size(), newBonus);
                            }
                        }
                        session.bonusEnemy.move(1,0);
                        
                    } else {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e){
                        }
                        if (Math.random() <= 0.05) {//TODO change this back to 0.01
                            session.bonusEnemy.setX(-(session.bonusEnemy.getWidth()));
                            session.bonusEnemy.setY(10);
                            session.bonusEnemy.activate();
                            System.out.println("Created a bonus enemy.");
                        }
                    }
                    
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e){
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        public void start() {
            bet = new Thread(this, "bonusEnemyHandlingThread");
            bet.start();
        }
    }
    
    static class BonusSelectionHandler implements Runnable {
        private Thread bst;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    for (Map.Entry<Integer, Bonus> e : session.bonuses.entrySet()) {
                        Bonus selectedBonus = (Bonus) e.getValue();
                        if (session.player.intersects(selectedBonus) && selectedBonus.isActive()) {
                            try {
                                session.player.currentBonus.deactivateEffect(session);
                            } catch (Exception ee) {
                            } //If this is first bonus used...
                            System.out.println("Player caught bonus: ");
                            selectedBonus.deactivate();
                            session.player.currentBonus = selectedBonus;
                            session.player.currentBonus.activateEffect(session);
                        }
                    }
                    
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e){
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        public void start() {
            bst = new Thread(this, "bonusSelectionHandlingThread");
            bst.start();
        }
    }
    
    static class BonusMovement implements Runnable {
        private Thread bmt;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    for (Map.Entry<Integer, Bonus> e : session.bonuses.entrySet()) {
                        Bonus selectedBonus = (Bonus) e.getValue();
                        selectedBonus.move(0, 1);
                    }
                    
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e){
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        public void start() {
            bmt = new Thread(this, "bonusMovementHandlingThread");
            bmt.start();
        }
    }
    
    static class SpriteCollisionDetection implements Runnable {
        private Thread cdt;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    for (Map.Entry<Integer, Bullet> e : session.playerBullets.entrySet()) { 
                        Bullet selectedBullet = (Bullet) e.getValue();
                        for (Map.Entry<Integer, Enemy> ee : session.enemies.entrySet()) {
                            Enemy selectedEnemy = (Enemy) ee.getValue();
                            if (selectedBullet.intersects(selectedEnemy) && selectedBullet.isActive() && selectedEnemy.isActive()) {
                                selectedEnemy.setHealth(selectedEnemy.getHealth()-selectedBullet.getDamage());
                                //Sound.playSound(selectedEnemy, Sound.soundType.HIT);
                                selectedBullet.deactivate();
                                session.player.setScore(session.player.getScore() + 1);
                            }
                        }
                        if (selectedBullet.getY() <= session.borderWidth) {
                            // Collision with bottom border...
                            selectedBullet.deactivate();
                        }
                        for (Map.Entry<Integer, Barrier> ee : session.barriers.entrySet()) {
                            Barrier selectedBarrier = (Barrier) ee.getValue();
                            if (selectedBullet.intersects(selectedBarrier) && selectedBullet.isActive() && selectedBarrier.isActive()) {
                                //selectedBarrier.setHealth(selectedBarrier.getHealth()-selectedBullet.getDamage());
                                selectedBarrier.setHealth(selectedBarrier.getHealth()-1);// All bullets do same damage to barrier.
                                selectedBullet.deactivate();
                            }
                        }
                    }
                    for (Map.Entry<Integer, Bullet> e : session.enemyBullets.entrySet()) {
                        Bullet selectedBullet = (Bullet) e.getValue();
                        if (session.player.intersects(selectedBullet) && selectedBullet.isActive()) {
                            // Collision with Player...
                            System.out.println("Collision Has Been Detected");
                            session.player.setHealth(session.player.getHealth()-selectedBullet.getDamage());
                            selectedBullet.deactivate();
                            session.player.setScore(session.player.getScore() - 3);
                        }
                        if (selectedBullet.getY() >= session.canvasHeightGame - session.borderWidth) {
                            // Collision with bottom border...
                            selectedBullet.deactivate();
                        }
                        for (Map.Entry<Integer, Barrier> ee : session.barriers.entrySet()) {
                            Barrier selectedBarrier = (Barrier) ee.getValue();
                            if (selectedBullet.intersects(selectedBarrier) && selectedBullet.isActive() && selectedBarrier.isActive()) {
                                //selectedBarrier.setHealth(selectedBarrier.getHealth()-selectedBullet.getDamage());
                                selectedBarrier.setHealth(selectedBarrier.getHealth()-1);// All bullets do same damage to barrier.
                                selectedBullet.deactivate();
                            }
                        }
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                    }
                    // Added delay, may not be needed...
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        public void start() {
            cdt = new Thread(this, "spriteCollisionDetectionThread");
            cdt.start();
        }
    }
    
    static class GameEndHandler implements Runnable {
        private Thread geht;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    if (! session.player.isActive()) {
                        end("Player Died.");
                    }
                    int enemiesAlive = 0;
                    for (Map.Entry<Integer, Enemy> e : session.enemies.entrySet()) {
                        Enemy selectedEnemy = (Enemy) e.getValue();
                        if (selectedEnemy.isActive()) {
                            enemiesAlive += 1;
                        }
                    }
                    if (enemiesAlive <= 0) {
                        //Level won...
                        playing = false;
                        paused = true;
                        System.out.println("Added 1 to level...");
                        SpaceInvaders.level += 1;
                    }
                    int lowestY = 0;
                    for (Map.Entry<Integer, Enemy> e : session.enemies.entrySet()) {
                        Enemy selectedEnemy = (Enemy) e.getValue();
                        if ((selectedEnemy.getY() > lowestY && selectedEnemy.isActive())) {
                            lowestY = selectedEnemy.getY();
                        }
                    }
                    if (lowestY >= session.lineOfDefense - new Enemy().getHeight()) {
                        end("Enemies reached bottom.");
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        private void end(String reason) {
            paused = true;
            playing = false;
            JOptionPane.showMessageDialog(session,"YOU LOSE: " + reason);
            //JOptionPane.showMessageDialog(session,"YOU LOSE");
            System.exit(0);
        }
        
        public void start() {
            geht = new Thread(this, "gameEndHandlingThread");
            geht.start();
        }
    }
    
    static class TempSkinManager implements Runnable {
        private Thread tst;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    for (Map.Entry<Integer, Enemy> e : session.enemies.entrySet()) {
                        Enemy selectedEnemy = (Enemy) e.getValue();
                        if (selectedEnemy.isTempSkinActive()) {
                            System.out.println("Found temp enemy costume...");
                            resetSkin(selectedEnemy);
                        }
                    }
                    if (session.player.isTempSkinActive()) {
                        resetSkin(session.player);
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        private void resetSkin(Entity entity) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            if (entity.getFaction() == Entity.entityFaction.PLAYER) {
                Player selectedPlayer = (Player) entity;
                selectedPlayer.resetSkin();
            } else {
                Enemy selectedEnemy = (Enemy) entity;
                selectedEnemy.resetSkin();
            }
        }
        
        public void start() {
            tst = new Thread(this, "TempSkinManagingThread");
            tst.start();
        }
    }
    
    static class PlayerFiring implements Runnable {
        private Thread pft;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    while (session.player.isFiring()) {
                        session.player.setTempSkin(Player.tempSkin.FIRING);
                        Sound.playSound(session.player,Sound.soundType.SHOOT);
                        session.playerBullets.put(session.playerBullets.size(),new Bullet(session.player,-1));
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                        }
                    }
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
            }
        }
        
        public void start() {
            pft = new Thread(this, "PlayerFiringThread");
            pft.start();
        }
    }
    
    static class CanvasRefresher implements Runnable {
        private Thread crt;
        @Override
        public void run() {
            while (playing) {
                while (!paused) {
                    session.repaint();
                    try {
                        Thread.sleep(1000/fps);
                    } catch (InterruptedException e) {
                    }
                }
                try {
                    Thread.sleep(1000/pausedFps);
                } catch (InterruptedException e){
                }
                session.repaint();
            }
        }
        
        public void start() {
            crt = new Thread(this, "CanvasRefresherThread");
            crt.start();
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
        
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "SpaceReleased");
        actionMap.put("SpaceReleased", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("SpaceReleased");
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
        
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0), "KillAll");
        actionMap.put("KillAll", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                keyAction("KillAll");
            }
        });
    }
    
    private static void keyAction(String actionString) {
        //System.out.println("Got Command: " + actionString);
//        switch (actionString) {
//            case "Fire" :
//                session.player.setFiring(true);
//                break;
//            case "SpaceReleased" :
//                session.player.setFiring(false);
//                break;
//            case "Left" :
//                session.player.setDirection(-1);
//                break;
//            case "Right" :
//                session.player.setDirection(1);
//                break;
//            case "ArrowKeyReleased" :
//                session.player.setDirection(0);
//                break;
//            case "Toggle_Pause" :
//                paused = !paused;
//                break;
//            case "KillAll" :
//                for (Map.Entry<Integer, Enemy> e : session.enemies.entrySet()) {
//                    Enemy selected = (Enemy) e.getValue();
//                    selected.deactivate();
//                }
//                break;
//            default :
//                break;
//        }
        if (actionString.equals("Fire")) {
            session.player.setFiring(true);
        } else if (actionString.equals("SpaceReleased")) {
            session.player.setFiring(false);
        } else if (actionString.equals("Left")) {
            session.player.setDirection(-1);
        } else if (actionString.equals("Right")) {
            session.player.setDirection(1);
        } else if (actionString.equals("ArrowKeyReleased")) {
            session.player.setDirection(0);
        } else if (actionString.equals("Toggle_Pause")) {
            paused = !paused;
        } else if (actionString.equals("KillAll")) {
            for (Map.Entry<Integer, Enemy> e : session.enemies.entrySet()) {
                    Enemy selected = (Enemy) e.getValue();
                    selected.deactivate();
                }
        }
        //session.repaint();
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
        playing = true;
        paused = false;
        setUpKeyboardListener();
        
        Sound.startMusic();
        
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
        GameEndHandler geh = new GameEndHandler();
        geh.start();
        TempSkinManager tsm = new TempSkinManager();
        tsm.start();
        PlayerFiring pf = new PlayerFiring();
        pf.start();
        CanvasRefresher cr = new CanvasRefresher();
        cr.start();
        BonusEnemyHandler beh = new BonusEnemyHandler();
        beh.start();
        BonusSelectionHandler bsh = new BonusSelectionHandler();
        bsh.start();
        BonusMovement bom = new BonusMovement();
        bom.start();
        //http://www.java-gaming.org/index.php?topic=24220.0
    }
}
