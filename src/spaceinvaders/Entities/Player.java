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
public class Player extends Entity{
    private final String name;
    private int movementVector = 0; //Int because only vector for x needs to be represented.
    private Image currentSkin;
    private String defaultSkin = "Skins/Player.png";
    
    public enum tempSkin {
        FIRING
    }
    private boolean tempSkinActive = false;
    
    public Player(String name) {
        super();
        this.name = name;
        this.spriteHeight = 10;
        this.spriteWidth = 20;
        this.speed = 3; //Default is 3.
        this.health = 3;
        this.bulletDmg = 1;
        this.faction = Entity.entityFaction.PLAYER;
        
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Player.png"));
	}catch(Exception e){
            System.err.println(e);
        }
    }
    
    public void setDirection(int newDirection) {
        movementVector = newDirection;
    }
    
    public int getDirection() {
        return movementVector;
    }
    
    public Image getImage() {
        return currentSkin;
    }
    
    public void setTempSkin(tempSkin name) {
        tempSkinActive = true;
        if (name == tempSkin.FIRING) {
            try {
                currentSkin = ImageIO.read(getClass().getResourceAsStream("Player_Firing.png"));
            }catch(Exception e){
                System.err.println(e);
            }
        }
    }
    
    public void resetSkin() {
        tempSkinActive = false;
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream(defaultSkin));
        }catch(Exception e){
            System.err.println(e);
        }
    }
    
    public boolean isTempSkinActive() {
        return tempSkinActive;
    }
}
