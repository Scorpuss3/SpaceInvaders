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
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author Acer Laptop
 */
public class HowToPlay {
    private static class HowToPlayPanel extends JPanel {
        private JFrame myFrame;
        private final int height, width;
        private int border = 50;
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setColor(Color.red);
            g2d.drawRect(border,border,width-2*border,height-2*border);
            g2d.setFont(new Font("Gill Sans", Font.BOLD ,30));
            g2d.drawString("How To Play:", border * 2, border * 2);
            try {
                g2d.drawImage(ImageIO.read(MainMenu.class.getResourceAsStream("howToPlay.png")),2*border,50 + 2*border,width-4*border,height-(150 + 2* border), this);
                        } catch (IOException ex) {
                Logger.getLogger(HowToPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            g2d.drawString("Any Key to go back",width-400,height-60);
        }
        
        public void setUpKeyboardListener() {
            ActionMap actionMap = this.getActionMap();
            InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

            inputMap.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "EXIT");
            inputMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "EXIT");
            inputMap.put(KeyStroke.getKeyStroke("pressed SPACE"), "EXIT");
            actionMap.put("EXIT", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    keyAction("EXIT");
                }
            });
        }
        
        private void keyAction(String actionString) {
            System.out.println("Got Command: " + actionString);
            if (actionString.equals("EXIT")) {
                myFrame.setVisible(false);
            }
        }
        
        public HowToPlayPanel(int parsedWidth, int parsedHeight, JFrame parsedFrame) {
            height = parsedHeight; width = parsedWidth;
            setUpKeyboardListener();
            myFrame = parsedFrame;
            repaint();
        }
    }
    
    public static void start(int gotWidth, int gotHeight) {
        JFrame tutFrame = new JFrame();
        tutFrame.setUndecorated(true);
        tutFrame.setSize(gotWidth, gotHeight);
        tutFrame.add(new HowToPlayPanel(gotWidth,gotHeight, tutFrame));
        tutFrame.setVisible(true);
    }
}
