/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.util.render;


import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.InputStream;

public class FontUtil {

    private final int size;
    private final String fontName;

    private UnicodeFont unicodeFont;
    private Font awtFont;

    public FontUtil(String fontName, String suffix, int size) {
        this.size = size;
        this.fontName = fontName;
        try {
            final String fontPath = "./font/".concat(fontName.concat(".".concat(suffix)));
            final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fontPath);
            assert inputStream != null;
            this.awtFont = Font.createFont(Font.PLAIN, inputStream);
            this.unicodeFont = new UnicodeFont(awtFont, size, false, false);
            unicodeFont.addAsciiGlyphs();
            unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
            unicodeFont.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawString(String text, float xPos, float yPos, org.newdawn.slick.Color color) {
        unicodeFont.drawString(xPos, yPos, text, color);
    }

    public void drawString(String text, float xPos, float yPos, Color color) {
        unicodeFont.drawString(xPos, yPos, text, new org.newdawn.slick.Color(color.getRGB()));
    }

    public void drawWhiteString(String text, float xPos, float yPos) {
        unicodeFont.drawString(xPos, yPos, text);
    }

    public int getStringWidth(String text) {
        return this.unicodeFont.getWidth(text);
    }

    public int getHeight(final String text) {
        return this.unicodeFont.getHeight(text);
    }

    public int getHeight() {
        return this.unicodeFont.getFont().getSize();
    }

    public int getSize() {
        return size;
    }

    public String getFontName() {
        return fontName;
    }

    /**
     * TODO: REPLACE!!
     *
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