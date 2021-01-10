/*
 * @author Roman
 * Last modified: 09.01.21, 20:01 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.components;

import net.bplaced.abzzezz.core.ui.screen.Screen;
import net.bplaced.abzzezz.core.util.UIBasic;
import net.bplaced.abzzezz.core.util.io.KeyboardUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;
import net.bplaced.abzzezz.game.Game;
import net.bplaced.abzzezz.game.command.Command;
import net.bplaced.abzzezz.game.handler.CommandHandler;
import net.bplaced.abzzezz.game.util.AllowedCharacter;
import net.bplaced.abzzezz.game.util.TimeUtil;
import org.lwjgl.opengl.Display;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

import static org.lwjgl.input.Keyboard.*;

public class CommandLine implements UIBasic {

    private final StringBuilder backupText = new StringBuilder();
    private final StringBuilder displayText = new StringBuilder();

    private final int width, height;

    private final String arrow = "> ";
    private boolean nextInputRequested, inputRestricted, allInputRequested;

    private Consumer<String> requestedInput;

    private final TimeUtil bounceTime = new TimeUtil(), bounceTime2 = new TimeUtil();

    public CommandLine() {
        this.width = Display.getWidth();
        this.height = textFont.getHeight();
    }

    public void draw() {
        final String displayString = arrow + displayText.toString();

        if (bounceTime.isTimeOver(1000)) {
            if (bounceTime2.isTimeOver(1600)) {
                bounceTime2.reset();
                bounceTime.reset();
            }
        } else {
            final int tabHeight = 4;
            RenderUtil.drawQuad(xPos + textFont.getStringWidth(displayString), Screen.currentY + height - tabHeight, tabHeight * 2, tabHeight, mainColor);
        }

        textFont.drawWhiteString(displayString, xPos, Screen.currentY);
    }

    /*
    TODO: Will be used for scrolling
     */
    public void setYStack(int increment) {

    }

    /* ----------------- Input logic ----------------- */

    public void keyListener(int keyCode, char keyTyped) {
        if (isInputRestricted()) return;

        //Keyboard shortcuts
        if (KeyboardUtil.shiftReturn())
            deleteAllText();
        else if (KeyboardUtil.ctrlVDown())
            displayText.append(KeyboardUtil.getClipboard());


        //Enter action
        if (keyCode == KEY_RETURN) {
            final String string = this.toString();

            Game.GAME.addTextToUI(arrow + string);
            //Important for text fields and decision making in the actual game
            if (isNextInputRequested()) {
                this.requestedInput.accept(string);
                this.setNextInputRequested(false);
                this.deleteAllText();
                return;
            } else if (isAllInputRequested()) {
                this.requestedInput.accept(string);
                this.deleteAllText();
                return;
            }

            //Loops through all commands. If no command trigger matches, a error message is printed
            boolean commandFound = false;
            loop:
            for (final Command command : CommandHandler.COMMAND_HANDLER.getCommands()) {
                for (final String trigger : command.trigger()) {
                    final String line0 = string.split("\\s+")[0];
                    if (line0.equalsIgnoreCase(trigger)) {
                        commandFound = true;
                        final String exec = command.execute(string);
                        if (exec != null && !exec.isEmpty())
                            Game.GAME.addTextToUI(exec);
                        break loop;
                    }
                }
            }
            if (!commandFound)
                Game.GAME.addTextToUI("Command not found! Try help for a list of all commands.");

            this.deleteAllText();
        }

        //"Real" entering text
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
            final boolean disallowed = !(keyCode == KEY_LSHIFT) && !(keyCode == KEY_RSHIFT) && !(keyCode == KEY_RCONTROL) && !(keyCode == KEY_LCONTROL);
            if (disallowed && AllowedCharacter.isAllowedCharacter(keyTyped))
                displayText.append(keyTyped);
        }
        //Move text that is over a certain bound to the backup string builder
        while (textFont.getStringWidth(displayText.toString()) > width - 40) {
            backupText.append(displayText.charAt(0));
            displayText.deleteCharAt(0);
        }
    }


    private void deleteAllText() {
        displayText.delete(0, displayText.length());
        backupText.delete(0, backupText.length());
    }

    /* ----------------- Input requested ----------------- */

    private boolean isNextInputRequested() {
        return nextInputRequested;
    }

    private void setNextInputRequested(boolean nextInputRequested) {
        this.nextInputRequested = nextInputRequested;
    }

    public void requestNextInput(final Consumer<String> requestedInput) {
        this.setNextInputRequested(true);
        this.requestedInput = requestedInput;
    }

    /* ----------------- Input restricted ----------------- */

    public boolean isInputRestricted() {
        return inputRestricted;
    }

    public void setInputRestricted(boolean inputRestricted) {
        this.inputRestricted = inputRestricted;
    }

    /* ----------------- request all input ----------------- */

    public void requestAllInput(final Consumer<String> requestedInput) {
        this.setAllInputRequested(true);
        this.requestedInput = requestedInput;
    }

    public void stopRequestingAllInput() {
        this.allInputRequested = false;
    }

    public boolean isAllInputRequested() {
        return allInputRequested;
    }

    private void setAllInputRequested(boolean allInputRequested) {
        this.allInputRequested = allInputRequested;
    }

    /* ----------------- Other ----------------- */


    public int yPos() {
        return 0;
    }

    public int height() {
        return 0;
    }

    /**
     * Appends both the display & backup text
     *
     * @return result appending both
     */
    public String toString() {
        return String.valueOf(backupText) + displayText;
    }
}
