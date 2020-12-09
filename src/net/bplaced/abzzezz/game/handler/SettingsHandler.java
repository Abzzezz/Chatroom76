package net.bplaced.abzzezz.game.handler;

import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.game.setting.Setting;
import net.bplaced.abzzezz.game.shader.BackgroundShader;
import net.bplaced.abzzezz.game.shader.ExitBackgroundShader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SettingsHandler {

    private final List<Setting> settings = new ArrayList<>();

    public SettingsHandler() {
        settings.add(new Setting("resolution", "600x600", true, "600x600", "900x900", "300x300", "500x500", "1200x1200").setSettingStateChangedListener(setting -> {
            final String[] split = setting.getSelected().split("x");
        }));
        settings.add(new Setting("volume", 0, 100, 50));
        settings.add(new Setting("background", "TV", true, "TV", "Exit").setSettingStateChangedListener(setting -> {
            if (setting.getSelected().equals("TV")) {
                ShaderHandler.SHADER_HANDLER.setBackgroundShader(new BackgroundShader());
            } else {
                ShaderHandler.SHADER_HANDLER.setBackgroundShader(new ExitBackgroundShader());
            }
        }));
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public Optional<Setting> getSettingByTag(final String tag) {
        return settings.stream().filter(setting -> setting.getTag().equals(tag)).findFirst();
    }

}
