package net.bplaced.abzzezz.game.sounds;

import net.bplaced.abzzezz.game.MainClass;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.IOException;


public class SoundPlayer {

    private Music effect;
    private Music backgroundMusic;

    public void playSound(File soundLocation, float volume) {
        try {
            this.effect = new Music(soundLocation.toURI().toURL());
            effect.play(1.0F, volume * MainClass.getInstance().getSettingsHandler().getVolume());
        } catch (IOException | SlickException e) {
            e.printStackTrace();
        }
    }

    public void stopSounds() {
        if (effect != null) {
            effect.stop();
            effect = null;
        }
    }

    public void playBackgroundMusic(File soundLocation, float volume) {
        try {
            this.backgroundMusic = new Music(soundLocation.toURI().toURL());
            backgroundMusic.play();
            backgroundMusic.loop(1.0F, volume * MainClass.getInstance().getSettingsHandler().getVolume());
        } catch (IOException | SlickException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic = null;
        }
    }
}
