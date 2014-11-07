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
    
    public Enemy () {
        super();
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Enemy_Level_1.png"));
	}catch(Exception e){
            System.err.println(e);
        }
        this.health = 2;
    }
    
    public Enemy (int level) {
        super();
        String skinName;
        switch (level) {
            case 1:
                skinName = "Skins/Enemy_Level_1.png";
                this.health = 2;
                this.bulletDmg = 1;
                break;
            case 2:
                skinName = "Skins/Enemy_Level_2.png";
                this.health = 2;
                this.bulletDmg = 1;
                break;
            case 3:
                skinName = "Skins/Enemy_Level_3.png";
                this.health = 4;
                this.bulletDmg = 1;
                break;
            default:
                skinName = "Skins/Enemy_Level_1.png";
                this.health = 2;
                this.bulletDmg = 1;
                break;
        }
        try {
            currentSkin = ImageIO.read(getClass().getResourceAsStream(skinName));
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
}
