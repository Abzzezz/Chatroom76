/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui.component;

import net.bplaced.abzzezz.core.util.io.MouseUtil;
import net.bplaced.abzzezz.core.util.render.FontUtil;
import net.bplaced.abzzezz.core.util.render.RenderUtil;

import java.util.Comparator;
import java.util.List;

public class Switcher<Item> implements UIComponent {

    private final List<Item> items;
    private final float xPos;
    private final float yPos;
    private final int width;
    private final int height;
    private final String title;
    private final String leftArrow = "<";
    private final String rightArrow = ">";
    private final int textWidth;
    private final FontUtil fontUtil;
    private Item selected;
    private SwitcherListener switcherListener;
    private float
            stringX,
            midHeight,
            rightArrowX,
            titleY,
            leftArrowWidth,
            rightArrowWidth;

    public Switcher(List<Item> listIn, Item selected, float xPos, float yPos, int width, int height, String title) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
        this.title = title;
        this.items = listIn;
        this.selected = selected;
        this.textWidth = width - (textFont.getStringWidth(rightArrow) + textFont.getStringWidth(leftArrow));

        final Item maxItem = listIn.stream().max(Comparator.comparingInt(value -> value.toString().length())).orElse(null);
        assert maxItem != null;
        this.fontUtil = new FontUtil(textFont.getFontName(), textFont.determineFontSizeMax(maxItem.toString(), textWidth, 25));
    }

    @Override
    public void initComponent() {
        refreshPositions();
    }

    @Override
    public void drawComponent() {
        textFont.drawString(title, xPos, titleY, textColor);

        RenderUtil.drawQuad(xPos, yPos, width, height,mainColor);

        RenderUtil.drawQuad(xPos, yPos, leftArrowWidth, height,mainColor);
        RenderUtil.drawQuad(rightArrowX, yPos, rightArrowWidth, height, mainColor);

        textFont.drawString(leftArrow, xPos, midHeight, textColor);
        textFont.drawString(rightArrow, rightArrowX, midHeight, textColor);

        fontUtil.drawString(selected.toString(), stringX, midHeight, textColor);
    }

    @Override
    public void keyListener(int keyCode, char keyTyped) {
    }

    @Override
    public void mouseListener(int mouseButton) {
        int i = items.indexOf(selected);

        final boolean rightArrow = MouseUtil.mouseHovered(rightArrowX, yPos, rightArrowWidth, height);
        final boolean leftArrow = MouseUtil.mouseHovered(xPos, yPos, leftArrowWidth, height);

        if (rightArrow) {
            if (i >= items.size() - 1) i = 0;
            else i++;

        } else if (leftArrow) {
            if (i > 0) i--;
            else i = items.size() - 1;
        }

        if (i != items.indexOf(selected)) {
            final Item item = items.get(i);
            selected = item;

            if (switcherListener != null)
                switcherListener.onItemSelected(item);
        }
        refreshPositions();
    }

    @Override
    public void drawShader() {
    }

    @Override
    public void refreshPositions() {
        stringX = xPos + width / 2 - fontUtil.getStringWidth(selected.toString()) / 2;
        midHeight = yPos + height / 2 - textFont.getHeight() / 2;
        rightArrowX = xPos + width - textFont.getStringWidth(this.rightArrow);
        titleY = yPos - textFont.getHeight();

        leftArrowWidth = textFont.getStringWidth(leftArrow);
        rightArrowWidth = textFont.getStringWidth(rightArrow);
    }

    @Override
    public float getXPos() {
        return xPos;
    }

    @Override
    public float getYPos() {
        return yPos;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setSwitcherListener(SwitcherListener switcherListener) {
        this.switcherListener = switcherListener;
    }

    public interface SwitcherListener {
        <Item> void onItemSelected(Item item);
    }
}
