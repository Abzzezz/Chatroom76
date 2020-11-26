package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.ui.uicomponents.CheckBox;
import net.bplaced.abzzezz.engine.ui.uicomponents.ListView;
import net.bplaced.abzzezz.engine.ui.uicomponents.Slider;
import net.bplaced.abzzezz.engine.ui.uicomponents.UIComponent;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.util.Collections;


public class SettingsScreen extends Screen {


    @Override
    public void init() {
        int yBuffer = 0;
        int xBuffer = 50;

        for (final Setting setting : GameMain.getInstance().getSettingsHandler().getSettings()) {
            if (yBuffer >= 400) {
                xBuffer += 50;
            }
            UIComponent component = null;
            final String tag = setting.getTag();
            switch (setting.getSettingsType()) {
                case SWITCHER:
                case LIST:
                    if(setting.getComponents() == null) break;
                    component = new ListView(setting.getComponents(), xBuffer, yBuffer, 50, tag);
                    break;
                case SLIDER:
                    component = new Slider(tag, xBuffer, yBuffer, 100, 50, setting.getMin(), setting.getMax(), setting.getCurrent());
                    break;
                case BOOL:
                    component = new CheckBox(setting.isState(), xBuffer, yBuffer, 50, tag);
                    break;
                default:
                    break;
            }
            if(component != null) {
                getUiComponents().add(component);
                yBuffer += 100;
            }
        }

        //final Optional<Setting> vol = GameMain.getInstance().getSettingsHandler().getSettingByTag("volume");
        // final Slider volumeSlider = new Slider("Volume", xPos, getHeight() / 4, 100, 20, 0, 100, vol.map(setting1 -> setting1.getCurrent() * 100).orElse(0F));
        // volumeSlider.setSliderListener(value -> GameMain.getInstance().getSettingsHandler().getSettingByTag("volume").ifPresent(setting -> {
        //   setting.setCurrent(value / 100);
        //}));
        //getUiComponents().add(volumeSlider);
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
