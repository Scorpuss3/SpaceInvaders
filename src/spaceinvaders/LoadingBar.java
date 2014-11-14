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
    public int totalLength = 400;//200
    public int totalHeight = 50;
    private float totalItems = 36;
    private float currentItem = 0;
    private String displayTextRoot = "";
    private String displayTextBranch = "";
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.DARK_GRAY);
        g2d.setColor(Color.blue);
        g2d.fillRect(0, 0, (int) ((currentItem/totalItems) * totalLength), totalHeight);
        g2d.setColor(Color.black);
        g2d.drawString(displayTextRoot + ": " + displayTextBranch,20,25);
        if (currentItem == totalItems) {
            System.out.println("Loading done.");
        } else if (currentItem > totalItems) {
            System.out.println("Error, too many items: " + Float.toString(currentItem));
        }
    }
    
    public void increment() {
        try {
            //Thread.sleep(200);
            Thread.sleep(0);
        } catch (InterruptedException e) {
            
        }
        currentItem += 1;
        displayTextBranch = "";
        repaint();
    }
    
    public void increment(String inputText) {
        try {
            //Thread.sleep(200);
            Thread.sleep(0);
        } catch (InterruptedException e) {
            
        }
        currentItem += 1;
        displayTextBranch = inputText;
        repaint();
    }
    
    public LoadingBar() {
    }
    
    public LoadingBar(String text) {
        displayTextRoot = text;
    }
}
