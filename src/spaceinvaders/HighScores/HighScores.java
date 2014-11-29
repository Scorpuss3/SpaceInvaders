/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders.HighScores;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import spaceinvaders.SpaceInvaders;

/**
 *
 * @author Acer Laptop
 */
public class HighScores {
    private static final int maxSavedScores = 10;
    private static class ScorePanel extends JPanel {
        private JFrame myFrame;
        private LinkedHashMap<String, String> scores = new LinkedHashMap<String, String>();
        private final int height, width;
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2d.setColor(Color.red);
            g2d.drawRect(50,50,width-100,height-100);
            
            g2d.setFont(new Font("Gill Sans", Font.BOLD,(int) (20*SpaceInvaders.aspectMultiplier)));
            int i = 200;
//            for (Map.Entry<String, String> e : scores.entrySet()) {
//                i += 40;
//                System.out.println("Looped through scores.");
//                g2d.drawString(e.getKey(),(width/2)-100,i);
//                g2d.drawString(e.getValue(),(width/2)+100,i);
//            }
            System.out.println("Score length at draw: " + scores.size());
            for (Object okey : scores.keySet()) {
                i += 40;
                String key = (String) okey;
                String pScore = (String) scores.get(key);
                g2d.drawString(key,(width/2)-100,i);
                g2d.drawString(pScore,(width/2)+100,i);
            }
        }
        
        public void getScoreInfo(LinkedHashMap scores) {
            //http://tutorials.jenkov.com/java-io/inputstream.html
            String fileName = "HighScores.txt";
            InputStream input = getClass().getResourceAsStream(fileName);
            Reader reader = new InputStreamReader(input);
            String asString = "";
            String[] lineArray;
            try {
                System.out.println("Starting file read...");
                int data = reader.read();
                System.out.println("File read ended...");
                while(data != -1){
                    char dataChar = (char) data;
                    asString += dataChar;
                    //System.out.println(dataChar);
                    data = reader.read();
                }
                lineArray = asString.split("\\r?\\n");
                for (String line : lineArray) {
                    //finalLines.add(line);
                    scores.put(line.substring(0,32),line.substring(32));
                }
            } catch (IOException x) {
                System.err.format("IOException: %s%n", x);
            }
        }
        
        public LinkedHashMap getScoreInfo2(LinkedHashMap scores) {
            Properties highScores = new Properties();
            //InputStream input = HighScores.class.getResourceAsStream("HighScores.properties");
            FileInputStream input = null;
            try {
                input = new FileInputStream("HighScores.properties");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                highScores.load(input);
                input.close();
            } catch (IOException ex) {
            }
            scores  = sortProperties(highScores);
            System.out.println("scores length: " + scores.size());
            for (Object key : scores.keySet()) {
                System.out.print((String) key);
                System.out.println(": " + (String) scores.get(key));
            }
            Enumeration e = highScores.propertyNames();

            //while (e.hasMoreElements()) {
            //    String key = (String) e.nextElement();
            //    String score =  highScores.getProperty(key);
            //    scores.put(key,score);
            //}
            return scores;
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
//            switch (actionString) {
//                case "EXIT" :
//                    myFrame.setVisible(false);
//                    break;
//                default :
//                    break;
//            }
            if (actionString.equals("EXIT")) {
                myFrame.setVisible(false);
            }
        }
        
        public ScorePanel(int parsedWidth, int parsedHeight, JFrame parsedFrame) {
            height = parsedHeight; width = parsedWidth;
            scores = getScoreInfo2(scores);
            setUpKeyboardListener();
            myFrame = parsedFrame;
            repaint();
        }
    }
    
    public static void start(int gotWidth, int gotHeight) {
        JFrame scoreFrame = new JFrame();
        scoreFrame.setUndecorated(true);
        scoreFrame.setSize(gotWidth, gotHeight);
        scoreFrame.add(new ScorePanel(gotWidth,gotHeight, scoreFrame));
        scoreFrame.setVisible(true);
    }
    
    public static LinkedHashMap sortProperties (Properties p) {
        Properties oldProps = new Properties();
        Enumeration e = p.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String score =  p.getProperty(key);
            oldProps.setProperty(key, score);
        }
        
        System.out.println("Number of scores: " + p.values().size());
        p.clear();
        
        Enumeration e0 = oldProps.propertyNames();
        Integer[] scoresInOrder = new Integer[oldProps.values().size()];
        System.out.println("Number of scores: " + oldProps.values().size());
        int index = 0;
        while (e0.hasMoreElements()) {
            String key = (String) e0.nextElement();
            String score =  oldProps.getProperty(key);
            scoresInOrder[index] = (Integer.parseInt(score));
            index++;
            System.out.println("Added a score to array.");
        }
        Arrays.sort(scoresInOrder);
        for (int i = scoresInOrder.length - 1; i >= 0; i--) {
            System.out.print(scoresInOrder[i] + " ");
        }
        System.out.println();
        
        LinkedHashMap<String, String> sortedScores = new LinkedHashMap<String, String>();
        for (int i = scoresInOrder.length -1  ; i >= 0 ; i--) {
            int currentScore = scoresInOrder[i];
            System.out.println("Selected Score: " + currentScore);
            Enumeration e2 = oldProps.propertyNames();
            while (e2.hasMoreElements()) {
                String key = (String) e2.nextElement();
                if (Integer.parseInt(oldProps.getProperty(key)) == currentScore) {
                    if (scoresInOrder.length - i <= maxSavedScores) {
                        p.setProperty(key, oldProps.getProperty(key));
                        System.out.println("Added a property to p, score was: " + oldProps.getProperty(key));
                        sortedScores.put(key, oldProps.getProperty(key));
                        System.out.println("Now finished adding to scores.");
                    }
                    oldProps.remove(key);
                }
            }
        }
        System.out.println("sortedScores length: " + sortedScores.size());
        return sortedScores;
    }
    
    public static void addRecord(String name, Integer score) {
//        try {
//            System.out.println(name + score.toString());
//            //PrintWriter writer = new PrintWriter(HighScores.class.getResource("HighScores/HighScores.txt").getPath());
//            //PrintWriter writer = new PrintWriter("HighScores.txt");
//            File file = new File(resourceUrl.toURI());
//            OutputStream output = new FileOutputStream(file);
//            PrintWriter writer = new PrintWriter(output); //use outputstream one
//            System.out.println("Writer successfully initialized");
//            String fullString = name + "                                ".substring(name.length()) + score.toString();
//            writer.append(fullString);
//            //writer.println(fullString);
//            writer.close();
//            System.out.println("Append and close done.");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        Properties highScores = new Properties();
        //InputStream input = HighScores.class.getResourceAsStream("HighScores.properties");
        FileInputStream input = null;
        try {
            input = new FileInputStream("HighScores.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HighScores.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            highScores.load(input);
            input.close();
            boolean gettingPossibleName = true;
            while (gettingPossibleName == true) {
                if (highScores.containsKey(name)) {
                    String newName = JOptionPane.showInputDialog(null,"Name taken. Enter new name:");
                    if (newName != null) {
                        name = newName;
                    }
                } else {
                    gettingPossibleName = false;
                }
            }
        } catch (IOException ex) {
        } catch (NullPointerException ee) {
        }
        
        highScores.setProperty(name, score.toString());
        sortProperties(highScores);
        FileOutputStream output;
        try {
            //output = new FileOutputStream(new File("HighScores.properties"));
            //highScores.store(output,"");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //URL url = classLoader.getResource("HighScores/HighScores.properties");
            //URL url = HighScores.class.getResource("HighScores.properties");
            //System.out.println(url.toString());
            File saveFile = new File("HighScores.properties");
            if (! saveFile.exists()) {
                saveFile.createNewFile();
            }
            highScores.store(new FileOutputStream(saveFile), "");
            //highScores.store(new FileOutputStream(new File(url.toURI())), "");
            //highScores.store(new FileOutputStream(new File("HighScores.properties")),""); //works out of jar...
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
    }
}
