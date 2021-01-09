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
import net.bplaced.abzzezz.game.ui.components.CommandLine;
import net.bplaced.abzzezz.game.ui.screen.LoadingScreen;
import org.apache.commons.lang3.text.WordUtils;

public class Game extends Core implements Basic {

    /* ----------------- Singleton ----------------- */
    public static final Game GAME = new Game();

    /* ----------------- UI ----------------- */
    private Screen screen;
    private CommandLine commandLine;


    /* ----------------- Handlers ----------------- */
    private SavableHandler savableHandler;


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

            }
        });
        this.initialiseGL(width, height);
    }

    private void initialiseHandlers() {
        this.savableHandler = new SavableHandler();
    }

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

    public void setScreen(final Screen newScreen) {
        if (this.screen != null)
            this.screen.close();
        newScreen.initialise();
        this.screen = newScreen;
    }

    public void addTextToUI(final String text) {
       getScreen().addUIComponent(new TextComponent(WordUtils.wrap(text, 40), Screen.yPos));
    }

    /* ----------------- Getters ----------------- */

    public SavableHandler getSavableHandler() {
        return savableHandler;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public Screen getScreen() {
        return screen;
    }

}
