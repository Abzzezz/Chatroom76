/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 14:04
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.engine;

import net.bplaced.abzzezz.engine.file.CustomFile;
import net.bplaced.abzzezz.engine.file.FileManager;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.utils.LogType;
import net.bplaced.abzzezz.engine.utils.Logger;
import net.bplaced.abzzezz.engine.utils.Util;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.*;
import java.io.File;

import static org.lwjgl.opengl.GL11.*;

public class EngineCore {

    private static EngineCore instance;
    private final int width;
    private final int height;
    private String gameName, fontDir;
    private float gameVersion;
    private Screen screen;
    private File mainDir;
    private int fpsSync;
    private OpenGLReference openGLReference;

    private FileManager fileManager;

    /**
     * Engine Core init. If left empty it gets auto set.
     *
     * @param gameName
     * @param gameVersion
     * @param width
     * @param height
     * @param startScreen
     * @param fpsSync
     */
    public EngineCore(String gameName, float gameVersion, int width, int height, Screen startScreen, int fpsSync, File outDir, String fontDir) {
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.fontDir = fontDir;
        this.screen = startScreen;
        this.fpsSync = fpsSync;
        this.mainDir = outDir;
        this.width = width;
        this.height = height;
        this.initHeaders();
    }

    /**
     * Auto Config
     *
     * @param width
     * @param height
     * @param startScreen
     */
    public EngineCore(int width, int height, Screen startScreen) {
        this.gameName = "Test Game";
        this.gameVersion = 1.0F;
        this.fontDir = "./font/";
        this.screen = startScreen;
        this.fpsSync = 60;
        this.mainDir = new File(System.getProperty("user.home"), gameName);
        this.width = width;
        this.height = height;
        this.initHeaders();
    }

    public static EngineCore getInstance() {
        return instance;
    }

    /**
     * Method to initialise headers before the engine gets configured further
     */
    private void initHeaders() {
        instance = this;
        Logger.log("Initialising headers", LogType.INFO);
        /*
        Create directory if it does not exists
         */
        this.fileManager = new FileManager();
    }

    /**
     * Start method. Must be called after configuring
     */
    public void start() {
        /*
        For Debug purposes
         */
        Logger.log("Game Engine starting", LogType.INFO);
        Logger.log("Game name:" + gameName, LogType.INFO);
        Logger.log("Game version: " + gameVersion, LogType.INFO);
        Logger.log("fps Sync: " + fpsSync, LogType.INFO);
        Logger.log("Font Path: " + fontDir, LogType.INFO);
        Logger.log("Loading files", LogType.INFO);

        if (!mainDir.exists()) mainDir.mkdir();

        fileManager.load();
        Logger.log("Game starting", LogType.INFO);

        //System.setProperty("org.lwjgl.util.Debug", "true");
        run(width, height);
    }

    /**
     * @param width
     * @param height
     */
    private void run(int width, int height) {
        initGL(width, height);
        if (openGLReference != null) openGLReference.onGLInitialised();

        while (true) {
            update();
            Display.update();
            Display.sync(fpsSync);
            if (Display.isCloseRequested()) {
                if (openGLReference != null) openGLReference.closeRequested();
                shutdown();
                Display.destroy();
                System.exit(0);
            }
        }
    }

    private void shutdown() {
        fileManager.save();
        Logger.log("Saving files", LogType.INFO);
    }

    /**
     * Init GL Method to initialise OpenGL
     *
     * @param width
     * @param height
     */
    private void initGL(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Display.setTitle(gameName + gameVersion);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        /*
        Init first screen
         */

        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);

        glDisable(GL_LIGHTING);
        glClearDepth(1);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(0, width, height, 0, 0.0f, 1.0f);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glViewport(0, 0, width, height);
        screen.init();
    }

    /**
     * Update Method
     */
    private void update() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        screen.drawShader();
        screen.drawScreen();

        while (Mouse.next()) {
            if (Mouse.getEventButtonState())
                screen.mousePressed(Mouse.getEventButton());
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) screen.keyTyped(Keyboard.getEventKey(), Keyboard.getEventCharacter());
        }
    }

    /**
     * Getters and setters to configure
     */

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public float getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(float gameVersion) {
        this.gameVersion = gameVersion;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        //Clear old screen
        this.screen.getUiComponents().clear();
        //Init new screen
        screen.init();
        //Set new screen
        this.screen = screen;
    }

    public void setOpenGLReference(OpenGLReference openGLReference) {
        this.openGLReference = openGLReference;
    }

    public void addSaveFile(CustomFile file) {
        getFileManager().getFiles().add(file);
    }

    public File getMainDir() {
        return mainDir;
    }

    public void setMainDir(File mainDir) {
        this.mainDir = mainDir;
    }

    public int getFpsSync() {
        return fpsSync;
    }

    public void setFpsSync(int fpsSync) {
        this.fpsSync = fpsSync;
    }

    public String getFontDir() {
        return fontDir;
    }

    public void setFontDir(String fontDir) {
        this.fontDir = fontDir;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public void setFileManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void setTextColor(Color color) {
        Util.textColor = color;
    }

    public void setMainColor(Color color) {
        Util.mainColor = color;
    }

    public void setTextFont(String fontName) {
        Util.textFont = fontName;
    }

    public void setBackgroundColor(Color color) {
        Util.backgroundColor = color;
    }

    public interface OpenGLReference {
        void onGLInitialised();

        void closeRequested();
    }

}
