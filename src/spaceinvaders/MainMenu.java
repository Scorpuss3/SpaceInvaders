/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author nobesj
 */
public class MainMenu {
    private static JFrame menuFrame;
    
    private static class Menu extends JPanel{
        private final Option startOption, exitOption, highScoresOption;
        private final Option[] options;
        
        private class Option {
            protected String caption = "";
            protected int size;
            protected boolean selected = false;
            
            public Option(String caption) {
                this.caption = caption;
                this.size = 20;
            }
            
            public void select() {
                selected = true;
                this.size = 30;
            }
            
            public void deselect() {
                selected = false;
                this.size = 20;
            }
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setFont(new Font("Gill Sans", Font.BOLD,startOption.size));
            g2d.drawString(startOption.caption,200,100);
            
            g2d.setFont(new Font("Gill Sans", Font.BOLD,exitOption.size));
            g2d.drawString(exitOption.caption,200,200);
            
            g2d.setFont(new Font("Gill Sans", Font.BOLD,highScoresOption.size));
            g2d.drawString(highScoresOption.caption,200,300);
        }
        
        public void setUpKeyboardListener() {
            ActionMap actionMap = this.getActionMap();
            InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

            inputMap.put(KeyStroke.getKeyStroke("pressed UP"), "UP");
            actionMap.put("UP", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    keyAction("UP");
                }
            });
            
            inputMap.put(KeyStroke.getKeyStroke("pressed DOWN"), "DOWN");
            actionMap.put("DOWN", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    keyAction("DOWN");
                }
            });
            
            inputMap.put(KeyStroke.getKeyStroke("pressed SPACE"), "CONFIRM");
            inputMap.put(KeyStroke.getKeyStroke("pressed ENTER"), "CONFIRM");
            actionMap.put("CONFIRM", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    keyAction("CONFIRM");
                }
            });
        }
        
        private int getSelectedIndex() {
            for (Option option : options) {
                if (option.selected) {
                    return Arrays.asList(options).indexOf(option);
                }
            }
            return -1;
        }
        
        private void keyAction(String actionString) {
        System.out.println("Got Command: " + actionString);
        switch (actionString) {
            case "UP" :
                int ui = getSelectedIndex();
                (options[ui]).deselect();
                try {
                    (options[ui-1]).select();
                } catch (Exception e) {
                    (options[options.length -1]).select();
                }
                break;
            case "DOWN" :
                int di = getSelectedIndex();
                (options[di]).deselect();
                try {
                    (options[di+1]).select();
                } catch (Exception e) {
                    (options[0]).select();
                }
                break;
            case "CONFIRM" :
                if (startOption.selected) {
                    SpaceInvaders.level = 1;
                    menuFrame.setVisible(false);
                } else  if (exitOption.selected) {
                    System.exit(0);
                } else if (highScoresOption.selected) {
                    // Do something...
                }
                break;
            default :
                break;
        }
        repaint();
    }
        
        public Menu() {
            options = new Option[3];
            startOption = new Option("Start"); options[0] = startOption;
            exitOption = new Option("Exit"); options[1] = exitOption;
            highScoresOption = new Option("HighScores"); options[2] = highScoresOption;
            
            startOption.select();
            setUpKeyboardListener();
        }
    }
    
    public static void start(int width, int height) {
        menuFrame = new JFrame();
        menuFrame.setUndecorated(true);
        menuFrame.setSize(width, height);
        menuFrame.add(new Menu());
        menuFrame.setVisible(true);
    }
}
