/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders.Levels;

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
        totalEnemies = 20;
        numInRow = 5;
        numInColumn = 4;
        rowLevels = new int[numInColumn];
        rowLevels[0] = 3;
        rowLevels[1] = 2;
        rowLevels[2] = 2;
        rowLevels[3] = 1;
    }
}
