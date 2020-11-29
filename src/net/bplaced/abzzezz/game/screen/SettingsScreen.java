package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.CheckBox;
import net.bplaced.abzzezz.core.ui.components.ListView;
import net.bplaced.abzzezz.core.ui.components.Slider;
import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.settings.Setting;
import org.lwjgl.input.Keyboard;


public class SettingsScreen extends BasicScreen {


    @Override
    public void init() {
        int yBuffer = getHeight() / 4 + 15;
        int xBuffer = 50;

        for (final Setting setting : GameMain.INSTANCE.getSettingsHandler().getSettings()) {
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
        GameMain.INSTANCE.getShader().draw();
        super.drawShader();
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) Core.getInstance().setScreen(new MainMenu());
        super.keyTyped(keyCode, keyTyped);
    }
}
