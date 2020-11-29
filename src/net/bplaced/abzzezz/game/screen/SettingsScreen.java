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


public class SettingsScreen extends Screen {


    @Override
    public void init() {
        int yBuffer = getHeight() / 4 + 15;
        int xBuffer = 50;

        for (final Setting setting : GameMain.getInstance().getSettingsHandler().getSettings()) {
            if (yBuffer >= getHeight() - 100) {
                xBuffer += 150;
            }
            UIComponent component = null;
            final String tag = setting.getTag();
            switch (setting.getSettingsType()) {
                case SWITCHER:
                case LIST:
                    if (setting.getComponents() == null) break;
                    component = new ListView(setting.getComponents(), xBuffer, yBuffer, 60, tag);
                    ((ListView) component).setClickListener((index, item) -> setting.setSelected(item.getObjectString()));
                    break;
                case SLIDER:
                    component = new Slider(tag, xBuffer, yBuffer, 100, 60, setting.getMin(), setting.getMax(), setting.getCurrent());
                    ((Slider) component).setSliderListener(setting::setCurrent);
                    break;
                case BOOL:
                    component = new CheckBox(setting.isState(), xBuffer, yBuffer, 60, tag);
                    ((CheckBox) component).setStateChangedListener(setting::setState);
                    break;
                default:
                    break;
            }
            if (component != null) {
                getUiComponents().add(component);
                yBuffer += 120;
            }
        }

        super.init();
    }

    @Override
    public void drawScreen() {
        drawCenteredMenuString("Settings", getWidth() / 2, getHeight() / 6);
        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.getInstance().getShader().draw();
        super.drawShader();
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) EngineCore.getInstance().setScreen(new MainMenu());
        super.keyTyped(keyCode, keyTyped);
    }
}
