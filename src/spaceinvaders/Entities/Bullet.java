/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.Entities;

import java.awt.Image;

/**
 *
 * @author Acer Laptop
 */
public class Bullet extends Entity{
    private final int damage;
    private final int direction; // + is down, - is up.
    
    public Bullet(Entity owner, int direction) {
        damage = owner.bulletDmg;
        this.direction = direction;
    }
    
    public Image getImage() {
        //Todo add image gathering stuff...
    }
}
