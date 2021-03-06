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
        this.spriteHeight = 10;
        this.spriteWidth = 40;
        this.activate();
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Barrier/Barrier_4.png"));
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
        if (this.health <=0) {
            this.deactivate();
        } else {
            try {
                currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Barrier/Barrier_"+ Integer.toString(newHealth) + ".png"));
            }catch(Exception e){
                System.err.println(e);
            }
        }
    }
}
