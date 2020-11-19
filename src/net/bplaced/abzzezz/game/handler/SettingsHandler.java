package net.bplaced.abzzezz.game.handler;

public class SettingsHandler {

    private float volume = 1;
    private int[] resolution = new int[]{1920, 1080};

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int[] getResolution() {
        return resolution;
    }

    public void setResolution(int[] resolution) {
        this.resolution = resolution;
    }
}
