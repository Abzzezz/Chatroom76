/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 13:36
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.engine.utils;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseUtil {

    /**
     * Checks if mouse in a quad radius
     *
     * @param xPos
     * @param yPos
     * @param width
     * @param height
     * @return
     */
    public static boolean mouseHovered(float xPos, float yPos, float width, float height) {
        int[] mouse = getMousePositions();
        return mouse[0] >= xPos && mouse[0] <= xPos + width && mouse[1] >= yPos && mouse[1] <= yPos + height;
    }

    /**
     * Check if mouse is close to position (in radius)
     *
     * @param xPos
     * @param yPos
     * @param radius
     * @return
     */

    public static boolean mouseHovered(float xPos, float yPos, double radius) {
        double px = getMousePositions()[0] - xPos;
        double py = getMousePositions()[1] - yPos;
        return Math.sqrt(px * px + py * py) <= radius;
    }

    /**
     * Mouse positions
     *
     * @return
     */
    public static int[] getMousePositions() {
        int x = Mouse.getX();
        int y = Display.getHeight() - Mouse.getY();
        return new int[]{x, y};
    }
}
