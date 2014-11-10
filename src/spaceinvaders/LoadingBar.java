/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Acer Laptop
 */
public class LoadingBar extends JPanel{
    private int totalLength = 200;
    private float totalItems = 21;
    private float currentItem = 0;
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.blue);
        g2d.fillRect(0, 0, (int) ((currentItem/totalItems) * totalLength), 50);
    }
    
    public void increment() {
        try {
            //Thread.sleep(200);
            Thread.sleep(0);
        } catch (InterruptedException e) {
            
        }
        currentItem += 1;
        repaint();
    }
}
