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

    public FontUtil(String fontName, int size) {
        try {
            final String fontDir = Core.getInstance().getFontDir() + fontName + ".ttf";
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fontDir);
            final Font awtFont = Font.createFont(Font.PLAIN, inputStream);
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

}