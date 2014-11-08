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
 * @author Acer Laptop
 */
public class Bullet extends Entity{
    private Entity owner;
    private final int damage;
    private final int direction; // + is down, - is up.
    private Image currentSkin;
    
    public Bullet(Entity passedOwner, int direction) {
        this.owner = passedOwner;
        damage = owner.bulletDmg;
        this.direction = direction;
        this.setX(owner.getX());
        this.setX(owner.getY());
        
        String skinName;
        if (owner.getFaction() == Entity.entityFaction.ENEMY) { // Might not work...
            skinName = "Skins/Bullet_Enemy.png";
        } else if (owner.getFaction() == Entity.entityFaction.PLAYER) {
            skinName = "Skins/Bullet_Player.png";
        } else {
            skinName = "Skins/Player.png";
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
    
    public int getDirection() {
        return direction;
    }
}
