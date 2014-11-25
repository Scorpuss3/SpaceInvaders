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
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import spaceinvaders.Entities.Enemy;
import spaceinvaders.HighScores.HighScores;

/**
 *
 * @author nobesj
 */
public class MainMenu {
    private static JFrame menuFrame;
    private static int width, height;
    
    private static class Menu extends JPanel{
        private final Option startOption, exitOption, highScoresOption;
        private final Option[] options;
        private boolean noScores = false;
        
        private class Option {
            protected String caption = "";
            protected int size;
            protected boolean selected = false;
            protected int fontType;
            
            
            public Option(String caption) {
                this.caption = caption;
                this.size = 20;
                this.fontType = Font.BOLD;
            }
            
            public void select() {
                selected = true;
                this.size = 40;
                this.fontType = Font.ITALIC;
            }
            
            public void deselect() {
                selected = false;
                this.size = 20;
                this.fontType = Font.BOLD;
            }
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawRect(0,0,width,height);
            g2d.setColor(Color.RED);
            try {
                g2d.drawImage(ImageIO.read(MainMenu.class.getResourceAsStream("MainTitle.png")),100,100, this);
            }catch(IOException e){
                System.err.println(e);
            }
                
            int spacing = 400;
            for (Option option : options) {
                g2d.setFont(new Font("Gill Sans", option.fontType ,option.size));
                g2d.drawString(option.caption,200,spacing+= 100);
                if (option.caption.equals("HighScores")) {
                    if (noScores) {
                        g2d.drawString("(No High Scores!!!)", 500, spacing);
                    }
                }
            }
            
            for (int e = 0; e <= 10; e++) {
                int xpos = 400 + (int) (Math.random()*(width-(500)));
                int ypos = (int) (Math.random()*(height-100));
                int enemyType = (int) (Math.random()*4);
                
                g2d.drawImage((new Enemy(enemyType)).getImage(),xpos,ypos,100,60, this);
            }
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
//        switch (actionString) {
//            case "UP" :
//                int ui = getSelectedIndex();
//                (options[ui]).deselect();
//                try {
//                    (options[ui-1]).select();
//                } catch (Exception e) {
//                    (options[options.length -1]).select();
//                }
//                break;
//            case "DOWN" :
//                int di = getSelectedIndex();
//                (options[di]).deselect();
//                try {
//                    (options[di+1]).select();
//                } catch (Exception e) {
//                    (options[0]).select();
//                }
//                break;
//            case "CONFIRM" :
//                if (startOption.selected) {
//                    SpaceInvaders.level = 1;
//                    menuFrame.setVisible(false);
//                } else  if (exitOption.selected) {
//                    System.exit(0);
//                } else if (highScoresOption.selected) {
//                    HighScores.start(width, height);
//                }
//                break;
//            default :
//                break;
//        }
        if (actionString.equals("UP")) {
            int ui = getSelectedIndex();
            (options[ui]).deselect();
            try {
                (options[ui-1]).select();
            } catch (Exception e) {
                (options[options.length -1]).select();
            }
        } else if (actionString.equals("DOWN")) {
            int di = getSelectedIndex();
            (options[di]).deselect();
            try {
                (options[di+1]).select();
            } catch (Exception e) {
                (options[0]).select();
            }
        } else if (actionString.equals("CONFIRM")) {
            if (startOption.selected) {
                SpaceInvaders.level = 1;
                menuFrame.setVisible(false);
            } else  if (exitOption.selected) {
                System.exit(0);
            } else if (highScoresOption.selected) {
                try {
                    HighScores.start(width, height);
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Assuming no HighScores have been set");
                    noScores = true;
                }
            }
        }
        repaint();
    }
        
        public Menu() {
            options = new Option[3];
            startOption = new Option("Start"); options[0] = startOption;
            highScoresOption = new Option("HighScores"); options[1] = highScoresOption;
            exitOption = new Option("Exit"); options[2] = exitOption;
            
            startOption.select();
            setUpKeyboardListener();
        }
    }
    
    public static void start(int w, int h) {
        width = w; height = h;
        menuFrame = new JFrame();
        menuFrame.setUndecorated(true);
        menuFrame.setSize(width, height);
        menuFrame.add(new Menu());
        menuFrame.setVisible(true);
    }
}
