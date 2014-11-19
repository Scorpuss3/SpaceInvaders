/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import spaceinvaders.Entities.*;

/**
 *
 * @author Acer Laptop
 */
public class HUD extends JPanel {
    private Player hudPlayer;
    private final SpaceInvaders session;
    
    @Override
        public void paint(Graphics g) {
            super.paint(g);
            SpaceInvaders freezeFrame = session;
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fillRect(0,0,this.getWidth(),this.getHeight());
            g2d.setColor(Color.LIGHT_GRAY);
            //g2d.setFont(new Font("Arial Rounded", Font.BOLD,(int) (14*SpaceInvaders.aspectMultiplier)));
            //g2d.setFont(new Font("Cooper Black", Font.BOLD,(int) (14*SpaceInvaders.aspectMultiplier)));
            //g2d.setFont(new Font("Fixedsys Regular", Font.BOLD,(int) (20*SpaceInvaders.aspectMultiplier)));
            g2d.setFont(new Font("Gill Sans", Font.BOLD,(int) (20*SpaceInvaders.aspectMultiplier)));
            //g2d.drawString("Health: " + Integer.toString(session.player.getHealth()),((float)this.getWidth() / 5)*0.5f,this.getHeight()/2);
            for (int i = 0 ; i < freezeFrame.player.getHealth(); i++) {
                g2d.drawImage(freezeFrame.player.getImage(),50, (int) (this.getHeight()/4 *1.5f*i),null);
            }
            g2d.drawString("Score: " + Integer.toString(session.player.getScore()),(this.getWidth() / 5)*1.5f,this.getHeight()/2);
            g2d.drawString("Level: " + Integer.toString(SpaceInvaders.level),((float)this.getWidth() / 5)*2.5f,this.getHeight()/2);
        }
    
    public HUD(SpaceInvaders parsedSession,int width,int height) {
        session = parsedSession;
        this.setSize(width,height);
    }
}
