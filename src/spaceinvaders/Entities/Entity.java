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
    
    public Entity() {
        xPosition = 0;
        yPosition = 0;
    }
    
    public void setX(int newX) {
        xPosition = newX;
    }
    
    public void setY(int newY) {
        yPosition = newY;
    }
    
    public void move(int newX, int newY) {
        xPosition += newX;
        yPosition += newY;
    }
    
    public int getX() {
        return xPosition;
    }
    
    public int getY() {
        return yPosition;
    }
}
