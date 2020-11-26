package net.bplaced.abzzezz.game.handler;

import net.bplaced.abzzezz.game.settings.Setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SettingsHandler {

    private final List<Setting> settings = new ArrayList<>();

    public SettingsHandler() {
        settings.add(new Setting("resolution", "1920x1080", true, "1920x1080", "1280x720"));
        settings.add(new Setting("volume", 0, 100, 50));
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public Optional<Setting> getSettingByTag(final String tag) {
        return settings.stream().filter(setting -> setting.getTag().equals(tag)).findFirst();
    }

}
