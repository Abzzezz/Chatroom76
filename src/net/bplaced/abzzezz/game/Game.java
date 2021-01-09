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
import net.bplaced.abzzezz.core.ui.component.components.TextComponent;
import net.bplaced.abzzezz.core.ui.screen.Screen;
import net.bplaced.abzzezz.core.util.Basic;
import net.bplaced.abzzezz.core.util.OpenGLListener;
import net.bplaced.abzzezz.game.data.SettingsSavable;
import net.bplaced.abzzezz.game.ui.components.CommandLine;
import net.bplaced.abzzezz.game.ui.screen.LoadingScreen;
import org.apache.commons.lang3.text.WordUtils;

public class Game extends Core implements Basic {

    /* ----------------- Singleton ----------------- */
    public static final Game GAME = new Game();

    /* ----------------- UI ----------------- */
    private Screen screen;
    private CommandLine commandLine;

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
                setScreen(new LoadingScreen());
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
        SavableHandler.SAVABLE_HANDLER.addSavable(
                new SettingsSavable()
        );
        SavableHandler.SAVABLE_HANDLER.loadAll();
    }

    /**
     * Overwrites the opengl draw loop
     */
    @Override
    public void draw() {
        super.draw();

        this.screen.draw();
        this.commandLine.draw();
    }

    @Override
    public void keyPressed(int keyCode, char keyCharacter) {
        if (screen != null)
            this.screen.keyPressed(keyCode, keyCharacter);
        this.commandLine.keyListener(keyCode, keyCharacter);
        super.keyPressed(keyCode, keyCharacter);
    }

    public void addTextToUI(final String text) {
        getScreen().addUIComponent(new TextComponent(WordUtils.wrap(text, 40), Screen.yPos));
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    /* ----------------- Getters ----------------- */

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(final Screen newScreen) {
        if (this.screen != null)
            this.screen.close();
        newScreen.initialise();
        this.screen = newScreen;
    }

}
