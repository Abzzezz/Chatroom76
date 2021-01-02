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

import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.ui.components.Button;
import net.bplaced.abzzezz.core.ui.components.KeyButton;
import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.core.util.render.ColorUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BasicScreen implements BasicComponent {

    private final List<UIComponent> uiComponents = new CopyOnWriteArrayList<>();
    private final List<KeyButton> keyButtons = new CopyOnWriteArrayList<>();

    protected KeyButton selectedKeyButton;

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

    public void keyButtonEntered(final float id) {
    }

    private void initComponents() {
        uiComponents.forEach(UIComponent::initComponent);
        keyButtons.forEach(KeyButton::initComponent);
        if (keyButtons.size() > 0) {
            setSelectedKeyButton(keyButtons.get(0));
        }
    }

    /*
    Screen drawing - simple
     */
    public void draw() {
        uiComponents.forEach(UIComponent::drawComponent);
        keyButtons.forEach(KeyButton::drawComponent);
    }


    public void drawShader() {
        uiComponents.forEach(UIComponent::drawShader);
        keyButtons.forEach(KeyButton::drawShader);
        ShaderHandler.SHADER_HANDLER.getBackgroundShader().draw();
    }

    /**
     * Gets called if mouse button down
     *
     * @param mouseButton
     */
    public void mousePressed(int mouseButton) {
        keyButtons.forEach(keyButton -> keyButton.mouseListener(mouseButton));
        uiComponents.forEach(uiComponent -> uiComponent.mouseListener(mouseButton));
    }

    /**
     * Gets called when keyboard key is down
     *
     * @param keyCode
     * @param keyTyped
     */
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_UP) {
            final int index = keyButtons.indexOf(getSelectedKeyButton());
            int previousIndex;
            if (index == -1) return;
            else if (index - 1 == -1) {
                previousIndex = keyButtons.size() - 1;
            } else
                previousIndex = index - 1;

            if (previousIndex == -1) return;
            selectedKeyButton.setSelected(false);
            final KeyButton newSelected = keyButtons.get(previousIndex);

            newSelected.setSelected(true);
            setSelectedKeyButton(newSelected);
        } else if (keyCode == Keyboard.KEY_DOWN) {
            final int index = keyButtons.indexOf(getSelectedKeyButton());

            int nextIndex;
            if (index == -1) return;
            else if (index + 1 > keyButtons.size() - 1) {
                nextIndex = 0;
            } else
                nextIndex = index + 1;

            selectedKeyButton.setSelected(false);
            final KeyButton newSelected = keyButtons.get(nextIndex);
            newSelected.setSelected(true);
            setSelectedKeyButton(newSelected);
        }
        keyButtons.forEach(keyButton -> keyButton.keyListener(keyCode, keyTyped));
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

    public List<KeyButton> getKeyButtons() {
        return keyButtons;
    }

    public Button getButtonByID(float id) {
        return (Button) uiComponents.stream().filter(uiComponent -> uiComponent instanceof Button && ((Button) uiComponent).getId() == id).findFirst().orElse(null);
    }

    public void drawCenteredMenuString(final String string, final float xPos, final float yPos) {
        bigFont.drawString(string, xPos - bigFont.getStringWidth(string) / 2, yPos, ColorUtil.MAIN_COLOR);
    }

    public KeyButton getSelectedKeyButton() {
        return selectedKeyButton;
    }

    public void setSelectedKeyButton(KeyButton selectedKeyButton) {
        this.selectedKeyButton = selectedKeyButton;
    }
}
