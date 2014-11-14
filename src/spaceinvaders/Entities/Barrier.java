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
public class Barrier extends Entity{
    private Image currentSkin;
    
    public Barrier() {
        this.health = 4;
        this.faction = Entity.entityFaction.BLOCK;
        this.spriteHeight = 5;
        this.spriteWidth = 40;
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Barrier_4.png"));
	}catch(Exception e){
            System.err.println(e);
        }
    }
    
    public Image getImage() {
        return currentSkin;
    }
    
    @Override
    public void setHealth(int newHealth) {
        this.health = newHealth;
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Barrier_"+ Integer.toString(newHealth) + ".png"));
	}catch(Exception e){
            System.err.println(e);
        }
    }
}
