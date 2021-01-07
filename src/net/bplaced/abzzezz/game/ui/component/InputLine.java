/*
 * @author Roman
 * Last modified: 06.01.21, 22:05 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.component;

import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.core.util.AllowedCharacter;
import net.bplaced.abzzezz.core.util.TimeUtil;
import net.bplaced.abzzezz.core.util.io.KeyboardUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.game.handler.CommandHandler;
import org.lwjgl.opengl.Display;

import static org.lwjgl.input.Keyboard.*;

public class InputLine implements UIComponent {

    private final StringBuilder backupText = new StringBuilder();
    private final StringBuilder displayText = new StringBuilder();

    private final TimeUtil bounceTime = new TimeUtil(), bounceTime2 = new TimeUtil();

    private final float xPos, yPos;
    private final int width, height;

    public InputLine() {
        this.xPos = 15;
        this.yPos = Display.getHeight() - textFont.getHeight() * 2;
        this.width = Display.getWidth();
        this.height = textFont.getHeight();
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void drawComponent() {
        final String displayString = "> " + displayText.toString();

        if (bounceTime.isTimeOver(1000)) {
            if (bounceTime2.isTimeOver(1600)) {
                bounceTime2.reset();
                bounceTime.reset();
            }
        } else {
            final int tabHeight = 4;
            RenderUtil.drawQuad(xPos + textFont.getStringWidth(displayString), yPos + height - tabHeight, tabHeight * 2, tabHeight, mainColor);
        }

        textFont.drawString(displayString, 15, yPos, textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
        if (KeyboardUtil.shiftReturn()) {
            deleteAllText();
        } else if (KeyboardUtil.ctrlVDown()) {
            displayText.append(KeyboardUtil.getClipboard());
        }

        if (keyCode == KEY_RETURN) {
            CommandHandler.COMMAND_HANDLER.getCommands().forEach(command -> {
                for (final String trigger : command.trigger()) {
                    final String lineStart = toString().split("\\s+")[0];
                    if (lineStart.equalsIgnoreCase(trigger)) {
                        command.execute(toString());
                        break;
                    }
                }
            });
        }

        if (keyCode == KEY_BACK) {
            if (displayText.length() > 0) {
                displayText.deleteCharAt(displayText.length() - 1);

                if (backupText.length() > 0) {
                    int index = backupText.length() - 1;
                    displayText.insert(0, backupText.charAt(index));
                    backupText.deleteCharAt(index);
                }
            }
        } else {
            //If text out of bounds append old characters to backup-string and delete from displayed string
            boolean disallowed = !(keyCode == KEY_LSHIFT) && !(keyCode == KEY_RSHIFT) && !(keyCode == KEY_RCONTROL) && !(keyCode == KEY_LCONTROL);
            if (disallowed && AllowedCharacter.isAllowedCharacter(keyTyped))
                displayText.append(keyTyped);
        }

        while (textFont.getStringWidth(displayText.toString()) > width - 40) {
            backupText.append(displayText.charAt(0));
            displayText.deleteCharAt(0);
        }
    }

    private void deleteAllText() {
        displayText.delete(0, displayText.length());
        backupText.delete(0, backupText.length());
    }


    public String toString() {
        return String.valueOf(backupText) + displayText;
    }

    @Override
    public void mouseListener(int mouseButton) {

    }

    @Override
    public void drawShader() {

    }

    @Override
    public void refreshPositions() {

    }

    @Override
    public float getXPos() {
        return xPos;
    }

    @Override
    public float getYPos() {
        return yPos;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
