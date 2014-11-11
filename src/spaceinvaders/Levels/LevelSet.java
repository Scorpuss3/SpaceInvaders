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
import spaceinvaders.LoadingBar;

/**
 *
 * @author nobesj
 */
public class LevelSet {
    public int totalEnemies;
    public int numInRow;
    public int numInColumn;
    public int[] rowLevels;
    private LoadingBar loadPanel;
    
    public LevelSet(int levelToLoad, LoadingBar loadPanel) {
        this.loadPanel = loadPanel;
        loadPanel.increment();
        ArrayList fileLines = getFileData(levelToLoad);
        totalEnemies = Integer.parseInt((String) fileLines.get(0));//20
        numInRow = Integer.parseInt((String) fileLines.get(1));//5
        numInColumn = Integer.parseInt((String) fileLines.get(2));//4
        rowLevels = new int[numInColumn];
        //rowLevels[0] = 3;
        //rowLevels[1] = 2;
        //rowLevels[2] = 2;
        //rowLevels[3] = 1;
        int i;
        int line = 3;
        for (i = 0; i <= numInColumn-1 ; i++) {
            rowLevels[i] = Integer.parseInt((String) fileLines.get(line));
            line++;
        }
    }
    
    private ArrayList getFileData (int levelToLoad) {
        //http://tutorials.jenkov.com/java-io/inputstream.html
        ArrayList finalLines = new ArrayList();
        String fileName = "Level_" + Integer.toString(levelToLoad) + ".txt";
        InputStream input = getClass().getResourceAsStream(fileName);
        Reader reader = new InputStreamReader(input);
        String asString = "";
        String[] lineArray;
        try {
            int data = reader.read();
            while(data != -1){
                char dataChar = (char) data;
                asString += dataChar;
                //System.out.println(dataChar);
                data = reader.read();
            }
            lineArray = asString.split("\\r?\\n");
            for (String line : lineArray) {
                finalLines.add(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return finalLines;
    }
}
