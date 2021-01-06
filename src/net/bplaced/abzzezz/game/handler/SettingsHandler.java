/*
 * @author Roman
 * Last modified: 04.01.21, 19:55 by kursc
 *
 *
 *
 */

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
        settings.add(new Setting("volume", 0, 100, 50));
        settings.add(new Setting("background", "TV", true, "TV", "Exit")
                .setSettingStateChangedListener(setting -> {
                    if (setting.getSelected().equals("TV")) {
                        ShaderHandler.SHADER_HANDLER.setBackgroundShader(new BackgroundShader());
                    } else {
                        ShaderHandler.SHADER_HANDLER.setBackgroundShader(new ExitBackgroundShader());
                    }
                }));

        settings.add(new Setting("CRT-shader", true).setSettingStateChangedListener(setting -> Settings.crtBackground = setting.isState()));
        settings.add(new Setting("CRT-Alpha", 0, 1, 0.6f).setSettingStateChangedListener(setting -> Settings.crtAlpha = setting.getCurrent()));
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public Optional<Setting> getSettingByTag(final String tag) {
        return settings.stream().filter(setting -> setting.getTag().equals(tag)).findFirst();
    }

}
