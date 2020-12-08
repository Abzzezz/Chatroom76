package net.bplaced.abzzezz.game;

import net.bplaced.abzzezz.core.Core;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import net.bplaced.abzzezz.game.file.SettingsFile;
import net.bplaced.abzzezz.game.handler.DialogHandler;
import net.bplaced.abzzezz.game.handler.SettingsHandler;
import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.game.sound.SoundPlayer;
import net.bplaced.abzzezz.game.ui.screen.MainMenu;

import java.io.File;

/**
 * Singleton main
 */

public class GameMain {

    public final static GameMain INSTANCE = new GameMain();

    private final String gameName = "Chatroom76";
    private String serverURL;

    private DialogHandler dialogHandler;
    private SoundPlayer soundPlayer;
    private SettingsHandler settingsHandler;

    private void startEngine() {
        //Initialise and start engine
        final Core core = new Core(600, 600, new MainMenu());
        core.setGameName(gameName);
        core.setMainDir(new File(System.getenv("LOCALAPPDATA"), gameName));
        core.addSaveFile(new SettingsFile());

        core.setOpenGLReference(new Core.OpenGLReference() {
            @Override
            public void onGLInitialised() {
                ShaderHandler.SHADER_HANDLER.setupShaders();
            }

            @Override
            public void closeRequested() {
                dialogHandler.savePreviousDialog();
            }

        });
        core.start();
    }

    /**
     * Initialise all handlers, then start engine
     */
    public void initHandlers() {
        this.serverURL = "http://abzzezz.bplaced.net/Chatroom/";
        this.dialogHandler = new DialogHandler();
        this.settingsHandler = new SettingsHandler();
        this.soundPlayer = new SoundPlayer();

        Logger.log("Handlers initialised", LogType.INFO);
        this.startEngine();
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public void setSettingsHandler(SettingsHandler settingsHandler) {
        this.settingsHandler = settingsHandler;
    }

    public String getFileOnServer(String file) {
        return getServerURL() + file;
    }

    public String getServerURL() {
        return serverURL;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public DialogHandler getDialogHandler() {
        return dialogHandler;
    }

    public void setDialogHandler(DialogHandler dialogHandler) {
        this.dialogHandler = dialogHandler;
    }

    public SoundPlayer getSoundPlayer() {
        return soundPlayer;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    public String getGameName() {
        return gameName;
    }

}
