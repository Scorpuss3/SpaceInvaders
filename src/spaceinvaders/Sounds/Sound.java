/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders.Sounds;

import java.net.URL;
import spaceinvaders.Entities.Entity;

/**
 *
 * @author nobesj
 */
public class Sound {
    public static enum soundType {
        SHOOT ("Shoot.wav"),
        HIT ("Hit.wav"),
        MOVE ("Move.wav");
        
        private final String name;
        
        private soundType (String name) {
            this.name = name;
        }
        
        @Override
        public String toString(){
            return name;
        }
    }
    
    public static void playSound(Entity entity, soundType sound) {
        if (entity.getFaction()== Entity.entityFaction.PLAYER) {
            URL url = Sound.class.getResource("Player/" + sound.toString());
        } else {
            URL url = Sound.class.getResource("Enemy/" + sound.toString());
        }
    }
    
    public static void startMusic() {
        
    }
    
    public static void stopMusic() {
        
    }
}
