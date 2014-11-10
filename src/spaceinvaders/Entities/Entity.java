/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.Entities;

import java.awt.Rectangle;

/**
 *
 * @author Acer Laptop
 */
public class Entity {
    private int xPosition;
    private int yPosition;
    protected int spriteWidth;
    protected int spriteHeight;
    private boolean isActive;
    protected int speed;
    protected int health;
    protected int bulletDmg;
    protected entityFaction faction;
    
    public enum entityFaction {
        PLAYER, ENEMY
    }
    
    public Entity() {
        xPosition = 0;
        yPosition = 0;
        isActive = true;
    }
    
    public void setX(int newX) {
        xPosition = newX;
    }
    
    public void setY(int newY) {
        yPosition = newY;
    }
    
    public void move(int newX, int newY) {
        //TODO remove this:
        //System.out.print("Moved from: " + Integer.toString(xPosition) + "," + Integer.toString(yPosition) + ")");
        //System.out.println("to:         " + Integer.toString(xPosition + newX) + "," + Integer.toString(yPosition + newY) + ")");
        
        //NOT THIS:
        xPosition += newX;
        yPosition += newY;
    }
    
    public int getX() {
        return xPosition;
    }
    
    public int getY() {
        return yPosition;
    }
    
    public void activate() {
        isActive = true;
    }
    
    public void deactivate() {
        isActive = false;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public int getWidth() {
        return spriteWidth;
    }
    
    public int getHeight() {
        return spriteHeight;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int newHealth) {
        health = newHealth;
        if (this.faction == entityFaction.PLAYER) {
            System.out.println("Player health now " + Integer.toString(newHealth));
        } else {
            System.out.println("Enemy health now " + Integer.toString(newHealth));
        }
        if (health == 0) {
            deactivate();
        }
    }
    
    public entityFaction getFaction() {
        return faction;
    }
    
    public boolean intersects(Entity otherEntity) {
        Rectangle local = new Rectangle(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        Rectangle other = new Rectangle(otherEntity.getX(),otherEntity.getY(),otherEntity.getWidth(),otherEntity.getHeight());
        return local.getBounds().intersects(other.getBounds());
    }
}
