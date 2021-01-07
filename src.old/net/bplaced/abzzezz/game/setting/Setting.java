/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.setting;

import java.util.Arrays;
import java.util.List;

public class Setting<E> {

    private final String tag;
    private final SettingsType settingsType;

    private boolean state;
    private List<E> components;
    private String selected;
    private float min, max, current;
    private SettingStateChanged settingStateChangedListener;

    public Setting(String tag, boolean state) {
        this.tag = tag;
        this.state = state;
        this.settingsType = SettingsType.BOOL;
    }

    public Setting(String tag, String selected, List<E> components, boolean switcher) {
        this.tag = tag;
        this.components = components;
        this.selected = selected;
        this.settingsType = switcher ? SettingsType.SWITCHER : SettingsType.LIST;
    }

    public Setting(String tag, String selected, boolean switcher, E... components) {
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
        this.settingsType = SettingsType.SLIDER;
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
        stateChanged();
    }

    public List<E> getComponents() {
        return components;
    }

    public void setComponents(List<E> components) {
        this.components = components;
        stateChanged();
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
        stateChanged();
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
        stateChanged();
    }

    private void stateChanged() {
        if (settingStateChangedListener != null)
            settingStateChangedListener.stateChanged(this);
    }

    public Setting setSettingStateChangedListener(final SettingStateChanged settingStateChangedListener) {
        this.settingStateChangedListener = settingStateChangedListener;
        return this;
    }

    public interface SettingStateChanged {
        void stateChanged(Setting setting);
    }
}


