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
import spaceinvaders.SpaceInvaders;

/**
 *
 * @author nobesj
 */
public class Sound {
    //private final static AudioClip musicClip = Applet.newAudioClip((URL) Sound.class.getResource("General/Music/theme.wav"));
    private static AudioClip musicClip;
    
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
        if (!SpaceInvaders.effectsMuted) {
            URL url;
            if (entity.getFaction()== Entity.entityFaction.PLAYER) {
                url = Sound.class.getResource("Player/" + sound.toString());
            } else {
                url = Sound.class.getResource("Enemy/" + sound.toString());
            }
            AudioClip clip = Applet.newAudioClip(url);
            clip.play();
        } else {
            //System.out.println("Sound effects muted, will not play...");
        }
    }
    
    static class MusicThread implements Runnable {
        private Thread mt;
        @Override
        public void run() {
            musicClip = Applet.newAudioClip((URL) Sound.class.getResource("General/Music/theme.wav"));
            musicClip.loop();
        }
        
        public void start() {
            mt = new Thread(this, "bonusMovementHandlingThread");
            mt.start();
        }
    }
    
    public static void startMusic() {
        System.out.print("Testing if muted...: ");
        System.out.println(SpaceInvaders.musicMuted);
        if (!SpaceInvaders.musicMuted) {
            MusicThread mpt = new MusicThread(); mpt.start();
        } else {
            System.out.println("Music was muted, no playing.");
        }
    }
    
    public static void stopMusic() {
        musicClip.stop();
    }
}
