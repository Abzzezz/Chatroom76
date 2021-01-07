/*
 * @author Roman
 * Last modified: 04.01.21, 19:49 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.ui.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.Slider;
import net.bplaced.abzzezz.core.ui.components.Switcher;
import net.bplaced.abzzezz.core.ui.components.Text;
import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.setting.Setting;
import net.bplaced.abzzezz.game.ui.component.DOSCheckBox;
import net.bplaced.abzzezz.game.ui.component.InputLine;
import org.lwjgl.input.Keyboard;


public class SettingsScreen extends BasicScreen {

    @Override
    public void init() {
        final int initialY = getHeight() / 4 + 40;

        int yStack = initialY;
        int xStack = 50;

        for (final Setting setting : GameMain.INSTANCE.getSettingsHandler().getSettings()) {
            if (yStack >= getHeight() - 100) {
                xStack += 250;
                yStack = initialY;
            }
            UIComponent component = null;
            final String tag = setting.getTag();
            switch (setting.getSettingsType()) {
                case LIST:
                case SWITCHER:
                    component = new Switcher<>(setting.getComponents(), setting.getSelected(), xStack, yStack, 200, 30, tag);
                    ((Switcher<?>) component).setSwitcherListener(new Switcher.SwitcherListener() {
                        @Override
                        public <Item> void onItemSelected(Item item) {
                            setting.setSelected((String) item);
                        }
                    });
                    break;
                case SLIDER:
                    component = new Slider(tag, xStack, yStack, 200, 15, setting.getMin(), setting.getMax(), setting.getCurrent());
                    ((Slider) component).setSliderListener(setting::setCurrent);
                    break;
                case BOOL:
                    component = new DOSCheckBox(setting.isState(), xStack, yStack, 30, tag);
                    ((DOSCheckBox) component).setStateChangedListener(setting::setState);
                    break;
                default:
                    break;
            }

            if (component != null) {
                getUiComponents().add(component);
                yStack += component.getHeight() * 5;
            }
        }
        getUiComponents().add(new Text(getWidth() / 2, getHeight() / 6, "Settings", mainColor, true, bigFont));
        getUiComponents().add(new InputLine());
        super.init();
    }
}
