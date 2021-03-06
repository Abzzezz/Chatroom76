/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.util.log.LogType;
import net.bplaced.abzzezz.core.util.log.Logger;
import net.bplaced.abzzezz.game.dialog.DialogLoader;
import net.bplaced.abzzezz.game.file.SettingsFile;
import net.bplaced.abzzezz.game.handler.DialogHandler;
import net.bplaced.abzzezz.game.handler.SettingsHandler;
import net.bplaced.abzzezz.game.sound.SoundPlayer;
import net.bplaced.abzzezz.game.ui.screen.MainMenu;

import java.io.File;

/**
 * Singleton main
 */

public class GameMain {

    public final static GameMain INSTANCE = new GameMain();

    private final String gameName = "Chatroom76";

    private DialogHandler dialogHandler;
    private SoundPlayer soundPlayer;
    private SettingsHandler settingsHandler;
    private DialogLoader dialogLoader;

    private void startEngine() {
        //Initialise and start engine
        final Core core = new Core(600, 600, new MainMenu());
        core.setGameName(gameName);
        core.setMainDir(new File(System.getenv("LOCALAPPDATA"), gameName));
        core.addSaveFile(new SettingsFile());

        this.dialogLoader = new DialogLoader();

        core.setOpenGLReference(new Core.OpenGLReference() {
            @Override
            public void onGLInitialised() {
                dialogLoader.loadDialogs();
                ShaderHandler.SHADER_HANDLER.setupShaders();
            }

            @Override
            public void closeRequested() {
                dialogHandler.savePreviousDialog();
                dialogLoader.saveDialogs();
            }

        });
        core.start();
    }

    /**
     * Initialise all handlers, then start engine
     */
    public void initHandlers() {
        this.dialogHandler = new DialogHandler();
        this.settingsHandler = new SettingsHandler();
        this.soundPlayer = new SoundPlayer();

        Logger.log("Handlers initialised", LogType.INFO);
        this.startEngine();
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public DialogHandler getDialogHandler() {
        return dialogHandler;
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public String getGameName() {
        return gameName;
    }

    public DialogLoader getDialogLoader() {
        return dialogLoader;
    }
}
