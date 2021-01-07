/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.core.ui;

import net.bplaced.abzzezz.core.Basic;
import net.bplaced.abzzezz.core.handler.ShaderHandler;
import net.bplaced.abzzezz.core.ui.components.Button;
import net.bplaced.abzzezz.core.ui.components.UIComponent;
import net.bplaced.abzzezz.game.ui.component.Option;
import org.lwjgl.opengl.Display;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BasicScreen implements Basic {

    private final List<UIComponent> uiComponents = new CopyOnWriteArrayList<>();
    private final List<Option> options = new CopyOnWriteArrayList<>();


    /**
     * Int method to add things like buttons etc.
     */
    public void init() {
        this.initComponents();
    }

    /**
     * Gets called whenever the screen is switched
     */

    public void close() {
        getUiComponents().clear();
        getOptions().clear();
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
        options.forEach(Option::initComponent);
    }

    /*
    Screen drawing - simple
     */
    public void draw() {
        uiComponents.forEach(UIComponent::drawComponent);
        options.forEach(Option::drawComponent);
    }


    public void drawShader() {
        uiComponents.forEach(UIComponent::drawShader);
        options.forEach(Option::drawShader);
        ShaderHandler.SHADER_HANDLER.getBackgroundShader().draw();
    }

    /**
     * Gets called if mouse button down
     *
     * @param mouseButton
     */
    public void mousePressed(int mouseButton) {
        options.forEach(option -> option.mouseListener(mouseButton));
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

    public List<Option> getOptions() {
        return options;
    }

    public Button getButtonByID(float id) {
        return (Button) uiComponents.stream().filter(uiComponent -> uiComponent instanceof Button && ((Button) uiComponent).getId() == id).findFirst().orElse(null);
    }

}
