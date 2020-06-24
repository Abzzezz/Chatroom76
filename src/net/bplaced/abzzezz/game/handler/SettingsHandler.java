package net.bplaced.abzzezz.game.handler;

public class SettingsHandler {

    private float volume = 1;
    private Integer[] resolution = new Integer[]{1920, 1080};

    public float getVolume() {
        return volume;
    }

    public Integer[] getResolution() {
        return resolution;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setResolution(Integer[] resolution) {
        this.resolution = resolution;
    }
}
