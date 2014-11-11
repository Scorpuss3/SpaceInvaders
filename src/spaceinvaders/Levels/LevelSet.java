/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders.Levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author nobesj
 */
public class LevelSet {
    public int totalEnemies;
    public int numInRow;
    public int numInColumn;
    public int[] rowLevels;
    
    public LevelSet(int levelToLoad) {
        ArrayList fileLines = getFileData2(levelToLoad);
        totalEnemies = 20;
        numInRow = 5;
        numInColumn = 4;
        rowLevels = new int[numInColumn];
        rowLevels[0] = 3;
        rowLevels[1] = 2;
        rowLevels[2] = 2;
        rowLevels[3] = 1;
    }
    
    private static ArrayList getFileData(int levelToLoad) {
        //http://stackoverflow.com/questions/2271926/how-to-read-a-file-from-a-jar-file
        //http://stackoverflow.com/questions/3369794/how-to-a-read-file-from-jar-in-java
        ArrayList finalLines = new ArrayList();
        String fileName = "/spaceinvaders/Levels/Level_" + Integer.toString(levelToLoad) + ".txt";
        Path filePath = Paths.get(fileName);
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                 System.out.println(line);
                 finalLines.add(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return finalLines;
    }
    
    private ArrayList getFileData2 (int levelToLoad) {
        //http://tutorials.jenkov.com/java-io/inputstream.html
        ArrayList finalLines = new ArrayList();
        String fileName = "Level_" + Integer.toString(levelToLoad) + ".txt";
        InputStream input = getClass().getResourceAsStream(fileName);
        Reader reader = new InputStreamReader(input);
        try {
            int data = reader.read();
            while(data != -1){
                char dataChar = (char) data;
                System.out.println(dataChar);
                finalLines.add(dataChar);
                data = reader.read();
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return finalLines;
    }
}
