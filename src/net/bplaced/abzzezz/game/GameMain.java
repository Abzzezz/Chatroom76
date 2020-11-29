package net.bplaced.abzzezz.game;

import net.bplaced.abzzezz.core.OpenGLCore;
import net.bplaced.abzzezz.core.util.render.GLSLShaderUtil;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import net.bplaced.abzzezz.game.file.SettingsFile;
import net.bplaced.abzzezz.game.handler.DialogHandler;
import net.bplaced.abzzezz.game.handler.SettingsHandler;
import net.bplaced.abzzezz.game.screen.MainMenu;
import net.bplaced.abzzezz.game.sounds.SoundPlayer;
import net.bplaced.abzzezz.core.util.render.TextureLoader;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Singleton main
 */

public class GameMain {

    public final static GameMain INSTANCE = new GameMain();

    private final String gameName = "Chatroom76";

    private DialogHandler dialogHandler;
    private SoundPlayer soundPlayer;
    private String serverURL;
    private SettingsHandler settingsHandler;
    private GLSLShaderUtil shader;


    private void startEngine() {
        //Initialise and start engine
        final OpenGLCore openGLCore = new OpenGLCore(600, 600, new MainMenu());
        openGLCore.setGameName(gameName);
        openGLCore.setMainDir(new File(System.getProperty("user.home"), gameName));
        openGLCore.addSaveFile(new SettingsFile());

        openGLCore.setOpenGLReference(new OpenGLCore.OpenGLReference() {
            @Override
            public void onGLInitialised() {
                shader = new GLSLShaderUtil(getClass().getResource("util/shaders/vertexshader.vert"), getClass().getResource("util/shaders/backgroundShaderFragment.frag"));
            }

            @Override
            public void closeRequested() {
                dialogHandler.savePreviousDialog();
            }

        });
        openGLCore.start();

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

    public void setShaderTexture(URL shaderTexture) {
        try {
            this.shader.texture = TextureLoader.loadPNGTexture(shaderTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GLSLShaderUtil getShader() {
        return shader;
    }

    public SettingsHandler getSettingsHandler() {
        return settingsHandler;
    }

    public String getFileOnServer(String file) {
        return getServerURL() + file;
    }

    public String getServerURL() {
        return serverURL;
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

    public void setDialogHandler(DialogHandler dialogHandler) {
        this.dialogHandler = dialogHandler;
    }

    public void setSoundPlayer(SoundPlayer soundPlayer) {
        this.soundPlayer = soundPlayer;
    }

    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    public void setSettingsHandler(SettingsHandler settingsHandler) {
        this.settingsHandler = settingsHandler;
    }

    public void setShader(GLSLShaderUtil shader) {
        this.shader = shader;
    }
}
