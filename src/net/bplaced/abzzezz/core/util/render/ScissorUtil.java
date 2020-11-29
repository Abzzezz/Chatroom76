/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 08.06.20, 12:50
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
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
