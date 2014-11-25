/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders.Entities;

import java.awt.Image;
import javax.imageio.ImageIO;
import spaceinvaders.SpaceInvaders;

/**
 *
 * @author nobesj
 */
public class Bonus extends Entity {
    protected enum type {
        HEALTH, SPEED, POWER
    }
    public type bonusType;
    private Image currentSkin;
    public int lastingTime;
    
    public Bonus() {
        this.spriteWidth=20;
        this.spriteHeight=10;
        double generated = Math.random();
        String skinName = null;
        if (generated <= 20) {
            bonusType = type.SPEED;
            lastingTime = 600;
            skinName = "Skins/Bonus/Speed.png";
        } else if (generated <= 40) {
            bonusType = type.POWER;
            lastingTime = 600;
            skinName = "Skins/Bonus/Power.png";
        } else {
            bonusType = type.HEALTH;
            lastingTime = 600;
            skinName = "Skins/Bonus/Health.png";
        }
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream(skinName));
	}catch(Exception e){
            System.err.println(e);
        }
    }
    
    public Image getImage() {
        return currentSkin;
    }
    
    public void activateEffect(SpaceInvaders session) {
        if (this.bonusType == type.HEALTH) {
            session.player.setHealth(session.player.getHealth()+1);
        } else if (this.bonusType == type.POWER) {
            session.player.bulletDmg = session.player.bulletDmg * 2;
        } else if (this.bonusType == type.SPEED) {
            session.player.speed = session.player.speed * 2;
        }
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
        }
        deactivateEffect(session);
    }
    
    public void deactivateEffect(SpaceInvaders session) {
        if (this.bonusType == type.POWER) {
            //session.player.bulletDmg = session.player.bulletDmg / 2;
            session.player.bulletDmg = 1;
        } else if (this.bonusType == type.SPEED) {
            //session.player.speed = session.player.speed / 2;
            session.player.speed = 3;
        }
        // Health is a permenant bonus, no need to be removed...
    }
}
