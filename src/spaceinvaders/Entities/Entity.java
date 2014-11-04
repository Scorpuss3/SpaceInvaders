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
    private float xPosition;
    private float yPosition;
    
    public Entity() {
        xPosition = 0;
        yPosition = 0;
    }
    
    public void setX(float newX) {
        xPosition = newX;
    }
    
    public void setY(float newY) {
        yPosition = newY;
    }
    
    public void move(int newX, int newY) {
        //TODO remove this:
        System.out.print("Moved from: " + Float.toString(xPosition) + "," + Float.toString(yPosition) + ")");
        System.out.println("to:         " + Float.toString(xPosition + newX) + "," + Float.toString(yPosition + newY) + ")");
        
        //NOT THIS:
        xPosition += newX;
        yPosition += newY;
    }
    
    public float getX() {
        return xPosition;
    }
    
    public float getY() {
        return yPosition;
    }
}
