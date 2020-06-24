package net.bplaced.abzzezz.game.dialog;

import java.awt.*;

public class DialogLine {

    private final String dialog;
    private final Color textColor;

    public DialogLine(String dialog, Color textColor) {
        this.dialog = dialog;
        this.textColor = textColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public String getDialog() {
        return dialog;
    }
}
