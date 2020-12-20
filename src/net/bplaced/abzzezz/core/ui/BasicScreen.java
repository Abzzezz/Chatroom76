/*
 * Copyright (c) 2020. Roman P.
 * All code belongs to its owners!
 * Last modified: 29.04.20, 14:02
 * APIS used:
 * LWJGL (https://www.lwjgl.org/)
 * Slick (http://slick.ninjacave.com/slick-util/)
 * Abzzezz Util (https://github.com/Abzzezz/AbzzezzUtil)
 */

package net.bplaced.abzzezz.core.ui;

import net.bplaced.abzzezz.core.ui.components.Button;
import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import org.lwjgl.opengl.Display;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BasicScreen implements BasicComponent {

    private final List<UIComponent> uiComponents = new CopyOnWriteArrayList<>();

    /**
     * Int method to add things like buttons etc.
     */
    public void init() {
        this.initComponents();
    }

    /**
     * Gets called when button is pressed then looks for action event
     *
     * @param buttonID
     */
    public void buttonPressed(float buttonID) {
    }

    private void initComponents() {
        uiComponents.forEach(UIComponent::initComponent);
    }

    /*
    Screen drawing - simple
     */
    public void drawScreen() {
        uiComponents.forEach(UIComponent::drawComponent);
    }


    public void drawShader() {
        uiComponents.forEach(UIComponent::drawShader);
    }

    /**
     * Gets called if mouse button down
     *
     * @param mouseButton
     */
    public void mousePressed(int mouseButton) {
        uiComponents.forEach(uiComponent -> uiComponent.mouseListener(mouseButton));
    }

    /**
     * Gets called when keyboard key is down
     *
     * @param keyCode
     * @param keyTyped
     */
    public void keyTyped(int keyCode, char keyTyped) {
        uiComponents.forEach(uiComponent -> uiComponent.keyListener(keyCode, keyTyped));
    }

    protected int getWidth() {
        return Display.getWidth();
    }

    protected int getHeight() {
        return Display.getHeight();
    }

    public List<UIComponent> getUiComponents() {
        return uiComponents;
    }

    public Button getButtonByID(float id) {
        return (Button) uiComponents.stream().filter(uiComponent -> uiComponent instanceof Button && ((Button) uiComponent).getId() == id).findFirst().get();
    }

    public void drawCenteredMenuString(final String string, final float xPos, final float yPos) {
        bigFont.drawString(string, xPos - bigFont.getStringWidth(string) / 2, yPos, ColorUtil.MAIN_COLOR);
    }
}
