/*
 * @author Roman
 * Last modified: 04.01.21, 21:01 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core;

import net.bplaced.abzzezz.core.file.BasicFile;
import net.bplaced.abzzezz.core.handler.FileHandler;
import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.handler.TextureHandler;
import net.bplaced.abzzezz.core.ui.screen.BasicScreen;
import net.bplaced.abzzezz.core.util.DeltaTime;
import net.bplaced.abzzezz.core.util.logging.LogType;
import net.bplaced.abzzezz.core.util.logging.Logger;
import net.bplaced.abzzezz.game.handler.Settings;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.io.File;

import static org.lwjgl.opengl.GL11.*;

/**
 * Singleton Class for all basic core functionality
 */
public class Core implements Basic {

    private static Core instance;
    private final int width, height;
    private String gameName, fontDir;
    private int fpsSync;
    private long lastFrame;

    private float gameVersion;

    private File mainDir;

    private FileHandler fileHandler;

    private BasicScreen basicScreen;

    private OpenGLReference openGLReference;

    /**
     * Engine Core init. If left empty it gets auto set.
     *
     * @param gameName
     * @param gameVersion
     * @param width
     * @param height
     * @param startBasicScreen
     * @param fpsSync
     */
    public Core(String gameName, float gameVersion, int width, int height, BasicScreen startBasicScreen, int fpsSync, File outDir, String fontDir) {
        this.gameName = gameName;
        this.gameVersion = gameVersion;
        this.fontDir = fontDir;
        this.basicScreen = startBasicScreen;
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
     * @param startBasicScreen
     */
    public Core(int width, int height, BasicScreen startBasicScreen) {
        this.gameName = "Test Game";
        this.gameVersion = 1.0F;
        this.fontDir = "./font/";
        this.basicScreen = startBasicScreen;
        this.fpsSync = 144;
        this.mainDir = new File(System.getProperty("user.home"), gameName);
        this.width = width;
        this.height = height;
        this.initHeaders();
    }

    public static Core getInstance() {
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
        this.fileHandler = new FileHandler();
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
        fileHandler.save();
        Logger.log("Saving files", LogType.INFO);
        //Clean
        ShaderHandler.SHADER_HANDLER.deletePrograms();
        TextureHandler.TEXTURE_HANDLER.deleteTextures();
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
         * Enable repeated events
         */

        Keyboard.enableRepeatEvents(true);

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
        glViewport(0, 0, width, height);
        glLoadIdentity();

        lastFrame = getTime();
        if (openGLReference != null) openGLReference.onGLInitialised();
        fileHandler.load();
        basicScreen.init();
    }

    /**
     * Update Method
     */
    private void update() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        final long time = getTime();
        final int deltaTime = (int) (time - lastFrame);
        lastFrame = time;
        DeltaTime.deltaTime = deltaTime;

        basicScreen.drawShader();
        basicScreen.draw();
        if (Settings.crtBackground)
            ShaderHandler.SHADER_HANDLER.getBitShader().draw();

        while (Mouse.next()) {
            if (Mouse.getEventButtonState())
                basicScreen.mousePressed(Mouse.getEventButton());
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState())
                basicScreen.keyTyped(Keyboard.getEventKey(), Keyboard.getEventCharacter());
        }
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
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

    public BasicScreen getScreen() {
        return basicScreen;
    }

    public void setScreen(BasicScreen newScreen) {
        //Clear old screen
        this.basicScreen.close();
        screenStack.push(this.basicScreen);
        //Init new screen
        newScreen.init();
        //Set new screen
        this.basicScreen = newScreen;
    }

    public void returnTo(BasicScreen oldScreen) {
        //Clear old screen
        this.basicScreen.close();
        //Init new screen
        oldScreen.init();
        //Set new screen
        this.basicScreen = oldScreen;
    }

    public void setOpenGLReference(OpenGLReference openGLReference) {
        this.openGLReference = openGLReference;
    }

    public void addSaveFile(BasicFile file) {
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

    public FileHandler getFileManager() {
        return fileHandler;
    }

    public interface OpenGLReference {
        void onGLInitialised();

        void closeRequested();
    }

}
