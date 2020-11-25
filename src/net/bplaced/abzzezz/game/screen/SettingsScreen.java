package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.ui.uicomponents.Slider;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.util.Optional;


public class SettingsScreen extends Screen {


    @Override
    public void init() {
        int xPos = 50;
        final Optional<Setting> vol = GameMain.getInstance().getSettingsHandler().getSettingByTag("volume");
        final Slider volumeSlider = new Slider("Volume", xPos, getHeight() / 4, 100, 20, 0, 100, vol.map(setting1 -> setting1.getCurrent() * 100).orElse(0F));
        volumeSlider.setSliderListener(value -> GameMain.getInstance().getSettingsHandler().getSettingByTag("volume").ifPresent(setting -> {
            setting.setCurrent(value / 100);
        }));
        getUiComponents().add(volumeSlider);
        super.init();
    }

    @Override
    public void drawScreen() {

        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.getInstance().getShader().draw();
        super.drawShader();
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            EngineCore.getInstance().setScreen(new MainMenu());
        }
        super.keyTyped(keyCode, keyTyped);
    }
}
