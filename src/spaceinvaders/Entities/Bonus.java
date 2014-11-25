/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders.Entities;

import java.awt.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author nobesj
 */
public class Bonus extends Entity {
    protected enum type {
        HEALTH, SPEED, POWER
    }
    protected type bonusType;
    private Image currentSkin;
    public int lastingTime;
    
    public Bonus() {
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
}
