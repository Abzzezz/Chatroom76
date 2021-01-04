/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util.io;

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
        final int[] mouse = getMousePositions();
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
        final double px = getMousePositions()[0] - xPos;
        final double py = getMousePositions()[1] - yPos;
        return Math.sqrt(px * px + py * py) <= radius;
    }

    /**
     * Mouse positions
     *
     * @return
     */
    public static int[] getMousePositions() {
        final int x = Mouse.getX();
        final int y = Display.getHeight() - Mouse.getY();
        return new int[]{x, y};
    }
}
