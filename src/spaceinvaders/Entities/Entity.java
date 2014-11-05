/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.Entities;

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
        System.out.print("Moved from: " + Integer.toString(xPosition) + "," + Integer.toString(yPosition) + ")");
        System.out.println("to:         " + Integer.toString(xPosition + newX) + "," + Integer.toString(yPosition + newY) + ")");
        
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
}
