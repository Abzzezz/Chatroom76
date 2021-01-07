/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.sound;

import net.bplaced.abzzezz.game.GameMain;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    private final int pitch = 1;
    private Music effect;
    private Music backgroundMusic;

    public void playSound(File soundLocation, float volume) {
        try {
            this.effect = new Music(soundLocation.toURI().toURL());
            effect.play(pitch, volume * GameMain.INSTANCE.getSettingsHandler().getSettingByTag("volume").map(setting -> setting.getCurrent() / 100).orElse(1F));
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
            backgroundMusic.play(pitch, volume * GameMain.INSTANCE.getSettingsHandler().getSettingByTag("volume").map(setting -> setting.getCurrent() / 100).orElse(1F));
            backgroundMusic.loop(pitch, volume * GameMain.INSTANCE.getSettingsHandler().getSettingByTag("volume").map(setting -> setting.getCurrent() / 100).orElse(1F));
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
