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
public class Enemy extends Entity{
    private static int defaultSpriteWidth = 30;
    private static int defaultSpriteHeight = 15;
    private Image currentSkin;
    private double shotProbability; // Out of 1 (1 means certain shot).
    private String defaultSkin;
    private int diffLevel;
    private int bulletSpeed;
    private String skinName;
    
    public enum tempSkin {
        FIRING
    }
    private boolean tempSkinActive = false;
    
    public Enemy () {
        this(1);
    }
    
    public Enemy (int level) {
        super();
        this.diffLevel = level;
        this.spriteWidth = defaultSpriteWidth;
        this.spriteHeight = defaultSpriteHeight;
        this.faction = Entity.entityFaction.ENEMY;
        switch (level) {
            case 1:
                defaultSkin = "Skins/Enemy/Enemy_Level_1.png";
                this.health = 1;
                this.bulletDmg = 1;
                this.bulletSpeed = 2;
                this.shotProbability = 0.01;
                this.skinName = "Skins/Bullet/Bullet_Enemy.png";
                break;
            case 2:
                defaultSkin = "Skins/Enemy/Enemy_Level_2.png";
                this.health = 2;
                this.bulletDmg = 1;
                this.bulletSpeed = 2;
                this.shotProbability = 0.01;
                this.skinName = "Skins/Bullet/Bullet_Enemy.png";
                break;
            case 3:
                defaultSkin = "Skins/Enemy/Enemy_Level_3.png";
                this.health = 4;
                this.bulletDmg = 1;
                this.bulletSpeed = 3;
                this.shotProbability = 0.02;
                this.skinName = "Skins/Bullet/Bullet_Enemy_Special.png";
                break;
            case 4:
                defaultSkin = "Skins/Enemy/Enemy_Bonus.png";
                this.health = 1;
                this.bulletDmg = 0;
                this.bulletSpeed = 0;
                this.shotProbability = 0;
                this.skinName =  "Skins/Bullet/Bullet_Enemy.png";
                this.spriteWidth = 30;
                this.spriteHeight = 10;
                System.out.println("CREATED IT RIGHT...");
                break;
            default:
                defaultSkin = "Skins/Enemy/Enemy_Level_1.png";
                this.health = 2;
                this.bulletDmg = 1;
                this.bulletSpeed = 2;
                this.shotProbability = 0.01;
                break;
        }
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream(defaultSkin));
	}catch(Exception e){
            System.err.println(e);
        }
    }
    
    public static int getGenericWidth() {
        return defaultSpriteWidth;
    }
    
    public static int getGenericHeight() {
        return defaultSpriteHeight;
    }
    
    public Image getImage() {
        return currentSkin;
    }
    
    public double getProbability() {
        return shotProbability;
    }
    
    public int getBulletDamage() {
        return bulletDmg;
    }
    
    public String getSkinName() {
        return skinName;
    }
    
    public void setTempSkin(tempSkin name) {
        tempSkinActive = true;
        if (name == tempSkin.FIRING) {
            try {
                currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Enemy/Enemy_Level_" + Integer.toString(diffLevel) + "_Firing.png"));
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
    
    public int getBulletSpeed() {
        return bulletSpeed;
    }
}
