package net.bplaced.abzzezz.game.screen;

import net.bplaced.abzzezz.engine.EngineCore;
import net.bplaced.abzzezz.engine.ui.Screen;
import net.bplaced.abzzezz.engine.ui.uicomponents.Progressbar;
import net.bplaced.abzzezz.engine.ui.uicomponents.TextField;
import net.bplaced.abzzezz.game.GameMain;
import org.lwjgl.input.Keyboard;

public class ImportDialogScreen extends Screen {

    private final Screen parent;
    private TextField textFieldURL;
    private Progressbar downloadProgress;

    private final String title = "Import";

    public ImportDialogScreen(final Screen parent) {
        this.parent = parent;
    }

    @Override
    public void init() {
        final int componentHeight = 20;
        int size = bigFont.getStringWidth(title);

        final int xPos = getWidth() / 2 - size / 2;

        getUiComponents().add(textFieldURL = new TextField(xPos, getHeight() / 2, size, componentHeight, "URL"));
        getUiComponents().add(downloadProgress = new Progressbar("Download: ", xPos, getHeight() / 1.5F, size, componentHeight, 0, 0, 0));


        getUiComponents().add(new CustomButton(1, "Import", getWidth() / 2 - 50, getHeight() - componentHeight * 3, 100, componentHeight));
        super.init();
    }

    @Override
    public void buttonPressed(float buttonID) {
        if (buttonID == 1)
            GameMain.getInstance().getDialogHandler().downloadDialog(textFieldURL.toString(), integer -> downloadProgress.setMax(integer), integer -> downloadProgress.increment(integer), s -> downloadProgress.setTitle("Download: " + s));
        super.buttonPressed(buttonID);
    }

    @Override
    public void drawScreen() {
        drawCenteredMenuString(title, getWidth() / 2, getHeight() / 6);
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
