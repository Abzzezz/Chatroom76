/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util.render;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class ScissorUtil {

    /*
 Enables and disables OpenGL's Scissor function
  */
    public static void enableScissor() {
        glEnable(GL_SCISSOR_TEST);
    }

    public static void disableScissor() {
        glDisable(GL_SCISSOR_TEST);
    }

    public static void scissor(float xPos, float yPos, float width, float height) {
        glScissor((int) xPos, (int) (Display.getHeight() - yPos - height), (int) width, (int) height);
    }
}
