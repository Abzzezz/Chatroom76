/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.handler;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class TextureHandler {

    public static final TextureHandler TEXTURE_HANDLER = new TextureHandler();
    private final List<Integer> textureList = new ArrayList<>();

    public void deleteTextures() {
        getTextureList().forEach(GL11::glDeleteTextures);
    }

    public List<Integer> getTextureList() {
        return textureList;
    }
}
