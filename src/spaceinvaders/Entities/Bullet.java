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
        this.spriteWidth = 2;
        this.spriteHeight = 2;
        this.owner = passedOwner;
        damage = owner.bulletDmg;
        this.direction = direction;
        this.setX(owner.getX()+ owner.getWidth()/2 -1);
        this.setY(owner.getY());
        
        String skinName;
        if (owner.getFaction() == Entity.entityFaction.ENEMY) { // Might not work...
            Enemy enemyOwner = (Enemy) owner;
            this.speed = enemyOwner.getBulletSpeed();
            //skinName = "Skins/Bullet_Enemy.png";
            skinName = enemyOwner.getSkinName();
            this.setY(owner.getY() + owner.getHeight());
        } else if (owner.getFaction() == Entity.entityFaction.PLAYER) {
            Player playerOwner = (Player) owner;
            this.speed = playerOwner.getBulletSpeed();
            skinName = "Skins/Bullet/Bullet_Player.png";
        } else {
            skinName = "Skins/Bullet/Player.png";
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
    
    public int getDamage() {
        return damage;
    }
}
