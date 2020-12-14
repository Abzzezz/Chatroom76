/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 13:59
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.util.render;


import net.bplaced.abzzezz.core.Core;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.InputStream;

public class FontUtil {

    private UnicodeFont unicodeFont;
    private Font awtFont;

    private final int size;

    public FontUtil(String fontName, int size) {
        this.size = size;
        try {
            final String fontDir = Core.getInstance().getFontDir() + fontName + ".ttf";
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fontDir);
            assert inputStream != null;
            awtFont = Font.createFont(Font.PLAIN, inputStream);
            this.unicodeFont = new UnicodeFont(awtFont, size, false, false);

            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
            unicodeFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Font rendering using Slick
     *
     * @param text
     * @param xPos
     * @param yPos
     * @param color
     */
    public void drawString(String text, float xPos, float yPos, Color color) {
        unicodeFont.drawString(xPos, yPos, text, new org.newdawn.slick.Color(color.getRGB()));
    }

    public int getStringWidth(String text) {
        return this.unicodeFont.getWidth(text);
    }

    public float getHeight(final String text) {
        return this.unicodeFont.getHeight(text);
    }

    public float getHeight() {
        return this.unicodeFont.getFont().getSize();
    }

    public int getSize() {
        return size;
    }

    /**
     * TODO: REPLACE!!
     * @param s
     * @param width
     * @return
     */
    public int determineFontSizeMax(String s, int width, int size) {
        int max = size;
        while (new UnicodeFont(awtFont, max, false, false).getWidth(s) >= width)
            max--;
        return max;
    }

}