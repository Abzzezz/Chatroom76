package net.bplaced.abzzezz.game;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.utils.GLSLShaderUtil;
import net.bplaced.abzzezz.game.files.SettingsFile;
import net.bplaced.abzzezz.game.handler.DialogHandler;
import net.bplaced.abzzezz.game.handler.SettingsHandler;
import net.bplaced.abzzezz.game.screen.MainMenu;
import net.bplaced.abzzezz.game.sounds.SoundPlayer;
import net.bplaced.abzzezz.game.util.TextureLoader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainClass {

    /**
     * Basic main class
     */

    private DialogHandler dialogHandler;
    private static MainClass inst;
    private SoundPlayer soundPlayer;
    private String serverURL;
    private SettingsHandler settingsHandler;
    private GLSLShaderUtil glslShaderUtil, shader;

    public static void main(String[] args) {
        MainClass mainClass = new MainClass();
        mainClass.initHandlers();
    }

    private void startEngine() {
        /*
        Init Engine
         */
        EngineCore engineCore = new EngineCore(600, 600, new MainMenu());
        engineCore.setGameName("Chatroom");
        engineCore.setMainDir(new File(System.getProperty("user.home"), "Chatroom"));
        engineCore.setBackgroundColor(Color.BLACK);
        engineCore.addSaveFile(new SettingsFile());
        engineCore.setOpenGLReference(new EngineCore.OpenGLReference() {
            @Override
            public void onGLInitialised() {
                glslShaderUtil = new GLSLShaderUtil(getClass().getResource("shaders/vertexshader.txt"), getClass().getResource("shaders/blurshader.txt"));
                shader = new GLSLShaderUtil(getClass().getResource("shaders/vertexshader.txt"), getClass().getResource("shaders/shader.txt"));
            }

            @Override
            public void closeRequested() {
                dialogHandler.savePreviousDialog();
            }
        });
        engineCore.start();

    }

    private void initHandlers() {
        /*
        Handlers before Engine
         */
        inst = this;
        this.serverURL = "http://abzzezz.bplaced.net/Chatroom/";
        this.dialogHandler = new DialogHandler();
        this.settingsHandler = new SettingsHandler();
        this.soundPlayer = new SoundPlayer();
        this.startEngine();

    }

    public GLSLShaderUtil getGlslShaderUtil() {
        return glslShaderUtil;
    }

    public void setShaderTexture(URL shaderTexture) {
        try {
            this.glslShaderUtil.texture = TextureLoader.loadPNGTexture(shaderTexture);
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

    public static MainClass getInstance() {
        return inst;
    }

}