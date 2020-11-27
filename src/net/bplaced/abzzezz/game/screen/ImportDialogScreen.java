package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.ui.uicomponents.Progressbar;
import net.bplaced.abzzezz.engine.ui.uicomponents.TextField;
import net.bplaced.abzzezz.engine.utils.FontUtil;
import net.bplaced.abzzezz.engine.utils.Util;
import net.bplaced.abzzezz.game.GameMain;
import org.lwjgl.input.Keyboard;

public class ImportDialogScreen extends Screen {

    protected FontUtil textFont;
    private final Screen parent;
    private TextField textField;
    private Progressbar progressbar;

    private final String title = "Import";

    public ImportDialogScreen(final Screen parent) {
        this.parent = parent;
    }

    @Override
    public void init() {
        textFont = new FontUtil(Util.textFont, 60);

        int size = textFont.getStringWidth(title);
        getUiComponents().add(textField = new TextField(getWidth() / 2 - size / 2, getHeight() / 2, size, 20, "URL"));

        final int componentWidth = 100, componentHeight = 15;
        final int yPos = getHeight() - componentHeight * 3;
        getUiComponents().add(new CustomButton(1, "Import", getWidth() / 2 - 50, yPos, componentWidth, componentHeight));
        getUiComponents().add(progressbar = new Progressbar("Download",getWidth() / 2 - 50, getHeight() / 1.5F, componentWidth, componentHeight, 0, 100, 0));

        super.init();
    }

    @Override
    public void buttonPressed(float buttonID) {
        if (buttonID == 1) GameMain.getInstance().getDialogHandler().downloadDialog(textField.toString());

        super.buttonPressed(buttonID);
    }

    @Override
    public void drawScreen() {
        textFont.drawString(title, getWidth() / 2 - textFont.getStringWidth(title) / 2, getHeight() / 6, Util.TRANSPARENT_WHITE_75);
        progressbar.increment(1);
        super.drawScreen();
    }

    @Override
    public void drawShader() {
        GameMain.getInstance().getShader().draw();
        super.drawShader();
    }

    @Override
    public void keyTyped(int keyCode, char keyTyped) {
        if (keyCode == Keyboard.KEY_ESCAPE) EngineCore.getInstance().setScreen(parent);
        super.keyTyped(keyCode, keyTyped);
    }
}
