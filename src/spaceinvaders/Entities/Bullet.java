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
    private final int damage;
    private final int direction; // + is down, - is up.
    private Image currentSkin;
    
    public Bullet(Entity owner, int direction) {
        damage = owner.bulletDmg;
        this.direction = direction;
        
        String skinName;
        if (owner.getClass() == new Enemy().getClass()) { // Might not work...
            skinName = "Skins/Bullet_Enemy.png";
        } else if (owner.getClass() == new Player("").getClass()) {
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
}
