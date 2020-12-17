package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.CheckBox;
import net.bplaced.abzzezz.core.ui.components.Slider;
import net.bplaced.abzzezz.core.ui.components.Switcher;
import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.setting.Setting;
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
                case LIST:
                case SWITCHER:
                    component = new Switcher<>(setting.getComponents(), setting.getSelected(), xBuffer, yBuffer, 100, 30, tag);
                    ((Switcher<?>) component).setSwitcherListener(new Switcher.SwitcherListener() {
                        @Override
                        public <Item> void onItemSelected(Item item) {
                            setting.setSelected((String) item);
                        }
                    });
                    break;
                case SLIDER:
                    component = new Slider(tag, xBuffer, yBuffer, 100, 15, setting.getMin(), setting.getMax(), setting.getCurrent());
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
                yBuffer += component.getWidth();
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
        ShaderHandler.SHADER_HANDLER.getBackgroundShader().draw();
        super.drawShader();
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) Core.getInstance().setScreen(new MainMenu());
        super.keyTyped(keyCode, keyTyped);
    }
}
