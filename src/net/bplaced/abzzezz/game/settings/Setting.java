package net.bplaced.abzzezz.game.settings;

import java.util.Arrays;
import java.util.List;

public class Setting {

    private final String tag;
    private final SettingsType settingsType;

    private boolean state;
    private List<String> components;
    private String selected;
    private float min, max, current;

    public Setting(String tag, boolean state) {
        this.tag = tag;
        this.state = state;
        this.settingsType = SettingsType.BOOL;
    }

    public Setting(String tag, String selected, List<String> components, boolean switcher) {
        this.tag = tag;
        this.components = components;
        this.selected = selected;
        this.settingsType = switcher ? SettingsType.SWITCHER : SettingsType.LIST;
    }

    public Setting(String tag, String selected, boolean switcher, String... components) {
        this.tag = tag;
        this.components = Arrays.asList(components);
        this.selected = selected;
        this.settingsType = switcher ? SettingsType.SWITCHER : SettingsType.LIST;
    }

    public Setting(String tag, float min, float max, float current) {
        this.tag = tag;
        this.min = min;
        this.max = max;
        this.current = current;
        this.settingsType = SettingsType.LIST;
    }

    public SettingsType getSettingsType() {
        return settingsType;
    }

    public String getTag() {
        return tag;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }
}


