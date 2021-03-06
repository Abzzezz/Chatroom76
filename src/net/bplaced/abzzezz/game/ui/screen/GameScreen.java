/*
 * @author Roman
 * Last modified: 10.01.21, 20:49 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.ui.component.components.ColorTextComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;
import net.bplaced.abzzezz.game.Game;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class GameScreen extends Screen {

    @Override
    public void initialise() {
        //TODO: Fix char 0; Restrict input
        Game.GAME.getCommandLine().requestAllInput(s -> {
            if (!s.isEmpty()) {
                Game.GAME.getDialogHandler().selectOption(s.charAt(0), selected -> {
                    addUIComponent(new ColorTextComponent(selected, currentY, Color.decode("#FB7E3F")));
                    keyPressed(Keyboard.KEY_SPACE, ' ');
                });
            }
        });
        super.initialise();
    }

    @Override
    public void keyPressed(int keyCode, char keyCharacter) {
        if (keyCode == Keyboard.KEY_SPACE && !Game.GAME.getDialogHandler().isPending()) {
            final String[] strings = Game.GAME.getDialogHandler().getNextDialog();
            if (strings != null)
                addUIComponent(new ColorTextComponent(strings[0], currentY, Color.decode(strings[1])));
        }
        super.keyPressed(keyCode, keyCharacter);
    }

    @Override
    public void drawShader() {
        ShaderHandler.SHADER_HANDLER.getTextureShader().draw();
        ShaderHandler.SHADER_HANDLER.getCrtShader().draw();
        super.drawShader();
    }
}
