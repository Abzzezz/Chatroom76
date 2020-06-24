package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.ui.uicomponents.Slider;
import net.bplaced.abzzezz.game.MainClass;
import org.lwjgl.input.Keyboard;


public class SettingsScreen extends Screen {


    @Override
    public void init() {
        int xPos = 50;
        Slider volumeSlider = new Slider("Volume", xPos, getHeight() / 4, 100, 40, 0, 100, MainClass.getInstance().getSettingsHandler().getVolume() * 100);
        volumeSlider.setSliderListener(value -> MainClass.getInstance().getSettingsHandler().setVolume(value / 100));
        getUiComponents().add(volumeSlider);
        super.init();
    }

    @Override
    public void drawScreen() {

        super.drawScreen();
    }

    @Override
    public void drawShader() {
        super.drawShader();
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if(keyCode == Keyboard.KEY_ESCAPE) {
            EngineCore.getInstance().setScreen(new MainMenu());
        }
        super.keyTyped(keyCode, keyTyped);
    }
}
