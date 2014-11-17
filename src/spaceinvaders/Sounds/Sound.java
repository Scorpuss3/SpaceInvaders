/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders.Sounds;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import spaceinvaders.Entities.Entity;

/**
 *
 * @author nobesj
 */
public class Sound {
    private static AudioClip musicClip = Applet.newAudioClip((URL) Sound.class.getResource("General/Music/theme.wav"));
    
    public static enum soundType {
        SHOOT ("Shoot.wav"),
        HIT ("Hit.wav"),
        MOVE ("Move.wav"),
        DEAD ("Dead.wav");
        
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
        URL url;
        if (entity.getFaction()== Entity.entityFaction.PLAYER) {
            url = Sound.class.getResource("Player/" + sound.toString());
        } else {
            url = Sound.class.getResource("Enemy/" + sound.toString());
        }
        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
    }
    
    public static void startMusic() {
        musicClip.loop();
    }
    
    public static void stopMusic() {
        musicClip.stop();
    }
}
