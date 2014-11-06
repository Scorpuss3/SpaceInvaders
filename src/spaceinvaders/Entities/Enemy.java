/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.Entities;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Acer Laptop
 */
public class Enemy extends Entity{
    private static int defaultSpriteWidth = 30;
    private static int defaultSpriteHeight = 15;
    private Image currentSkin;
    private BufferedImage buffImage;
    
    public Enemy () {
        super();
        try {
            //buffImage = ImageIO.read(new File("Enemy_1.jpg"));
            currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Enemy_Level_1.png"));
	}catch(Exception e){
            System.err.println(e);
        }
    }
    
    public Enemy (int level) {
        super();
        try {
            //buffImage = ImageIO.read(new File("Enemy_1.jpg"));
            currentSkin = ImageIO.read(getClass().getResourceAsStream("Skins/Enemy_Level_" + Integer.toString(level) + ".png"));
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
        //return buffImage;
    }
}
