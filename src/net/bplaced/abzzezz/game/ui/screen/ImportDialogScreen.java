/*
 * @author Roman
 * Last modified: 03.01.21, 20:22 by kursc
 *
 *
 *
 */

package net.bplaced.abzzezz.game.ui.screen;

import net.bplaced.abzzezz.core.ui.screen.BasicScreen;
import net.bplaced.abzzezz.core.ui.components.Progressbar;
import net.bplaced.abzzezz.core.ui.components.Text;
import net.bplaced.abzzezz.core.ui.components.TextField;
import net.bplaced.abzzezz.game.GameMain;
import net.bplaced.abzzezz.game.command.OptionType;
import net.bplaced.abzzezz.game.ui.component.InputLine;
import net.bplaced.abzzezz.game.ui.component.Option;

public class ImportDialogScreen extends BasicScreen {

    private TextField textFieldURL;
    private Progressbar downloadProgress;

    @Override
    public void init() {
        final int componentHeight = 20;
        String title = "Import";
        final int size = bigFont.getStringWidth(title);
        final int xPos = getWidth() / 2 - size / 2;

        getUiComponents().add(textFieldURL = new TextField(xPos, getHeight() / 2, size, componentHeight, "URL"));
        getUiComponents().add(downloadProgress = new Progressbar("Download: ", xPos, getHeight() / 1.5F, size, componentHeight, 0, 0, 0));

        getOptions().add(new Option(getWidth() / 2 - 50, getHeight() - componentHeight * 3, "Import", OptionType.EXECUTABLE, consumer -> GameMain.INSTANCE.getDialogHandler().download(textFieldURL.toString(), integer -> downloadProgress.setMax(integer), integer -> downloadProgress.increment(integer), s -> downloadProgress.setTitle("Download: " + s))));

        getUiComponents().add(new Text(getWidth() / 2, getHeight() / 6, title, mainColor, true, bigFont));

        getUiComponents().add(new InputLine());
        super.init();
    }
}
