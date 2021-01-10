/*
 * @author Roman
 * Last modified: 07.01.21, 23:08 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.game;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.handler.SavableHandler;
import net.bplaced.abzzezz.core.ui.component.UIComponent;
import net.bplaced.abzzezz.core.ui.component.components.OptionComponent;
import net.bplaced.abzzezz.core.ui.component.components.TextComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;
import net.bplaced.abzzezz.core.util.Basic;
import net.bplaced.abzzezz.core.util.OpenGLListener;
import net.bplaced.abzzezz.game.data.SettingsSavable;
import net.bplaced.abzzezz.game.handler.DialogHandler;
import net.bplaced.abzzezz.game.ui.components.CommandLine;
import net.bplaced.abzzezz.game.ui.screen.MainScreen;
import org.apache.commons.lang3.text.WordUtils;

public class Game extends Core implements Basic {

    /* ----------------- Singleton ----------------- */
    public static final Game GAME = new Game();

    /* ----------------- UI ----------------- */
    private Screen screen;
    private CommandLine commandLine;

    private DialogHandler dialogHandler;

    /**
     * Essentially the main method.
     * Starts the "opengl core" and initialises all handlers
     */
    public void initialise() {
        final int width = 600;
        final int height = 600;
        this.initialiseHandlers();
        this.setOpenGLListener(new OpenGLListener() {
            @Override
            public void onDisplayCreated() {
            }

            @Override
            public void onGLInitialised() {
                commandLine = new CommandLine();
                setScreen(new MainScreen());
            }

            @Override
            public void onDisplayCloseRequested() {
                SavableHandler.SAVABLE_HANDLER.saveAll();
            }
        });
        this.initialiseGL(width, height);
    }

    /**
     * Initialises all handlers, if they have no own instance
     */
    private void initialiseHandlers() {
        this.dialogHandler = new DialogHandler();

        SavableHandler.SAVABLE_HANDLER.addSavable(
                new SettingsSavable()
        );
        SavableHandler.SAVABLE_HANDLER.loadAll();
    }

    /**
     * Overwrites the core update
     */
    @Override
    protected void update() {
        super.update();

        this.screen.update();
    }

    /**
     * Overwrites the opengl draw loop
     */
    @Override
    public void draw() {
        super.draw();
        //Draw components
        this.uiComponents.forEach(UIComponent::draw);
        this.activeOptions.forEach(OptionComponent::draw);
        //Draw the commandline
        this.commandLine.draw();
    }

    /**
     * Overwrites the key pressed method
     *
     * @param keyCode      the responsible keycode
     * @param keyCharacter the actual character typed
     */
    @Override
    public void keyPressed(int keyCode, char keyCharacter) {
        if (screen != null)
            this.screen.keyPressed(keyCode, keyCharacter);
        this.commandLine.keyListener(keyCode, keyCharacter);
        super.keyPressed(keyCode, keyCharacter);
    }

    /**
     * Sets the new screen, closes the old one and pushes the old screen on the stack
     *
     * @param newScreen screen to switch to
     */
    public void setScreen(final Screen newScreen) {
        if (this.screen != null) {
            this.screen.close();
            this.previousScreens.addElement(screen);
        }
        newScreen.initialise();
        this.screen = newScreen;
    }

    /**
     * Essentially the setScreen method without pushing the last screen
     *
     * @param oldScreen screen to return to
     */
    public void returnTo(final Screen oldScreen) {
        System.out.printf("Returning to:%s", oldScreen);
        //Clear old screen
        this.screen.close();
        //Init new screen
        oldScreen.initialise();
        //Set new screen
        this.screen = oldScreen;
    }

    public void addTextToUI(final String text) {
        getScreen().addUIComponent(new TextComponent(WordUtils.wrap(text, 40), Screen.currentY));
    }

    /* ----------------- Getters ----------------- */

    public Screen getScreen() {
        return screen;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public DialogHandler getDialogHandler() {
        return dialogHandler;
    }
}
