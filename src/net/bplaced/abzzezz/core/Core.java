/*
 * @author Roman
 * Last modified: 07.01.21, 23:08 by Roman
 *
 *
 *
 */

/*
 * @author Roman
 * Last modified: 07.01.21, 23:08 by Roman
 *
 *
 *
 */

package net.bplaced.abzzezz.core;

import net.bplaced.abzzezz.core.util.Basic;
import net.bplaced.abzzezz.core.util.DeltaTime;
import net.bplaced.abzzezz.core.util.OpenGLListener;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.Display.*;
import static org.lwjgl.opengl.GL11.*;

public class Core implements Basic {

    private long lastFrame;

    private OpenGLListener openGLListener;


    /**
     * Gets called every time the screen is updated
     * Calculates the delta time for now
     */
    private void update() {
        final long time = getTime();
        final int deltaTime = (int) (time - lastFrame);
        this.lastFrame = time;
        DeltaTime.deltaTime = deltaTime;

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                keyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
            }
        }
    }

    /**
     * gets called after a key press (or a key is held)
     * @param keyCode the responsible keycode
     * @param keyCharacter the actual character typed
     */
    public void keyPressed(final int keyCode, final char keyCharacter) {

    }

    /**
     * Draws the actual components on screen
     */
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void runCycle() {
        while (!isCloseRequested()) {
            Display.update();
            sync(60);
            update();
            draw();
        }
        destroy();
        if (openGLListener != null)
            openGLListener.onDisplayCloseRequested();

        System.exit(0);
    }

    protected void initialiseGL(final int width, final int height) {
        try {
            setDisplayMode(new DisplayMode(width, height));
            create();
            setTitle(gameName);
        } catch (final LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        if (openGLListener != null)
            openGLListener.onDisplayCreated();
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

        this.lastFrame = getTime();

        if (openGLListener != null)
            openGLListener.onGLInitialised();

        this.runCycle();
    }

    private long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public void setOpenGLListener(OpenGLListener openGLListener) {
        this.openGLListener = openGLListener;
    }
}
